package service.web.parser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.GsonBuilder;

class WebContentParser {

	public Collection parse(String page){
		Document doc = Jsoup.parse(page)
		Elements rows = doc.select("table#Datagrid17").select("tr");
		def cells = []
		rows.eachWithIndex{ row, i ->
			Elements rowData = row.select("td")
			cells.add(rowData*.text())
		}
		println cells
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
