<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/system/showSystemQQ">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 

	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add"
					href="<%=adminbasePath%>/system/showSystemQQviw?qq_id=0" 
					target="dialog" mask=true width="800" height="270"><span>添加</span></a></li>
				<li><a class="edit"
					href="<%=adminbasePath%>/system/showSystemQQviw?qq_id={orderid}" 
					target="dialog" mask=true width="800" height="270"><span>编辑</span></a></li>
				<li><a title="确实要删除这些记录吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/system/delSystemQQ?qq_id={orderid}" class="delete"><span>删除</span></a></li>
				<!-- <li class="line">line</li>
				<li><a class="edit" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>修改</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>查看</span></a></li>
				<li class="line">line</li> -->
			</ul>
		</div>
		<table class="table" width="1200" layoutH="75">
			<thead>
				<tr>
					<th>编号</th>
					<th>QQ号码</th>
					<th>QQ名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="orderid" rel="${order.qq_id }">
						<td><c:out value="${order.qq_id }"></c:out></td>
						<td><c:out value="${order.qq_number}"></c:out></td>
						<td><c:out value="${order.qq_name}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>