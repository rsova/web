//@Grab('org.apache.jena:apache-jena-libs:2.11.0')
import com.hp.hpl.jena.rdf.model.*
import com.hp.hpl.jena.query.*
import com.hp.hpl.jena.shared.*
import com.hp.hpl.jena.shared.impl.*

String service = "http://api.xsb.com/sparql/query"

def sparql='''
SELECT DISTINCT ?property
WHERE { [] ?property [] }
ORDER BY ?property
'''	
//sparql = '''
//select * { 
//?s rdf:type <http://xsb.com/swiss/logistics#Cage> .
//?s ?x ?y 
//} limit 100
//'''
sparql = '''select * { ?s rdf:type ?p} limit 100'''
sparql = '''select * { ?s rdfs:subClassOf prod:PART. ?s rdfs:label ?x } limit 100'''
sparql = '''select * { ?s rdfs:subClassOf prod:node01:ELECTRICAL_AND_ELECTRONICS} limit 100'''
sparql = '''
SELECT distinct ?x ?label
{ ?x rdfs:subClassOf ?z .
?z rdfs:label ?label
  FILTER regex(str(?label), "MEDIC", "i") 
}
'''
//sparql = 
'''select * 
where{
<http://xsb.com/swiss/product#NIIN013754314> ?o ?p.
#?o rdfs:label ?label
}'''
//sparql = 
'''select * 
where{
?cls rdfs:subClassOf* <http://xsb.com/swiss/product#NIIN013754314> .
?a rdfs:label ?c
}'''
//sparql = 
'''select * 
where{
?cls rdfs:subClassOf* <http://xsb.com/swiss/product#NIIN013754314> .
?a rdfs:label ?c
}'''
//sparql = 
'''select * 
where{
<http://xsb.com/swiss/product#NIIN013754314> rdfs:subClassOf* ?z .
?a rdfs:label ?c
}''' // interesting
//sparql = 
'''select *
where{
prod:NIIN:013754314 rdfs:subClassOf* ?z .
?a rdfs:label ?c
}''' //worked
//sparql = 
'''select *
where{
?cls rdfs:subClassOf prod:NIIN:013754314 .
?a rdfs:label ?c
}'''

//sparql = 
'''
SELECT * 
#WHERE { prod:NIIN:013754314 ?o ?p .
where {
#<http://xsb.com/swiss/product#NIIN013754314> ?o ?p.
prod:NIIN:013754314 ?o ?p.

#        ?niin rdfs:subClassOf ?res .
#        ?res owl:onProperty .
#        ?res owl:hasValue ?val .
 #       ?val  ?mat .
}'''

//sparql = 
'''
SELECT distinct ?mat
#SELECT *
WHERE { 
<http://xsb.com/swiss/product#NIIN013754314> rdfs:subClassOf ?res .
?res owl:onProperty ?A.
?res owl:hasValue ?val .
?val ?mat ?B.
}
'''
// MATERIAL BY NIIN
//sparql = 
'''
SELECT *
WHERE { ?niin prod:nationalItemId '013754314'.
#		?niin rdfs:subClassOf* ?res.
		?niin rdfs:subClassOf+ ?res.
#		?res owl:onProperty ?a 
#		?res owl:onProperty prod:MATERIAL_AND_LOCATION.
#		?res owl:hasValue ?val.
#		?val type:material_usage_2-MATERIAL ?mat.
}'''
// <http://xsb.com/swiss/product#NIIN013754314> | <http://xsb.com/swiss/product#49888:EAR_PACK_MEDICATION>              | "EAR PACK,MEDICATION"              |
//sparql =
'''select distinct ?cntry
where{ 
?cls rdfs:subClassOf* prod:NIIN:013754314 .
?a a vcard:Address .
?a vcard:country-name ?cntry .
} order by ?cntry'''

sparql = '''select *
where{ 
	#<http://xsb.com/swiss/product#NIIN013754314> ?o ?s .
	?niin prod:nationalItemId '013754314'.
	?niin ?b ?c.
	#?c rdfs:label ?d
	?c ?d ?e
}'''
sparql = 
'''select *
where{ 
?a prod:itemNameCode '49888'.
#?a ?b ?c
?a rdfs:subClassOf ?b.
?c ?d ?e.
#log:CAGE01234 a log:Cage .
?e log:hasCageCode ?f.
#?f rdfs:label ?g
}'''
sparql = '''select * {<http://xsb.com/swiss/logistics#CAGE87991> ?a ?b}'''
Query query = QueryFactory.make()
query.limit = 1
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
 
 QueryFactory.parse(query,sparql,null,Syntax.syntaxSPARQL)
 QueryExecution x = QueryExecutionFactory.sparqlService(service,query)
 
 ResultSet results = x.execSelect()
 ResultSetFormatter.out(System.out, results)
