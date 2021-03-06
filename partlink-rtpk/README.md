# Partlink Application


-----------------------------

#### Find suppliers for individual part using NIIN.
#### Estimate delivery to customer destination using USPS web service.
-----------------------------

* Discover Relationships between NIINs and Suppliers.
* Find suppliers for list of parts presented as an Assemblage, with individual shipping advice to zip code.
* Response to Web Service client, with data organized in comprehensive tree structure.
* Content Type aware responds with JSON or XML.   
* Desktop and Mobile client application for tracking specific Assemblages. Webkit browsers supported (Chrome, Safari, IE-10)
* Use of the PartLink triple store. 
* Extended search to other logistic authorities such as WebFLIS to find NIIN to Cage Code relationship.
* Batch parallel processing of individual NIINs, group takes as long as single request.

Project layout
----------------------------
    <proj>
      |
      +- src
          |
          +- ratpack
          |     |
          |     +- ratpack.groovy
          |     +- ratpack.properties
          |     +- public          // Static assets in here
          |          |
          |          +- app  // sencha touch application files
          |
          +- main
          |   |
          |   +- groovy
                   |
                   +- // App classes in here!
          |
          +- test
              |
              +- groovy
                   |
                   +- // tests in here!

If you have groovy installed allready, start the app with from the root directory.

    ./gradlew run
    
install groovy: http://groovy.codehaus.org/Installing+Groovy    


