<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset="utf-8">
</head>
<body>
<div>请稍后,正在请求服务器,如果5秒未相应,请点击<span onClick="submit()">"提交订单"</span></div>
	<form id="form" action="https://cardpay.shengpay.com/mobile-acquire-channel/cashier.htm" method="post">	
		<input type="hidden" name="Name" value="${Name}">
		<input type="hidden" name="Version" value="${Version}">
		<input type="hidden" name="Charset" value="${Charset}">
		<input type="hidden" name="TraceNo" value="${TraceNo}">
		<input type="hidden" name="MsgSender" value="${MsgSender}">

		<input type="hidden" name="SendTime" value="${SendTime}">
		<input type="hidden" name="OrderNo" value="${OrderNo}">
		<input type="hidden" name="OrderAmount" value="${OrderAmount}">
		<input type="hidden" name="OrderTime" value="${OrderTime}">
		<input type="hidden" name="Currency" value="${Currency}">

		<input type="hidden" name="PayType" value="${PayType}">
		<input type="hidden" name="PayChannel" value="${PayChannel}">
		<input type="hidden" name="PageUrl" value="${PageUrl}">
		<input type="hidden" name="NotifyUrl" value="${NotifyUrl}">
		<input type="hidden" name="SignType" value="${SignType}">
		
		<input type="hidden" name="ProductName" value="${ProductName}">
		<input type="hidden" name="Ext2" value="${Ext2}">
		<input type="hidden" name="SignMsg" value="${SignMsg}">
	</form>

		Name:<input type="text" name="Name" value="${Name}"><br>
		Version:<input type="text" name="Version" value="${Version}"><br>
		Charset:<input type="text" name="Charset" value="${Charset}"><br>
		TraceNo:<input type="text" name="TraceNo" value="${TraceNo}"><br>
		MsgSender:<input type="text" name="MsgSender" value="${MsgSender}"><br>

		SendTime:<input type="text" name="SendTime" value="${SendTime}"><br>
		OrderNo:<input type="text" name="OrderNo" value="${OrderNo}"><br>
		OrderAmount:<input type="text" name="OrderAmount" value="${OrderAmount}"><br>
		OrderTime:<input type="text" name="OrderTime" value="${OrderTime}"><br>
		Currency:<input type="text" name="Currency" value="${Currency}"><br>

		PayType:<input type="text" name="PayType" value="${PayType}"><br>
		PayChannel:<input type="text" name="PayChannel" value="${PayChannel}"><br>
		PageUrl:<input type="text" name="PageUrl" value="${PageUrl}"><br>
		NotifyUrl:<input type="text" name="NotifyUrl" value="${NotifyUrl}"><br>
		SignType:<input type="text" name="SignType" value="${SignType}"><br>
		
		ProductName:<input type="text" name="ProductName" value="${ProductName}"><br>
		Ext2:<input type="text" name="Ext2" value="${Ext2}"><br>
		SignMsg:<input type="text" name="SignMsg" value="${SignMsg}"><br>

<script type="text/javascript">
	function submit(){
		document.getElementById('form').submit();
	}
	//document.getElementById('form').submit();
</script>
</body>
</html>