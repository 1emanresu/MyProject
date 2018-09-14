<%@page import="com.vo.Onlineorder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../page/page.jsp"%>
<%
	String ret_code = request.getParameter("ret_code");
	String sporder_id = request.getParameter("sporder_id");
	String ordersuccesstime = request.getParameter("ordersuccesstime");
	String err_msg = request.getParameter("err_msg");
	String st = "";
	if(ret_code.equals("1")){
		st = "支付成功！";
		Onlineorder onlineorder = Onlineorder.dao.findById(sporder_id);
		onlineorder.set("ret_code", ret_code);
		onlineorder.set("ordersuccesstime", ordersuccesstime);
		onlineorder.set("err_msg", "充值成功");
		onlineorder.update();
	}else if(ret_code.equals("9")){
		st = "订单撤销！";
		Onlineorder onlineorder = Onlineorder.dao.findById(sporder_id);
		onlineorder.set("ret_code", ret_code);
		onlineorder.set("ordersuccesstime", ordersuccesstime);
		onlineorder.set("err_msg", err_msg);
		onlineorder.update();
	}
%>
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
								<i class="icon-retweet"></i> <%=st %>
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
											<fieldset>
												<legend>
													<span class="icon32 icon-blue icon-arrow-e"></span><%=st %>
												</legend>
											</fieldset>
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