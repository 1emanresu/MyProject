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
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/dynamic">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${numPerPage}" /> <input type="hidden"
			name="starttime" value="${starttime}" /> <input type="hidden"
			name="endtime" value="${endtime }" /> <input type="hidden"
			name="title" value="${title}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/dynamic"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<ul class="searchContent">
					<li><label>名称：</label> <input type="text" name="title">
					</li>
					<li style="width: 400px"><label>日期：</label><input type="text"
						name="starttime" class="date" dateFmt="yyyy-MM-dd" /> --<input
						type="text" name="endtime" class="date" dateFmt="yyyy-MM-dd" /></li>
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
					href="<%=adminbasePath%>/dynamic/getaddDynamic" 
					target="dialog" width="800" height="650"><span>添加</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要删除这些记录吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/dynamic/delDynamic?dynamicid={dynamicid}" class="delete"><span>删除</span></a></li>
				<li class="line">line</li>
			</ul>
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>编号</th>
					<th>标题</th>
					<th>创建时间</th>
					<th>操作人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dynamiclist}" var="dynamic">
					<tr target="dynamicid" rel="${dynamic.dynamicid }">
						<td><c:out value="${dynamic.dynamicid}"></c:out></td>
						<td><c:out value="${dynamic.title}"></c:out></td>
						<td><c:out value="${dynamic.createtime}"></c:out></td>
						<td><c:out value="${dynamic.employeeid}"></c:out></td>
						<td>
						<a title="确实要删除这些记录吗?" target="ajaxTodo" href="<%=adminbasePath%>/dynamic/delDynamic?dynamicid=${dynamic.dynamicid }" class="btnDel"><span>删除</span></a>
						<a class="btnEdit" title="编辑" href="<%=adminbasePath%>/dynamic/getupDynamic?dynamicid=${dynamic.dynamicid }" target="dialog" rel="dynamic${dynamic.dynamicid }" width="800" height="650"><span>编辑</span></a>
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