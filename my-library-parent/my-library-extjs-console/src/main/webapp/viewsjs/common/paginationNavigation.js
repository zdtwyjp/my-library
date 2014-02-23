function createPaginationNav(pageFunName, currentPage, totalPages, totalResult) {
	var pageText = "";
	if(totalResult > 0) {
		if(totalPages > 0) {
			pageText += "<div class='p_bar'>";
			pageText += "<a class='p_total'>&nbsp;" + totalResult + "&nbsp;</a>";
			pageText += "<a class='p_pages'>&nbsp;" + (currentPage + 1) + "/" + totalPages + "&nbsp;</a>";
			if(currentPage > 4) {
				pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(0)' class='p_redirect'>|‹</a>";
			}
			if(currentPage > 0) {
				pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + (currentPage - 1)
						+ ")' class='p_redirect'>‹‹</a>";
			}
			if(totalPages <= 9) {
				for(var i = 0; i < totalPages; i++) {
					if(currentPage != i) {
						pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + i + ")' class='p_num'>" + (i + 1)
								+ "</a>";
					}else {
						pageText += "<a class='p_curpage'>" + (i + 1) + "</a>";
					}
				}
			}else {
				if(currentPage < 5) {
					for(var i = 0; i < 9; i++) {
						if(currentPage != i) {
							pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + i + ")' class='p_num'>" + (i + 1)
									+ "</a>";
						}else {
							pageText += "<a class='p_curpage'>" + (i + 1) + "</a>";
						}
					}
				}else {
					if(totalPages > (currentPage + 3)) {
						for(var i = (currentPage - 4); i < (currentPage + 4); i++) {
							if(currentPage != i) {
								pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + i + ")' class='p_num'>"
										+ (i + 1) + "</a>";
							}else {
								pageText += "<a class='p_curpage'>" + (i + 1) + "</a>";
							}
						}
					}else {
						for(var i = (totalPages - 10); i < (totalPage); i++) {
							if(currentPage != i) {
								pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + i + ")' class='p_num'>"
										+ (i + 1) + "</a>";
							}else {
								pageText += "<a class='p_curpage'>" + (i + 1) + "</a>";
							}
						}
					}
				}
			}
			if(currentPage + 1 < totalPages) {
				pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + (currentPage + 1)
						+ ")' class='p_redirect'>››</a>";
			}
			if(currentPage + 5 < totalPages) {
				pageText += "<a href='javascript:void(0)' onclick='" + pageFunName + "(" + (totalPages - 1)
						+ ")' class='p_redirect'>|›</a>";
			}
		}
	}
	pageText += "<a class='p_pages' style='padding: 0px;'>";
	pageText += "<input  class='p_input' id='numInput' name='custompage' onkeydown='if(event.keyCode==13) {valid(this)}' type='text'>";
	pageText += "</a>";
	return pageText;
}