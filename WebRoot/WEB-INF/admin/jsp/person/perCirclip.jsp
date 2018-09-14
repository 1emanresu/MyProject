<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#circparticipate option[value='${circparticipate}']").attr("selected", true);
$("#circstate option[value='${circstate}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/showCirclip" onsubmit="return divSearch(this, 'jbsxBox');">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="circstarttime" value="${circstarttime}" />
		<input type="hidden" name="circendtime" value="${circendtime }" />
		<input type="hidden" name="circparticipate" value="${circparticipate}" />
		<input type="hidden" name="circstate" value="${circstate}" />
		<input type="hidden" name="id" value="${id}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/showCirclip"
			onsubmit="return divSearch(this, 'jbsxBox');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<input type="hidden" name="id" value="${id}" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px"><label>日期：</label><input type="text" name="circstarttime" value="${circstarttime }" class="date" dateFmt="yyyy-MM-dd"/>
					--<input type="text" name="circendtime" value="${circendtime }" class="date" dateFmt="yyyy-MM-dd"/>
					</li>
					<li style="width: 250px"><label>支付方式：</label>
						<select class="combox" name="circparticipate" id="circparticipate">
						<option value="0">请选择</option>
						<c:forEach items="${cardcode}" varStatus="l" var="card">
							<option value="${card.codeid}"><c:out
									value="${card.payable}"></c:out></option>
						</c:forEach>
						</select>
					</li>
					<li style="width: 200px"><label>订单状态：</label>
						<select class="combox" name="circstate" id="circstate" style="width: 100px">
							<option value="0">请选择</option>
							<option value="2000">成功</option>
							<option value="2001">处理中</option>
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
					<th>卡类</th>
					<th>充值卡号</th>
					<th>充值卡密码</th>
					<th>殴飞订单号</th>
					<th>金额</th>
					<th>实际金额</th>
					<th>最后结算</th>
					<th>支付结果</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="circlipid" rel="${order.circlipid }">
						<td><c:out value="${order.circlipid}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.payable}"></c:out></td>
						<td><c:out value="${order.cardno}"></c:out></td>
						<td><c:out value="${order.cardpass}"></c:out></td>
						<td><c:out value="${order.billid}"></c:out></td>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.actual}"></c:out></td>
						<td><c:out value="${order.accountvalue}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${order.result==2000}">
								成功
							</c:when>
							<c:when test="${order.result==2001}">
								处理中
							</c:when>
							<c:otherwise>
								失败
							</c:otherwise>
						</c:choose></td>
						<td><c:out value="${order.datetime}"></c:out></td>
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