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
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/showLog" onsubmit="return divSearch(this, 'jbsxBox');">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="logstarttime" value="${logstarttime}" />
		<input type="hidden" name="logendtime" value="${logendtime }" />
		<input type="hidden" name="id" value="${id}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/showLog"
			onsubmit="return divSearch(this, 'jbsxBox');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="id" value="${id}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px"><label>日期：</label><input value="${logstarttime}" type="text" name="logstarttime" class="date" dateFmt="yyyy-MM-dd"/>
					--<input value="${logendtime }" type="text" name="logendtime" class="date" dateFmt="yyyy-MM-dd"/>
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
					<th>用户</th>
					<th>登陆ip</th>
					<th>登录时间</th>
					<th>退出时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="logid" rel="${order.logid }">
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.landingip}"></c:out></td>
						<td><c:out value="${order.landingtime}"></c:out></td>
						<td><c:out value="${order.exittime}"></c:out></td>
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