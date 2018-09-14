<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
.pad_0_10{padding:0 10px;}
</style>

<script type="text/javascript">
	$("#netparticipate option[value='${netparticipate}']").attr("selected", true);
	$("#netstate option[value='${netstate}']").attr("selected", true);
	$("#netlock option[value='${netlock}']").attr("selected", true);
	

</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/userRequestLog">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		 <input type="hidden" name="netorderid" value="${netorderid}" />
		 <input type="hidden" name="netstarttime" value="${netstarttime}" />
		 <input type="hidden" name="netendtime" value="${netendtime}" />
		 <input type="hidden" name="netemployeeid" value="${netemployeeid}" />
		 <input type="hidden" name="uname" value="${uname}">
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/userRequestLog" onsubmit="return navTabSearch(this);" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="20" />
			
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>编号：<input type="text" name="netid" value="${netid}"></td>
						<td>用户id：<input type="text" name="netemployeeid" value="${netemployeeid }"></td>
						<td>日期：
								
							<input type="text"  name="netstarttime" value="${netstarttime }" class="date netstarttime" dateFmt="yyyy-MM-dd" /> --
							<input type="text" name="netendtime" value="${netendtime }" class="date netendtime" dateFmt="yyyy-MM-dd" />

						</td>
						<td>
							<div>
								<input type="button" name="today" value=" 今天 " onClick="MyDate(1,this);">
								<input type="button" name="yesteday" value=" 昨天 " onClick="MyDate(2,this);">
								<input type="button" name="week" value=" 本周 " onClick="MyDate(4,this);">
							</div>
						</td>
						
						
					</tr>
					
					<tr>
						<td>名称：<input type="text" name="uname" value="${uname}"></td>
						<td>管理员名称：<input type="text" name="name" value="${name}"></td>
						<td></td>
						<td></td>
						
						
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="return validaData();">检索</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
	
		<table class="table" width="1160" layoutH="110">
			<thead>
				<tr>
					<th>编号</th>
					<th>用户id</th>
					<th>用户名称</th>
					<th>时间</th>
					<th>地址名称</th>
					<th>请求地址</th>
					<th>请求参数</th>
					
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${logs}" var="log">
					<tr target="orderid" rel="${log.id}">
						<td class="wid_100"><c:out value="${log.id}"></c:out></td>
						<td  class="wid_100"><c:out value="${log.employee_id}"></c:out></td>
						<td  class="wid_100"><c:out value="${log.name}"></c:out></td>
						<td class="wid_260"><c:out value="${log.date}"></c:out></td>
						<td class="wid_100"><c:out value="${log.uname}"></c:out></td>
						<td class=""><c:out value="${log.url}"></c:out></td>
						<td style="width:400px;"><c:out value="${log.para}"></c:out></td>
					</tr>
				</c:forEach>
				<tr><td colspan="7">&nbsp;</td></tr>
				<tr>
					<td colspan="7">
						<div class="panelBar">
							<div class="pages">
								<span>显示</span>
								<select class="combox" name="numPerPage"
									onchange="navTabPageBreak({numPerPage:this.value})">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select><span>条，共${totalCount}条</span>
							</div>
							<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}"currentPage="${pageNum}"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	

</body>
</html>