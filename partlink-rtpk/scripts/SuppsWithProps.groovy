//import com.hp.hpl.jena.rdf.model.*
import com.hp.hpl.jena.query.*
//import com.hp.hpl.jena.shared.*
//import com.hp.hpl.jena.shared.impl.*
//import com.hp.hpl.jena.shared.*
//import com.hp.hpl.jena.shared.impl.*

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
'''select distinct ?y
where{ 
#	?a prod:itemNameCode '49888'.
#	?a rdfs:subClassOf ?b.
	?c ?d log:hasCageCode.
	?x log:hasCageName ?y 
	#?e log:hasCageCode ?f.
	#?f rdfs:label ?g
	} order by ?y '''

//sparql =
'''
SELECT distinct ?mat
WHERE { ?niin prod:nationalItemId '015030806'.
        ?niin rdfs:subClassOf ?res.
        ?res owl:onProperty .
        ?res owl:hasValue ?val.
        ?val  ?mat.
}
'''

//sparql =
'''
SELECT ?s
{ ?s text:query (rdfs:label 'm' 10);
	 rdfs:label ?label
}
'''//not working
//sparql =
'''
SELECT DISTINCT ?cage ?cageCode {
#	?logNiin log:hasProductNIIN prod:NIIN010077987 .
	?logNiin log:hasProductNIIN prod:NIIN015127231 . 
	?logNiin log:hasReferenceNumber ?refNum .
	?refNum log:hasCage ?cage .
	?cage log:hasCageCode ?cageCode
  }
'''//[89875, 64616, 67032, Z04E6, 65442, FAE78]
//sparql = '''select * {<http://xsb.com/swiss/product#node00:UNCLASSIFIED_INCS> ?a ?b}'''	
//execute(query, sparql, service)

//<http://xsb.com/swiss/logistics#CAGE64616>
sparql =
'''
SELECT DISTINCT *{
log:CAGE64616  vcard:hasAddress ?add.
#?add ?x ?y.
#log:CAGE64616  vcard:hasAddress ?phone.
#?phone ?px ?py.
#log:CAGE64616  log:hasCageName ?name.
#?name ?nx ?ny.
#log:CAGE64616  log:hasCageStatus ?stat.
#?stat ?sx ?sy.
#log:CAGE64616  log:hasBusinessSizeCode ?bzs.
#?bzs ?bx ?by.
#log:CAGE64616  log:hasBusinessSizeCode ?bzs.
#?bzs ?bx ?by.
#| _:b0 | <http://xsb.com/swiss/logistics#hasCageName>            | "DEFENSE MEDICAL STANDARDIZATION"                  |
#| _:b0 | <http://xsb.com/swiss/logistics#hasCageStatus>          | <http://xsb.com/swiss/logistics#CageStatus_A>      |
#| _:b0 | <http://www.w3.org/2006/vcard/ns#hasAddress>            | _:b0                                               |
#| _:b0 | <http://www.w3.org/2006/vcard/ns#hasTelephone>          | _:b1                                               |
#| _:b0 | <http://xsb.com/swiss/logistics#hasBusinessSizeCode>    | <http://xsb.com/swiss/logistics#BusinessSize_N>    |
#| _:b0 | <http://xsb.com/swiss/logistics#hasBusinessTypeCode>    | <http://xsb.com/swiss/logistics#BusinessType_N>    |
#| _:b0 | <http://xsb.com/swiss/logistics#hasPrimaryBusinessCode> | <http://xsb.com/swiss/logistics#PrimaryBusiness_N> |
#| _:b0 | <http://xsb.com/swiss/logistics#hasCAO>                 | "S2101A"                                           |
#| _:b0 | <http://xsb.com/swiss/logistics#hasADP>                 | "HQ0338"                                           |
#| _:b0 | <http://xsb.com/swiss/logistics#isWomanOwned>           | "N"                                                | 
}'''

sparql =
'''
SELECT * 
WHERE{
#log:CAGE64616  ?x ?y.
#log:CAGE64616  log:hasCageCode ?y.
?o log:hasCageCode "64616";
vcard:hasAddress ?address.
?address ?x ?y
}
''' //takes long 
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

#?stat ?sx ?sy.
#log:CAGE64616  log:hasBusinessSizeCode ?bzs.
 #       ?cageCode rdfs:subClassOf ?res.
 #       ?cageCode owl:onProperty ?prop.
 #      ?cageCode owl:hasValue ?val.
 #       ?cageCode  ?x ?y.
}
'''
execute(query, sparql, service)

private execute(query, sparql, service) {
	QueryFactory.parse(query, sparql ,null,Syntax.syntaxSPARQL)
	QueryExecution x = QueryExecutionFactory.sparqlService(service,query)
	ResultSet results = x.execSelect()
	ResultSetFormatter.out(System.out, results)
}