package com.pay.cib.client.controller;

import java.io.UnsupportedEncodingException;

import com.jfinal.core.Controller;
import com.pay.Resend;
import com.vo.Order;
import com.vo.Payment;
import com.yeepay.DigestUtil;

public class Cib extends Controller {
	
	public void callback() {
		renderJsp("/WEB-INF/pay/yeepay/callback.jsp");
	}
	
	public void weixin() {
		System.out.println("----------------这里是微信扫码-------------");
		renderJsp("/WEB-INF/weixin/weixin.jsp");
	}
	
	public void haofucallbank(){
		renderJsp("/WEB-INF/pay/haofu/callback.jsp");
	}
	
	public void pay(){
		System.out.println("aaaaaaaaaaaaaaaaaaaaa");
		//renderJsp("/WEB-INF/jsp/a.html");
		renderJsp("/WEB-INF/jsp/CibPay.jsp");
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
		//System.out.println(getPara("r6_Order") + "===========" + url.toString());
		redirect(url.toString());
	}

	public void retunet() {
		try {
			Order order = Order.dao.findById(getPara("orderid"));
			if (order.getInt("r1_Code") == 1) {
				System.out.println("发送通知的订单号是==："+getPara("orderid"));
				boolean boo = Resend.asynchronous(getPara("orderid"));
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
