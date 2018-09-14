<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" import=" com.allinpay.ets.client.*" errorPage="" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
	<%	
	//页面编码要与参数inputCharset一致，否则服务器收到参数值中的汉字为乱码而导致验证签名失败。
	request.setCharacterEncoding("UTF-8"); 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String serverUrl = "https://service.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action";	//提交地址
	String key = (String)request.getAttribute("key");	//用于计算signMsg的key值
	String version="v1.0";	//版本号，固定值v1.0
	String language="1";	//固定值1，urf-8
	String inputCharset = "1";	//字符集，固定值1
	String merchantId = (String)request.getAttribute("merchantId");	//商户号，100020091218001
	String pickupUrl = (String)request.getAttribute("pickupUrl");		//取货地址，同步地址
	String receiveUrl = (String)request.getAttribute("receiveUrl");	//商户系统通知地址，异步地址
	String payType="10";	//支付方式
	String signType="1";	//签名类型，固定值1
	String orderNo = (String)request.getAttribute("orderNo");	//商户系统订单号
	String orderAmount = (String)request.getAttribute("orderAmount");	//订单金额（单位分）
	String orderDatetime = (String)request.getAttribute("orderDatetime");	//商户的订单提交时间
	System.out.println(orderDatetime);
	String orderCurrency="0";	//订单金额币种类型，默认为人民币;0,156表示人民币;344表示港币;840表示美元
	String orderExpireDatetime="";	//订单过期时间，固定值60
	String payerTelephone="";	//付款人电话
	String payerEmail="";	//付款人联系email
	String payerName="";	//付款人姓名
	String payerIDCard="";	//付款人证件号
	String pid="";	//合作伙伴商户号
	String productName="";	//商品名称
	String productId="";	//商品标识
	String productNum="";	//商品数量
	String productPrice="";	//商品单价
	String productDesc="";	//商品描述
	String ext1="";	//扩展字段1
	String ext2="";	//扩展字段2
	String extTL="";//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	String issuerId = (String)request.getAttribute("issuerId");	//发卡方代码
	String pan="";	//付款人支付卡号
	String tradeNature = "";	//贸易类型，GOODS表示实物类型，SERVICES表示服务类型
	String sign="";
	
	//若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空
	//其中payerIDCard、pan需使用公钥加密（PKCS1格式）后进行Base64编码
	if(null!=payerIDCard&&!"".equals(payerIDCard)){
		try{
			//payerIDCard = SecurityUtil.encryptByPublicKey("C:\\TLCert.cer", payerIDCard);
			payerIDCard = SecurityUtil.encryptByPublicKey("TLCert.cer", payerIDCard);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	if(null!=pan&&!"".equals(pan)){
		try{
			pan = SecurityUtil.encryptByPublicKey("TLCert.cer", pan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//构造订单请求对象，生成signMsg。
	com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
	if(null!=inputCharset&&!"".equals(inputCharset)){
		requestOrder.setInputCharset(Integer.parseInt(inputCharset));
	}
	requestOrder.setPickupUrl(pickupUrl);
	requestOrder.setReceiveUrl(receiveUrl);
	requestOrder.setVersion(version);
	if(null!=language&&!"".equals(language)){
		requestOrder.setLanguage(Integer.parseInt(language));
	}
	requestOrder.setSignType(Integer.parseInt(signType));
	requestOrder.setPayType(Integer.parseInt(payType));
	requestOrder.setIssuerId(issuerId);
	requestOrder.setMerchantId(merchantId);
	requestOrder.setPayerName(payerName);
	requestOrder.setPayerEmail(payerEmail);
	requestOrder.setPayerTelephone(payerTelephone);
	requestOrder.setPayerIDCard(payerIDCard);
	requestOrder.setPid(pid);
	requestOrder.setOrderNo(orderNo);
	requestOrder.setOrderAmount(Long.parseLong(orderAmount));
	requestOrder.setOrderCurrency(orderCurrency);
	requestOrder.setOrderDatetime(orderDatetime);
	requestOrder.setOrderExpireDatetime(orderExpireDatetime);
	requestOrder.setProductName(productName);
	if(null!=productPrice&&!"".equals(productPrice)){
		requestOrder.setProductPrice(Long.parseLong(productPrice));
	}
	if(null!=productNum&&!"".equals(productNum)){
		requestOrder.setProductNum(Integer.parseInt(productNum));
	}	
	requestOrder.setProductId(productId);
	requestOrder.setProductDesc(productDesc);
	requestOrder.setExt1(ext1);
	requestOrder.setExt2(ext2);
	requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	requestOrder.setPan(pan);
	requestOrder.setTradeNature(tradeNature);
	requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

	String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
	String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
	%>
	
	<!--
		1、订单可以通过post方式或get方式提交，建议使用post方式；
		   提交支付请求可以使用http或https方式，建议使用https方式。
		2、通联支付网关地址、商户号及key值，在接入测试时由通联提供；
		   通联支付网关地址、商户号，在接入生产时由通联提供，key值在通联支付网关会员服务网站上设置。
	-->
	<!--================= post 方式提交支付请求 start =====================-->
    <!--================= 测试地址为 http://ceshi.allinpay.com/gateway/index.do =====================-->
	<!--================= 生产地址请在测试环境下通过后从业务人员获取 (https://service.allinpay.com/gateway/index.do )=====================-->
	<div style="margin-top: 250px" align="center" >
		<p class="pzs">${title }</p>
		<img src="<%=basePath %>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
	<form name="form2" action="<%=serverUrl %>" method="post">
		<input type="hidden" name="inputCharset" value="<%=inputCharset%>"/>
		<input type="hidden" name="pickupUrl" value="<%=pickupUrl%>"/>
		<input type="hidden" name="receiveUrl" value="<%=receiveUrl%>" />
		<input type="hidden" name="version" value="<%=version %>"/>
		<input type="hidden" name="language" value="<%=language %>" />
		<input type="hidden" name="signType" value="<%=signType%>"/>
		<input type="hidden" name="merchantId" value="<%=merchantId%>" />
		<input type="hidden" name="payerName" value="<%=payerName%>"/>
		<input type="hidden" name="payerEmail" value="<%=payerEmail%>" />
		<input type="hidden" name="payerTelephone" value="<%=payerTelephone %>" />
		<input type="hidden" name="payerIDCard" value="<%=payerIDCard %>" />
		<input type="hidden" name="pid" value="<%=pid%>"/>
		<input type="hidden" name="orderNo" value="<%=orderNo%>" />
		<input type="hidden" name="orderAmount" value="<%=orderAmount %>"/>
		<input type="hidden" name="orderCurrency" value="<%=orderCurrency%>" />
		<input type="hidden" name="orderDatetime" value="<%=orderDatetime%>" />
		<input type="hidden" name="orderExpireDatetime" value="<%=orderExpireDatetime %>"/>
		<input type="hidden" name="productName" value="<%=productName%>" />
		<input type="hidden" name="productPrice" value="<%=productPrice%>" />
		<input type="hidden" name="productNum" value="<%=productNum %>"/>
		<input type="hidden" name="productId" value="<%=productId%>" />
		<input type="hidden" name="productDesc" value="<%=productDesc%>" />
		<input type="hidden" name="ext1" value="<%=ext1%>" />
		<input type="hidden" name="ext2" value="<%=ext2%>" />
		<input type="hidden" name="payType" value="<%=payType%>" />
		<input type="hidden" name="issuerId" value="<%=issuerId%>" />
		<input type="hidden" name="pan" value="<%=pan %>" />
		<input type="hidden" name="tradeNature" value="<%=tradeNature %>" />
		<input type="hidden" name="signMsg" value="<%=strSignMsg %>" />
	 </form>
	 <script type="text/javascript">
		function sub(){
			document.form2.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub,titletime);
	</script>
	</body>
	</html>
