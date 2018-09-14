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
	$("#pay_result option[value='${pay_result}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/caifutong/">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/caifutong/"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>订单：<input type="text" name="sp_billno"
							value="${sp_billno}"></td>
						<td>日期：<input type="text" name="starttime"
							value="${starttime }" class="date" dateFmt="yyyy-MM-dd" /> --<input
							type="text" name="endtime" value="${endtime }" class="date"
							dateFmt="yyyy-MM-dd" /></td>
						<td><select class="combox" name="pay_result" id="pay_result"
							style="width: 100px">
								<option value="4">订单状态</option>
								<option value="0">成功</option>
								<option value="2">处理中</option>
								<option value="3">失败</option>
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
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>订单号</th>
					<th>财付通订单号</th>
					<th>用户财付通账号</th>
					<th>商户时间</th>
					<th>订单总金额</th>
					<th>分账回退结果</th>
					<th>分账回退结果信息</th>
					<th>退款单ID</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="sp_billno" rel="${order.sp_billno }">
						<td><c:out value="${order.sp_billno}"></c:out></td>
						<td><c:out value="${order.transaction_id}"></c:out></td>
						<td><c:out value="${order.purchaser_id}"></c:out></td>
						<td><c:out value="${order.date}"></c:out></td>
						<td><c:out value="${order.total_fee}"></c:out></td>
						<td><c:choose>
							<c:when test="${order.pay_result==0}">退款成功</c:when>
							<c:when test="${order.pay_result==2}">订单处理中</c:when>
							<c:otherwise>
								退款失败
							</c:otherwise>
						</c:choose>
						</td>
						<td><c:out value="${order.pay_info}"></c:out></td>
						<td><c:out value="${order.refund_id}"></c:out></td>
						<td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a>  -->
							<a title="查看订单详情" target="dialog" rel="caifu${order.sp_billno }"
							href="<%=adminbasePath %>/caifutong/getCaifutong?sp_billno=${order.sp_billno }"
							class="btnView" width="800" height="270">查看订单详情</a>
							<a title="退款" target="_blank" href="<%=adminbasePath %>/caifutong/caifutongreturn?sp_billno=${order.sp_billno }" class="btnEdit">退款</a>
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