<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<% 	//获取当前交易时间
	Date currTime = new Date();
    //时间以yyyy-MM-dd HH:mm:ss的方式表示
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
    //时间以yyyyMMDDHHmmss的方式表示 
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
    String webdate=new String(formatter1.format(currTime));
    String TradeDate=new String(formatter2.format(currTime));

	String MerchantID = "100754";//分配的商户号
	String TransID = MerchantID + TradeDate;//生成商户流水号
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据输入页</title>
<SCRIPT LANGUAGE="JavaScript">
    function dosubmit(){
       form1.action = "post.jsp";
       document.form1.submit();
    }
</SCRIPT>
</head>

<body>
<form method="post" name="form1" id="form1" >
<table>
<tr>
<td width="120">选择支付方式：</td>
<td width="350"><select name="PayID" id="PayID" ><!--充值方式，神州行1 联通2 电信3 盛大11 完美12 征途14 骏网一卡通15 网易 16 网银支付总1000-->
			    <option value="1">神州行</option>
                <option value="2">联通卡</option>
                <option value="3">电信卡</option>
                <option value="11">盛大卡</option>
                <option value="12">完美卡</option>
                <option value="14">征途卡</option>
                <option value="15">骏网一卡通</option>
                <option value="16">网易卡</option>
				<option value="1000">网银支付（总）</option>
				<option value="1001">招商银行</option>
				<option value="1002">工商银行</option>
				<option value="1003">建设银行</option>
				<option value="1004">浦发银行</option>
				<option value="1005">农业银行</option>
				<option value="1006">民生银行</option>
				<option value="1009">兴业银行</option>
				<option value="1020">交通银行</option>
				<option value="1022">光大银行</option>
				<option value="1026">中国银行</option>
				<option value="1032">北京银行</option>		
				<option value="1035">平安银行</option>
				<option value="1036">广发银行</option>
				<option value="1039">中信银行</option>
				<option value="1080">银联在线</option>
      </select></td>
</tr>
<tr>
<td>商户号:</td>
<td><input width="350" type="text" name="MerchantID" value="<%=MerchantID%>"/></td>
</tr>
<td>交易时间:</td>
<td><input  type='text' name='webdate' value= "<%=webdate%>" /> <input name='TradeDate' value= "<%=TradeDate%>" /></td><!--获取当前交易时间-->
</tr>
<tr>
<td>商户流水号:</td>
<td><input width="350" type="text"  name="TransID" value="<%=TransID%>" /></td>
</tr>
<tr>
<td>订单金额:</td>
<td><input width="350" type="text" name="OrderMoney" value="0.01" /><span>建议1分钱支付</span> </td>
</tr>
<tr>
<td>商品名称:</td>
<td><input width="350" type="text" name="ProductName" /></td>
</tr>
<tr>
<td>商品数量:</td>
<td><input width="350" type="text" name="Amount" /></td>
</tr>
<tr>
<td>产品logo:</td>
<td><input width="350" type="text" name="ProductLogo" /></td>
</tr>
<tr>
<td>支付用户名:</td>
<td><input width="350" type="text" name="Username" /></td>
</tr>
<tr>
<td>Email:</td>
<td><input width="350" type="text" name="Email" /></td>
</tr>
<tr>
<td>Mobile:</td>
<td><input width="350" type="text" name="Mobile" /></td>
</tr>
<tr>
<td>订单附加消息:</td>
<td><input width="350" type="text" name="AdditionalInfo" /></td>
</tr>
<tr>
<td>页面跳转地址:</td>
<td><input width="350" type="text" name="Merchant_url"  value="http://127.0.0.1:8080/test/merchant_url.jsp" />
<font color="red"><b>此地址注意更换成你们可用的通知地址</b></font>
</td> <!--页面跳转连接的商户页面地址-->
</tr>
<tr>
<td>底层访问地址:</td>
<td><input width="350" type="text" name="Return_url"  value="http://127.0.0.1:8080/test/return_url.jsp" />
<font color="red"><b>此地址注意更换成你们可用的通知地址</b></font>
</td><!--通知服务器底层地址-->
</tr>
<tr>
<td>通知方式:</td>
<td><input width="350" type="text" name="NoticeType"  value="0" />
</td><!--Notice=1时支付结束会从页面跳转到Merchant_url-->
</tr>
<tr>
</tr>
<tr>
<td colspan="2" align="center"><input type="button" name="button" value="提交" onClick="dosubmit()" /></td>
</tr>

</table>
</form>
</body>
</html>
