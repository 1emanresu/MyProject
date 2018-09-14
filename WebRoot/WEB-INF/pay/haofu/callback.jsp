<%@page import="com.vo.Gateway"%>
<%@page import="com.pay.yeepay.client.service.YeepayService"%>
<%@page import="com.pay.yeepay.server.PaymentForOnlineService"%>
<%@page import="com.pay.yeepay.server.Configuration"%>
<%@page language="java" contentType="text/html;charset=gbk"%>
<%!	String formatString(String text){ 
			if(text == null) {
				return ""; 
			}
			return text;
		}
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String r0_Cmd 	  = formatString(request.getParameter("r0_Cmd")); // 业务类型
	String r1_Code    = formatString(request.getParameter("r1_Code"));// 支付结果
	String r2_TrxId   = formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
	String r3_Amt     = formatString(request.getParameter("r3_Amt"));// 支付金额
	String r4_Cur     = formatString(request.getParameter("r4_Cur"));// 交易币种
	String r5_Pid     = new String(formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"),"gbk");// 商品名称
	String r6_Order   = formatString(request.getParameter("r6_Order"));// 商户订单号
	String r7_Uid     = formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
	String r8_MP      = new String(formatString(request.getParameter("r8_MP")).getBytes("iso-8859-1"),"gbk");// 商户扩展信息
	String r9_BType   = formatString(request.getParameter("r9_BType"));// 交易结果返回类型
	String ru_Trxtime       = formatString(request.getParameter("ru_Trxtime"));
	String ro_BankOrderId       = formatString(request.getParameter("ro_BankOrderId"));
	String rp_PayDate       = formatString(request.getParameter("rp_PayDate"));
	String hmac       = formatString(request.getParameter("hmac"));// 签名数据
	
	Gateway gateway = Gateway.dao.findById(7);
	String keyValue = gateway.getStr("gateway_key");   // 商家密钥
	String p1_MerId = gateway.getStr("gateway_merid");   // 商户编号
	
	boolean isOK = false;
	// 校验返回数据包
	isOK = PaymentForOnlineService.verifyCallback(hmac,p1_MerId,r0_Cmd,r1_Code,r2_TrxId,r3_Amt,r4_Cur,r5_Pid,r6_Order,r7_Uid,r8_MP,r9_BType,keyValue);
	if(isOK) {
		//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
		if(r1_Code.equals("1")) {
			// 产品通用接口支付成功返回-浏览器重定向
			if(r9_BType.equals("1")) {
				YeepayService.service.netcallback(r2_TrxId, r6_Order, ro_BankOrderId, rp_PayDate, ru_Trxtime);
				response.sendRedirect(basePath+"/yeepay/yeeReturn?r6_Order="+r6_Order);
//				out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
				// 产品通用接口支付成功返回-服务器点对点通讯
			} else if(r9_BType.equals("2")) {
				// 如果在发起交易请求时	设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
				out.println("SUCCESS");
				YeepayService.service.netcallback(r2_TrxId, r6_Order, ro_BankOrderId, rp_PayDate, ru_Trxtime);
				YeepayService.service.asynchronous(r6_Order);
			  // 产品通用接口支付成功返回-电话支付返回
			}
			// 下面页面输出是测试时观察结果使用
//			out.println("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
		}else{
			YeepayService.service.failure(r6_Order, ru_Trxtime);
			response.sendRedirect(basePath+"/yeepay/yeeReturn?r6_Order="+r6_Order);
		}
	} else {
		out.println("交易签名被篡改!");
	}
%>