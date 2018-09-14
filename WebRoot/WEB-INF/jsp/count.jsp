<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/page/upage1.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath %>/css/pager.css"/>
<script type="text/javascript" src="<%=basePath %>/js/pager.js"></script>
<script src="<%=basePath%>/js/ajaxs/UserOrder.js"></script>
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
						<li class="active">查询统计</li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i> 查询订单
									</a></li>
									<li><a data-toggle="tab" href="#custom">统计订单</a></li>
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<div class="row">
											<div class="col-sm-3">
												商户订单号：<input style="width: 150px" name="netid" id="netid"
													type="text" Placeholder="订单编号" value="" />
											</div>
											<div class="col-sm-3">
												<span>日期：<input name="netstarttime" id="netstarttime"
													data-date-format="yyyy-mm-dd" style="width: 100px"
													type="text" class="netstarttime" Placeholder="开始时间"
													value=""></span>—<span><input name="netendtime"
													id="netendtime" style="width: 100px"
													data-date-format="yyyy-mm-dd" type="text"
													class="netendtime" Placeholder="结束时间" value=""></span>
											</div>
											<div class="col-sm-3">
												<input type="button" name="today" value=" 今天 "
													onClick="MyDate(1)"> <input type="button"
													name="yesteday" value=" 昨天 " onClick="MyDate(2)"> <input
													type="button" name="week" value=" 本周 " onClick="MyDate(4)">
											</div>
											<div class="col-sm-3">
												订单状态：<select name="netstate" id="netstate"
													style="width: 80px">
													<option value="0">请选择</option>
													<option value="1">成功</option>
													<option value="2">处理中</option>
													<option value="3">失败</option>
													<option value="4">失效</option>
												</select>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="btn btn-small btn-primary" type="button" style="line-height: 10px;margin-bottom: 10px;"
													onclick="net(1)" value="查询">
											</div>

											<div class="col-sm-12">
												<div class="alert alert-warning" id="my_count">订单总数：${ordertotal.tote
													}条 充值金额：${ordertotal.amttote }（元）
													结算金额：${ordertotal.amounttote}（元）</div>
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th style="text-align: center">商户订单号</th>
													<th style="text-align: center">银行订单号</th>
													<th style="text-align: center">订单金额</th>
													<th style="text-align: center">结算金额</th>
													<th style="text-align: center">手续费</th>
													<th style="text-align: center">订单日期</th>
													<th style="text-align: center">状态</th>
													<th style="text-align: center">通知</th>
												</tr>
											</thead>
											<tbody id="net" style="background: #FFFFFF">
											</tbody>
										</table>
										<!-- <div>
											<ul class="pagination">
												<li class="active" id="netprev">
													<a href="#" onclick="net(0)" id="netprev">上一页</a>
												</li>
												<li class="active"><a href="#" id="netnum">1</a></li>
												<li id="netnext">
													<a href="#" onclick="net(2)" id="netnext">下一页</a>
												</li>
												<li><a href="javascript:void(0);" id="totalCount"></a></li>
											</ul>
										</div> -->
										<div  class="pagination" id="tm_pagination"></div>
									</div>
									<div class="tab-pane" id="custom">
										<div class="row">
											<div class="col-sm-3">
												<span>日期：<input name="netstarttime2" id="netstarttime2"
													data-date-format="yyyy-mm-dd" style="width: 100px"
													type="text" class="netstarttime" Placeholder="开始时间"
													value="">——</span><span> <input name="netendtime2"
													id="netendtime2" style="width: 100px"
													data-date-format="yyyy-mm-dd" type="text"
													class="netendtime" Placeholder="结束时间" value=""></span>
											</div>
											<div class="col-sm-3">
												<input type="button" name="today" value=" 今天 "
													onClick="MyDate2(1)"> <input type="button"
													name="yesteday" value=" 昨天 " onClick="MyDate2(2)">
												<input type="button" name="week" value=" 本周 "
													onClick="MyDate2(4)">
											</div>
											<div class="col-sm-3">
												<input class="btn btn-small btn-primary" onClick="mycount()" style="line-height: 10px;margin-bottom: 10px;"
													type="button" value="统计">
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th style="text-align: center">订单条数</th>
													<th style="text-align: center">交易金额</th>
													<th style="text-align: center">结算金额</th>
												</tr>
											</thead>
											<tbody id="usernet">
											</tbody>
										</table>
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
	<script type="text/javascript">
		$(document).ready(function() {
	
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
			$('.netstarttime2').datepicker({
				autoclose : true
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			$('.netendtime2').datepicker({
				autoclose : true
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
		});
		function net(pages){
			var netstarttime = $("#netstarttime").val();
			var netendtime = $("#netendtime").val();
			var netstate = $("#netstate").val();
			var netid = $("#netid").val();
			loadDataByPage.loadData(pages);
			UserSum(basePath,netstarttime,netendtime,netstate,netid,function(itemCount){
				loadDataByPage.initPage(itemCount);
			});
		};
		
		function mycount() {
			var start_time = $("#netstarttime2").val();
			var end_time = $("#netendtime2").val();
			var netstate = $("#netstate").val();
			var netid = $("#netid").val();
			usercount(basePath, start_time, end_time);
		}
	
		function getDay(day) {
			var today = new Date();
	
			var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24
					* day;
			today.setTime(targetday_milliseconds); //注意，这行是关键代码    
			var tYear = today.getFullYear();
			var tMonth = today.getMonth();
			var tDate = today.getDate();
			tMonth = doHandleMonth(tMonth + 1);
			tDate = doHandleMonth(tDate);
			return tYear + "-" + tMonth + "-" + tDate;
		}
		function doHandleMonth(month) {
			var m = month;
			if (month.toString().length == 1) {
				m = "0" + month;
			}
			return m;
		}
	
		function MyDate(v) {
			if (v == 1) {
				document.getElementById("netstarttime").value = getDay(0);
				document.getElementById("netendtime").value = getDay(1);
			} else if (v == 2) {
				document.getElementById("netstarttime").value = getDay(-1);
				document.getElementById("netendtime").value = getDay(0);
			} else {
				var date = new Date(); //当前日期
				var now = date.getDay();
				if(now==0){
					now = 7;	
				}
				var first = 1 - now;
				document.getElementById("netstarttime").value = getDay(first);
				document.getElementById("netendtime").value = getDay(1);
			}
		}
	
		function MyDate2(v) {
			if (v == 1) {
				document.getElementById("netstarttime2").value = getDay(0);
				document.getElementById("netendtime2").value = getDay(1);
			} else if (v == 2) {
				document.getElementById("netstarttime2").value = getDay(-1);
				document.getElementById("netendtime2").value = getDay(0);
			} else {
				var date = new Date(); //当前日期
				var now = date.getDay();
				if(now==0){
					now = 7;	
				}
				var first = 1 - now;			
				document.getElementById("netstarttime2").value = getDay(first);
				document.getElementById("netendtime2").value = getDay(1);
			}
		}
	</script>
</body>
</html>