<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.vo.Gateway"%>
<%@page import="com.jsp.payment.service.PaymentService"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.capinfo.crypt.*"%>

<%
	//接收参数
	request.setCharacterEncoding("gb2312");
	String v_oid = request.getParameter("v_oid");//订单编号
	String v_pmode = request.getParameter("v_pmode");//支付方式
	String v_pstatus = request.getParameter("v_pstatus");//支付结果 20支付成功 30 支付失败
	String v_pstring = request.getParameter("v_pstring");//支付结果信息说明
	String v_amount = request.getParameter("v_amount");//订单金额
	String v_moneytype = request.getParameter("v_moneytype");//币种
	String v_md5money = request.getParameter("v_md5money");//数字指纹
	String v_md5info = request.getParameter("v_md5info");//数字指纹
	String v_sign = request.getParameter("v_sign");//RSA数字指纹

	//中文解析
	v_pstring = new String(v_pstring.getBytes("8859_1"));
	v_pmode = new String(v_pmode.getBytes("8859_1"));

	System.out.println("paymentreceived1接收数据==============================");
	System.out.println("v_oid:"+v_oid);
	System.out.println("v_pmode:"+v_pmode);
	System.out.println("v_pstatus:"+v_pstatus);
	System.out.println("v_pstring:"+v_pstring);
	System.out.println("v_amount:"+v_amount);
	System.out.println("v_moneytype:"+v_moneytype);
	System.out.println("v_md5money:"+v_md5money);
	System.out.println("v_md5info:"+v_md5info);
	System.out.println("v_sign:"+v_sign);
	System.out.println("paymentreceived1接收数据结束==============================");
	
	String source1 = v_oid + v_pstatus + v_pstring + v_pmode;
	String source2 = v_amount + v_moneytype;

	/*  out.println("v_pmode:");
	 out.print(v_pmode);  
	 out.print("<br>");  
	 
	 out.println("v_pstring:");
	 out.print(v_pstring);  
	 out.print("<br>");  
	 
	 
	 out.println("v_md5info指纹结果");
	 out.print("<br>");   */
	request.setCharacterEncoding("gb2312");

	Gateway gateway = Gateway.dao.findById(8);
	String keyValue = gateway.getStr("gateway_key");   // 商家密钥
	 
	Md5 md5 = new Md5("");
	md5.hmac_Md5(source1, keyValue);
	byte b[] = md5.getDigest();
	String digestString = md5.stringify(b);

	/*   out.println (digestString) ;
	 out.print("<br>"); 
	

	 out.println("v_md5money指纹结果");
	 out.print("<br>");   */
	request.setCharacterEncoding("gb2312");

	Md5 m = new Md5("");
	md5.hmac_Md5(source2, keyValue);
	byte a[] = md5.getDigest();
	String digestString2 = md5.stringify(a);

	
	String publicKey = "D:/tomcat/webworkhs/haosong/WEB-INF/pay/ease/Public1024.key"; //public1024.key的路径
	String SignString = v_sign;
	String strSource = v_oid + v_pstatus + v_amount + v_moneytype;
	RSA_MD5 rsaMD5 = new RSA_MD5();
	int k = rsaMD5.PublicVerifyMD5(publicKey, SignString, strSource);
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	/* out.println (digestString2) ;
	out.print("<br>");

	
	out.println("输出md5info对比结果");
	out.print("<br>"); */
	
	System.out.println("paymentreceived1验证数据=================================");
	System.out.println("digestString:"+digestString);
	System.out.println("digestString2:"+digestString2);
	System.out.println("k:"+k);
	System.out.println("paymentreceived1验证数据结束==================================");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String[] v_oidu = v_oid.split("-");
	System.out.println("订单===="+v_oidu[2]);
	if (digestString.equals(v_md5info)&&digestString2.equals(v_md5money)&&k == 0) {
		if(v_pstatus.equals("20")){
			PaymentService.service.netcallback("", v_oidu[2], "", sdf.format(new Date()), sdf.format(new Date()));
			response.sendRedirect(basePath+"/payment/showPayOk");
		}else{
			PaymentService.service.failure(v_oidu[2], sdf.format(new Date()));
			response.sendRedirect(basePath+"/payment/showPayNo");
		}
	} else {
		out.println("error");
	}
	//数据库操作略
%>