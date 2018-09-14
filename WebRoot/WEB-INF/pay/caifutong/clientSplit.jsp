<%@page import="com.pay.caifutong.server.client.ScriptClientResponseHandler"%>
<%@page import="com.pay.caifutong.server.client.TenpayHttpClient"%>
<%@page import="com.pay.caifutong.server.BaseSplitRequestHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.File"%>
<%
    //商户号 
    String bargainor_id = request.getParameter("bargainor_id");
    //bargainor_id = "1900000109";
    //密钥 
    String key = request.getParameter("key");
    //key = "8934e7d15453e97507ef794cf7b0519d";
    
    String sp_billno = request.getParameter("sp_billno");
    
    String transaction_id = request.getParameter("transaction_id");
    
    String total_fee = request.getParameter("total_fee");
    
  //用户分账金额
    String amts1 = request.getParameter("amts1");

    //平台利润
    String amts2 = request.getParameter("amts2");

    //平台财付通账号
    String caifutongemial = request.getParameter("caifutongemial");

    //用户财付通账号
    String caifu_account = request.getParameter("caifu_account");
    
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
    reqHandler.setGateUrl("https://mch.tenpay.com/cgi-bin/split.cgi");
    String ddate1= new SimpleDateFormat("yyyyMMDDhhmmss").format(new Date());
    //-----------------------------
    //设置接口参数
    //-----------------------------
    reqHandler.setParameter("cmdno", "3");
    reqHandler.setParameter("version", "4");
    reqHandler.setParameter("fee_type", "1");
    reqHandler.setParameter("bargainor_id", bargainor_id);    		    	//商户号
    reqHandler.setParameter("sp_billno", sp_billno);    	    		//商家订单号
    reqHandler.setParameter("transaction_id", transaction_id);	//财付通交易单号    
    reqHandler.setParameter("return_url", "http://127.0.0.1/");    	    //后台系统调用，必现填写为http://127.0.0.1/
    reqHandler.setParameter("total_fee", total_fee);    	    	    	//商品金额,以分为单位
    reqHandler.setParameter("bus_type", "97");    	    	    	//业务类型
    /**
     * 业务参数
     * 帐号^分帐金额^角色
     * 角色说明:	1:供应商 2:平台服务方 3:资金清算方 4:独立分润方
     * 帐号1^分帐金额1^角色1|帐号2^分帐金额2^角色2|...
    **/
    reqHandler.setParameter("bus_args",  caifu_account+"^"+amts1+"^4|"+caifutongemial+"^"+amts2+"^1");

    //行业描述信息
    reqHandler.setParameter("bus_desc","12345^测试^1^spruce^1^1");
    
    //-----------------------------
    //设置通信参数
    //-----------------------------
    //证书必须放在用户下载不到的目录，避免证书被盗取
    //设置CA证书
    httpClient.setCaInfo(new File("G:/Sirius/test/WebRoot/cacert.pem"));	
    //设置个人(商户)证书
    httpClient.setCertInfo(new File("G:/Sirius/test/WebRoot/1215668201_20130311125023.pfx"), "1215668201");
    
    
    //设置请求内容
    httpClient.setReqContent(reqHandler.getRequestURL());
    System.out.println(reqHandler.getRequestURL());
    //后台调用
    if(httpClient.call()) {
    	//设置结果参数
    	resHandler.setContent(httpClient.getResContent());
    	resHandler.setKey(key);
    	
    	
    	//获取返回参数
    	String pay_result = resHandler.getParameter("pay_result");
    	
    	//判断签名及结果
    	if(resHandler.isTenpaySign()&& "0".equals(pay_result)) {
    		System.out.println("分账成功");
    		out.println("分账成功");
    		//取结果参数做业务处理
    		
    	} else {
    		//错误时，返回结果未签名。
    		//如包格式错误或未确认结果的，请使用原来订单号重新发起，确认结果，避免多次操作
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
