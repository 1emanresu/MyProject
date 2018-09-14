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
	<div class="pageContent">
		<table class="table" width="1200" layoutH="20">
			<thead>
				<tr>
					<th>订单编号</th>
					<!-- <th>充值卡的卡号</th>
					<th>充值卡密码</th> -->
					<th>金额</th>
					<th>实际金额</th>
					<th>最后结算</th>
					<th>扣除金额</th>
					<th>订单流水编号</th>
					<th>交易情况说明</th>
					<th>交易方式</th>
					<th>支付结果</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${singlist}" var="sing">
					<tr target="singid" rel="${sing.orderno }">
						<td><c:out value="${sing.orderno}"></c:out></td>
						<%-- <td><c:out value="${order.cardcode}"></c:out></td>
						<td><c:out value="${order.cardno}"></c:out></td> --%>
						<td><c:out value="${sing.value}"></c:out></td>
						<td><c:out value="${sing.actual}"></c:out></td>
						<td><c:out value="${sing.accountvalue}"></c:out></td>
						<td><c:out value="${sing.deducted}"></c:out></td>
						<td><c:out value="${sing.billid}"></c:out></td>
						<td><c:out value="${sing.info}"></c:out></td>
						<td><c:out value="${sing.payable}"></c:out></td>
						<td><c:choose>
								<c:when test="${sing.result==2000}">
								成功
							</c:when>
								<c:when test="${sing.result==2001}">
								处理中
							</c:when>
								<c:when test="${sing.result!=2000&&order.result!=2001}">
								失败
							</c:when>
							</c:choose></td>
						<td><c:out value="${sing.datetime}"></c:out></td>
						<td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a> -->
							<a title="查看订单详情" target="dialog" rel="sings${sing.orderno }" mask=true
							href="<%=adminbasePath %>/card/showSing?singid=${sing.orderno }"
							class="btnView" width="900" height="370">查看订单详情</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>