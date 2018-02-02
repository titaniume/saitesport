package cn.jack.core.controller;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/control")
public class CenterController {
	
	/**
	 * 入口
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index.do")
	public String index(Model model){
		return "index";
	}
	/**
	 * 头部
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/top.do")
	public String top(Model model){
		return "top";
	}
	
	/**
	 * 身体
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/main.do")
	public String ccc(Model model){
		return "main";
	}
	
	/**
	 * 身体---左边
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/left.do")
	public String left(Model model){
		return "left";
	}
	
	/**
	 * 身体---右边
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/right.do")
	public String right(Model model){
		return "right";
	}
	
	
	//商品身体-
	@RequestMapping(value = "/frame/product_main.do")
	public String product_main(Model model){
		
		return "frame/product_main";
	}
	
	@RequestMapping(value = "/frame/product_left.do")
	public String product_left(Model model){
		
		return "frame/product_left";
	}
	

	

	
	


}
