<%@page import="com.pay.caifutong.client.service.CaiFuTongService"%>
<%@page import="com.pay.caifutong.server.util.TenpayUtil"%>
<%@page import="com.pay.caifutong.server.client.ScriptClientResponseHandler"%>
<%@page import="com.pay.caifutong.server.client.TenpayHttpClient"%>
<%@page import="com.pay.caifutong.server.BaseSplitRequestHandler"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.File"%>
<%
    //商户号 
    String bargainor_id = request.getParameter("bargainor_id");

    //密钥 
    String key = request.getParameter("key");
    
  	//订单号
    String sp_billno = request.getParameter("sp_billno");
  	
  	//财付通订单编号
  	String transaction_id = request.getParameter("transaction_id");
  	
  	//金额
  	String total_fee = request.getParameter("total_fee");
  	
  	//商户费率后金额
  	String amts1 = request.getParameter("amts1");
  	
  	//平台利润
  	String amts2 = request.getParameter("amts2");
  	
  	//平台账号
  	String caifutongemial = request.getParameter("caifutongemial");
  	
  	//商户账号
  	String caifu_account = request.getParameter("caifu_account");
  	
  //---------------生成订单号 开始------------------------
  //当前时间 yyyyMMddHHmmss
  String currTime = TenpayUtil.getCurrTime();
  //8位日期
  String strDate = currTime.substring(0, 8);
  //6位时间
  String strTime = currTime.substring(8, currTime.length());
  //四位随机数
  String strRandom = TenpayUtil.buildRandom(1) + "";
  //10位序列号,可以自行调整。
  String strReq = strTime + strRandom;
  //商家订单号，长度若超过32位，取前32位。财付通只记录商家订单号，不保证唯一。
  //财付通交易单号，规则为：10位商户号+8位时间（YYYYmmdd)+10位流水号，商户根据自己情况调整，只要保证唯一
  String refund_id = 109 + bargainor_id + strDate + strReq;
  //---------------生成订单号 结束------------------------
  	
    //创建分账请求对象
    BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
    //通信对象
    TenpayHttpClient httpClient = new TenpayHttpClient();
    //应答对象
    ScriptClientResponseHandler resHandler = new ScriptClientResponseHandler();
    
    //-----------------------------
    //设置请求参数
    //-----------------------------
    reqHandler.init();
    reqHandler.setKey(key);
    reqHandler.setGateUrl("https://mch.tenpay.com/cgi-bin/split_rollback.cgi");
    
    //-----------------------------
    //设置接口参数
    //-----------------------------
    reqHandler.setParameter("cmdno", "95");
    reqHandler.setParameter("version", "4");
    reqHandler.setParameter("fee_type", "1");
    reqHandler.setParameter("bargainor_id", bargainor_id);    	        //商户号
    reqHandler.setParameter("sp_billno", sp_billno);    	    		//商家订单号
    reqHandler.setParameter("transaction_id", transaction_id);	//财付通交易单号    
    reqHandler.setParameter("return_url", "http://127.0.0.1/");    	    //后台系统调用，必现填写为http://127.0.0.1/
    reqHandler.setParameter("total_fee", total_fee);    	    	    	//商品金额,以分为单位
    //退款ID，同个ID财付通认为是同一笔退款,格式为109+10位商户号+8位日期+7位序列号
    reqHandler.setParameter("refund_id", refund_id);
    reqHandler.setParameter("bus_type", "97");
    
    //业务参数，特定格式的字符串，格式为
    //退款总额|账户^退款金额[|账户^退款金额]
    reqHandler.setParameter("bus_args", total_fee+"|"+caifu_account+"^"+amts1+"|"+caifutongemial+"^"+amts2);
    //reqHandler.setParameter("bus_args", "3|896070200^1|1445575273^1|1552565201^1");
    //-----------------------------
    //设置通信参数
    //-----------------------------
    //证书必须放在用户下载不到的目录，避免证书被盗取
    //设置CA证书
    httpClient.setCaInfo(new File("cacert.pem"));	
    //设置个人(商户)证书
    httpClient.setCertInfo(new File("1900000107.pfx"), "1900000107");
    //设置请求内容
    httpClient.setReqContent(reqHandler.getRequestURL());

    //后台调用
    if(httpClient.call()) {
    	//设置结果参数
    	String tmp = httpClient.getResContent();
    	resHandler.setContent(tmp);
    	resHandler.setKey(key);
    	
    	
    	//获取返回参数
    	String pay_result = resHandler.getParameter("pay_result");
    	
   		String pay_info = resHandler.getParameter("pay_info");
    	
   		refund_id = reqHandler.getParameter("refund_id");
   		
    	//判断签名及结果
    	if(resHandler.isTenpaySign()&& "0".equals(pay_result)) {
    		CaiFuTongService.service.caifutongOrderreturn(sp_billno, pay_result, pay_info, refund_id);
    		System.out.println("分账回退成功");
    		out.println("分账回退成功");
    		//取结果参数做业务处理
    		
    	} else {
    		//错误时，返回结果未签名。
    		//如包格式错误或未确认结果的，请使用原来refund_id重新发起，确认结果，避免多次操作
    		System.out.println("验证签名失败或业务错误");
    		out.println("验证签名失败或业务错误");
    		System.out.println("pay_result:" + resHandler.getParameter("pay_result")+
    	    	" pay_info:" + resHandler.getParameter("pay_info"));
    	}	
    } else {
    	System.out.println("后台调用通信失败");
    	out.println("后台调用通信失败");
    	System.out.println(httpClient.getResponseCode());
    	System.out.println(httpClient.getErrInfo());
    	//有可能因为网络原因，请求已经处理，但未收到应答。
    }
%>
