//import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.query.Query
import com.hp.hpl.jena.query.QueryFactory
final String service = "http://api.xsb.com/sparql/query"

Query query = QueryFactory.make()
query.limit = 100
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


def	sparql
//sparql =
sparql =
'''
SELECT * 
WHERE{
?cageCode log:hasCageCode "64616".
?cageCode ?sx ?sy.
?cageCode vcard:hasAddress ?address.
?address vcard:street-address ?streetAddress.
?address vcard:locality ?locality.
?address vcard:region ?region.
?address vcard:country-name ?countryNname.
?address vcard:postal-code ?postalCode.
?cageCode log:hasBusinessSizeCode ?businessSizeCode.
optional {?businessSizeCode ?bx ?by}.
'''
execute(query, sparql, service)

private execute(query, sparql, service) {
	QueryFactory.parse(query, sparql ,null,Syntax.syntaxSPARQL)
	QueryExecution x = QueryExecutionFactory.sparqlService(service,query)
	ResultSet results = x.execSelect()
	ResultSetFormatter.out(System.out, results)
}
