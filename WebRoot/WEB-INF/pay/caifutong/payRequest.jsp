<%@page import="com.pay.caifutong.server.PayRequestHandler"%>
<%@page import="com.pay.caifutong.server.util.TenpayUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.text.SimpleDateFormat" %>    
<%@ page import="java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//商户号
String bargainor_id = (String)request.getAttribute("bargainor_id");

//密钥
String key = (String)request.getAttribute("key");

//返回处理地址,真实环境中请填写服务器公网IP或者域名
String return_url = (String)request.getAttribute("return_url");

//---------------生成订单号 开始------------------------
//当前时间 yyyyMMddHHmmss
String currTime = TenpayUtil.getCurrTime();
//8位日期
String strDate = currTime.substring(0, 8);
//6位时间
String strTime = currTime.substring(8, currTime.length());
//四位随机数
String strRandom = TenpayUtil.buildRandom(4) + "";
//10位序列号,可以自行调整。
String strReq = strTime + strRandom;
//商家订单号,长度若超过32位，取前32位。财付通只记录商家订单号，不保证唯一。
String sp_billno = (String)request.getAttribute("sp_billno");
//财付通交易单号，规则为：10位商户号+8位时间（YYYYmmdd)+10位流水号
String transaction_id = bargainor_id + strDate + strReq;
//---------------生成订单号 结束------------------------

//创建支付请求对象
PayRequestHandler reqHandler = new PayRequestHandler(request, response);
reqHandler.init();
//设置密钥
reqHandler.setKey(key);
reqHandler.setGateUrl("https://www.tenpay.com/cgi-bin/v1.0/pay_gate.cgi");
request.setCharacterEncoding("GBK");
//System.out.println("+++" + request.getCharacterEncoding());
//-----------------------------
//设置支付参数
//-----------------------------
reqHandler.setParameter("bargainor_id", bargainor_id);		//商户号
reqHandler.setParameter("sp_billno", sp_billno);		//商家订单号
reqHandler.setParameter("transaction_id", transaction_id);	//财付通交易单号
reqHandler.setParameter("total_fee", (String)request.getAttribute("total_fee"));			//商品金额,以分为单位
reqHandler.setParameter("return_url", return_url);		//返回处理地址
reqHandler.setParameter("desc", (String)request.getAttribute("desc"));	                        //商品名称
reqHandler.setParameter("bank_type", (String)request.getAttribute("bank_type"));

//用户的公网ip
reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());

//请求的url
String requestUrl = reqHandler.getRequestURL();

//debug信息
String debuginfo = reqHandler.getDebugInfo();

//System.out.println("requestUrl:" + requestUrl);
//System.out.println("debuginfo:" + debuginfo);

%>
<html>
<head>
<style>
body{text-align:center;margin:0 atuto;}
.pzs{
	margin: 0;font-family: 'Karla', sans-serif;
  font-weight: bold;
  color: #317eac;
  text-rendering: optimizelegibility;
}
</style>
</head>
	<body>
	<div style="margin-top: 250px" align="center" >
		<p class="pzs">${title }</p>
		<img src="<%=basePath %>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
		<form name="yeepay" action='<%=requestUrl%>' method='POST'>
			
		</form>
	</body>
	<script type="text/javascript">
		function sub(){
			document.yeepay.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub,titletime);
	</script>
</html>
