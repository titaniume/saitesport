package cn.jack.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jack.core.bean.product.Sku;
import cn.jack.core.bean.product.SkuQuery;
import cn.jack.core.dao.product.ColorDao;
import cn.jack.core.dao.product.SkuDao;



@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService{
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ColorDao colorDao;
	
	//商品Id 查询 库存结果集
	public List<Sku> selectSkuListByProductId(Long productId){
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
			
		}
		return skus;
	}
	
	//修改
	public void updaeSkuById(Sku sku){
		skuDao.updateByPrimaryKeySelective(sku);
	}
	
}
