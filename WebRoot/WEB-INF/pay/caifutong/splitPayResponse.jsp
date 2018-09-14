<%@page import="com.pay.caifutong.client.service.CaiFuTongService"%>
<%@page import="com.pay.yeepay.client.service.YeepayService"%>
<%@page import="com.vo.Gateway"%>
<%@page import="com.pay.caifutong.server.ResponseHandler"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//财付通支付分帐回调处理示例，商户按照此文档进行开发即可
//---------------------------------------------------------
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//密钥
Gateway gate = Gateway.dao.findById(4);
String key = gate.getStr("gateway_key");
//key = "123456";

//创建支付应答对象
ResponseHandler resHandler = new ResponseHandler(request, response);
resHandler.setKey(key);

//支付结果、状态码
String pay_result = resHandler.getParameter("pay_result");

//支付信息、错误信息
String pay_info = resHandler.getParameter("pay_info");

//判断签名
if(resHandler.isTenpaySign()) {
	//交易单号
	String transaction_id = resHandler.getParameter("transaction_id");
	//订单号
	String sp_billno = resHandler.getParameter("sp_billno");
	//日期
	String date = resHandler.getParameter("date");
	//金额,以分为单位
	String total_fee = resHandler.getParameter("total_fee");
	//用户财付通账号
	String purchaser_id = resHandler.getParameter("purchaser_id");
	//业务参数
	String bus_args = resHandler.getParameter("bus_args");
	
	if( "0".equals(pay_result) ) {
		//------------------------------
		//处理业务开始
		//------------------------------ 
		CaiFuTongService.service.netcallback(transaction_id, sp_billno, "", date, date);
		//注意交易单不要重复处理
		//注意判断返回金额
		CaiFuTongService.service.caifutongOrder(sp_billno, transaction_id, purchaser_id, date, total_fee, bus_args);
		//------------------------------
		//处理业务完毕
		//------------------------------
	
		//调用doShow, 打印meta值跟js代码,告诉财付通处理成功,并在用户浏览器显示show页面.
		resHandler.doShow(basePath+"/caifutong/caifureturn?orderId="+sp_billno);
	} else {
		//当做不成功处理
		YeepayService.service.failure(sp_billno, date);
		resHandler.doShow(basePath+"/yeepay/yeeReturn?r6_Order="+sp_billno);
	}
	
} else {

	out.println("认证签名失败");
	out.println("状态码：" + pay_result);
	out.println("错误信息：" + pay_info);	
	
	//调试信息
	/*
	String debugInfo = resHandler.getDebugInfo();
	System.out.println("debugInfo:" + debugInfo);
	*/
}

%>

