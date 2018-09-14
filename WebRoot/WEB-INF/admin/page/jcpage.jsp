<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=systemss.getStr("system_webName") %></title>
<META name="Keywords" content="<%=systemss.getStr("system_keyword") %>" />
<!-- Zv2joq5E4xNszwIVvi3qUWVJmJQ -->

<link href="<%=basePath %>/css/fang.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/admin/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath %>/admin/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath %>/admin/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=basePath %>/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="<%=basePath %>/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="<%=basePath %>/dwz/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="<%=basePath %>/dwz/jquery-1.7.2.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="<%=basePath %>/dwz/chart/raphael.js"></script>
<script type="text/javascript" src="<%=basePath %>/dwz/chart/g.raphael.js"></script>
<script type="text/javascript" src="<%=basePath %>/dwz/chart/g.bar.js"></script>
<script type="text/javascript" src="<%=basePath %>/dwz/chart/g.line.js"></script>
<script type="text/javascript" src="<%=basePath %>/dwz/chart/g.pie.js"></script>
<script type="text/javascript" src="<%=basePath %>/dwz/chart/g.dot.js"></script>
<script src="<%=basePath %>/dwz/dwz.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/dwz/dwz.regional.zh.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/dwz/jquery.jclock.js"></script>
</head>
</html>