package cn.jack.core.service;



import cn.jack.common.page.Pagination;


public interface SearchService {

	// 全文检索
	public Pagination selectPaginationByQuery(Integer pageNo,String keyword,Long brandId,String price) throws Exception;

	//保存商品到solr
	public void insertProductToSolr(Long id);
}
