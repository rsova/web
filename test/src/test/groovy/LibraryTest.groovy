/*
 * This Spock specification was auto generated by running 'gradle init --type groovy-library'
 * by 'romansova' at '11/1/14 12:24 PM' with Gradle 2.1
 *
 * @author romansova, @date 11/1/14 12:24 PM
 */

import spock.lang.Specification

class LibraryTest extends Specification{
    def "someLibraryMethod returns true"() {
        setup:
        Library lib = new Library()
        when:
        def result = lib.someLibraryMethod()
        then:
        result == true
    }
}