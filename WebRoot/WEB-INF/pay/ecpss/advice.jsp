<%@page import="com.pay.ecpss.server.MD5"%>
<%@page import="com.pay.yeepay.client.service.YeepayService"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vo.Gateway"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String BillNo = request.getParameter("BillNo");
	String Amount = request.getParameter("Amount");
	String Succeed = request.getParameter("Succeed");
	String Result = request.getParameter("Result");
	String SignMD5info = request.getParameter("SignMD5info");
	Gateway gateway = Gateway.dao.findById(12);
	String MD5key = gateway.getStr("gateway_key");
	String md5Str = BillNo +"&"+  Amount  +"&"+  Succeed +"&"+ MD5key; 
	MD5 md5 = new MD5();
	String MD5info = md5.getMD5ofStr(md5Str);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if(MD5info.equals(SignMD5info)){
		if(Succeed.equals("88")){
			out.println("ok");
			YeepayService.service.netcallback("", BillNo, "", sdf.format(new Date()), sdf.format(new Date()));
			YeepayService.service.asynchronous(BillNo);
		}else{
			out.println("ok");
			YeepayService.service.failure(BillNo, sdf.format(new Date()));
			response.sendRedirect(basePath+"/yeepay/yeeReturn?r6_Order="+BillNo);
		}
	}else{
		out.println("fail");
	}
%>