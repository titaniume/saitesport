package cn.jack.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jack.common.page.Paginable;
import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;
import cn.jack.core.bean.product.Product;
import cn.jack.core.service.SearchService;
import cn.jack.core.service.product.BrandService;

/**
 * 前台商品
 * @author lx
 *
 */
@Controller
public class ProductController {
	
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private BrandService brandService;
	

	//去首页  入口
	@RequestMapping(value = "/")
	public String index(){
		
		return "index";
	}
	/**
	 * 搜索
	 * @param pageNo 页码
	 * @param keyword 关键字
	 * @param model 返回模型
	 * @return  分页对象
	 * @throws Exception
	 * brandId price 回显到页面
	 */
	@RequestMapping(value = "/search")
	public String search(Integer pageNo,String keyword,Long brandId,String price,Model model) throws Exception{
		//从Redis中查询品牌结果集
		List<Brand> brands = brandService.selectBrandListFromRedi();
		model.addAttribute("brands", brands);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		//已选条件 容器 Map
		Map<String,String> map = new HashMap<String,String>();
		//品牌
		if(null != brandId){
			for (Brand brand : brands) {
				if(brandId == brand.getId()){
					map.put("品牌", brand.getName());
					break;
				}
			}
		}
		//价格  0-99     1600
		if(null != price){
			if(price.contains("-")){
				map.put("价格", price);
			}else{
				map.put("价格", price + "以上");
			}
		}
		
		model.addAttribute("map", map);

		Pagination pagination= searchService.selectPaginationByQuery(pageNo, keyword,brandId,price);
		model.addAttribute("pagination", pagination);
		return "search";
	}
	
	
}
