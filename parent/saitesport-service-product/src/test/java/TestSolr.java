

import org.apache.solr.client.solrj.SolrServer;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicaiton-context.xml"})
public class TestSolr {
	
	@Autowired
	private SolrServer solrServer;

	
	@Test
	public void testSolrJ() throws Exception{
		String baseURL="http://192.168.200.128:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 3);
		doc.setField("name", "张三");
		solrServer.add(doc);
		solrServer.commit();
	}
	
	@Test
	public void testSolrJSpring() throws Exception{
	
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 4);
		doc.setField("name", "李四");
		solrServer.add(doc);
		solrServer.commit();
	}
	
	
	
}
