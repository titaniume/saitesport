package cn.jack.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.json.JSONObject;
import cn.jack.core.bean.product.Sku;
import cn.jack.core.service.product.SkuService;


/**
 * 库存管理
 * 去库存页面
 * js修改
 * 保存
 * @author titaniume
 *
 */
@Controller
public class SkuController {
	
	@Autowired
	private SkuService skuService;
	
	/**
	 * 库存查询
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/sku/list.do")
	public String list(Long productId,Model model){
		List<Sku> skus = skuService.selectSkuListByProductId(productId);
		model.addAttribute("skus", skus);
		return "/sku/list";
	}
	
	

	@RequestMapping(value="/sku/addSku.do") 
	public void addSku(Sku sku,HttpServletResponse response) throws IOException{
		skuService.updaeSkuById(sku);
		JSONObject josn = new JSONObject();
		josn.put("message", "保存成功!");
		//response.setContentType("application/json;character=UTF-8");
		//设置输出乱码问题
	    response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
		
		response.getWriter().write(josn.toString());
	}
}
