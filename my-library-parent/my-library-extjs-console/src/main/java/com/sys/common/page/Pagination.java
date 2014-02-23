package com.sys.common.page;

import java.io.Serializable;

/**
 * <p>
 * Title: Pagination.java
 * </p>
 * <p>
 * Description:分页信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: fxpgy
 * </p>
 * <p>
 * team: fxpgy Team
 * </p>
 * <p>
 * 
 * @author: YangJunping
 *          </p>
 * @date 2012-4-7下午8:17:24
 * @version 1.0
 */
public class Pagination implements Serializable {
	private static final long serialVersionUID = 3081264459672330922L;

	/** 当前页数 */
	private int currentPage;

	/** 每页记录数,等于0表示一页显示全部 */
	private int pageResults = 10;

	/** 总页数 */
	private int totalPages = 0;

	/** 总记录数 */
	private int totalResults;

	/** 是否默认显示最后页数据 **/
	private boolean showLast = true;

	public Pagination() {}

	public Pagination(int pageResults) {
		this.pageResults = pageResults;
	}

	/**
	 * @Title: getFirstResult
	 * @Description: 指定返回第一记录索引
	 * @return
	 * @return int
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-7下午8:18:33
	 */
	public int getFirstResult() {
		int result = currentPage * pageResults;
		if(this.isShowLast()) {
			if(result > totalResults)
				result = totalResults - 1;
		}
		if(result < 0)
			result = 0;
		return result;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageResults() {
		return pageResults;
	}

	public void setPageResults(int pageRecords) {
		this.pageResults = pageRecords;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public boolean isShowLast() {
		return showLast;
	}

	public void setShowLast(boolean showLast) {
		this.showLast = showLast;
	}

	/**
	 * @Title: getTotalPages
	 * @Description: 得到总页数
	 * @return
	 * @return int
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-7下午8:19:35
	 */
	public int getTotalPages() {
		int result = 0;
		if(pageResults == 0) {
			result = (totalResults > 0) ? 1 : 0;
		}else {
			result = totalResults / pageResults
					+ ((totalResults % pageResults) > 0 ? 1 : 0);
		}
		this.totalPages = result;
		return result;
	}

	/**
	 * @Title: setTotalResults
	 * @Description: 设置总记录数
	 * @param totalResults
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-7下午8:19:47
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
		// if (pageResults <= 0) { // 一页显示全部
		// pageResults = totalResults;
		// }
		int pages = getTotalPages();
		if(this.isShowLast()) {
			if(currentPage >= pages) {
				currentPage = pages - 1;
			}
		}
		if(currentPage < 0) {
			currentPage = 0;
		}
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
