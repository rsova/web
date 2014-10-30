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
		def start = new Date()//profile	start
			
		def product = execute(Sparql.NIIC_TO_CAGE_REF,['var':['id':niin]])
		def suppliers = []
		if(product.refNum){
			product.refNum.each{ refNum ->
				def supp = execute(Sparql.GAGE_DETAILS_BY_REF ,['uri':['iri':refNum]])//expected single row	
				if(!supp.isEmpty()){suppliers.add(supp?.first())}
			}				
		}else{
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
		 return  (clientFeed)?generateClientFeed(lineItem):lineItem
	}
	
	public Map lookupSupplierByNiinWithCache(String niin){
		//List products = execute(Sparql.NIIC_TO_SUPPS, ['prodNiin':'prod:NIIN'+niin])
		def start = new Date()
		Map products = CacheManager.getInstance()?.getCache("plCache")?.get(niin)?.getObjectValue()
		if(!products){

			products = execute(Sparql.NIIC_TO_SUPPS, ['var':['niin':niin]])

			products = [items:generateClientFeed(products)]
			Element elm = new Element(niin, products)
			elm.timeToLive = 60*60*48 //48 hours
			CacheManager.getInstance()?.getCache("plCache").put(elm)
		}

		use ( TimeCategory ) {
			println "time to query : " + (new Date() - start)
		}

		return products
	}
	
	/*
	 * Original query runs up to 4O seconds, had to brake it up
	 */
	public Map lookupSupplierByNiinOrg(String niin){
		//List products = execute(Sparql.NIIC_TO_SUPPS, ['prodNiin':'prod:NIIN'+niin])
		def start = new Date()
		def products = execute(Sparql.NIIC_TO_SUPPS,['var':['niin':niin]])
		//products = [items:generateClientFeed(products)]

		use ( TimeCategory ) {
			println "time to query : " + (new Date() - start)
		}

		return products
	}
	protected Map generateClientFeed(Map product){
		String itemText = "$product.prodName<br><b>\$$product.price<b>, <i>NIIN:$product.NIIN</i>, <font size='3' color='red'> USPS Standard: 3-8 days</font>"
		def supps = []
		for (supp in product.suppliers) {
			String suppText = supp.name
			supp.remove('name')
			Map leafs = generateLeafs(supp)
			def sup = ['text':suppText]
			sup.putAll(leafs)
			supps.add(sup)
		}
		return ['text':itemText,'items':supps]
	}
		
	protected Map generateLeafs(Map pairs){
		def leafs = []
		for (pair in pairs) {
			leafs.add(['text':pair.key<<': '<<pair.value,'leaf':'true'])
		}
		return [items:leafs]
	}
	
	// Old methods
	public Map lookupSupplierByNiinJson(String niin){
		List products = execute(Sparql.NIIC_TO_SUPPS,['var':['prodNiin':'prod:NIIN'+niin]])
		//List products = execute(Sparql.NIIC_TO_SUPPS, ['prodNiin':'prod:NIIN'+niin])
		Map first = products.get(0)
				
		// LinkedHashMap keeps keys order
		List supps = []
		Map commonProd = first.subMap(first.keySet().toArray()[0..2]) 
		products.each{ Map map -> supps.add(map - commonProd)}
		commonProd.put('suppliers', supps)
		return [product:commonProd]
	}
	
	protected Map generateClientFeedOld(List products){
		Map first = products.get(0)
		
		// LinkedHashMap keeps keys order
		List supps = []
		Map commonProd = first.subMap(first.keySet().toArray()[0..2])
		def desc = commonProd.toString()
		println desc
		//products.each{ Map map ->
		for(prod in products){
			//suplier
			def sup = prod - commonProd
			def item = sup.subMap(sup.keySet().toArray()[0])
			Map itemMap = ['text': item.name]
			//itemMap.putAll(item)
			Map leafs = generateLeafs(sup-item)
			itemMap.putAll(leafs)
			supps.add(itemMap)
		}
		commonProd.put('items', supps)
		commonProd.put('text', desc )
		return commonProd
	}
	
}
