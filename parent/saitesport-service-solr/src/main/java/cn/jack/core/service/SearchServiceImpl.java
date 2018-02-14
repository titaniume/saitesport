package cn.jack.core.service;

import java.io.IOException;
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
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Product;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.bean.product.Sku;
import cn.jack.core.bean.product.SkuQuery;
import cn.jack.core.dao.product.ProductDao;
import cn.jack.core.dao.product.SkuDao;




/**
 * 全文检索 solr
 * @author titaniume
 *
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SkuDao skuDao;
	
	//全文检索
	public Pagination selectPaginationByQuery(Integer pageNo,String keyword,Long brandId,String price) throws Exception{
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
		//品牌
		if(null != brandId){
			solrQuery.setFilterQueries("brandId:" + brandId);
		}
		//价格 0-99 1600
		if(null != price){
			String[] p = price.split("-");
			if(p.length == 2){
				solrQuery.setFilterQueries("price:[" + p[0] + " TO " + p[1] + "]" + price);
			}else{
				solrQuery.setFilterQueries("price:[" + p[0] + " TO *]");
			}
		
		}
		
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
			product.setPrice((Float) doc.get("price"));
			//品牌Id
			product.setBrandId(Long.parseLong(String.valueOf((Integer) doc.get("brandId"))));
			products.add(product);
		}
		//构建分页对象
		Pagination pagination =  new Pagination(productQuery.getPageNo(), productQuery.getPageNo(), (int)numFound,products);
		//页面展示
		String url="/search";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	/**
	 * 保存商品到solr
	 */
	public void insertProductToSolr(Long id) {
		// TODO 保存商品信息到SOlr服务器
		SolrInputDocument doc = new SolrInputDocument();
		// 商品Id
		doc.setField("id", id);
		// 商品名称 ik
		Product p = productDao.selectByPrimaryKey(id);
		doc.setField("name_ik", p.getName());
		// 图片
		doc.setField("url", p.getImages()[0]);
		// 价格 售价 select price FROM sts_sku WHERE product_id =1005 ORDER BY price
		// asc LIMIT 1
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id);
		skuQuery.setOrderByClause("price asc");
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		skuQuery.setFields("price");
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		doc.setField("price", skus.get(0).getPrice());
		// 品牌Id
		doc.setField("brandId", p.getBrandId());
		// 时间 可选
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
