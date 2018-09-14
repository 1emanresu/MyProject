<%@ page import="com.vo.Employees"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	</head>
	<body>
		<form id="pagerForm" method="post" action="<%=adminbasePath%>/withdraw">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="refustarttime" value="${refustarttime}" />
			<input type="hidden" name="refuendtime" value="${refuendtime }" />
			<input type="hidden" name="refustate" value="${refustate}" />
			<input type="hidden" name="refuid" value="${refuid}" />
			<input type="hidden" name="refuname" value="${refuname}" />
			<input type="hidden" name="settlementauthority" value="${settlementauthority}" />
		</form>
		<div class="pageHeader">
			<form rel="pagerForm" action="<%=adminbasePath%>/withdraw" onsubmit="return navTabSearch(this)" method="post">
				<input type="hidden" name="pageNum" value="1" />
				<input type="hidden" name="numPerPage" value="${numPerPage}" />
				<div class="searchBar">
					<ul class="searchContent" >
						<li style="width: 380px"><label style="width: 25px">日期</label>
							<input value="${starttime}" type="text" name="withdrawstarttime"  class="date netstarttime" dateFmt="yyyy-MM-dd" />--
							<input value="${endtime}" type="text" name="withdrawendtime"  class="date netendtime" dateFmt="yyyy-MM-dd" />
						</li>
					</ul>
					<ul>
						<li style="padding-left:38px;">
							
							<input type="button" name="today" value=" 今天 " onClick="MyDate(1,this);">
							<input type="button" name="yesteday" value=" 昨天 " onClick="MyDate(2,this);">
							<input type="button" name="week" value=" 本周 " onClick="MyDate(4,this);">
						
						</li>
					</ul>
					<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent"><button type="submit">检索</button></div>
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
						<!--<th><input type="checkbox" group="refundlist" name="refundlist" value="${order.id }" class="checkboxCtrl"></th> -->
						<th>用户id</th>
						<th>提现总金额</th>
						<th>成功总金额</th>
						<th>成功的条数</th>
						<th>处理中的条数</th>
						<th>失败的条数</th>
						<th>记录总条数</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${withdraw}" var="order">
						<tr>
							<td>${order.id}</td>
							<td>${order.allmoney}</td>
							<td>${order.successmoney}</td>
							<td>${order.successtotal}</td>
							<td>${order.dealtotal}</td>
							<td>${order.failtotal}</td>
							<td>${order.alltotal}</td>
							<td>${order.createtime}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	
	</body>
</html>