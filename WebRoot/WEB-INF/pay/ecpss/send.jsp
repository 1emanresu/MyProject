<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String MerNo = (String)request.getAttribute("MerNo");
String BillNo = (String)request.getAttribute("BillNo");
String Amount = (String)request.getAttribute("Amount");
String ReturnURL = (String)request.getAttribute("ReturnURL");
String AdviceURL = (String)request.getAttribute("AdviceURL");
String SignInfo = (String)request.getAttribute("SignInfo");
String orderTime = (String)request.getAttribute("orderTime");
String defaultBankNumber = (String)request.getAttribute("defaultBankNumber");
String Remark = "";
String products = "";
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
<form action="https://pay.ecpss.com/sslpayment" method="POST" name="ease">
  <input type="hidden" name="MerNo"  value="<%=MerNo%>">
	<input type="hidden" name="BillNo" value="<%=BillNo%>">
	<input type="hidden" name="Amount" value="<%=Amount%>">
	<input type="hidden" name="ReturnURL" value="<%=ReturnURL%>">
	<input type="hidden" name="AdviceURL" value="<%=AdviceURL%>">
	<input type="hidden" name="SignInfo" value="<%=SignInfo%>">
	<input type="hidden" name="orderTime" value="<%=orderTime%>">
	<input type="hidden" name="defaultBankNumber" value="<%=defaultBankNumber%>">
	<input type="hidden" name="Remark"  value="<%=Remark%>">
	<input type="hidden" name="products"  value="<%=products%>">
</form>
</body>
<script type="text/javascript">
		function sub(){
			document.ease.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub,titletime);
	</script>
</html>