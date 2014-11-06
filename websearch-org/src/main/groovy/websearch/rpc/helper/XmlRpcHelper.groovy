package websearch.rpc.helper

import redstone.xmlrpc.XmlRpcSerializer

class XmlRpcHelper {
	
	static 	public String toXmlRpcRequest(String methodName, params) {
		Writer writer = new StringWriter();
		XmlRpcSerializer x = new XmlRpcSerializer();
		x.serialize(params, writer)
		return "<methodCall><methodName>$methodName</methodName><params><param>" + writer.toString() + "</param></params></methodCall>"
	}

}
