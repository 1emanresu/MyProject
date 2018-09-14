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
		action="<%=adminbasePath%>/rolemanager">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${numPerPage}" /> <input type="hidden"
			name="rolename" value="${rolename }" /> <input type="hidden"
			name="roleid" value="${role_id }" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/rolemanager"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li><label>角色名：</label> <input type="text" name="rolename"
						value="${rolename }" /></li>
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
					href="<%=adminbasePath%>/rolemanager/showAddRole" rel="addrole"
					target="dialog" width="800" height="200"><span>添加角色</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要删除这项吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/rolemanager/delRole?roleid={roleid}"
					rel="deleterole" class="delete"><span>删除角色</span></a></li>
			</ul>
		</div>
		<table class="table" width="500" layoutH="138">
			<thead>
				<tr>
					<th>角色编号</th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>授权</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rolelist}" var="role">
					<tr target="roleid" rel="${role.role_id }">
						<td><c:out value="${role.role_id}"></c:out></td>
						<td><c:out value="${role.role_name}"></c:out></td>
						<td><c:out value="${role.role_depict}"></c:out></td>
						<td><a class="btnEdit" title="授权"
							href="<%=adminbasePath%>/rolemanager/showRolePerm?roleid=${role.role_id }"
							target="dialog" rel="role${role.role_id }" width="410"
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