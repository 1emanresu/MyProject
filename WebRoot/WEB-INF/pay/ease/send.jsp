<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.capinfo.crypt.*"%>
<%
/**
	此页面是向首信易支付提交页面。接口文档的第一部分。表单一共13项。有些参数可以用常量代替。首信不提取消费者的敏感信息。
*/
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
  String v_mid = (String)request.getAttribute("v_mid");		//商户编号，签约由易支付分配。250是测试商户编号。
  String ddate1= new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
  String v_oid = ddate1+"-"+v_mid+"-"+(String)request.getAttribute("v_oid");  //订单编号，订单编号的格式:yyyymmdd-v_mid-流水号。流水号可以按照自己的订单规则生成，但是要保证每一次提交，订单号是唯一值，否则会出错
  String v_rcvname = (String)request.getAttribute("v_mid"); //收货人姓名，建议用商户编号代替。
  String v_rcvaddr = "浩松"; //收货人姓名，可以用常量
  String v_rcvtel = "12345678912";  	//收货人电话，可以用常量
  String v_rcvpost = "100080";					//收货人邮编，可以用常量
  String v_amount = (String)request.getAttribute("v_amount"); //订单金额
  String v_ymd = ddate1;        //订单日期
  String v_orderstatus = "1";		//配货状态，0-未配齐，1-已配齐。
  String v_ordername = "浩松";  //订货人姓名，可以用常量
  String v_moneytype = "0";  //币种。0-人民币，1-美元。
  String v_url = (String)request.getAttribute("v_url");  //支付完成后返回地址。此地址是支付完成后，订单信息实时的向这个地址做返回。返回参数详见接口文档第二部分。
  String MD5Key = (String)request.getAttribute("MD5Key"); //MD5Key,签约后由商户自定义一个16位的数字字母组合作为密钥，发到huangyi@payeasenet.com.说明商户编号，公司名称和密钥。
  //MD5算法
  Md5 md5 = new Md5 ("") ;
  md5.hmac_Md5(v_moneytype+v_ymd+v_amount+v_rcvname+v_oid+v_mid+v_url,MD5Key) ;
  byte b[]= md5.getDigest();
  String digestString = md5.stringify(b) ;
  String v_pmode = (String)request.getAttribute("v_pmode");
  System.out.println("send提交中=========v_mid:"+v_mid);
  System.out.println("ddate1:"+ddate1);
  System.out.println("v_oid:"+v_oid);
  System.out.println("v_rcvname:"+v_rcvname);
  System.out.println("v_rcvaddr:"+v_rcvaddr);
  System.out.println("v_rcvtel:"+v_rcvtel);
  System.out.println("v_rcvpost:"+v_rcvpost);
  System.out.println("v_amount:"+v_amount);
  System.out.println("v_ymd:"+v_ymd);
  System.out.println("v_orderstatus:"+v_orderstatus);
  System.out.println("v_ordername:"+v_ordername);
  System.out.println("v_moneytype:"+v_moneytype);
  System.out.println("v_url:"+v_url);
  System.out.println("MD5Key:"+MD5Key);
  System.out.println("digestString:"+digestString);
  System.out.println("v_pmode:"+v_pmode);
  System.out.println("send提交结束===================");
%>

<html>
<head>
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
<div style="margin-top: 250px" align="center" >
		<p class="pzs">${title }</p>
		<img src="<%=basePath %>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
<form action="http://pay.beijing.com.cn/customer/gb/pay_bank.jsp" method="POST" name="ease">
  <input type="hidden" name="v_md5info" size="100"  value="<%=digestString%>">
	<input type="hidden" name="v_mid" value="<%=v_mid%>">
	<input type="hidden" name="v_oid" value="<%=v_oid%>">
	<input type="hidden" name="v_rcvname" value="<%=v_rcvname%>">
	<input type="hidden" name="v_rcvaddr" value="<%=v_rcvaddr%>">
	<input type="hidden" name="v_rcvtel" value="<%=v_rcvtel%>">
	<input type="hidden" name="v_rcvpost" value="<%=v_rcvpost%>">
	<input type="hidden" name="v_amount" value="<%=v_amount%>">
	<input type="hidden" name="v_ymd"  value="<%=v_ymd%>">
	<input type="hidden" name="v_orderstatus"  value="<%=v_orderstatus%>">
	<input type="hidden" name="v_ordername"  value="<%=v_ordername%>">
	<input type="hidden" name="v_moneytype"  value="<%=v_moneytype%>">
	<input type="hidden" name="v_url" value="<%=v_url%>">
	<input type="hidden" name="v_pmode" value="<%=v_pmode %>"/>
</form>
</body>
<script type="text/javascript">
		function sub(){
			document.ease.submit();
		}
		var titletime = ${titletime};
		setTimeout(sub,titletime);
	</script>
</html>