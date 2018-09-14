<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
</head>
<body style="font-family: Microsoft YaHei">
	<jsp:include page="../page/top2.jsp"></jsp:include>
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
						<li><i class="icon-home home-icon"></i> <a href="#">聚优支付</a></li>
						<li class="active">投诉电话：888888</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">购买商品通道
									</a></li>
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<form class="form-horizontal" action="http://127.0.0.1:8080/WebZfapi/req.action"
											id="netform" method="post" target="_blank">
											<fieldset>
												<div class="form-group"></div>
												<table width="100%" border="0" align="center"
													cellpadding="5" cellspacing="1">													
														<tr>
															<td align="left" width="30%">&nbsp;&nbsp;商户订单号</td>
															<td align="left">&nbsp;&nbsp;<input size="50"
																type="text" name="p2_Order" id="p2_Order" /></td>
														</tr>
														<tr>
															<td align="left">&nbsp;&nbsp;支付金额</td>
															<td align="left">&nbsp;&nbsp;<input size="50"
																type="text" name="p3_Amt" id="p3_Amt" value="10.00" />&nbsp;<span
																style="color: #FF0000; font-weight: 100;">*</span></td>
														</tr>

														<tr>
															<td align="left">&nbsp;&nbsp;支付通道编码</td>
															<td align="left">&nbsp;&nbsp;<input type="text"
																name="pd_FrpId" value="WXWAP" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">API接口文档下载</a>
														</tr>	
														<tr>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;支付通道编码:</span></td>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;</span></td>
														</tr>
														<tr>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;支付宝H5：alipaywap</span></td>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;</span></td>
														</tr>
														<tr>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;支付宝扫码：ali</span></td>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;</span></td>
														</tr>
														<tr>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;微信H5：wxwap</span></td>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;</span></td>
														</tr>
														<tr>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;微信扫码：weixin</span></td>
															<td align="left"><span style="color:blue;">&nbsp;&nbsp;</span></td>
														</tr>
																											
													<tr>
														<td height="5" bgcolor="#6BBE18" colspan="2"></td>
													</tr>
												</table>
												<div class="form-actions">
													<button type="submit" class="btn btn-primary">确认充值</button>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
							<div></div>
						</div>
						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>

			<a href="#" id="btn-scroll-up"
				class="btn-scroll-up btn btn-sm btn-inverse"> <i
				class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div>
		<script type="text/javascript">
			window.onload = function() {
				document.getElementById("p2_Order").value = new Date() * 1;
			}
		</script>
</body>
</html>