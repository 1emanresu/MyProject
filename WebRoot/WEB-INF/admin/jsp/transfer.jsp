<%@page import="com.vo.Employees"%>
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
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/net/">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden" name="starttime" value="${starttime}" /> 
		<input type="hidden" name="endtime" value="${endtime }" />
		<input type="hidden" name="transfer_id" value="${transfer_id }" />
		<input type="hidden" name="pname" value="${pname }" />
		<input type="hidden" name="pename" value="${pename }" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/transfer"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>订单：<input type="text" name="transfer_id"
							value="${transfer_id}"></td>
						<td>转账用户：<input type="text" name="pname" value="${pname }"></td>
						<td>被转账用户：<input type="text" name="pename" value="${pename }"></td>
						<td>日期：<input type="text" name="starttime"
							value="${starttime }" class="date" dateFmt="yyyy-MM-dd" /> --<input
							type="text" name="endtime" value="${endtime }" class="date"
							dateFmt="yyyy-MM-dd" /></td>
					</tr>
				</table>
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
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>转账id</th>
					<th>转账账户</th>
					<th>转账接收账户</th>
					<th>转账时间</th>
					<th>转账说明</th>
					<th>转账金额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="orderid" rel="${order.transfer_id }">
						<td><c:out value="${order.transfer_id}"></c:out></td>
						<td><c:out value="${order.pname}"></c:out></td>
						<td><c:out value="${order.pename}"></c:out></td>
						<td><c:out value="${order.transfer_time}"></c:out></td>
						<td><c:out value="${order.transfer_explain}"></c:out></td>
						<td><c:out value="${order.transfer_amount}"></c:out></td>
						<%-- <td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a>  -->
							<a title="查看订单详情" target="dialog" rel="net${order.orderid }"
							href="<%=adminbasePath %>/net/showNet?netorderid=${order.orderid }"
							class="btnView" width="800" height="560">查看订单详情</a>
						</td> --%>
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