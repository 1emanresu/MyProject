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
	function userprofit(){
		$.ajax({
        	url:"<%=adminbasePath%>/statement/profit?statement_time=${statement_time}",
            type:"get",            
            dataType:"json",
            success:function(data){
            	alert(data);
            	
            }
        });
	}
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/statement/showGateway">
		<input type="hidden"name="statement_time" value="${statement_time }" />
		<input type="hidden"name="gateway_id" value="${gateway_id}" />
	</form>
	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/statement/showGateway"
			onsubmit="return navTabSearch(this)" method="post">
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 210px"><label style="width: 40px">通道id:</label>
					<input type="text" value="${gateway_id}" name="gateway_id"/></li>
					<li style="width: 420px">统计日期:
					<input value="${statement_time}" type="text" name="statement_time"
						id="gateway_statement_time" class="date" dateFmt="yyyy-MM-dd" /></li>
				</ul>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<a title="确实要更新补单吗?" target="ajaxTodo" href="<%=adminbasePath%>/statement/profit?statement_time=${statement_time}">更新补单</a>
								</div>
							</div>
						</li>
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
					<th>通道id</th>
					<th>通道名称</th>
					<th>结算金额</th>
					<th>平台利润</th>
					<th>总成交金额</th>
					<th>金额成功率</th>
					<th>成功总条数</th>
					<th>订单成功率</th>
					<th>用户id</th>
					<th>用户名称</th>
					<th>结算金额</th>
					<th>平台利润</th>
					<th>成交金额</th>
					<th>金额成功率</th>
					<th>成功条数</th>
					<th>订单成功率</th>
					<th>补单条数</th>
					<th>补单金额</th>
					<th>补单结算金额</th>
				</tr>
			</thead>
			<tbody>
				${table_html}
			</tbody>
		</table>
	</div>
</body>
</html>