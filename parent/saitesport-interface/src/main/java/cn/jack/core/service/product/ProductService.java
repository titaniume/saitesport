package cn.jack.core.service.product;

import java.util.List;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Color;

public interface ProductService {

	
	//分页对象
	public Pagination selectPaginationByQuery(Integer pageNo,String name,Long brandId,Boolean isShow);

	
	//颜色结果集
	public List<Color> selectColorList();
}
