
<%@page import="com.jsp.payment.service.PaymentService"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.pay.yeepay.client.service.YeepayService"%>
<%@page import="com.vo.Gateway"%>
<%@page import="com.pay.w95epay.server.MD5Util"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String CharacterEncoding = "UTF-8";
	request.setCharacterEncoding(CharacterEncoding);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	Gateway gateway = Gateway.dao.findById(15);
	String MerNo = gateway.getStr("gateway_merid");   // 商家密钥gateway_merid
	String MD5key = gateway.getStr("gateway_key");   // 商户编号

	String BillNo = request.getParameter("BillNo");

	String Amount = request.getParameter("Amount");
	String Succeed = request.getParameter("Succeed");
	String Result = request.getParameter("Result");

	String MD5info = request.getParameter("MD5info");
	String md5str;
	MD5Util md5util = new MD5Util();
	md5str = md5util.signMap(new String[] { MerNo, BillNo, Amount,
			String.valueOf(Succeed) }, MD5key, "RES");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if (MD5info.equals(md5str)) {
		if (Succeed.equals("88")) {
			//请修改订单成功状态,或者其他操作
			String billnostr = BillNo.substring(0,1);
			String billnos = BillNo.substring(0,2);
			if(billnostr.equals("O")){
				System.out.println("接口订单进行时");
				YeepayService.service.netcallback("", BillNo, "", sdf.format(new Date()), sdf.format(new Date()));
				response.sendRedirect(basePath+"/w95epay/w95epayreturn?orderId="+BillNo);
				System.out.println("接口订单结束");
			}else if(billnos.equals("RE")){
				System.out.println("充值订单进行时");
				PaymentService.service.netcallback("", BillNo, "",sdf.format(new Date()), sdf.format(new Date()));
				response.sendRedirect(basePath + "/payment/showPayOk");
				System.out.println("充值订单结束");
			}
			//在界面显示，付款结果提示信息 Result
		} else {//失败处理
				//请修改订单状态,或者其他操作
			String billnostr = BillNo.substring(0,1);
			String billnos = BillNo.substring(0,2);
			if(billnostr.equals("O")){
				System.out.println("接口订单进行时");
				YeepayService.service.failure(BillNo, sdf.format(new Date()));
				response.sendRedirect(basePath+"/yeepay/yeeReturn?r6_Order="+BillNo);
				System.out.println("接口订单结束");
			}else if(billnos.equals("RE")){
				System.out.println("充值订单进行时");
				PaymentService.service.failure(BillNo,sdf.format(new Date()));
				response.sendRedirect(basePath + "/payment/showPayNo");
				System.out.println("充值订单结束");
			}
				//在界面显示，付款结果提示信息 Result
		}
	}else {
		out.println("交易签名被篡改!");
	}
%>

