package service.web.parser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import groovy.util.logging.Slf4j

@Slf4j
class WebContentParser {

	static final String DATA = "td"
	static final String ROW = "tr";
	static final String ITEM_NAME = "lblItemName"
	static final String TABLE_REFS = "Datagrid17"
//	static final String TABLE_MNGMNT = "Datagrid19"
	

	// Uses WebFlis web site to get cage codes information from other authoritative source.
	public Collection parse(String page){
		Document doc = Jsoup.parse(page)
		def matches = []		
		String productName = doc?.getElementById(ITEM_NAME)?.text()
		matches.add(['PROD':productName])		
		matches.add(processTable(doc,TABLE_REFS))// cage codes
		//matches.add(processTable(doc,TABLE_MNGMNT))//price
		return matches
	}

	protected Map processTable(Document doc, String tableId ) {
		Elements rows = doc.getElementById(tableId)?.select(ROW);
		def cells = []
		def map = [:]
		rows.eachWithIndex{ row, i ->
			Elements rowData = row.select(DATA)
			cells.add(rowData*.text())
		}

		log.info(cells?.toString())

		if(!cells.isEmpty()){
			def keys = cells.get(0)
			for(i in 1..<cells.size()){
				def data = [:]
				keys.eachWithIndex {key, idx ->; data.put(key, cells.get(i).get(idx))}
				map.putAll(data)
			}
		}
		return map
	}
}
