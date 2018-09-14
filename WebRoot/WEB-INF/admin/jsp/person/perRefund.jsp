<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#refustate option[value='${refustate}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/showRefund" onsubmit="return divSearch(this, 'jbsxBox');">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="refustarttime" value="${refustarttime}" />
		<input type="hidden" name="refuendtime" value="${refuendtime }" />
		<input type="hidden" name="refustate" value="${refustate}" />
		<input type="hidden" name="id" value="${id}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/showRefund"
			onsubmit="return divSearch(this, 'jbsxBox');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="id" value="${id}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px"><label>日期：</label><input value="${refustarttime}" type="text" name="refustarttime" class="date" dateFmt="yyyy-MM-dd"/>
					--<input value="${refuendtime }" type="text" name="refuendtime" class="date" dateFmt="yyyy-MM-dd"/>
					</li>
					<li style="width: 200px"><label>订单状态：</label>
						<select class="combox" name="refustate" id="refustate" style="width: 100px">
							<option value="0">请选择</option>
							<option value="1">成功</option>
							<option value="2">处理中</option>
							<option value="3">失败</option>
						</select>
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
					<th>订单编号</th>
					<th>用户</th>
					<th>提现金额</th>
					<th>提现账户类型</th>
					<th>提现权限</th>
					<th>提现说明</th>
					<th>提现状态</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="refund_id" rel="${order.refund_id }">
						<td><c:out value="${order.refund_id}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.refund_amount}"></c:out></td>
						<td>
						<c:choose>
						<c:when test="${empty order.payable}">
							支付宝
						</c:when>
						<c:otherwise>
							<c:out value="${order.payable}"></c:out>
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${order.settlementauthority==1}">
							T+0
						</c:when>
						<c:when test="${order.settlementauthority==2}">
							T+1
						</c:when>
						</c:choose>
						</td>
						<td>
						<c:out value="${order.explain}"></c:out>
						</td>
						<td>
						<c:if test="${order.refund_state==1}">
							成功
						</c:if>
						<c:if test="${order.refund_state==2}">
							处理中
						</c:if>
						<c:if test="${order.refund_state==3}">
							失败
						</c:if></td>
						<td><c:out value="${order.createtime}"></c:out></td>
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