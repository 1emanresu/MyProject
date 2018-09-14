<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mustpay.config.*"%>
<%@ page import="com.mustpay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.math.BigDecimal"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>688手游统一下单支付</title>
</head>
<%
		////////////////////////////////////请求参数//////////////////////////////////////
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = (String)request.getAttribute("out_trade_no");
		
        //商品名称，必填
        String subject = (String)request.getAttribute("subject");
		
        //付款金额，必填（单位：分）
        String total_fee = (String)request.getAttribute("total_fee");
		
        //商品展示的超链接，选填
        String show_url = "";
		
        //商品描述，选填
        String body = (String)request.getAttribute("body");	
		String prepayId=(String)request.getAttribute("prepayId");	
		System.out.println("prepayId====:"+prepayId);
	%>
<body>
	<div align="center">请稍等……</div>
	<input type="hidden" id="prepayId" value="<%= prepayId%>">
	<script src="<%=basePath%>/nb/zepto.min.js"></script>
	<script src="https://www.mustpay.com.cn/service/js/h5sdk.js"></script>
	<script>
	alert('${prepay_id}');
				MUSTPAY.init({
					'apps_id': '<%=MustpayConfig.APPS_ID%>', //MustPay系统分配的应用ID号
		            'prepay_id': '<%= prepayId%>', //商户通过统一下单接口获取的预支付ID
		            'pay_type': 'ali_pay_wap' //开通的通道简称
				});
		</script>
</body>
</html>
