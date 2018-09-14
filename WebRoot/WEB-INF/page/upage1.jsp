<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<META name="Keywords" content="" />
<link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath %>/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<!-- <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->

		<!-- ace styles -->
		<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath %>/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=basePath %>/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath %>/assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>/assets/js/respond.min.js"></script>
		<![endif]-->
<!--[if !IE]> -->
		<!--[if !IE]> -->
		<script src="<%=basePath %>/assets/js/jquery-2.0.3.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
			<script src="<%=basePath %>/assets/js/jquery.1.10.2.min.js"></script>
		<![endif]-->
		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=basePath %>/assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="<%=basePath %>/assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="<%=basePath %>/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.slimscroll.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.sparkline.min.js"></script>
		<script src="<%=basePath %>/assets/js/flot/jquery.flot.min.js"></script>
		<script src="<%=basePath %>/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=basePath %>/assets/js/flot/jquery.flot.resize.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.dataTables.bootstrap.js"></script>
		<!-- ace scripts -->
		<script src="<%=basePath %>/assets/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="<%=basePath %>/assets/js/date-time/bootstrap-timepicker.min.js"></script>
		<script src="<%=basePath %>/assets/js/date-time/moment.min.js"></script>
		<script src="<%=basePath %>/assets/js/date-time/daterangepicker.min.js"></script>
		<script src="<%=basePath %>/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>/assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->

<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
var basePath = '<%=basePath%>';
function user(){
	window.location.href="<%=basePath%>/user";
}
function exchange(){
	window.location.href="<%=basePath%>/exchange";
	}
function statement(){
	window.location.href="<%=basePath%>/statement";
}
function payment(){	
	window.location.href="<%=basePath%>/payment";
}
function payment2(){
	window.location.href="<%=basePath%>/payment/payment2";
}
function payment3(){
	window.location.href="<%=basePath%>/payment/payment3";
}
function mycount(){
	window.location.href="<%=basePath%>/user/count";
}
function payment5(){
	window.location.href="<%=basePath%>/payment/payment5";
}
function daifu(){
	window.location.href="<%=basePath%>/settlement/daifu";
}
function settlement(){
	window.location.href="<%=basePath%>/settlement";
}
function transfer(){
	window.location.href="<%=basePath%>/transfer";
}
function getannouncement(){
	window.location.href="<%=basePath%>/user/getannouncement?pageNum=1";
}
function getHelp(){
	window.location.href="<%=basePath%>/user/getHelp";
}
function getAbout(){
	window.location.href="<%=basePath%>/user/getAbout";
}

function agent(){
	window.location.href="<%=basePath%>/user/agent";
}
</script>
</head>
<jsp:include page="footjsp.jsp"></jsp:include>
</html>