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
<jsp:include page="../page/upage1.jsp"></jsp:include>
<script src="<%=basePath%>/js/ajaxs/order.js"></script>
<script type="text/javascript">
	var page = 1;
	var cardpage = 1;
	$(document).ready(function() {
		net(page);
		card(cardpage);
		$('.netstarttime').datepicker({
			autoclose : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});
		$('.netendtime').datepicker({
			autoclose : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});
		$('.cardstarttime').datepicker({
			autoclose : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});
		$('.cardendtime').datepicker({
			autoclose : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});
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
	<jsp:include page="../page/top1.jsp"></jsp:include>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <sol-sm-
					class="menu-text"></sol-sm->
			</a>

			<jsp:include page="../page/left1.jsp"></jsp:include>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">交易管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i> 网银订单
									</a></li>
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<div class="row">
											<div class="col-sm-3">
												订单编号：<input style="width: 150px" name="netid" id="netid"
													type="text" Placeholder="订单编号" value="" />
											</div>
											<div class="col-sm-3">
												<div class="row">
													<div class="col-sm-7">
														日期：<input name="netstarttime" id="netstarttime"
															data-date-format="yyyy-mm-dd" style="width: 85px"
															type="text" class="netstarttime" Placeholder="开始时间"
															value="">
													</div>
													<div class="col-sm-5">
														<input name="netendtime" id="netendtime"
															style="width: 85px" data-date-format="yyyy-mm-dd"
															type="text" class="netendtime" Placeholder="结束时间"
															value="">
													</div>
												</div>
											</div>
											<div class="col-sm-2">
												支付方式：<select name="netparticipate" id="netparticipate"
													style="width: 85px">
													<option value="0">请选择</option>
													<c:forEach items="${partici}" varStatus="l" var="par">
														<option value="${par.codeid}">
															<c:out value="${par.payable}"></c:out>
														</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-2">
												订单状态：<select name="netstate" id="netstate"
													style="width: 80px">
													<option value="0">请选择</option>
													<option value="1">成功</option>
													<option value="2">处理中</option>
													<option value="3">失败</option>
													<option value="4">失效</option>
												</select>
											</div>
											<div class="col-sm-2">
												<input class="btn btn-small btn-primary" type="button"
													onclick="net(1)" value="查询">
											</div>
											<div class="col-sm-12">
												<div class="alert alert-warning">订单总数：${ordertotal.tote
													}条 充值金额：${ordertotal.amttote }（元）
													结算金额：${ordertotal.amounttote}（元）</div>
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>订单号</th>
													<th>订单日期</th>
													<th>订单金额</th>
													<th>结算金额</th>
													<th>手续费</th>
													<th>状态</th>
													<th>同步</th>
													<th>通知</th>
												</tr>
											</thead>
											<tbody id="net">
											</tbody>
										</table>
										<div>
											<ul class="pagination">
												<li class="active" id="netprev"><a href="#"
													onclick="net(0)" id="netprev">上一页</a></li>
												<li class="active"><a href="#" id="netnum">1</a></li>
												<li id="netnext"><a href="#" onclick="net(2)"
													id="netnext">下一页</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- /sol-sm- -->

						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /sol-sm- -->
					</div>
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="../page/switch.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!--/.fluid-container-->
</body>
</html>