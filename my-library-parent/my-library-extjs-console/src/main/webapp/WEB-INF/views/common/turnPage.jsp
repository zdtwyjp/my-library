<%@ page  contentType="text/html; charset=UTF-8"%>
<c:if test="${pagination!=null&&pagination.totalPages>0}">
<div class="p_bar">
	<a class="p_total">&nbsp;当前第${pagination.currentPage+1}页 / 总${pagination.totalPages}页&nbsp;</a>
	<c:if test="${pagination!=null&&pageUrl!=null}">					
		<c:if test="${pagination.totalPages>0}">			
			<c:if test="${pagination.currentPage>4}">
				<a href="<c:url value="${pageUrl}&currentPage=0"/>" class="p_num" title="第一页">|‹</a>
			</c:if>		
        	<c:if test="${pagination.currentPage>0}">        		
        		<a href="<c:url value="${pageUrl}&currentPage=${pagination.currentPage-1}"/>" class="p_num"  title="上一页">‹‹</a>
<%--         		<a href="<c:url value="${pageUrl}&currentPage=${pagination.currentPage-1}"/>" class="on"></a> --%>
        	</c:if>
        	<c:choose>
        	<c:when test="${pagination.totalPages<=9}">
       		<c:forEach begin="1" end="${pagination.totalPages}" var="page">
       			<c:choose>
       			<c:when test="${pagination.currentPage+1!=page}"> 
       				<a href="<c:url value="${pageUrl}&currentPage=${page-1}"/>" class="p_num"><c:out value="${page}"/></a>
       			</c:when>
       			<c:otherwise> 
					<a class="p_curpage"><c:out value="${page}"/></a>
       			</c:otherwise>
       			</c:choose>
       		</c:forEach>
       		</c:when>
       		<c:otherwise>
        		<c:choose>
        		<c:when test="${pagination.currentPage<5}">
        		<c:forEach begin="1" end="9" var="page">
        			<c:choose>
        			<c:when test="${pagination.currentPage+1!=page}"> 
        				<a href="<c:url value="${pageUrl}&currentPage=${page-1}"/>" class="p_num"><c:out value="${page}"/></a>
        			</c:when>
        			<c:otherwise> 
						<a class="p_curpage"><c:out value="${page}"/></a>
        			</c:otherwise>
        			</c:choose>
        		</c:forEach>
        		</c:when>
        		<c:otherwise>
        			<c:choose>
	        		<c:when test="${pagination.totalPages>pagination.currentPage+4}">
	        		<c:forEach begin="${pagination.currentPage-3}" end="${pagination.currentPage+5}" var="page">
	        			<c:choose>
	        			<c:when test="${pagination.currentPage+1!=page}"> 
	        				<a href="<c:url value="${pageUrl}&currentPage=${page-1}"/>" class="p_num"><c:out value="${page}"/></a>
	        			</c:when>
	        			<c:otherwise> 
							<a class="p_curpage"><c:out value="${page}"/></a>
	        			</c:otherwise>
	        			</c:choose>
	        		</c:forEach>
	        		</c:when>
	        		<c:otherwise>
		        		<c:forEach begin="${pagination.totalPages-9}" end="${pagination.totalPages}" var="page">
			        		<c:choose>
		        			<c:when test="${pagination.currentPage+1!=page}"> 
		        				<a href="<c:url value="${pageUrl}&currentPage=${page-1}"/>" class="p_num"><c:out value="${page}"/></a>
		        			</c:when>
		        			<c:otherwise> 
								<a class="p_curpage"><c:out value="${page}"/></a>
		        			</c:otherwise>
		        			</c:choose>
		        		</c:forEach>				        		
	        		</c:otherwise>
	        		</c:choose>
        		</c:otherwise>
        		</c:choose>
       		</c:otherwise>
       		</c:choose>
     		
        	<c:if test="${pagination.currentPage+1<pagination.totalPages}">        		
        		<a href="<c:url value="${pageUrl}&currentPage=${pagination.currentPage+1}"/>"  class="p_num"  title="下一页">››</a>
<%--         		<a href="<c:url value="${pageUrl}&currentPage=${pagination.currentPage+1}"/>" class="next"></a> --%>
        	</c:if>

			<c:if test="${pagination.currentPage+5<pagination.totalPages}">
				<a href="<c:url value="${pageUrl}&currentPage=${pagination.totalPages-1}"/>" class="p_num" title="最后一页">›|</a>
			</c:if>
			<span class="total">共有<b class="red">${pagination.totalResults}</b>条数据</span>&nbsp;			
			<!-- <a  class="input">
			<input name="custompage" onkeydown="if(event.keyCode==13) {window.location='<c:url value="${pageUrl}&currentPage="/>'+(this.value-1); return false;}" type="text">
			</a> -->
       	</c:if>	
	</c:if>       
</div>
</c:if>