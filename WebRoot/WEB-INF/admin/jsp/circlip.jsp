<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/circlip">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${numPerPage}" /> <input type="hidden"
			name="circstarttime" value="${circstarttime}" /> <input
			type="hidden" name="circendtime" value="${circendtime }" /> <input
			type="hidden" name="circparticipate" value="${circparticipate}" /> <input
			type="hidden" name="circstate" value="${circstate}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/circlip"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 400px"><label>日期：</label><input type="text"
						name="circstarttime" class="date" dateFmt="yyyy-MM-dd" /> --<input
						type="text" name="circendtime" class="date" dateFmt="yyyy-MM-dd" />
					</li>
					<li><label>支付方式：</label> <select class="combox"
						name="circparticipate">
							<option value="0">请选择</option>
							<c:forEach items="${cardcode}" varStatus="l" var="card">
								<option value="${card.codeid}"><c:out
										value="${card.payable}"></c:out></option>
							</c:forEach>
					</select></li>
					<li><label>订单状态：</label> <select class="combox"
						name="circstate" style="width: 100px">
							<option value="0">请选择</option>
							<option value="2000">成功</option>
							<option value="2001">处理中</option>
							<option value="3">失败</option>
					</select></li>
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
		<div class="panelBar">
			<!-- <ul class="toolBar">
				<li><a title="确实要删除这些记录吗?" target="ajaxTodo"
					href="demo/common/ajaxDone.html" class="delete"><span>删除</span></a></li>
				<li class="line">line</li>
				<li><a class="edit" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>修改</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="demo_page4.html?uid={orderid}"
					target="navTab"><span>查看</span></a></li>
			</ul> -->
			<h1 align="center" style="color: red;">订单总数：${totalCount}条
				交易金额：${circlipstatis.actual }（元） 实际金额：${circlipstatis.accountvalue }（元）
				收益金额：${circlipstatis.deducted }（元）</h1>
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>订单编号</th>
					<th>用户</th>
					<!-- <th>充值卡的卡号</th>
					<th>充值卡密码</th> -->
					<th>金额</th>
					<th>实际金额</th>
					<th>最后结算</th>
					<th>扣除金额</th>
					<th>订单流水号</th>
					<th>交易情况说明</th>
					<th>交易方式</th>
					<th>支付结果</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="circlipid" rel="${order.circlipid }">
						<td><c:out value="${order.circlipid}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<%-- <td><c:out value="${order.cardcode}"></c:out></td>
						<td><c:out value="${order.cardno}"></c:out></td> --%>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.actual}"></c:out></td>
						<td><c:out value="${order.accountvalue}"></c:out></td>
						<td><c:out value="${order.deducted}"></c:out></td>
						<td><c:out value="${order.billid}"></c:out></td>
						<td><c:out value="${order.info}"></c:out></td>
						<td><c:out value="${order.payable}"></c:out></td>
						<td><c:choose>
								<c:when test="${order.result==2000}">
								成功
							</c:when>
								<c:when test="${order.result==2001}">
								处理中
							</c:when>
								<c:when test="${order.result!=2000&&order.result!=2001}">
								失败
							</c:when>
							</c:choose></td>
						<td><c:out value="${order.datetime}"></c:out></td>
						<td>
							<!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a> -->
							<a title="查看订单详情" target="dialog" rel="circ${order.circlipid }"
							href="<%=adminbasePath %>/circlip/showCirclip?circlipid=${order.circlipid }"
							class="btnView" width="900" height="370">查看订单详情</a>
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