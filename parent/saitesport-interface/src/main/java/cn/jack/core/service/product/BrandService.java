package cn.jack.core.service.product;

import java.util.List;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;
import cn.jack.core.bean.product.BrandQuery;

public interface BrandService {
	
	
	//查询分页对象
	public Pagination selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo);
	
	//查询结果集
	public List<Brand> selectBrandListByQuery(Integer isDisplay);
	
	//通过Id查询品牌
    public Brand selectBrandById(Long id);
    
    //修改
    public void updateBrandById(Brand brand);
    
    //删除
  	public void deletes(Long [] ids);

}
