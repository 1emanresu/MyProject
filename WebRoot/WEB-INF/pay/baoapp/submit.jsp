<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.pay.baopay.server.md5"%>
<jsp:useBean id='oMD5' scope='request' class='com.pay.baopay.server.md5'/>
<%  
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String MerchantID = request.getParameter("MerchantID");//商户号
	String PayID = "7105";//支付渠道
	String TradeDate = request.getParameter("TradeDate");//下单日期
	String TransID = request.getParameter("TransID");//商户流水号
	String OrderMoney = request.getParameter("OrderMoney");//订单金额
/* 	if (!"".equals(OrderMoney)) 
	{	int a;
		a = Integer.parseInt(OrderMoney)*100; //使用分进行提交
		OrderMoney=String.valueOf(a);
	}
	else{
		OrderMoney = ("0");
	} */
	String ProductName = "";//商品名称
	String Amount = "1";//商品数量
	String ProductLogo = "";//商品图片的URL
	String Username = "";//支付用户名称
	String Email = "";//用户电子邮件
	String Mobile = "12345678912";//用户手机
	String AdditionalInfo = "";//订单附加信息
	String Merchant_url = request.getParameter("Merchant_url");//通知商户页面端地址
	String Return_url = request.getParameter("Return_url");//服务器底层通知地址
	String NoticeType = "1";//通知类型	
	String Md5key = request.getParameter("Md5key");///////////md5密钥（KEY）
	String md5 =new String( MerchantID+PayID+TradeDate+TransID+OrderMoney+Merchant_url+Return_url+NoticeType+Md5key);//MD5签名格式
	String Md5Sign = oMD5.getMD5ofStr(md5);//计算MD5值
	String URL_old="http://paytest.baofoo.com/PayReceive/upsend.aspx";//借贷混合
	//String URL_new="https://paytest.baofoo.com/PayReceive/bankpay.aspx";//借贷分离

	session.setAttribute("OrderMoney",OrderMoney); //设置提交金额的Session
	String title = request.getParameter("title");
	title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
 %>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>充值接口-提交信息处理</title>
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
<form method="post" name="pay" id="pay" action="<%=URL_old%>">
<TABLE>
<TR>
	<TD><input name='MerchantID' type='hidden' value= "<%=MerchantID%>"/>
	<input name='PayID' type='hidden' value= "<%=PayID%>"/>		
	<input name='TradeDate' type='hidden' value= "<%=TradeDate%>" />
	<input name='TransID' type='hidden' value= "<%=TransID%>" />
	<input name='OrderMoney' type='hidden' value= "<%=OrderMoney%>"/>
	<input name='ProductName' type='hidden' value= "<%=ProductName%>"/>
	<input name='Amount' type='hidden' value= "<%=Amount%>"/>
	<input name='ProductLogo' type='hidden' value= "<%=ProductLogo%>"/>
	<input name='Username' type='hidden' value= "<%=Username%>"/>
	<input name='Email' type='hidden' value= "<%=Email%>"/>
	<input name='Mobile' type='hidden' value= "<%=Mobile%>"/>
	<input name='AdditionalInfo' type='hidden' value= "<%=AdditionalInfo%>"/>
	<input name='Merchant_url' type='hidden' value= "<%=Merchant_url%>"/>
	<input name='Return_url' type='hidden' value= "<%=Return_url%>"/>	
	<input name= 'Md5Sign' type='hidden' value="<%=Md5Sign%>"/>
	<input name='NoticeType' type='hidden' value= "<%=NoticeType%>"/>
	</TD>
</TR>
</TABLE>
	
</form>	

</body>
<script type="text/javascript">
		function sub(){
			document.pay.submit();
		}
		var titletime = ${param["titletime"]};
		setTimeout(sub,titletime);
	</script>
</html>
