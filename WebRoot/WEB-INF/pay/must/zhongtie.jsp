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
	////////////////////////////////////请求参数//////////////////////////////////////
    String reqUrl = (String)request.getAttribute("reqUrl");
	String pay_memberid = (String)request.getAttribute("pay_memberid");
	String pay_orderid = (String)request.getAttribute("pay_orderid");
	String pay_amount = (String)request.getAttribute("pay_amount");
	String pay_applydate = (String)request.getAttribute("pay_applydate");
	String notify_url = (String)request.getAttribute("notify_url");
	String pay_md5sign = (String)request.getAttribute("pay_md5sign");
%>
	<div align="center">请稍等……</div>
	<form id="Form1" name="Form1" method="post" action="<%= reqUrl %>">
		<input type="hidden" name="pay_memberid" value="<%= pay_memberid %>">
		<input type="hidden" name="pay_orderid" value="<%= pay_orderid %>">			
		<input type="hidden" name="pay_amount" value="<%= pay_amount %>">						
		<input type="hidden" name="pay_applydate" value="<%= pay_applydate %>">
		<input type="hidden" name="pay_bankcode" value="H5WEIXINPAY">
		<input type="hidden" name="pay_notifyurl" value="<%= notify_url %>">
		<input type="hidden" name="pay_callbackurl" value="<%= notify_url %>">
		<input type="hidden" name="pay_md5sign" value="<%= pay_md5sign %>">
    </form>
    <script>
        document.Form1.submit();
    </script>
</body>
</html>
