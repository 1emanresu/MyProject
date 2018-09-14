<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/page/upage1.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath %>/css/pager.css"/>
<script type="text/javascript" src="<%=basePath %>/js/pager.js"></script>
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
						<li class="active">结算管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i>提现申请
									</a></li>
									<li><a data-toggle="tab" href="#custom"> <i
											class="green icon-home bigger-110"></i>提现记录
									</a></li>
									<li><a data-toggle="tab" href="#five"> <i
											class="green icon-home bigger-110"></i>提现账号
									</a></li>
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<div class="row">
											<div class="col-sm-6">
												<form class="form-horizontal"
													action="<%=basePath%>/settlement/withdrawal" method="post"
													id="refundform">
													<input type="hidden" name="withdrawalToken"
														value="${withdrawalToken}" />
													<fieldset>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right">提现方式：</label>
															<c:if test="${balance.settlementauthority==1}">
																<div class="controls">T+0 每天提现次数:无限</div>
															</c:if>
															<c:if test="${balance.settlementauthority==2}">
																<div class="controls">T+1 每天提现次数:1</div>
															</c:if>
														</div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="selectError3">提现账户类型：</label>
															<div class="col-sm-9">
																<select id="selectError3" name="refund_types">
																	<c:choose>
																		<c:when test="${empty bank}">
																			<option value="">银行卡：未绑定</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${bank.account_types}">
																				银行卡：
																				<c:out value="${bank.codeid}"></c:out>
																			</option>
																		</c:otherwise>
																	</c:choose>
																	<c:choose>
																		<c:when test="${empty network}">
																			<option value="">网络账户：未绑定</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${network.account_types}">
																				网络账户：
																				<c:out value="${network.branch}"></c:out>
																			</option>
																		</c:otherwise>
																	</c:choose>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right">可结算金额：</label>
															<div class="col-sm-9">
																<div class="input-prepend input-append">
																	<span class="add-on"></span>
																	<span class="add-on">
																		<c:out value="${balance.settlement } 元"></c:out>
																	</span>
																	<h5 class="text-danger">温馨提示：提现请减去单笔提现手续费&nbsp;<span style="color:red;font-weight:800;">${refund_rate.refund }</span>&nbsp;元</h5>
																</div>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right">提现金额：</label>
															<div class="col-sm-9">
																<input type="text" name="refund_amount" datatype="n"
																	nullmsg="请输入金额！" errormsg="输入金额有误，请重新输入！" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right">支付密码：</label>
															<div class="col-sm-9">
																<input type="text" name="payment" datatype="n"
																	nullmsg="请输入支付密码！" errormsg="输入支付密码有误，请重新输入！"/>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right">备注：</label>
															<div class="col-sm-9">
																<input type="text" name="remark"
																	nullmsg="备注" errormsg="" />
															</div>
														</div>
														<div class="clearfix form-actions">
															<button type="submit" class="btn btn-primary">确认提交</button>
															<button class="btn">重新填写</button>
														</div>
													</fieldset>
												</form>
												<div style="color:blue;">结算规则以商户约定为准，为确保提现成功，请保证可用余额资金充足</div>
											</div>
										</div>
									</div>
									<div id="custom" class="tab-pane">
										<div class="row">
											<div class="col-sm-3">
												订单编号：<input style="width: 150px" name="refundid"
													id="refundid" type="text" Placeholder="订单编号" />
											</div>
											<div class="col-sm-2">
												名称：<input style="width: 150px" name="account_name"
													id="account_name" type="text" Placeholder="名称" />
											</div>
											<div class="col-sm-3">
												<div class="row">
													<div class="col-sm-6">
														日期：<input name="refundstarttime" id="refundstarttime"
															style="width: 90px" type="text" class="datepicker"
															data-date-format="yyyy-mm-dd" Placeholder="开始时间">
													</div>
													<div class="col-sm-5">
														<input name="refundendtime" id="refundendtime"
															style="width: 90px" type="text" class="datepicker"
															data-date-format="yyyy-mm-dd" Placeholder="结束时间">
													</div>
												</div>
											</div>
											<div class="col-sm-2">
												订单状态：<select name="refundstate" id="refundstate"
													style="width: 80px">
													<option value="0">请选择</option>
													<option value="1">成功</option>
													<option value="2">处理中</option>
													<option value="3">失败</option>
												</select>
											</div>
											<div class="col-sm-2">
												<input class="btn btn-small btn-primary" type="button" style="line-height: 7px;margin-bottom: 10px;"
													onclick="refund(1)" value="查询">
											</div>
											
											<div class="col-sm-12">
												<div class="alert alert-warning" id="my_count">总条数：${ordertotal.tote}条 金额：${ordertotal.amounttote}（元）</div>
											</div>
										</div>
										<div style="color:#000000">
											<table class="table table-bordered" style="background：#ffffff">
												<thead>
													<tr>
														<th STYLE="text-align: center">序列号</th>
														<th STYLE="text-align: center">提现金额(元)</th>
														<th STYLE="text-align: center">收款用户名</th>
														<th STYLE="text-align: center">提现方式</th>
														<th STYLE="text-align: center">提现银行账号</th>
														<th STYLE="text-align: center">提现分行</th>
														<th STYLE="text-align: center">提现说明</th>
														<th STYLE="text-align: center">申请日期</th>
														<th STYLE="text-align: center">备注</th>
														<th STYLE="text-align: center">提现状态</th>
													</tr>
												</thead>
												<tbody id="refund" style="background: #FFFFFF"></tbody>
											</table>
										</div>
										<div class="pagination" id="tm_pagination"></div>
									</div>
									<div id="five" class="tab-pane">
										<div class="row">
											<div class="col-sm-5">
												<div class="tabbable">
													<ul class="nav nav-tabs" id="myTab">
														<li class="active"><a data-toggle="tab" href="#bank">
																银行卡</a></li>
														<li><a data-toggle="tab" href="#network"> 网络账户</a></li>
													</ul>

													<div class="tab-content">
														<div id="bank" class="tab-pane in active">
															<form class="form-horizontal" id="bankform"
																action="<%=basePath%>/settlement/upSettleAcount"
																method="post">
																<input type="hidden" name="account_types" value="1" />
																<fieldset>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right"
																			for="selectError">开户银行：</label>
																		<div class="col-sm-8">
																			<select id="selectError" data-rel="chosen"
																				name="codeid">
																				<c:forEach items="${partici}" varStatus="l"
																					var="par">
																					<option value="${par.codeid}">
																						<c:out value="${par.payable}"></c:out>
																					</option>
																				</c:forEach>
																			</select>
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">开户姓名：</label>
																		<div class="col-sm-8">
																			<c:out value="${per.contacts}"></c:out>
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">银行卡号：</label>
																		<div class="col-sm-8">
																			<input type="text" name="account" datatype="*"
																				errormsg="新银行卡号不能为空" />
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">确认卡号：</label>
																		<div class="col-sm-8">
																			<input type="text" datatype="*" recheck="account"
																				errormsg="您两次输入的银行卡号不一致！" />
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">收款人开户行的省名：</label>
																		<div class="col-sm-8">
																			<input type="text" name="branchsheng" datatype="*"
																				errormsg="开户行的省名不能为空" />
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">收款人开户行的市名：</label>
																		<div class="col-sm-8">
																			<input type="text" name="branchshi" datatype="*"
																				errormsg="开户行的市名不能为空" />
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-4 control-label no-padding-right">开户支行：</label>
																		<div class="col-sm-8">
																			<input type="text" name="branch" datatype="*"
																				errormsg="开户行不能为空" />
																		</div>
																	</div>

																	<div class="clearfix form-actions">
																		<button type="submit" class="btn btn-primary">确认提交</button>
																		<button class="btn">重新填写</button>
																	</div>
																</fieldset>
															</form>
														</div>

														<div id="network" class="tab-pane">
															<form class="form-horizontal" id="networkform"
																action="<%=basePath%>/settlement/upSettleAcount"
																method="post">
																<input type="hidden" name="account_types" value="2" />
																<fieldset>
																	<div class="form-group">
																		<label class="col-sm-3 control-label no-padding-right"
																			for="selectError">账户类型：</label>
																		<div class="col-sm-9">
																			<label class="radio"> <input type="radio"
																				name="optionsRadios" id="optionsRadios1"
																				value="option1" checked=""> 支付宝
																			</label>
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-3 control-label no-padding-right">真实姓名：</label>
																		<div class="col-sm-9">
																			<c:out value="${per.contacts}"></c:out>
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-3 control-label no-padding-right">新收款帐号：</label>
																		<div class="col-sm-9">
																			<input type="text" name="account" datatype="*"
																				errormsg="新收款账号不能为空" />
																		</div>
																	</div>
																	<div class="form-group">
																		<label class="col-sm-3 control-label no-padding-right">确认新收款帐号：</label>
																		<div class="col-sm-9">
																			<input type="text" datatype="*" recheck="account"
																				errormsg="您两次输入的收款账号不一致！" />
																		</div>
																	</div>
																	<div class="clearfix form-actions">
																		<button type="submit" class="btn btn-primary">确认提交</button>
																		<button class="btn">重新填写</button>
																	</div>
																</fieldset>
															</form>
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-7" style="height:500px;overflow:auto;">
												<div>
													<h3 class="text-primary">您所有绑定的结算账户信息</h3>
													<dl>
														<dt>银行卡：</dt>
														<c:choose>
															<c:when test="${empty bank}">
																<dd class="text-danger">您尚未绑定银行卡</dd>
															</c:when>
															<c:otherwise>
															<table class="table table-striped table-hover table-bordered">
																<thead>
																	<tr>
																		<th>银行类型</th>
																		<th>开户姓名</th>
																		<th>收款帐号</th>
																		<th>开户行省名</th>
																		<th>开户行市名</th>
																		<th>开户支行</th>
																		<th>操作</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${userCards }" var="cards">
																		<tr>
																			<td>${cards.codeid}</td>
																			<td>${cards.account_name}</td>
																			<td>${cards.account}</td>
																			<td>${cards.branchsheng}</td>
																			<td>${cards.branchshi}</td>
																			<td>${cards.branch}</td>
																			<td>
																				<a href="javascript:void(0);"><button type="button" data-toggle="modal" data-target="#modal-dialog-card" data-whatever="${cards.primary_id}">更改</a>
																				<button type="button" class="pull-right"><a href="<%=basePath%>/settlement/delUsercard?primary_id=${cards.primary_id}">删除</a></button>
																			</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
															</c:otherwise>
														</c:choose>
														<dt>网络帐户：</dt>
														<c:choose>
															<c:when test="${empty network}">
																<dd class="text-danger">您尚未绑定网络账户</dd>
															</c:when>
															<c:otherwise>
																<table class="table table-striped table-hover table-bordered">
																	<thead>
																		<tr>
																			<th>帐户类型</th>
																			<th>真实姓名</th>
																			<th>收款帐号</th>
																			<th>操作</th>
																		</tr>
																	</thead>
																	<tbody>
																		<tr>
																			<td>${network.branch}</td>
																			<td>${network.account_name}</td>
																			<td>${network.account}</td>
																			<td><a href="javascript:void(0);"><button type="button" data-toggle="modal" data-target="#modal-dialog-network">更改</a></td>
																		</tr>
																	</tbody>
																</table>
															</c:otherwise>
														</c:choose>
													</dl>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- /span -->
						<!-- start银行卡弹出层 -->
						<div class="modal fade" id="modal-dialog-card" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
						    <div class="modal-dialog" role="document">
						        <div class="modal-content">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						                        aria-hidden="true">×</span></button>
						                <h4 class="modal-title">更改银行卡信息</h4>
						                <h5 class="text-danger">*如果只修改某一项，其它的可以不填写，*</h5>
						            </div>
						            <div class="modal-body">
						                <form action="<%=basePath %>/settlement/updateUserCard" method="post">
						                    <input type="hidden" name="primary_id" id="card-primary"/>
						                    <input type="hidden" name="account_types" value="1"/>
						                    <div class="form-group">
						                    	<label class="col-sm-3 control-label no-padding-right" for="selectError">开户银行：</label>
												<div class="col-sm-9">
													<select id="selectError" data-rel="chosen" name="codeid">
														<c:forEach items="${partici}" varStatus="l" var="par">
															<option value="${par.codeid}">
																<c:out value="${par.payable}"></c:out>
															</option>
														</c:forEach>
													</select>
												</div>
						                    </div>
						                    <div class="form-group">
						                        <label class="control-label">开户姓名:</label>
						                        <input type="text" class="form-control" name="account_name">
						                    </div>
						                    <div class="form-group">
						                        <label class="control-label">银行卡号:</label>
						                        <input type="text" class="form-control" name="account">
						                    </div>
						                    <div class="form-group">
						                        <label class="control-label">收款人开户行的省名：</label>
						                        <input type="text" class="form-control" name="branchsheng">
						                    </div>
						                    <div class="form-group">
						                        <label class="control-label">收款人开户行的市名：</label>
						                        <input type="text" class="form-control" name="branchshi">
						                    </div>
						                    <div class="form-group">
						                        <label class="control-label">开户支行：</label>
						                        <input type="text" class="form-control" name="branch">
						                    </div>
								            <div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="submit" class="btn btn-primary">确认提交</button>
								            </div>
						                </form>
						            </div>
						        </div>
						    </div>
						</div>
						<script type="text/javascript">
							$('#modal-dialog-card').on('show.bs.modal', function (event) {
						        var button = $(event.relatedTarget); // 触发事件的按钮
						        var recipient = button.data('whatever'); // 解析出data-whatever内容
						        var modal = $(this);
						        modal.find('#card-primary').val(recipient);
						    });
						</script>
						<!-- end银行卡弹出层 -->
						<!-- start网络账户弹出层 -->
						<div class="modal fade" id="modal-dialog-network" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
						    <div class="modal-dialog" role="document">
						        <div class="modal-content">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						                        aria-hidden="true">×</span></button>
						                <h4 class="modal-title">更改网络账户信息</h4>
						            </div>
						            <div class="modal-body">
						                <form action="<%=basePath%>/settlement/updateUserCard" method="post" onsubmit="return checkUpdateVal();">
						                    <input type="hidden" name="account_types" value="2"/>
						                    <div class="form-group">
						                        <label for="recipient-name" class="control-label">支付宝账号<span style="color:red;">(*必填*)</span>:</label>
						                        <input type="text" class="form-control" name="account" id="net_account">
						                    </div>
						                    <div class="form-group">
						                        <label for="recipient-name" class="control-label">真实姓名<span style="color:red;">(*必填*)</span>:</label>
						                        <input type="text" class="form-control" name="account_name"  id="net_account_name">
						                    </div>
								            <div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="submit" class="btn btn-primary">确认提交</button>
								            </div>
						                </form>
						            </div>
						        </div>
						    </div>
						</div>
						<!-- end网络账号弹出层 -->
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
	<script type="text/javascript">
		function isEmpty(val) {
			val = $.trim(val);
			if (val == null)
				return true;
			if (val == undefined || val == 'undefined')
				return true;
			if (val == "")
				return true;
			if (val.length == 0)
				return true;
			if (!/[^(^\s*)|(\s*$)]/.test(val))
				return true;
			return false;
		}
		function checkUpdateVal(){
			var net_account = $("#net_account").val();
			var net_account_name = $("#net_account_name").val();
			if(isEmpty(net_account)){
				alert("支付宝账号不能为空");
				return false;
			}
			if(isEmpty(net_account_name)){
				alert("姓名不能为空");
				return false;
			}
			return true;
		};
		function refund(pages) {
			var refundstarttime = $("#refundstarttime").val();
			var refundendtime = $("#refundendtime").val();
			var refundstate = $("#refundstate").val();
			var refundid = $("#refundid").val();
			var account_name = $("#account_name").val();
			loadDataByPage.loadData(pages);
			RefundsSum(basePath,refundstarttime,refundendtime,refundstate,refundid,account_name,function(itemCount){
				loadDataByPage.initPage(itemCount);
			});
		}
	</script>
</body>
</html>