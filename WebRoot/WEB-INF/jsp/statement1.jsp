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
<script src="<%=basePath%>/js/ichart.1.2.min.js"></script>
<script type="text/javascript">
$(function(){
	var netamount = ${netamount};
	var cardamount = ${cardamount};
	var data = [
	        	{name : '网银',value : netamount,color:'#5d7f97'},
	        	{name : '点卡',value : cardamount,color:'#97b3bc'}
        	];
	
	new iChart.Pie2D({
		render : 'canvasDiv',
		data: data,
		title : '网银 点卡收益比例',
		legend : {
			enable : true
		},
		showpercent:true,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:140
	}).draw();
});
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<!-- topbar starts -->
	<jsp:include page="../page/top.jsp"></jsp:include>
	<!-- topbar ends -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- left menu starts -->
			<jsp:include page="../page/left.jsp"></jsp:include>
			<!-- left menu ends -->

			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href="#" onclick="statement()">报表查看</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-retweet"></i> 报表
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
						<div id="canvasDiv"></div>
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

		
	<jsp:include page="../page/jspfooter.jsp"></jsp:include>
	</div>
	<!--/.fluid-container-->
</body>
</html>