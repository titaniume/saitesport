

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.jack.core.bean.TestTb;
import cn.jack.core.bean.product.Product;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.dao.TestTbDao;
import cn.jack.core.dao.product.ProductDao;
import cn.jack.core.service.TestTbService;


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
}
