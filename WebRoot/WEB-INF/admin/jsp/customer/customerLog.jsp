<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/customerLog">
		<input type="hidden"name="pageNum" value="1" /> 
		<input type="hidden"name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden"name="customerstarttime" value="${customerstarttime}" /> 
		<input type="hidden"name="customerendtime" value="${customerendtime }" /> 
		<input type="hidden"name="customer_id" value="${customer_id}" /> 
		<input type="hidden"name="operatType" value="${operatType}" /> 
		<input type="hidden"name="refundType" value="${refundType}" /> 
	</form>
	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/customerLog"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
				    <li style="width: 210px"><label style="width: 40px">用户id:</label>
						<input type="text" value="${customer_id}" name="customer_id" id="userid"/>
					</li>
					<li style="width:420px">日期:
						<input value="${customerstarttime}" type="text" name="customerstarttime"   class="date netstarttime" dateFmt="yyyy-MM-dd" /> 
						<input value="${customerendtime }" type="text" name="customerendtime"  class="date netendtime" dateFmt="yyyy-MM-dd" />
						<ul style="position:absolute;top:30px;">
							<li>
								<input type="button" name="today" value=" 今天 " onClick="MyDate(1,this);">
								<input type="button" name="yesteday" value=" 昨天 " onClick="MyDate(2,this);">
								<input type="button" name="week" value=" 本周 " onClick="MyDate(4,this);">
							</li>
						</ul>
					</li>
					<li style="width: 200px"><label>操作类型:</label><select
						class="combox" name="operatType" id="operatType"
						style="width: 100px">
							<option value="">请选择</option>
							<option value="0">提现申请</option>
							<option value="1">代付申请</option>
							<option value="2">日结金额</option>
							<option value="3">后台提现管理</option>
					</select></li>
					<li style="width: 200px"><label>操作状态:</label><select
						class="combox" name="refundType" id="refundType"
						style="width: 100px">
							<option value="">请选择</option>
							<option value="0">失败</option>
							<option value="1">成功</option>
							<option value="2">处理中</option>
					</select></li>
				</ul>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>用户id</th>
					<th>代理</th>
					<th>用户操作类型</th>
					<th>后台操作状态</th>
					<th>涉及金额</th>
					<th>今日支付宝金额</th>
					<th>今日微信金额</th>
					<th>今日qq金额</th>
					<th>用户账户余额</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${customerList}" var="customer">
					<tr>
						<td>${customer.customer_id }</td>
						<td>
							<c:choose>
								<c:when test="${customer.isAgent == 1 }"><span style="color:red;font-weight:700;">是</span></c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:if test="${customer.operation_type==0 }">提现申请</c:if>
							<c:if test="${customer.operation_type==1 }">代付申请</c:if>
							<c:if test="${customer.operation_type==2 }">日结金额</c:if>
							<c:if test="${customer.operation_type==3 }">后台提现管理</c:if>
						</td>
						<td>
							<c:if test="${customer.refund_type==0 }">失败</c:if>
							<c:if test="${customer.refund_type==1 }">成功</c:if>
							<c:if test="${customer.refund_type==2 }">处理中</c:if>
						</td>
						<td>${customer.amount }</td>
						<td>${customer.ali_result }</td>
						<td>${customer.weixin_result }</td>
						<td>${customer.qq_result }</td>
						<td>${customer.settlement }</td>
						<td>${customer.create_time }</td>
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
				totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>
	</div>
	<script type="text/javascript">
		$("#operatType option[value='${operatType}']").attr("selected", true);
		$("#refundType option[value='${refundType}']").attr("selected", true);
	</script>
</body>
</html>