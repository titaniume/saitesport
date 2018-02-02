package cn.jack.core.service.product;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;

public interface BrandService {
	
	
	//查询分页对象
	public Pagination selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo);
	
	
	//通过Id查询品牌
    public Brand selectBrandById(Long id);
    
    //修改
    public void updateBrandById(Brand brand);

}
