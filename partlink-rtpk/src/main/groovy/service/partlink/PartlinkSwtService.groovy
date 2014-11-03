package service.partlink

import javax.inject.Inject;

import groovyx.gpars.GParsPool;

import com.google.gson.GsonBuilder
import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.rdf.model.*

import groovy.time.*
import net.sf.ehcache.Element
import net.sf.ehcache.management.Cache
import net.sf.ehcache.CacheManager
import service.partlink.sparql.Sparql
import service.web.UspsShippingLookupService
import service.web.WebLookupService;
import utils.*


class PartlinkSwtService {
	@Inject
	WebLookupService webService
	@Inject
	UspsShippingLookupService uspsService


	private String serviceUri
	private Query query
	//private Cache plCache 

	public PartlinkSwtService(String serviceUri){
		this.serviceUri = serviceUri
	}
	
	protected List execute(String sparql, Map params = null) {		
		if(params){
			ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparql);
			params?.var?.each { key, val -> queryStr.setLiteral(key, val)}
			params?.uri?.each{ key, val -> queryStr.setIri(key,val)}
			sparql = queryStr.toString()
			params?.filter?.each{ key, val -> sparql= sparql.replace(key,val)}
			println sparql
		}
		//Query object with aggregation can't be re-used, build it every time
		Query query = initQuery()
		QueryFactory.parse(query, sparql , null, Syntax.syntaxSPARQL)
		QueryExecution x = QueryExecutionFactory.sparqlService(serviceUri,query)
		x.setTimeout(30000)//30 sec
		ResultSet results = x.execSelect()
		def vars = results.getResultVars()
		//ByteArrayOutputStream out = new ByteArrayOutputStream();
		//ResultSetFormatter.outputAsJSON(out,results)
		def all = []		
		while (results.hasNext()) {
		    QuerySolution row = results.next();
			def rowMap = [:]
			vars.each{var ->; rowMap.put(var, row.get(var).toString())}
			all.add(rowMap)
		}
		
		return all		
	}

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

	public Map lookupSupplierByNiin(String niin){
		def start = new Date()
					
		def product = execute(Sparql.NIIC_TO_CAGE_REF,['var':['id':niin]])
		def suppliers = []
		//Try to get suppliers from PartLink - works most of the time
		if(product.'refNum'){
			product.'refNum'.each{ refNum ->
				def supp = execute(Sparql.GAGE_DETAILS_BY_REF ,['uri':['iri':refNum]])//expected single row	
				if(!supp.isEmpty()){suppliers.add(supp?.first())}
			}				
		}
		
		//The join on suppliers is a miss, use web to get cage codes
		if(suppliers.isEmpty()){
			def cages = webService.lookupByNiin(niin)
			if(!cages.isEmpty()){
				cages=cages*.'CAGE CD'
				def tokens = cages.collect{ "'$it'" }.join(',')
				def supps=execute(Sparql.GAGE_DETAILS_BY_CODE,['filter': ['$CCTOKENS':tokens]])
				if(!supps.isEmpty){suppliers.add()}
			}
		}
		
		Map lineItem = [:]
		if(!product.isEmpty()){
			lineItem = product.first() //products all to the same niin, pick the first one
			lineItem.remove('refNum')
			lineItem.putAll(['suppliers': suppliers]) 
		}
		
		use (TimeCategory) {println "time to query : " + (new Date() - start) }//profile end

		return lineItem
	}
	
	public Map lookupSupplierByNiin(String niin, boolean clientFeed){
		 Map lineItem = lookupSupplierByNiin(niin)
		 lineItem = generateShippingEstimate(lineItem)
		 return  (clientFeed)?generateClientFeed(lineItem):lineItem
	}
	

	protected Map generateClientFeed(Map product){
		String itemTemplate = "$product.prodName<br><b>\$$product.price</b>, <i>Niin:$product.NIIN</i>, <font size='3' color='grey'>$product.estimate</font>"
		def supps = []
		for (Map sup in product.suppliers) {
			String supTemplate = "$sup.name, <font size='3' color='blue'><i>Allow $sup.DaysToShip days for delivery</1></font>"
			sup.remove('name')
			Map leafs = generateLeafs(sup)
			def item = ['text':supTemplate]
			item.putAll(leafs)
			supps.add(item)
		}
		return ['text':itemTemplate,'items':supps]
	}
		
	protected Map generateLeafs(Map pairs){
		def leafs = []
		for (pair in pairs) {
			leafs.add(['text':'<i>'<<StringUtil.splitCamelCase(pair.key)<<'</i>'<<': '<<pair.value,'leaf':'true'])
		}
		return [items:leafs]
	}
	
	protected Map generateShippingEstimate(Map lineItem){

		def sups =  lineItem.'suppliers'
		for(Map sup in sups){
			Set range = []
			if( sup?.'Country'?.equals("UNITED STATES")){
				if(sup?.'Zip'){
					sup.putAll(uspsService.lookupPackageServiceStandard(sup?.'Zip'?.substring(0, 5),'96753'))
				}
			}
		}
		
		String estimate = "Special Order Part"
		if(sups && !sups.isEmpty()){
			def range = lineItem.'suppliers'*.'DaysToShip'
			def est = (range?.size()==1)?range?.getAt(0):''+range.min()<<'-'<<range.max()
			estimate = 'Standard Shipping : '<<est<<' days'
		}
		lineItem.put('estimate',estimate)
		return lineItem
	}
	
}
