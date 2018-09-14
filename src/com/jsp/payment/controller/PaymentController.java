package com.jsp.payment.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import com.interceptor.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jsp.exchange.service.ExchangeService;
import com.jsp.payment.service.PaymentService;
import com.jsp.payment.service.PaymentTimer;
import com.pay.alipay.client.service.AlipayService;
import com.pay.alipay.server.config.AlipayConfig;
import com.pay.allinpay.client.service.AllinpayService;
import com.pay.baopay.client.service.BaopayService;
import com.pay.caifutong.client.service.CaiFuTongService;
import com.pay.cardyee.client.service.CardyeeService;
import com.pay.cardyee.server.XmlStringParse;
import com.pay.dinpay.client.serivce.DinpayService;
import com.pay.ease.client.service.EaseService;
import com.pay.ecpss.client.service.EcpssService;
import com.pay.ecpss.server.MD5;
import com.pay.fastmoney.client.service.FastMoneyService;
import com.pay.ips.client.service.IpsService;
import com.pay.w95epay.client.service.W95epayService;
import com.pay.yeepay.server.Configuration;
import com.pay.yeepay.server.PaymentForOnlineService;
import com.tool.CryptTool;
import com.vo.Caifutong;
import com.vo.CardCode;
import com.vo.Circlip;
import com.vo.Gateway;
import com.vo.Person;
import com.vo.PersonGateway;
import com.vo.Recharge;
import com.vo.System_interface;

@Before(LoginInterceptor.class)
public class PaymentController extends Controller {
	public void index() {
		renderJsp("/WEB-INF/jsp/CibPay.jsp");
	}
	
	public void cibpay() {		
		renderJsp("/WEB-INF/cib/reqpay.jsp");
	}
	
	public void payment() {
		Person person = getSessionAttr("session");
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		setAttr("rechordertotal", ExchangeService.exchangeService.getRechOrdertotal(person.getInt("id"), new Date()));
		setAttr("circordertotal", ExchangeService.exchangeService.getCircOrdertotal(person.getInt("id"), new Date()));
		renderJsp("/WEB-INF/jsp/payment1.jsp");
	}
	
	public void payment2() {
		Person person = getSessionAttr("session");
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		setAttr("rechordertotal", ExchangeService.exchangeService.getRechOrdertotal(person.getInt("id"), new Date()));
		setAttr("circordertotal", ExchangeService.exchangeService.getCircOrdertotal(person.getInt("id"), new Date()));
		renderJsp("/WEB-INF/jsp/payment2.jsp");
	}
	
	public void payment3() {
		Person person = getSessionAttr("session");
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		setAttr("rechordertotal", ExchangeService.exchangeService.getRechOrdertotal(person.getInt("id"), new Date()));
		setAttr("circordertotal", ExchangeService.exchangeService.getCircOrdertotal(person.getInt("id"), new Date()));
		renderJsp("/WEB-INF/jsp/payment3.jsp");
	}
	
	public void payment4() {
		Person person = getSessionAttr("session");
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		setAttr("rechordertotal", ExchangeService.exchangeService.getRechOrdertotal(person.getInt("id"), new Date()));
		setAttr("circordertotal", ExchangeService.exchangeService.getCircOrdertotal(person.getInt("id"), new Date()));
		renderJsp("/WEB-INF/jsp/payment4.jsp");
	}	
	
	public void viewExcel(){		
        createToken("withdrawalToken", 30*60);
		renderJsp("/WEB-INF/jsp/ExcelList.jsp");
	}


	public void reqpay() {
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + path;
		Person person = getSessionAttr("session");
		String p3_Amt = getPara("p3_Amt");
		String pd_FrpId = getPara("pd_FrpId");
		if (p3_Amt.equals("") && pd_FrpId.equals("")) {
			renderText("参数错误请重新充值");
		} else {
			PersonGateway pergetGateway = PersonGateway.dao.findById(person.getInt("id"));
			String num = getPara("num");
			Gateway gateway = null;
			if(num.equals("1")){
				gateway = Gateway.dao.findById(pergetGateway.getInt("paygete_id1"));
			}else if(num.equals("2")){
				gateway = Gateway.dao.findById(pergetGateway.getInt("paygete_id2"));
			}else if(num.equals("3")){
				gateway = Gateway.dao.findById(pergetGateway.getInt("paygete_id3"));
			}else if(num.equals("4")){
				gateway = Gateway.dao.findById(pergetGateway.getInt("paygete_id4"));
			}else if(num.equals("5")){
				gateway = Gateway.dao.findById(pergetGateway.getInt("paygete_id5"));
			}else{
				gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			}
			String p1_MerId = gateway.getStr("gateway_merid");
			
			String keyValue = gateway.getStr("gateway_key"); // 商家密钥
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSSmmm");
			String p2_Order = "RE" + sdf.format(new Date())
					+ CryptTool.getPasswordOfNumber(4) + person.get("id");
			String p5_Pid = "充值";
			String p6_Pcat = "充值";
			String p7_Pdesc = "充值";
			String p8_Url = basePath + "/payment/paymentReturn";
			String p9_SAF = "0";
			String pa_MP = "充值";
			int function = gateway.getInt("gateway_function");
			String hmac = "";
			if(function==1){
				hmac = PaymentForOnlineService
						.getReqMd5HmacForOnlinePayment("Buy", p1_MerId, p2_Order,
								p3_Amt, "CNY", p5_Pid, p6_Pcat, p7_Pdesc, p8_Url,
								p9_SAF, pa_MP, pd_FrpId, "1", keyValue);
			}
			Recharge recharge = new Recharge();
			recharge.set("rechargeid", p2_Order);
			recharge.set("ip", CryptTool.getIpAddr(getRequest()));
			recharge.set("p0_Cmd", "Buy");
			recharge.set("p1_MerId", person.getInt("id"));
			recharge.set("p2_Order", p2_Order);
			recharge.set("p3_Amt", p3_Amt);
			recharge.set("p4_Cur", "CNY");
			recharge.set("p5_Pid", p5_Pid);
			recharge.set("p6_Pcat", p6_Pcat);
			recharge.set("p7_Pdesc", p7_Pdesc);
			recharge.set("p8_Url", p8_Url);
			recharge.set("p9_SAF", p9_SAF);
			recharge.set("pa_MP", pa_MP);
			recharge.set("pd_FrpId", pd_FrpId);
			recharge.set("pr_NeedResponse", "1");
			recharge.set("hmac", hmac);
			recharge.set("CreateTime", new Date());
			recharge.set("success", 2);
			recharge.set("r1_Code", 2);
			recharge.set("lock", 1);
			recharge.save();
			
			
			
			//--------------------------------------------------------
			if(function==1){
				yibao(p1_MerId, keyValue, recharge);
			}else if(function==2){
				kuaiqian(p1_MerId, keyValue, recharge);;
			}else if(function==3){
				baopay(p1_MerId, keyValue, recharge);;
			}else if(function==4){
				caifutong(p1_MerId, keyValue, recharge);;
			}else if(function==5){
				ailpay(p1_MerId, keyValue, recharge);;
			}else if(function==6){
				dinpay(p1_MerId, keyValue, recharge);;
			}else if(function==7){
				haofu(p1_MerId, keyValue, recharge);;
			}else if(function==8){
				allinpay(p1_MerId, keyValue, recharge);;
			}else if(function==9){
				ease(p1_MerId, keyValue, recharge);;
			}else if(function==10){
				wapalipay(p1_MerId, keyValue, recharge);;
			}else if(function==12){
				ecpss(p1_MerId, keyValue, recharge);;
			}else if(function==13){
			    baopaykuaijie(p1_MerId, keyValue, recharge);;
            }else if(function==14){
            	ips(p1_MerId, keyValue, recharge);;
            }else if(function==15){
            	w95epay(p1_MerId, keyValue, recharge);;
            }else{
				renderText("系统错误！");
			}
			//--------------------------------------------------------
			
			
		}
	}
	
	/**
	 * 双乾接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void w95epay(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("MerNo", p1_MerId);
		setAttr("MD5key", keyValue);
		setAttr("BillNo", recharge.get("rechargeid"));
		setAttr("Amount", recharge.get("p3_Amt"));
		String pd_FrpId = W95epayService.service.getW95epayParticipate(recharge.getStr("pd_FrpId"));
		setAttr("PaymentType", pd_FrpId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String ReturnURL = httpurl+"/w95epay/payresult";
		setAttr("ReturnURL", ReturnURL);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/95epay/paysubmit.jsp");
	}
	
	/**
	 * 环迅接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param recharge
	 */
	public void ips(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("Mer_code", p1_MerId);
		setAttr("Mer_key", keyValue);
		setAttr("Billno", recharge.get("rechargeid"));
		setAttr("Amount", recharge.get("p3_Amt"));
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		setAttr("Date", format.format(new Date()));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String Merchanturl = httpurl + "/ips/paymentMerchanturl";
		setAttr("Merchanturl", Merchanturl);
		setAttr("FailUrl", Merchanturl);
		String ServerUrl = httpurl + "/ips/paymentServerUrl";
		setAttr("ServerUrl", ServerUrl);
		String pd_FrpId = IpsService.service.getIpsParticipate(recharge.getStr("pd_FrpId"));
		String Bankco = IpsService.service.getbankebh(p1_MerId, keyValue, pd_FrpId);
		setAttr("Bankco", Bankco);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ips/redirect.jsp");
	}
	
	/**
	 * 汇潮支付接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void ecpss(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("MerNo", p1_MerId);
		setAttr("BillNo", recharge.get("rechargeid"));
		setAttr("Amount", recharge.get("p3_Amt"));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String ReturnURL =  httpurl+"/ecpss/paymentReturnURL";
		setAttr("ReturnURL", ReturnURL);
		setAttr("AdviceURL", httpurl+"/ecpss/paymentAdviceURL");
		String md5Str = p1_MerId +"&"+ recharge.get("rechargeid") +"&"+ recharge.get("p3_Amt") +"&"+ ReturnURL +"&"+ keyValue ;
		MD5 md5 = new MD5();
		setAttr("SignInfo", md5.getMD5ofStr(md5Str).toUpperCase());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		setAttr("orderTime", format.format(new Date()));
		String pd_FrpId = EcpssService.service.getEcpssParticipate(recharge.getStr("pd_FrpId"));
		setAttr("defaultBankNumber", pd_FrpId);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ecpss/send.jsp");
	}
	
	/**
	 * 支付宝手机接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void wapalipay(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("out_trade_no", recharge.get("rechargeid"));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/wapalipay/paymentnotify_url");
		setAttr("call_back_url", httpurl+"/wapalipay/paymentcall_back_url");
		String alipayemail = Configuration.getInstance().getValue("alipayemail");
		setAttr("seller_email", alipayemail);
		setAttr("total_fee", recharge.get("p3_Amt"));
		com.pay.wapalipay.server.config.AlipayConfig.partner = p1_MerId;
		com.pay.wapalipay.server.config.AlipayConfig.key = keyValue;
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/wapalipay/alipayapi.jsp");
	}
	
	/**
	 * 首信易接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param recharge
	 */
	public void ease(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("v_mid", p1_MerId);
		setAttr("v_oid", recharge.get("rechargeid"));
		setAttr("v_amount", recharge.get("p3_Amt"));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("v_url", httpurl+"/ease/paymentreceived1");
		setAttr("MD5Key", keyValue);
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String v_pmode = EaseService.service.getEaseParticipate(pd_FrpId);
		setAttr("v_pmode", v_pmode);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ease/send.jsp");
	}
	
	/**
	 * 通联接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void allinpay(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("key", keyValue);
		setAttr("merchantId", p1_MerId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("pickupUrl", httpurl+"/allinpay/paymentpickupUrl");
		setAttr("receiveUrl", httpurl+"/allinpay/paymentreceiveUrl");
		setAttr("orderNo", recharge.get("rechargeid"));
		String orderAmount = (new BigDecimal((String) recharge.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
		if(orderAmount.indexOf(".") > 0){  
			orderAmount = orderAmount.replaceAll("0+?$", "");//去掉多余的0  
			orderAmount = orderAmount.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		setAttr("orderAmount", orderAmount);
		Date createtime = recharge.get("CreateTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		setAttr("orderDatetime", sdf.format(createtime));
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String issuerId = AllinpayService.service.getAllinpayParticipate(pd_FrpId);
		setAttr("issuerId", issuerId);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/allinpay/post.jsp");
	}
	
	public void haofu(String p1_MerId, String keyValue, Recharge recharge){
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + path;
		setAttr("p0_Cmd", "Buy");
		setAttr("p1_MerId", p1_MerId);
		setAttr("p2_Order",recharge.get("rechargeid"));
		setAttr("p3_Amt",recharge.get("p3_Amt"));
		setAttr("pd_FrpId",recharge.get("pd_FrpId"));
		setAttr("p4_Cur", "CNY");
		setAttr("p5_Pid",recharge.get("p5_Pid"));
		setAttr("p6_Pcat",recharge.get("p6_Pcat"));
		setAttr("p7_Pdesc",recharge.get("p7_Pdesc"));
		setAttr("p8_Url", basePath + "/payment/haofupaymentReturn");
		setAttr("p9_SAF",recharge.get("p9_SAF"));
		setAttr("pa_MP",recharge.get("pa_MP"));
		setAttr("pr_NeedResponse", "1");
		setAttr("nodeAuthorizationURL", "http://www.haofpay.com/hspay/node");
		String hmac = PaymentForOnlineService
				.getReqMd5HmacForOnlinePayment("Buy", p1_MerId, recharge.getStr("rechargeid"),
						recharge.getStr("p3_Amt"), "CNY", recharge.getStr("p5_Pid"), recharge.getStr("p6_Pcat"), recharge.getStr("p7_Pdesc"), basePath + "/payment/haofupaymentReturn",
						recharge.getStr("p9_SAF"), recharge.getStr("pa_MP"), recharge.getStr("pd_FrpId"), "1", keyValue);
		setAttr("hmac",hmac);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer), 15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/yeepay/reqpay.jsp");
	}
	
	/**
	 * 智付接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void dinpay(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("merchant_code", p1_MerId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/dinpay/paymentDinpayToMer");
		setAttr("order_amount", recharge.get("p3_Amt"));
		setAttr("order_no", recharge.get("rechargeid"));
		Date createtime = recharge.get("CreateTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setAttr("order_time", sdf.format(createtime));
		setAttr("product_name", formatString(recharge.getStr("p5_Pid")));
		setAttr("return_url", httpurl+"/dinpay/paymentDinpayToMer");
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String bank_code = DinpayService.service.getDinpayParticipate(pd_FrpId);
		setAttr("bank_code", bank_code);
		setAttr("key", keyValue);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/dinpay/MerDinpayUTF-8.jsp");
	}
	
	public void caifutong(String p1_MerId, String keyValue, Recharge recharge){
		StringBuffer url = new StringBuffer();
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		url.append(httpurl+"/caifutong/splitPayRequest?");
		url.append("&key="+keyValue);
		url.append("&bargainor_id="+p1_MerId);
		url.append("&return_url="+httpurl+"/caifutong/paymentsplitPayResponse");
		url.append("&sp_billno="+recharge.getStr("rechargeid"));
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String bank_type = CaiFuTongService.service.getCaiFuParticipate(pd_FrpId);
		url.append("&bank_type="+bank_type);
		url.append("&desc="+recharge.get("p5_Pid"));
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		Caifutong caifutong = Caifutong.dao.findById(id);
		BigDecimal caifu_rate = caifutong.getBigDecimal("caifu_rate");
		BigDecimal total_fee = new BigDecimal((String) recharge.get("p3_Amt"));
		BigDecimal amt1 = total_fee.multiply(caifu_rate);
		BigDecimal amt2 = total_fee.subtract(amt1);
		String total_fees = total_fee.toString();
		if(total_fees.indexOf(".") > 0){  
			total_fees = total_fees.replaceAll("0+?$", "");//去掉多余的0  
			total_fees = total_fees.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&total_fee="+total_fee);
		String amts1 = amt1.toString();
		if(amts1.indexOf(".") > 0){  
			amts1 = amts1.replaceAll("0+?$", "");//去掉多余的0  
			amts1 = amts1.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts1="+amts1);
		String amts2 = amt2.toString();
		if(amts2.indexOf(".") > 0){  
			amts2 = amts2.replaceAll("0+?$", "");//去掉多余的0  
			amts2 = amts2.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts2="+amts2);
		String caifutongemial = systeminterface.getStr("system_interface_caifutongemial");
		url.append("&caifutongemial="+caifutongemial);
		url.append("&caifu_account="+caifutong.getStr("caifu_account"));
		url.append("&ip="+recharge.getStr("ip"));
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		url.append("&title="+title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		url.append("&titletime="+titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("orderid"), timer),
				15 * 60 * 1000);
		redirect(url.toString());
	}
	
	public void ailpay(String p1_MerId, String keyValue, Recharge recharge){
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/alipay/paymentnotify_url");
		setAttr("return_url", httpurl+"/alipay/paymentreturn_url");
		AlipayConfig.partner = p1_MerId;
		AlipayConfig.key = keyValue;
		String seller_email = systeminterface.getStr("system_interface_alipayemail");
		setAttr("seller_email", seller_email);
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String defaultbank = AlipayService.service.getAlipayParticipate(pd_FrpId);
		setAttr("defaultbank", defaultbank);
		setAttr("out_trade_no", recharge.get("rechargeid"));
		setAttr("subject", formatString(recharge.getStr("p5_Pid")));
		setAttr("total_fee", recharge.get("p3_Amt"));
		setAttr("body", formatString(recharge.getStr("p7_Pdesc")));
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/alipay/alipayapi.jsp");
	}
	
	
	public void yibao(String p1_MerId, String keyValue, Recharge recharge){
		setAttr("p0_Cmd", "Buy");
		setAttr("p1_MerId", p1_MerId);
		setAttr("p2_Order",recharge.get("rechargeid"));
		setAttr("p3_Amt",recharge.get("p3_Amt"));
		setAttr("pd_FrpId",recharge.get("pd_FrpId"));
		setAttr("p4_Cur", "CNY");
		setAttr("p5_Pid",recharge.get("p5_Pid"));
		setAttr("p6_Pcat",recharge.get("p6_Pcat"));
		setAttr("p7_Pdesc",recharge.get("p7_Pdesc"));
		setAttr("p8_Url",recharge.get("p8_Url"));
		setAttr("p9_SAF",recharge.get("p9_SAF"));
		setAttr("pa_MP",recharge.get("pa_MP"));
		setAttr("pr_NeedResponse", "1");
		setAttr("nodeAuthorizationURL", "https://www.yeepay.com/app-merchant-proxy/node");
		setAttr("hmac",recharge.get("hmac"));
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer), 15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/yeepay/reqpay.jsp");
	}
	
	public void kuaiqian(String merchantAcctId, String keyValue, Recharge recharge){
		setAttr("merchantAcctId", merchantAcctId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("bgUrl", httpurl+"/payment/fastcallback");
		setAttr("orderId", recharge.get("rechargeid"));
		String orderamount = (new BigDecimal((String) recharge.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
		if(orderamount.indexOf(".") > 0){  
			orderamount = orderamount.replaceAll("0+?$", "");//去掉多余的0  
			orderamount = orderamount.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		setAttr("orderAmount", orderamount);
		setAttr("productName", recharge.get("p5_Pid"));
		setAttr("productDesc", recharge.get("p7_Pdesc"));
		String pd_FrpId = recharge.getStr("pd_FrpId");
		String bankId = FastMoneyService.service.getFastParticipate(pd_FrpId);
		setAttr("bankId", bankId);
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		setAttr("title", title);
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		setAttr("titletime", titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer), 15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/fastmoney/send.jsp");
	}

	public void baopay(String p1_MerId, String keyValue, Recharge recharge){
		String PayID = BaopayService.service.getBaopayParticipate(recharge.getStr("pd_FrpId"));
		String OrderMoney = (new BigDecimal((String) recharge.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
		if(OrderMoney.indexOf(".") > 0){  
			OrderMoney = OrderMoney.replaceAll("0+?$", "");//去掉多余的0  
			OrderMoney = OrderMoney.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		StringBuffer url = new StringBuffer();
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		url.append(httpurl+"/baopay/post");
		url.append("?MerchantID="+p1_MerId+"&");
		url.append("PayID="+PayID+"&");
		Date createtime = recharge.get("CreateTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        url.append("TradeDate="+sdf.format(createtime)+"&");
		url.append("TransID="+recharge.get("rechargeid")+"&");
		url.append("OrderMoney="+OrderMoney+"&");
		url.append("ProductName="+formatString(recharge.getStr("p5_Pid"))+"&");
		url.append("AdditionalInfo="+recharge.get("pa_MP")+"&");
		url.append("Merchant_url="+httpurl+"/baopay/paymentbaomerchant&");
		url.append("Return_url="+httpurl+"/baopay/paymentbaoreturn&");
		url.append("Md5key="+keyValue+"&");
		Person person = getSessionAttr("session");
		int id = person.getInt("id");
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String title = pergate.get("title");
		if(title==null||title.equals("")){
			title = "提交中。。。";
		}
		try {
			title = java.net.URLEncoder.encode(title,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.append("title="+title+"&");
		String titletime = pergate.get("titletime");
		if(titletime==null||titletime.equals("")){
			titletime = "1000";
		}
		url.append("titletime="+titletime);
		Timer timer = new Timer();
		timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer), 15 * 60 * 1000);
		System.out.println(url.toString());
		redirect(url.toString());
	}
	
	public void baopaykuaijie(String p1_MerId, String keyValue, Recharge recharge){
        String PayID = "1080";
        String OrderMoney = (new BigDecimal((String) recharge.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
        if(OrderMoney.indexOf(".") > 0){  
            OrderMoney = OrderMoney.replaceAll("0+?$", "");//去掉多余的0  
            OrderMoney = OrderMoney.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
        StringBuffer url = new StringBuffer();
        System_interface systeminterface = System_interface.dao.findById(1);
        String httpurl = systeminterface.getStr("system_interface_httpurl");
        url.append(httpurl+"/baopay/post");
        url.append("?MerchantID="+p1_MerId+"&");
        url.append("PayID="+PayID+"&");
        Date createtime = recharge.get("CreateTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        url.append("TradeDate="+sdf.format(createtime)+"&");
        url.append("TransID="+recharge.get("rechargeid")+"&");
        url.append("OrderMoney="+OrderMoney+"&");
        url.append("ProductName="+formatString(recharge.getStr("p5_Pid"))+"&");
        url.append("AdditionalInfo="+recharge.get("pa_MP")+"&");
        url.append("Merchant_url="+httpurl+"/baopay/paymentbaomerchant&");
        url.append("Return_url="+httpurl+"/baopay/paymentbaoreturn&");
        url.append("Md5key="+keyValue+"&");
        Person person = getSessionAttr("session");
        int id = person.getInt("id");
        PersonGateway pergate = PersonGateway.dao.findById(id);
        String title = pergate.get("title");
        if(title==null||title.equals("")){
            title = "提交中。。。";
        }
        try {
            title = java.net.URLEncoder.encode(title,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        url.append("title="+title+"&");
        String titletime = pergate.get("titletime");
        if(titletime==null||titletime.equals("")){
            titletime = "1000";
        }
        url.append("titletime="+titletime);
        Timer timer = new Timer();
        timer.schedule(new PaymentTimer(recharge.getStr("rechargeid"), timer), 15 * 60 * 1000);
        System.out.println(url.toString());
        redirect(url.toString());
    }
	
	@Clear
	public void paymentReturn() {
		renderJsp("/WEB-INF/pay/yeepay/paymentCallback.jsp");
	}
	
	@Clear
	public void haofupaymentReturn(){
		renderJsp("/WEB-INF/pay/haofu/paymentCallback.jsp");
	}
	
	@Clear
	public void showPayOk() {
		renderJsp("/WEB-INF/pay/yeepay/payok.jsp");
	}
	
	@Clear
	public void showPayNo() {
		renderJsp("/WEB-INF/pay/yeepay/payno.jsp");
	}
	
	@Clear
	public void fastcallback(){
		renderJsp("/WEB-INF/pay/fastmoney/paymentreceive.jsp");
	}
	
	
	
	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}

	public void getre() {
		String p3_Amt = getPara("p3_Amt");
		String pd_FrpId = getPara("pd_FrpId");
		String num = getPara("num");
		setAttr("num", num);
		setAttr("p3_Amt", p3_Amt);
		setAttr("pd_FrpId", pd_FrpId);
		renderJsp("/WEB-INF/pay/yeepay/recharge.jsp");
	}

	public void getRech() {
		int page = getParaToInt("rechpage");
		String rechstarttime = "";
		String rechendtime = "";
		String rechparticipate = "";
		String rechstate = "";
		String rechid = "";
		if (!getPara("rechstarttime").equals("")) {
			rechstarttime = getPara("rechstarttime");
		}
		if (!getPara("rechendtime").equals("")) {
			rechendtime = getPara("rechendtime");
		}
		if (!getPara("rechparticipate").equals("0")) {
			rechparticipate = getPara("rechparticipate");
		}
		if (!getPara("rechstate").equals("0")) {
			rechstate = getPara("rechstate");
		}
		if (!getPara("rechid").equals("")) {
			rechid = getPara("rechid");
		}
		Person per = getSessionAttr("session");
		Page<Recharge> orderPage = PaymentService.service.getRechOrder(
				per.getInt("id"), page, rechstarttime, rechendtime,
				rechparticipate, rechstate, rechid);
		List<Recharge> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		renderJson(json);
	}

	public void getCirc() {
		int page = getParaToInt("circpage");
		String circstarttime = "";
		String circendtime = "";
		String circparticipate = "";
		String circstate = "";
		String circid = "";
		if (!getPara("circstarttime").equals("")) {
			circstarttime = getPara("circstarttime");
		}
		if (!getPara("circendtime").equals("")) {
			circendtime = getPara("circendtime");
		}
		if (!getPara("circparticipate").equals("0")) {
			circparticipate = getPara("circparticipate");
		}
		if (!getPara("circstate").equals("0")) {
			circstate = getPara("circstate");
		}
		if (!getPara("circid").equals("")) {
			circid = getPara("circid");
		}
		Person per = getSessionAttr("session");
		Page<Circlip> orderPage = PaymentService.service.getCircOrder(
				per.getInt("id"), page, circstarttime, circendtime,
				circparticipate, circstate, circid);
		List<Circlip> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		renderJson(json);
	}

	public void circlip() {
		try {
			String url = getPara("url");
			int cardid = getParaToInt("cardid");
			String amount = getPara("amount");
			if (amount != null) {
				String cardno = getPara("cardno");
				String cardpass = getPara("cardpass");
				Person per = getSessionAttr("session");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSSmmm");
				String orderid = "CI" + sdf.format(new Date())
						+ CryptTool.getPasswordOfNumber(3) + per.get("id");
				String cardcode = CardCode.dao.findById(cardid).getStr(
						"participate");
				String cardcodes = cardcode + amount;
				Circlip circlip = new Circlip();
				XmlStringParse xml = CardyeeService.service.HanCard(orderid,
						url, cardno, cardpass, cardcodes);
				String result = xml.getParameter("result");
				circlip.set("circlipid", orderid);
				circlip.set("cardid", cardid);
				circlip.set("cardcode", cardcode);
				circlip.set("cardno", cardno);
				circlip.set("cardpass", cardpass);
				circlip.set("datetime", new Date());
				circlip.set("result", result);
				circlip.set("info", xml.getParameter("info"));
				circlip.set("amount", amount);
				circlip.set("sign", xml.getParameter("sign"));
				circlip.set("billid", xml.getParameter("billid"));
				circlip.set("result", xml.getParameter("result"));
				circlip.set("id", per.get("id"));
				circlip.set("xml", xml.getSrc());
				circlip.save();
				renderJson("{\"info\":\"提交成功！\",\"status\":\"y\"}");
			} else {
				renderJson("{\"info\":\"提交失败，未选择金额！\",\"status\":\"n\"}");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			renderJson("{\"info\":\"提交失败！\",\"status\":\"n\"}");
			e.printStackTrace();
		}
	}
	
	public void ApiDownload(){        
        String ExcelPath = PathKit.getWebRootPath() + "\\download\\jyypay.rar";
        File excelFile = new File(ExcelPath);
        if(excelFile.isFile()){
         renderFile(excelFile); 
        }else{
        	System.out.println("文件不存在或者路劲不正确"+ExcelPath);
        }
    }
}