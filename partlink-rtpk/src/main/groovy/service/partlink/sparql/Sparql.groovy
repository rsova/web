package service.partlink.sparql

class Sparql {
public static final String NIIC_TO_SUPPS = '''
select distinct ?name ?cageCode ?address (group_concat(?ph) as ?phone) ?cao ?adp ?isWomanOwned  
where{
	#?logNiin log:hasProductNIIN prod:NIIN010077987 .   
	?logNiin log:hasProductNIIN $prodNiin .   
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
} group by ?name ?cageCode ?address ?cao ?adp ?isWomanOwned 
'''

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
