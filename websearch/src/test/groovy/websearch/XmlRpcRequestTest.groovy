package websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.Test

import redstone.xmlrpc.XmlRpcSerializer
import websearch.rpc.helper.XmlRpcHelper


class XmlRpcRequestTest extends XMLTestCase {

	void setUp() throws Exception {
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
	}

	
	public void testLists_Condo() {
		String expectedXml = getClass().getClassLoader().getResourceAsStream('requests/lists-condo.xml').getText()
		def actual = XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'condo'])
		assertXMLEqual(expectedXml, actual)
	}
	
	public void testshort_Residential() {
		String expectedXml = getClass().getClassLoader().getResourceAsStream('requests/openhouse-residential.xml').getText()
		def actual = XmlRpcHelper.toXmlRpcRequest('short',['dbid':'dbid1139259934','WhatPropType':['Residential']])
		assertXMLEqual(expectedXml, actual)
	}


}
