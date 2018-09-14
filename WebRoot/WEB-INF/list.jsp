<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vo.Logrecord"%>
<%@ page import="com.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/page/upage1.jsp"></jsp:include>
<script src="<%=basePath%>/js/ajaxs/order.js"></script>
<script type="text/javascript">
var page = 1;
$(document).ready(function(){
	$('#refundstarttime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$('#refundendtime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	refund(page);
	
$("#bankform").Validform({
	tiptype:1,
	postonce:true,
	ajaxPost:true,
	ignoreHidden:true,
	callback:function(data){
		if(data.status=="y"){
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
			},1000);
		}else{
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
			},1000);
		}
	}
});

$("#networkform").Validform({
	tiptype:1,
	postonce:true,
	ajaxPost:true,
	ignoreHidden:true,
	callback:function(data){
		if(data.status=="y"){
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
			},1000);
		}else{
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
			},1000);
		}
	}
});

$("#refundform").Validform({
	tiptype:1,
	postonce:true,
	ajaxPost:true,
	ignoreHidden:true,
	callback:function(data){
		if(data.status=="y"){
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
			},1000);
		}else{
			setTimeout(function(){
				$.Hidemsg(); 
				window.location.href="<%=basePath%>/settlement";
					}, 1000);
				}
			}
		});
	});
	
	function refund(pages) {
		var refundstarttime = $("#refundstarttime").val();
		var refundendtime = $("#refundendtime").val();
		var refundstate = $("#refundstate").val();
		var refundid = $("#refundid").val();
		refunds(basePath, pages, refundstarttime, refundendtime, refundstate,refundid);
	}
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<jsp:include page="./page/top1.jsp"></jsp:include>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<jsp:include page="./page/left1.jsp"></jsp:include>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li><li class="active">代付管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i> 代付申请
									</a></li>
									<li><a data-toggle="tab" href="#custom"> <i
											class="green icon-home bigger-110"></i> 确认申请
									</a></li>
									<li><a data-toggle="tab" href="#five"> <i
											class="green icon-home bigger-110"></i> 查看结果
									</a></li>
								</ul>
								<div class="tab-content">
									<div id="info" class="tab-pane">
										<div class="row">
											<div class="col-sm-6">
													<input type="hidden" name="withdrawalToken" value="${withdrawalToken}" />
													<fieldset>
													<form action="importExcel" method="post" enctype="multipart/form-data">																											
														<div class="clearfix form-actions">	
														    <input class="btn btn-primary" type="file" name="excel" accept="application/vnd.ms-excel"/>																												
															<input type="submit" class="btn btn-primary" value="确认上传">																											
														</div>
														</form>	
													</fieldset>
											</div>
											<div class="col-sm-6">
												<div class="well">
													<ol>
														<li>为确保提现成功，请保证可用余额资金充足；</li>
														<li>目前只支持：工商银行、农业银行、招商银行、建设银行；</li>
														<li>招商银行、工商银行支持T+0到账、农业银行、建设银行支持T+1到账；</li>
														<li>T+0:0点~8点提交并审核的批付单，8点后一起付出，正常情况当天13:00左右到账；</li>
														<li>T+0:8点~12点提交并审核的批付单，12点后一起付出，正常情况当天16:00左右到账；</li>
														<li>T+0:12点~24点提交并审核的批付单，次日8点后一起付出，正常情况次日16:00左右到账；</li>
														<li>T+1结算，是指T日的交易，在T+1日结算。</li>
													</ol>
												</div>
											</div>
										</div>
									</div>
									<div id="custom" class="tab-pane in active">										
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>订单号</th>
													<th>申请日期</th>
													<th>提现金额(元)</th>
													<th>提现方式</th>
													<th>提现说明</th>
													<th>提现状态</th>
												</tr>
											</thead>
											<tbody id="refund-1"> 
											<%
												System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
											%>
											
											</tbody>
										</table>
										<ul class="pagination">
											
											<li class="active"><a href="#" id="refundnum">1</a></li>
											<li id="refundnext"><a href="#" onclick="refund(2)"
												id="refundnext"> <i class="icon-double-angle-right"></i>
											</a></li>
										</ul>
									</div>										
									<!-- CUSTOM结束 -->
								</div>
							</div>
						</div>
						<!-- /span -->

						<div class="col-sm-12">
							<jsp:include page="./page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /span -->
					</div>
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="./page/switch.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
</body>
</html>