<?php
    include('xml/kd_xmlrpc_php5.php');        // Include xmlrpc lib
?>


<html>

<head>
<title>Search Form Demo</title>
</head>

<body>
<form method="GET" action="GetSearch.php">
<div align="center">
  <center>
  <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="80%">
    <tr>
      <td width="100%" align="center">Listing Search - (Show XML request and response info 
        <input name="ShowXML" type="radio" value="Yes">) </td>
    </tr>
    <tr>
      <td width="100%" align="center">MLS# <font FACE="Arial" SIZE="2"> <input type="text" name="WhatMLS" size="10"></font></td>
    </tr>
    <tr>
      <td width="100%" align="center">OR</td>
    </tr>
    <tr>
      <td width="100%" align="center">
Property Type:<select SIZE="3" NAME="WhatPropType[]" multiple>
<option VALUE="Residential">Residential</option>
<option VALUE="Vacant Land">Vacant Land</option>
<option VALUE="Condominium">Condominium</option>
<option VALUE="Commercial">Commercial</option>
<option VALUE="Business">Business</option>
<option VALUE="Time Interval">Time Interval</option>
<option VALUE="Multi-Dwelling Res">Multi Dwelling</option>
<option VALUE="Rental-Long Term">Long Term Rental</option>

</select></td>
    </tr>	
    <tr>
      <td align="center">REO: <input type="checkbox" name="WhatREO" value="Y"> || Potential Short Sale: <input type="checkbox" name="WhatPSS" value="Y"></td>
    </tr>
    <tr>
      <td width="100%" align="center">
Ohana:<select SIZE="3" NAME="WhatOhana[]" multiple>
<option VALUE="Single Family">Single Family - No Ohana</option>
<option VALUE="Single Family w/Att Ohana">Single Family - Attached Ohana</option>
<option VALUE="SF w/Det Ohana or Cottage">Single Family - Detached Ohana or Cottage</option>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">
Waterfront:<select SIZE="3" NAME="WhatWater[]" multiple>
<option VALUE="OceanFront">OceanFront</option>
<option VALUE="BeachFront">BeachFront</option>
<option VALUE="Across Street from Ocean">Across Street from Ocean</option>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">
Condo Name:
<select SIZE="5" NAME="WhatCondo[]" multiple>
<?php
GetList('condo');
?>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Price:<br>
      From:
<select SIZE="1" NAME="WhatStartPrice">
<OPTION VALUE="">No Minimum</OPTION>
<OPTION VALUE="50000">50,000</OPTION>
<OPTION VALUE="100000">100,000</OPTION>
<OPTION VALUE="150000">150,000</OPTION>
<OPTION VALUE="200000">200,000</OPTION>
<OPTION VALUE="250000">250,000</OPTION>
<OPTION VALUE="300000">300,000</OPTION>
<OPTION VALUE="350000">350,000</OPTION>
<OPTION VALUE="400000">400,000</OPTION>
<OPTION VALUE="450000">450,000</OPTION>
<OPTION VALUE="500000">500,000</OPTION>
<OPTION VALUE="600000">600,000</OPTION>
<OPTION VALUE="700000">700,000</OPTION>
<OPTION VALUE="800000">800,000</OPTION>
<OPTION VALUE="900000">900,000</OPTION>
<OPTION VALUE="1250000">1,250,000</OPTION>
<OPTION VALUE="1500000">1,500,000</OPTION>
<OPTION VALUE="1750000">1,750,000</OPTION>
<OPTION VALUE="2000000">2,000,000</OPTION>
<OPTION VALUE="2500000">2,500,000</OPTION>
<OPTION VALUE="3000000">3,000,000</OPTION>
<OPTION VALUE="4000000">4,000,000</OPTION>
<OPTION VALUE="5000000">5,000,000</OPTION>
</select> -To:
<select SIZE="1" NAME="WhatEndPrice">
<OPTION VALUE="">No Maximum</OPTION>
<OPTION VALUE="50000">50,000</OPTION>
<OPTION VALUE="100000">100,000</OPTION>
<OPTION VALUE="150000">150,000</OPTION>
<OPTION VALUE="200000">200,000</OPTION>
<OPTION VALUE="250000">250,000</OPTION>
<OPTION VALUE="300000">300,000</OPTION>
<OPTION VALUE="350000">350,000</OPTION>
<OPTION VALUE="400000">400,000</OPTION>
<OPTION VALUE="450000">450,000</OPTION>
<OPTION VALUE="500000">500,000</OPTION>
<OPTION VALUE="600000">600,000</OPTION>
<OPTION VALUE="700000">700,000</OPTION>
<OPTION VALUE="800000">800,000</OPTION>
<OPTION VALUE="900000">900,000</OPTION>
<OPTION VALUE="1250000">1,250,000</OPTION>
<OPTION VALUE="1500000">1,500,000</OPTION>
<OPTION VALUE="1750000">1,750,000</OPTION>
<OPTION VALUE="2000000">2,000,000</OPTION>
<OPTION VALUE="2500000">2,500,000</OPTION>
<OPTION VALUE="3000000">3,000,000</OPTION>
<OPTION VALUE="4000000">4,000,000</OPTION>
<OPTION VALUE="5000000">5,000,000</OPTION>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">View:
<select SIZE="5" NAME="WhatView[]" multiple>
<option VALUE="Ocean">Ocean</option>
<option VALUE="Mountain">Mountain</option>
<option VALUE="Mountain/Ocean">Mountain/Ocean</option>
<option VALUE="Golf Course">Golf Course</option>
<option VALUE="Garden View">Garden View</option>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">District:
<select SIZE="5" NAME="WhatDistrict[]" multiple>
<?php
GetList('district');
?>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Land Tenure :
<select SIZE="3" NAME="WhatLandTenure[]" multiple>
<option VALUE="Fee Simple">Fee Simple</option>
<option VALUE="Leasehold">Leasehold</option>
<option VALUE="Leasehold-FA">Leasehold-FA</option>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Bedroom(s)<br>
      From:
<select SIZE="1" NAME="WhatStartBed">
<OPTION VALUE="">No Minimum</OPTION>
<OPTION VALUE="1">1</OPTION>
<OPTION VALUE="2">2</OPTION>
<OPTION VALUE="3">3</OPTION>
<OPTION VALUE="4">4</OPTION>
<OPTION VALUE="5">5</OPTION>
</select> - To:

<select SIZE="1" NAME="WhatEndBed">
<OPTION VALUE="">No Maximum</OPTION>
<OPTION VALUE="1">1</OPTION>
<OPTION VALUE="2">2</OPTION>
<OPTION VALUE="3">3</OPTION>
<OPTION VALUE="4">4</OPTION>
<OPTION VALUE="5">5</OPTION>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Bath(s)<br>
      From:
<select SIZE="1" NAME="WhatStartBath">
<OPTION VALUE="">No Minimum</OPTION>
<OPTION VALUE="1">1</OPTION>
<OPTION VALUE="2">2</OPTION>
<OPTION VALUE="3">3</OPTION>
<OPTION VALUE="4">4</OPTION>
<OPTION VALUE="5">5</OPTION>
</select> - To:

<select SIZE="1" NAME="WhatEndBath">
<OPTION VALUE="">No Maximum</OPTION>
<OPTION VALUE="1">1</OPTION>
<OPTION VALUE="2">2</OPTION>
<OPTION VALUE="3">3</OPTION>
<OPTION VALUE="4">4</OPTION>
<OPTION VALUE="5">5</OPTION>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Interior Area:<br>
      From:
<select SIZE="1" NAME="WhatStartIntArea">
<OPTION VALUE="">No Minimum</OPTION>
<OPTION VALUE="500">500 Sq. Ft.</OPTION>
<OPTION VALUE="700">700 Sq. Ft.</OPTION>
<OPTION VALUE="1000">1,000 Sq. Ft.</OPTION>
<OPTION VALUE="2000">2,000 Sq. Ft.</OPTION>
<OPTION VALUE="3000">3,000 Sq. Ft.</OPTION>
<OPTION VALUE="4000">4,000 Sq. Ft.</OPTION>
<OPTION VALUE="5000">5,000 Sq. Ft.</OPTION>
<OPTION VALUE="6000">6,000 Sq. Ft.</OPTION>
</select> - To:
<select SIZE="1" NAME="WhatEndIntArea">
<OPTION VALUE="">No Maximum</OPTION>
<OPTION VALUE="500">500 Sq. Ft.</OPTION>
<OPTION VALUE="700">700 Sq. Ft.</OPTION>
<OPTION VALUE="1000">1,000 Sq. Ft.</OPTION>
<OPTION VALUE="2000">2,000 Sq. Ft.</OPTION>
<OPTION VALUE="3000">3,000 Sq. Ft.</OPTION>
<OPTION VALUE="4000">4,000 Sq. Ft.</OPTION>
<OPTION VALUE="5000">5,000 Sq. Ft.</OPTION>
<OPTION VALUE="6000">6,000 Sq. Ft.</OPTION>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">Land Area:<br>
      From:
<select SIZE="1" NAME="WhatStartExtArea">
<OPTION VALUE="">No Minimum</OPTION>
<OPTION VALUE="3000">3,000 Sq. Ft.</OPTION>
<OPTION VALUE="6000">6,000 Sq. Ft.</OPTION>
<OPTION VALUE="10000">10,000 Sq. Ft.</OPTION>
<OPTION VALUE="15000">15,000 Sq. Ft.</OPTION>
<OPTION VALUE="21780">1/2 Acre</OPTION>
<OPTION VALUE="43560">1 Acre</OPTION>
<OPTION VALUE="87120">2 Acres</OPTION>
<OPTION VALUE="217800">5 Acres</OPTION>
<OPTION VALUE="435600">10 Acres</OPTION>
<OPTION VALUE="653400">15 Acres</OPTION>
</select> - To:
<select SIZE="1" NAME="WhatEndExtArea">
<OPTION VALUE="">No Maximum</OPTION>
<OPTION VALUE="3000">3,000 Sq. Ft.t</OPTION>
<OPTION VALUE="6000">6,000 Sq. Ft.</OPTION>
<OPTION VALUE="10000">10,000 Sq. Ft.</OPTION>
<OPTION VALUE="15000">15,000 Sq. Ft.</OPTION>
<OPTION VALUE="21780">1/2 Acre</OPTION>
<OPTION VALUE="43560">1 Acre</OPTION>
<OPTION VALUE="87120">2 Acres</OPTION>
<OPTION VALUE="217800">5 Acres</OPTION>
<OPTION VALUE="435600">10 Acres</OPTION>
<OPTION VALUE="653400">15 Acres</OPTION>
</select></td>
    </tr>
    <tr>
      <td width="100%" align="center">
	From: Month-<select size="1" name="WhatStartMonth">
    <option value="">Any</option>
	<option value="01">January</option>
	<option value="02">February</option>
	<option value="03">March</option>
	<option value="04">April</option>
	<option value="05">May</option>
	<option value="06">June</option>
	<option value="07">July</option>
	<option value="08">August</option>
	<option value="09">September</option>
	<option value="10">October</option>
	<option value="11">November</option>
	<option value="12">December</option>
	</select> Day-<select size="1" name="WhatStartDay">
    <option value="">Any</option>
	<option value="01">1</option>
	<option value="02">2</option>
	<option value="03">3</option>
	<option value="04">4</option>
	<option value="05">5</option>
	<option value="06">6</option>
	<option value="07">7</option>
	<option value="08">8</option>
	<option value="09">9</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
	<option value="24">24</option>
	<option value="25">25</option>
	<option value="26">26</option>
	<option value="27">27</option>
	<option value="28">28</option>
	<option value="29">29</option>
	<option value="30">30</option>
	<option value="31">31</option>
	</select> Year-<select size="1" name="WhatStartYear">
    <option value="">Any</option>
	<option value="2004">2004</option>
	<option value="2005">2005</option>
	<option value="2006">2006</option>
	</select> - To: Month-<select size="1" name="WhatEndMonth">
    <option value="">Any</option>
	<option value="01">January</option>
	<option value="02">February</option>
	<option value="03">March</option>
	<option value="04">April</option>
	<option value="05">May</option>
	<option value="06">June</option>
	<option value="07">July</option>
	<option value="08">August</option>
	<option value="09">September</option>
	<option value="10">October</option>
	<option value="11">November</option>
	<option value="12">December</option>
	</select> Day-<select size="1" name="WhatEndDay">
    <option value="">Any</option>
	<option value="01">1</option>
	<option value="02">2</option>
	<option value="03">3</option>
	<option value="04">4</option>
	<option value="05">5</option>
	<option value="06">6</option>
	<option value="07">7</option>
	<option value="08">8</option>
	<option value="09">9</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
	<option value="24">24</option>
	<option value="25">25</option>
	<option value="26">26</option>
	<option value="27">27</option>
	<option value="28">28</option>
	<option value="29">29</option>
	<option value="30">30</option>
	<option value="31">31</option>
	</select> Year-<select size="1" name="WhatEndYear">
    <option value="">Any</option>
	<option value="2004">2004</option>
	<option value="2005">2005</option>
	<option value="2006">2006</option>
	</select>      </td>
    </tr>
    <tr>
      <td width="100%" align="center">
Div:<input type="text" name="WhatDiv" size="5">&nbsp;&nbsp; Zone:<input type="text" name="WhatZone" size="5">&nbsp;&nbsp;
Sec:<input type="text" name="WhatSec" size="5">&nbsp;&nbsp; Plat:<input type="text" name="WhatPlat" size="5">&nbsp;&nbsp;
Par:<input type="text" name="WhatPar" size="5"></td>
    </tr>
    <tr>
      <td width="100%" align="center">
<select size="1" name="WhatSortType1">
<option value="ListPrice">Price</option>
<option value="Beds">Bedrooms</option>
<option value="Baths">Bathrooms</option>
<option value="District">District</option>
<option value="BuildingName">Condo</option>
<option value="Class">Property Type</option>
<option value="View">View</option>
</select>&nbsp;&nbsp;&nbsp; <input type="radio" name="WhatSortDirection1" value="ASC" checked>Ascending&nbsp;&nbsp;&nbsp;
<input type="radio" name="WhatSortDirection1" value="DESC">Descending<br>
&nbsp;

<select size="1" name="WhatSortType2">
<option value="">None</option>
<option value="ListPrice">Price</option>
<option value="Beds">Bedrooms</option>
<option value="Baths">Bathrooms</option>
<option value="District">District</option>
<option value="BuildingName">Condo</option>
<option value="Class">Property Type</option>
<option value="View">View</option>
</select>&nbsp;&nbsp;&nbsp; <input type="radio" name="WhatSortDirection2" value="ASC" checked>Ascending&nbsp;&nbsp;&nbsp;
<input type="radio" name="WhatSortDirection2" value="DESC">Descending<br>
&nbsp;     </td>
    </tr>
    <tr>
      <td width="100%" align="center">
<input type="hidden" name="WhatNumber" value="10">	  
<input type="submit" value="Submit" name="B1"></td>
    </tr>
  </table>
  </center>
</div>
</form>
</body>

</html>


<?php
function GetList($List) {
    // Uncomment this for handy debugging feature of the XML lib:
    #define('XMLRPC_DEBUG', 1);

    // Location of the RAM service...
    $site        = 'websearch.ramidx.com';        // The url of the XML server
    $location    = '/ramxml.php';    // The script to talk to
    $method        = 'lists';                    // The remote method to invoke

    // We will encode all of our search params into one associative array:
    $paramfields = array(
    // Stuff the programmer can define:
    'dbid'        =>    'dbid1113086003',
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

}


?>