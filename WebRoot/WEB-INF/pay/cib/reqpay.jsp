<%@page language="java" contentType="text/html;charset=gbk"%>
<%!	String formatString(String text){ 
			if(text == null) {
				return ""; 
			}
			return text;
		}
%>
<%
	request.setCharacterEncoding("GBK");
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String nodeAuthorizationURL = formatString((String) request.getAttribute("nodeAuthorizationURL")); // 交易请求地址
	// 商家设置用户购买商品的支付信息
	String p0_Cmd = formatString((String) request.getAttribute("p0_Cmd")); // 在线支付请求，固定值 ”Buy”
	String p1_MerId = formatString((String) request.getAttribute("p1_MerId")); // 商户编号
	String p2_Order = formatString((String) request.getAttribute("p2_Order")); // 商户订单号
	String p3_Amt = formatString((String) request.getAttribute("p3_Amt")); // 支付金额
	String p4_Cur = formatString((String) request.getAttribute("p4_Cur")); // 交易币种
	String p5_Pid = formatString((String) request.getAttribute("p5_Pid")); // 商品名称
	String p6_Pcat = formatString((String) request.getAttribute("p6_Pcat")); // 商品种类
	String p7_Pdesc = formatString((String) request.getAttribute("p7_Pdesc")); // 商品描述
	String p8_Url = formatString((String) request.getAttribute("p8_Url")); // 商户接收支付成功数据的地址
	String p9_SAF = formatString((String) request.getAttribute("p9_SAF")); // 需要填写送货信息 0：不需要  1:需要
	String pa_MP =formatString((String) request.getAttribute("pa_MP")); // 商户扩展信息
	String pd_FrpId = formatString((String) request.getAttribute("pd_FrpId")); // 支付通道编码
	// 银行编号必须大写
	pd_FrpId = pd_FrpId.toUpperCase();
	String pr_NeedResponse = formatString((String) request.getAttribute("pr_NeedResponse")); // 默认为"1"，需要应答机制
	String hmac = formatString((String) request.getAttribute("hmac")); // 交易签名串
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
		<form name="yeepay" action='<%=nodeAuthorizationURL%>' method='POST'>
			<input type='hidden' name='p0_Cmd'   value='<%=p0_Cmd%>'>
			<input type='hidden' name='p1_MerId' value='<%=p1_MerId%>'>
			<input type='hidden' name='p2_Order' value='<%=p2_Order%>'>
			<input type='hidden' name='p3_Amt'   value='<%=p3_Amt%>'>
			<input type='hidden' name='p4_Cur'   value='<%=p4_Cur%>'>
			<input type='hidden' name='p5_Pid'   value='<%=p5_Pid%>'>
			<input type='hidden' name='p6_Pcat'  value='<%=p6_Pcat%>'>
			<input type='hidden' name='p7_Pdesc' value='<%=p7_Pdesc%>'>
			<input type='hidden' name='p8_Url'   value='<%=p8_Url%>'>
			<input type='hidden' name='p9_SAF'   value='<%=p9_SAF%>'>
			<input type='hidden' name='pa_MP'    value='<%=pa_MP%>'>
			<input type='hidden' name='pd_FrpId' value='<%=pd_FrpId%>'>
			<input type="hidden" name="pr_NeedResponse"  value="<%=pr_NeedResponse%>">
			<input type='hidden' name='hmac'     value='<%=hmac%>'>
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
