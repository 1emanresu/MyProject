package com.pay.yeepay.client.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jfinal.core.Controller;
import com.pay.yeepay.client.service.YeepayService;
import com.vo.Order;
import com.vo.Payment;
import com.yeepay.DigestUtil;

public class Yeepay extends Controller {


/*	public void node() {
		try {
			getRequest().setCharacterEncoding("gbk");
			int pd = 0;
			if (getParaToInt("pd") != null) {
				pd = getParaToInt("pd");
			}
			String p0_Cmd = getPara("p0_Cmd");
			String p1_MerId = getPara("p1_MerId");
			String p4_Cur = getPara("p4_Cur");
			String hmac = getPara("hmac");
			String p3_Amt = getPara("p3_Amt");
			String pd_FrpId = getPara("pd_FrpId");
			Person per = null;
			if (!p1_MerId.equals("")) {
				per = Person.dao.findById(Integer.parseInt(p1_MerId));
			}
			if (p3_Amt.equals("")) {
				renderHtml("抱歉，交易金额太小。");
			} else if (p0_Cmd.equals("") || p1_MerId.equals("")
					|| p4_Cur.equals("") || hmac.equals("")) {
				renderHtml("抱歉，参数提交错误！");
			} else if (per == null) {
				renderHtml("抱歉，无该用户！");
			} else if (per.getInt("ifnet") == 2) {
				renderHtml("抱歉，您的网银接口未开通请联系客服进行开通!");
			} else if (pd_FrpId.equals("")) {
				Person person = Person.dao.findById(Integer.parseInt(p1_MerId));
				setAttr("name", person.get("name"));
				setAttr("time", new Date());
				setAttr("p0_Cmd", getPara("p0_Cmd"));
				setAttr("p1_MerId", getPara("p1_MerId"));
				setAttr("p2_Order", getPara("p2_Order"));
				setAttr("p3_Amt", getPara("p3_Amt"));
				setAttr("p4_Cur", getPara("p4_Cur"));
				setAttr("p5_Pid", getPara("p5_Pid"));
				setAttr("p6_Pcat", getPara("p6_Pcat"));
				setAttr("p7_Pdesc", getPara("p7_Pdesc"));
				setAttr("p8_Url", getPara("p8_Url"));
				setAttr("p9_SAF", getPara("p9_SAF"));
				setAttr("pa_MP", getPara("pa_MP"));
				setAttr("pr_NeedResponse", getPara("pr_NeedResponse"));
				setAttr("hmac", getPara("hmac"));
				renderJsp("/WEB-INF/pay/yeepay/banksMight.jsp");
			} else {
				Order order = new Order();
				order.set("ip", CryptTool.getIpAddr(getRequest()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSSmmm");
				String orderid = "OR" + sdf.format(new Date())
						+ CryptTool.getPasswordOfNumber(4) + p1_MerId;
				order.set("orderid", orderid);
				order.set("p0_Cmd", getPara("p0_Cmd"));
				order.set("p1_MerId", p1_MerId);
				if (!getPara("p2_Order").equals("")) {
					order.set("p2_Order", getPara("p2_Order"));
				}
				if (!getPara("p3_Amt").equals("")) {
					order.set("p3_Amt", getPara("p3_Amt"));
				}
				order.set("p4_Cur", getPara("p4_Cur"));
				if (!getPara("p5_Pid").equals("")) {
					order.set("p5_Pid", getPara("p5_Pid"));
				}
				if (!getPara("p6_Pcat").equals("")) {
					order.set("p6_Pcat", getPara("p6_Pcat"));
				}
				if (!getPara("p7_Pdesc").equals("")) {
					order.set("p7_Pdesc", getPara("p7_Pdesc"));
				}
				if (!getPara("p8_Url").equals("")) {
					order.set("p8_Url", getPara("p8_Url"));
				}
				if (!getPara("p9_SAF").equals("")) {
					order.set("p9_SAF", getPara("p9_SAF"));
				}
				if (!getPara("pa_MP").equals("")) {
					order.set("pa_MP", getPara("pa_MP"));
				}
				if (!getPara("pd_FrpId").equals("")) {
					order.set("pd_FrpId", getPara("pd_FrpId"));
				}
				if (!getPara("pr_NeedResponse").equals("")) {
					order.set("pr_NeedResponse", getPara("pr_NeedResponse"));
				}
				order.set("hmac", getPara("hmac"));
				boolean boo = YeepayService.service.order(order, pd);
				if (boo) {
					PersonGateway pergetGateway = PersonGateway.dao.findById(per.getInt("id"));
					Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
					
					p1_MerId = gateway.getStr("gateway_merid");
					
					String keyValue = gateway.getStr("gateway_key"); // 商家密钥
					
					Method get = this.getClass().getMethod(gateway.getStr("gateway_function"), null);
					
					get.invoke(this);
					
				} else {
					renderText("抱歉,交易签名无效");
				}
			}
		} catch (Exception e) {
			renderText("提交错误！");
		}
	}
	
	public void yibao(String p1_MerId, String keyValue, Order order ){
		String nodeAuthorizationURL = Configuration.getInstance()
				.getValue("yeepayCommonReqURL"); // 交易请求地址
		setAttr("p0_Cmd", order.get("p0_Cmd"));
		setAttr("p1_MerId", p1_MerId);
		setAttr("p2_Order", order.get("orderid"));
		setAttr("p3_Amt", order.get("p3_Amt"));
		setAttr("p4_Cur", order.get("p4_Cur"));
		setAttr("p5_Pid", order.get("p5_Pid"));
		setAttr("p6_Pcat", order.get("p6_Pcat"));
		setAttr("p7_Pdesc", order.get("p7_Pdesc"));
		setAttr("p8_Url", "http://www.haofpay.com/hspay/callback");
		setAttr("p9_SAF", order.get("p9_SAF"));
		setAttr("pa_MP", order.get("pa_MP"));
		setAttr("pd_FrpId", order.get("pd_FrpId"));
		setAttr("pr_NeedResponse", order.get("pr_NeedResponse"));
		setAttr("nodeAuthorizationURL", nodeAuthorizationURL);
		String hmac1 = PaymentForOnlineService
				.getReqMd5HmacForOnlinePayment(
						formatString(order.getStr("p0_Cmd")),
						formatString(p1_MerId),
						formatString(order.getStr("orderid")),
						formatString(order.getStr("p3_Amt")),
						formatString(order.getStr("p4_Cur")),
						formatString(order.getStr("p5_Pid")),
						formatString(order.getStr("p6_Pcat")),
						formatString(order.getStr("p7_Pdesc")),
						formatString("http://www.haofpay.com/hspay/callback"),
						formatString(order.getStr("p9_SAF")),
						formatString(order.getStr("pa_MP")),
						formatString(order.getStr("pd_FrpId")),
						formatString(order
								.getStr("pr_NeedResponse")),
						keyValue);
		setAttr("hmac", hmac1);
		Timer timer = new Timer();
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/yeepay/reqpay.jsp");
	}*/

	
	
	public void callback() {
		renderJsp("/WEB-INF/pay/yeepay/callback.jsp");
	}
	
	public void haofucallbank(){
		renderJsp("/WEB-INF/pay/haofu/callback.jsp");
	}
	
	
	public void AutoCallBack() {
		List<Order> order = Order.dao.find("SELECT * from orders WHERE r1_Code=1 and  CreateTime>'2017-05-05 01:00:00' and CreateTime<='2017-05-05 02:10:00'");
		for(int i=0;i<order.size();i++){
			
		}
		
	}
	
	public void yeeReturn() {
		Order order = Order.dao.findById(getPara("r6_Order"));
		Payment payment = Payment.dao.findById(order.getInt("p1_MerId"));
		StringBuffer sValue = new StringBuffer();
		// 商户编号
		sValue.append(order.getInt("p1_MerId").toString());
		// 业务类型
		sValue.append(order.getStr("p0_Cmd"));
		// 支付结果
		sValue.append(order.getInt("r1_Code").toString());
		// 易宝支付交易流水号
		sValue.append(order.getStr("orderid"));
		// 支付金额
		sValue.append(order.getBigDecimal("p3_Amt").toString());
		// 交易币种
		sValue.append(order.getStr("p4_Cur"));
		// 商品名称
		sValue.append(formatString(order.getStr("p5_Pid")));
		// 商户订单号
		sValue.append(order.getStr("p2_Order"));
		// 易宝支付会员ID
		sValue.append("");
		// 商户扩展信息
		sValue.append(formatString(order.getStr("pa_MP")));
		// 交易结果返回类型
		sValue.append("1");
		String sNewString = DigestUtil.hmacSign(sValue.toString(),
				payment.getStr("key"));
		StringBuffer url = new StringBuffer();
		url.append(order.getStr("p8_Url") + "?");
		url.append("p1_MerId=" + order.get("p1_MerId") + "&");
		url.append("r0_Cmd=" + order.get("p0_Cmd") + "&");
		url.append("r1_Code=" + order.get("r1_Code") + "&");
		url.append("r2_TrxId=" + order.get("orderid") + "&");
		url.append("r3_Amt=" + order.get("p3_Amt") + "&");
		url.append("r4_Cur=" + order.get("p4_Cur") + "&");
		String p5_Pid = formatString(order.getStr("p5_Pid"));
		try {
			p5_Pid = java.net.URLEncoder.encode(p5_Pid,"gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.append("r5_Pid=" + p5_Pid + "&");
		url.append("r6_Order=" + order.get("p2_Order") + "&");
		url.append("r7_Uid=&");
		String r8_MP = formatString(order.getStr("pa_MP"));
		try {
			r8_MP = java.net.URLEncoder.encode(r8_MP,"gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.append("r8_MP=" + r8_MP + "&");
		url.append("r9_BType=" + 1 + "&");
		url.append("rb_BankId=" + order.get("pd_FrpId") + "&");
		url.append("ro_BankOrderId=" + order.get("ro_BankOrderId") + "&");
		url.append("rp_PayDate=" + order.get("rp_PayDate") + "&");
		url.append("rq_CardNo=&");
		url.append("ru_Trxtime=" + order.get("ru_Trxtime") + "&");
		url.append("hmac=" + sNewString);
		System.out
				.println(getPara("r6_Order") + "===========" + url.toString());
		redirect(url.toString());
	}

	public void retunet() {
		try {
			Order order = Order.dao.findById(getPara("orderid"));
			if (order.getInt("r1_Code") == 1) {
				boolean boo = YeepayService.service.asynchronous(getPara("orderid"));
				if(boo){
				    renderText("通知成功");
				}else{
				    renderText("通知失败");
				}
			} else {
				renderText("订单未支付成功！");
			}
		} catch (Exception e) {
			renderText("通知失败");
		}
	}

	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
}
