package cn.jack.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Color;
import cn.jack.core.bean.product.ColorQuery;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.bean.product.ProductQuery.Criteria;
import cn.jack.core.dao.product.ColorDao;
import cn.jack.core.dao.product.ProductDao;

/**
 * 商品
 * @author titaniume
 *
 */
@Service("productService")
@Transactional
public class ProductServiceImpl  implements ProductService{
	
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ColorDao colorDao;
	
	//分页对象
	public Pagination selectPaginationByQuery(Integer pageNo,String name,Long brandId,Boolean isShow){
		
		ProductQuery productQuery = new ProductQuery();
		productQuery.setPageNo(Pagination.cpn(pageNo));
		
		//排序
		productQuery.setOrderByClause("id desc");
		Criteria createCriteria = productQuery.createCriteria();
		StringBuilder params = new StringBuilder();
		if(null != name	){
			createCriteria.andNameLike("%"+ name +"%");
			params.append("name=").append(name);
		}
		if(null != brandId){
			createCriteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		//上下架
		if(null != isShow){
			createCriteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}else{
			createCriteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		Pagination pagination = new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(),
				productDao.countByExample(productQuery),
				productDao.selectByExample(productQuery)
					);
		String url="/product/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	/**
	 * 颜色结果集
	 * @return
	 */
	public List<Color> selectColorList(){
		ColorQuery colorQuery = new ColorQuery();
		//查询出不是父级的色系
		colorQuery.createCriteria().andParentIdNotEqualTo(0l);
		return colorDao.selectByExample(colorQuery);
	}
	
	
	
}
