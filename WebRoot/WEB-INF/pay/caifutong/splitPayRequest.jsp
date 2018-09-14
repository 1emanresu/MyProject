<%@page import="com.pay.caifutong.server.BaseSplitRequestHandler"%>
<%@page import="com.pay.caifutong.server.util.TenpayUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//财付通支付并分账请求示例，商户按照此文档进行开发即可
//---------------------------------------------------------
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//商户号
String bargainor_id = request.getParameter("bargainor_id");
//bargainor_id = "2000000501";
//密钥
String key = request.getParameter("key");
//key = "123456";
//返回处理地址,真实环境中请填写服务器公网IP或者域名
String return_url = request.getParameter("return_url");

//银行编号
String bank_type = request.getParameter("bank_type");

//商品名称
String desc = request.getParameter("desc");

//商品金额
String total_fee = request.getParameter("total_fee");

//ip
String ip = request.getParameter("ip");

//用户分账金额
String amts1 = request.getParameter("amts1");

//平台利润
String amts2 = request.getParameter("amts2");

//平台财付通账号
String caifutongemial = request.getParameter("caifutongemial");

//用户财付通账号
String caifu_account = request.getParameter("caifu_account");
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
//商家订单号，长度若超过32位，取前32位。财付通只记录商家订单号，不保证唯一。
String sp_billno = request.getParameter("sp_billno");
//财付通交易单号，规则为：10位商户号+8位时间（YYYYmmdd)+10位流水号，商户根据自己情况调整，只要保证唯一
String transaction_id = bargainor_id + strDate + strReq;
//---------------生成订单号 结束------------------------

//创建支付请求对象
BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(request, response);
reqHandler.init();
reqHandler.setKey(key);
reqHandler.setGateUrl("https://www.tenpay.com/cgi-bin/v4.0/pay_set.cgi");

//-----------------------------
//设置支付参数
//-----------------------------
reqHandler.setParameter("bank_type", bank_type);
reqHandler.setParameter("cmdno", "1");
reqHandler.setParameter("date", strDate);
reqHandler.setParameter("fee_type", "1");
reqHandler.setParameter("version", "4");

reqHandler.setParameter("bargainor_id", bargainor_id);			//商户号
reqHandler.setParameter("sp_billno", sp_billno);				//商家订单号
reqHandler.setParameter("transaction_id", transaction_id);		//财付通交易单号
reqHandler.setParameter("return_url", return_url);				//支付通知url
reqHandler.setParameter("desc", desc);				//商品名称
reqHandler.setParameter("total_fee", total_fee);						//商品金额,以分为单位
reqHandler.setParameter("spbill_create_ip", ip);  //用户IP

//业务类型
reqHandler.setParameter("bus_type", "97");

/**
 * 业务参数
 * 帐号^分帐金额^角色
 * 角色说明:	1:供应商 2:平台服务方 3:资金清算方 4:独立分润方
 * 帐号1^分帐金额1^角色1|帐号2^分帐金额2^角色2|...
 */
reqHandler.setParameter("bus_args", caifu_account+"^"+amts1+"^4|"+caifutongemial+"^"+amts2+"^1");
//行业描述信息
reqHandler.setParameter("bus_desc","12345^深圳-上海^1^fady^庄^13800138000");

//请求的url
String requestUrl = reqHandler.getRequestURL();

String title = request.getParameter("title");
title = new String(title.getBytes("ISO-8859-1"),"UTF-8");



//debug信息
//String debuginfo = reqHandler.getDebugInfo();
//System.out.println("debuginfo:" + debuginfo);
//System.out.println("requestUrl:" + requestUrl);

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
		<p class="pzs"><%=title %></p>
		<img src="<%=basePath %>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
		<form name="yeepay" action='<%=requestUrl%>' method='POST'>
			
		</form>
	</body>
	<script type="text/javascript">
		function sub(){
			document.yeepay.submit();
		}
		var titletime = ${param["titletime"]};
		setTimeout(sub,titletime);
	</script>
</html>
