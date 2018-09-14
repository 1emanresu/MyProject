<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../../page/upage.jsp"></jsp:include>
<link href="<%=basePath%>/css/bootstrap-cerulean.css" rel="stylesheet">
</head>
<body style="font-family: Microsoft YaHei">
	<div class="container-fluid">
		<div class="row-fluid">
			<div id="content" class="span12">
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-retweet"></i> 确认支付
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<div class="box-content">
								<div id="myTabContent" class="tab-content">
									<div class="tab-pane active" id="info">
										<form class="form-horizontal" action="<%=basePath%>/payment/reqpay" method="post" target="_blank">
											<input type="hidden" name="p3_Amt" value="${p3_Amt}" />
											<input type="hidden" name="pd_FrpId" value="${pd_FrpId}" />
											<input type="hidden" name="num" value="${num }" />
											<fieldset>
												<legend>
													<span class="icon32 icon-blue icon-arrow-e"></span>确认金额<span
														class="icon32 icon-blue icon-arrow-e"></span>进行充值<span
														class="icon32 icon-blue icon-arrow-e"></span>付款成功
												</legend>
												<div class="control-group">
													<label class="control-label" for="typeahead">付款金额：</label>
													<div class="controls">
														<div class="input-prepend input-append">
															<span class="add-on">$</span><span
																class="add-on"><c:out value="${p3_Amt }"></c:out></span>
														</div>
													</div>
												</div>
												<div class="form-actions">
													<button type="submit" class="btn btn-primary">确认充值</button>
												</div>
											</fieldset>
										</form>
									</div>
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

		<footer>
		<p class="pull-left">Copyright ? 2009-2013
			haofpay.com海阳浩付网络科技有限公司版权所有</p>
		<p class="pull-right">网站备案号：鲁ICP备13026900号-1
			电信增值业务经营许可证号：鲁13026900号-1 鲁13026900号-1</p>
		</footer>

	</div>
	<!--/.fluid-container-->
</body>
</html>