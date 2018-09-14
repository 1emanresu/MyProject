<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#rechparticipate option[value='${rechparticipate}']").attr("selected", true);
$("#rechstate option[value='${rechstate}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/recharge">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="rechid" value="${rechid}" />
		<input type="hidden" name="rechname" value="${rechname}" />
		<input type="hidden" name="rechstarttime" value="${rechstarttime}" />
		<input type="hidden" name="rechendtime" value="${rechendtime }" />
		<input type="hidden" name="rechparticipate" value="${rechparticipate}" />
		<input type="hidden" name="rechstate" value="${rechstate}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/recharge"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<ul class="searchContent">
					<li style="width: 210px"><label style="width: 40px">订单：</label><input type="text" value="${rechid}" name="rechid"/></li>
					<li style="width: 210px"><label style="width: 40px">用户：</label><input type="text" value="${rechname}" name="rechname"> </li>
					<li style="width: 360px"><label style="width: 40px">日期：</label><input type="text" name="rechstarttime" value="${rechstarttime }" class="date" dateFmt="yyyy-MM-dd"/>
					--<input type="text" name="rechendtime" value="${rechendtime }" class="date" dateFmt="yyyy-MM-dd"/>
					</li>
					<li style="width: 210px"><label style="width: 60px">支付方式：</label>
						<select class="combox" name="rechparticipate" id="rechparticipate" style="width: 200px">
						<option value="0">请选择</option>
						<c:forEach items="${partici}" varStatus="l" var="par">
							<option value="${par.codeid}"><c:out
									value="${par.payable}"></c:out></option>
						</c:forEach>
						</select>
					</li>
					<li style="width: 160px"><label style="width: 60px">订单状态：</label>
						<select class="combox" name="rechstate" id="rechstate" style="width: 60px">
							<option value="0">请选择</option>
							<option value="1">成功</option>
							<option value="2">处理中</option>
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
			<h1 align="center" style="color: red;">订单总数：${totalCount}条                       交易金额：${recharge.p3_Amt }（元）                            实际金额：${recharge.amount }（元）                           收益金额：${recharge.deducted }（元）</h1>
		</div>
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					<th>订单编号</th>
					<th>用户</th>
					<th>支付金额</th>
					<th>实际金额</th>
					<th>商品名称</th>
					<th>交易方式</th>
					<th>ip</th>
					<th>支付结果</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderlist}" var="order">
					<tr target="rechargeid" rel="${order.rechargeid }">
						<td><c:out value="${order.rechargeid}"></c:out></td>
						<td><c:out value="${order.name}"></c:out></td>
						<td><c:out value="${order.p3_Amt}"></c:out></td>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.p5_Pid}"></c:out></td>
						<td><c:out value="${order.payable}"></c:out></td>
						<td><c:out value="${order.ip}"></c:out></td>
						<td><c:choose>
								<c:when test="${order.r1_Code==1}">
								成功
							</c:when>
								<c:when test="${order.r1_Code==2}">
								处理中
							</c:when>
								<c:when test="${order.r1_Code==3}">
								失败
							</c:when>
								<c:when test="${order.r1_Code==4}">
								失效
							</c:when>
							</c:choose></td>
						<td><c:out value="${order.CreateTime}"></c:out></td>
						<td><!-- <a title="删除" target="ajaxTodo" href="#" class="btnDel">删除</a>
							<a title="编辑" target="navTab" href="#" class="btnEdit">编辑</a>  -->
							<a title="查看订单详情" target="dialog" rel="rech${order.rechargeid }"
							href="<%=adminbasePath %>/recharge/showRech?rechid=${order.rechargeid }"
							class="btnView" width="800" height="500">查看订单详情</a></td>
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