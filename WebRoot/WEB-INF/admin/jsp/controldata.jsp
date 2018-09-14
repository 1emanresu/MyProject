<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees) request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
	<div style="width:1199px; height:400px; overflow:auto; border:1px solid #000000" id="div">
	<table class="list" width="1200px" layoutH="138" id="table">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>用户</th>
				<th>商户订单编号</th>
				<th>支付金额</th>
				<th>实际金额</th>
				<th>交易方式</th>
				<th>ip</th>
				<th>支付结果</th>
				<th>是否反馈</th>
				<%
					if (employeeid == 1) {
				%>
				<th>是否被扣单</th>
				<%
					}
				%>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderlist}" var="order">
				<tr target="orderid" rel="${order.orderid }">
					<td><c:out value="${order.orderid}"></c:out></td>
					<td><c:out value="${order.p1_MerId}"></c:out></td>
					<td><c:out value="${order.p2_Order}"></c:out></td>
					<td><c:out value="${order.p3_Amt}"></c:out></td>
					<td><c:out value="${order.amount}"></c:out></td>
					<td><c:out value="${order.pd_FrpId}"></c:out></td>
					<td><c:out value="${order.ip}"></c:out></td>
					<td id="code" data-status="${order.r1_Code}"><c:choose>
							<c:when test="${order.r1_Code==1}">
								成功
							</c:when>
							<c:when test="${order.r1_Code==2}">
								处理中
							</c:when>
							<c:when test="${order.r1_Code==3}">
								失败
							</c:when>
							<c:when test="${order.r1_Code==4}">
								失效
							</c:when>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${order.success==1}">是</c:when>
							<c:when test="${order.success==2}">否</c:when>
						</c:choose></td>
					<%
						if (employeeid == 1) {
					%>
					<td><c:choose>
							<c:when test="${order.lock==1 }">正常</c:when>
							<c:when test="${order.lock==2 }">被扣单</c:when>
						</c:choose></td>
					<%
						}
					%>
					<td><c:out value="${order.CreateTime}"></c:out></td>
					<td>
						<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a>  -->
						<a title="查看订单详情" target="dialog" rel="net${order.orderid }"
						href="<%=adminbasePath %>/net/showNet?netorderid=${order.orderid }"
						class="btnView" width="800" height="560">查看订单详情</a> <%
 	if (employeeid == 1) {
 %> <c:if test="${order.lock==2 }">
							<a title="确实要对此记录进行恢复吗吗?" target="ajaxTodo"
								href="<%=adminbasePath%>/net/netRecovery?netorderid=${order.orderid }"
								class="btnEdit">恢复订单</a>
						</c:if> <%
 	}
 %>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="13">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="13"></td>
			</tr>
		</tbody>
	</table>
	<script type="text/javascript">
	$(function(){
		var audio = document.getElementById("audio");		
		var warn = "${warn}";
		if (warn == "true") {
			audio.play();
			console.log(warn)
		}else{			
			audio.pause();
			 audio.currentTime = 0;
			 console.log(warn)
		}
	})
	
	</script>
	</div>	
</body>
</html>