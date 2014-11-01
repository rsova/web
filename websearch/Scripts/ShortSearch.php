<?php
    // We used the very simple-to-use Keith Devins XML lib found at:
    // http://www.keithdevens.com/software/xmlrpc/
    include('kd_xmlrpc.php');        // Include the handy xmlrpc lib

    // Uncomment this for handy debugging feature of the XML lib:
	if ($ShowXML) {
    define('XMLRPC_DEBUG', 1);
	}

    // Location of the RAM service...
    $site        = 'websearch.ramidx.com';        // The url of the XML server
    $location    = '/ramxml.php';    // The script to talk to
    $method        = 'short';                    // The remote method to invoke

    // We will encode all of our search params into one associative array:
    $paramfields = array(
    // Stuff the programmer can define:
    'dbid'						=>    'dbid1113086003',

    //Variables from your form:
    'WhatPropType'				=>	$_REQUEST[WhatPropType]);	

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


/*  echo '<div align="center">';
 echo 'Listing Search Results - '.$TotalCount.' matches found.<br>';
  if ($TotalCount > 0) {
   echo 'Showing listings '.(($CurrentPage-1)*10 + 1).' - '.(((($CurrentPage-1)*10 + 10) <= $TotalCount) ? (($CurrentPage-1)*10 + 10) : $TotalCount).'<br>';

   }
 echo '</div>'; */


$Counter = 0;
echo '<table border="1">';
while ($SearchResults[$Counter]) {
  echo '<tr>';
 if ($SearchResults[$Counter][Image][P100][0]) {
  echo '<td><img src="'.$SearchResults[$Counter][Image][P100][0].'"></td>';
 } else {
  echo '<td>&nbsp;</td>';
 }

 echo '<td>'.$Counter.' - '.$SearchResults[$Counter][MLS].'</td>';
 echo '<td>'.$SearchResults[$Counter]["Class"].'</td>';
 echo '<td>'.$SearchResults[$Counter][ListPrice].'</td>';
 echo '<td>'.$SearchResults[$Counter][ListDate].'</td>';
 echo '<td>'.$SearchResults[$Counter][District].'</td>';
 echo '<td>'.$SearchResults[$Counter][Beds].'</td>';
 echo '<td>'.$SearchResults[$Counter][Baths].'</td>'; 
 echo '<td>'.$SearchResults[$Counter][BuildingName].'</td>'; 
 echo '</tr>';
 $Counter += 1;
}
echo '</table>';
 
 



} else {
 echo 'Failure.<br>';
 echo $response[faultString];
}


?>


</body>

</html>


<?php




 
?>