<?php
    // Uncomment this for handy debugging feature of the XML lib:
    #define('XMLRPC_DEBUG', 1);

    // Location of the RAM service...
    $site        = 'websearch.ramidx.com/smartframe';        // The url of the XML server
    $location    = '/ramxml.php';    // The script to talk to
    $method        = 'lists';                    // The remote method to invoke
    $List = 'condo';
    //'<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1113086003</string></value></member><member><name>getlist</name><value><string>district</string></value></member></struct></value></param></params></methodCall>'
    // We will encode all of our search params into one associative array:
    $paramfields = array(
    // Stuff the programmer can define:
    'dbid'        =>    'dbid1139259934',
    'getlist'		  =>    $List);

    // We pass in a single array containing an associative array:
    $params = array( XMLRPC_prepare( $paramfields ) );

    // This actually connects and feeds in the request and returns the data:
    list($success, $response) = XMLRPC_request( $site, $location, $method, $params );

    #XMLRPC_debug_print();            // uncomment for debugging



 if ($success) {
  if ($List == 'condo' || $List == 'district') {
   $counter = 0;
    while ($response[2][$counter]) {
     echo '<option value="'.urldecode($response[2][$counter]).'">'.urldecode($response[2][$counter]).'</option>';
     $counter ++;
    }
  } else {
    $counter = 0;
     while ($response[2][$counter]) {
      echo '<option value="'.$response[2][$counter][0].'">'.urldecode($response[2][$counter][1]).'</option>';
      $counter ++;
     }
  }
 } else {
  echo 'Failure.<br>';
  echo $response[faultString];
 }
?>