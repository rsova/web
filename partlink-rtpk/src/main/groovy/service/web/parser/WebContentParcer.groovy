package service.web.parser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import groovy.util.logging.Slf4j

@Slf4j
class WebContentParser {

	static final String DATA = "td"
	//private final static Logger LOGGER = LoggerFactory.getLogger(WebContentParser.class)
	static final String TABLE_CAGES = "table#Datagrid17"
	static final String ROW = "tr";
	

	// Uses WebFlis web site to get cage codes information from other authoritative source.
	public Collection parse(String page){
		Document doc = Jsoup.parse(page)
		Elements rows = doc.select(TABLE_CAGES).select(ROW);
		def cells = []
		rows.eachWithIndex{ row, i ->
			Elements rowData = row.select(DATA)
			cells.add(rowData*.text())
		}
		
		log.debug(cells)
		
		def mathes = []
		if(!cells.isEmpty()){
			def keys = cells.get(0)
			for(i in 1..<cells.size()){
				def data = [:]
				keys.eachWithIndex {key, idx ->; data.put(key, cells.get(i).get(idx))}
				mathes.add(data)
			}
		}
		return mathes
	}
}
