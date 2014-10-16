import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.GsonBuilder;

def page = new File("./scripts/out.html").text
Document doc = Jsoup.parse(page)
//println doc.title()

def cells = []
Elements rows = doc.select("table#Datagrid17").select("tr");
rows.eachWithIndex{ row, i ->;
	Elements rowData = row.select("td")
	cells.add(rowData*.text())
}
//println cells
def json = []
def keys = cells.get(0)
for(i in 1..<cells.size()){
	def data = [:]
	keys.eachWithIndex {key, idx ->; data.put(key, cells.get(i).get(idx))}
	json.add(data)
}

println new GsonBuilder().setPrettyPrinting().create().toJson('data':json);


