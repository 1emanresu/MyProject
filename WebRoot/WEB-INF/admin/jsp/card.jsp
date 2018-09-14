<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
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
$("#cardparticipate option[value='${cardparticipate}']").attr("selected", true);
$("#cardstate option[value='${cardstate}']").attr("selected", true);
$("#cardlock option[value='${cardlock}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/card/">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden" name="cardorderid" value="${cardorderid}" /> 
		<input type="hidden" name="cardname" value="${cardname}" /> 
		<input type="hidden" name="cardstarttime" value="${cardstarttime}" /> 
		<input type="hidden" name="cardendtime" value="${cardendtime }" /> 
		<input type="hidden" name="cardparticipate" value="${cardparticipate}" /> 
		<input type="hidden" name="cardstate" value="${cardstate}" /> 
		<input type="hidden" name="cardlock" value="${cardlock}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/card/"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>订单：<input type="text" name="cardorderid"
							value="${cardorderid}"></td>
						<td>用户：<input type="text" name="cardname"
							value="${cardname }"></td>
						<td>日期：<input type="text" name="cardstarttime"
							value="${cardstarttime }" class="date" dateFmt="yyyy-MM-dd" />
							--<input type="text" name="cardendtime" value="${cardendtime }"
							class="date" dateFmt="yyyy-MM-dd" /></td>
						<td><select class="combox" name="cardparticipate" id="cardparticipate">
						<option value="0">请选择</option>
						<c:forEach items="${cardcode}" varStatus="l" var="card">
							<option value="${card.codeid}"><c:out
									value="${card.payable}"></c:out></option>
						</c:forEach>
						</select></td>
						<td><select class="combox" name="cardstate" id="cardstate"
							style="width: 100px">
								<option value="0">提交状态</option>
								<option value="1">成功</option>
								<option value="2">失败</option>
						</select></td>
						<%
							//if(employeeid==1){
						%>
						<td><select class="combox" name="cardlock" id="cardlock"
							style="width: 100px">
								<option value="0">扣单状态</option>
								<option value="1">正常</option>
								<option value="2">被扣单</option>
						</select></td>
						<%
							//}
						%>
					</tr>
				</table>
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
		<div class="panelBar">
			<%
				//if(employeeid==1){
			%>
			<ul class="toolBar">
				<li><a title="确实要对这些记录进行扣单吗?" target="selectedTodo"
					rel="cardlist" href="<%=adminbasePath%>/card/cardDeductions"
					class="delete"><span>批量扣单</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要对此记录进行扣单吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/card/cardDeduction?cardorderid={orderid}"
					class="delete"><span>扣单</span></a></li>
				<!-- <li><a title="确实要删除这些记录吗?" target="ajaxTodo"
					href="demo/common/ajaxDone.html" class="delete"><span>删除</span></a></li>
				<li class="line">line</li>
				<li><a class="edit" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>修改</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>查看</span></a></li> -->
			</ul>
			<%
				//}
			%>
			<h1 align="center" style="color: red;">订单总数：${totalCount}条
				交易金额：${carstatis.p3_Amt }（元） 实际金额：${carstatis.amount }（元）
				收益金额：${carstatis.deducted }（元）</h1>
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th><input type="checkbox" group="cardlist"
						class="checkboxCtrl"></th>
					<th>订单编号</th>
					<th>用户</th>
					<th>商户订单编号</th>
					<th>支付金额</th>
					<th>实际金额</th>
					<th>交易方式</th>
					<th>ip</th>
					<th>提交状态</th>
					<th>是否反馈</th>
					<%
						//if(employeeid==1){
					%>
					<th>是否扣单</th>
					<%
						//}
					%>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="orderid" rel="${order.orderid }">
						<td><input name="cardlist" value="${order.orderid }"
							type="checkbox"></td>
						<td><c:out value="${order.orderid}"></c:out></td>
						<td><c:out value="${order.p1_MerId}"></c:out></td>
						<td><c:out value="${order.p2_Order}"></c:out></td>
						<td><c:out value="${order.p3_Amt}"></c:out></td>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.pd_FrpId}"></c:out></td>
						<td><c:out value="${order.ip}"></c:out></td>
						<td><c:choose>
								<c:when test="${order.r1_Code==1}">提交成功</c:when>
								<c:otherwise>提交失败</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${order.success==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose></td>
						<%
							//if(employeeid==1){
						%>
						<td><c:choose>
								<c:when test="${order.lock==1}">正常</c:when>
								<c:when test="${order.lock==2}">扣单</c:when>
							</c:choose></td>
						<%
							//}
						%>
						<td><c:out value="${order.CreateTime}"></c:out></td>
						<td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a> <a
							title="查看" target="navTab" href="#" class="btnView">查看</a> --> <a mask=true
							title="查看订单详情" target="dialog" rel="card${order.orderid }"
							href="<%=adminbasePath %>/card/showCard?cardid=${order.orderid }"
							class="btnView" width="800" height="560">查看订单详情</a> <a mask=true
							title="查看点卡订单详情" target="dialog" rel="sing${order.orderid }"
							href="<%=adminbasePath %>/card/showSinglist?cardid=${order.orderid }"
							class="btnView" width="1200" height="400">查看点卡订单详情</a> 
							<%
								//if(employeeid==1){
							%>
							<c:if
								test="${order.lock==2 }">
								<a title="确实要对此记录进行恢复吗吗?" target="ajaxTodo"
									href="<%=adminbasePath%>/card/cardRecovery?cardorderid=${order.orderid }"
									class="btnEdit">恢复订单</a>
							</c:if>
							<%
								//}
							%>
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
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>