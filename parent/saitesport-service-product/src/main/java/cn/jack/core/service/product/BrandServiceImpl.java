package cn.jack.core.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jack.common.page.Pagination;
import cn.jack.core.bean.product.Brand;
import cn.jack.core.bean.product.BrandQuery;
import cn.jack.core.dao.product.BrandDao;
import redis.clients.jedis.Jedis;



/**
 * 品牌管理
 * @author titaniume
 *
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private Jedis jedis;
	
	
	//查询分页对象
	public Pagination selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo){
		
		BrandQuery brandQuery = new BrandQuery();
		//当前页
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		//没页数
		brandQuery.setPageSize(3);
		
		StringBuilder params = new StringBuilder();
		
		
		//条件
		if(null !=name){
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
			params.append("$isDisplay=").append(isDisplay);
		}else{
			brandQuery.setIsDisplay(1);
			params.append("$isDisplay=").append(1);
		}
		Pagination pagination = new Pagination(
				brandQuery.getPageNo(),
				brandQuery.getPageSize(),
				brandDao.selectCount(brandQuery)
				);
		//设置结果集
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		//分页展示
		String url= "/brand/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}


	@Override
	public Brand selectBrandById(Long id) {
		return brandDao.selectBrandById(id);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void updateBrandById(Brand brand) {
		//修改Redis
		jedis.hset("brand", String.valueOf(brand.getId()),brand.getName());
		brandDao.updateBrandById(brand);
		
	}
	
	/**
	 * 查询从redis中
	 * @return
	 */
	public List<Brand> selectBrandListFromRedi(){
		List<Brand> brands = new ArrayList<Brand>();
		//从Reeis中查询
		Map<String, String> hgetAll = jedis.hgetAll("brand");
		Set<Entry<String, String>> entrySet = hgetAll.entrySet();
		for (Entry<String, String> entry : entrySet) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(entry.getKey()));
			brand.setName(entry.getValue());
			brands.add(brand);
		}
		return brands;
	}


	@Override
	public void deletes(Long[] ids) {
		brandDao.deletes(ids);
		
	}


	@Override
	public List<Brand> selectBrandListByQuery(Integer isDisplay) {
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setIsDisplay(isDisplay);
		return brandDao.selectBrandListByQuery(brandQuery);
	}
	
}
