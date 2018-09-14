<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
<script src="<%=basePath%>/js/ajaxs/order.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	transferpa(1);
	$('#starttime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$('#endtime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$("#transferform").Validform({
		tiptype:1,
		postonce:true,
		ajaxPost:true,
		ignoreHidden:true,
		callback:function(data){
			if(data.status=="y"){
				setTimeout(function(){
					$.Hidemsg(); 
					window.location.href="<%=basePath%>/transfer";
				},1000);
			}else{
				setTimeout(function(){
					$.Hidemsg(); 
					window.location.href="<%=basePath%>/transfer";
					}, 1000);
				}
			}
		});
	});
function transferpa(pages){
	var transferid = $("#transferid").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	gettransfer(basePath, pages, starttime, endtime, transferid);
}
</script>
</head>
<body>
	<jsp:include page="../page/top1.jsp"></jsp:include>

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
						<li class="active">转账管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="widget-box">
								<div class="widget-header">
									<h4 class="smaller">转账管理</h4>
								</div>
								<div class="widget-body">
									<div class="widget-main">
										<div class="tabbable">
											<ul class="nav nav-tabs">
												<li class="active"><a data-toggle="tab" href="#home">
														转账</a></li>
												<li><a data-toggle="tab" href="#profile">转账列表</a></li>
											</ul>
											<div class="tab-content">
												<div id="home" class="tab-pane in active">
													<form class="form-horizontal" role="form" id="transferform"
														action="<%=basePath%>/transfer/addTransfer" method="post">
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-field-1"> 可用金额 </label>

															<div class="col-sm-9">
																$
																<c:out value="${balance.settlement }"></c:out>
															</div>
														</div>

														<div class="space-4"></div>

														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-field-2"> 转账金额 </label>

															<div class="col-sm-9">
																<input type="text" id="form-field-2"
																	name="transfer_amount" placeholder="转账金额" datatype="*"
																	nullmsg="金额不能为空！" class="col-xs-10 col-sm-5" />
															</div>
														</div>

														<div class="space-4"></div>

														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-input-readonly"> 转账id </label>

															<div class="col-sm-9">
																<input type="text" class="col-xs-10 col-sm-5"
																	name="transfer_usert" datatype="*" nullmsg="用户id不能为空！"
																	placeholder="转账用户id" />
															</div>
														</div>

														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-input-readonly"> 支付密码 </label>

															<div class="col-sm-9">
																<input type="password" class="col-xs-10 col-sm-5"
																	name="payment" datatype="*" nullmsg="请输入支付密码！"
																	placeholder="支付密码" />
															</div>
														</div>

														<div class="space-4"></div>

														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-input-readonly"> 转账说明 </label>

															<div class="col-sm-9">
																<textarea class="form-control" id="form-field-8"
																	placeholder="转账说明" name="transfer_explain" datatype="*"
																	nullmsg="转账说明不能为空！"></textarea>
															</div>
														</div>

														<div class="space-4"></div>

														<div class="clearfix form-actions">
															<div class="col-md-offset-3 col-md-9">
																<button class="btn btn-info" type="submit">
																	<i class="icon-ok bigger-110"></i> 提交
																</button>

																&nbsp; &nbsp; &nbsp;
																<button class="btn" type="reset">
																	<i class="icon-undo bigger-110"></i> 清除
																</button>
															</div>
														</div>
													</form>
												</div>

												<div id="profile" class="tab-pane">
													<div class="row">
														<div class="col-sm-3">
															订单编号：<input style="width: 150px" name="transferid"
																id="transferid" type="text" Placeholder="订单编号" />
														</div>
														<div class="col-sm-3">
															<div class="row">
																<div class="col-sm-6">
																	日期：<input name="starttime" id="starttime"
																		data-date-format="yyyy-mm-dd" style="width: 70px"
																		type="text" class="datepicker" Placeholder="开始时间">
																</div>
																<div class="col-sm-5">
																	<input name="endtime" id="endtime"
																		data-date-format="yyyy-mm-dd" style="width: 70px"
																		type="text" class="datepicker" Placeholder="结束时间">
																</div>
															</div>


														</div>
														<div class="col-sm-2">
															<input class="btn btn-small btn-primary" type="button"
																onclick="transferpa(1)" value="查询">
														</div>
													</div>
													<table class="table table-bordered">
														<thead>
															<tr>
																<th>转账编号</th>
																<th>转账账户</th>
																<th>转账接收账户</th>
																<th>金额(元)</th>
																<th>转账时间</th>
																<th>转账说明</th>
															</tr>
														</thead>
														<tbody id="transfer">
														</tbody>
													</table>
													<div>
														<ul class="pagination">
															<li class="active" id="tranprev"><a href="#"
																onclick="transferpa(0)" id="tranprev"> <i
																	class="icon-double-angle-left"></i>
															</a></li>
															<li class="active"><a href="#" id="trannum">1</a></li>
															<li id="trannext"><a href="#" onclick="transferpa(2)"
																id="trannext"> <i class="icon-double-angle-right"></i>
															</a></li>
														</ul>
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
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