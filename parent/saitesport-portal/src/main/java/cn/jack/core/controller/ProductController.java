package cn.jack.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jack.common.page.Paginable;
import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Product;
import cn.jack.core.service.SearchService;

/**
 * 前台商品
 * @author lx
 *
 */
@Controller
public class ProductController {
	
	
	@Autowired
	private SearchService searchService;
	

	//去首页  入口
	@RequestMapping(value = "/")
	public String index(){
		
		return "index";
	}
	
	@RequestMapping(value = "/search")
	public String search(Integer pageNo,String keyword,Model model) throws Exception{
		 Pagination pagination= searchService.selectPaginationByQuery(pageNo, keyword);
		 model.addAttribute("pagination", pagination);
		return "search";
	}
	
	
}
