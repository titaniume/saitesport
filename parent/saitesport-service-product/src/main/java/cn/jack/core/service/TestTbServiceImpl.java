package cn.jack.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jack.core.bean.TestTb;
import cn.jack.core.dao.TestTbDao;

@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {
	
	@Autowired
	private TestTbDao testTbDao;
	
	public void insertTestTb(TestTb testTb){
		testTbDao.insertTestTb(testTb);
		//throw new RuntimeException();
	}
	
}
