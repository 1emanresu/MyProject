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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<META name="Keywords" content="<%=systemss.getStr("system_keyword")%>" />
<meta name="description"
	content="<%=systemss.getStr("system_keyword")%>" />
<link rel="stylesheet" type="text/css" href="static/new/css/style.css" />
<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
<!--[if lte IE 6]>
<SCRIPT src="static/new/js/iepng.js" type="text/javascript"></SCRIPT>
 <![endif]-->
<link rel="icon" type="image/x-icon" href="<%=basePath %>/static/images/favicon16.ico" />
<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/static/images/favicon16.ico" />
<script src="<%=basePath %>/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/static/new/js/jqnav.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/static/new/js/ban.js" charset="gb2312"></script>
<script type="text/javascript" src="<%=basePath %>/static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/js/QQOnline.js"></script>
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
</head>
<jsp:include page="footjsp.jsp"></jsp:include>
</html>