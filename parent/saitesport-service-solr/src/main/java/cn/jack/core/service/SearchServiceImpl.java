package cn.jack.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.apache.taglibs.standard.tei.ForEachTEI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jack.common.page.Paginable;
import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Product;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.bean.product.Sku;
import cn.jack.core.bean.product.SkuQuery;



/**
 * 全文检索 solr
 * @author titaniume
 *
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private SolrServer solrServer;
	
	//全文检索
	public Pagination selectPaginationByQuery(Integer pageNo,String keyword) throws Exception{
		//创建包装泪
		ProductQuery productQuery = new ProductQuery();
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo)); 
		//内页显示12
		productQuery.setPageSize(12);
		
		//拼接条件
		StringBuilder params = new StringBuilder();
				
		List<Product> products = new ArrayList<>();
		
		SolrQuery solrQuery = new SolrQuery();
		//关键词
		solrQuery.set("q", "name_ik:" + keyword);
		params.append("keyword=").append(keyword);
		//过滤条件
		//高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		// 样式  <span style='color:red'>2016</span>
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		//排序
		solrQuery.addSort("price", ORDER.asc);
		//排序
		solrQuery.addSort("price",ORDER.asc);
		//分页 limit 开始行 每页显示多少行
		solrQuery.setStart(productQuery.getPageNo());
	    solrQuery.setRows(productQuery.getPageSize());
		QueryResponse response = solrServer.query(solrQuery);
		
		//取高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//Map K : V  442 :Map
		//Map K : V name_ik : List<String>
		//List<String>  2016最新款的缔彩枫2015秋冬新款时尚英伦风大衣简约收腰显瘦灰色中长款毛呢外套 灰色 S
		//结果集
		SolrDocumentList docs = response.getResults();
		//发现条数 (总条数) 构建分页会用到
		long numFound = docs.getNumFound();
		for (SolrDocument doc : docs) {
			//创建品牌对象
			Product product = new Product();
			//商品Id
			String id =(String) doc.get("id");
			product.setId(Long.parseLong(id));
			//商品名称 ik
			
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("name_ik");
			product.setName(list.get(0));
		/*	String name =(String) doc.get("name_ik");
			product.setName(name);*/
			//图片
			String url =(String) doc.get("url");	
			product.setImgUrl(url); 
			//价格 售价 select price FROM sts_sku WHERE product_id =1005 ORDER BY price asc LIMIT 1
			Float price =(Float) doc.get("price");
			product.setPrice(price);
			//品牌Id
			Integer brandId =(Integer) doc.get("brandId");
			product.setBrandId(Long.parseLong(String.valueOf(brandId)));
			products.add(product);
		}
		//构建分页对象
		Pagination pagination =  new Pagination(productQuery.getPageNo(), productQuery.getPageNo(), (int)numFound,products);
		//页面展示
		String url="/search";
		pagination.pageView(url, params.toString());
		return pagination;
	}
}
