package cn.jack.core.service.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Color;
import cn.jack.core.bean.product.ColorQuery;
import cn.jack.core.bean.product.Product;
import cn.jack.core.bean.product.ProductQuery;
import cn.jack.core.bean.product.ProductQuery.Criteria;
import cn.jack.core.bean.product.Sku;
import cn.jack.core.dao.product.ColorDao;
import cn.jack.core.dao.product.ProductDao;
import cn.jack.core.dao.product.SkuDao;
import redis.clients.jedis.Jedis;

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
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private Jedis jedis;
	
	
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
	
	/**
	 * 添加商品
	 * @param product
	 */
	public void  insertProduct(Product product){
			//唯一Id
			Long id = jedis.incr("pno");
			product.setId(id);
		
			//下架状态 后台程序写的
			product.setIsShow(false);
			//删除  后台程序写的不删除
			product.setIsDel(true);
			productDao.insertSelective(product);
			//返回ID
			//保存SKU
			String[] colors = product.getColors().split(",");
			String[] sizes = product.getSizes().split(",");
			//颜色
			for (String color : colors) {
				for (String size : sizes) {
					//保存SKU
					Sku sku = new Sku();
					//sku.setId(null);
					//商品ＩＤ
					sku.setProductId(product.getId());
					//颜色
					sku.setColorId(Long.parseLong(color));
					//尺码
					sku.setSize(size);
					//市场价
					sku.setMarketPrice(999f);
					//售价
					sku.setPrice(666f);
					//运费
					sku.setDeliveFee(8f);
					//库存
					sku.setStock(0);
					//限制
					sku.setUpperLimit(200);
				// 时间
				sku.setCreateTime(new Date());

				skuDao.insertSelective(sku);

			}
		}
	}
	
	// 上架
	public void isShow(Long[] ids) {
		Product product = new Product();
		// 上架
		product.setIsShow(true);
		for (Long id : ids) {
			product.setId(id);
			// 商品状态的变更
			productDao.updateByPrimaryKeySelective(product);

			// TODO 保存商品信息到SOlr服务器

			// TODO 静态化
		}
	}
}
	

