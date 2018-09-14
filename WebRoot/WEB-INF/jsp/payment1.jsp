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
	$(document).ready(function() {
		$('#rechstarttime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		$('#rechendtime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		$('#circstarttime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		$('#circendtime').datepicker({autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		recharge(page);
		circlip(page);
		$("#circlipform").Validform({
			tiptype:1,
			postonce:true,
			ajaxPost:true,
			callback:function(data){
				if(data.status=="y"){
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/payment";
						$("li#licirclip").addClass("active");
						$("li#lireceivables").removeClass("active");
					}, 1000);
				} else {
					setTimeout(function() {
						$.Hidemsg();
						document.getElementById("circlipform").reset();
					}, 1000);
				}
			}
		});
		$("#netform").Validform({
			tiptype : 1,
			ignoreHidden : true
		});
	});
	function recharge(pages) {
		var rechstarttime = $("#rechstarttime").val();
		var rechendtime = $("#rechendtime").val();
		var rechparticipate = $("#rechparticipate").val();
		var rechstate = $("#rechstate").val();
		var rechid = $("#rechid").val();
		rechar(basePath, pages, rechstarttime, rechendtime, rechparticipate,
				rechstate, rechid);
	}
	function circlip(pages) {
		var circstarttime = $("#circstarttime").val();
		var circendtime = $("#circendtime").val();
		var circparticipate = $("#circparticipate").val();
		var circstate = $("#circstate").val();
		var circid = $("#circid").val();
		circ(basePath, pages, circstarttime, circendtime, circparticipate,circstate, circid);
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
			<a class="menu-toggler" id="menu-toggler" href="#"> <col-sm-
				class="menu-text"></col-sm->
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
						<li class="active">支付管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info"> 充值 </a></li>
									<li><a data-toggle="tab" href="#custom"> 充值纪录</a></li>
									<li><a data-toggle="tab" href="#messages">销卡</a></li>
									<li id="licirclip"><a data-toggle="tab" href="#four">销卡纪录</a></li>
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<form class="form-horizontal"
											action="<%=basePath%>/payment/getre" id="netform"
											method="post" target="_blank">
											<input name="num" type="hidden" value="1" />
											<fieldset>
												<legend>
													<col-sm- class="icon32 icon-blue icon-arrow-e"></col-sm->填写金额<col-sm-
														class="icon32 icon-blue icon-arrow-e"></col-sm-><i
														class="icon-double-angle-right"></i>选择银行<col-sm-
														class="icon32 icon-blue icon-arrow-e"></col-sm-><i
														class="icon-double-angle-right"></i>确认付款<col-sm-
														class="icon32 icon-blue icon-arrow-e"></col-sm-><i
														class="icon-double-angle-right"></i> 充值成功
												</legend>
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="typeahead">付款金额：</label>
													<div class="col-sm-9">
														<input name="p3_Amt"
																size="16" type="text" datatype="*" nullmsg="金额不能为空！" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="date01">支付方式：</label>
													<div class="col-sm-9">
														<table>
															<tbody>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CCB-NET-B2C"
																			checked=""> <img
																			src="<%=basePath%>/images/banklogo/jianshe.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="ICBC-NET-B2C">
																			<img
																			src="<%=basePath%>/images/banklogo/gongshang.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/zhaohang.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="ABC-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/nongye.gif">
																	</label></td>
																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="BOCO-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/jiaotong.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="BOC-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/zhongguo.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="POST-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/youzheng.gif">
																	</label></td>

																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="HKBEA-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/dongya.gif">
																	</label></td>

																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CEB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/guangda.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="GDB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/guangfa.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="HZBANK-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/hangzhou.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="SCCB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/hebei.gif">
																	</label></td>
																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="BCCB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/beijing.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CBHB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/buohai.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CMBC-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/minsheng.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="NJCB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/nanjing.gif">
																	</label></td>
																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="NCBBANK-NET-B2C">
																			<img
																			src="<%=basePath%>/images/banklogo/nanyanbank.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="NBCB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/ningbo.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="BJRCB-NET-B2C">
																			<img
																			src="<%=basePath%>/images/banklogo/nongcunshangye.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="ECITIC-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/zhongxin.gif">
																	</label></td>
																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="PINGANBANK-NET">
																			<img src="<%=basePath%>/images/banklogo/pingan.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="SHB-NET-B2C">
																			<img
																			src="<%=basePath%>/images/banklogo/shanghaibank.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="SRCB-NET-B2C">
																			<img width="154" height="33"
																			src="<%=basePath%>/images/banklogo/shangnongshang.jpg">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="SPDB-NET-B2C">
																			<img
																			src="<%=basePath%>/images/banklogo/shangpufa.gif">
																	</label></td>
																</tr>
																<tr>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="SDB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/shenfa.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="ZJTLCB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/tailong.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CIB-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/xingye.gif">
																	</label></td>
																	<td><label class="radio"> <input
																			type="radio" name="pd_FrpId" value="CZ-NET-B2C">
																			<img src="<%=basePath%>/images/banklogo/zheshang.gif">
																	</label></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
												<div class="form-actions">
													<button type="submit" class="btn btn-primary">确认充值</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div id="custom" class="tab-pane">
										<div class="row">
											<div class="col-sm-3">
												订单编号：<input style="width: 150px" name="rechid" id="rechid"
													type="text" Placeholder="订单编号" />
											</div>
											<div class="col-sm-3">
												<div class="row">
											<div class="col-sm-6">
											日期：<input name="rechstarttime" id="rechstarttime" data-date-format="yyyy-dd-mm"
													style="width: 70px" type="text" class="datepicker" Placeholder="开始时间">
											</div>
											<div class="col-sm-5">
												<input name="rechendtime" id="rechendtime" data-date-format="yyyy-dd-mm"
													style="width: 70px" type="text" class="datepicker" Placeholder="结束时间">
											</div>
										</div>
												
												
											</div>
											<div class="col-sm-2">
												支付方式：<select name="rechparticipate" id="rechparticipate"
													style="width: 85px">
													<option value="0">请选择</option>
													<c:forEach items="${partici}" varStatus="l" var="par">
														<option value="${par.codeid}"><c:out
																value="${par.payable}"></c:out></option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-2">
												订单状态：<select name="rechstate" id="rechstate"
													style="width: 80px">
													<option value="0">请选择</option>
													<option value="1">成功</option>
													<option value="2">处理中</option>
													<option value="3">失败</option>
												</select>
											</div>
											<div class="col-sm-2">
												<input class="btn btn-small btn-primary" type="button"
													onclick="recharge(1)" value="查询">
											</div>
											<div class="col-sm-12">
												<div class="alert alert-warning">订单总数：${rechordertotal.tote }条 交易金额：${rechordertotal.amttote }（元） 实际金额：${rechordertotal.amounttote}（元） </div>
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>订单号</th>
													<th>订单日期</th>
													<th>交易方式</th>
													<th>金额(元)</th>
													<th>实际金额(元)</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody id="recharge">
											</tbody>
										</table>
										<div>
										<ul class="pagination">
												<li class="active" id="rechprev"><a href="#"
													onclick="recharge(0)" id="rechprev">上一页</a></li>
												<li class="active"><a href="#" id="rechnum">1</a></li>
												<li id="rechnext"><a href="#" onclick="recharge(2)"
													id="rechnext">下一页</a></li>
											</ul>
											</div>
									</div>
									<div class="tab-pane" id="messages">
										<form class="form-horizontal" id="circlipform"
											action="<%=basePath%>/payment/circlip" target="_blank">
											<input type="hidden" name="url"
												value="<%=basePath%>/hscard/querCirclip" />
											<fieldset>
												<legend>注：请一定正确选择卡面值提交,否则造成损失商户自行承担；卡信息提交成功后，可在订单查询页面查询支付结果。处理结果以订单查询页为准。</legend>
												<div class="form-group">
													<label class="control-label col-sm-2" for="selectError">选择点卡类型：</label>
													<div class="col-sm-9">
														<select id="cardcode" name="cardid" data-rel="chosen">
															<c:forEach items="${cardcode}" varStatus="l" var="card">
																<option value="${card.codeid}"><c:out
																		value="${card.payable}"></c:out></option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-sm-2">选择面额：</label>
													<div class="col-sm-9">
														<label class="radio" id="face0" style="display: none;">
															<input type="radio" name="amount" value="0"> 0
														</label> <label class="radio" id="face1" style="display: none;">
															<input type="radio" name="amount" value="1"> 1
														</label> <label class="radio" id="face2" style="display: none;">
															<input type="radio" name="amount" value="2"> 2
														</label> <label class="radio" id="face3" style="display: none;">
															<input type="radio" name="amount" value="3"> 3
														</label> <label class="radio" id="face5"> <input
															type="radio" name="amount" value="5"> 5
														</label> <label class="radio" id="face6"> <input
															type="radio" name="amount" value="6"> 6
														</label> <label class="radio" id="face9" style="display: none;">
															<input type="radio" name="amount" value="9"> 9
														</label> <label class="radio" id="face10"> <input
															type="radio" name="amount" value="10"> 10
														</label> <label class="radio" id="face15"> <input
															type="radio" name="amount" value="15"> 15
														</label> <label class="radio" id="face20"> <input
															type="radio" name="amount" value="20"> 20
														</label> <label class="radio" id="face25" style="display: none;">
															<input type="radio" name="amount" value="25"> 25
														</label> <label class="radio" id="face30"> <input
															type="radio" name="amount" value="30"> 30
														</label> <label class="radio" id="face35" style="display: none;">
															<input type="radio" name="amount" value="35"> 35
														</label> <label class="radio" id="face40" style="display: none;">
															<input type="radio" name="amount" value="40"> 40
														</label> <label class="radio" id="face45" style="display: none;">
															<input type="radio" name="amount" value="45"> 45
														</label> <label class="radio" id="face50"> <input
															type="radio" name="amount" value="50"> 50
														</label> <label class="radio" id="face60" style="display: none;">
															<input type="radio" name="amount" value="60"> 60
														</label> <label class="radio" id="face70" style="display: none;">
															<input type="radio" name="amount" value="70"> 70
														</label> <label class="radio" id="face80" style="display: none;">
															<input type="radio" name="amount" value="80"> 80
														</label> <label class="radio" id="face90" style="display: none;">
															<input type="radio" name="amount" value="90"> 90
														</label> <label class="radio" id="face100"> <input
															type="radio" name="amount" value="100"> 100
														</label> <label class="radio" id="face120"> <input
															type="radio" name="amount" value="120"> 120
														</label> <label class="radio" id="face200"> <input
															type="radio" name="amount" value="200"> 200
														</label> <label class="radio" id="face300"> <input
															type="radio" name="amount" value="300"> 300
														</label> <label class="radio" id="face350" style="display: none;">
															<input type="radio" name="amount" value="350">
															350
														</label> <label class="radio" id="face468" style="display: none;">
															<input type="radio" name="amount" value="468">
															468
														</label> <label class="radio" id="face500"> <input
															type="radio" name="amount" value="500"> 500
														</label> <label class="radio" id="face1000"> <input
															type="radio" name="amount" value="1000"> 1000
														</label>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-2">卡号：</label>
													<div class="col-sm-9">
														<input type="text" id="cardno" name="cardno" datatype="*" nullmsg="卡号不能为空！"> 
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-sm-2">卡密：</label>
													<div class="col-sm-9">
														<input type="text" id="cardpass" name="cardpass" datatype="*" nullmsg="卡密不能为空！">
													</div>
												</div>
												<br /> <br /> <br />
												<div class="form-actions">
													<button type="submit" class="btn btn-primary">确认提交</button>
													<button type="reset" class="btn">重新填写</button>
												</div>
											</fieldset>
										</form>
									</div>
									<div class="tab-pane" id="four">
										<div class="row-fluid">
											<div class="col-sm-3">
												订单编号：<input style="width: 150px" name="circid" id="circid"
													type="text" Placeholder="订单编号" />
											</div>
											
											<div class="col-sm-3">
											<div class="row">
											<div class="col-sm-7">
												日期：<input name="circstarttime" id="circstarttime" data-date-format="yyyy-dd-mm"
												style="width: 80px" type="text" class="circstarttime" Placeholder="开始时间" value="">
											</div>
											<div class="col-sm-5">
												<input name="circendtime" id="circendtime" style="width: 80px" data-date-format="yyyy-dd-mm"
												type="text" class="circendtime" Placeholder="结束时间" value="">
											</div>
										</div>
											</div>
											<div class="col-sm-2">
												支付方式：<select name="circparticipate" id="circparticipate"
													style="width: 85px">
													<option value="0">请选择</option>
													<c:forEach items="${cardcode}" varStatus="l" var="card">
														<option value="${card.codeid}"><c:out
																value="${card.payable}"></c:out></option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-2">
												订单状态：<select name="circstate" id="circstate"
													style="width: 80px">
													<option value="0">请选择</option>
													<option value="2000">成功</option>
													<option value="2001">处理中</option>
													<option value="3">失败</option>
												</select>
											</div>
											<div class="col-sm-2">
												<input class="btn btn-small btn-primary" type="button"
													onclick="circlip(1)" value="查询">
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>订单号</th>
													<th>订单日期</th>
													<th>交易方式</th>
													<th>金额(元)</th>
													<th>实际金额(元)</th>
													<th>扣除金额(元)</th>
													<th>状态</th>
													<th>说明</th>
												</tr>
											</thead>
											<tbody id="circlip">
											</tbody>
										</table>
										<div>
											<ul class="pagination">
												<li class="active" id="circprev"><a href="#"
													onclick="circlip(0)" id="circprev">上一页</a></li>
												<li class="active"><a href="#" id="circnum">1</a></li>
												<li id="circnext"><a href="#" onclick="circlip(2)"
													id="circnext">下一页</a></li>
											</ul>
										</div>
									</div>
									
								</div>
							</div>

						</div>
						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /col-sm- -->
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