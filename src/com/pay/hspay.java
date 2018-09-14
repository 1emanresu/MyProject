package com.pay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import com.jfinal.core.Controller;
import com.pay.alipay.client.service.AlipayService;
import com.pay.alipay.server.config.AlipayConfig;
import com.pay.allinpay.client.service.AllinpayService;
import com.pay.baopay.client.service.BaopayService;
import com.pay.caifutong.client.service.CaiFuTongService;
import com.pay.dinpay.client.serivce.DinpayService;
import com.pay.ease.client.service.EaseService;
import com.pay.ecpss.client.service.EcpssService;
import com.pay.ecpss.server.MD5;
import com.pay.fastmoney.client.service.FastMoneyService;
import com.pay.ips.client.service.IpsService;
import com.pay.yeepay.client.service.YeepayService;
import com.pay.yeepay.client.service.YeepayThread;
import com.pay.yeepay.client.service.yeepayTimer;
import com.pay.yeepay.server.Configuration;
import com.pay.yeepay.server.PaymentForOnlineService;
import com.tool.CryptTool;
import com.vo.Caifutong;
import com.vo.Gateway;
import com.vo.Order;
import com.vo.Person;
import com.vo.PersonGateway;
import com.vo.System_interface;

public class hspay extends Controller {
	
	public void node() {
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				String orderid = "O" + sdf.format(new Date())
						+ CryptTool.getPasswordOfNumber(2) + p1_MerId;
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
					
					int function = gateway.getInt("gateway_function");
					//--------------------------------------------------------
					if(function==1){
						yibao(p1_MerId, keyValue, order);
					}else if(function==2){
						kuaiqian(p1_MerId, keyValue, order);;
					}else if(function==3){
						baopay(p1_MerId, keyValue, order);
					}else if(function==4){
						caifutong(p1_MerId, keyValue, order);
					}else if(function==5){
						ailpay(p1_MerId, keyValue, order);
					}else if(function==6){
						dinpay(p1_MerId, keyValue, order);
					}else if(function==7){
						haofu(p1_MerId, keyValue, order);
					}else if(function==8){
						allinpay(p1_MerId, keyValue, order);
					}else if(function==9){
						ease(p1_MerId, keyValue, order);
					}else if(function==10){
						wapalipay(p1_MerId, keyValue, order);
					}else if(function==12){
						ecpss(p1_MerId, keyValue, order);
					}else if(function==13){
                        baopaykuaijie(p1_MerId, keyValue, order);
                    }else if(function==14){
                    	ips(p1_MerId, keyValue, order);
                    }else if(function==15){
                    	w95epay(p1_MerId, keyValue, order);
                    }else{
						renderText("系统错误！");
					}
					//--------------------------------------------------------
				} else {
					renderText("抱歉,交易签名无效");
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 双乾接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void w95epay(String p1_MerId, String keyValue, Order order){
		setAttr("MerNo", p1_MerId);
		setAttr("MD5key", keyValue);
		setAttr("BillNo", order.get("orderid"));
		setAttr("Amount", order.get("p3_Amt"));
		String pd_FrpId = "";//W95epayService.service.getW95epayParticipate(order.getStr("pd_FrpId"));
		setAttr("PaymentType", pd_FrpId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String ReturnURL = httpurl+"/w95epay/payresult";
		setAttr("ReturnURL", ReturnURL);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/95epay/paysubmit.jsp");
	}
	
	/**
	 * 环迅接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void ips(String p1_MerId, String keyValue, Order order){
		setAttr("Mer_code", p1_MerId);
		setAttr("Mer_key", keyValue);
		setAttr("Billno", order.get("orderid"));
		setAttr("Amount", order.get("p3_Amt"));
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		setAttr("Date", format.format(new Date()));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String Merchanturl = httpurl + "/ips/Merchanturl";
		setAttr("Merchanturl", Merchanturl);
		setAttr("FailUrl", Merchanturl);
		String ServerUrl = httpurl + "/ips/ServerUrl";
		setAttr("ServerUrl", ServerUrl);
		String pd_FrpId = IpsService.service.getIpsParticipate(order.getStr("pd_FrpId"));
		String Bankco = IpsService.service.getbankebh(p1_MerId, keyValue, pd_FrpId);
		setAttr("Bankco", Bankco);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ips/redirect.jsp");
	}
	
	/**
	 * 汇潮支付接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void ecpss(String p1_MerId, String keyValue, Order order){
		setAttr("MerNo", p1_MerId);
		setAttr("BillNo", order.get("orderid"));
		setAttr("Amount", order.get("p3_Amt"));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		String ReturnURL = httpurl + "/ecpss/ReturnURL";
		setAttr("ReturnURL", ReturnURL);
		setAttr("AdviceURL", httpurl+"/ecpss/AdviceURL");
		String md5Str = p1_MerId+"&"+order.get("orderid")+"&"+order.get("p3_Amt")+"&"+ReturnURL+"&"+ keyValue;
		MD5 md5 = new MD5();
		setAttr("SignInfo", md5.getMD5ofStr(md5Str).toUpperCase());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		setAttr("orderTime", format.format(new Date()));
		String pd_FrpId = EcpssService.service.getEcpssParticipate(order.getStr("pd_FrpId"));
		setAttr("defaultBankNumber", pd_FrpId);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ecpss/send.jsp");
	}
	
	/**
	 * 支付宝手机接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void wapalipay(String p1_MerId, String keyValue, Order order){
		setAttr("out_trade_no", order.get("orderid"));
		System_interface systemInterface = System_interface.dao.findById(1);
		String httpurl = systemInterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/wapalipay/notify_url");
		setAttr("call_back_url", httpurl+"/wapalipay/call_back_url");
		String alipayemail = Configuration.getInstance().getValue("alipayemail");
		setAttr("seller_email", alipayemail);
		setAttr("total_fee", order.get("p3_Amt"));
		com.pay.wapalipay.server.config.AlipayConfig.partner = p1_MerId;
		com.pay.wapalipay.server.config.AlipayConfig.key = keyValue;
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/wapalipay/alipayapi.jsp");
	}
	
	/**
	 * 首信易接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param recharge
	 */
	public void ease(String p1_MerId, String keyValue, Order order){
		setAttr("v_mid", p1_MerId);
		setAttr("v_oid", order.get("orderid"));
		setAttr("v_amount", order.get("p3_Amt"));
		System_interface systemInterface = System_interface.dao.findById(1);
		String httpurl = systemInterface.getStr("system_interface_httpurl");
		setAttr("v_url", httpurl+"/ease/received1");
		setAttr("MD5Key", keyValue);
		String pd_FrpId = order.getStr("pd_FrpId");
		String v_pmode = EaseService.service.getEaseParticipate(pd_FrpId);
		setAttr("v_pmode", v_pmode);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/ease/send.jsp");
	}
	
	/**
	 * 通联接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void allinpay(String p1_MerId, String keyValue, Order order){
		setAttr("key", keyValue);
		setAttr("merchantId", p1_MerId);
		System_interface systemInterface = System_interface.dao.findById(1);
		String httpurl = systemInterface.getStr("system_interface_httpurl");
		setAttr("pickupUrl", httpurl+"/allinpay/pickupUrl");
		setAttr("receiveUrl", httpurl+"/allinpay/receiveUrl");
		setAttr("orderNo", order.get("orderid"));
		String orderAmount = (new BigDecimal((String) order.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
		if(orderAmount.indexOf(".") > 0){  
			orderAmount = orderAmount.replaceAll("0+?$", "");//去掉多余的0  
			orderAmount = orderAmount.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		setAttr("orderAmount", orderAmount);
		Date createtime = order.get("CreateTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDhhmmss");
		setAttr("orderDatetime", sdf.format(createtime));
		String pd_FrpId = order.getStr("pd_FrpId");
		String issuerId = AllinpayService.service.getAllinpayParticipate(pd_FrpId);
		setAttr("issuerId", issuerId);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/allinpay/post.jsp");
	}
	
	/**
	 * 浩付提交
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void haofu(String p1_MerId, String keyValue, Order order ){
		String nodeAuthorizationURL = "http://www.haofpay.com/hspay/node"; // 交易请求地址
		setAttr("p0_Cmd", order.get("p0_Cmd"));
		setAttr("p1_MerId", p1_MerId);
		setAttr("p2_Order", order.get("orderid"));
		setAttr("p3_Amt", order.get("p3_Amt"));
		setAttr("p4_Cur", order.get("p4_Cur"));
		setAttr("p5_Pid", order.get("p5_Pid"));
		setAttr("p6_Pcat", order.get("p6_Pcat"));
		setAttr("p7_Pdesc", order.get("p7_Pdesc"));
		System_interface systemInterface = System_interface.dao.findById(1);
		String httpurl = systemInterface.getStr("system_interface_httpurl");
		setAttr("p8_Url", httpurl+"/yeepay/haofucallbank");
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
						formatString(httpurl+"/yeepay/haofucallbank"),
						formatString(order.getStr("p9_SAF")),
						formatString(order.getStr("pa_MP")),
						formatString(order.getStr("pd_FrpId")),
						formatString(order
								.getStr("pr_NeedResponse")),
						keyValue);
		setAttr("hmac", hmac1);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/yeepay/reqpay.jsp");
		
	}
	
	/**
	 * 智付接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void dinpay(String p1_MerId, String keyValue, Order order){
		setAttr("merchant_code", p1_MerId);
		System_interface systemInterface = System_interface.dao.findById(1);
		String httpurl = systemInterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/dinpay/dinpayToMer");
		String p3_Amt = order.get("p3_Amt");
		BigDecimal value = new BigDecimal(p3_Amt);
		// 小数点两位，四舍五入
		//value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
		setAttr("order_amount", value.toString());
		setAttr("order_no", order.get("orderid"));
		Date createtime = order.get("CreateTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setAttr("order_time", sdf.format(createtime));
		setAttr("product_name", "充值");
		setAttr("return_url", httpurl+"/dinpay/dinpayToMer");
		String pd_FrpId = order.getStr("pd_FrpId");
		String bank_code = DinpayService.service.getDinpayParticipate(pd_FrpId);
		setAttr("bank_code", bank_code);
		setAttr("key", keyValue);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/dinpay/MerDinpayUTF-8.jsp");
	}
	
	/**
	 * 支付宝接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void ailpay(String p1_MerId, String keyValue, Order order ){
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("notify_url", httpurl+"/alipay/notify_url");
		setAttr("return_url", httpurl+"/alipay/return_url");
		AlipayConfig.partner = p1_MerId;
		AlipayConfig.key = keyValue;
		String seller_email = systeminterface.getStr("system_interface_alipayemail");
		setAttr("seller_email", seller_email);
		setAttr("out_trade_no", order.get("orderid"));
		setAttr("subject", formatString(order.getStr("p5_Pid")));
		String pd_FrpId = order.getStr("pd_FrpId");
		String defaultbank = AlipayService.service.getAlipayParticipate(pd_FrpId);
		setAttr("defaultbank", defaultbank);
		setAttr("total_fee", order.get("p3_Amt"));
		setAttr("body", formatString(order.getStr("p7_Pdesc")));
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/alipay/alipayapi.jsp");
	}
	
	/**
	 * 财付通接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void caifutong(String p1_MerId, String keyValue, Order order ){
		StringBuffer url = new StringBuffer();
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		url.append(httpurl+"/caifutong/splitPayRequest?");
		url.append("&key="+keyValue);
		url.append("&bargainor_id="+p1_MerId);
		url.append("&return_url="+httpurl+"/caifutong/splitPayResponse");
		url.append("&sp_billno="+order.getStr("orderid"));
		String pd_FrpId = order.getStr("pd_FrpId");
		String bank_type = CaiFuTongService.service.getCaiFuParticipate(pd_FrpId);
		url.append("&bank_type="+bank_type);
		url.append("&desc="+order.get("p5_Pid"));
		String id = order.get("p1_MerId");
		Caifutong caifutong = Caifutong.dao.findById(id);
		BigDecimal caifu_rate = caifutong.getBigDecimal("caifu_rate");
		BigDecimal total_fee = new BigDecimal((String) order.get("p3_Amt"));
		BigDecimal amt1 = total_fee.multiply(caifu_rate);
		BigDecimal amt2 = total_fee.subtract(amt1);
		String total_fees = total_fee.multiply(BigDecimal.valueOf(100)).toString();
		if(total_fees.indexOf(".") > 0){  
			total_fees = total_fees.replaceAll("0+?$", "");//去掉多余的0  
			total_fees = total_fees.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&total_fee="+total_fees);
		String amts1 = amt1.multiply(BigDecimal.valueOf(100)).toString();
		if(amts1.indexOf(".") > 0){  
			amts1 = amts1.replaceAll("0+?$", "");//去掉多余的0  
			amts1 = amts1.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts1="+amts1);
		String amts2 = amt2.multiply(BigDecimal.valueOf(100)).toString();
		if(amts2.indexOf(".") > 0){  
			amts2 = amts2.replaceAll("0+?$", "");//去掉多余的0  
			amts2 = amts2.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts2="+amts2);
		String caifutongemial = systeminterface.getStr("system_interface_caifutongemial");
		url.append("&caifutongemial="+caifutongemial);
		url.append("&caifu_account="+caifutong.getStr("caifu_account"));
		url.append("&ip="+order.getStr("ip"));
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		Thread caifutongThred = new Thread(new YeepayThread(order.getStr("orderid")));
		caifutongThred.start();
		redirect(url.toString());
	}
	
	
	/**
	 * 易宝提交
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void yibao(String p1_MerId, String keyValue, Order order ){
		String nodeAuthorizationURL = "https://www.yeepay.com/app-merchant-proxy/node"; // 交易请求地址
		setAttr("p0_Cmd", order.get("p0_Cmd"));
		setAttr("p1_MerId", p1_MerId);
		setAttr("p2_Order", order.get("orderid"));
		setAttr("p3_Amt", order.get("p3_Amt"));
		setAttr("p4_Cur", order.get("p4_Cur"));
		setAttr("p5_Pid", order.get("p5_Pid"));
		setAttr("p6_Pcat", order.get("p6_Pcat"));
		setAttr("p7_Pdesc", order.get("p7_Pdesc"));
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("p8_Url", httpurl+"/yeepay/callback");
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
						formatString(httpurl+"/yeepay/callback"),
						formatString(order.getStr("p9_SAF")),
						formatString(order.getStr("pa_MP")),
						formatString(order.getStr("pd_FrpId")),
						formatString(order
								.getStr("pr_NeedResponse")),
						keyValue);
		setAttr("hmac", hmac1);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/yeepay/reqpay.jsp");
		
	}
	
	
	/**
	 * 块钱接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void kuaiqian(String merchantAcctId, String keyValue, Order order){
		setAttr("merchantAcctId", merchantAcctId);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		setAttr("bgUrl", httpurl+"/fastmoney/callback");
		setAttr("orderId", order.get("orderid"));
		String orderamount = (new BigDecimal((String) order.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
		if(orderamount.indexOf(".") > 0){  
			orderamount = orderamount.replaceAll("0+?$", "");//去掉多余的0  
			orderamount = orderamount.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		setAttr("orderAmount", orderamount);
		setAttr("productName", order.get("p5_Pid"));
		setAttr("productDesc", order.get("p7_Pdesc"));
		String pd_FrpId = order.getStr("pd_FrpId");
		String bankId = FastMoneyService.service.getFastParticipate(pd_FrpId);
		setAttr("bankId", bankId);
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/fastmoney/send.jsp");
	}
	
	
	
	/**
	 * 宝付接口
	 * @param p1_MerId
	 * @param keyValue
	 * @param order
	 */
	public void baopay(String p1_MerId, String keyValue, Order order){
		String PayID = BaopayService.service.getBaopayParticipate(order.getStr("pd_FrpId"));
		String OrderMoney = (new BigDecimal((String) order.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
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
		Date createtime = order.get("CreateTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        url.append("TradeDate="+sdf.format(createtime)+"&");
		url.append("TransID="+order.get("orderid")+"&");
		url.append("OrderMoney="+OrderMoney+"&");
		url.append("ProductName="+formatString(order.getStr("p5_Pid"))+"&");
		url.append("AdditionalInfo="+formatString(order.getStr("pa_MP"))+"&");
		url.append("Merchant_url="+httpurl+"/baopay/Merchant_url&");
		url.append("Return_url="+httpurl+"/baopay/Return_url&");
		url.append("Md5key="+keyValue+"&");
		String id = order.get("p1_MerId");
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
		timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
				15 * 60 * 1000);
		System.out.println(url.toString());
		redirect(url.toString());
	}
	
	/**
     * 宝付快捷接口
     * @param p1_MerId
     * @param keyValue
     * @param order
     */
    public void baopaykuaijie(String p1_MerId, String keyValue, Order order){
        String PayID = "1080";
        String OrderMoney = (new BigDecimal((String) order.get("p3_Amt")).multiply(new BigDecimal("100"))).toString();
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
        Date createtime = order.get("CreateTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        url.append("TradeDate="+sdf.format(createtime)+"&");
        url.append("TransID="+order.get("orderid")+"&");
        url.append("OrderMoney="+OrderMoney+"&");
        url.append("ProductName="+formatString(order.getStr("p5_Pid"))+"&");
        url.append("AdditionalInfo="+formatString(order.getStr("pa_MP"))+"&");
        url.append("Merchant_url="+httpurl+"/baopay/Merchant_url&");
        url.append("Return_url="+httpurl+"/baopay/Return_url&");
        url.append("Md5key="+keyValue+"&");
        String id = order.get("p1_MerId");
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
        timer.schedule(new yeepayTimer(order.getStr("orderid"), timer),
                15 * 60 * 1000);
        System.out.println(url.toString());
        redirect(url.toString());
    }
	
	
	
	
	
	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
}
