<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'testPay.jsp' starting page</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
	<form action="http://192.168.0.189:8080/MyProject/pay/getway" method="post">
		<p>业务类型<input type="text" name="p0_Cmd" value="Buy"/></p>
		<p>商户ID<input type="text" name="p1_MerId" value="30"/></p>
		<p>商户订单号<input type="text" name="p2_Order" value="1487122746036"/></p>
		<p>支付金额<input type="text" name="p3_Amt" value="100"/></p>
		<p>交易币种<input type="text" name="p4_Cur" value="CNY"/></p>
		<p>商品名称<input type="text" name="p5_Pid" value="充值"/></p>
		<p>商品种类<input type="text" name="p6_Pcat" value=""/></p>
		<p>商品描述<input type="text" name="p7_Pdesc" value="充值"/></p>
		<p>返回地址<input type="text" name="p8_Url" value="http://pay.jyypay.net/web/callback.do"/></p>
		<p>送货地址<input type="text" name="p9_SAF" value=""/></p>
		<p>商品拓展信息<input type="text" name="pa_MP" value="充值"/></p>
		<p>支付通道编码<input type="text" name="pd_FrpId" value="alipaywap"/></p>
		<p>应答机制,系统默认为1,需要应答<input type="text" name="pr_NeedResponse" value="1"/></p>
		<p><input type="text" name="hmac" value="c1ceffaa6e5227de0446e366c5658dd7"/></p>
		<input type="submit" value="提交测试"/>
	</form>
  </body>
</html>
