package cn.jack.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jack.core.bean.TestTb;
import cn.jack.core.service.TestTbService;

@Controller
public class CenterController {
	
	
	@Autowired
	private TestTbService testTbServices;
	/**
	 * 
	 * @param model
	 * @return 
	 * ModelAndView :跳转视图 + 数据 不用
	 * void : 异步ajax
	 * String  :跳转视图 + model
	 */
	@RequestMapping(value="/test/index.do")
	public String index(Model model){
		System.out.println("centerController");
//		model.addAttribute("msg", "控制器");
		TestTb testTb = new TestTb();
		testTb.setName("测试111");
		testTb.setBirthday(new Date());
		testTbServices.insertTestTb(testTb);
		return "index";
	}
}
