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
	<form id="pagerForm" method="post"
		action="<%=adminbasePath%>/usermanager">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${numPerPage}" /> <input type="hidden"
			name="account" value="${account }" /> <input type="hidden"
			name="employeeid" value="${emp.employeeid }" /> <input type="hidden"
			name="rolename" value="${emp.role_name}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/usermanager"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li><label>登录名：</label> <input type="text" name="account"
						value="${account }" /></li>
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
				<li><a class="add" href="<%=adminbasePath%>/usermanager/findRolename?employeeid={employeeid}" rel="addpower"
					target="dialog" width="600" height="480"><span>分配角色</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要删除这项吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/usermanager/delUser?employeeid={employeeid}"
					rel="deleterole" class="delete"><span>删除用户</span></a></li>
				<li><a target="dialog"
					href="<%=adminbasePath%>/usermanager/findUserRolename?employeeid={employeeid}"
					 class="delete"><span>删除分配角色</span></a></li>
			</ul>
		</div>
		<table class="table" width="1000" layoutH="138">
			<thead>
				<tr>
					<th>用户编号</th>
					<th>用户账号</th>
					<th>用户角色</th>
					<th>地址</th>
					<th>电话</th>
					<th>创建时间</th>
					<th>授权</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${emplist}" var="emp">
					<tr target="employeeid" rel="${emp.employeeid }">
						<td><c:out value="${emp.employeeid}"></c:out></td>
						<td><c:out value="${emp.account}"></c:out></td>
						<td><c:out value="${emp.role_name}"></c:out></td>
						<td><c:out value="${emp.address}"></c:out></td>
					<%--  --%>	<td><c:out value="${emp.phone}"></c:out></td>
						<td><c:out value="${emp.creationtime}"></c:out></td>
						<td><a class="btnEdit" title="授权"
							href="<%=adminbasePath%>/usermanager/showEmpPerm?employeeid=${emp.employeeid }"
							target="dialog" rel="emp${emp.employeeid }" width="410"
							height="500"><span>授权</span></a></td>
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