﻿<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$("#refustate option[value='${refustate}']").attr("selected", true);
	$("#settlementauthority option[value='${settlementauthority}']").attr("selected", true);
	$("#bankType option[value='${bankType}']").attr("selected", true);
	
	function upload(){
		var url = "<%=basePath%>/settlement/showExpor";
		var userid = document.getElementById("userid").value;
		var refustarttime = document.getElementById("refustarttime").value;
		var refuendtime = document.getElementById("refuendtime").value;
		var refustate = document.getElementById("refustate").value;
		var accountname = document.getElementById("accountname").value;	
		var bankType = $("#bankType").val();
		url = url + "?id=" + userid + "&refustarttime=" + refustarttime
				+ "&refuendtime=" + refuendtime + "&refustate=" + refustate+ "&accountname=" + accountname+"&bankType="+bankType;
		window.location.href = encodeURI(url);
	}
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/refund">
		<input type="hidden"name="pageNum" value="1" /> 
		<input type="hidden"name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden"name="refustarttime" value="${refustarttime}" /> 
		<input type="hidden"name="refuendtime" value="${refuendtime }" /> 
		<input type="hidden"name="refustate" value="${refustate}" /> 
		<input type="hidden"name="accountname" value="${accountname}" /> 
		<input type="hidden"name="userid" value="${id}" /> 
		<input type="hidden"name="bankType" value="${bankType}" /> 
		<input type="hidden"name="settlementauthority"value="${settlementauthority}" />
	</form>
	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/refund"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent" style="height:auto;">
					<li style="width: 210px"><label style="width: 40px">姓名:</label>
					<input type="text" value="${accountname}" name="accountname" id="accountname" /></li>
				    <li style="width: 210px"><label style="width: 40px">用户id:</label>
						<input type="text" value="${id}" name="id" id="userid"/>
					</li>	
					<li style="width:360px">日期:
						<input value="${refustarttime}" type="text" name="refustarttime" id="refustarttime"  class="date netstarttime" dateFmt="yyyy-MM-dd" /> 
						<input value="${refuendtime }" type="text" name="refuendtime" id="refuendtime" class="date netendtime" dateFmt="yyyy-MM-dd" />
						<ul style="position:absolute;top:30px;">
							<li>
								<input type="button" name="today" value=" 今天 " onClick="MyDate(1,this);">
								<input type="button" name="yesteday" value=" 昨天 " onClick="MyDate(2,this);">
								<input type="button" name="week" value=" 本周 " onClick="MyDate(4,this);">
							</li>
						</ul>
					</li>
					<li style="width:280px"><label>银行名称:</label>
						<select name="bankType" id="bankType">
							<option value="0">请选择</option>
							<c:forEach items="${bankList }" var="bank">
								<option value="${bank.net_name }">${bank.net_name }</option>
							</c:forEach>
						</select>
<!-- 						<input type="text" name="bankType" id="bankType" value="${bankType}"/> -->
					</li>
					<li style="width: 200px"><label>提现权限:</label> <select
						class="combox" name="settlementauthority" id="settlementauthority"
						style="width: 100px">
							<option value="0">请选择</option>
							<option value="1">T+0</option>
							<option value="2">T+1</option>
					</select></li>
					<li style="width:200px;"><label>订单状态：</label> <select
						class="combox" name="refustate" id="refustate"
						style="width: 100px">
							<option value="0">请选择</option>
							<option value="1">成功</option>
							<option value="2">处理中</option>
							<option value="3">失败</option>
					</select></li>
				</ul>
				<div class="subBar">
					<ul>
						<li>
							<button type="button" onClick="upload()">下载记录</button>
						</li>
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
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="edit" title="确实要对这些记录进行操作吗?"
					target="selectedTodo" rel="refundlist"
					href="<%=adminbasePath%>/refund/refundDeductions"><span>批量成功</span></a></li>
				<li><a class="edit" title="确实要对这些记录进行操作吗?"
					target="selectedTodo" rel="refundlist"
					href="<%=adminbasePath%>/refund/refundDeductionsfail"><span>批量失败</span></a></li>
				<li><a class="edit" title="操作"
					href="<%=adminbasePath%>/refund/showRefund?refuid={refund_id}"
					width="850" height="480" mask=true target="dialog" rel="uprefund"><span>操作</span></a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>查看</span></a></li> -->
			</ul>
		</div>
		<table class="table" width="1200" layoutH="165">
			<thead>
				<tr>
					<th><input type="checkbox" group="refundlist"
						name="refundlist" value="${order.refund_id}"  class="checkboxCtrl"></th>
					<th>用户id</th>
					<th>提现账号</th>
					<th>开户姓名</th>
					<th>提现金额</th>
					<th>银行名称</th>
					<th>开户省名</th>
					<th>开户市名</th>
					<th>开户支行</th>
					<th>提现状态</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<c:if test="${order.refund_state==1}">
						<tr target="refund_id" rel="${order.refund_id }"
							style='background: #4169E1'>
					</c:if>
					<c:if test="${order.refund_state!=1}">
						<tr target="refund_id" rel="${order.refund_id }">
					</c:if>
					<td><c:if test="${order.refund_state == 2}">
							<input name="refundlist" type="checkbox"
								value="${order.refund_id }">
						</c:if></td>
					<td><c:out value="${order.id}"></c:out></td>
					<td><c:out value="${order.account}"></c:out></td>
					<td><c:out value="${order.account_name}"></c:out></td>
					<td><c:out value="${order.refund_amount}"></c:out></td>
					<td><c:out value="${order.net_name}"></c:out></td>
					<td><c:out value="${order.branchsheng}"></c:out></td>
					<td><c:out value="${order.branchshi}"></c:out></td>
					<td><c:out value="${order.branch}"></c:out></td>
					<td><c:if test="${order.refund_state==1}">
							成功
						</c:if> <c:if test="${order.refund_state==2}">
							处理中
						</c:if> <c:if test="${order.refund_state==3}">
							失败
						</c:if>
					</td>
					<td><c:out value="${order.createtime}"></c:out></td>
					
					<td>
						<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a> -->
						<a class="btnEdit" title="编辑"
						href="<%=adminbasePath%>/refund/showRefund?refuid=${order.refund_id }"
						width="850" height="480" mask=true target="dialog"
						rel="refu${order.refund_id }"><span>编辑</span></a> <!-- <a title="查看" target="navTab" href="#" class="btnView">查看</a> -->
					</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>