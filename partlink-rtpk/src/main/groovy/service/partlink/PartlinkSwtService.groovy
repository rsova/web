package service.partlink

import com.hp.hpl.jena.query.*
import service.partlink.sparql.Sparql

class PartlinkSwtService {
	String serviceUri
	private Query query

	public PartlinkSwtService(String serviceUri){
		this.serviceUri = serviceUri

		query = QueryFactory.make()
		query.limit = 10
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
	}

	protected String execute(String sparql, Map params = null) {
		if(params){
			//ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparql);
			//params.each { key, val -> queryStr.setLiteral(key, val)}
			//sparql = queryStr.toString()
			params.each { key, val -> sparql = sparql.replace('$'<<key,val)}
		}
		QueryFactory.parse(query, sparql , null, Syntax.syntaxSPARQL)
		QueryExecution x = QueryExecutionFactory.sparqlService(serviceUri,query)
		ResultSet results = x.execSelect()
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(out,results)
		return out.toString()		
	}

	public Object lookupSupplierByNiin(String niin){
		return execute(Sparql.NIIC_TO_SUPPS, ['prodNiin':'prod:NIIN'<<niin])
	}
}
