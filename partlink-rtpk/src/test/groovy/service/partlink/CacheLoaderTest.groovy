package service.partlink;
import static org.junit.Assert.*;

import com.google.gson.Gson
import groovy.io.FileType
import net.sf.ehcache.Element
import org.junit.Test;
import net.sf.ehcache.Cache
import net.sf.ehcache.Element
import net.sf.ehcache.management.Cache
import net.sf.ehcache.CacheManager


public class CacheLoaderTest {

	@Test
	public void test() {
		def list = []
		def dir = new File("src/ratpack/cache")
		dir.eachFileRecurse (FileType.FILES) { file -> list << file }
		list.each {println it.path}

		def json = new File("src/ratpack/cache/4.json").text
		def prods = []
		prods= new Gson().fromJson(json, prods.getClass());
		CacheManager.getInstance().addCache('test')
		for(prod in prods){
			def elm = new Element(prod.items.niin, ((com.google.gson.internal.LinkedTreeMap) prod) as Map)
			elm.timeToLive = 60*60*48 //48 hours
			CacheManager.getInstance()?.getCache('test').put(elm)
		}
		
		['016033650','015127231','010077987','015303893'].each{ niin ->
			def products = CacheManager.getInstance()?.getCache("test")?.get(niin)?.getObjectValue()
			println products
		}
		
	}
}
