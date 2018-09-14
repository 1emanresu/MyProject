package com.pay.cardyee.client.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jsp.payment.service.PaymentService;
import com.pay.cardyee.client.service.CardyeeService;
import com.pay.cardyee.server.util.EncodeUtils;
import com.tool.CryptTool;
import com.vo.Balance;
import com.vo.CardOrder;
import com.vo.Circlip;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Payment;
import com.vo.Person;
import com.vo.SingleCard;
import com.vo.System_interface;
import com.yeepay.DigestUtil;

public class Cardyee extends Controller {
	public void node() {
		try {
			getRequest().setCharacterEncoding("gbk");
			String p0_Cmd = getPara("p0_Cmd");
			String p1_MerId = "";
			if (getPara("p1_MerId") != null) {
				p1_MerId = getPara("p1_MerId");
			}
			String p2_Order = getPara("p2_Order");
			String p3_Amt = getPara("p3_Amt");
			String p4_verifyAmt = getPara("p4_verifyAmt");
			String p5_Pid = "";
			if (getPara("p5_Pid") != null) {
				p5_Pid = getPara("p5_Pid");
			}
			String p6_Pcat = "";
			if (getPara("p6_Pcat") != null) {
				p6_Pcat = getPara("p6_Pcat");
			}
			String p7_Pdesc = "";
			if (getPara("p7_Pdesc") != null) {
				p7_Pdesc = getPara("p7_Pdesc");
			}
			String p8_Url = getPara("p8_Url");
			String pa_MP = "";
			if (getPara("pa_MP") != null) {
				pa_MP = getPara("pa_MP");
			}
			String pa7_cardAmt = getPara("pa7_cardAmt");
			String pa8_cardNo = getPara("pa8_cardNo");
			String pa9_cardPwd = getPara("pa9_cardPwd");
			String pd_FrpId = getPara("pd_FrpId");
			String pr_NeedResponse = getPara("pr_NeedResponse");
			String pz_userId = "";
			if (getPara("pz_userId") != null) {
				pz_userId = getPara("pz_userId");
			}
			String pz1_userRegTime = "";
			if (getPara("pz1_userRegTime") != null) {
				pz1_userRegTime = getPara("pz1_userRegTime");
			}
			String hmac = getPara("hmac");
			String r1_Code = "";
			String r6_Order = "";
			String rq_ReturnMsg = "";
			Person per = null;
			if (!p1_MerId.equals("")) {
				per = Person.dao.findById(Integer.parseInt(p1_MerId));
			}
			boolean boo = CardyeeService.service
					.equCard(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_verifyAmt,
							p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, pa_MP,
							pa7_cardAmt, pa8_cardNo, pa9_cardPwd, pd_FrpId,
							pr_NeedResponse, pz_userId, pz1_userRegTime, hmac);
			if (p0_Cmd.equals("") && p1_MerId.equals("") && p2_Order.equals("")
					&& p3_Amt.equals("") && p4_verifyAmt.equals("")
					&& p8_Url.equals("") && pa7_cardAmt.equals("")
					&& pa8_cardNo.equals("") && pa9_cardPwd.equals("")
					&& pd_FrpId.equals("") && pr_NeedResponse.equals("")
					&& hmac.equals("")) {
				renderText("参数错误");
			} else if (per == null) {
				renderHtml("抱歉，无该用户！");
			} else if (boo) {
				CardOrder card = new CardOrder();
				card.set("ip", CryptTool.getIpAddr(getRequest()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSSmmm");
				String orderid = "CA" + sdf.format(new Date())
						+ CryptTool.getPasswordOfNumber(4) + p1_MerId;
				card.set("orderid", orderid);
				card.set("p0_Cmd", p0_Cmd);
				card.set("p1_MerId", p1_MerId);
				card.set("p2_Order", p2_Order);
				card.set("p3_Amt", p3_Amt);
				card.set("p4_verifyAmt", p4_verifyAmt);
				card.set("p5_Pid", p5_Pid);
				card.set("p6_Pcat", p6_Pcat);
				card.set("p7_Pdesc", p7_Pdesc);
				card.set("p8_Url", p8_Url);
				card.set("pa_MP", pa_MP);
				card.set("pa7_cardAmt", pa7_cardAmt);
				card.set("pa8_cardNo", pa8_cardNo);
				card.set("pa9_cardPwd", pa9_cardPwd);
				card.set("pd_FrpId", pd_FrpId);
				card.set("pr_NeedResponse", pr_NeedResponse);
				card.set("pz_userId", pz_userId);
				card.set("lock", 1);
				card.set("success", 2);
				if (!pz1_userRegTime.equals("")) {
					card.set("pz1_userRegTime", pz1_userRegTime);
				}
				card.set("hmac", hmac);
				card.set("CreateTime", new Date());
				String path = getRequest().getContextPath();
				String basePath = getRequest().getScheme() + "://"
						+ getRequest().getServerName() + ":"
						+ getRequest().getServerPort() + path;
				card = CardyeeService.service.HandCard(card, basePath);
				r1_Code = card.getInt("r1_Code").toString();
				rq_ReturnMsg = card.getStr("rq_ReturnMsg");
			} else {
				r1_Code = "-1";
				r6_Order = p2_Order;
				rq_ReturnMsg = "签名较验失败或未知错误";
			}
			Payment pay = Payment.dao.findById(Integer.parseInt(p1_MerId));
			String newHmac = "";
			newHmac = DigestUtil.getHmac(new String[] { p0_Cmd, r1_Code,
					p2_Order, rq_ReturnMsg }, pay.getStr("key"));
			setAttr("hmac", newHmac);
			setAttr("r0_Cmd", p0_Cmd);
			setAttr("r1_Code", r1_Code);
			setAttr("r6_Order", p2_Order);
			setAttr("rq_ReturnMsg", rq_ReturnMsg);
			renderJsp("/WEB-INF/pay/card/callback.jsp");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			renderText("提交错误！");
			e.printStackTrace();
		}
	}

	public void cardReturn() {
		try {
			getRequest().setCharacterEncoding("gbk");
			
			String usercode = getPara("usercode");
			String mode = getPara("mode");
			String version = getPara("version");
			String orderno = getPara("orderno");
			String billid = getPara("billid");
			String result = getPara("result");
			String info = getPara("info");
			String value = getPara("value");
			String accountvalue = getPara("accountvalue");
			String datetime = getPara("datetime");
			String sign = getPara("sign");
			String md5src = usercode + mode + version + orderno + billid
					+ result + info + value + accountvalue + datetime;
			System_interface systeminterface = System_interface.dao.findById(1);
			String md5key = systeminterface.getStr("system_interface_md5key");
			if (!sign.equals(EncodeUtils.testDigest(md5src + md5key))) {
				info += "加密验证失败";
			}
			SingleCard singcard = SingleCard.dao.findById(orderno);
			String orderid = singcard.getStr("orderid");
			if (!singcard.get("result").equals("2000")) {
				CardOrder cardorder = CardOrder.dao.findById(orderid);
				int id = cardorder.getInt("p1_MerId");
				Balance balance = Balance.dao.findById(id);
				String cardcode = singcard.get("cardcode");
				cardcode = cardcode.substring(0, 6);
				BigDecimal big = PaymentService.service.getRate(cardcode, id);
				BigDecimal amount = new BigDecimal(value).multiply(big);
				BigDecimal deducted = new BigDecimal(value).subtract(amount);
				singcard.set("value", value);
				singcard.set("actual", amount);
				singcard.set("deducted", deducted);
				cardorder.set("amount",
						cardorder.getBigDecimal("amount").add(amount));
				cardorder.set("deducted", cardorder.getBigDecimal("deducted").add(deducted));
				cardorder.update();
				balance.set("amount",
						balance.getBigDecimal("amount").add(amount));
				balance.set("cardamount", balance.getBigDecimal("cardamount")
						.add(amount));
				balance.set("deductedcard",
						balance.getBigDecimal("deductedcard").add(deducted));
				int settlementauthority = balance.getInt("settlementauthority");
				if (settlementauthority == 1) {
					balance.set("settlement",
							balance.getBigDecimal("settlement").add(amount));
				} else if (settlementauthority == 2) {
					balance.set("newamount", balance.getBigDecimal("newamount")
							.add(amount));
				}
				singcard.set("accountvalue", accountvalue);
				singcard.set("result", result);
				singcard.set("info", info);
				singcard.update();
				balance.update();
				Employee_Person employee_Person = Employee_Person.dao.findById(id);
                if(employee_Person!=null){
                    int employeeid = employee_Person.getInt("employeeid");
                    Employees emp = Employees.dao.findById(employeeid);
                    emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(amount.multiply(emp.getBigDecimal("commission"))));
                    emp.update();
                }
			}
			List<String> resultList = CardyeeService.service
					.getSingResult(orderid);
			boolean bo = true;
			for (String resu : resultList) {
				if (resu.equals("2001")) {
					bo = false;
					break;
				}
			}
			if (bo) {
				CardyeeService.service.asynchronous(orderid);
			}
			renderText("OK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void querCirclip() {
		try {
			getRequest().setCharacterEncoding("gbk");

			String result = getPara("result");
			String orderno = getPara("orderno");
			String info = getPara("info");
			String value = getPara("value");
			String accountvalue = getPara("accountvalue");
			Circlip cir = Circlip.dao.findById(orderno);
			if(!cir.getStr("result").equals("2000")){
				if (result.equals("2000")) {
					Person per = Person.dao.findById(cir.getInt("id"));
					Balance balance = Balance.dao.findById(per.getInt("id"));
					String cardcode = cir.get("cardcode");
					BigDecimal big = PaymentService.service.getRate(cardcode,
							per.getInt("id"));
					BigDecimal amount = new BigDecimal(value).multiply(big);
					BigDecimal deducted = new BigDecimal(value).subtract(amount);
					cir.set("actual", amount);
					cir.set("accountvalue", accountvalue);
					cir.set("deducted", deducted);
					balance.set("amount",
							balance.getBigDecimal("amount").add(amount));
					balance.set("cardamount", balance.getBigDecimal("cardamount")
							.add(amount));
					balance.set("deductedcard",
							balance.getBigDecimal("deductedcard").add(deducted));
					int settlementauthority = balance.getInt("settlementauthority");
					if (settlementauthority == 2) {
						balance.set("newamount", balance.getBigDecimal("newamount")
								.add(amount));
					} else {
						balance.set("settlement",
								balance.getBigDecimal("settlement").add(amount));
					}
					balance.update();
					int employeeid = Employee_Person.dao.findById(per.getInt("id")).getInt("employeeid");
					Employees emp = Employees.dao.findById(employeeid);
					emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(amount.multiply(emp.getBigDecimal("commission"))));
					emp.update();
				}
				cir.set("result", result);
				cir.set("info", info);
				cir.update();
			}
			renderText("OK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cardret(){
		String orderid = getPara("orderid");
		try {
			CardyeeService.service.asynchronous(orderid);
			renderText("通知成功！");
		} catch (Exception e) {
			renderText("通知失败！");
			e.printStackTrace();
		}
	}

	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
}
