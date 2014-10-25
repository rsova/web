package service.partlink.sparql

class Sparql {
	
def public static final String NIIC_TO_SUPPS = '''
select distinct ?prodName ?name ?id  ?price ?assignmentDate ?name ?cageCode ?address  (group_concat(distinct ?ph; separator = ",") as ?phone) ?cao ?adp ?isWomanOwned  
where{
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
	?cage log:hasCageCode ?cageCode;   
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
	?addr vcard:postal-code ?postalCode.
	
	#?telephone rdf:type vcard:Voice.
	?telephone vcard:hasValue ?ph.
	
	bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	
} group by ?prodName ?name ?id ?price ?assignmentDate ?name ?cageCode ?address ?cao ?adp ?isWomanOwned 
'''

public static final String NIIC_TO_SUPPS_org= '''
select distinct ?niin ?price ?assignmentDate ?name ?cageCode ?address (group_concat(?ph) as ?phone) ?cao ?adp ?isWomanOwned  
where{
	#?logNiin log:hasProductNIIN prod:NIIN010077987 .   
	?logNiin log:hasProductNIIN $prodNiin . 
	?logNiin rdfs:label ?niin .
	?logNiin log:hasReferenceNumber ?refNum . 
	?logNiin log:hasUnitPrice ?price.
	?logNiin log:assignmentDate ?assignmentDate.
	?logNiin log:hasReferenceNumber ?refNum .
	?refNum log:hasCage ?cage .  
	?cage log:hasCageCode ?cageCode;   
	#?cageCode log:hasCageCode ?cageId;
	log:hasCageName ?name;
	#log:hasCageStatus ?cageStatus;
	#log:hasBusinessSizeCode ?businessSizeCode;
	vcard:hasAddress ?addr;
	vcard:hasTelephone ?telephone;
	log:hasCAO ?cao;
	log:hasADP ?adp;                                 
	log:isWomanOwned ?isWomanOwned.                                 
	
	?addr vcard:street-address ?streetAddress.
	?addr vcard:locality ?locality.
	?addr vcard:region ?region.
	?addr vcard:country-name ?countryName.
	?addr vcard:postal-code ?postalCode.
	
	#?telephone rdf:type vcard:Voice.
	?telephone vcard:hasValue ?ph.
	
	bind (concat(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address).	
} group by ?niin ?price ?assignmentDate ?name ?cageCode ?address ?cao ?adp ?isWomanOwned 
'''
//National Item Identification Number
def bkp = 
'''
SELECT DISTINCT 
?name 
(CONCAT(?streetAddress,', ', ?locality,', ',?region,', ', ?countryName,', ', ?postalCode) as ?address)
?pnone
?cao
?adp 
?isWomanOwned 
WHERE{
?cageCode log:hasCageCode ?cageId;
log:hasCageName ?name;
#log:hasCageStatus ?cageStatus;
#log:hasBusinessSizeCode ?businessSizeCode;
vcard:hasAddress ?addr;
vcard:hasTelephone ?telephone;
log:hasCAO ?cao;
log:hasADP ?adp;                                 
log:isWomanOwned ?isWomanOwned.                                 
?addr vcard:street-address ?streetAddress.
?addr vcard:locality ?locality.
?addr vcard:region ?region.
?addr vcard:country-name ?countryName.
?addr vcard:postal-code ?postalCode.
?telephone vcard:hasValue ?pnone.
}
'''
}
