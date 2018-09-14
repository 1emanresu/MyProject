
<%@page import="com.pay.cardyee.client.service.CardyeeService"%>
<%@page language="java" contentType="text/html;charset=gbk"%>
<%!	String formatString(String text){ 
			if(text == null) {
				return ""; 
			}
			return text;
		}
%>
<%
	String r0_Cmd 	  = formatString((String)request.getAttribute("r0_Cmd")); // 业务类型
	String r1_Code    = formatString((String)request.getAttribute("r1_Code"));// 支付结果
	String r6_Order   = formatString((String)request.getAttribute("r6_Order"));// 商户订单号
	String rq_ReturnMsg       = formatString((String)request.getAttribute("rq_ReturnMsg"));
	String hmac       = formatString((String)request.getAttribute("hmac"));// 签名数据
	System.out.println(r0_Cmd);
	out.println("r0_Cmd="+r0_Cmd);
	out.println("r1_Code="+r1_Code);
	out.println("r6_Order="+r6_Order);
	out.println("rq_ReturnMsg="+rq_ReturnMsg);
	out.println("hmac="+hmac);
%>