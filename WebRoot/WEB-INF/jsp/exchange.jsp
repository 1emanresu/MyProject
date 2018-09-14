<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage.jsp"></jsp:include>
<script src="<%=basePath%>/js/ajaxs/order.js"></script>
<script type="text/javascript">
	var page = 1;
	var cardpage = 1;
	$(document).ready(function() {
		net(page);
		card(cardpage);
	});
	function net(pages) {
		var netstarttime = $("#netstarttime").val();
		var netendtime = $("#netendtime").val();
		var netparticipate = $("#netparticipate").val();
		var netstate = $("#netstate").val();
		var netid = $("#netid").val();
		wangyin(basePath, pages, netstarttime, netendtime, netparticipate,
				netstate, netid);
	}
	function card(pages) {
		var cardstarttime = $("#cardstarttime").val();
		var cardendtime = $("#cardendtime").val();
		var cardparticipate = $("#cardparticipate").val();
		var cardstate = $("#cardstate").val();
		var cardid = $("#cardid").val();
		cards(basePath, pages, cardstarttime, cardendtime, cardparticipate,
				cardstate, cardid);
	}
	function showcard(singid) {
		sing(basePath, singid);
		$('#cardmodal').modal('show');
	}
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<!-- topbar starts -->
	<jsp:include page="../page/top.jsp"></jsp:include>
	<!-- topbar ends -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- left menu starts -->
			<jsp:include page="../page/left.jsp"></jsp:include>
			<!-- left menu ends -->

			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href="#" onclick="exchange()">交易管理</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-retweet"></i> 交易记录
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<ul class="nav nav-tabs" id="myTab">
								<li class="active"><a href="#info">网银订单</a></li>
								<li><a href="#custom">点卡订单</a></li>
								<li><a href="#messages">对接编码</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active" id="info">
									<div class="row-fluid">
										<div class="span3">
											订单编号：<input style="width: 150px" name="netid" id="netid"
												type="text" Placeholder="订单编号" value=""/>
										</div>
										<div class="span3">
											日期：<input name="netstarttime" id="netstarttime"
												style="width: 70px" type="text" class="datepicker" Placeholder="开始时间" value="">
											-<input name="netendtime" id="netendtime" style="width: 70px"
												type="text" class="datepicker" Placeholder="结束时间" value="">
										</div>
										<div class="span2">
											支付方式：<select name="netparticipate" id="netparticipate"
												style="width: 85px">
												<option value="0">请选择</option>
												<c:forEach items="${partici}" varStatus="l" var="par">
													<option value="${par.codeid}"><c:out
															value="${par.payable}"></c:out></option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											订单状态：<select name="netstate" id="netstate"
												style="width: 80px">
												<option value="0">请选择</option>
												<option value="1">成功</option>
												<option value="2">处理中</option>
												<option value="3">失败</option>
												<option value="4">失效</option>
											</select>
										</div>
										<div class="span2">
											<input class="btn btn-small btn-primary" type="button"
												onclick="net(1)" value="查询">
										</div>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>订单号</th>
												<th>订单日期</th>
												<th>交易方式</th>
												<th>来源</th>
												<th>金额(元)</th>
												<th>状态</th>
												<th>同步</th>
												<th>通知</th>
											</tr>
										</thead>
										<tbody id="net">
										</tbody>
									</table>
									<div class="pagination pagination-centered">
										<ul>
											<li class="active" id="netprev"><a href="#"
												onclick="net(0)" id="netprev">上一页</a></li>
											<li class="active"><a href="#" id="netnum">1</a></li>
											<li id="netnext"><a href="#" onclick="net(2)"
												id="netnext">下一页</a></li>
										</ul>
									</div>
								</div>
								<div class="tab-pane" id="custom">
									<div class="row-fluid">
										<div class="span3">
											订单编号：<input style="width: 150px" name="cardid" id="cardid"
												type="text" Placeholder="订单编号" />
										</div>
										<div class="span3">
											日期：<input name="cardstarttime" id="cardstarttime"
												style="width: 70px" type="text" class="datepicker" Placeholder="开始时间">
											-<input name="cardendtime" id="cardendtime"
												style="width: 70px" type="text" class="datepicker" Placeholder="结束时间">
										</div>
										<div class="span2">
											支付方式：<select name="cardparticipate" id="cardparticipate"
												style="width: 85px">
												<option value="0">请选择</option>
												<c:forEach items="${cardcode}" varStatus="l" var="card">
													<option value="${card.codeid}"><c:out
															value="${card.payable}"></c:out></option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											订单状态：<select name="cardstate" id="cardstate"
												style="width: 80px">
												<option value="0">请选择</option>
												<option value="1">成功</option>
												<option value="2">处理中</option>
												<option value="3">失败</option>
											</select>
										</div>
										<div class="span2">
											<input class="btn btn-small btn-primary" type="button"
												onclick="card(1)" value="查询">
										</div>
									</div>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>订单号</th>
												<th>订单日期</th>
												<th>交易方式</th>
												<th>来源</th>
												<th>金额(元)</th>
												<th>状态</th>
												<th>同步</th>
												<th>通知</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="card">
										</tbody>
									</table>
									<div class="pagination pagination-centered">
										<ul>
											<li class="active" id="cardprev"><a href="#"
												onclick="card(0)" id="cardprev">上一页</a></li>
											<li class="active"><a href="#" id="cardnum">1</a></li>
											<li id="cardnext"><a href="#" onclick="card(2)"
												id="cardnext">下一页</a></li>
										</ul>
									</div>
								</div>
								<div class="tab-pane" id="messages">
									<table
										class="table table-striped table-bordered bootstrap-datatable datatable">
										<thead>
											<tr>
												<th>序号</th>
												<th>支付名称</th>
												<th>接入代码</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${participate}" varStatus="l" var="par">
												<tr>
													<td><c:out value="${par.codeid}"></c:out></td>
													<td><c:out value="${par.payable }"></c:out></td>
													<td><c:out value="${par.participate }"></c:out></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!--/span-->
				</div>
				<!--/row-->

				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->

		<hr>
		<div class="modal hide fade" style="width: 1200px;margin-left: -600px" id="cardmodal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>点卡信息</h3>
			</div>
			<div class="modal-body">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>订单号</th>
							<th>卡号</th>
							<th>订单日期</th>
							<th>交易方式</th>
							<th>金额(元)</th>
							<th>实际金额(元)</th>
							<th>扣除金额(元)</th>
							<th>状态</th>
							<th>说明</th>
						</tr>
					</thead>
					<tbody id="singlecard">
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">关闭</a>
				<!-- <a href="#" class="btn btn-primary">Save changes</a> -->
			</div>
		</div>

		
	<jsp:include page="../page/jspfooter.jsp"></jsp:include>

	</div>
	<!--/.fluid-container-->
</body>
</html>