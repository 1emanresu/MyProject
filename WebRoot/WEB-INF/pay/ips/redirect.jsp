<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.text.DecimalFormat"%> 
<%
//设置请求字符集 避免产生乱码	
request.setCharacterEncoding("gb2312");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//提交地址
String form_url;

	form_url = "http://pay.ips.net.cn/ipayment.aspx"; //测试

	//form_url = "https://pay.ips.com.cn/ipayment.aspx"; //正式

//商户号
String Mer_code = (String)request.getAttribute("Mer_code");

//商户证书：登陆http://merchant.ips.com.cn/商户后台下载的商户证书内容
String Mer_key = (String)request.getAttribute("Mer_key");

//商户订单编号
String Billno = (String)request.getAttribute("Billno");

//订单金额(保留2位小数)
DecimalFormat currentNumberFormat=new DecimalFormat("#0.00"); 
String Amount = currentNumberFormat.format(Double.parseDouble((String)request.getAttribute("Amount")));
 
//订单日期
String Date = (String)request.getAttribute("Date");

//币种  RMB 人民币
String Currency_Type = "RMB" ;

//支付卡种
String Gateway_Type = "01";

//语言
String Lang = "GB";

//支付结果成功返回的商户URL
String Merchanturl = (String)request.getAttribute("Merchanturl");

//支付结果失败返回的商户URL
String FailUrl = (String)request.getAttribute("FailUrl");

//支付结果错误返回的商户URL  建议传空
String ErrorUrl = "" ;

//商户数据包
String Attach = "";

//订单支付接口加密方式
String OrderEncodeType = "5";

//交易返回接口加密方式 
String RetEncodeType = "17";

//返回方式
String Rettype = "1";

//Server to Server 返回页面URL
String ServerUrl = (String)request.getAttribute("ServerUrl");

String Bankco = (String)request.getAttribute("Bankco");

String DoCredit = "";

if(!Bankco.equals("")){
	DoCredit = "1";
}

//订单支付接口的Md5摘要，原文=订单号+金额+日期+支付币种+商户证书 
cryptix.jce.provider.MD5 b=new cryptix.jce.provider.MD5();
//订单加密的明文 billno+【订单编号】+ currencytype +【币种】+ amount +【订单金额】+ date +【订单日期】+ orderencodetype +【订单支付接口加密方式】+【商户内部证书字符串】 
String SignMD5 = b.toMD5("billno"+Billno +"currencytype"+Currency_Type+"amount"+ Amount + "date" +Date +"orderencodetype"+OrderEncodeType + Mer_key).toLowerCase();
%>
<html>
  <head>
    <title>跳转......</title>
    <meta http-equiv="content-Type" content="text/html; charset=gb2312" />
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
    <form action="<%=form_url%>" method="post" name="ips">
      <input type="hidden" name="Mer_code" value="<%=Mer_code%>">
      <input type="hidden" name="Billno" value="<%=Billno%>">
      <input type="hidden" name="Amount" value="<%=Amount%>" >
      <input type="hidden" name="Date" value="<%=Date%>">
      <input type="hidden" name="Currency_Type" value="<%=Currency_Type%>">
      <input type="hidden" name="Gateway_Type" value="<%=Gateway_Type%>">
      <input type="hidden" name="Lang" value="<%=Lang%>">
      <input type="hidden" name="Merchanturl" value="<%=Merchanturl%>">
      <input type="hidden" name="FailUrl" value="<%=FailUrl%>">
      <input type="hidden" name="ErrorUrl" value="<%=ErrorUrl%>">
      <input type="hidden" name="Attach" value="<%=Attach%>">
      <input type="hidden" name="OrderEncodeType" value="<%=OrderEncodeType%>">
      <input type="hidden" name="RetEncodeType" value="<%=RetEncodeType%>">
      <input type="hidden" name="Rettype" value="<%=Rettype%>">
      <input type="hidden" name="ServerUrl" value="<%=ServerUrl%>">
      <input type="hidden" name="SignMD5" value="<%=SignMD5%>">
      <%
      	if(!Bankco.equals("")){
      %>
      <input type="hidden" name="Bankco" value="<%=Bankco %>" />
      <input type="hidden" name="DoCredit" value="<%=DoCredit %>" />
      <%
      	}
      %>
    </form>
    <script type="text/javascript">
		function sub(){
			document.ips.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub,titletime);
	</script>
  </body>
</html>
