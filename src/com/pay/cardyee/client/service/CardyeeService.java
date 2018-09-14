package com.pay.cardyee.client.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.ofpay.rcvcard.util.RSA;
import com.pay.cardyee.client.dao.CardyeeDao;
import com.pay.cardyee.server.XmlStringParse;
import com.pay.cardyee.server.util.EncodeUtils;
import com.pay.yeepay.server.DigestUtil;
import com.tool.CryptTool;
import com.vo.CardOrder;
import com.vo.Payment;
import com.vo.SingleCard;
import com.vo.System_interface;

public class CardyeeService extends CardyeeDao {
	public static CardyeeService service = new CardyeeService();

	public boolean equCard(String p0_Cmd, String p1_MerId, String p2_Order,
			String p3_Amt, String p4_verifyAmt, String p5_Pid, String p6_Pcat,
			String p7_Pdesc, String p8_Url, String pa_MP, String pa7_cardAmt,
			String pa8_cardNo, String pa9_cardPwd, String pd_FrpId,
			String pr_NeedResponse, String pz_userId, String pz1_userRegTime,
			String hmac) {
		boolean boo = false;
		List<Payment> paylist = eqPayment(p1_MerId);
		Payment pay = paylist.get(0);
		String key = pay.getStr("key");
		String hmacs = DigestUtil.getHmac(new String[] { formatString(p0_Cmd),
				formatString(p1_MerId), formatString(p2_Order),
				formatString(p3_Amt), formatString(p4_verifyAmt),
				formatString(p5_Pid), formatString(p6_Pcat),
				formatString(p7_Pdesc), formatString(p8_Url),
				formatString(pa_MP), formatString(pa7_cardAmt),
				formatString(pa8_cardNo), formatString(pa9_cardPwd),
				formatString(pd_FrpId), formatString(pr_NeedResponse),
				formatString(pz_userId), formatString(pz1_userRegTime) }, key);
		if (hmac.equals(hmacs)) {
			boo = true;
		}
		return boo;
	}

	public CardOrder HandCard(CardOrder cardOrder, String basePath) {
		int r1_Code = 0;
		BigDecimal amount = BigDecimal.valueOf(0);
		String pa7_cardAmt = cardOrder.getStr("pa7_cardAmt");
		String pa8_cardNo = cardOrder.getStr("pa8_cardNo");
		String pa9_cardPwd = cardOrder.getStr("pa9_cardPwd");
		String[] cardAmt = pa7_cardAmt.split(",");
		String[] cardno = pa8_cardNo.split(",");
		String[] cardpass = pa9_cardPwd.split(",");
		if (cardno.length == cardpass.length && cardAmt.length == cardno.length
				&& cardAmt.length == cardpass.length) {
			if (cardno.length > 10 && cardpass.length > 10
					&& cardAmt.length > 10) {
				cardOrder.set("rq_ReturnMsg", "卡数量过多，目前最多支持10张卡");
				r1_Code = 5;
			} else {
				SingleCard singcard = new SingleCard();
				for (int i = 0; i < cardno.length; i++) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMDDHHMMSSmmm");
					String orderid = "C" + sdf.format(new Date())
							+ CryptTool.getPasswordOfNumber(3)
							+ cardOrder.getStr("p1_MerId");
					System_interface systeminterface = System_interface.dao.findById(1);
					String httpurl = systeminterface.getStr("system_interface_httpurl");
					XmlStringParse xml = HanCard(orderid,
							httpurl+"/hscard/cardReturn", cardno[i], cardpass[i],
							getParticipate(cardOrder.getStr("pd_FrpId")) + Math.round(Float.parseFloat(cardAmt[i])));
					String result = xml.getParameter("result");
					singcard.set("orderid", cardOrder.get("orderid"));
					singcard.set("orderno", orderid);
					singcard.set("cardcode", getParticipate(cardOrder.getStr("pd_FrpId")));
					singcard.set("cardno", cardno[i]);
					singcard.set("cardpass", cardpass[i]);
					singcard.set("sign", xml.getParameter("sign"));
					singcard.set("datetime", xml.getParameter("datetime"));
					singcard.set("billid", xml.getParameter("billid"));
					singcard.set("result", result);
					singcard.set("info", xml.getParameter("info"));
					singcard.set("value",cardAmt[i]);
					singcard.set("xml", xml.getSrc());
					singcard.save();
					r1_Code = 1;
				}
			}
		} else {
			cardOrder.set("rq_ReturnMsg", "卡号密码为空或者数量不相等");
			r1_Code = 8002;
		}
		cardOrder.set("r1_Code", r1_Code);
		cardOrder.set("amount", amount);
		cardOrder.set("deducted", 0);
		cardOrder.set("rq_ReturnMsg", "提交成功!");
		cardOrder.save();
		return cardOrder;
	}
	
	public void asynchronous(String r6_Order){
		try {
			for(int i=0; i<5; i++){
				String p6_confirmAmount = "";
				String p7_realAmount = "";
				String p8_cardStatus = "";
				String pb_BalanceAmt = "";
				String pc_BalanceAct = "";
				String r1_Code = "2";
				CardOrder order = CardOrder.dao.findById(r6_Order);
				List<SingleCard> singlist = SingleCard.dao.find("select * from singlecard s where orderid='"+r6_Order+"'");
				for(SingleCard sing : singlist){
					p7_realAmount += sing.getBigDecimal("value")+",";
					String result = sing.get("result");
					if(result.equals("2000")){
						//支付成功
						p8_cardStatus += 0+",";
					}else if(result.equals("2001")){
						//数据接收成功
						p8_cardStatus += 1+",";
					}else if(result.equals("2002")){
						//不支持该类卡或者该面值的卡
						p8_cardStatus += 1003+",";
					}else if(result.equals("2004")){
						//订单内容重复
						p8_cardStatus += 7+",";
					}else if(result.equals("2005")){
						//该卡密已经有被使用的记录
						p8_cardStatus += 2005+",";
					}else if(result.equals("2009")){
						//暂时停止该类卡或者该面值的卡交易
						p8_cardStatus += 1003+",";
					}else if(result.equals("2010")){
						//充值卡无效
						p8_cardStatus += 1006+",";
					}else if(result.equals("2011")){
						//支付成功,实际面值{0}元
						p8_cardStatus += 1+",";
					}else if(result.equals("2016")){
						//密码错误
						p8_cardStatus += 1004+",";
					}else if(result.equals("2018")){
						//余额不足
						p8_cardStatus += 1007+",";
					}else if(result.equals("2020")){
						//提交次数过多
						p8_cardStatus += 1002+",";
					}else{
						p8_cardStatus += 10000+",";
					}
					if(sing.get("result").equals("2000")){
						p6_confirmAmount += sing.getBigDecimal("value")+",";
					}else{
						pb_BalanceAmt += sing.getBigDecimal("value")+",";
						pc_BalanceAct += sing.getStr("cardno")+",";
					}
				}
				String[] p8_cardStatusarr = p8_cardStatus.split(",");
				for(String cardstat : p8_cardStatusarr){
				    if(cardstat.equals("0")){
				        r1_Code = "1";
				    }
				}
				Payment pay = Payment.dao.findById(order.getInt("p1_MerId"));
				String newHmac = DigestUtil.getHmac(new String[] {  order.getStr("p0_Cmd"), 
				        r1_Code,
						order.getInt("p1_MerId").toString(),
						order.getStr("p2_Order"),
						order.getBigDecimal("p3_Amt").toString(),
						order.getStr("pd_FrpId"),
						order.getStr("pa8_cardNo"),
					   	p6_confirmAmount,
					   	p7_realAmount,
					   	p8_cardStatus,
					   	order.getStr("pa_MP"),
					   	pb_BalanceAmt,
					   	pc_BalanceAct}, pay.getStr("key"));
				HttpClient hClient = new HttpClient();
				HttpConnectionManagerParams managerParams = hClient
						.getHttpConnectionManager().getParams();
				// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);
				// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(order.getStr("p8_Url"));
				NameValuePair[] nvp = {
						new NameValuePair("r0_Cmd", order.getStr("p0_Cmd")),
						new NameValuePair("r1_Code", r1_Code),
						new NameValuePair("p1_MerId", order.getInt("p1_MerId").toString()),
						new NameValuePair("p2_Order", order.getStr("p2_Order")),
						new NameValuePair("p3_Amt", order.getBigDecimal("p3_Amt").toString()),
						new NameValuePair("p4_FrpId", order.getStr("pd_FrpId")),
						new NameValuePair("p5_CardNo", order.getStr("pa8_cardNo")),
						
						new NameValuePair("p6_confirmAmount", p6_confirmAmount),//确认金额组
						new NameValuePair("p7_realAmount", p7_realAmount),//实际金额组
						
						new NameValuePair("p8_cardStatus", p8_cardStatus),//卡状态组
						
						new NameValuePair("p9_MP", order.getStr("pa_MP")),
						
						new NameValuePair("pb_BalanceAmt", pb_BalanceAmt),//支付余额
						new NameValuePair("pc_BalanceAct", pc_BalanceAct),//余额卡号
						
						new NameValuePair("hmac", newHmac)
				};
				post.setRequestBody(nvp);
				post.setRequestHeader("Connection", "close");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				String[] re = returnStr.split("\n");
				boolean boo = false;
				for(String st:re){
					st = st.trim();
					if(st.toUpperCase().equals("SUCCESS")){
						order.set("success", 1);
						order.update();
						boo = true;
						break;
					}
				}
				if(boo){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XmlStringParse HanCard(String orderno, String retaction,
			String cardno, String cardpass, String cardcode) {
		XmlStringParse xml = null;
		try {
			System_interface systeminterface = System_interface.dao.findById(1);
			String usercode = systeminterface.getStr("system_interface_usercode");
			// String orderno = request.getParameter("orderno");
			String mode = "r";
			String version = "1.0";
			// String retaction = request.getParameter("retaction");
			String sign = "";
			String format = "xml";
			String md5key = systeminterface.getStr("system_interface_md5key");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSS");
			String datetime = sdf.format(new Date());
			// String cardno = request.getParameter("cardno");
			// String cardpass = request.getParameter("cardpass");
			// String cardcode = request.getParameter("cardcode");

			String md5src = usercode + mode + version + orderno + cardcode
					+ cardno + cardpass + retaction + datetime + format
					+ md5key;
			sign = EncodeUtils.testDigest(md5src);

			usercode = RSA.encrypt(usercode, "gbk");
			cardpass = RSA.encrypt(cardpass, "gbk");
			HttpClient hClient = new HttpClient();
			HttpConnectionManagerParams managerParams = hClient
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(1110000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3011000);
			PostMethod post = null;
			String rcvard = "http://card.pay.ofpay.com/rcvcard.do";
			post = new PostMethod(rcvard);
			NameValuePair[] nvp = { new NameValuePair("mode", mode),
					new NameValuePair("version", version),
					new NameValuePair("usercode", usercode),
					new NameValuePair("orderno", orderno),
					new NameValuePair("cardcode", cardcode),
					new NameValuePair("cardno", cardno),
					new NameValuePair("cardpass", cardpass),
					new NameValuePair("retaction", retaction),
					new NameValuePair("format", format),
					new NameValuePair("datetime", datetime),
					new NameValuePair("sign", sign) };
			post.setRequestBody(nvp);
			post.setRequestHeader("Connection", "close");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			System.out.println("提交收卡支付返回:" + returnStr);
			xml = new XmlStringParse(returnStr);
			String retusercode = xml.getParameter("usercode");
			String retmode = xml.getParameter("mode");
			String retversion = xml.getParameter("version");
			String retorderno = xml.getParameter("orderno");
			String retbillid = xml.getParameter("billid");
			String retcardcode = xml.getParameter("cardcode");
			String retcardno = xml.getParameter("cardno");
			String retretaction = xml.getParameter("retaction");
			String retresult = xml.getParameter("result");
			String retinfo = xml.getParameter("info");
			String retdatetime = xml.getParameter("datetime");
			String retsign = xml.getParameter("sign");
			// request.setAttribute("result",retresult);
			// request.setAttribute("usercode", retusercode);
			// request.setAttribute("orderno", retorderno);
			// request.setAttribute("billid", retbillid);
			// request.setAttribute("retaction", retretaction);
			// request.setAttribute("info", retinfo);
			// request.setAttribute("datetime", retdatetime);

			md5src = retusercode + retmode + retversion + retorderno
					+ retbillid + retcardcode + retcardno + retretaction
					+ retresult + retinfo + retdatetime;
			// MD5check
			if (!retsign.equals(EncodeUtils.testDigest(md5src + md5key))) {
				System.out.println("加密验证失败");
			}
			post.releaseConnection();
			post = null;
			hClient = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
}
