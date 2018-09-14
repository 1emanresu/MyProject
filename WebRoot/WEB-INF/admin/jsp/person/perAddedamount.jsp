<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/showAddedamount" onsubmit="return divSearch(this, 'jbsxBox');">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="addestarttime" value="${addestarttime}" />
		<input type="hidden" name="addeendtime" value="${addeendtime }" />
		<input type="hidden" name="id" value="${id}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/showAddedamount"
			onsubmit="return divSearch(this, 'jbsxBox');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="id" value="${id}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px"><label>日期：</label><input value="${addestarttime}" type="text" name="addestarttime" class="date" dateFmt="yyyy-MM-dd"/>
					--<input value="${addeendtime }" type="text" name="addeendtime" class="date" dateFmt="yyyy-MM-dd"/>
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
		<table class="table" width="99%" layoutH="290" rel="jbsxBox">
			<thead>
				<tr>
					<th>用户</th>
					<th>操作金额</th>
					<th>操作时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="addedamount_id" rel="${order.addedamount_id }">
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.addedamount_amount}"></c:out></td>
						<td><c:out value="${order.addedamount_time}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="dialogPageBreak({pageNum:this.value},{numPerPage:this.value},'jbsxBox')">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" rel="jbsxBox" targetType="dialog"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>