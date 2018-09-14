<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$("#ret_code option[value='${ret_code}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/phonerecharge">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden" name="starttime" value="${starttime}" />
		<input type="hidden" name="endtime" value="${endtime }" /> 
		<input type="hidden" name="ret_code" value="${ret_code}" /> 
		<input type="hidden" name="sporder_id" value="${sporder_id}" /> 
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/phonerecharge"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>订单：<input type="text" name="sporder_id"
							value="${sporder_id}"></td>
						<td>日期：<input type="text" name="starttime"
							value="${starttime }" class="date" dateFmt="yyyy-MM-dd" /> --<input
							type="text" name="endtime" value="${endtime }" class="date"
							dateFmt="yyyy-MM-dd" /></td>
						<td><select class="combox" name="ret_code" id="ret_code"
							style="width: 100px">
								<option value="0">订单状态</option>
								<option value="1">成功</option>
								<option value="2">处理中</option>
								<option value="9">失效</option>
						</select></td>
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
					<th>订单编号</th>
					<th>手机号</th>
					<th>面值</th>
					<th>订单时间</th>
					<th>处理时间</th>
					<th>充值后状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="orderid" rel="${order.sporder_id }">
						<td><c:out value="${order.sporder_id}"></c:out></td>
						<td><c:out value="${order.game_userid}"></c:out></td>
						<td><c:out value="${order.cardnum}"></c:out></td>
						<td><c:out value="${order.sporder_time}"></c:out></td>
						<td><c:out value="${order.ordersuccesstime}"></c:out></td>
						<td><c:choose>
								<c:when test="${order.ret_code==1}">
								成功
							</c:when>
								<c:when test="${order.ret_code==2}">
								处理中
							</c:when>
								<c:when test="${order.ret_code==4}">
								失效
							</c:when>
							<c:when test="${order.ret_code==9}">
								撤销
							</c:when>
							</c:choose></td>
						<td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a>  -->
							<a title="查看订单详情" target="dialog" rel="net${order.sporder_id }"
							href="<%=adminbasePath %>/phonerecharge/showPhoneRecharge?sporder_id=${order.sporder_id }"
							class="btnView" width="800" height="220">查看订单详情</a>
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