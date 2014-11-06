package websearch.xml.helper

import org.custommonkey.xmlunit.Difference
import org.custommonkey.xmlunit.DifferenceConstants
import org.custommonkey.xmlunit.DifferenceListener
import org.w3c.dom.Node;

public class IgnoreNamedElementsDifferenceListener implements DifferenceListener {
    private Set<String> blackList = new HashSet<String>();

	public IgnoreNamedElementsDifferenceListener(String ... elementNames) {
		for (String name : elementNames) {
			blackList.add(name);
		}
	}

	@Override
	public int differenceFound(Difference difference) {
		//println difference
        if (difference.getId() == DifferenceConstants.TEXT_VALUE_ID) {
			//println "~~~~~~: " + difference.controlNodeDetail.xpathLocation
            //if (blackList.contains(difference.getControlNodeDetail().getNode().getParentNode().getNodeName())) {
        	if (blackList.contains(difference.controlNodeDetail.xpathLocation)) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
	}

	@Override
	public void skippedComparison(Node control, Node test) {
		
	}
 
	
}