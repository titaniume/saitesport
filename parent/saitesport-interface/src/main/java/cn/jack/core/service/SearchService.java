package cn.jack.core.service;


import cn.jack.common.page.Paginable;
import cn.jack.common.page.Pagination;


public interface SearchService {

	// 全文检索
	public Pagination selectPaginationByQuery(Integer pageNo,String keyword) throws Exception;
}
