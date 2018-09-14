<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@page import="com.jfinal.core.Controller" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
<script type="text/javascript">
var page = 1;
$(document).ready(function(){
	$('#refundstarttime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$('#refundendtime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	//refund(page);
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
	<jsp:include page="../page/top1.jsp"></jsp:include>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed');
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<jsp:include page="../page/left1.jsp"></jsp:include>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed');
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li><li class="active">代付管理</li>
					</ul>
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">									
									<li class="active"><a data-toggle="tab" href="#custom">
										<i class="green icon-home bigger-110"></i> 确认申请
									</a></li>
									<li><a data-toggle="tab" href="#five">
											<i class="green icon-home bigger-110"></i> 查看结果
									</a></li>
								</ul>
								<div class="tab-content">
									<div id="custom" class="tab-pane active ${list}">
									<form action="<%=basePath%>/settlement/ViewResult" method="post">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>订单号</th>
													<th>开户姓名</th>
													<th>提现金额(元)</th>
													<th>银行名称</th>
													<th>提现账号</th>
													<th>开户省名</th>
													<th>开户市名</th>
													<th>开户支行</th>
													<th>备注</th>
												</tr>
											</thead>
											<tbody id="refund-m">
												<c:forEach items="${refunds }" var="refund">
													<tr>
														<td>${refund.refund_id }</td>
														<td>${refund.account_name }</td>
														<td>${refund.refund_amount }</td>
														<td>${refund.net_name}</td>
														<td>${refund.account }</td>
														<td>${refund.branchsheng}</td>
														<td>${refund.branchshi }</td>
														<td>${refund.branch }</td>
														<td>${refund.remark }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
										 <div class="clearfix form-actions">
										    <input type="hidden" name="user_id" value="${user_id}">
										    <input type="hidden" name="refund_state" value="${refund_state}">
										    <input type="hidden" name="createtime" value="${createtime}">
										    <input type="hidden" name="all_refund_rate" value="${all_refund_rate}">
										    <input type="submit"  value="确认提交" class="class="btn btn-primary"">
										 </div>
										</div>
										</form>
									</div>
									<div id="five" class="tab-pane ${result}">	
									    <div style="height:40px;width:100%">&nbsp;</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>${LastResult}</th>													
												</tr>
											</thead>										
																														
										</table>								
										<div class="row">											
											 <div class="col-sm-6">
												<div class="well">
													<ol>
														<li>为确保提现无误，本次成功申请笔数为:${count} 笔，请核查。</li>
														<li>本次提现申请前可提金额为：${old_settlement}元</li>
														<li>本次提现申请单笔手续费：${refund_rate}元</li>
														<li>本次提现申请总手续费：${all_refund_rate}元</li>
														<li>本次提现申请的总金额为：${amount}元</li>
														<li>扣除本次账户可提现金额为：  ${now_settlement }元</li>
													</ol>
												</div>
											</div>										    
										</div>
									</div>									
									<!-- CUSTOM结束 -->
								</div>
							</div>
						</div>
						<!-- /span -->

						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /span -->
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