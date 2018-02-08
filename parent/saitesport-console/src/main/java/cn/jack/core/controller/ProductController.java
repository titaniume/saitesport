package cn.jack.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;
import cn.jack.core.bean.product.Color;
import cn.jack.core.bean.product.Product;
import cn.jack.core.service.product.BrandService;
import cn.jack.core.service.product.ProductService;

/**
 * 商品管理
 * 列表
 * 添加
 * 上架
 * @author titaniume
 *
 */
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value= "/product/list.do")
	public String list(Integer pageNo,String name, Long brandId,Boolean isShow,Model model){
		
		//品牌结果集
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		
		Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name",name );
		model.addAttribute("brandId",brandId);
		if(null != isShow){
			model.addAttribute("isShow",isShow);
		}else{
			model.addAttribute("isShow",false);
		}
		return "product/list";
	}
	
	@RequestMapping(value= "/product/toAdd.do")
	public String toAdd(Model model){
		
		//品牌结果集
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		
		//颜色结果集
		List<Color> colors = productService.selectColorList();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	
	@RequestMapping(value ="/product/add.do")
	public String add(Product product){
		productService.insertProduct(product);
		return "redirect:/product/list.do";
	}
}
	
