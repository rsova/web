package ez7gen.messages.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
//import java.util.concurrent.atomic.AtomicReference;

public class StructureService {
	final static String LBRCKT = "[";
	final static String RBRCKT = "]";
	final static String LBRS = "{";
	final static String RBRS = "}";

	final static List CONTROLS = Arrays.asList(new String[] { LBRCKT, RBRCKT, LBRS, RBRS });
	final static List OPTIONAL = Arrays.asList(new String[] { LBRCKT, RBRCKT});
	final static List REPEATING = Arrays.asList(new String[] { LBRS, RBRS});

	String definition;
	String[] tokens;
	int cursor;
	Stack<String> controls = new Stack<String>();
	Stack<String> segments = new Stack<String>();


	public StructureService(String def) {
		this.definition = def;
		tokens = definition.split("~");
	}

	public StructureService() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Map<String, Object>> parse() {
		List<Map<String, Object>> segments = new ArrayList<Map<String, Object>>();
		for (cursor = 0; cursor < tokens.length; cursor++) {
			if (!CONTROLS.contains(tokens[cursor])) {
				System.out.println(tokens[cursor]);
				Map<String, Object> m = new HashMap();
				m.put("req", tokens[cursor]);
				segments.add(m);
//				break;
			} else {
				segments.add(handleConditionalSegmetns());
			}
			//segments.add(getNextSegment());
		}
		return segments;
	}

	protected Map getNextSegment() {
		
		HashMap<String, Object> segmentMap = new HashMap<String, Object>();

		for (; cursor < tokens.length; cursor++) {
			
			if (!CONTROLS.contains(tokens[cursor])) {
				System.out.println(tokens[cursor]);
				segmentMap.put("req", tokens[cursor]);
				break;
			} else {
				segmentMap.putAll(handleConditionalSegmetns());
			}
		}
		return segmentMap;
	}

	protected Map<String, Object> handleConditionalSegmetns() {
		Map<String,Object> segmentMap = new HashMap< String,Object>();
		controls.push(tokens[cursor]);
		
		for (; cursor < tokens.length; cursor++) {
			//put a segment on the stack
			if(!CONTROLS.contains(tokens[cursor])){
				segments.push(tokens[cursor]);
			}else{
				//control character check for group boundaries
				if(isGroup(tokens[cursor])){
					Set cntrl = new HashSet();
					//single element group
					if(segments.size() == 1){
						while(controls.size()>0){
						  	String state = controls.pop();
						  	cntrl.add((OPTIONAL.contains(state))?"opt":"rep");
						}
						break;
					}else{
						System.out.println("in else");
						//?
					}
					segmentMap.put(cntrl.toString(), segments.pop());
				}
			}
		}
		return segmentMap;
	}

	protected boolean isGroup1(String current, String stackTop) {
		
		char top = (stackTop != null)?stackTop.charAt(0):'*';
		char cur = current.charAt(0);
		int step = cur - top;
		
		//return (Math.abs(step) == 2)?true:false;
		return (step == 2)?true:false;
	}
	
	protected boolean isGroup(String item) {
		boolean check = false;
		if(controls.empty()){
			controls.push(item);
			//return check;
		}else{
			char first = controls.firstElement().charAt(0);
			char cur = item.charAt(0);
			int step = cur - first;
			if(step == 2){
				controls.push(item);
				check = true;
			}else{
				controls.push(item);
			}
		}
		return check;
	}

}
