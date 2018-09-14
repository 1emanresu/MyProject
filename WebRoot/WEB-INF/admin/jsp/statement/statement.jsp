<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$("#refustate option[value='${refustate}']").attr("selected", true);
	$("#settlementauthority option[value='${settlementauthority}']").attr(
			"selected", true);
	
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/statement">
		<input type="hidden"name="pageNum" value="1" />
		<input type="hidden"name="numPerPage" value="${numPerPage}" />
		<input type="hidden"name="statementStarttime" value="${statementStarttime}" />
		<input type="hidden"name="statementEndtime" value="${statementEndtime }" />
		<input type="hidden"name="user_id" value="${user_id}" />
		<input type="hidden"name="gateway_id" value="${gateway_id}" />
	</form>
	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/statement"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
				    <li style="width: 210px"><label style="width: 40px">用户id:</label>
					<input type="text" value="${user_id}" name="user_id"/></li>	
					<li style="width: 210px"><label style="width: 40px">通道id:</label>
					<input type="text" value="${gateway_id}" name="gateway_id"/></li>
					<li style="width: 420px">统计日期:
					<input value="${statementStarttime}" type="text" name="statementStarttime"
						id="statementStarttime" class="date" dateFmt="yyyy-MM-dd" />--
					<input value="${statementEndtime }" type="text" name="statementEndtime"
						id="statementEndtime" class="date" dateFmt="yyyy-MM-dd" /></li>
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
					<th>通道id</th>
					<th>订单金额</th>
					<th>成交金额</th>
					<th>金额成功率</th>
					<th>订单条数</th>
					<th>成功条数</th>
					<th>订单成功率</th>
					<th>补单条数</th>
					<th>补单金额</th>
					<th>更新者</th>
					<th>统计时间</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dayReports}" var="reports">
					<tr>
						<td>${reports.user_id}</td>
						<td>${reports.gateway_id}</td>
						<td>${reports.all_amount}</td>
						<td>${reports.deal_amount}</td>
						<td>${reports.amounts_rate}</td>
						<td>${reports.all_order}</td>
						<td>${reports.deal_order}</td>
						<td>${reports.order_rate}</td>
						<td>${reports.additional_order}</td>
						<td>${reports.additional_amount}</td>
						<td>${reports.update_user}</td>
						<td>${reports.date}</td>
						<td>${reports.create_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span><select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select><span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>