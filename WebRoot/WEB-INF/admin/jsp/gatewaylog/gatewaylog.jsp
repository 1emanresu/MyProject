<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/gatewayLog">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="gateway_id" value="${gateway_id}" />
		<input type="hidden" name="starttime" value="${starttime}" />
		<input type="hidden" name="endtime" value="${endtime}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/gatewayLog"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>通道编号：</label>
						<input type="text" name="gateway_id" value="${gateway_id }"/>
					</li>
					<li style="width: 380px"><label style="width: 25px">日期</label>
						<input value="${starttime}" type="text" name="starttime"  class="date netstarttime" dateFmt="yyyy-MM-dd" />--
						<input value="${endtime}" type="text" name="endtime"  class="date netendtime" dateFmt="yyyy-MM-dd" />
						<ul style="position:absolute;top:30px;">
							<li>
								<input type="button" name="today" value=" 今天 " onClick="MyDate(1,this);">
								<input type="button" name="yesteday" value=" 昨天 " onClick="MyDate(2,this);">
								<input type="button" name="week" value=" 本周 " onClick="MyDate(4,this);">
							</li>
						</ul>
					</li>
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
		<table class="table" width="1150" layoutH="138">
			<thead>
				<tr>
					<th>通道编号</th>
					<th>通道名称</th>
					<th>通道的金额</th>
					<th>通道费率金额</th>
					<th>支付宝金额</th>
					<th>支付宝费率金额</th>
					<th>微信金额</th>
					<th>微信费率金额</th>
					<th>qq金额</th>
					<th>qq费率金额</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${gatewayLog}" var="log">
					<tr>
						<td>${log.gateway_id }</td>
						<td>${log.gateway_name }</td>
						<td>${log.gateway_amount }</td>
						<td>${log.gateway_rate_amount }</td>
						<td>${log.ali_amount }</td>
						<td>${log.ali_rate_amount }</td>
						<td>${log.wx_amount }</td>
						<td>${log.wx_rate_amount }</td>
						<td>${log.qq_amount }</td>
						<td>${log.qq_rate_amount }</td>
						<td>${log.create_time }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20" <c:if test="${numPerPage==20 }">selected</c:if>>20</option>
					<option value="50" <c:if test="${numPerPage==50 }">selected</c:if>>50</option>
					<option value="100" <c:if test="${numPerPage==100 }">selected</c:if>>100</option>
					<option value="200" <c:if test="${numPerPage==200 }">selected</c:if>>200</option>
				</select> <span>条，共${totalCount}条</span>	
						
			</div>

			<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>