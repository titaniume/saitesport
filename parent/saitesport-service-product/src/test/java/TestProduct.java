

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.TestTb;
import cn.jack.core.bean.product.Product;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.bean.product.Sku;
import cn.jack.core.dao.TestTbDao;
import cn.jack.core.dao.product.ProductDao;
import cn.jack.core.dao.product.SkuDao;
import cn.jack.core.service.TestTbService;
import cn.jack.core.service.product.ProductService;


/**
 * 测试类  junit + Spring 
 * @author lx
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicaiton-context.xml"})
public class TestProduct {

	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testAdd() throws Exception {
		
		//通过Id查询商品
		Product product = productDao.selectByPrimaryKey(1l);
		//System.out.println(product);
		
		//条件
		ProductQuery productQuery = new ProductQuery();
		//productQuery.createCriteria().andBrandIdEqualTo(4l).andNameLike("%纤韵%");
		//分页
		productQuery.setPageNo(2);
		productQuery.setPageSize(10);
		
		//排序
		productQuery.setOrderByClause("id asc");
		
		//指定字段查询
		productQuery.setFields("id,brand_id");
		List<Product> products = productDao.selectByExample(productQuery);
		for (Product product2 : products) {
			System.err.println(product2);
		}
		int count = productDao.countByExample(productQuery);
		System.out.println(count);
		
	}
	
	@Autowired
	private SkuDao skuDao;
	
	@Test
	public void test1(){
		//保存SKU
		Sku sku = new Sku();
		//商品ＩＤ
		sku.setProductId(1l);
		//颜色
		sku.setColorId(1l);
		//尺码
		sku.setSize("L");
		//市场价
		sku.setMarketPrice(999f);
		//售价
		sku.setPrice(666f);
		//运费
		sku.setDeliveFee(8f);
		//库存
		sku.setStock(0);
		//限制
		sku.setUpperLimit(200);
		//时间
		sku.setCreateTime(new Date());
		
		int i = skuDao.insertSelective(sku);
		System.out.println(i+"aa");
	}
	
	@Test
	public void aaa(){
		Pagination pagination = productService.selectPaginationByQuery(1, null, 5l,true);
		List<Product> list =(List<Product>) pagination.getList();
		for (Product product : list) {
			System.out.println(product.getImgUrl().split(",")[0]);
		}
		}
	}

