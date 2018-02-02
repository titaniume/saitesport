package cn.jack.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;
import cn.jack.core.service.product.BrandService;

/**
 * 品牌管理
 * 列表
 * 删除
 * 修改
 * 添加
 * 删除(单)
 * @author titaniume
 *
 */
@Controller
public class BrandController {
	@Autowired
	private  BrandService brandService;
	
	@RequestMapping(value="/brand/list.do")
	public String list(String name,Integer isDisplay, Integer pageNo,Model model){
		Pagination pagination = brandService.selectPaginationByQuery(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		if(null != isDisplay){
			model.addAttribute("isDisplay", isDisplay);
		}else{
			model.addAttribute("isDisplay", 1);
		}
		
		return "brand/list";
	}
	
	@RequestMapping(value="/brand/toEdit.do")
	public String toEdit(Long id,Model model){
		Brand brand = brandService.selectBrandById(id); //快速返回:shift+alt+l
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	@RequestMapping(value="/brand/edit.do")
	public String toEdit(Brand brand){ 
		brandService.updateBrandById(brand);
		return "redirect:/brand/list.do";
	}
}
