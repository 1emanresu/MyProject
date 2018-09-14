<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
   function emppersub(employeeid){
       $("#empper"+employeeid).submit() ;
   }
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/showEmp">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="empname" value="${empname }"/>
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/showEmp"
			onsubmit="return dwzSearch(this, 'dialog');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="id" value="${id}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px">
						<label>姓名：</label>
						<input type="text" value="${empname }" name="empname" />
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
		<table class="table" width="100%" layoutH="120" targetType="dialog">
			<thead>
				<tr>
					<th>员工编号</th>
					<th>账号</th>
					<th>部门</th>
					<th>手机号</th>
					<th>地址</th>
					<th>创建时间</th>
					<th>邮箱</th>
					<th>名称</th>
					<th>提成</th>
					<th>已经提成</th>
					<th>锁定状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="empid" rel="${order.employeeid }">
						<td><c:out value="${order.employeeid}"></c:out></td>
						<td><c:out value="${order.account}"></c:out></td>
						<td><c:out value="${order.powername}"></c:out></td>
						<td><c:out value="${order.phone}"></c:out></td>
						<td><c:out value="${order.address}"></c:out></td>
						<td><c:out value="${order.creationtime}"></c:out></td>
						<td><c:out value="${order.email}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.commission}"></c:out></td>
						<td><c:out value="${order.haveroyalty}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${order.iflock==1}">
								正常
							</c:when>
							<c:otherwise>
								锁定
							</c:otherwise>
						</c:choose></td>
						<td>
							<form id="empper${order.employeeid}" action="<%=adminbasePath %>/person/upEmpPerson" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
								<input type="hidden" name="id" value="${id }"/>
								<input type="hidden" name="empid" value="${order.employeeid}" />
								<a href="javascript:emppersub(${order.employeeid})" class="btnSelect">选择</a>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="dwzPageBreak({targetType:dialog, numPerPage:'10'})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" targetType="dialog"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>