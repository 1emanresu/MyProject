<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mustpay.config.*"%>
<%@ page import="com.mustpay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.math.BigDecimal"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>688手游统一下单支付</title>
</head>
<body>
<%
    String reqUrl = (String)request.getAttribute("reqUrl");
%>
	<div align="center">请稍等……</div>
    <script>
	window.location.href=<%= reqUrl %>; 
    </script>
</body>
</html>
