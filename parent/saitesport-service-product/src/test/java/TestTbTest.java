

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.jack.core.bean.TestTb;
import cn.jack.core.dao.TestTbDao;
import cn.jack.core.service.TestTbService;


/**
 * 测试类  junit + Spring 
 * @author lx
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicaiton-context.xml"})
public class TestTbTest {

	@Autowired
	private TestTbService testTbService;
	
	@Test
	public void testAdd() throws Exception {
		TestTb testTb = new TestTb();
		testTb.setName("下雪啦2");
		testTb.setBirthday(new Date());
		
		testTbService.insertTestTb(testTb);
		
		
	}
}
