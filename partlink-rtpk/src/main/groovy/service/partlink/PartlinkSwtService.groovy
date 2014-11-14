package service.partlink

import groovy.time.*

import javax.inject.Inject
import service.partlink.sparql.Sparql
import service.web.UspsShippingLookupService
import service.web.WebLookupService
import utils.*

import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.rdf.model.*
import groovy.util.logging.Slf4j

@Slf4j
class PartlinkSwtService {
	static final String USA = "UNITED STATES"
	static final String SPECIAL_ORDER = "Special Order Part"	
	private String serviceUri
	private Query query

	@Inject
	WebLookupService webService
	
	@Inject
	UspsShippingLookupService uspsService

	public PartlinkSwtService(String serviceUri){
		this.serviceUri = serviceUri
	}
	
	//Initialize query and set name spaces
	private Query initQuery() {
		Query query = QueryFactory.create()
		query.limit = 50
		query.prefixMap.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#")
		query.prefixMap.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
		query.prefixMap.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#")
		query.prefixMap.setNsPrefix("text", "http://jena.apache.org/text#")
		query.prefixMap.setNsPrefix("swiss", "http://xsb.com/swiss#")
		query.prefixMap.setNsPrefix("mat", "http://xsb.com/swiss/material#")
		query.prefixMap.setNsPrefix("log", "http://xsb.com/swiss/logistics#")
		query.prefixMap.setNsPrefix("prod", "http://xsb.com/swiss/product#")
		query.prefixMap.setNsPrefix("vcard", "http://www.w3.org/2006/vcard/ns#")
		query.prefixMap.setNsPrefix("type", "http://xsb.com/swiss/types#")
		query.prefixMap.setNsPrefix("proc", "http//xsb.com/swiiss/process#")
		return query
	}

	//Method uses Parameterized query to set all the variable parameters
	protected List execute(String sparql, Map params = null) {		
		if(params){
			ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparql);
			params?.var?.each { key, val -> queryStr.setLiteral(key, val)}
			params?.uri?.each{ key, val -> queryStr.setIri(key,val)}
			sparql = queryStr.toString()
			params?.filter?.each{ key, val -> sparql= sparql.replace(key,val)}
			log.info( sparql)
		}
		
		//Query object with aggregation can't be re-used, build it every time
		Query query = initQuery()
		QueryFactory.parse(query, sparql , null, Syntax.syntaxSPARQL)
		QueryExecution x = QueryExecutionFactory.sparqlService(serviceUri,query)
		x.setTimeout(15000)//15 sec
		ResultSet results = x.execSelect()
		def vars = results.getResultVars()
		def all = []		
		while (results.hasNext()) {
		    QuerySolution row = results.next();
			def rowMap = [:]
			vars.each{var ->; rowMap.put(var, row.get(var).toString())}
			all.add(rowMap)
		}
		
		return all		
	}

	//Working horse of the app method. Runs sequence of sparql and ws calls to put together
	//information for part suppliers 
	public Map lookupSupplierByNiin(String niin){
					
		def product = execute(Sparql.NIIC_TO_CAGE_REF,['var':['id':niin]])
		def suppliers = []
		//Try to get suppliers from PartLink, sometimes can't match niin to cage codes.
		if(product.'refNum'){
			
			for (refNum in product.'refNum') {
				def supp
				try {
				   supp = execute(Sparql.GAGE_DETAILS_BY_REF ,['uri':['iri':refNum]])//expected single row	
				} catch (Exception e) {
					e.printStackTrace()
					continue
				}
				if(!supp.isEmpty()){
					def supplier = supp?.first()
					//exclude cage code dups on different ref numberes
					if(!suppliers*.'CageCode'.contains(supplier.'CageCode')){
						suppliers.add(supplier)
					}
				}
			}				
		}
		
		//The join on suppliers is a miss, use WebFLIS to get cage codes
		if(suppliers.isEmpty()){
			def data = webService.lookupByNiin(niin)
			if(!data.isEmpty()){
				
				def cages = data*.'CAGE CD'
				cages?.removeAll([null])
				if(!cages.isEmpty()){
					def tokens = cages.collect{ "'$it'" }.join(',')
					 suppliers=execute(Sparql.GAGE_DETAILS_BY_CODE,['filter': ['$CCTOKENS':tokens]])
				}
				
				//Also product could be a miss
				if(product.isEmpty()){
					def prod = data*.'PROD'
					prod?.removeAll([null])
					product.add(["prodName":prod.first(), 'NIIN':niin, 'price':'N/A',"assignmentDate":'N/A' ])
				}
			}
		}
		
		Map lineItem = [:]
		if(!product.isEmpty()){
			lineItem = product.first() //products all to the same niin, pick the first one
			lineItem.remove('refNum')
			lineItem.putAll(['suppliers': suppliers]) 
		}
		
		return lineItem
	}
	
	// Looks up suppliers and generates additional advice for shipping.
	// Generates the web client app feed 
	public Map lookupSupplierByNiin(String niin, String zip, boolean clientFeed){
		 Map lineItem = lookupSupplierByNiin(niin)
		 lineItem = generateShippingEstimate(lineItem, zip)
		 return  (clientFeed)?generateClientFeed(lineItem,zip):lineItem
	}
	
	// Modifies and decorates data for web application. 
	protected Map generateClientFeed(Map product, String zip){
		String itemTemplate = "$product.prodName<br><b>\$$product.price</b>, <i>Niin:$product.NIIN</i>, <font size='3' color='grey'>$product.estimate to $zip</font>"
		def supps = []
		for (Map sup in product.suppliers) {
			def msg = (sup.DaysToShip)?"Allow $sup.DaysToShip days for delivery":"Standard Shipping N/A"
			String supTemplate = "$sup.name, <font size='3' color='blue'><br><i>$msg</i></font>"
			sup.remove('name')
			Map leafs = generateLeafs(sup)
			def item = ['text':supTemplate]
			item.putAll(leafs)
			supps.add(item)
		}
		return ['text':itemTemplate,'items':supps]
	}
	
	// Handles lower level records	
	protected Map generateLeafs(Map pairs){
		def leafs = []
		for (pair in pairs) {
			leafs.add(['text':'<i>'<<StringUtil.splitCamelCase(pair.key)<<'</i>'<<': '<<pair.value,'leaf':'true'])
		}
		return [items:leafs]
	}
	
	// Utilizes usps web service to provide shipping estimates
	protected Map generateShippingEstimate(Map lineItem, String zip){

		def sups =  lineItem.'suppliers'
		for(Map sup in sups){
			if( sup?.'Country'?.equals(USA)){
				if(sup?.'Zip'){
					sup.putAll(uspsService.lookupPackageServiceStandard(sup?.'Zip'?.substring(0, 5),zip))
				}
			}
		}
		
		String estimate = SPECIAL_ORDER
		if(sups && !sups.isEmpty()){
			Set range = lineItem.'suppliers'*.'DaysToShip'
			range?.removeAll([null]) //cleanup
			def days = (range?.size()==1)?range?.getAt(0):''+range.min()<<'-'<<range.max()
			estimate = 'Standard Shipping : '<<days<<' days'
		}
		lineItem.put('estimate',estimate)
		return lineItem
	}
	
}
