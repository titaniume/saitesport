package cn.jack.core.service.product;

import java.util.List;

import cn.jack.core.bean.product.Sku;

public interface SkuService {
	
	//商品Id 查询 库存结果集
	public List<Sku> selectSkuListByProductId(Long productId);
	
	
	//修改
	public void updaeSkuById(Sku sku);
}
