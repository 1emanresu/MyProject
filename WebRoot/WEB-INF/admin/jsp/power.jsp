<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/power">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="powerid" value="${powerid }"/>
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/power"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>权限编号：</label>
						<input type="text" name="powerid" value="${powerid }"/>
					</li>
				</ul>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add"
					href="<%=adminbasePath%>/power/showAddPower" rel="addpower"
					target="dialog" width="800" height="180"><span>添加</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要删除这项记录吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/power/delPower?powerid={powerid}" class="delete"><span>删除</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>权限编号</th>
					<th>名称</th>
					<th>说明</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${powerlist}" var="power">
					<tr target="powerid" rel="${power.powerid }">
						<td><c:out value="${power.powerid}"></c:out></td>
						<td><c:out value="${power.name}"></c:out></td>
						<td><c:out value="${power.explain}"></c:out></td>
						<td>
						<a class="btnEdit" title="授权" href="<%=adminbasePath%>/power/showEmpower?powerid=${power.powerid}" target="dialog" rel="power${power.powerid}" width="410" height="500"><span>授权</span></a>
						</td>
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