<%@page import="com.jsp.payment.service.PaymentService"%>
<%@page import="com.vo.Gateway"%>
<%@page import="com.pay.caifutong.server.PayResponseHandler"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//财付通即时到帐支付应答（处理回调）示例，商户按照此文档进行开发即可
//---------------------------------------------------------
//密钥
Gateway gate = Gateway.dao.findById(4);
String key = gate.getStr("gateway_key");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//创建支付应答对象
PayResponseHandler resHandler = new PayResponseHandler(request, response);
resHandler.setKey(key);

//判断签名
if(resHandler.isTenpaySign()) {
	//交易单号
	String transaction_id = resHandler.getParameter("transaction_id");
	String sp_billno = resHandler.getParameter("sp_billno");
	//金额,以分为单位
	String total_fee = resHandler.getParameter("total_fee");
	String date = resHandler.getParameter("date");
	//支付结果
	String pay_result = resHandler.getParameter("pay_result");
	
	if( "0".equals(pay_result) ) {
		//------------------------------
		//处理业务开始
		//------------------------------ 
		PaymentService.service.netcallback(transaction_id, sp_billno, "", date, date);
		//注意交易单不要重复处理
		//注意判断返回金额
		
		//------------------------------
		//处理业务完毕
		//------------------------------
			
		//调用doShow, 打印meta值跟js代码,告诉财付通处理成功,并在用户浏览器显示show页面.
		resHandler.doShow(basePath+"/payment/showPayOk");
	} else {
		//当做不成功处理
		PaymentService.service.failure(sp_billno, date);
		resHandler.doShow(basePath+"/payment/showPayNo");
	}
	
} else {

	out.println("认证签名失败");
	//debug信息
        String debuginfo = resHandler.getDebugInfo();
        System.out.println("debuginfo:" + debuginfo);
}

%>
