<%@page import="com.jsp.payment.service.PaymentService"%>
<%@page import="com.vo.Gateway"%>
<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

String billno=request.getParameter("billno");
String currency_type=request.getParameter("Currency_type");
String amount=request.getParameter("amount");
String mydate=request.getParameter("date");
String succ=request.getParameter("succ");
String msg=request.getParameter("msg");
String attach=request.getParameter("attach");
String ipsbillno=request.getParameter("ipsbillno");
String retEncodeType=request.getParameter("retencodetype");
String signature=request.getParameter("signature");
String bankbillno = request.getParameter("bankbillno");
//返回订单加密的明文:billno+【订单编号】+currencytype+【币种】+amount+【订单金额】+date+【订单日期】+succ+【成功标志】+ipsbillno+【IPS订单编号】+retencodetype +【交易返回签名方式】+【商户内部证书】 
String content="billno"+billno + "currencytype"+currency_type+"amount"+amount+"date"+mydate+"succ"+succ+"ipsbillno"+ipsbillno+"retencodetype"+retEncodeType;  //明文：订单编号+订单金额+订单日期+成功标志+IPS订单编号+币种

boolean verify = false;

//验证方式：16-md5withRSA  17-md5
if(retEncodeType.equals("16"))
{
	cryptix.jce.provider.MD5WithRSA a=new cryptix.jce.provider.MD5WithRSA();
	a.verifysignature(content, signature, "c:\\PubKey\\publickey.txt");

	//Md5withRSA验证返回代码含义
	//-99 未处理
	//-1 公钥路径错
	//-2 公钥路径为空
	//-3 读取公钥失败
	//-4 验证失败，格式错误
	//1： 验证失败
	//0: 成功
	if (a.getresult() == 0)
	{
		verify = true;
	}	
}
else if(retEncodeType.equals("17"))
{
	Gateway gateway = Gateway.dao.findById(14);
	//登陆http://merchant.ips.com.cn/商户后台下载的商户证书内容
	String md5Key = gateway.getStr("gateway_key");
	cryptix.jce.provider.MD5 b=new cryptix.jce.provider.MD5();
	String SignMD5 = b.toMD5(content + md5Key).toLowerCase();
	
	if(SignMD5.equals(signature))
	{
		verify = true;
	}
}

if(verify)
{
	if (succ!=null)
	{
	 if(succ.equalsIgnoreCase("Y"))
	 {
		 PaymentService.service.netcallback(ipsbillno, billno, bankbillno, mydate, mydate);
	 }
	 else
	 {
		 PaymentService.service.failure(billno, mydate);
		 response.sendRedirect(basePath+"/payment/showPayNo");
	 }
	}
}
else
{
 out.println("验证失败");
}
%>