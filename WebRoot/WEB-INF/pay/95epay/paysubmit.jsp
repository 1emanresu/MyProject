<%@page import="com.pay.w95epay.server.MD5Util"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

	String MerNo = (String) request.getAttribute("MerNo");

	String MD5key = (String) request.getAttribute("MD5key");

	String BillNo = (String) request.getAttribute("BillNo");

	String Amount = (String) request.getAttribute("Amount");

	String PaymentType = (String) request.getAttribute("PaymentType");

	//PaymentType = 银行代码:(空值为跳转到银联)
	//工行		"ICBC"
	//农行		"ABC"
	//中行		"BOCSH"
	//建行		"CCB"
	//招行		"CMB"
	//浦发		"SPDB"
	//广发		"GDB"
	//交行		"BOCOM"
	//邮储		"PSBC"
	//中信		"CNCB"
	//民生		"CMBC"
	//光大		"CEB"
	//华夏		"HXB"
	//兴业		"CIB"

	String PayType = "CSPAY";//CSPAY:网银支付 ; NCPAY:无卡支付;UNION:银联无卡网银合并

	String ReturnURL =  (String) request.getAttribute("ReturnURL"); //(返回地址必须绑定到95epay后台,否则无法支付,格式为:http://网店域名/payresult.jsp)

	String MD5info;

	MD5Util md5util = new MD5Util();

	MD5info = md5util.signMap(new String[]{MerNo, BillNo, Amount,
			ReturnURL}, MD5key, "REQ");

	String MerRemark; //

	MerRemark = "";
	String SubMerNo = "";
	String Products = ""; ////https://payment.95epay.com/sslpayment
%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>

<title>提交交易</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body {
	text-align: center;
	margin: 0 atuto;
}

.pzs {
	margin: 0;
	font-family: 'Karla', sans-serif;
	font-weight: bold;
	color: #317eac;
	text-rendering: optimizelegibility;
}
</style>
</head>
<body>
	<div style="margin-top: 250px" align="center">
		<p class="pzs">${title }</p>
		<img src="<%=basePath%>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
	<form name="w95epay" action="https://www.95epay.cn/sslpayment"
		method="post">

		<input type="hidden" name="MerNo" value="<%=MerNo%>" /> <input
			type="hidden" name="BillNo" value="<%=BillNo%>" /> <input
			type="hidden" name="Amount" value="<%=Amount%>" /> <input
			type="hidden" name="PaymentType" value="<%=PaymentType%>" /> <input
			type="hidden" name="PayType" value="<%=PayType%>"> <input
			type="hidden" name="ReturnURL" value="<%=ReturnURL%>" /> <input
			type="hidden" name="MD5info" value="<%=MD5info%>" /> <input
			type="hidden" name="MerRemark" value="<%=MerRemark%>" /> <input
			type="hidden" name="products" value="<%=Products%>" />
			<input
			type="hidden" name="SubMerNo" value="<%=SubMerNo%>" />
	</form>
	<script type="text/javascript">
		function sub() {
			document.w95epay.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub, titletime);
	</script>
</body>
</html>