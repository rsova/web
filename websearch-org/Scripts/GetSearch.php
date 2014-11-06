<?php
    // We used the very simple-to-use Keith Devins XML lib found at:
    // http://www.keithdevens.com/software/xmlrpc/
    include('xml/kd_xmlrpc_php5.php');        // Include the handy xmlrpc lib

    // Uncomment this for handy debugging feature of the XML lib:
	if ($ShowXML) {
    define('XMLRPC_DEBUG', 1);
    }
	
	
    // We treat the from and to list date special:
    if ($_REQUEST[WhatStartYear] && $_REQUEST[WhatStartMonth] && $_REQUEST[WhatStartDay]) {
      $WhatStartDate = $_REQUEST[WhatStartYear].'-'.$_REQUEST[WhatStartMonth].'-'.$_REQUEST[WhatStartDay];
    } else {
      $WhatStartDate = '';
    }
    if ($_REQUEST[WhatEndYear] && $_REQUEST[WhatEndMonth] && $_REQUEST[WhatEndDay]) {
      $WhatEndDate = $_REQUEST[WhatEndYear].'-'.$_REQUEST[WhatEndMonth].'-'.$_REQUEST[WhatEndDay];
    } else {
      $WhatEndDate = '';
    }

    // Location of the RAM service...
    $site        = 'websearch.ramidx.com';        // The url of the XML server
    $location    = '/ramxml.php';    // The script to talk to
    $method        = 'search';                    // The remote method to invoke
	
	// Split TMK into array
	if ($_REQUEST[WhatDiv]) {
	 $Div = explode(',',$_REQUEST[WhatDiv]);
	}
	if ($_REQUEST[WhatZone]) {
	 $Zone = explode(',',$_REQUEST[WhatZone]);
	}	
	if ($_REQUEST[WhatSec]) {
	 $Sec = explode(',',$_REQUEST[WhatSec]);
	}
	if ($_REQUEST[WhatPlat]) {
	 $Plat = explode(',',$_REQUEST[WhatPlat]);
	}
	if ($_REQUEST[WhatPar]) {
	 $Par = explode(',',$_REQUEST[WhatPar]);
	}

    // Split MLS into array
	if ($_REQUEST[WhatMLS]) {
	 $MLS = explode(',',$_REQUEST[WhatMLS]);
	}
						
    // We will encode all of our search params into one associative array:
    $paramfields = array(
    // Stuff the programmer can define:
    'dbid'						=>    'dbid1113086003',

    //Variables from your form:
    'WhatPage'					=>	$_REQUEST[WhatPage],
	'WhatNumber'				=>	$_REQUEST[WhatNumber],
    'WhatMLS'					=>	$MLS,
    'WhatPropType'				=>	$_REQUEST[WhatPropType],
    'WhatWater'					=>	$_REQUEST[WhatWater],
    'WhatOhana'					=>	$_REQUEST[WhatOhana],
    'WhatCondo'					=>	$_REQUEST[WhatCondo],
    'WhatStartPrice'			=>	$_REQUEST[WhatStartPrice],
    'WhatEndPrice'				=>	$_REQUEST[WhatEndPrice],
    'WhatView'					=>	$_REQUEST[WhatView],
    'WhatDistrict'				=>	$_REQUEST[WhatDistrict],
    'WhatLandTenure'			=>	$_REQUEST[WhatLandTenure],
    'WhatAgent'					=>	$_REQUEST[WhatAgent],
    'WhatOffice'				=>	$_REQUEST[WhatOffice],
    'WhatStartBed'				=>	$_REQUEST[WhatStartBed],
    'WhatEndBed'				=>	$_REQUEST[WhatEndBed],
    'WhatStartBath'				=>	$_REQUEST[WhatStartBath],
    'WhatEndBath'				=>	$_REQUEST[WhatEndBath],
    'WhatStartIntArea'			=>	$_REQUEST[WhatStartIntArea],
    'WhatEndIntArea'			=>	$_REQUEST[WhatEndIntArea],
    'WhatStartExtArea'			=>	$_REQUEST[WhatStartExtArea],
    'WhatEndExtArea'			=>	$_REQUEST[WhatEndExtArea],
    'WhatDiv'					=>	$Div,
	'WhatZone'					=> 	$Zone,
	'WhatSec'					=>	$Sec,
	'WhatPlat'					=>	$Plat,
	'WhatPar'					=>	$Par,		
    'WhatStartDate'				=>  $WhatStartDate,
    'WhatEndDate'				=>  $WhatEndDate,
    'WhatSortType1'				=>	$_REQUEST[WhatSortType1],
    'WhatSortDirection1'		=>	$_REQUEST[WhatSortDirection1],
    'WhatSortType2'				=>	$_REQUEST[WhatSortType2],
    'WhatSortDirection2'		=>	$_REQUEST[WhatSortDirection2]);	

    // We pass in a single array containing an associative array:
    $params = array( XMLRPC_prepare( $paramfields ) );

    // This actually connects and feeds in the request and returns the data:
    list($success, $response) = XMLRPC_request( $site, $location, $method, $params );
	
	if ($ShowXML) {
    XMLRPC_debug_print();            // uncomment for debugging
	}
?>

<html>

<head>
<title>Get Search</title>
</head>

<body>
<?PHP
 if ($success) {
  // Extract the 5 variables returned from the XML structure:
  $TotalCount = $response[0];
  $CurrentPage = $response[1];
  $TotalPages = $response[2];
  $SearchTime = $response[3];
  $SearchResults = $response[4];

echo 'Search Time: '.$SearchTime.'<br>';

if ($Show == 'Full') {
echo '<table width="100%">';
echo '<tr><td align="center">';
if ($SearchResults[0][Images][P400][0]) {
echo '<img src="'.$SearchResults[0][Images][P400][0].'">';
}
echo '</td><td>';
echo '<b>MLS: </b>'.$SearchResults[0][MLS].'<br>';
echo '<b>Listing Date: </b>'.$SearchResults[0][ListDate].'<br>';
echo '<b>Status: </b>'.$SearchResults[0][Status].'<br>';
echo '<b>Type: </b>'.$SearchResults[0][Type].'<br>';
echo '<b>Price: </b>$'.addcomma($SearchResults[0][ListPrice]).' '.$SearchResults[0][LT].'<br>';
echo '<b>District: </b>'.$SearchResults[0][District].'<br>';
echo '<b>Address: </b>'.$SearchResults[0][AddressNumber].' '.$SearchResults[0][AddressDirection].' '.$SearchResults[0][AddressStreet].'<br>';
echo '<b>Bed: </b>'.$SearchResults[0][Beds].'<br>';
echo '<b>Bath: </b>'.$SearchResults[0][Baths].'<br>';
echo '<b>Living Area: </b>'.addcomma($SearchResults[0][LivSQFT]).' SF<br>';
echo '<b>Land Area: </b>'.addcomma($SearchResults[0][LandSQFT]).' SF<br>';
echo '<b>View: </b>'.$SearchResults[0][View].'<br>';
echo '<b>WaterFront: </b>'.$SearchResults[0][WaterFront].'<br>';
echo '<b>Partial Ownership: </b>'.$SearchResults[0][PartialOwnership].'<br>';
echo '<b>Percentage Ownership: </b>'.$SearchResults[0][PercentOwnership].'<br>';
echo '<b>Fixed Begin Date: </b>'.$SearchResults[0][FixedBeginDate].'<br>';
echo '<b>Fixed End Date: </b>'.$SearchResults[0][FixedEndDate].'<br>';
echo '</td></tr>';
echo '<tr><td colspan="2">';
echo urldecode($SearchResults[0][PublicRemarks]).'<br><br>';
echo '<b>Virtual Tour: </b>'.$SearchResults[0][VirtualTour].'<br>';
echo '<b>Agent: </b>'.$SearchResults[0][AgentName].'<br>';
echo '<b>Agent Phone: </b>'.$SearchResults[0][AgentPhone].'<br>';
echo '<b>Agent Fax: </b>'.$SearchResults[0][AgentFax].'<br>';
echo '<b>Agent Email: </b>'.$SearchResults[0][AgentEmail].'<br>';
echo '<b>Agent Web Page: </b>'.$SearchResults[0][AgentWebPage].'<br>';
echo '<b>Office: </b>'.$SearchResults[0][ListingOffice1_Name].'<br>';
echo '<b>Office Phone: </b>'.$SearchResults[0][ListingOffice1_Phone].'<br>';
echo '<b>Office Fax: </b>'.$SearchResults[0][ListingOffice1_Fax].'<br>';
echo '<b>Office Email: </b>'.$SearchResults[0][ListingOffice1_Email].'<br>';
echo '<b>Office Web Page: </b>'.$SearchResults[0][ListingOffice1_WebPage].'<br>';
echo '<b>Co Agent: </b>'.$SearchResults[0][CoListingAgent_Name].'<br>';
echo '<b>Co Agent Phone: </b>'.$SearchResults[0][CoListingAgent_Phone].'<br>';
echo '<b>Co Agent Fax: </b>'.$SearchResults[0][CoListingAgent_Fax].'<br>';
echo '<b>Co Agent Email: </b>'.$SearchResults[0][CoListingAgent_Email].'<br>';
echo '<b>Co Agent Web Page: </b>'.$SearchResults[0][CoListingAgent_WebPage].'<br>';
echo '<b>Co Office: </b>'.$SearchResults[0][ListingOffice2_Name].'<br>';
echo '<b>Co Office Phone: </b>'.$SearchResults[0][ListingOffice2_Phone].'<br>';
echo '<b>Co Office Fax: </b>'.$SearchResults[0][ListingOffice2_Fax].'<br>';
echo '<b>Co Office Email: </b>'.$SearchResults[0][ListingOffice2_Email].'<br>';
echo '<b>Co Office Web Page: </b>'.$SearchResults[0][ListingOffice2_WebPage].'<br>';


echo '</td></tr></table>';

} else {
 echo '<div align="center">';
 echo 'Listing Search Results - '.$TotalCount.' matches found.<br>';
  if ($TotalCount > 0) {
   echo 'Showing listings '.(($CurrentPage-1)*10 + 1).' - '.(((($CurrentPage-1)*10 + 10) <= $TotalCount) ? (($CurrentPage-1)*10 + 10) : $TotalCount).'<br>';
    if ($TotalCount > 10) {
     PrintLinks($CurrentPage,$TotalPages);
    }
   }
 echo '</div>';
$Counter = 0;
while ($Counter < 10 && $SearchResults[$Counter][MLS]) {
echo '<hr>';
echo 'MLS: <a href="GetSearch.php?Show=Full&WhatMLS='.$SearchResults[$Counter][MLS].'">'.$SearchResults[$Counter][MLS].'</a><br>';
 if ($SearchResults[$Counter][Images][P100][0]) {
 echo '<a href="GetSearch.php?Show=Full&WhatMLS='.$SearchResults[$Counter][MLS].'"><img align="left" hspace="15" src="'.$SearchResults[$Counter][Images][P100][0].'"></a>';
 }
 echo '<b>Price: </b>$'.addcomma($SearchResults[$Counter][ListPrice]).' '.$SearchResults[$Counter][LT].'<br>';
 echo '<b>Class: </b>'.$SearchResults[$Counter]['Class'].'<br>';
 echo '<b>Type: </b>'.$SearchResults[$Counter][Type].'<br>'; 
 echo '<b>District: </b>'.$SearchResults[$Counter][District].'<br>';
 echo '<b>Building: </b>'.$SearchResults[$Counter][BuildingName].'<br>';
 echo '<b>Unit: </b>'.$SearchResults[$Counter][Unit].'<br>';
 echo urldecode($SearchResults[$Counter][PublicRemarks]);
 echo '<br><br>';
 echo '<b>Agent Name:</b> '.$SearchResults[$Counter][AgentName].'<br>';
 echo '<b>Office Name:</b> '.$SearchResults[$Counter][ListingOffice1_Name].'<br>';


$Counter += 1;
}
}




} else {
 echo 'Failure.<br>';
 echo $response[faultString];
}


?>


</body>

</html>


<?php


// A function to print out the pretty page links:
function PrintLinks($CurrentPage,$TotalPages) {
  $url = ereg_replace('&WhatPage=[0-9]+', '', $_SERVER['REQUEST_URI']);
  $PrettyLinks = array();
  for ($i = 1; $i <= $TotalPages; $i++) {
   $PrettyLinks[] = ($i == $CurrentPage ? $i : '<a href="' . $url . '&WhatPage=' . $i . '">' . $i . '</a>');
  }
  echo implode(' &nbsp;� &nbsp;', $PrettyLinks);
}



function addcomma($str)
{
    if(strlen($str)<=3)
    {
        return $str;
    }
    $strl=substr($str,0,strlen($str)-3);
    $return=addcomma($strl).",".substr($str,-3);
    return $return;
}

?>