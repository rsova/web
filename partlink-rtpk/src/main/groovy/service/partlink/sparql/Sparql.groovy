package service.partlink.sparql

class Sparql {
	
def public static final String NIIC_TO_CAGE_REF = '''
SELECT DISTINCT ?prodName ?NIIN ?price ?assignmentDate ?refNum
WHERE{
	?niinIri prod:nationalItemId ?id.
	?niinIri rdfs:subClassOf ?prod.
	?prod rdfs:label ?prodName.
	?logNiin log:hasProductNIIN ?niinIri. 
	OPTIONAL {?logNiin rdfs:label ?NIIN}.
    OPTIONAL {?logNiin log:hasUnitPrice ?varPrice}.
    BIND ( COALESCE(?varPrice, "N/A") As ?price)

    OPTIONAL {?logNiin log:assignmentDate ?assignmentDate}.
    OPTIONAL {?logNiin log:hasReferenceNumber ?refNum}  
}'''	

def public static final String GAGE_DETAILS_BY_REF = '''
SELECT DISTINCT ?name ?Address ?Country ?Zip ?CageCode  
WHERE {
	?iri log:hasCage ?cage.
	?cage log:hasCageCode ?CageCode;   
	log:hasCageName ?name;
	vcard:hasAddress ?addr.

	?addr vcard:street-address ?streetAddress.
	?addr vcard:locality ?locality.
	optional {?addr vcard:region ?reg}.
	?addr vcard:country-name ?Country.
	?addr vcard:postal-code ?Zip.

	BIND ( COALESCE(?reg, "") As ?region)
	BIND (concat(?streetAddress,', ', ?locality,', ',?region) as ?Address).
} LIMIT 1 '''

def public static final String GAGE_DETAILS_BY_CODE = '''
	SELECT DISTINCT ?Address ?Country ?Zip ?CageCode 
	WHERE{
	?cage log:hasCageCode ?CageCode.
	FILTER(?CageCode IN ($CCTOKENS)).
	?cage log:hasCageName ?name.
			
	?cage vcard:hasAddress ?addr.

	?addr vcard:street-address ?streetAddress.
	?addr vcard:locality ?locality.
	optional {?addr vcard:region ?reg}.
	?addr vcard:country-name ?Country.
	?addr vcard:postal-code ?Zip.

	BIND ( COALESCE(?reg, "") As ?region)
	BIND (concat(?streetAddress,', ', ?locality,', ',?region) as ?Address).
	} LIMIT 10'''

//Original query that takes to long to run
def public static final String NIIC_TO_SUPPS = '''
SELECT DISTINCT ?prodName ?name ?id  ?price ?assignmentDate ?name ?CageCode ?address  (group_concat(distinct ?ph; separator = ",") as ?phone) ?cao ?adp ?isWomanOwned  
WHERE{
	#?NIIN prod:nationalItemId "015303893".
	?NIIN prod:nationalItemId ?niin.
	?NIIN rdfs:subClassOf ?prod.
	?prod rdfs:label ?prodName.
	?logNiin log:hasProductNIIN ?NIIN . 
	?logNiin rdfs:label ?id .

    ?logNiin log:hasReferenceNumber ?refNum . 
	?logNiin log:hasUnitPrice ?price.
	?logNiin log:assignmentDate ?assignmentDate.
	?refNum log:hasCage ?cage .  
	?cage log:hasCageCode ?CageCode;   
	log:hasCageName ?name;
	vcard:hasAddress ?addr;
	vcard:hasTelephone ?telephone;
	log:hasCAO ?cao;
	log:hasADP ?adp;                                 
	log:isWomanOwned ?isWomanOwned.                                 
	
	?addr vcard:street-address ?streetAddress.
	?addr vcard:locality ?locality.
	?addr vcard:region ?region.
	?addr vcard:country-name ?countryName.
	?addr vcard:postal-code ?Zip.
	
	#?telephone rdf:type vcard:Voice.
	?telephone vcard:hasValue ?ph.
	
	BIND (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?Zip) as ?address).	
} GROUP BY ?prodName ?name ?id ?price ?assignmentDate ?name ?CageCode ?address ?cao ?adp ?isWomanOwned 
'''
}
