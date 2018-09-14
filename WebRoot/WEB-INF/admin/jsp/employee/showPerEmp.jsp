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
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/employee/showPerEmp">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="pername" value="${pername }"/>
		<input type="hidden" name="empid" value="${empid }"/>
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/employee/showPerEmp"
			onsubmit="return dwzSearch(this, 'dialog');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="empid" value="${empid }"/>
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px">
						<label>姓名：</label>
						<input type="text" value="${pername }" name="pername" />
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
					<th>商户id</th>
					<th>邮箱</th>
					<th>商户类型</th>
					<th>名称</th>
					<th>联系人名称</th>
					<th>手机号码</th>
					<th>QQ号码</th>
					<th>注册时间</th>
					<th>登陆状态</th>
					<th>锁定状态</th>
					<th>网银状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="per">
					<tr target="id" rel="${per.id }">
						<td><c:out value="${per.id}"></c:out></td>
						<td><c:out value="${per.email}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${per.huge==1}">
								企业
							</c:when>
							<c:otherwise>
								个人
							</c:otherwise>
						</c:choose></td>
						<td>
							<c:choose>
								<c:when test="${per.name==''}">
									未选择
								</c:when>
								<c:otherwise>
									<c:out value="${per.name}"></c:out>
								</c:otherwise>
							</c:choose>
						</td>
						<td><c:out value="${per.contacts}"></c:out></td>
						<td><c:out value="${per.phone}"></c:out></td>
						<td><c:out value="${per.qq}"></c:out></td>
						<td><c:out value="${per.time}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${per.iflogin==1}">
								登陆
							</c:when>
							<c:otherwise>
								未登录
							</c:otherwise>
						</c:choose></td>
						<td>
						<c:choose>
							<c:when test="${per.iflock==1}">
								正常
							</c:when>
							<c:otherwise>
								锁定
							</c:otherwise>
						</c:choose></td>
						<td>
						<c:choose>
							<c:when test="${per.ifnet==1}">
								开通
							</c:when>
							<c:otherwise>
								未开通
							</c:otherwise>
						</c:choose></td>
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