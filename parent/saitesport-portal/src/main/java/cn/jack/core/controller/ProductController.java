package cn.jack.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台商品
 * @author lx
 *
 */
@Controller
public class ProductController {

	//去首页  入口
	@RequestMapping(value = "/")
	public String index(){
		
		return "index";
	}
}
