<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" import="java.text.*" import="com.pay.baopay.server.md5"%>
<jsp:useBean id='oMD5' scope='request' class='com.pay.baopay.server.md5'/><%
String MID = request.getParameter("MerchantID");//商户号
String TID = request.getParameter("TransID");//商户流水号
String Md5key = "gamzkmjdrbpbct9v"; ///////////md5密钥（KEY）
String md5 = new String(MID+TID+Md5key);//MD5签名格式
String Md5Sign = oMD5.getMD5ofStr(md5);//计算MD5值

//抓取OrderQuery.aspx页面提交后的数据
String sCurrentLine=new String();
String sTotalString=new String();
java.io.InputStream l_urlStream;
String url="http://paytest.baofoo.com/Check/OrderQuery.aspx?MerchantID="+MID+"&TransID="+TID+"&Md5Sign="+Md5Sign;  
java.net.URL l_url = new java.net.URL(url);
java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();
l_connection.connect();
l_urlStream = l_connection.getInputStream();
java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));
while ((sCurrentLine = l_reader.readLine())!=null)
{
	sTotalString+=sCurrentLine;     
}

//根据分割符把数据提取到数组
String[] s;
StringTokenizer st = new StringTokenizer(sTotalString,"\\|");
s=new String[st.countTokens()];
for(int i=0;st.hasMoreTokens();i++)
	{
		s[i]=st.nextToken();
	}
String MerchantID = s[0]; //商户号
String TransID = s[1]; //商户流水号
String CheckResult = s[2]; //支付结果
String factMoney = s[3];//实际成功金额,分为单位
Float Money = Float.parseFloat(factMoney)/100; //成功金额，使用元单位
String SuccTime = s[4];//支付完成时间
String WaitSign = s[5];//MD5签名
String MD5 = new String(MerchantID+TransID+CheckResult+factMoney+SuccTime+Md5key);//MD5签名格式
String MD5Sign = oMD5.getMD5ofStr(MD5);//计算MD5值
if(WaitSign.compareTo(MD5Sign)==0){			
		out.println("<script>alert('Md5OK');</script>");	//MD5校验通过
	}else{
		out.println("<script>alert('Md5CheckFail');</script>");//MD5校验失败，订单信息不显示
		MerchantID="";
		TransID="";
		Money=null;
		SuccTime="";
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>充值接口-商户充值结果</title>
<!-- TemplateEndEditable -->
<!-- TemplateBeginEditable name="head" -->
<!-- TemplateEndEditable -->
</head>
<body>
<form id="form1" method="get" name="form1">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td height="30" align="center">
				<h1>
					※ 宝付查询接口 ※
				</h1>
			</td>
		</tr>
	</table>
	<table bordercolor="#cccccc" cellspacing="5" cellpadding="0" width="400" align="center"
		border="0">
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				商户号：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
			<input  name='MerchantID' value= "<%=MerchantID%>"/>
				</td>
		</tr>
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				商户订单号：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
			<input  name='TransID' value= "<%=TransID%>"/>
				</td>
		</tr>
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				支付结果描述：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
			<input  name='CheckResult' value= "<%=CheckResult%>"/> <!--//支付结果----Y：成功 F：失败 P：处理中 N：没有订单-->
				</td>
		</tr>
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				支付成功金额：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
			<input  name='factMoney'  value= "<%=Money%>"/>
				</td>
		</tr>			
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				交易成功时间：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
			<input  name='SuccTime' value= "<%=SuccTime%>"/>
			</td>
		</tr>		
	</table> 
</form>
</body>
</html>
