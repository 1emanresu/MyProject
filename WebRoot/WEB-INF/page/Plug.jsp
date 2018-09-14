<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=systemss.getStr("system_webName") %></title>
<META name="Keywords" content="<%=systemss.getStr("system_keyword") %>" />
<link href="<%=basePath%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
<!-- Loading Flat UI -->
<link href="<%=basePath%>/css/flat-ui.css" rel="stylesheet">
<link href="<%=basePath%>/css/demo.css" rel="stylesheet">

<link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="<%=basePath%>/js/respond.js"></script>
      <script src="<%=basePath%>/js/html5shiv.js"></script>
<![endif]-->
<script src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=basePath%>/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/bootstrap-select.js"></script>
<script src="<%=basePath%>/js/bootstrap-switch.js"></script>
<script src="<%=basePath%>/js/flatui-checkbox.js"></script>
<script src="<%=basePath%>/js/flatui-radio.js"></script>
<script src="<%=basePath%>/js/jquery.tagsinput.js"></script>
<script src="<%=basePath%>/js/jquery.placeholder.js"></script>
<script src="<%=basePath%>/js/jquery.stacktable.js"></script>
<script src="<%=basePath%>/js/application.js"></script>
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
</head>
<jsp:include page="footjsp.jsp"></jsp:include>
</html>