<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
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

function Cleanup() {  
    window.clearInterval(idTmr);  
    CollectGarbage();  
}  
function zuotian(){
	 
	    var zuo=GetDateStr(-1); 
	    var jin=GetDateStr(0); 
	    $('input[name="starttime"]').val(zuo);
	    $('input[name="endtime"]').val(jin);
	  
}
function jintian(){
	
	    var jin =GetDateStr(0);
	    var	ming=GetDateStr(1);
	    $('input[name="starttime"]').val(jin);
	    $('input[name="endtime"]').val(ming);
	
}
function GetDateStr(AddDayCount) { 
var dd = new Date(); 
dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
var y = dd.getFullYear(); 
var m = dd.getMonth()+1;//获取当前月份的日期 
var d = dd.getDate(); 
return y+"-"+m+"-"+d; 
} 
function upload(){
	var url = "<%=basePath%>/settlement/showExpor1";
	
	var starttime = $('input[name="starttime"]').val();
	var endtime = $('input[name="endtime"]').val();
	var btn = $('input[name="btn"]').val();

	url = url + "?starttime=" + starttime + "&endtime=" + endtime
			+ "&btn=" + btn;
	
	this.location.href=url;
}
</script>
</head>
<body>
	<div class="pageHeader">
		<form action="<%=adminbasePath%>/gateway/getEmployeeGetAll"
			onsubmit="return navTabSearch(this)" method="post">
			<div class="searchBar">
				<table class="searchContent">
					<tr>

						<td>日期：<input type="text" name="starttime"
							value="${starttime}" class="date" dateFmt="yyyy-MM-dd" /> --<input
							type="text" name="endtime" value="${endtime}" class="date"
							dateFmt="yyyy-MM-dd" /> --<input type="button" value="昨天"
							onclick='zuotian()' /> --<input type="button" value="今天"
							onclick='jintian()' /></td>
						<td>用戶id:<input name="btn" type="text" value="${btn}" />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent" id="ta">
		<h1 style="margin: 0 30%">通道金额统计 开始日期 ${starttime} 结束日期
			${endtime}</h1>

		<table class="table" width="1200" layoutH="138">

			<thead>

				<tr>
					<th>用户编号</th>
					<th>用户名称</th>
					<th>交易金额</th>
					<th>实际金额</th>
					<th>收入</th>
					<th>支付宝交易金额</th>
					<th>支付宝实际金额</th>
					<th>微信交易金额</th>
					<th>微信实际金额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist1}" var="order">
					<tr>
						<td><c:out value="${order.id}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.amt}"></c:out></td>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.deducted}"></c:out></td>
						<td><c:out value="${order.aliamt}"></c:out></td>
						<td><c:out value="${order.aliamo}"></c:out></td>

						<td><c:out value="${order.wxamt}"></c:out></td>
						<td><c:out value="${order.wxamo}"></c:out></td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<input type="button" value="导出EXCEL" onclick="javascript:upload()" />

</body>
</html>