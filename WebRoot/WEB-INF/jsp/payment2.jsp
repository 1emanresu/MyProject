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
		recharge(page);
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
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<form class="form-horizontal"
											action="<%=basePath%>/payment/getre" id="netform"
											method="post" target="_blank">
											<input name="num" type="hidden" value="2" />
											<fieldset>
												<legend>
													<span class="icon32 icon-blue icon-arrow-e"></span>填写金额<span
														class="icon32 icon-blue icon-arrow-e"></span><i
														class="icon-double-angle-right"></i>选择银行<span
														class="icon32 icon-blue icon-arrow-e"></span><i
														class="icon-double-angle-right"></i>确认付款<span
														class="icon32 icon-blue icon-arrow-e"></span><i
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
														<table class="table">
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
												<li class="active" id="rechprev">
													<a href="#"
													onclick="recharge(0)" id="rechprev">
														<i class="icon-double-angle-left"></i>
													</a>
												</li>
												<li class="active">
													<a href="#" id="rechnum">1</a>
												</li>
												<li id="rechnext">
													<a href="#" onclick="recharge(2)"
													id="rechnext">
														<i class="icon-double-angle-right"></i>
													</a>
												</li>
											</ul>
											</div>
									</div>
								</div>
							</div>

						</div>
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