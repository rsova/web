# Partlink Application


-----------------------------

#### Find suppliers for individual part using NIIN.
#### Estimate delivery to customer destination using USPS web service.
-----------------------------

* Discover Relationsips between NIINs and Suppliers.
* Find suppliers for list of parts presented as an Assemblage, with idividual shipping advice to zip code.
* JSON response to Webservice, with data organized in comprehensive tree structure.  
* Desctop and Mobile client as an example of tracking specific Assemblages. Webkit browsers supported (Chrome, Safari, IE-10)
* Use of the Partlink tripple store. Extended search to other logistic authorities such as WebFLIS to find NIIN to Cage Code relationship.
* Batch parrallel processing of individual NIINs, group takes as long as single request.

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


