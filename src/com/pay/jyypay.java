package com.pay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.servlet.ServletException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Element;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;

import com.apppay110.config.SAMConfig;
import com.apppay111.config.YHHConfig;
import com.jfinal.core.Controller;

import com.pay.cib.client.service.CibService;
import com.pay.cib.client.service.WftRateSave;
import com.pay.cib.server.DigestUtil;
import com.pay.pinan.HttpUtil;
import com.pay.pinan.HttpsUtil;
import com.pay.pinan.TLinx2Util;
import com.pay.pinan.TLinxAESCoder;
import com.pay.pinan.TestParams;
import com.pay.zhaoshang.BoingPay;
import com.payplus.api.security.AESUtil;
import com.payplus.api.security.Digest;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.swiftpass.util.MD5;
import com.swiftpass.util.SignUtils;
import com.swiftpass.util.XmlUtils;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.vo.AccountList;
import com.vo.Asynchronous;
import com.vo.Gateway;
import com.vo.Order;
import com.vo.Payment;
import com.vo.PersonGateway;
import com.yeepay.paymobile.utils.PaymobileUtils;
import com.zzzhifu.HttpUtils;
import com.zzzhifu.IAppPaySDKConfig;
import com.zzzhifu.OrderIapay;
import com.zzzhifu.SignUtilsAIB;
import com.zzzhifu.sign.SignHelper;

import com.zzzhifu.xiaoxie.util.Md5Util;
import com.zzzhifu.xiaoxie.util.SignUtil;

public class jyypay extends Controller {

	public void getway() throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//String userAgent = getRequest().getHeader("user-agent");
		//String systemType = "";
		//if(userAgent.toLowerCase().contains("android")){
		//	systemType = "android";
		//}else{
		//	systemType="ios";
		//}
		//System.out.println("获取到的手机类型是1=："+systemType);
		
		String p0_Cmd = getPara("p0_Cmd");		//业务类型
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));		//交易币种
		String p5_Pid = formatString(getPara("p5_Pid"));    	//商品名称
		String p6_Pcat =formatString(getPara("p6_Pcat"));	  	//商品种类
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));	//商品描述
		String p8_Url = getPara("p8_Url");    	//返回地址
		String p9_SAF = formatString(getPara("p9_SAF"));    	//送货地址
		String pa_MP = formatString(getPara("pa_MP"));		//商品拓展信息
		String pd_FrpId = getPara("pd_FrpId");  //支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//应答机制,系统默认为1,需要应答      
		String hmac = getPara("hmac");
		if(pr_NeedResponse.equalsIgnoreCase("")){
			pr_NeedResponse="1";
		}
		setAttr("p0_Cmd",p0_Cmd);
		setAttr("p1_MerId",p1_MerId);
		setAttr("p2_Order",p2_Order);
		setAttr("p3_Amt",p3_Amt);
		setAttr("p4_Cur",p4_Cur);
		setAttr("p5_Pid",p5_Pid);
		setAttr("p6_Pcat",p6_Pcat);
		setAttr("p7_Pdesc",p7_Pdesc);
		setAttr("p8_Url",p8_Url);
		setAttr("p9_SAF",p9_SAF);
		setAttr("pa_MP",pa_MP);
		setAttr("pd_FrpId",pd_FrpId);
		setAttr("pr_NeedResponse",pr_NeedResponse);
		//setAttr("systemType",systemType);
		setAttr(hmac,hmac);
		//Date d=new Date();   
		//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		//System.out.println("当前的时间是："+df.format(d));   
		//System.out.println("30分钟后的时间：" + df.format(new Date(d.getTime() - 30 * 60 * 1000)));  					
		//boolean boo = false;
		//List<Order> order = Order.dao.find("select p.p2_Order from order p where p.p2_Order = '"+p2_Order+"'");
		//if(order.size()>0){
		//boo = true;
		//}
		if(!formatString(p1_MerId).equals("")){
			PersonGateway perGateway = PersonGateway.dao.findById(p1_MerId);
			int gateway_id = perGateway.getInt("gateway_id");
			String title = formatString(perGateway.getStr("title"));	
			String user_gatepay_id="";				
			boolean flag = false;//判断商户通道类型是否已经设置
			if(!title.equals("")){
				String[] ts = title.split(";");
				for(int i=0;i<ts.length;i++){
					String[] temp = ts[i].split("-");
					if(temp.length<2){
						renderHtml("错误代码1001：商户未开通此类型通道："+pd_FrpId);
					}else{
						String u_gatepay_id = temp[0];
						String u_pd_FrpId = temp[1];
						if(u_pd_FrpId.equals(pd_FrpId)){
							flag = true;
							user_gatepay_id = u_gatepay_id;	
							setAttr("pd_FrpId",u_pd_FrpId);
							System.out.println("user_gatepay_id==="+user_gatepay_id);
							break;
						}
					}
				}
			}else{
				renderHtml("错误代码1002：商户未授权："+pd_FrpId);
			}
			if(flag && !formatString(user_gatepay_id).equals("")){				
				int id = Integer.parseInt(user_gatepay_id);				
				setAttr("user_gateway_id",id);
				if(gateway_id==1 && (pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY"))){
					BigDecimal amt = new BigDecimal(p3_Amt);
					//BigDecimal sysamt = new BigDecimal("2000");
					int paygete_id5 = perGateway.getInt("paygete_id5");
					int paygete_id4 = perGateway.getInt("paygete_id4");
					int paygete_id2 = perGateway.getInt("paygete_id2");
					int paygete_id3 = perGateway.getInt("paygete_id3");	
					int paygete_id1 = perGateway.getInt("paygete_id1");
					
					BigDecimal sysamt1 = new BigDecimal(paygete_id1);
					BigDecimal sysamt2 = new BigDecimal(paygete_id2);
					BigDecimal sysamt3 = new BigDecimal(paygete_id3);
					BigDecimal sysamt4 = new BigDecimal(paygete_id4);
					BigDecimal sysamt5 = new BigDecimal(paygete_id5);
					if(amt.compareTo(sysamt2)>=0 && amt.compareTo(sysamt3)<=0){	
						getway_duobao(115);												
					}else if(amt.compareTo(sysamt4)>=0 && amt.compareTo(sysamt5)<=0){
						getway_tongle(127);
					}else{
						//getway_duobao(115);
						getway_duobao2(130);
					}
				}else if(gateway_id==2 && (pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP"))){
					BigDecimal amt = new BigDecimal(p3_Amt);
					int paygete_id5 = perGateway.getInt("paygete_id5");
					int paygete_id4 = perGateway.getInt("paygete_id4");
					int paygete_id2 = perGateway.getInt("paygete_id2");
					int paygete_id3 = perGateway.getInt("paygete_id3");	
					int paygete_id1 = perGateway.getInt("paygete_id1");
					
					BigDecimal sysamt1 = new BigDecimal(paygete_id1);
					BigDecimal sysamt2 = new BigDecimal(paygete_id2);
					BigDecimal sysamt3 = new BigDecimal(paygete_id3);
					BigDecimal sysamt4 = new BigDecimal(paygete_id4);
					BigDecimal sysamt5 = new BigDecimal(paygete_id5);
					if(amt.compareTo(sysamt2)>=0 && amt.compareTo(sysamt3)<=0){
						getway_swiftpass_model(2014);//耗子1		
						//getway_swiftpass2(23);	//墨汀										
					}else if(amt.compareTo(sysamt4)>=0 && amt.compareTo(sysamt5)<=0){
						getway_swiftpass_model(2012);  //耗子2
						//getway_swiftpass_20(20);//耗子1											
					}else if(sysamt1.compareTo(sysamt5)>0){	
						getway_swiftpass_model(2012);	//墨汀		
					}else{						
						getway_swiftpass_model(2012);	//肥猪
						}											
				}else{					
					if(id==19){
						getway_ahpay(id);
					}else if(id==20){
						System.out.println("到20-耗子1-网关走去");
						getway_swiftpass_20(id);
					}else if(id==21){
						System.out.println("到21-耗子2-网关走去");
						getway_swiftpass_21(id);
					}else if(id==24){
						System.out.println("到24-肥猪-网关走去");
						getway_swiftpass(id);
					}else if(id==25){
						System.out.println("到25-肥猪-网关走去");
						getway_swiftpass25(id);	
					}else if(id==26){
						System.out.println("到26-肥猪-网关走去");
						getway_swiftpass26(id);	
					}else if(id==23){
						System.out.println("到23-墨汀-网关走去");
						getway_swiftpass2(id);						
					}else if(id==102){
						System.out.println("到102网关走去");
						//getway_game68qb(id);
						getway_shanhai(id);
						//smAliipay(id);					
					}else if(id==106){
						System.out.println("到106网关走去");
						getway_huifubao(id);
					}else if(id==110){
						System.out.println("到桥支付110网关走去");
						//getway_sam(id);	
						getway_qiaozhifu(id);
					}else if(id==111){
						System.out.println("到111环球网关走去");
						getway_huanqiu(id);
						//getway_yhh(id);						
					}else if(id==112){
						System.out.println("到112中信网关走去");
						getway_must(id);
					}else if(id==113){
						System.out.println("到113微信公众号支付");
						getway_wxGZH(id);					
					}else if(id==114){
						System.out.println("到114爱贝支付");
						getway_aibei(id);
					}else if(id==115){
						System.out.println("到115多宝1支付");
						getway_duobao(id);
					}else if(id==116){
						System.out.println("到116芯火支付");
						getway_mobao2(id);
					}else if(id==117){
						System.out.println("到117支付");
						getway_bianwa(id);
					}else if(id==118){
						System.out.println("到118聚合支付");
						getway_juhe(id);						
					}else if(id==119){
						System.out.println("到119中铁支付");
						getway_ms(id);
					}else if(id==120){
							System.out.println("到120捕鱼专线支付");
							//getway_cai(id);
							getway_SamBuyu(id);
					}else if(id==121){
						System.out.println("到121微信公众号支付");
						getway_gongzhonghao(id);
					}else if(id==122){
						System.out.println("到122阿三支付");
						//gateway_sampay(id);
						gateway_sampay2(id);
					}else if(id==123){
						System.out.println("到123橡胶支付");
						getway_xjpay(id);
					}else if(id==124){
						System.out.println("到124明天动力支付");
						//getway_mtdongli(id);
						getway_mtdongli2(id);
					}else if(id==125){
						System.out.println("到125国连四方BBNPAY支付");
						getway_bbnpay(id);
					}else if(id==126){
						System.out.println("到126盛付通支付");
						getway_shft(id);//盛付通
					}else if(id==127){
						System.out.println("到127同乐支付");
						getway_tongle(id);//盛付通
					}else if(id==128){
						System.out.println("到128兆行聚合支付");
						getway_zhaohang(id);//盛付通
					}else if(id==129){
						System.out.println("到129--SAND支付");
						getway_sandRedirect();
						//getway_sand();//小谢微信
					}else if(id==130){
						System.out.println("到130创世伟业-李枫支付");
						//getway_duobao2(id);
						getway_sand2(id);
					}else if(id==131){
						System.out.println("到131平安银行支付");
						getway_pingan(id);
					}else if(id==132){
						System.out.println("到132--真扬--支付");
						getway_zhengyang(id);
					}else if(id==133){
						System.out.println("到133--金游--支付");
						getway_jinyou(id);
					}else if(id==134){
						System.out.println("到134--捕鱼--支付");
						gateway_buyu(id);
					}else if(id==135){
							System.out.println("到135--麻袋--支付");
							gateway_yimadai(id);
					}else if(id==136){
						System.out.println("到136--光大--支付");
						getway_swiftpass4(id);
					}else if(id==137){
						System.out.println("到137--光大2--支付");
						getway_swiftpass5(id);
					}else if(id==138){
						System.out.println("到138--兴业银行--支付");
						getway_swiftpass6(id);
					}else if(id==139){
						System.out.println("到139--耗子3--支付");
						getway_swiftpass7(id);
					}else if(id==140){
						System.out.println("到140--光大3--支付");
						getway_swiftpass8(id);
					}else if(id==141){
						System.out.println("到141--宝希商务--支付");
						getway_baoxishangwu(id);
					}else if(id==142){
						System.out.println("到142--好付--支付");
						getway_haofu();
					}else if(id==143){
						System.out.println("到143--好付2--支付");
						getway_haofu2();
					}else if(id==144){
						System.out.println("到144--好付3--支付");
						getway_haofu3();
					}else if(id==145){
						System.out.println("到145--好付4--支付");
						getway_haofu4();
					}else if(id==146){
						System.out.println("到146--好付5--支付");
						getway_haofu5();
					}else if(id==147){
						System.out.println("到147--测试--支付");
						getway_hjy(147);
					}else if(id==150){
						System.out.println("到150--芯活--支付");
						getway_xinhuo(150);
					}else if(id==151){
						System.out.println("到151--友付1--支付");
						getway_youfu1(151);
					}else if(id==152){
						System.out.println("到152--中铁2--支付");
						getway_zhongtie2(152);
					}else if(id==153){
						System.out.println("到153--好付6--支付");
						getway_haofu6();
					}else if(id==154){
						System.out.println("到154--易宝--支付");
						getway_yeepay(id);
					}else if(id==155){
						System.out.println("到155--建设银行--支付");
						getway_jiansheyh1(id);
					}else if(id==156){
						System.out.println("到156--中铁3--支付");
						getway_zhongtie3(id);
					}else if(id>=2000 && id<=4000){
						System.out.println("到2000-4000-qq支付--兴业银行--支付");
						getway_swiftpass_model(id);
					}else if(id>=4001 && id<=5000){
						System.out.println("到4000-5000-招商--支付");
						getway_zhaoshang_model(id);
					}else{
						renderText("通道未授权！");
						System.out.println("通道未设置，无法进行！");
					}
				}
			}else{
				setAttr("user_gateway_id",gateway_id);
				renderText("通道未授权！");
			}			
		}
	}
	
	public void getway_yeepay(int id) throws IOException {		
		try{
			//UTF-8编码
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/html");
			PrintWriter out	= this.getResponse().getWriter();	
										
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "YEEALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "YEEWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "YEEWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
	          
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            BigDecimal amt = amtbig.multiply(amtbig1).setScale(0);	            

	    		String productcatalog       = "1";				//支付类别;
	    		String productname          = "手游-虚拟币充值";
	    		String identityid           = String.valueOf(new Date().getTime());//用户标识
	    		String userip               = user_ip;
	    		//String paytool				= "";			//扫码必填，否则不用填
	    		String terminalid           = "IOS";			//用户终端
	    		//String userua               = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";//终端设备 UA
	    		String productdesc          = "不到账?请联系客服";
	    		String callbackurl          = notify_url;
	    		String time = String.valueOf(date.getTime()).substring(0,10);
	    		System.out.println("time=:"+time);
	    		
	    		int transtime				= Integer.parseInt(time); 
	    		int amount              	= Integer.parseInt(amt.toString()); 
	    		int identitytype        	= 4; //用户标识码，4：用户手机号码
	    		int terminaltype        	= 0; //终端类型0、IMEI；1、MAC；2、UUID；3、OTHER
	    		int directpaytype		    = 0;
	    		int orderexpdate        	= 24*60; 
	    		int currency	        	= 156; 
	    		int version		        	= 0;

	    		//使用TreeMap
	    		TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
	    		treeMap.put("orderid", 			orderid);
	    		treeMap.put("productcatalog", 	productcatalog);
	    		treeMap.put("productname", 		productname);
	    		treeMap.put("identityid", 		identityid);
	    		treeMap.put("userip", 			userip);
	    		treeMap.put("directpaytype", 	directpaytype);
	    		treeMap.put("terminalid", 		terminalid);
	    		//treeMap.put("userua", 			userua);
	    		treeMap.put("transtime", 		transtime);
	    		treeMap.put("amount", 			amount);
	    		treeMap.put("identitytype", 	identitytype);
	    		treeMap.put("terminaltype", 	terminaltype);
	    		treeMap.put("productdesc", 		productdesc);
	    		treeMap.put("callbackurl", 		callbackurl);
	    		treeMap.put("currency", 		currency);
	    		treeMap.put("orderexpdate", 	orderexpdate);
	    		treeMap.put("version", 			version);

	    		System.out.println(treeMap);
	    		
	    		//第一步 生成AESkey及encryptkey
	    		String AESKey		= PaymobileUtils.buildAESKey();
	    		String encryptkey	= PaymobileUtils.buildEncyptkey(AESKey);

	    		//第二步 生成data
	    		String data			= PaymobileUtils.buildData(treeMap, AESKey);

	    		//第三步 获取商户编号及请求地址，并组装支付链接
	    		String merchantaccount	= PaymobileUtils.getMerchantaccount();
	    		String url				= PaymobileUtils.getRequestUrl(PaymobileUtils.PAYAPI_NAME);
	    		TreeMap<String, String> responseMap	= PaymobileUtils.httpPost(url, merchantaccount, data, encryptkey);
	    		//System.out.println("url=" + url);
	    		//System.out.println("merchantaccount=" + merchantaccount);
	    		//System.out.println("data=" + data);
	    		//System.out.println("encryptkey=" + encryptkey);
	    		//System.out.println("第四步 判断请求是否成功!");
	    		//第四步 判断请求是否成功
	    		if(responseMap.containsKey("error_code")) {
	    			out.println(responseMap);
	    			return;
	    		}
	    		
	    		//第五步 请求成功，则获取data、encryptkey，并将其解密
	    		String data_response						= responseMap.get("data");
	    		String encryptkey_response					= responseMap.get("encryptkey");
	    		TreeMap<String, String> responseDataMap	= PaymobileUtils.decrypt(data_response, encryptkey_response);

	    		//第六步 sign验签
	    		if(!PaymobileUtils.checkSign(responseDataMap)) {
	    			out.println("sign 验签失败！");
	    			out.println("<br><br>responseMap:" + responseDataMap);
	    			return;
	    		}
	    		System.out.println("第七步 判断请求是否成功:"+responseDataMap.toString());
	    		//第七步 判断请求是否成功
	    		if(responseDataMap.containsKey("error_code")) {
	    			out.println(responseDataMap);
	    			return;
	    		}
	    		//System.out.println("第八步 进行业务处理!");
	    		//第八步 进行业务处理
	    		String payurl = formatStr(responseDataMap.get("payurl"));
	    		redirect(payurl);
//	    		setAttr("responseDataMap", responseDataMap);
//	    		RequestDispatcher view	= getRequest().getRequestDispatcher("/WEB-INF/pay/must/41payApiResponse.jsp");
//	    		view.forward(getRequest(), getResponse());
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void callback_yeepay(){
		try {
			//UTF-8编码
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/html");
			PrintWriter out	= getResponse().getWriter();
			String data			= formatStr(getRequest().getParameter("data"));
			String encryptkey	= formatStr(getRequest().getParameter("encryptkey"));
			//解密data
			TreeMap<String, String>	dataMap	= PaymobileUtils.decrypt(data, encryptkey);
			System.out.println("返回的明文参数：" + dataMap);
			//sign验签
			if(!PaymobileUtils.checkSign(dataMap)) {
				out.println("sign 验签失败！");
				out.println("<br><br>dataMap:" + dataMap);
				return;
			}else{
			//回写SUCCESS
			//out.println("SUCCESS");
			//out.flush();
			//out.close(); 
			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;			
			//Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			//String key = gateway.getStr("gateway_key");
			int gateway_id = 154;
			String status = dataMap.get("status");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			if(status != null && "0".equals(status)){
				String r6_Order = dataMap.get("orderid");//订单号
				//String service = dataMap.get("trade_type");//交易类型
				String r2_TrxId= dataMap.get("yborderid");//威富通交易号
				String ro_BankOrderId = r2_TrxId;//第三方订单号
				String rp_PayDate = sdf.format(new Date());//支付完成时间
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				String r3_Amt = dataMap.get("amount");
				//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
				BigDecimal amt = new BigDecimal(r3_Amt);
				BigDecimal yb = new BigDecimal(100);
				amt = amt.divide(yb);																				
				//查询订单列表中对应的订单号状态  
				String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
				List<Order> bb = Order.dao.find(sql.toString());
				int len = bb.size();
				Order o = bb.get(0);        
				String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
				int  r1_Code1=o.getInt("r1_Code");        //支付结果         
				String UserCallback = o.get("p8_Url");    //取得用户的回传地址
				String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
				int user_merId = o.getInt("p1_MerId");
				String user_key = Payment.dao.findById(user_merId).getStr("key");
				//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
				//System.out.println("用户回调地址：UserCallback=："+UserCallback);

				String r0_Cmd="Buy";
				int r1_Code=1;
				String r4_Cur="CNY";
				String r5_Pid="154";
				String r7_Uid="BUY";
				String r8_MP="charge";

				String  ssb= "";
				ssb += user_merId;
				ssb += r0_Cmd;
				ssb += r1_Code;
				ssb += r2_TrxId;
				ssb += amt;
				ssb += r4_Cur;
				ssb += r5_Pid;
				ssb += user_r6_order;
				ssb += r7_Uid;//商品描述
				ssb += r8_MP;
				ssb += "1";        
				ssb += rp_PayDate;
				String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
				//给客户的回调URL，带参数
				String result = "";
				result += UserCallback;
				result += "?r0_Cmd=" + r0_Cmd;
				result += "&r1_Code=" + r1_Code;
				result += "&r2_TrxId=" + r2_TrxId;
				result += "&r3_Amt=" + amt;
				result += "&r4_Cur=" + r4_Cur;
				result += "&r5_Pid=" + r5_Pid;
				result += "&r6_Order=" + user_r6_order;
				result += "&r7_Uid=" + r7_Uid;
				result += "&r8_MP=" + r8_MP;
				result += "&r9_BType=1";
				result += "&rp_PayDate=" +rp_PayDate;
				result += "&hmac=" + rehmac;

				//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				//System.out.println("充值成功回调给客户的URL=："+result);
				CibService cibservice = new CibService(); 
				if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
					cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
				}	
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
					Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
					if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
					}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
					}
				}else{
					System.out.println(r6_Order+"同步通知成功");
					renderText("SUCCESS");
				}	
			}else{
				renderText("交易失败！"); 
			}
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}
	
	public void getway_jyy(int gateway_id) throws IOException {	
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();										

		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名     

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户)       

		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String sys_callback = gateway.getStr("notify_url");
		String PostUrl = gateway.getStr("req_url");

		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");		
		String orderid = "JYY" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;//平台订单号
		if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			orderid = "JYYWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
		}else if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			orderid = "JYYALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
		}else{
			System.out.println("通道编码错误");
		}
		Date date = new Date();		

		String sign="";        
		sign += p0_Cmd;
		sign += gateway_merid;
		sign += orderid;
		sign += p3_Amt;
		sign += p4_Cur;
		sign += p5_Pid;
		sign += p6_Pcat;
		sign += p7_Pdesc;
		sign += sys_callback;
		sign += p9_SAF;
		sign += pa_MP;
		sign += pd_FrpId;
		sign += pr_NeedResponse;  
		String sys_hmac = DigestUtil.hmacSign(sign, gateway_key); //生成数据签名(平台)

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId", p1_MerId);
		order.set("p2_Order", p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", sys_hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();

		String result = "";
		result += PostUrl;
		result += "?p0_Cmd=" + p0_Cmd;
		result += "&p1_MerId=" + gateway_merid;
		result += "&p2_Order=" + orderid;
		result += "&p3_Amt=" + p3_Amt;
		result += "&p4_Cur=" + p4_Cur;
		result += "&p5_Pid=" + p5_Pid;
		result += "&p6_Pcat=" + p6_Pcat;
		result += "&p7_Pdesc=" + p7_Pdesc;
		result += "&p8_Url=" + sys_callback;
		result += "&p9_SAF=" + p9_SAF;
		result += "&pa_MP=" +pa_MP;
		result += "&pd_FrpId=" + pd_FrpId;
		result += "&pr_NeedResponse=" + pr_NeedResponse;
		result += "&hmac=" + sys_hmac;
		//response.sendRedirect(result);        

		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("平台提交的参数列表是==:"+result);
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			getResponse().sendRedirect(result);
		}else{
			renderText("签名失败");
		}
	}

	public void callback_jyypay() throws IOException{		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String r0_Cmd = getPara("r0_Cmd");
		String r1_Code = getPara("r1_Code");
		String r2_TrxId = getPara("r2_TrxId");	//平台流水号
		String r3_Amt = getPara("r3_Amt");		//支付金额
		String r4_Cur = getPara("r4_Cur");
		String r5_Pid = getPara("r5_Pid");
		String r6_Order =getPara("r6_Order");	//商户订单号
		String r7_Uid = getPara("r7_Uid");
		String r8_MP = getPara("r8_MP");
		String r9_BType = getPara("r9_BType");  //通知类型 1同步通知 2异步通知
		String rp_PayDate = getPara("rp_PayDate");
		String hmac = getPara("hmac");	  

		System.out.println("服务器返回给平台的数字签名是====："+hmac);
		System.out.println("银行返回来的商户订单号==========："+r6_Order);

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);

		int gateway_id = 41;
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");

		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("success");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);

			//根据返回数据加工计算客户的数字签名Hmac
			String sb = "";
			sb += user_merId;
			sb += r0_Cmd;
			sb += r1_Code;
			sb += r2_TrxId;
			sb += r3_Amt;
			sb += r4_Cur;
			sb += r5_Pid;
			sb += user_r6_order;
			sb += r7_Uid;//是否为银行订单号？？？
			sb += r8_MP;
			sb += r9_BType;        
			sb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(sb, user_key); //返回给客户的数据签名
			System.out.println("生成的客户数字签名字符串:"+sb);
			System.out.println("==返回时==生成的客户数字签名=："+rehmac);

			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=" + r9_BType;
			result += "&rp_PayDate="+ rp_PayDate;
			result += "&hmac=" + rehmac;        
			System.out.println("传递的URL=："+result);

			String sbOld = "";
			sbOld += mch_id;
			sbOld += r0_Cmd;
			sbOld += r1_Code;
			sbOld += r2_TrxId;
			sbOld += r3_Amt;
			sbOld += r4_Cur;
			sbOld += r5_Pid;
			sbOld += r6_Order;
			sbOld += r7_Uid;
			sbOld += r8_MP;
			sbOld += r9_BType;        
			sbOld += rp_PayDate;
			String nhmac = DigestUtil.hmacSign(sbOld, mch_key); //平台数据签名
			System.out.println("服务器返回给平台的数字签名拼接==："+sbOld);
			System.out.println("服务器返回给平台的数字签名是==："+hmac);
			System.out.println("游戏平台自己计算的数字签名是==："+nhmac);

			if (nhmac.equals( hmac)) {           	
				//支付成功,请处理自己的逻辑 请注意通知可能会多次 请避免重复到帐
				Date date2 = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String ru_Trxtime= sdf.format(date2);    //交易结果通知时间                     
				System.out.println("充值成功后更新数据库参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				CibService cibservice = new CibService();   
				if(cibservice.JyypayUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id)){						
					System.out.println("***充值成功，更新数据库成功***");
				}
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
					Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
					if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
					}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
					}
				}else{
					System.out.println(r6_Order+"异步通知成功");
					renderText("充值成功");
				}				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
		}
	}

	/*********************威富通********************************/
	public void getway_swiftpass(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "FZ1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "FZ1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.toLowerCase().equals("weixin")){//QQ钱包支付
				pd_FrpId = "pay.tenpay.wappay";
				orderid = "FZ1QQ" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				renderText("通道未授权");
				return;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家客服";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", r3_Amt.toString());
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏平台";
				String mch_app_id="http://netpay.fz222.com";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			if(pd_FrpId.equals("pay.tenpay.wappay")){
				sign = sign.toUpperCase();
			}
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else if(pd_FrpId.equals("pay.tenpay.wappay")){
						String sign_2 = resultMap.get("sign");
						resultMap.remove("sign");
			            StringBuilder buf = new StringBuilder((resultMap.size() +1) * 10);
			            SignUtils.buildPayParams(buf,resultMap,false);
			            String preStr = buf.toString();
			            String signRecieve = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			            			            
			            if(sign_2.equals(signRecieve)){
			            	if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
								String pay_info = formatString(resultMap.get("pay_info"));
								if(!pay_info.equals("")){										
									redirect(pay_info);								
								}else{
									renderText("网络繁忙，请稍后再试！");
								}									
							}else{
								renderText(formatString(resultMap.get("message")));
							}
			            }else{
			            	renderText("签名验证不通过");
			            }
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){										
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 24;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){			
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				}else{
					renderText("数字签名错误！"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*********************肥猪2********************************/
	public void getway_swiftpass25(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "FZ2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "FZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "FZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家客服";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", r3_Amt.toString());
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏平台";
				String mch_app_id="http://netpay.fz222.com";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){										
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass25(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 25;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){			
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				}else{
					renderText("数字签名错误！"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*********************肥猪2********************************/
	public void getway_swiftpass26(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "FZ2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "FZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "FZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家客服";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", r3_Amt.toString());
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏平台";
				String mch_app_id="http://netpay.fz222.com";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){										
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass26(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 26;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){			
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				}else{
					renderText("数字签名错误！"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*********************威富通20********************************/
	public void getway_swiftpass_20(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = formatString(getPara("hmac"));//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "HZ1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "HZ1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "HZ1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "QQ2353354300";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏币充值";
				String mch_app_id="http://vip1.swiftpasss.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			//mch_key+="[*****]";
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",sdf2.format(date));
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			//String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										System.out.println("提交的URL地址激活=："+pay_info);
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_swiftpass_20(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				System.out.println("err:通道传递参数错误"); 
			}  			
			//System.out.println(rec_xml);
			if(formatString(rec_xml).equals("")){
				System.out.println("err:未获得通道参数"); 
			}

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 20;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				System.out.println("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = formatString(map.get("out_trade_no"));//订单号
						String service = formatString(map.get("trade_type"));//交易类型
						String r2_TrxId= formatString(map.get("transaction_id"));//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = formatString(map.get("time_end"));//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						
						String user_key = Payment.dao.findById(user_merId).getStr("key");				
						
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid= String.valueOf(gateway_id);//新增
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						boolean flag=false;
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							flag = cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						if(flag){
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					  }else{
						  System.out.println("更新数据库失败！"); 
					  }
					}
				} 
				}else{
				System.out.println("数字签名错误！"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*********************威富通21********************************/
	public void getway_swiftpass_21(int id) throws IOException {		
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = formatString(getPara("hmac"));//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "HZ2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "HZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "HZ2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "QQ2353354300";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏币充值";
				String mch_app_id="http://vip1.swiftpasss.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			//mch_key+="[*****]";
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",sdf2.format(date));
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			//String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}	

	public void callback_swiftpass_21(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				System.out.println("err:通道传递参数错误"); 
			}  			
			//System.out.println(rec_xml);
			if(formatString(rec_xml).equals("")){
				System.out.println("err:未获得通道参数"); 
			}

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 21;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				System.out.println("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						
						String user_key = Payment.dao.findById(user_merId).getStr("key");				
						
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid= String.valueOf(gateway_id);//新增
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						boolean flag=false;
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							flag = cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						if(flag){
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					  }else{
						  System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！"); 
					  }
					}
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}else{
				System.out.println("数字签名错误！"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//汇付宝
	public void getway_huifubao(int gateway_id) throws IOException {
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();				
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名     

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户)       

		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String sys_callback = gateway.getStr("notify_url");
		String PostUrl = gateway.getStr("req_url");

		String user_ip = CryptTool.getIpAddr(getRequest());

		Order order = new Order();
		int pay_type= 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderid = "hfb" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;//平台订单号
		if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pay_type=30;
			orderid = "hfbwx" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;		
		}else if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){			
			orderid = "hfbali" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			pay_type=22;
		}else{
			System.out.println("通道编码错误");
		}
		Date date = new Date();	
		String basePath = this.getRequest().getScheme()+"://"+this.getRequest().getServerName();
		String version="1";
		String agent_id = gateway_merid;//商户ID
		String agent_bill_id=orderid;
		String agent_bill_time = sd.format(date);
		//+支付类型pay_type
		String pay_amt = p3_Amt;
		String notify_url = sys_callback;
		String return_url = basePath+"/web/Result/";
		System.out.println("user_ip:"+user_ip);
		user_ip= user_ip.replace(".","_");

		String is_phone = "1";//手机支付
		String is_frame="0";			
		String goods_name = "有疑问，请联系商家客服";
		String remark = "charge";	
		String goods_num="1";
		String goods_note="charge";
		String meta_option="{'s':'WAP','n':'游戏道具','id':'http://www.fz222.com'}";
		meta_option = java.net.URLEncoder.encode(Base64.encode(meta_option.getBytes("gb2312"),Base64.BASE64DEFAULTLENGTH),"UTF-8");
		String sign="";        
		sign += "version="+version;
		sign += "&agent_id="+agent_id;
		sign += "&agent_bill_id="+agent_bill_id;
		sign += "&agent_bill_time="+agent_bill_time;
		sign += "&pay_type="+pay_type;
		sign += "&pay_amt="+pay_amt;		
		sign += "&notify_url="+notify_url;
		sign += "&return_url="+return_url;
		sign += "&user_ip="+user_ip;
		//sign += "&is_test=1";
		sign += "&key="+gateway_key;		
		String sys_hmac="";
		try {
			sys_hmac = MD5Utils.createMD5(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}//生成数据签名(平台)

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId", p1_MerId);
		order.set("p2_Order", p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", sys_hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();

		String result="";
		result = PostUrl+"?";
		result +="version="+version;
		result +="&is_phone="+is_phone;
		if(pay_type==30){			
			result +="&is_frame="+is_frame;
			result +="&meta_option="+meta_option;			
		}
		result +="&agent_id="+agent_id;
		result +="&agent_bill_id="+agent_bill_id;
		result +="&agent_bill_time="+agent_bill_time;
		result +="&pay_type="+pay_type;
		result +="&pay_amt="+pay_amt;
		result +="&notify_url="+notify_url;
		result +="&return_url="+return_url;
		result +="&user_ip="+user_ip;
		result +="&goods_name="+goods_name;
		result +="&goods_num="+goods_num;
		result +="&goods_note="+goods_note;
		result +="&remark="+remark;		 
		result +="&sign="+sys_hmac;		
		System.out.println("--------------------------------------------------");
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			getResponse().sendRedirect(result);			
		}else{
			System.out.println("客户签名失败");
			renderText("签名失败");
		}
	}

	public void callback_huifb() throws IOException{		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String r0_Cmd = "buy";	//业务类型
		String r1_Code = "";
		String r2_TrxId = "";	//平台流水号
		String r3_Amt = "";		//支付金额
		String r4_Cur = "CNY";	//币种
		String r5_Pid = "card";		//商品名称
		String r6_Order ="";	//商户订单号
		String r7_Uid = "27";	//服务端对应的商户号
		String r8_MP = "";		//商户拓展信息
		String r9_BType = "1";  //通知类型 1同步通知 2异步通知
		String rp_PayDate = "";

		r1_Code = getPara("result");
		r2_TrxId = getPara("jnet_bill_no");	//汇付宝交易号(订单号)

		//String p1_MerId = getPara("agent_id");		    //商户编号			
		r6_Order = getPara("agent_bill_id");			//平台订单号	
		String pay_type = getPara("pay_type");			//通道编码	
		r3_Amt = getPara("pay_amt");					//实际充值金额
		r8_MP = getPara("remark");				//商家数据包
		String hmac = getPara("sign");					//密匙

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");

		System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);

		int gateway_id = 106;
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");	
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");

		rp_PayDate = simple.format(new Date());
		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("ok");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
			String sbOld = "";
			sbOld += "result="+r1_Code;
			sbOld += "&agent_id="+mch_id;
			sbOld += "&jnet_bill_no="+r2_TrxId;
			sbOld += "&agent_bill_id="+r6_Order;
			sbOld += "&pay_type="+pay_type;
			sbOld += "&pay_amt="+r3_Amt;
			sbOld += "&remark="+r8_MP;
			sbOld += "&key="+mch_key;			
			String nhmac = ""; //平台数据签名
			try {
				nhmac = MD5Utils.createMD5(sbOld);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("服务器返回给平台的数字签名是==："+hmac);
			System.out.println("游戏平台自己计算的数字签名是==："+nhmac);

			//根据返回数据加工计算客户的数字签名Hmac
			String sb = "";
			sb += user_merId;
			sb += r0_Cmd;
			sb += r1_Code;
			sb += r2_TrxId;
			sb += r3_Amt;
			sb += r4_Cur;
			sb += r5_Pid;
			sb += user_r6_order;
			sb += r7_Uid;//是否为银行订单号？？？
			sb += r8_MP;
			sb += r9_BType;        
			sb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(sb, user_key); //返回给客户的数据签名
			System.out.println("生成的客户数字签名字符串:"+sb);
			System.out.println("==返回时==生成的客户数字签名=："+rehmac);

			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=" + r9_BType;
			result += "&rp_PayDate="+ rp_PayDate;
			result += "&hmac=" + rehmac;        
			System.out.println("传递的URL=："+result);			

			if (nhmac.equals( hmac)) {           	
				//支付成功,请处理自己的逻辑 请注意通知可能会多次 请避免重复到帐
				Date date2 = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String ru_Trxtime= sdf.format(date2);    //交易结果通知时间                     
				//System.out.println("充值成功后更新数据库参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				CibService cibservice = new CibService();   
				if(cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id)){						
					System.out.println("***充值成功，更新数据库成功***");
				}
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
					Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
					if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
					}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
					}
				}else{
					System.out.println(r6_Order+"异步通知成功");
					renderText("ok");
				}				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
		}
	}

	public void getway_sam(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="SAMALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="SAMWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("服务器通道未授权！");
		}
		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "CHARGE");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "CHARGE");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "CHARGE");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("a.gengyoufu.com客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");			
			String contact_us="Service("+gateway_id+") QQ:1904178861";
			String subject = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");//商品名称
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");//商品描述			

			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
			//调用RSA签名方式
			AlipayClient client = new DefaultAlipayClient(SAMConfig.URL, SAMConfig.APPID, SAMConfig.RSA_PRIVATE_KEY, SAMConfig.FORMAT, SAMConfig.CHARSET, SAMConfig.ALIPAY_PUBLIC_KEY,SAMConfig.SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			String timeout_express="2m";
			String product_code="QUICK_WAP_PAY";
			// 封装请求支付信息
			AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_fee);
			model.setBody(body);
			model.setTimeoutExpress(timeout_express);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			// 设置异步通知地址
			alipay_request.setNotifyUrl(SAMConfig.notify_url);
			// 设置同步地址
			alipay_request.setReturnUrl(SAMConfig.return_url);
			// form表单生产
			String form = "";
			try {
				// 调用SDK生成表单
				form = client.pageExecute(alipay_request).getBody();
				this.getResponse().setContentType("text/html;charset=" + SAMConfig.CHARSET); 
				this.getResponse().getWriter().write(form);//直接将完整的表单html输出到页面 
				this.getResponse().getWriter().flush(); 
				this.getResponse().getWriter().close();
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			renderText("签名失败");
		}
	}

	public void callback_samGe(){		
		int gateway_id=110;;
		try {
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");

			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = getRequest().getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			String out_trade_no = this.getRequest().getParameter("out_trade_no");//商户订单号			
			String trade_no = this.getRequest().getParameter("trade_no");// 支付宝交易号 

			//String pay_name = this.getRequest().getParameter("pay_name");// 支付方式名称
			String pay_time = this.getRequest().getParameter("gmt_payment");//付款时间
			String total_fee = this.getRequest().getParameter("total_amount");//订单金额

			String apps_id = this.getRequest().getParameter("app_id");
			String seller_id = this.getRequest().getParameter("seller_id");
			String trade_status = this.getRequest().getParameter("trade_status");			
			String sign = this.getRequest().getParameter("sign");			
			String sign_type = this.getRequest().getParameter("sign_type");

			System.out.println("pay_time=:"+pay_time+"---total_fee="+total_fee+"---apps_id="+apps_id+"--seller_id=:"+seller_id);
			System.out.println("sign_type="+sign_type);
			System.out.println("sign="+sign);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			//pay_time = sdf.format(date);
			String rpdate = sdf2.format(date);			
			boolean verify_result=false;
			try {
				verify_result = AlipaySignature.rsaCheckV1(params, SAMConfig.ALIPAY_PUBLIC_KEY, SAMConfig.CHARSET, "RSA2");
			} catch (AlipayApiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("verify_result=："+verify_result);
			if(verify_result){//验证成功	
				System.out.println("验证成功	");
				String r6_Order = out_trade_no;//平台订单号				
				String r2_TrxId= trade_no;//支付宝交易号
				//String ro_BankOrderId = trade_no;//第三方订单号
				String rp_PayDate = pay_time;//支付完成时间
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				String r3_Amt = total_fee;//分回的金额单位是分																	
				//查询订单列表中对应的订单号状态  
				String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.p3_Amt from orders o where o.orderid='"+r6_Order+"'";							
				List<Order> bb = Order.dao.find(sql.toString());
				int len = bb.size();
				Order o = bb.get(0);				
				if(len==1 && apps_id.equals(SAMConfig.APPID) && trade_status.equals("TRADE_SUCCESS") && seller_id.equals(SAMConfig.seller_id)){
					System.out.println("验证成功-确认成功！");					
					String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
					int  r1_Code1=o.getInt("r1_Code");        //支付结果         
					String UserCallback = o.get("p8_Url");    //取得用户的回传地址
					String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
					int user_merId = o.getInt("p1_MerId");
					String user_key = Payment.dao.findById(user_merId).getStr("key");
					System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
					System.out.println("用户回调地址：UserCallback=："+UserCallback);

					String r0_Cmd="Buy";
					int r1_Code=1;
					String r4_Cur="CNY";
					String r5_Pid="";
					String r7_Uid="72";
					String r8_MP="charge";
					if(UserCallback.equalsIgnoreCase("http://bb.zzzhifu.com/pay/callback_wenzi")){
						user_merId=28;
						user_key = Payment.dao.findById(user_merId).getStr("key");
					}
					String  ssb= "";
					ssb += user_merId;
					ssb += r0_Cmd;
					ssb += r1_Code;
					ssb += r2_TrxId;
					ssb += r3_Amt;
					ssb += r4_Cur;
					ssb += r5_Pid;
					ssb += user_r6_order;
					ssb += r7_Uid;//商品描述
					ssb += r8_MP;
					ssb += "1";        
					ssb += rpdate;
					String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
					System.out.println("返回给客户的数字签名字符串是=："+ssb);
					//给客户的回调URL，带参数
					String result = "";
					result += UserCallback;
					result += "?r0_Cmd=" + r0_Cmd;
					result += "&r1_Code=" + r1_Code;
					result += "&r2_TrxId=" + r2_TrxId;
					result += "&r3_Amt=" + r3_Amt;
					result += "&r4_Cur=" + r4_Cur;
					result += "&r5_Pid=" + r5_Pid;
					result += "&r6_Order=" + user_r6_order;
					result += "&r7_Uid=" + r7_Uid;
					result += "&r8_MP=" + r8_MP;
					result += "&r9_BType=1";
					result += "&rp_PayDate=" +rpdate;
					result += "&hmac=" + rehmac;

					System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
					System.out.println("充值成功回调给客户的URL=："+result);
					CibService cibservice = new CibService();
					if(r1_Code1==1){						
						renderText("success");
					}else{
						cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						try {
							hClient.executeMethod(post);
							returnStr = post.getResponseBodyAsString();
						} catch (HttpException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}					
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("success");
						}	
					}
				}else{
					renderText("fail");
				}
			}else{//验证失败
				renderText("fail");
			}
		} catch (UnsupportedEncodingException e) {
			renderText("fail");
		}
	}

	public void getway_yhh(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="YHHALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="YHHMWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("服务器通道未授权！");
		}
		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "CHARGE");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "CHARGE");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "CHARGE");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("a.gengyoufu.com客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");			
			String contact_us="Service("+gateway_id+") QQ:1904178861";
			String subject = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");//商品名称
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");//商品描述			

			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
			//调用RSA签名方式
			AlipayClient client = new DefaultAlipayClient(YHHConfig.URL, YHHConfig.APPID, YHHConfig.RSA_PRIVATE_KEY, YHHConfig.FORMAT, YHHConfig.CHARSET, YHHConfig.ALIPAY_PUBLIC_KEY,YHHConfig.SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			String timeout_express="2m";
			String product_code="QUICK_WAP_PAY";
			// 封装请求支付信息
			AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_fee);
			model.setBody(body);
			model.setTimeoutExpress(timeout_express);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			// 设置异步通知地址
			alipay_request.setNotifyUrl(YHHConfig.notify_url);
			// 设置同步地址
			alipay_request.setReturnUrl(YHHConfig.return_url);
			// form表单生产
			String form = "";
			try {
				// 调用SDK生成表单
				form = client.pageExecute(alipay_request).getBody();
				this.getResponse().setContentType("text/html;charset=" + YHHConfig.CHARSET); 
				this.getResponse().getWriter().write(form);//直接将完整的表单html输出到页面 
				this.getResponse().getWriter().flush(); 
				this.getResponse().getWriter().close();
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			renderText("签名失败");
		}
	}

	public void callback_yhh(){		
		int gateway_id=111;
		try {
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");

			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = getRequest().getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			String out_trade_no = this.getRequest().getParameter("out_trade_no");//商户订单号			
			String trade_no = this.getRequest().getParameter("trade_no");// 支付宝交易号 

			//String pay_name = this.getRequest().getParameter("pay_name");// 支付方式名称
			String pay_time = this.getRequest().getParameter("gmt_payment");//付款时间
			String total_fee = this.getRequest().getParameter("total_amount");//订单金额

			String apps_id = this.getRequest().getParameter("app_id");
			String seller_id = this.getRequest().getParameter("seller_id");
			String trade_status = this.getRequest().getParameter("trade_status");			
			String sign = this.getRequest().getParameter("sign");			
			String sign_type = this.getRequest().getParameter("sign_type");

			System.out.println("pay_time=:"+pay_time+"---total_fee="+total_fee+"---apps_id="+apps_id+"--seller_id=:"+seller_id);
			System.out.println("sign_type="+sign_type);
			System.out.println("sign="+sign);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			//pay_time = sdf.format(date);
			String rpdate = sdf2.format(date);			
			boolean verify_result=false;
			try {
				verify_result = AlipaySignature.rsaCheckV1(params, YHHConfig.ALIPAY_PUBLIC_KEY, YHHConfig.CHARSET, "RSA2");
			} catch (AlipayApiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("verify_result=："+verify_result);
			if(verify_result){//验证成功	
				System.out.println("验证成功	");
				String r6_Order = out_trade_no;//平台订单号				
				String r2_TrxId= trade_no;//支付宝交易号
				//String ro_BankOrderId = trade_no;//第三方订单号
				String rp_PayDate = pay_time;//支付完成时间
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				String r3_Amt = total_fee;//分回的金额单位是分																	
				//查询订单列表中对应的订单号状态  
				String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.p3_Amt from orders o where o.orderid='"+r6_Order+"'";							
				List<Order> bb = Order.dao.find(sql.toString());
				int len = bb.size();
				Order o = bb.get(0);				
				if(len==1 && apps_id.equals(YHHConfig.APPID) && trade_status.equals("TRADE_SUCCESS") && seller_id.equals(YHHConfig.seller_id)){
					System.out.println("验证成功-确认成功！");					
					String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
					int  r1_Code1=o.getInt("r1_Code");        //支付结果         
					String UserCallback = o.get("p8_Url");    //取得用户的回传地址
					String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
					int user_merId = o.getInt("p1_MerId");
					String user_key = Payment.dao.findById(user_merId).getStr("key");
					System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
					System.out.println("用户回调地址：UserCallback=："+UserCallback);

					String r0_Cmd="Buy";
					int r1_Code=1;
					String r4_Cur="CNY";
					String r5_Pid="";
					String r7_Uid="26";
					String r8_MP="charge";

					String  ssb= "";
					ssb += user_merId;
					ssb += r0_Cmd;
					ssb += r1_Code;
					ssb += r2_TrxId;
					ssb += r3_Amt;
					ssb += r4_Cur;
					ssb += r5_Pid;
					ssb += user_r6_order;
					ssb += r7_Uid;//商品描述
					ssb += r8_MP;
					ssb += "1";        
					ssb += rpdate;
					String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
					System.out.println("返回给客户的数字签名字符串是=："+ssb);
					//给客户的回调URL，带参数
					String result = "";
					result += UserCallback;
					result += "?r0_Cmd=" + r0_Cmd;
					result += "&r1_Code=" + r1_Code;
					result += "&r2_TrxId=" + r2_TrxId;
					result += "&r3_Amt=" + r3_Amt;
					result += "&r4_Cur=" + r4_Cur;
					result += "&r5_Pid=" + r5_Pid;
					result += "&r6_Order=" + user_r6_order;
					result += "&r7_Uid=" + r7_Uid;
					result += "&r8_MP=" + r8_MP;
					result += "&r9_BType=1";
					result += "&rp_PayDate=" +rpdate;
					result += "&hmac=" + rehmac;

					System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
					System.out.println("充值成功回调给客户的URL=："+result);
					CibService cibservice = new CibService();
					if(r1_Code1==1){						
						renderText("success");
					}else{
						cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						try {
							hClient.executeMethod(post);
							returnStr = post.getResponseBodyAsString();
						} catch (HttpException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}					
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("success");
						}	
					}
				}else{
					renderText("fail");
				}
			}else{//验证失败
				renderText("fail");
			}
		} catch (UnsupportedEncodingException e) {
			renderText("fail");
		}
	}

	public void getway_shanhai(int gateway_id) throws IOException {	
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();										

		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名     

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户)       

		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String sys_callback = gateway.getStr("notify_url");
		String PostUrl = gateway.getStr("req_url");

		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");		
		String orderid = "SHH" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;//平台订单号
		if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			orderid = "SHHWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
		}else if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			orderid = "SHHALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
		}else{
			System.out.println("通道编码错误");
		}
		Date date = new Date();	

		String sign="";        
		sign += "customerid="+gateway_merid;
		sign += "&sdcustomno="+orderid;
		sign += "&ordermoney="+p3_Amt;
		sign += "&cardno=52";
		sign += "&faceno=zap";
		sign += "&noticeurl="+sys_callback;
		sign += "&key="+gateway_key;
		System.out.println("组合字符串=："+sign);
		String sys_hmac = ""; //生成数据签名(平台)
		try {
			sys_hmac= MD5Utils.createMD5(sign).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("加密得到数字签名=："+sign);
		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId", p1_MerId);
		order.set("p2_Order", p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", sys_hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();

		String basePath = this.getRequest().getScheme()+"://"+this.getRequest().getServerName();
		String customerid = gateway_merid;
		String sdcustomno= orderid;
		String ordermoney= p3_Amt;
		String cardno = "52";
		String faceno = "zap";
		String noticeurl = sys_callback;
		String backurl= basePath+"/web/Result/";
		String endcustomer = "vs1188118";
		//String endip = "61.160.247.45";
		String endip = user_ip;
		String remarks = orderid;//商品名称
		String mark = "CardCharge";//商品描述
		String stype ="1";
		//String ZFtype="2";

		String result = "";
		result += PostUrl;
		result += "?customerid=" + customerid;
		result += "&sdcustomno=" + sdcustomno;
		result += "&ordermoney=" + ordermoney;
		result += "&cardno=" + cardno;
		result += "&faceno=" + faceno;
		result += "&noticeurl=" + noticeurl;
		result += "&backurl=" + backurl;
		result += "&endcustomer=" + endcustomer;
		result += "&endip=" + endip;
		result += "&remarks=" + remarks;
		result += "&mark=" +mark;
		result += "&stype=" + stype;
		//result += "&ZFtype=" + ZFtype;
		result += "&sign=" + sys_hmac;
		//response.sendRedirect(result);       
		//System.out.println("-----------------------------------------------------------------------------");
		//System.out.println("平台提交的参数列表是==:"+result);
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			getResponse().sendRedirect(result);
			//getResponse().sendRedirect(result);
//			HttpClient hClient = new HttpClient(); 
//			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
//			managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
//			managerParams.setSoTimeout(3011000);
//			PostMethod post = null;
//			post = new PostMethod(result);
//			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
//			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
//			String returnStr = "";
//			hClient.executeMethod(post);
//			returnStr = post.getResponseBodyAsString();
//			System.out.println("xxxxxxxx="+returnStr);
//			String url = XmlUtils.MyXMLvalue(returnStr,"url");
//			System.out.println("获取body中的内容："+url);
//			redirect(url);
		}else{
			renderText("签名失败");
		}
	}

	public void callback_shanhai() throws IOException{		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String state = getPara("state");
		String customerid = getPara("customerid");
		//String superid = getPara("superid");
		String sd51no = getPara("sd51no");
		String sdcustomno = getPara("sdcustomno");//订单号
		String ordermoney = getPara("ordermoney");
		//String cardno = getPara("cardno");
		String mark = getPara("mark");
		String sign = getPara("sign");
		String resign = getPara("resign");	
        String r6_Order = sdcustomno;
        if(state.equals("1")){       	       
		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);

		int gateway_id = 102;
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");
		
		String text="";        
		text += "customerid="+customerid;
		text += "&sd51no="+sd51no;
		text += "&sdcustomno="+sdcustomno;
		text += "&mark="+mark;
		text += "&key="+mch_key;
		System.out.println("组合字符串=："+text);
		String sys_hmac = ""; //生成数据签名(平台)
		String sys_regsign="";
		try {
			sys_hmac= MD5Utils.createMD5(text).toUpperCase();
			String regText = "";
			regText += "sign="+sys_hmac;
			regText += "&customerid="+customerid;
			regText += "&ordermoney="+ordermoney;
			regText += "&sd51no="+sd51no;
			regText += "&state="+state;			
			regText += "&key="+mch_key;
			sys_regsign=MD5Utils.createMD5(regText).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if(!sys_regsign.equals(resign)){
			renderText("不合法的数据提交");
		}else{
		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("success");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="";
			String r7_Uid="102";
			String r8_MP="charge";
			String r2_TrxId=sd51no;
			String r3_Amt=ordermoney;
			String rpdate = sdf.format(date);
			String ru_Trxtime = rpdate;
			String ro_BankOrderId=sd51no;//支付网关订单号

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rpdate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//System.out.println("返回给客户的数字签名字符串是=："+ssb);
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rpdate;
			result += "&hmac=" + rehmac;

			System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+ro_BankOrderId+"--rp_PayDate=:"+rpdate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
			//System.out.println("充值成功回调给客户的URL=："+result);
			CibService cibservice = new CibService();
			if(r1_Code1==1){						
				renderText("success");
			}else{
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);
				System.out.println("充值成功后更新数据库的状态："+flag);
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				try {
					hClient.executeMethod(post);
					returnStr = post.getResponseBodyAsString();
				} catch (HttpException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}					
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
					Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
					if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
					}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
					}
				}else{
					System.out.println(r6_Order+"同步通知成功");
					renderText("success");
				}	
			}
		}
	  }
        }else{
        	System.out.println("返回状态不正确:"+state);
        }
	}

	//环球畅想支付宝接口
	public void getway_huanqiu(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "ALIWAP";			
			///orderid="FZRALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
			orderid="HQALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "WXWAP";	
			orderid="HQWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		//Gateway gateway = Gateway.dao.findById(gateway_id);
		//String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		//String gateway_key = gateway.getStr("gateway_key");		

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");				
			try {
				// 发送 POST 请求
				BigDecimal rate = new BigDecimal("100");
				BigDecimal new_p3_Amt = new BigDecimal(p3_Amt);
				new_p3_Amt = new_p3_Amt.multiply(rate).setScale(0);

				String sql = "select * from account_list order by rec_date limit 1";
				AccountList accountlist = new AccountList();
				List<AccountList> list = accountlist.dao.find(sql);
				String account = list.get(0).get("account");
				String privatekey = list.get(0).get("privatekey");
				int id = list.get(0).get("id");
				String mch_key = "6846860684f05029abccc09a53cd66f1";
				System.out.println("读取的--------id值是=："+id);
				System.out.println("读取的---account值是=："+account);
				System.out.println("读取的---mch_key值是=："+mch_key);
				long rec_date = date.getTime();					
				AccountList acc_list = AccountList.dao.findById(id);
				acc_list.set("rec_date", rec_date);
				//System.out.println("保存的结果是=："+);
				acc_list.update();				

				/**参与验签的字段*/
				String sb="account="+account;		            
				sb+="&amount="+new_p3_Amt;
				sb+="&userid=146503";
				String sign = getKeyedDigest(sb, mch_key);

				System.out.println("签名连接字符串是："+sb);
				System.out.println("数字签名是："+sign);

				String result="http://extman.kefupay.cn/tradition/WeChatpayment_mobile.action?";
				result+="account="+account;
				result+="&amount="+new_p3_Amt;
				result+="&userid=146503";
				result+="&orderCode=tb_alipay";
				result+="&pay_number="+orderid;
				result+="&privatekey="+privatekey;
				result+="&notify_url=http://netpay.fz222.com/pay/callback_hq";
				result+="&sign="+sign;

				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				//Map ValidCard = JsonUtil.jsonToMap(sr);
				JSONObject js = JSONObject.parseObject(returnStr);
				String s= js.getString("QRcodeURL");
				System.out.println(s);	
				getResponse().sendRedirect(s);
			} catch (Exception e) {
				renderText("调用支付宝异常");
				e.printStackTrace();
			}			

		}else{
			renderText("签名失败");
		}
	}

	public void callback_hq() throws IOException{		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			String json="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					json +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(json);    
			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 111;
			//Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			//String key = gateway.getStr("gateway_key");
			//Map<String, String> map = new HashMap<String, String>();
			String[] strs = json.split("&");
			Map<String, String> map = new HashMap<String, String>();
			for(String s:strs){
				String[] ms = s.split("=");
				map.put(ms[0], ms[1]);
			}
			String hmac = map.get("sign");	
			String p3_Amt=map.get("amount");//单位：分

			String sb="";		            
			sb="amount="+p3_Amt;			
			sb+="&orderId="+map.get("orderId");	
			sb+="&pay_number="+map.get("pay_number");
			System.out.println(sb);
			String nhmac = getKeyedDigest(sb, "6846860684f05029abccc09a53cd66f1");

			System.out.println("通道方传递回的数字签名sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String result_code = map.get("respCode");
				if(result_code != null && "000000".equals(result_code)){	
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");								
					Date date = new Date();	
					String r6_Order = map.get("pay_number");//订单号
					//String service = map.get("trade_type");//交易类型
					String r2_TrxId= map.get("orderId");//威富通交易号
					//String ro_BankOrderId = formatString(map.get("orderId"));//第三方订单号
					String rp_PayDate = sdf.format(date);//支付完成时间
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					String r3_Amt = p3_Amt;
					//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
					BigDecimal amt = new BigDecimal(r3_Amt);
					BigDecimal yb = new BigDecimal(100);
					amt = amt.divide(yb);																				
					//查询订单列表中对应的订单号状态  
					String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
					List<Order> bb = Order.dao.find(sql.toString());
					int len = bb.size();
					Order o = bb.get(0);        
					String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
					int  r1_Code1=o.getInt("r1_Code");        //支付结果         
					String UserCallback = o.get("p8_Url");    //取得用户的回传地址
					String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
					int user_merId = o.getInt("p1_MerId");
					String user_key = Payment.dao.findById(user_merId).getStr("key");
					//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
					System.out.println("用户回调地址：UserCallback=："+UserCallback);

					String r0_Cmd="Buy";
					int r1_Code=1;
					String r4_Cur="CNY";
					String r5_Pid="";
					String r7_Uid=String.valueOf(user_merId);
					String r8_MP="charge";
					if("http://pay.zzzhifu.com/pay/callback_server".equals(UserCallback)){
						r7_Uid = "71";
					}
					String  ssb= "";
					ssb += user_merId;
					ssb += r0_Cmd;
					ssb += r1_Code;
					ssb += r2_TrxId;
					ssb += amt;
					ssb += r4_Cur;
					ssb += r5_Pid;
					ssb += user_r6_order;
					ssb += r7_Uid;//商品描述
					ssb += r8_MP;
					ssb += "1";        
					ssb += rp_PayDate;
					String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
					//给客户的回调URL，带参数
					String result = "";
					result += UserCallback;
					result += "?r0_Cmd=" + r0_Cmd;
					result += "&r1_Code=" + r1_Code;
					result += "&r2_TrxId=" + r2_TrxId;
					result += "&r3_Amt=" + amt;
					result += "&r4_Cur=" + r4_Cur;
					result += "&r5_Pid=" + r5_Pid;
					result += "&r6_Order=" + user_r6_order;
					result += "&r7_Uid=" + r7_Uid;
					result += "&r8_MP=" + r8_MP;
					result += "&r9_BType=1";
					result += "&rp_PayDate=" +rp_PayDate;
					result += "&hmac=" + rehmac;

					System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
					System.out.println("充值成功回调给客户的URL=："+result);
					CibService cibservice = new CibService(); 
					if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date2 = new Date();
						ru_Trxtime = sdf2.format(date2);
						cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					}	
					HttpClient hClient = new HttpClient(); 
					HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
					managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
					managerParams.setSoTimeout(3011000);
					PostMethod post = null;
					post = new PostMethod(result);
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
					post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
					String returnStr = "";
					hClient.executeMethod(post);
					returnStr = post.getResponseBodyAsString();
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
							asynchronous = new Asynchronous();
							asynchronous.set("orderid", r6_Order);
							asynchronous.set("status", "N");
							asynchronous.set("url", result);
							asynchronous.save();
						}else{
							asynchronous.set("url",result);
							asynchronous.set("status", "N");
							asynchronous.update();
						}
					}else{
						System.out.println(r6_Order+"同步通知成功");
						renderText("SUCCESS");
					}																								
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getway_must(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "ALIWAP";			
			orderid="ZHXALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "WXWAP";			
			orderid="ZHXWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="Service"+gateway_id+"qq1904178861";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			map.put("method", "mbupay.alipay.wap");
			map.put("version", "2.0.1");
			//map.put("charset", "utf-8");
			map.put("appid", "ca2017051910000034");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id","cm2017051910000034");//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			String mch_key="67ef8a7849c3b9456b80b2a46d1ea6fd";//2f34fbf27de6d56ac0a09a4af0d6953b
			System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			//System.out.println("preStr:"+preStr);
			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");

				res = XmlUtils.toXml(resultMap);
				//System.out.println("中信银行服务器返回数据1："+res);
				System.out.println("中信银行服务器返回数据2："+resultMap);
				String return_code = resultMap.get("return_code");
				resultMap.remove("return_msg");
				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
								System.out.println(prepayId);
								setAttr("out_trade_no",out_trade_no);
								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
								setAttr("body",body);
								setAttr("prepayId", prepayId);
								renderJsp("/WEB-INF/pay/must/zxPayType.jsp");									
							}else{
								renderText("服务器链接失败");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			renderText("签名失败");
		}
	}

	public void must_callback(){		
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 112;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("return_code");
				if(status != null && "SUCCESS".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("method");//交易类型
						String r2_TrxId= map.get("pass_trade_no");//威富通交易号
						String ro_BankOrderId = map.get("transaction_id");//支付宝订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="112";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}																								
					} 
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getway_mobao(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="MOBALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="MOBWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="Service"+gateway_id+"qq1904178861";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			map.put("method", "mbupay.alipay.wap");
			map.put("version", "2.0.1");
			//map.put("charset", "utf-8");
			map.put("appid", "a20170627000013931");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id","m20170627000013931");//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			String mch_key="16a19d4ff15b20f458e5d0bdaa526e25";//2f34fbf27de6d56ac0a09a4af0d6953b
			System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			//System.out.println("preStr:"+preStr);
			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");

				res = XmlUtils.toXml(resultMap);
				//System.out.println("中信银行服务器返回数据1："+res);
				System.out.println("中信银行服务器返回数据2："+resultMap);
				String return_code = resultMap.get("return_code");
				resultMap.remove("return_msg");
				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
								System.out.println(prepayId);
								setAttr("out_trade_no",out_trade_no);
								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
								setAttr("body",body);
								setAttr("prepayId", prepayId);
								renderJsp("/WEB-INF/pay/must/zxPayType.jsp");									
							}else{
								renderText("服务器链接失败");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			renderText("签名失败");
		}
	}

	public void mobao_callback(){		
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 115;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("return_code");
				if(status != null && "SUCCESS".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("method");//交易类型
						String r2_TrxId= map.get("pass_trade_no");//威富通交易号
						String ro_BankOrderId = map.get("transaction_id");//支付宝订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="115";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}																								
					} 
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getway_mobao2(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="YSHALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="YSHWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		//String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		//String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="G"+gateway_id+"qq2491893265";
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();

			if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
				map.put("method", "mbupay.wxpay.jswap2");
			}else{
				map.put("method", "mbupay.alipay.wap");
			}
			map.put("version", "2.0.0");
			//map.put("charset", "utf-8");
			map.put("appid", "ca2017110610000320");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id","cm2017110610000320");//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			String mch_key="a80f2b35b3f31c3b8759ac70e81732fb";//f5029029907c04b812d97ff28709a508
			//System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);

			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			System.out.println("preStr:"+preStr);

			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			//System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			//System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			//System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");

				res = XmlUtils.toXml(resultMap);
				//System.out.println("中信银行服务器返回数据1："+res);
				//System.out.println("中信银行服务器--银盛返回数据："+resultMap);
				String return_code = resultMap.get("return_code");

				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						//Map<String,String> new_map = com.mustpay.util.SignUtils.paraFilter(resultMap);//过滤掉空值和map
						resultMap.remove("device_info");
						resultMap.remove("err_code");
						resultMap.remove("err_code_des");
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
								System.out.println(prepayId);
								setAttr("out_trade_no",out_trade_no);
								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
								setAttr("body",body);
								setAttr("prepayId", prepayId);
								renderJsp("/WEB-INF/pay/must/zxPayType.jsp");									
							}else{
								renderText("服务器链接失败");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			renderText("签名失败");
		}
	}

	public void mobao2_callback(){		
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  

			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 116;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("return_code");
				if(status != null && "SUCCESS".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("method");//交易类型
						String r2_TrxId= map.get("pass_trade_id>");//威富通交易号
						String ro_BankOrderId = map.get("transaction_id");//支付宝订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="116";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}																								
					} 
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_gongzhonghao(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="GZHALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="GZHWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		//String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		//String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean f = order.save();
		if(f){
		 if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="GH("+gateway_id+")qq2491893265";
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			map.put("method", "mbupay.wxpay.jswap2");
			map.put("version", "2.0.1");
			//map.put("charset", "utf-8");
			map.put("appid", "ca2017072310000106");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id","cm2017072310000106");//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			String mch_key="20e0b130351967640a7f9e66a28cb170";//f5029029907c04b812d97ff28709a508
			//System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);

			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			System.out.println("preStr:"+preStr);

			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			//System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			//System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			//System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");

				res = XmlUtils.toXml(resultMap);
				System.out.println("公众号返回数据1："+res);
				//System.out.println("中信银行服务器--银盛返回数据："+resultMap);
				String return_code = resultMap.get("return_code");

				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						//Map<String,String> new_map = com.mustpay.util.SignUtils.paraFilter(resultMap);//过滤掉空值和map
//						resultMap.remove("device_info");
//						resultMap.remove("err_code");
//						resultMap.remove("err_code_des");
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
//								System.out.println(prepayId);
//								setAttr("out_trade_no",out_trade_no);
//								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
//								setAttr("body",body);
								setAttr("prepayId", prepayId);
								renderJsp("/WEB-INF/pay/must/gzhPayType.jsp");									
							}else{
								renderText("服务器链接失败1");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败2");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			}else{
				renderText("签名失败");
			}
		}else{
			renderText("通讯失败，请稍后再试！");
		}
	}

	public void gongzhonghao_callback(){		
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			} 
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 121;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("return_code");
				if(status != null && "SUCCESS".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("method");//交易类型
						String r2_TrxId= map.get("bank_type>");//付款银行
						String ro_BankOrderId = map.get("transaction_id");//支付宝订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="116";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}																								
					} 
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void getway_bianwa(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="BWAALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="BWAWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="Service"+gateway_id+"qq1904178861";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowdate = sdf2.format(new Date());
			map.put("merchant_order_no", out_trade_no);			
			map.put("merchant_no", gateway_merid);			
			map.put("callback_url", notify_url);			
			map.put("order_smt_time", nowdate);			
			map.put("order_type","02");			
			map.put("trade_amount", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分			
			map.put("goods_name", "游戏道具/金币");			
			map.put("goods_type", "02");			
			map.put("trade_desc", nonce_str);//交易描述
			map.put("sign_type", "01");			
			String mch_key=gateway_key;//key
			//System.out.println("map====:"+map);

			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);//过滤参数
			Collection<String> keyset= params.keySet();  			  
			List<String> list=new ArrayList<String>(keyset);			  
			Collections.sort(list);  
			//这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的  
			JSONObject obj = new JSONObject();			
			String json = "";
			for(int i=0;i<list.size();i++){ 
				obj.put(list.get(i), params.get(list.get(i)));			
			}
			json = obj.toJSONString();
			String ssb = mch_key+json;
			String sign = MD5.sign(ssb,mch_key, "utf-8");
			System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);


			String charset="UTF-8";
			System.out.println("提交地址=："+reqUrl);
			StringBuffer response = new StringBuffer();  
			HttpClient client = new HttpClient();  
			PostMethod method = new PostMethod(reqUrl);

			// 设置Http Post数据  
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=" + charset);  
			if(map != null){  
				Set<String> keySet = map.keySet();  
				NameValuePair[] param = new NameValuePair[keySet.size()];  
				int i = 0;  
				for(String key2 : keySet){  
					param[i] = new NameValuePair(key2, map.get(key2));  
					i++;  
				}  
				method.setRequestBody(param);  
			}  
			InputStream responseBodyStream = null;  
			InputStreamReader streamReader = null;  
			BufferedReader reader = null;  
			try {  
				client.executeMethod(method);  
				if (method.getStatusCode() == HttpStatus.SC_OK) {  
					responseBodyStream = method.getResponseBodyAsStream();  
					streamReader = new InputStreamReader(responseBodyStream, charset);  
					reader = new BufferedReader(streamReader);  
					String line;  
					while ((line = reader.readLine()) != null) {  
						response.append(line);  
					}  
				} 
				System.out.println("response==:"+response);
				JSONObject jsonObject = JSONObject.parseObject(response.toString());
				String code = jsonObject.getString("code");
				String mybody = jsonObject.getString("body");
				System.out.println("Body==:"+mybody);
			} catch (IOException e) {  
				renderText("执行HTTP Post请求" + reqUrl + "时，发生异常！");  
			} finally {  
				try {  
					responseBodyStream.close();  
					streamReader.close();  
					reader.close();  
				} catch (IOException e) {  
					renderText("执行HTTP Post请求" + reqUrl + "时，发生异常！");  
					e.printStackTrace();  
				}  
				method.releaseConnection();  
			}
		}else{
			renderText("签名失败");
		}
	}

	public void getway_juhe(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名  		
		//String systemType= getPara("systemType");		

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="JHALI" + date.getTime();
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="JHEWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		//String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean flag = order.save();		
		if (nhmac.equals( hmac) && flag) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			//String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			//String contact_us="Service"+gateway_id+"qq1904178861";
			String total_fee = new BigDecimal(p3_Amt).setScale(2).toString();
			//String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			//String nonce_str = sdf.format(new Date());//随机字符串				

			Map<String,Object> map = new HashMap<String, Object>();
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			//String nowdate = sdf2.format(new Date());						
			//map.put("channelId", "");											
			//map.put("inputCharset","UTF-8");	
			//map.put("timestamp", nowdate);
			
			Map<String,String> data = new HashMap<String, String>();
			data.put("appId", "100105");
			data.put("orderMoney",total_fee);//将元转换为分			
			data.put("outTradeNo", out_trade_no);			
			data.put("merchantId", gateway_merid);			
			//data.put("systemType", systemType);//交易机型
			Map<String,String> params = SignUtils.paraFilter(data);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			String sign = MD5.sign(preStr, "&key=" + gateway_key, "utf-8");
			map.put("signature", sign);	
			
			String result = "";
			result += reqUrl;
			result += "?merchantId=" + gateway_merid;
			result += "&orderMoney=" + total_fee;			
			result += "&outTradeNo="+out_trade_no;
			result += "&appId=100105";
			result += "&signature=" + sign;	
			System.out.println("提交的URL=："+result);
			getResponse().sendRedirect(result);
			
//			setAttr("p3_Amt", total_fee);
//			setAttr("appId", "100105");
//			setAttr("outTradeNo", out_trade_no);
//			setAttr("merchantId",gateway_merid);
//			setAttr("orderMoney", total_fee);
//			setAttr("signature", sign);
//			renderJsp("/WEB-INF/pay/must/SubmitJH.jsp");					
		}else{
			renderText("签名失败");
		}
	}

	public void callback_juhe(){		
		try {
			String outTradeNo = getPara("outTradeNo");		//业务类型
			String merchantId = getPara("merchantId");  //商户ID
			String msg = getPara("msg");	//商户订单号			
			String noncestr = formatString(getPara("noncestr"));		//交易币种
			String orderNo = formatString(getPara("orderNo"));    	//商品名称
			String payResult =formatString(getPara("payResult"));	  	//商品种类
			String hmac = formatString(getPara("sign"));	//商品描述
			
			int gateway_id = 118;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");
			JSONObject js = JSONObject.parseObject(msg);						
			String r3_Amt =js.getString("payMoney");

			Map<String,String> map = new HashMap<String, String>();
			map.put("outTradeNo", outTradeNo);			
			map.put("merchantId", merchantId);			
			map.put("msg", msg);			
			map.put("noncestr", noncestr);			
			map.put("orderNo", orderNo);			
			map.put("payResult", payResult);					
			Map<String,String> params = SignUtils.paraFilter(map);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			String nhmac = MD5.sign(preStr, "&key=" + key, "utf-8");
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{				
				if(payResult != null && "1".equals(payResult)){
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					    Date date = new Date();
						String r6_Order = outTradeNo;//订单号
						String service = "alipaywap" ;//交易类型
						String r2_TrxId= orderNo;//威富通交易号
						String ro_BankOrderId = orderNo;//支付宝订单号
						String rp_PayDate = sdf.format(date);//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间						
						
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
//						BigDecimal amt = new BigDecimal(r3_Amt);
//						BigDecimal yb = new BigDecimal(100);
//						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid=String.valueOf(user_merId);
						String r7_Uid="118";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							boolean flag=cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, rp_PayDate, ru_Trxtime, pd_FrpId,r3_Amt.toString(),gateway_id);	
							if(flag){	
								HttpClient hClient = new HttpClient(); 
								HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
								managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
								managerParams.setSoTimeout(301100);
								PostMethod post = null;
								post = new PostMethod(result);
								post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
								post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
								String returnStr = "";
								hClient.executeMethod(post);
								returnStr = post.getResponseBodyAsString();
								if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
									System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
									Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
									if(asynchronous==null){
										asynchronous = new Asynchronous();
										asynchronous.set("orderid", r6_Order);
										asynchronous.set("status", "N");
										asynchronous.set("url", result);
										asynchronous.save();
									}else{
										asynchronous.set("url",result);
										asynchronous.set("status", "N");
										asynchronous.update();
									}
								}else{
									System.out.println(r6_Order+"同步通知成功");
									renderText("success");
								}
							}else{
								renderText("数据保存失败");
							}
						}else{
							renderText("success"); 
						}
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void getway_qiaozhifu(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="QZFALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="QZFWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			//String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="game-coin";
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			map.put("service", "qzf.aliwap.native.hee");
			map.put("version", "2.0");
			map.put("charset", "UTF-8");			
			map.put("sign_type", "MD5");			
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id",gateway_merid);//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("attach", body);
			map.put("goods_tag", body);

			map.put("notify_url", notify_url);
			map.put("mch_create_ip", user_ip);			
			String mch_key=gateway_key;//2f34fbf27de6d56ac0a09a4af0d6953b					
			//Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
			//StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			//com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			//String preStr = buf.toString();
			//System.out.println("preStr:"+preStr);
			String sign="";
			try {
				sign = MD5Utils.createMD5(mch_key);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);
			System.out.println("map====:"+map);				

			String res = "";
			System.out.println("提交地址=："+reqUrl);
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") || pd_FrpId.equals("wxwap")){
						renderText("通道分批异常，请联系客服");
					}else{
						if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){									
							redirect(code_url);
							out_trade_no = map.get("out_trade_no");
							total_fee = map.get("total_fee");
							body = map.get("body");
							System.out.println("订单号："+out_trade_no+"交易金额："+total_fee+"商品名称："+body);
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			

		}else{
			renderText("签名失败");
		}
	}

	public void callback_qiaozhifu(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 110;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = SignUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");						
			map.remove("sign");
			String preStr = SignUtils.formatUrlMap(map);			
			String nhmac  = SignUtils.sign(preStr, "&key="+key, "UTF-8");			
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("pay_result");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("service");//交易类型
						String r2_TrxId= map.get("transaction_id");//交易号
						String ro_BankOrderId = formatString(map.get("theThirdOrderNumber"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间

						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						rp_PayDate = sdf.format(new Date());

						System.out.println("生成的rp_PayDate=："+rp_PayDate);

						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);

						BigDecimal var_amt = new BigDecimal(1.5);//策略判断开始
						BigDecimal temp1 = new BigDecimal(1000);
						BigDecimal temp2 = new BigDecimal(2000);
						BigDecimal temp3 = new BigDecimal(3000);						
						var_amt = var_amt.add(amt);
						if(var_amt.compareTo(temp1)==0 || var_amt.compareTo(temp2)==0 || var_amt.compareTo(temp3)==0){
							amt = var_amt;
						}
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="25";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public void smAliipay(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "aliwap";			
			orderid="QZFALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="QZFWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("ALIPAY") || pd_FrpId.equals("alipay")){
			pd_FrpId = "alipay";			
			orderid="SMALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

			//String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			//String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="GameCoinCharge";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			//String nonce_str = sdf.format(new Date());//随机字符串

			String merNo= gateway_merid;
			String orderNo = orderid;
			String channelFlag="01";//支付宝
			String amount=String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue());//将元转换为分;
			String reqId = sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
			String reqTime = sdf2.format(new Date());
			String goodsName = body;	


			Map<String,String> map = new HashMap<String, String>();
			map.put("merNo", merNo);
			map.put("orderNo", orderNo);
			map.put("channelFlag", channelFlag);			
			map.put("amount", amount);			
			map.put("reqId", reqId);
			map.put("reqTime",reqTime);//m20170517000010420			
			map.put("goodsName", goodsName);					
			map.put("notifyUrl", notify_url);

			String mch_key=gateway_key;//2f34fbf27de6d56ac0a09a4af0d6953b					

			String sign="";			
			String preStr = SignUtils.formatUrlMap(map);			
			sign  = SignUtils.sign(preStr, mch_key, "UTF-8").toUpperCase();
			System.out.println("计算的数字签名是："+sign);
			map.put("signIn", sign);
			//System.out.println("map====:"+map);				

			System.out.println("提交地址=："+reqUrl);
			String result = "";
			result += reqUrl;
			result += "?merNo=" + merNo;
			result += "&orderNo=" + orderNo;
			result += "&channelFlag=" + channelFlag;			
			result += "&amount=" + amount;
			result += "&reqId=" + reqId;
			result += "&reqTime=" + reqTime;
			result += "&goodsName=" + goodsName;
			result += "&notifyUrl=" + notify_url;			
			result += "&signIn=" + sign;			
			try {
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				System.out.println("抓取的参数是=："+returnStr);

				if(returnStr != null){					
					JSONObject jsonObj = JSONObject.parseObject(returnStr);
					String code_url = jsonObj.getString("codeUrl");
					String status = jsonObj.getString("result");
					String orderNo2 = jsonObj.getString("orderNo");
					String req_id = jsonObj.getString("reqId");
					if(code_url.equals("") || pd_FrpId.equals("wxwap")){
						renderText("通道分批异常，请联系客服");
					}else{
						if("0000".equals(status) && reqId.equals(req_id)){
							redirect(code_url);
							setAttr("code_img_url", code_url);
							setAttr("out_trade_no", orderNo2);
							setAttr("total_fee", p3_Amt);
							setAttr("body", goodsName);
							//renderJsp("/WEB-INF/winxin/alipay.jsp");

							System.out.println("*****************************************************");
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
			} 

		}else{
			renderText("签名失败");
		}
	}

	public void getway_wxGZH(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "ALIWAP";			
			orderid="ZHXALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="GZHWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="Service"+gateway_id+"qq1904178861";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();
			map.put("method", "mbupay.alipay.wap");
			map.put("version", "2.0.1");
			//map.put("charset", "utf-8");
			map.put("appid", "ca2017051910000034");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id","cm2017051910000034");//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			String mch_key="67ef8a7849c3b9456b80b2a46d1ea6fd";//2f34fbf27de6d56ac0a09a4af0d6953b
			System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			//System.out.println("preStr:"+preStr);
			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");

				res = XmlUtils.toXml(resultMap);
				//System.out.println("中信银行服务器返回数据1："+res);
				System.out.println("中信银行服务器返回数据2："+resultMap);
				String return_code = resultMap.get("return_code");
				resultMap.remove("return_msg");
				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
								System.out.println(prepayId);
								setAttr("out_trade_no",out_trade_no);
								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
								setAttr("body",body);
								setAttr("prepayId", prepayId);
								renderJsp("/WEB-INF/pay/must/gzhPayType.jsp");									
							}else{
								renderText("服务器链接失败");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			renderText("签名失败");
		}
	}

	public void getway_aibei(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		String ptype="";
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";
			ptype="401";
			orderid="AIBALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";	
			ptype="403";
			orderid="AIBWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		//		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		//		String gateway_key = gateway.getStr("gateway_key");	
		//		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String cporderid =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");//商户订单号
			//String appid = "3013677303 ";	//应用appid
			//int waresid=1;      			//商品编号
			String waresname="有疑问，请联系商家客服";			//商品名称
			float price = Float.parseFloat(p3_Amt);
			//String currency="RMB";
			String appuserid="15000086800894";//用户在商户应用的唯一标识
			String cpprivateinfo=p1_MerId;		  //商户私有信息
			//System.out.println("APPID=:"+IAppPaySDKConfig.APP_ID);
			String reqData = OrderIapay.ReqData(IAppPaySDKConfig.APP_ID,  IAppPaySDKConfig.WARES_ID_1, waresname,cporderid, price, appuserid, cpprivateinfo, notify_url);
			//String postUrl="https://cp.cassim.cn/payapi/order";//凯新
			//String postUrl="http://ipay.iapppay.com:9999/payapi/order";//爱贝
			//String respData = HttpUtils.sentPost("https://cp.cassim.cn/payapi/order", reqData,"UTF-8"); // 请求验证服务端
			String respData = HttpUtils.sentPost("http://ipay.iapppay.com:9999/payapi/order", reqData,"UTF-8"); // 请求验证服务端
			System.out.println("响应数据："+respData);

			Map<String, String> reslutMap = SignUtilsAIB.getParmters(respData);
			String signtype = reslutMap.get("signtype"); // "RSA";
			if(signtype==null)
			{
				renderText("支付类型错误");
			}else{
				/*
				 * 调用验签接口
				 * 
				 * 主要 目的 确定 收到的数据是我们 发的数据，是没有被非法改动的
				 */
				//System.out.println("数字签名是1："+reslutMap.get("sign"));
				//System.out.println("数字签名是2："+SignHelper.sign(reslutMap.get("transdata"), IAppPaySDKConfig.PLATP_KEY));

				if (SignHelper.verify(reslutMap.get("transdata"), reslutMap.get("sign"),IAppPaySDKConfig.PLATP_KEY)) {
					//System.out.println(reslutMap.get("transdata"));
					//System.out.println(reslutMap.get("sign"));			
					JSONObject json=JSONObject.parseObject(reslutMap.get("transdata"));
					String transid = formatString(json.getString("transid"));
					System.out.println("verify ok");
					if(transid.equals("")){
						renderText("网络繁忙，请稍后再试");
					}else{
						String basePath = this.getRequest().getScheme()+"://"+this.getRequest().getServerName()+":"+this.getRequest().getServerPort();
						String url_r=basePath+"/web/Result/";
						String url_h=basePath+"/web/Result/";
						//String sendURL = "https://h.cassim.cn/h5/d/gateway?";
						String sendURL = "https://web.iapppay.com/h5/d/gateway?";
						String url = sendURL+OrderIapay.ReqData2(transid, url_r, url_h, ptype);
						System.out.println("提交的地址是："+url);
						//BareBonesBrowserLaunch.openURL(url);
						getResponse().sendRedirect(url);
					}
				} else {
					renderText("签名错误");
				}
			}

		}else{
			renderText("签名失败");
		}
	}

	public void callback_aib(){
		try {
			String respData="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					respData +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  
			respData = java.net.URLDecoder.decode(respData,"UTF-8");
			System.out.println(respData);		    
			Map<String, String> reslutMap = SignUtilsAIB.getParmters(respData);			
			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 114;
			//Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			//String key = gateway.getStr("gateway_key");
			//String sign1 = reslutMap.get("sign");			
			//String sign2 = SignHelper.sign(reslutMap.get("transdata"), IAppPaySDKConfig.PLATP_KEY);
			String transdata = reslutMap.get("transdata");
			JSONObject jsonObject = JSONObject.parseObject(transdata);

			if(!SignHelper.verify(reslutMap.get("transdata"), reslutMap.get("sign"),IAppPaySDKConfig.PLATP_KEY)){
				System.out.println("数字签名错误！"); 
			}else{
				String status = jsonObject.getString("result");
				if(status != null && "0".equals(status)){
					String money = jsonObject.getString("money");

					BigDecimal total_fee = new BigDecimal(money);
					BigDecimal temp = new BigDecimal(0);//验证返回金额为正数
					System.out.println("money=:"+total_fee);
					if(total_fee.compareTo(temp)>0){

						String r6_Order = jsonObject.getString("cporderid");//订单号
						System.out.println("r6_Order=:"+r6_Order);
						String service = jsonObject.getString("paytype");//交易类型
						if(service.equals("401")){
							service = "alipaywap";
						}
						String r2_TrxId= jsonObject.getString("transid");//交易号						
						String ro_BankOrderId = jsonObject.getString("transid");//第三方订单号

						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf.format(new Date());

						System.out.println("生成的r6_Order=："+r6_Order);

						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = money;
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);

						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="1004";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					}else{
						System.out.println("返回金额有误！");
					}
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getway_swiftpass2(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "MOTALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "MOTWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "MOTWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass2(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 23;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//2017-09-20 新增-----------------威富通4------------start
	public void getway_swiftpass4(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "GDAALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "GDAWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "GDAWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass4(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 136;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							//System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getway_swiftpass5(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "GDA2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "GDA2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "GDA2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass5(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 137;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							//System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_swiftpass6(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "XY1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass6(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 138;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							//System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_swiftpass7(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "XY1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass7(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 139;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							//System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_swiftpass8(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = "XY1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "XY1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");

			String note = "有疑问，请联系商家电话";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			//String sys_callback = "http://183.131.83.71:8080/pay/callback_swiftpass";//地址
			//map.put("callback_url ", sys_callback);   //前台显示地址
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", String.valueOf(r3_Amt));
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="书城";
				String mch_app_id="http://pay2.yongzewangluo.cn";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			String res = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					res = XmlUtils.toXml(resultMap);
					//System.out.println("服务器返回数据："+res);
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("通道异常，请联系客服");
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								res = "验证签名不通过";
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}
									String out_trade_no = map.get("out_trade_no");
									String total_fee = map.get("total_fee");
									String body = map.get("body");
									//System.out.println("结果1：" + pay_info+"结果2："+out_trade_no+"结果3："+total_fee+"结果4："+body);
								}
							}
						} 
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}
			if(res.startsWith("<")){
				getResponse().setHeader("Content-type", "text/xml;charset=UTF-8");
			}else{
				getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			}
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass8(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  			
			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 140;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				

			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("系统故障，请联系客服");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							//System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//2017-09-20 新增-----------------威富通4------------end
	
	
	public void getway_ms(int gateway_id) throws IOException{

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");
		String p2_Order = getPara("p2_Order");
		String p3_Amt = getPara("p3_Amt");
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat = getPara("p6_Pcat");
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");
		String pr_NeedResponse = getPara("pr_NeedResponse");
		String hmac = getPara("hmac");

		String sbOld = "";
		sbOld = sbOld + p0_Cmd;
		sbOld = sbOld + p1_MerId;
		sbOld = sbOld + p2_Order;
		sbOld = sbOld + p3_Amt;
		sbOld = sbOld + p4_Cur;
		sbOld = sbOld + p5_Pid;
		sbOld = sbOld + p6_Pcat;
		sbOld = sbOld + p7_Pdesc;
		sbOld = sbOld + p8_Url;
		sbOld = sbOld + p9_SAF;
		sbOld = sbOld + pa_MP;
		sbOld = sbOld + pd_FrpId;
		sbOld = sbOld + pr_NeedResponse;
		String key = ((Payment) Payment.dao.findById(p1_MerId)).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key);
		//System.out.println("生成的客户签名是==：" + nhmac);
		//System.out.println("接收到的客户签名是=：" + hmac);

		if (nhmac.equals(hmac)) {
			String user_ip = CryptTool.getIpAddr(getRequest());
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			Date date = new Date();
			String orderid = "";
			if ((pd_FrpId.equals("alipaywap"))|| (pd_FrpId.equals("ALIPAYWAP"))) {
				pd_FrpId = "alipaywap";
				orderid = "ZWPALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4) + gateway_id;
			}else {
				renderText("抱歉，无扫码支付权限");
			}
			Gateway gateway = (Gateway) Gateway.dao.findById(gateway_id);
			String account_no = gateway.getStr("gateway_merid");
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String note = "GAMECOIN";			
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("account_no", account_no);
			map.put("body", note);
			map.put("method", "00000007");
			map.put("money", p3_Amt);
			map.put("nonce_str", String.valueOf(date.getTime()));
			map.put("notify", notify_url);
			map.put("order_sn",orderid);
			map.put("pay_tool", "alismhf");
			map.put("productId", "02");			
			map.put("version", "v1.0");			
			
			//Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((map.size() +1) * 10);
			SignUtils.buildPayParams(sb,map,false);			
			String signature = MD5.sign(sb.toString(), mch_key, "utf-8");
			//String signature = getKeyedDigest(getSignParam(map), mch_key);11
			
			map.put("signature",signature);
			String baowen = getSignParam(map);			
			map.put("signature",signature);			
			//String baowen = sb.toString();
			System.out.println("请求报文："+baowen);
			
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", Integer.valueOf(0));
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);
			order.set("pr_NeedResponse", Integer.valueOf(1));
			order.set("hmac", "dsadmshfdshf");
			order.set("CreateTime", date);
			order.set("success", Integer.valueOf(2));
			order.set("r1_Code", Integer.valueOf(2));
			order.set("lock", Integer.valueOf(1));
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			boolean flag = order.save();
		try{		
			if(flag){					
			String res = new String(MysendPost(reqUrl, baowen).getBytes("UTF-8"),"UTF-8");
//			System.out.println("返回的参数是1=："+res);
//			JSONObject json=JSONObject.parseObject(res);
//			String msg = formatString(json.getString("res_msg"));
//			msg = new String(msg.getBytes("gbk"),"UTF-8");					
//			System.out.println("返回的参数msg=："+msg);
//			System.out.println("返回的参数是2=："+json);
//			String codeUrl = formatString(json.getString("codeUrl"));
			String[] ts = res.split(",");
			String result = ""; 
			for (String string : ts) {
				if(string.contains("codeUrl")){
					result = string;
					break;
				}
			}
			String[] codeUrl = result.split("\":\"");
			String reString = codeUrl[1];
			String code = reString.replace("\"","").replace("\\", "");
			System.out.println("参数URL-1=："+code);
			//code=java.net.URLEncoder.encode(code, "UTF-8");
			getResponse().sendRedirect(code);
			}else{
				renderText("网络链接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		} else {
			renderText("客户签名失败");
		}
	}
	
	public void callback_ms(){		
		try {
			String json="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					json +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  
			String res_code="";
			String res_msg="";
			String status="";
			String order_sn="";
			String money="";
			String nonce_str="";
			String signature="";			
			//System.out.println("json===:"+json);
			String[] jsons = json.split("&");
			for(int i=0;i<jsons.length;i++){
				String[] res_codes = jsons[i].split("=");
				if(res_codes[0].equals("res_code")){
					res_code = res_codes[1];
				}
				if(res_codes[0].equals("res_msg")){
					res_msg = res_codes[1];
				}
				if(res_codes[0].equals("status")){
					status = res_codes[1];
				}
				if(res_codes[0].equals("order_sn")){
					order_sn = res_codes[1];
				}
				if(res_codes[0].equals("money")){
					money = res_codes[1];
				}
				if(res_codes[0].equals("nonce_str")){
					nonce_str = res_codes[1];
				}
				if(res_codes[0].equals("signature")){
					signature = res_codes[1];
				}
			}
			//money=0.01&nonce_str=wR0cihM65eoCel2&order_sn=ZWPALI201707210246250469049119&res_code=&res_msg=success&status=1HA5IFCOs8ecwigcuT4it6v
			//money=0.01&nonce_str=EJvpOV1tIR6A9Zc&order_sn=ZWPALI201707210231140313229119&res_code=0000&res_msg=success&status=1HA5IFCOs8ecwigcuT4it6v
			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 119;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");						
			String hmac = signature;
			
			Map<String,String> map = new HashMap<String, String>();			
			map.put("res_code", res_code);			
			map.put("res_msg", res_msg);			
			map.put("nonce_str", nonce_str);			
			map.put("status", status);
			map.put("order_sn", order_sn);
			map.put("money", money);	
			String pj=getSignParam(map);
			//System.out.println("sign的拼接字符串是=："+pj);
			String nhmac = MD5.sign(pj, key, "utf-8");
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{				
				if(status != null && "1".equals(status)){
					if(res_code != null && "0000".equals(res_code)){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						Date date = new Date();
						String r6_Order = order_sn;//订单号
						String service = "alipaywap" ;//交易类型
						String r2_TrxId= nonce_str;//交易号
						String ro_BankOrderId = nonce_str;//支付宝订单号
						String rp_PayDate = sdf.format(date);//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt =money;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);																									
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid=String.valueOf(user_merId);
						String r7_Uid="118";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							boolean flag=cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, rp_PayDate, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
							if(flag){	
								HttpClient hClient = new HttpClient(); 
								HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
								managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
								managerParams.setSoTimeout(301100);
								PostMethod post = null;
								post = new PostMethod(result);
								post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
								post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
								String returnStr = "";
								hClient.executeMethod(post);
								returnStr = post.getResponseBodyAsString();
								if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
									System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
									Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
									if(asynchronous==null){
										asynchronous = new Asynchronous();
										asynchronous.set("orderid", r6_Order);
										asynchronous.set("status", "N");
										asynchronous.set("url", result);
										asynchronous.save();
									}else{
										asynchronous.set("url",result);
										asynchronous.set("status", "N");
										asynchronous.update();
									}
								}else{
									System.out.println(r6_Order+"同步通知成功");
									renderText("success");
								}
							}else{
								System.out.println("数据保存失败");
							}
						}else{
							renderText("success"); 
						}
					} 
				}else{
					System.out.println("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("---出现异常---");
		}
	}
	
	public void getway_cai(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="CAIALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="QQ813560690";
			String total_fee = p3_Amt;
			String body = contact_us;			

			Map<String, String> paramMap = new HashMap<String, String>();
			 paramMap.put("payKey", gateway_merid);// 商户支付Key
			 paramMap.put("orderPrice", total_fee);
			 paramMap.put("outTradeNo", orderid);
			 paramMap.put("productType","20000303");//支付宝T0扫码支付			
			 paramMap.put("productName", body);// 商品名称
			String returnUrl = "http://netpay.fz222.com/web/Result"; // 页面通知返回url
			
			paramMap.put("returnUrl", returnUrl);
			String notifyUrl = notify_url; // 后台消息通知Url
			paramMap.put("notifyUrl", notifyUrl);
			paramMap.put("remark", "alipay");

			///// 签名及生成请求API的方法///
			String sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),"&paySecret="+gateway_key, "UTF-8").toUpperCase();
			paramMap.put("sign", sign);
			//System.out.println(reqUrl);
			//System.out.println(paramMap);
			//System.out.println("开始请求:" + DateUtil.formatDate(new Date(), "yyyy-MMdd HH:mm:ss SSS"));
			
			String preStr = SignUtils.formatUrlMap(paramMap);	
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(reqUrl);//"http://vip.readchen.com/pay/test_search_back");
			RequestEntity requestEntity = new StringRequestEntity(preStr,"text/xml","UTF-8");
			post.setRequestEntity(requestEntity);
			
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			try {
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//System.out.println("结束请求:" + DateUtil.formatDate(new Date(), "yyyy-MMdd HH:mm:ss SSS"));

			//System.out.println(returnStr);
			JSONObject jsonObject = JSON.parseObject(returnStr);
			Object resultCode = jsonObject.get("resultCode");// 返回码
			String payMessage = jsonObject.getString("payMessage");// 请求结果(请求成功时)
			//Object errMsg = jsonObject.get("errMsg");// 错误信息(请求失败时)

			if ("0000".equals(resultCode.toString())) {// 请求成功
				redirect(payMessage);
			} else {// 请求失败
				renderText("系统异常");
			}

		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_cai(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			Map<String,String> map = new HashMap<String,String>();
			Map requestParams = getRequest().getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				map.put(name, valueStr);
			}
			//System.out.println("req:"+map);
			int gateway_id = 120;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");			
			String hmac = map.get("sign");						
			map.remove("sign");
			String preStr = SignUtils.formatUrlMap(map);			
			String nhmac  = SignUtils.sign(preStr,"&paySecret="+key, "UTF-8").toUpperCase();			
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				String tradeStatus = map.get("tradeStatus");
				if(tradeStatus != null && "SUCCESS".equals(tradeStatus)){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){	
						
						String r6_Order = map.get("outTradeNo");//订单号
						String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= r6_Order;//交易号
						String ro_BankOrderId = map.get("trxNo");//第三方订单号
						String rp_PayDate = map.get("successTime");//支付完成时间						
						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);
						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("orderPrice");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="80";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
							renderText("SUCCESS");
						}else{
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				//} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}
	}
	
	void gateway_sampay(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码			
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="CAIALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="GameCoin";;
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			//String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", gateway_merid);
			map.put("payType", "1");
			map.put("amount", total_fee);
			map.put("commodity", contact_us);
			
			/** 参与验签的字段 */
			String sign = getSignParam(map);
//			System.out.println("计算签名的报文为：" + sign);
			sign = getKeyedDigest(sign, gateway_key);
			map.put("orderCode", "ysb_codePay");
			map.put("sign", sign);
			map.put("pay_number", orderid);
			map.put("notify_url", notify_url);
			String baowen = getSignParam(map);
			//System.out.println("上送的报文为：" + baowen);
			//java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
			//java.util.Date datef = new java.util.Date(System.currentTimeMillis());
			//System.out.println("发送请求时间："+ formatter.format(datef));
			String sr = MysendPost(reqUrl, baowen);
			//System.out.println(sr);
			//java.util.Date date1 = new java.util.Date(System.currentTimeMillis());
			//System.out.println("收到结果时间:"+formatter.format(date1));
			
			JSONObject resultjson = (JSONObject) JSONObject.parse(sr);
			String respCode = resultjson.getString("respCode");
			String qrCode = resultjson.getString("qrCode");
			String respInfo = resultjson.getString("respInfo");
			if ("000000".equals(respCode.toString())) {// 请求成功
				redirect(qrCode);
			} else {// 请求失败
				renderText(respInfo);
			}

		}else{
			renderText("签名失败");
		}
	}
	public void callback_sampay()
	{
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			Map<String,String> map = new HashMap<String,String>();
			Map requestParams = getRequest().getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				map.put(name, valueStr);
			}
			//System.out.println("req:"+map);
			int gateway_id = 122;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");
			
			String pay_number = map.remove("pay_number");
			String hmac = map.remove("sign");
			String orderId = map.remove("orderId");
			String status = map.remove("status");
			String amount = map.remove("amount");			
//			System.out.println("计算签名的报文为：" + sign);
			String nhmac = getKeyedDigest(String.format("pay_number=%s&orderId=%s&status=%s&amount=%s", pay_number,orderId,status,amount), key);
			
			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(status != null && "000000".equals(status)){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){							
						String r6_Order = pay_number;
						String service = "alipay";//map.get("service");//交易类型
						String r2_TrxId= r6_Order;//交易号
						String ro_BankOrderId = orderId;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf.format(new Date());						
						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);
						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = amount;
						
						BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);      
						service = o.get("pd_FrpId");
						System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="122";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						String returnStr = "";						
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						
						System.out.println("通知回"+returnStr);
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
							renderText("FAIL");
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}	
						
					} 
				//} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gateway_sampay2(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码			
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		String pm="";
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pm = "alipay";			
			orderid="SAMALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pm = "wxwap";			
			orderid="SAMWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			pm="alipay";
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean flag = order.save();
		if(flag){
			String Pe_Rurl="http://vip.haofpay.com/web/Result/";
		if (nhmac.equals( hmac)) {
			 pr_NeedResponse="1";
			 String sb = "";
			 sb += p0_Cmd;
			 sb += gateway_merid;
			 sb += orderid;
			 sb += p3_Amt;
			 sb += p4_Cur;
			 sb += p5_Pid;
			 sb += p6_Pcat;
			 sb += p7_Pdesc;
			 sb += notify_url;
			 sb += p9_SAF;
			 sb += pa_MP;
			 sb += pm;
			 if(pm.equals("wxwap")){
				sb+=Pe_Rurl; 
			 }
			 sb += pr_NeedResponse;	         
	         hmac = DigestUtil.hmacSign(sb, gateway_key); //数据签名
			
	         String result = "";
	         result += reqUrl;
	         result += "?p0_Cmd=" + p0_Cmd;
	         result += "&p1_MerId=" + gateway_merid;
	         result += "&p2_Order=" + orderid;
	         result += "&p3_Amt=" + p3_Amt;
	         result += "&p4_Cur=" + p4_Cur;
	         result += "&p5_Pid=" + p5_Pid;
	         result += "&p6_Pcat=" + p6_Pcat;
	         result += "&p7_Pdesc=" + p7_Pdesc;
	         result += "&p8_Url=" + notify_url;
	         result += "&p9_SAF=" + p9_SAF;
	         result += "&pa_MP=" +pa_MP;
	         if(pm.equals("wxwap")){
	        	 result += "&pe_Rurl=" +Pe_Rurl;
			 }
	         result += "&pd_FrpId=" + pm;
	         result += "&pr_NeedResponse=" + pr_NeedResponse;
	         result += "&hmac=" + hmac;	 	         
			 redirect(result);
			
		}else{
			renderText("签名失败");
		}
		}else{
			renderText("订单已存在，无法继续支付");
		}
	}
	
	public void callback_sampay2(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		this.getResponse().setCharacterEncoding("UTF-8");
		System.out.println("支付宝扫码-SAM回调地址");
		String r0_Cmd = getPara("r0_Cmd");
		String r1_Code = getPara("r1_Code");
		String r2_TrxId = getPara("r2_TrxId");	//平台流水号
		String r3_Amt = getPara("r3_Amt");		//支付金额
		String r4_Cur = getPara("r4_Cur");
		String r5_Pid = getPara("r5_Pid");
		String r6_Order =getPara("r6_Order");	//商户订单号
		String r7_Uid = getPara("r7_Uid");
		String r8_MP = getPara("r8_MP");
		String r9_BType = getPara("r9_BType");  //通知类型 1同步通知 2异步通知
		String rp_PayDate = getPara("rp_PayDate");
		String hmac = getPara("hmac");	  

		//System.out.println("服务器返回给平台的数字签名是====："+hmac);
		//System.out.println("银行返回来的商户订单号==========："+r6_Order);

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		int gateway_id = 122;
		//if(!formatString(r7_Uid).equals("")){
		//	gateway_id = Integer.parseInt(r7_Uid);
		//}else{
		//	renderText("参数错误");
		//}
		//System.out.println("商户使用通道信息：gateway_id=："+gateway_id);
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");

		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("success");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
			String sbOld = "";
			sbOld += mch_id;
			sbOld += r0_Cmd;
			sbOld += r1_Code;
			sbOld += r2_TrxId;
			sbOld += r3_Amt;
			sbOld += r4_Cur;
			sbOld += r5_Pid;
			sbOld += r6_Order;
			sbOld += r7_Uid;
			sbOld += r8_MP;
			sbOld += r9_BType;        
			sbOld += rp_PayDate;
			//根据返回数据加工计算客户的数字签名Hmac
			String nhmac = DigestUtil.hmacSign(sbOld, mch_key); //平台数据签名
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			rp_PayDate=sdf2.format(new Date());			
			r0_Cmd="Buy";
			//r1_Code="1";
			r4_Cur="CNY";
			r5_Pid="";
			r7_Uid="122";//通道id
			r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
			System.out.println("充值成功回调给客户的URL=："+result);
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
			System.out.println("数据库保存状态=："+flag);
			if (nhmac.equals(hmac)) {  
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3011000);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			try {
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				System.out.println("xxxxxxxxxxxxxxxxxxxxx=="+returnStr);
			} catch (HttpException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}
			}else{
				System.out.println(r6_Order+"同步通知成功");
				renderText("SUCCESS");
			}				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
			}else{
				renderText("SUCCESS");
			}
		}
	}
	
	public void gateway_buyu(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码			
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		String pm="";
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pm = "alipay";			
			orderid="BYUALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pm = "wxwap";			
			orderid="BYUWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			pm="alipay";
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean flag = order.save();
		if(flag){
			String Pe_Rurl="http://vip.haofpay.com/web/Result/";
		if (nhmac.equals( hmac)) {
			 pr_NeedResponse="1";
			 String sb = "";
			 sb += p0_Cmd;
			 sb += gateway_merid;
			 sb += orderid;
			 sb += p3_Amt;
			 sb += p4_Cur;
			 sb += p5_Pid;
			 sb += p6_Pcat;
			 sb += p7_Pdesc;
			 sb += notify_url;
			 sb += p9_SAF;
			 sb += pa_MP;
			 sb += pm;
			 if(pm.equals("wxwap")){
				sb+=Pe_Rurl; 
			 }
			 sb += pr_NeedResponse;	         
	         hmac = DigestUtil.hmacSign(sb, gateway_key); //数据签名
			
	         String result = "";
	         result += reqUrl;
	         result += "?p0_Cmd=" + p0_Cmd;
	         result += "&p1_MerId=" + gateway_merid;
	         result += "&p2_Order=" + orderid;
	         result += "&p3_Amt=" + p3_Amt;
	         result += "&p4_Cur=" + p4_Cur;
	         result += "&p5_Pid=" + p5_Pid;
	         result += "&p6_Pcat=" + p6_Pcat;
	         result += "&p7_Pdesc=" + p7_Pdesc;
	         result += "&p8_Url=" + notify_url;
	         result += "&p9_SAF=" + p9_SAF;
	         result += "&pa_MP=" +pa_MP;
	         if(pm.equals("wxwap")){
	        	 result += "&pe_Rurl=" +Pe_Rurl;
			 }
	         result += "&pd_FrpId=" + pm;
	         result += "&pr_NeedResponse=" + pr_NeedResponse;
	         result += "&hmac=" + hmac;	 	         
			 redirect(result);
			
		}else{
			renderText("签名失败");
		}
		}else{
			renderText("订单已存在，无法继续支付");
		}
	}
	
	public void callback_buyu(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		this.getResponse().setCharacterEncoding("UTF-8");
		System.out.println("支付宝扫码-SAM回调地址");
		String r0_Cmd = getPara("r0_Cmd");
		String r1_Code = getPara("r1_Code");
		String r2_TrxId = getPara("r2_TrxId");	//平台流水号
		String r3_Amt = getPara("r3_Amt");		//支付金额
		String r4_Cur = getPara("r4_Cur");
		String r5_Pid = getPara("r5_Pid");
		String r6_Order =getPara("r6_Order");	//商户订单号
		String r7_Uid = getPara("r7_Uid");
		String r8_MP = getPara("r8_MP");
		String r9_BType = getPara("r9_BType");  //通知类型 1同步通知 2异步通知
		String rp_PayDate = getPara("rp_PayDate");
		String hmac = getPara("hmac");	  

		//System.out.println("服务器返回给平台的数字签名是====："+hmac);
		//System.out.println("银行返回来的商户订单号==========："+r6_Order);

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		int gateway_id = 134;
		//if(!formatString(r7_Uid).equals("")){
		//	gateway_id = Integer.parseInt(r7_Uid);
		//}else{
		//	renderText("参数错误");
		//}
		//System.out.println("商户使用通道信息：gateway_id=："+gateway_id);
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");

		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("success");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
			String sbOld = "";
			sbOld += mch_id;
			sbOld += r0_Cmd;
			sbOld += r1_Code;
			sbOld += r2_TrxId;
			sbOld += r3_Amt;
			sbOld += r4_Cur;
			sbOld += r5_Pid;
			sbOld += r6_Order;
			sbOld += r7_Uid;
			sbOld += r8_MP;
			sbOld += r9_BType;        
			sbOld += rp_PayDate;
			//根据返回数据加工计算客户的数字签名Hmac
			String nhmac = DigestUtil.hmacSign(sbOld, mch_key); //平台数据签名
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			rp_PayDate=sdf2.format(new Date());			
			r0_Cmd="Buy";
			//r1_Code="1";
			r4_Cur="CNY";
			r5_Pid="";
			r7_Uid="122";//通道id
			r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
			System.out.println("充值成功回调给客户的URL=："+result);
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
			System.out.println("数据库保存状态=："+flag);
			if (nhmac.equals(hmac)) {  
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3011000);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			try {
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
			} catch (HttpException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}
			}else{
				System.out.println(r6_Order+"同步通知成功");
				renderText("SUCCESS");
			}				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
			}else{
				renderText("SUCCESS");
			}
		}
	} 
	
	public void getway_SamBuyu(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码			
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		String pm="";
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pm = "alipay";			
			orderid="BYUALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pm = "wxwap";			
			orderid="BYUWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			pm="alipay";
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean flag = order.save();
		if(flag){
			String Pe_Rurl="http://vip.haofpay.com/web/Result/";
		if (nhmac.equals( hmac)) {
			 pr_NeedResponse="1";
			 String sb = "";
			 sb += p0_Cmd;
			 sb += gateway_merid;
			 sb += orderid;
			 sb += p3_Amt;
			 sb += p4_Cur;
			 sb += p5_Pid;
			 sb += p6_Pcat;
			 sb += p7_Pdesc;
			 sb += notify_url;
			 sb += p9_SAF;
			 sb += pa_MP;
			 sb += pm;
			 if(pm.equals("wxwap")){
				sb+=Pe_Rurl; 
			 }
			 sb += pr_NeedResponse;	         
	         hmac = DigestUtil.hmacSign(sb, gateway_key); //数据签名
			
	         String result = "";
	         result += reqUrl;
	         result += "?p0_Cmd=" + p0_Cmd;
	         result += "&p1_MerId=" + gateway_merid;
	         result += "&p2_Order=" + orderid;
	         result += "&p3_Amt=" + p3_Amt;
	         result += "&p4_Cur=" + p4_Cur;
	         result += "&p5_Pid=" + p5_Pid;
	         result += "&p6_Pcat=" + p6_Pcat;
	         result += "&p7_Pdesc=" + p7_Pdesc;
	         result += "&p8_Url=" + notify_url;
	         result += "&p9_SAF=" + p9_SAF;
	         result += "&pa_MP=" +pa_MP;
	         if(pm.equals("wxwap")){
	        	 result += "&pe_Rurl=" +Pe_Rurl;
			 }
	         result += "&pd_FrpId=" + pm;
	         result += "&pr_NeedResponse=" + pr_NeedResponse;
	         result += "&hmac=" + hmac;	 	         
			 redirect(result);
			
		}else{
			renderText("签名失败");
		}
		}else{
			renderText("订单已存在，无法继续支付");
		}
	}
	
	public void callback_sambuyu(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		this.getResponse().setCharacterEncoding("UTF-8");
		System.out.println("支付宝扫码-SAM回调地址");
		String r0_Cmd = getPara("r0_Cmd");
		String r1_Code = getPara("r1_Code");
		String r2_TrxId = getPara("r2_TrxId");	//平台流水号
		String r3_Amt = getPara("r3_Amt");		//支付金额
		String r4_Cur = getPara("r4_Cur");
		String r5_Pid = getPara("r5_Pid");
		String r6_Order =getPara("r6_Order");	//商户订单号
		String r7_Uid = getPara("r7_Uid");
		String r8_MP = getPara("r8_MP");
		String r9_BType = getPara("r9_BType");  //通知类型 1同步通知 2异步通知
		String rp_PayDate = getPara("rp_PayDate");
		String hmac = getPara("hmac");	  

		//System.out.println("服务器返回给平台的数字签名是====："+hmac);
		//System.out.println("银行返回来的商户订单号==========："+r6_Order);

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		int gateway_id = 120;
		//if(!formatString(r7_Uid).equals("")){
		//	gateway_id = Integer.parseInt(r7_Uid);
		//}else{
		//	renderText("参数错误");
		//}
		//System.out.println("商户使用通道信息：gateway_id=："+gateway_id);
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");

		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("success");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
			String sbOld = "";
			sbOld += mch_id;
			sbOld += r0_Cmd;
			sbOld += r1_Code;
			sbOld += r2_TrxId;
			sbOld += r3_Amt;
			sbOld += r4_Cur;
			sbOld += r5_Pid;
			sbOld += r6_Order;
			sbOld += r7_Uid;
			sbOld += r8_MP;
			sbOld += r9_BType;        
			sbOld += rp_PayDate;
			//根据返回数据加工计算客户的数字签名Hmac
			String nhmac = DigestUtil.hmacSign(sbOld, mch_key); //平台数据签名
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			rp_PayDate=sdf2.format(new Date());			
			r0_Cmd="Buy";
			//r1_Code="1";
			r4_Cur="CNY";
			r5_Pid="";
			r7_Uid="120";//通道id
			r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
			System.out.println("充值成功回调给客户的URL=："+result);
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
				String ru_Trxtime=rp_PayDate;//交易结果记录时间
				Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
			System.out.println("数据库保存状态=："+flag);
			if (nhmac.equals(hmac)) {  
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3011000);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			try {
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
			} catch (HttpException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}
			}else{
				System.out.println(r6_Order+"同步通知成功");
				renderText("SUCCESS");
			}				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
			}else{
				renderText("SUCCESS");
			}
		}
	} 	
	
	public void getway_xjpay(int gateway_id) throws UnsupportedEncodingException{
		
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String Pe_Rurl = formatString(getPara("pe_Rurl",""));
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="BANALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="QQ813560690";
			BigDecimal a = new BigDecimal(p3_Amt);
			BigDecimal b = new BigDecimal(100);
			
			String total_fee = a.multiply(b).toString();
			String body = contact_us;			

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("attach", body);
			paramMap.put("cid", gateway_merid);// 商户支付Key			 
			paramMap.put("orderno", orderid);			 
			paramMap.put("title",body);//支付宝T0扫码支付				 
			paramMap.put("total_fee", total_fee);// 商品名称		 			
			paramMap.put("platform", "CR_ALI");

			///// 签名及生成请求API的方法///
			String sb=body+gateway_merid+orderid+body+total_fee+"CR_ALI";
			String sign = MD5.sign(sb, gateway_key, "UTF-8").toUpperCase();
			//String sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),gateway_key, "UTF-8").toUpperCase();
			paramMap.put("sign", sign);
			
			JSONObject json=new JSONObject(paramMap);
			String reqJsonStr = json.toJSONString();
			//System.out.println(reqUrl);
			//System.out.println(paramMap);
			//System.out.println("开始请求:" + DateUtil.formatDate(new Date(), "yyyy-MMdd HH:mm:ss SSS"));			
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			StringEntity entityParams = new StringEntity(reqJsonStr,"utf-8");
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				httpPost.setEntity(entityParams);
				httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){
					String resJson = new String(EntityUtils.toByteArray(response.getEntity()), "utf-8");		        	
					JSONObject jsonobject = JSONObject.parseObject(resJson);
					
					//String payUrl = java.net.URLDecoder.decode(js.get("payUrl").toString(),"UTF-8");
					String payUrl = jsonobject.get("code_url").toString();
					//System.out.println("提交参数payUrl=："+payUrl);					
					getResponse().sendRedirect(payUrl);		        	
				}else{
					renderText("系统异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(response != null){
						response.close();
					}
					if(client != null){
						client.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_xjpay(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			String json="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					json +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  
			System.out.println("req:"+json);
			JSONObject jsonobject = JSONObject.parseObject(json);
			String errcode = jsonobject.get("errcode").toString();
			String orderno = jsonobject.get("orderno").toString();
			String total_fee = jsonobject.get("total_fee").toString();
			String attach = jsonobject.get("attach").toString();
			String hmac = jsonobject.get("sign").toString();
			
			int gateway_id = 123;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			String sb=attach+errcode+orderno+total_fee;			
			String nhmac  = MD5.sign(sb, key, "UTF-8").toUpperCase();			
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(errcode != null && "0".equals(errcode)){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){	
						
						String r6_Order = orderno;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= r6_Order;//交易号
						String ro_BankOrderId = orderno;//第三方订单号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间		
						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);
						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						BigDecimal a = new BigDecimal(100);
						BigDecimal b = new BigDecimal(total_fee);
						String r3_Amt = b.divide(a).setScale(0).toString();
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="123";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("SUCCESS");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				//} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}
	}
	
	public void getway_mtdongli(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号	
		String payChannelId="";
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";
			payChannelId = "0000000006";
			orderid="MTDALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pd_FrpId = "wxwap";	
			payChannelId = "0000000007";
			orderid="MTDWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0dYM3DXkVg9q+WcNjBPWaUwKoeRMrwdE4p4F6fiztv/Ys6F5AxGCbFW5UfbtbQavMp9Rrg3+8mJ5/Lp8sjf471NFe6EvbCcVwJ63Q6fA4xVyCAE7mQdfAlpCk9WKN7Qa/HqwO/OM6JDyOyycnjnNi3f3K2tK/JbWd/SHYOSMEDQIDAQAB";//rsa加密
			String contact_us="QQ813560690";
			String subject = "游戏币";
			BigDecimal a = new BigDecimal(p3_Amt);
			BigDecimal b = new BigDecimal(100);			
			String amount = a.multiply(b).setScale(0).toString();
			String body = contact_us;
			String returnUrl = "http://netpay.fz222.com/web/Result/";
						
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("amount",amount);				
			paramMap.put("appid",gateway_merid);// 商户支付Key	
			paramMap.put("body",body);
			paramMap.put("clientIp",user_ip);			
			paramMap.put("mchntOrderNo",orderid);
			paramMap.put("payChannelId",payChannelId);			
			paramMap.put("notifyUrl",notify_url);
			paramMap.put("returnUrl",returnUrl);				 			
			paramMap.put("subject",subject);
			
			///// 签名及生成请求API的方法///
			//String preStr = SignUtils.formatUrlMap(map);			
			//sign  = SignUtils.sign(preStr, mch_key, "UTF-8").toUpperCase();
//			String sb = getSignParam2(paramMap);
//			System.out.println("计算签名的组合字符串：" + sb);
//			sb = getKeyedDigest(sb, gateway_key);
//			String signature = MD5.sign(sb, "&key="+gateway_key, "UTF-8");
			String signature=doEncrypt(paramMap,gateway_key);
			System.out.println("计算的签名：" + signature);
			//String sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),gateway_key, "UTF-8").toUpperCase();
			paramMap.put("signature", signature);
			
			//JSONObject json=new JSONObject(paramMap);
			String reqJsonStr=JSON.toJSONString(paramMap);
			reqJsonStr=reqJsonStr.trim();
			System.out.println("计算签名的报文为：" + reqJsonStr);
			String reqParams=null;
			try {
				//reqParams = Base64.encodeBase64String(RSAUtil.encryptByPublicKey(jsonObject.getBytes("utf-8"), PUBLIC_KEY));				
				byte[] str = encryptByPublicKeyByPKCS1Padding(reqJsonStr.getBytes("UTF-8"), PUBLIC_KEY);
				reqParams =org.apache.commons.codec.binary.Base64.encodeBase64String(str);
				System.out.println("加密后reqParams="+reqParams);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reqParams = replaceBlank(reqParams);
			//System.out.println("加密后reqParams="+reqParams);
			setAttr("reqParams",reqParams);
			setAttr("orderid",orderid);
			setAttr("p3_Amt",p3_Amt);
            try {
				this.getRequest().getRequestDispatcher("/WEB-INF/pay/must/Submit.jsp").forward(this.getRequest(), this.getResponse());
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				renderText("数据异常1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				renderText("数据异常2");
			}
			
			//System.out.println(reqUrl);
			//System.out.println(paramMap);
			//System.out.println("开始请求:" + DateUtil.formatDate(new Date(), "yyyy-MMdd HH:mm:ss SSS"));			
			//String returnString = MysendPost(reqUrl,reqParams);
			//System.out.println("returnString=:"+returnString);
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_mtdl(){

		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			String json="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					json +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}			
			//JSONObject jsonobject = JSONObject.parseObject(json);
			System.out.println("接收的回调参数是1:"+json);

			HashMap<String,Object> map = (HashMap)JSON.parseObject(json, HashMap.class);
			System.out.println("接收的回调参数是2:"+map);			
			String orderno = formatString(map.get("mchntOrderNo").toString());
			String errcode = formatString(map.get("paySt").toString());
			String outTransactionId = formatString(map.get("outTransactionId").toString());	
			String total_fee = formatString(map.get("amount").toString());
			String td_orderno = formatString(map.get("orderNo").toString());//通道方定号
			System.out.println("接收的回调参数=:orderno=:"+orderno+"--errcode=:"+errcode+"---outTransactionId=:"+outTransactionId+"---total_fee="+total_fee+"---td_orderno=:"+td_orderno);	
									
			String hmac = (String)map.get("signature");
			
			int gateway_id = 124;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
					
			String nhmac  =doEncrypt(map, key);			
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(errcode != null && "2".equals(errcode)){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){	
						
						String r6_Order = orderno;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= td_orderno;//交易号
						String ro_BankOrderId = outTransactionId;//第三方订单号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间		
						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);
						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						BigDecimal a = new BigDecimal(100);
						BigDecimal b = new BigDecimal(total_fee);
						String r3_Amt = b.divide(a).setScale(0).toString();
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						System.out.println("生成len=："+len);
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1014";
						String r7_Uid="124";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("{\"success\":\"true\"}");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("{\"success\":\"true\"}");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				//} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	public void getway_mtdongli2(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号	
		String paytype="";
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";
			paytype = "22";
			orderid="MTDALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pd_FrpId = "wxwap";	
			paytype = "21";
			orderid="MTDWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="QQ813560690";
			String subject = "游戏币";
			BigDecimal a = new BigDecimal(p3_Amt);
			BigDecimal b = new BigDecimal(100);			
			String amount = a.multiply(b).setScale(0).toString();
			String body = contact_us;			
			
			String appid = gateway_merid;
			String tongbu_url = notify_url;
			String clientip = user_ip;
			String back_url = "http://netpay.fz222.com/web/Result/";
			String sfrom = "wap";
			String cpparam = "QQ813560690";
			
			String str = appid+orderid+amount+tongbu_url;			
			String sign= MD5.sign(str, gateway_key, "UTF-8");					
			 String result = "";
	         result += reqUrl;
	         result += "?appid=" + appid;
	         result += "&orderid=" + orderid;
	         result += "&subject=" + subject;
	         result += "&body=" + body;
	         result += "&fee=" + amount;	         
	         result += "&tongbu_url=" + tongbu_url;	         
	         result += "&clientip=" + clientip;	         
	         result += "&back_url=" + back_url;
	         result += "&sfrom=" + sfrom;
	         result += "&paytype=" + paytype;
	         result += "&cpparam=" + cpparam;
	         result += "&sign=" + sign;	 
	         HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(301100);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				try {
					hClient.executeMethod(post);
					returnStr = post.getResponseBodyAsString();
					JSONObject jb = JSONObject.parseObject(returnStr);
					String succ = jb.getString("code");
					String url = jb.getString("msg");
					if(succ!=null && succ.equals("success")){
						redirect(url);
					}else{
						renderText("网络忙，稍后再试");
					}
				} catch (HttpException e) {
					renderText("网络忙，稍后再试");
					e.printStackTrace();
				} catch (IOException e) {
					renderText("网络忙，稍后再试");
					e.printStackTrace();
				}			 

		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_mtdl2() throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");
		String result_code = getPara("result");
		String paytype = getPara("paytype");
		String tradetime = getPara("tradetime");		
		String cpparam = getPara("cpparam");	//平台流水号
		String r3_Amt = getPara("fee");		//支付金额
		String hmac = getPara("sign");
		String r6_Order =getPara("orderid");	//商户订单号		
		//System.out.println("服务器返回给平台的数字签名是====："+hmac);
		System.out.println("银行返回来的商户订单号===："+r6_Order);

		//查询订单列表中对应的订单号状态  
		String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+r6_Order+"'";
		System.out.println("sql=:"+sql);
		List<Order> bb = Order.dao.find(sql.toString());
		Order o = bb.get(0);        
		String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
		int  r1_Code1=o.getInt("r1_Code");        //支付结果         
		String UserCallback = o.get("p8_Url");    //取得用户的回传地址
		String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
		int user_merId = o.getInt("p1_MerId");
		String user_key = Payment.dao.findById(user_merId).getStr("key");
		int gateway_id = o.getInt("gateway_id");		
		System.out.println("商户使用通道信息：gateway_id=："+gateway_id);
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String mch_key = gateway.getStr("gateway_key");

		if(r1_Code1==1){//防止服务器重复发送消息
			renderText("OK");
		}else{			
			//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
			//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);			
			//String ro_BankOrderId = "";
			String r2_TrxId= r6_Order;//交易号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf.format(new Date());						
			System.out.println("生成的rp_PayDate=："+rp_PayDate);			
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			BigDecimal a = new BigDecimal(r3_Amt);
			BigDecimal b = new BigDecimal(100);			
			BigDecimal amt = new BigDecimal(0);	
			amt = a.divide(b).setScale(2);

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1014";
			String r7_Uid="124";
			String r8_MP="charge";
			String r9_BType="1";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += r9_BType;        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名			
			//System.out.println("==返回时==生成的客户数字签名=："+rehmac);

			String result = "";
			result += UserCallback;
			result += "?r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=" + r9_BType;
			result += "&rp_PayDate="+ rp_PayDate;
			result += "&hmac=" + rehmac;        
			System.out.println("传递的URL=："+result);

			String sb001 = r6_Order + result_code + r3_Amt + tradetime;
 
			String nhmac = MD5.sign(sb001, mch_key, "UTF-8");//平台数据签名
			//System.out.println("服务器返回给平台的数字签名是==："+hmac);
			//System.out.println("游戏平台自己计算的数字签名是==："+nhmac);

			if (nhmac.equals( hmac) && result_code.equals("1")) {           	
				//支付成功,请处理自己的逻辑 请注意通知可能会多次 请避免重复到帐
				//Date date2 = new Date();
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//String ru_Trxtime= sdf2.format(date2);    //交易结果通知时间                     
				//System.out.println("充值成功后更新数据库参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				CibService cibservice = new CibService();   
				if(cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id)){						
					System.out.println("***充值成功，更新数据库成功***");
				}else{
					System.out.println("***充值成功，更新数据库失败***");
				}
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(301100);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
					Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
					if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
					}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
					}
				}else{
					System.out.println(r6_Order+"异步通知成功");
					//renderText("充值成功");
					renderText("OK");
				}
				
			}else {      
				System.out.print("签名失败");
				renderText("fail");
			}
		}
	} 	
	
	public void getway_bbnpay(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="BNNALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			orderid="BNNWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;			
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="QQ813560690";			
			String body = contact_us;
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			
			String appid = gateway_merid;
			int money=Integer.parseInt(a.multiply(b).setScale(0).toString());
			int goodsid=4441;//593;
			String goodsname=body;
			String pcorderid=orderid;
			String currency="CHY";			
			String pcuserid="6228480128578643677";
			String pcprivateinfo=String.valueOf(gateway_id);
			String notifyurl=notify_url;
			String signtype="MD5";
			String sign="";

			Map<String, String> paramMap = new HashMap<String, String>();
			 paramMap.put("appid", appid);// 商户支付Key
			 paramMap.put("goodsid", String.valueOf(goodsid));			 
			 paramMap.put("goodsname", goodsname);			 
			 paramMap.put("pcorderid",pcorderid);				 
			 paramMap.put("money", String.valueOf(money));	 
			 paramMap.put("currency", currency);
			 paramMap.put("pcuserid", pcuserid);
			 paramMap.put("pcprivateinfo", pcprivateinfo);
			 paramMap.put("notifyurl", notifyurl);			 
			///// 签名及生成请求API的方法///
			sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),"&key="+gateway_key, "UTF-8");
			//paramMap.put("sign", sign);
			//paramMap.put("signtype", signtype);
			//System.out.println(reqUrl);
			//System.out.println(paramMap);
			//System.out.println("开始请求:" + DateUtil.formatDate(new Date(), "yyyy-MMdd HH:mm:ss SSS"));		
						
//			String data ="{\"appid\":"+appid;  
//			data += ",\"goodsid\":"+goodsid;			
//			data += ",\"goodsname\":"+goodsname;
//			data += ",\"pcorderid\":"+pcorderid;
//			data += ",\"money\":"+money;
//			data += ",\"currency\":"+currency;
//			data += ",\"pcuserid\":"+pcuserid;
//			data += ",\"notifyurl\":"+notifyurl;
//			data += "}";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appid",appid);// 商户支付Key
			map.put("goodsid",goodsid);			 
			map.put("goodsname",goodsname);			 
			map.put("pcorderid",pcorderid);				 
			map.put("money", money);// 商品名称			 
			map.put("currency",currency);
			map.put("pcuserid",pcuserid);
			map.put("pcprivateinfo",pcprivateinfo);	
			map.put("notifyurl",notifyurl);				
			JSONObject json=new JSONObject(map);
			String data = json.toJSONString().trim();
			
			//System.out.println("请求的JSON=："+data);
			String transdata = java.net.URLEncoder.encode(data,"UTF-8");			
			String baowen= "transdata="+transdata+"&sign="+sign+"&signtype="+signtype;
			System.out.println("请求的数据URL=："+baowen);			
			String res = new String(MysendPost(reqUrl, baowen).getBytes("UTF-8"),"UTF-8");
			res = java.net.URLDecoder.decode(res,"UTF-8");
			System.out.println("返回结果=："+res);
			String[] strs = res.split("&");
			
			Map<String, String> datas = new HashMap<String, String>();
			for(int i=0;i<strs.length;i++){
				String[] s = strs[i].split("=");
				datas.put(s[0], s[1]);
				System.out.println(s[0]+":"+s[1]);
			}			
			//{"code":200,"transid":"0005931501838002095563859910"}
			String td = datas.get("transdata");
			//System.out.println("transdata结果=："+td);
			
			JSONObject obj = JSONObject.parseObject(td);
			String code = obj.get("code").toString();
			String transid = obj.get("transid").toString();
			//System.out.println("code结果=："+code);
			//System.out.println("transid结果=："+transid);
			
			//String tsign = datas.get("sign");
			String tsigntype = datas.get("signtype");
			//String transid = jsonobject.getString("transid");
			Map<String, Object> req = new HashMap<String, Object>();
			Map<String, String> req2 = new HashMap<String, String>();
			if(code.equals("200")){		
				String backurl="http://user.haofpay.com/web/Result/";
				req.put("appid", "7062017080459345");
				req.put("transid",transid);
				req.put("paytype","1");				
				req.put("backurl",backurl);	
				
				req2.put("appid", "7062017080459345");
				req2.put("transid",transid);
				req2.put("paytype","1");
				req2.put("backurl",backurl);	
				//req.put("sign", tsign);
				//req.put("signtype", tsigntype);
			}
			String sign2 = SignUtils.sign(SignUtils.formatUrlMap(req2),"&key="+gateway_key, "UTF-8");
			System.out.println("计算的数字签名sign2=："+sign2);
			if(req!=null){
				JSONObject j=new JSONObject(req);
				String m = j.toJSONString().trim();			
				//m = java.net.URLEncoder.encode(m,"UTF-8");
				//String result="https://payh5.bbnpay.com/browserh5/paymobile.php?";
				String result="https://payh5.bbnpay.com/h5pay/way.php?";
				result += "data="+m+"&sign="+sign2+"&signtype="+tsigntype;
				System.out.println("结果result=："+result);
				//result = java.net.URLEncoder.encode(m,"UTF-8");
				try {
					this.getResponse().sendRedirect(result);
				} catch (IOException e) {
					renderText("服务器通讯失败");
				}				
			}
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_bbnpay(){
		String transdata = getPara("transdata");
		String sign = getPara("sign");  //商户ID
		String signtype = getPara("signtype");	//商户订单号
		System.out.println("接收的回调参数transdata=:"+transdata);
		JSONObject js = JSONObject.parseObject(transdata);
		
		try{
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (HashMap<String, Object>)JSONObject.parseObject(transdata, HashMap.class);
			System.out.println("接收的回调参数是2:"+map);
			
			String transtype = map.get("transtype").toString();
			String cporderid = formatString(map.get("cporderid").toString());//商户订单号
			String transid = formatString(map.get("transid").toString());	 //交易流水号
			String pcuserid = formatString(map.get("pcuserid").toString());  
			String appid = formatString(map.get("appid").toString());//通道方定号
			String goodsid = formatString(map.get("goodsid").toString());
			String feetype = formatString(map.get("feetype").toString());
			String money = formatString(map.get("money").toString());			
			String fact_money = formatString(map.get("fact_money").toString());
			String currency = formatString(map.get("currency").toString());
			String result1 = formatString(map.get("result").toString());
			String transtime = formatString(map.get("transtime").toString());
			String pc_priv_info = formatString(map.get("pc_priv_info").toString());
			String paytype = formatString(map.get("paytype").toString());												
			String hmac = sign;
			//map.remove("pc_priv_info");
			System.out.println("cporderid=："+cporderid);
			System.out.println("fact_money=："+fact_money);
			System.out.println("money=："+money);
			System.out.println("transid=："+transid);
			System.out.println("result=："+result1);
			
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("transtype", transtype);
			map2.put("cporderid", cporderid);
			map2.put("transid", transid);
			map2.put("pcuserid", pcuserid);
			map2.put("appid", appid);
			map2.put("goodsid", goodsid);
			map2.put("feetype", feetype);
			map2.put("money", money);
			map2.put("fact_money", fact_money);
			map2.put("currency", currency);
			map2.put("result", result1);
			map2.put("transtime", transtime);
			map2.put("pc_priv_info", pc_priv_info);
			map2.put("paytype", paytype);
			int gateway_id = 125;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			//System.out.println("key=："+key);
			String nhmac  = SignUtils.sign(SignUtils.formatUrlMap(map2),"&key="+key, "UTF-8");	

			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(Integer.parseInt(money)==Integer.parseInt(fact_money) && result1.equals("1")){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){	
						
						String r6_Order = cporderid;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= transid;//交易号
						String ro_BankOrderId = transid;//第三方订单号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间		
						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);
						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						BigDecimal a = new BigDecimal(100);
						BigDecimal b = new BigDecimal(fact_money);
						String r3_Amt = b.divide(a).setScale(0).toString();
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						System.out.println("生成len=："+len);
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1014";
						String r7_Uid="124";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("SUCCESS");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			//}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	public void getway_shft(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="SFTALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			orderid="SFTWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4)+gateway_id;			
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String contact_us="QQ813560690";			
			String body = contact_us;			
			
			String Name = "B2CPayment";			
			String Version="V4.1.1.1.1";			
			String Charset="UTF-8";
			String TraceNo="17d44d01-ce5e-4f58-9d26-6ad17bbe307d";			
			String MsgSender=gateway_merid;
			String SendTime=sdf2.format(new Date());
			String OrderNo=orderid;
			String OrderAmount=p3_Amt;//单位：元			
			String OrderTime=sdf2.format(new Date());;
			String Currency="CNY";
			String PayType="PT312";
			String PayChannel="ha";
			String PageUrl="http://vip.haofpay.com/web/Result/";
			String NotifyUrl=notify_url;
			String SignType="MD5";
			String ProductName = body;
			//String wap_url="http://www.fz222.com";
			//String wap_name="688Game";
			String Ext2 = "{'requestFrom':'','app_name':'','bundle_id':'','package_name':'','wap_url':'http://vip.haofpay.com','wap_name':'688GAME','note':'fans','attach':'GOOD'}";
			String SignMsg="";			
			String text = Name+Version+Charset+TraceNo+MsgSender+SendTime+OrderNo+OrderAmount+OrderTime+Currency+PayType+PayChannel+PageUrl+NotifyUrl+ProductName+Ext2+SignType;
			SignMsg = SignUtils.sign(text,gateway_key, Charset).toUpperCase();
			//System.out.println("组合字符串"+text);
			//System.out.println("SignMsg"+SignMsg);
//			String baowei="";
//			baowei="Name="+Name;
//			baowei +="&Version="+Version;
//			baowei +="&Charset="+Charset;
//			baowei +="&TraceNo="+TraceNo;
//			baowei +="&MsgSender="+MsgSender;
//			
//			baowei +="&SendTime="+SendTime;
//			baowei +="&OrderNo="+OrderNo;
//			baowei +="&OrderAmount="+OrderAmount;
//			baowei +="&OrderTime="+OrderTime;
//			baowei +="&Currency="+Currency;
//			
//			baowei +="&PayType="+PayType;
//			baowei +="&PayChannel="+PayChannel;
//			baowei +="&PageUrl="+PageUrl;
//			baowei +="&NotifyUrl="+NotifyUrl;
//			baowei +="&SignType="+SignType;
//			
//			baowei +="&ProductName="+ProductName;
//			baowei +="&Ext2="+Ext2;
//			baowei +="&SignMsg="+SignMsg;
//			baowei = java.net.URLEncoder.encode(baowei,"UTF-8");
//			String str = MysendPost(reqUrl,baowei);
//			str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
//			renderHtml(str);			
//			System.out.println("str=:"+str);
			System.out.println("-----传递参数----");
			setAttr("Name",Name);
			setAttr("Version",Version);
			setAttr("Charset",Charset);			
			setAttr("TraceNo",TraceNo);
			setAttr("MsgSender",MsgSender);
			
			setAttr("SendTime",SendTime);
			setAttr("OrderNo",OrderNo);
			setAttr("OrderAmount",OrderAmount);
			setAttr("OrderTime",OrderTime);
			setAttr("Currency",Currency);
			
			setAttr("PayType",PayType);
			setAttr("PayChannel",PayChannel);
			setAttr("PageUrl",PageUrl);
			setAttr("NotifyUrl",NotifyUrl);			
			setAttr("SignType",SignType);
			
			setAttr("ProductName",ProductName);
			setAttr("Ext2",Ext2);
			setAttr("SignMsg",SignMsg);			
			renderJsp("/WEB-INF/pay/must/shft.jsp");
			//renderJsp("/WEB-INF/pay/must/");
			//this.getRequest().getRequestDispatcher("/WEB-INF/pay/must/").forward(this.getRequest(), this.getResponse());
			
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_shft(){
		System.out.println("*************************");
		String Name = getPara("Name");
		String Version = getPara("Version");
		String Charset = getPara("Charset");
		String TraceNo = getPara("TraceNo");
		String MsgSender = getPara("MsgSender");
		String SendTime = getPara("SendTime");
		String InstCode = getPara("InstCode");
		System.out.println("Name="+Name);
		
		String OrderNo = getPara("OrderNo");
		String OrderAmount = getPara("OrderAmount");		
		
		String TransNo = getPara("TransNo");//交易号
		String TransAmount = getPara("TransAmount");  //实际交易金额
		String TransStatus = getPara("TransStatus");	//支付状态
		String TransType = getPara("TransType");
		String TransTime = getPara("TransTime");
		
		String MerchantNo = getPara("MerchantNo");
		String ErrorCode = getPara("ErrorCode");
		String ErrorMsg = getPara("ErrorMsg");  //商户ID
		String Ext1 = getPara("Ext1");
		String Ext2 = getPara("Ext2");
		
		String SignType = getPara("SignType");  //商户ID
		String signMsg = getPara("SignMsg");	
		try{										
			String hmac = signMsg;			
			int gateway_id = 126;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			String text = Name+Version+Charset+TraceNo+MsgSender+SendTime+InstCode+OrderNo+OrderAmount+ TransNo+TransAmount+TransStatus+TransType+TransTime+MerchantNo+ErrorCode+ErrorMsg +Ext1+Ext2+SignType;		
			String nhmac  =SignUtils.sign(text, key, "UTF-8").toUpperCase();			
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(TransStatus != null && "01".equals(TransStatus)){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){	
						
						String r6_Order = OrderNo;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= TransNo;//交易号
						String ro_BankOrderId = TraceNo;//请求序列号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);						
						String ru_Trxtime=TransTime;//交易结果记录时间
						String r3_Amt = TransAmount;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						System.out.println("生成len=："+len);
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1014";
						String r7_Uid="124";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("OK");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("OK");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				//} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	/*********************同乐支付********************************/
	public void getway_tongle(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		System.out.println("生成的客户签名是："+nhmac);
		System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "TLEALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "TLEWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("weixin") || pd_FrpId.equals("weixin")){
				//同乐qq钱包
				pd_FrpId = "weixin";//微信通道编码
				orderid = "TLEQQ" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "TLEWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String trade_type="010002";
			//同乐qq钱包
			if(pd_FrpId.equals("weixin") || pd_FrpId.equals("weixin")){
				trade_type="010006";
			}else if(pd_FrpId.equals("alipaywap")){
				trade_type="010008";
			}
			String user_id="321321";
			String sys_callback = "http://user.ehaopay.net/web/Result/";//地址
			
			String note = "game-coin";
			String subject=note;
			Map<String,String> map = new HashMap<String, String>();				
			
			JSONObject js = new JSONObject();
			if(pd_FrpId.equals("alipaywap")){				
				js.put("mch_app_id", "http://user.ehaopay.net");
				js.put("device_info", "iOS_WAP");
				js.put("ua", "Mozilla/5.0 (Linux; U; Android 6.0.1; zh-CN; Redmi 3S Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.4.5.937 Mobile Safari/537.36");
				js.put("mch_app_name", "游戏平台");				
			}
			String biz_content= js.toString();			
			//System.out.println("业务JSON111=："+biz_content);
			biz_content=java.net.URLEncoder.encode(biz_content,"UTF-8");	
			
			biz_content = com.zzzhifu.sign.Base64.encode((biz_content.getBytes()));
			//System.out.println("业务JSON222=："+biz_content);
			map.put("biz_content",biz_content);
			map.put("body", note);      //商品描述	
			map.put("charset", "UTF-8");
			map.put("merchant_id", mch_id);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("notify_url", notify_url);
			map.put("out_trade_no", orderid);
			map.put("return_url", sys_callback);   //前台显示地址	
			map.put("subject", subject);
			map.put("total_fee", p3_Amt);
			map.put("trade_type", trade_type);
			map.put("user_id", user_id);
			map.put("user_ip", user_ip);
			map.put("version", "2.0");
			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8").toUpperCase();
			//计算sign			
			new_map.put("sign", sign);
			map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				String res = XmlUtils.parseXML3(map);
				
				System.out.println("发送的XML包："+res);
				StringEntity entityParams = new StringEntity(res,"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					String result_code = formatString(resultMap.get("result_code"));
					String status = formatString(resultMap.get("status"));		
					if(status.equals("") && !status.equals("0")){
						renderText("充值线路繁忙，请稍稍再试"+status);
					}else{							
						if("4".equals(result_code)){													
						String pay_info = formatString(resultMap.get("pay_info"));		
							if(!pay_info.equals("")){
								redirect(pay_info);									
							}else{
								renderText("网络繁忙，请稍后再试！");
							}									
						}else{
							renderText(formatString(resultMap.get("message")));
						}						
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_tongle(){
		System.out.println("同乐支付回调");
		String merchant_id = getPara("merchant_id");
		String out_trade_no = getPara("out_trade_no");
		String trade_no = getPara("trade_no");
		String subject = getPara("subject");
		String body = getPara("body");
		String total_fee = getPara("total_fee");
		String trade_time = getPara("trade_time");		
		String trade_status = getPara("trade_status");		
		String trade_result = getPara("trade_result");		
		String sign = getPara("sign");	
		try{										
			String hmac = sign;			
			int gateway_id = 127;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			Map<String,String> map = new HashMap<String, String>();
			map.put("body", body);
			map.put("merchant_id",merchant_id);
			map.put("out_trade_no", out_trade_no);
			map.put("subject", subject);
			map.put("total_fee", total_fee);
			map.put("trade_no", trade_no);				
			map.put("trade_result", trade_result);
			map.put("trade_status", trade_status);			
			map.put("trade_time", trade_time);	
			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8").toUpperCase();
						
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			System.out.println("微信或者支付宝传递回的sign=："+hmac);
			System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(trade_status != null && "1".equals(trade_status)){
					if(trade_result.equals("SUCCESS")){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){							
						String r6_Order = out_trade_no;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= trade_no;//交易号
						String ro_BankOrderId = trade_no;//请求序列号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);						
						String ru_Trxtime=trade_time;//交易结果记录时间
						String r3_Amt = total_fee;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="127";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?";
						result += "r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;
						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("SUCCESS");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				 } 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	public void getway_sand(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		this.getResponse().setCharacterEncoding("UTF-8");												

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = getPara("hmac");//数据签名		
		if(pr_NeedResponse.equalsIgnoreCase("")){
			pr_NeedResponse="1";
		}
		String str = "?";
		str += "p0_Cmd="+p0_Cmd;
		str += "&p1_MerId="+p1_MerId;
		str += "&p2_Order="+p2_Order;
		str += "&p3_Amt="+p3_Amt;
		str += "&p4_Cur="+p4_Cur;
		str += "&p5_Pid="+p5_Pid;
		str += "&p6_Pcat="+p6_Pcat;
		str += "&p7_Pdesc="+p7_Pdesc;
		str += "&p8_Url="+p8_Url;
		str += "&p9_SAF="+p9_SAF;
		str += "&pa_MP="+pa_MP;
		str += "&pd_FrpId="+pd_FrpId;
		str += "&pr_NeedResponse="+pr_NeedResponse;
		str += "&hmac="+hmac;	
		String reqUrl = "http://vip2.51mmk.com/pay/getway/getway_sandRedirect"+str;
		System.out.println("提交的URL："+reqUrl);
		HttpClient hClient = new HttpClient(); 
		HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(301100);
		PostMethod post = null;
		post = new PostMethod(reqUrl);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		//String returnStr = "";		
		try {
			hClient.executeMethod(post);
			//returnStr = post.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	/* *****小谢微信对接（sand）*************/
	public void getway_sandRedirect() throws IOException {
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											
		int id=129;
		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "SNDALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "SNDWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "wxpaywap";//默认为微信通道
				orderid = "SNDWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String agencyId = mch_key; 
			String merid = mch_id;			
			
			Order order = new Order();
			Date date = new Date();
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", hmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();	
			
			String outOrderId = orderid;
			String totalAmount= p3_Amt;					
			String paytype="01";
			String subject = "GameCoin";
			String spbillCreateIp  = user_ip;
			//String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqDtgUtVhGXbmgWpDZSy6ZV/RaASx0d40tscRgBQacZA4S4kP7S+J9Nyy92WnqKDrqW6AjeCAiBR1T5+X4hPNd6IC51L7r55xgsDN4rRuwaJyGT6CgfdZIuO03Wev2CGpc7KY4Z4Ryz7a02sJFKCAB1ElixHrQRCTQi9daOvETXAgMBAAECgYAR7dV2x+ST5vNR+Id9usj1DXeWJRlAz4K1zIo7tu4bTtMC4pFEahSAU6K4s94homCYGAP/4rC/jDBe7AXi0EXgSmCVuNoDLlpqze1HkDa9Oe0WRa56pbTtd9th/Tse/b1H2eWSDHnhPWJsF046P32kKXKm6F1MZs34+y1YgtjMIQJBANZBR5O3lnjA8nRLO5MeIYGwOurmw94+y4wi1gjltnouATNrR6lYFGunZQkgA2qLuv/swB8klHvKl+eAUtaJx2cCQQDLvLhj1pORshXOCw/yA4OAyc/u20N7S4lFAOItdoJTz7LaG4YAWGBkX1co1bTtkcHCBFVnD63TfnEYfSRPuGERAkBX/OMKNODkokhvnd3PYxZbjiYEBdT2Vk99M2k2qi+wKWhw12PMldF9DHefsbf1b4DSTUXxBDK+S8rqVXaviFGNAkAV0sU9jIKKHLVROMYgelfft75aK4py7ohpp8qSbBtRtvHFgyU7bDwHBF9ltF6JBA/pJGWxgHByMx0SLnVxRKLBAkEAmn5gPPJd2Wd1bVYuA+6QZafY65l3d7Yczkn1jHWN6nCr6kw65rGwMbwaK5OsmeepBffag19Gv8zRaysGnY2oDw==";			
			//String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqg7YFLVYRl25oFqQ2UsumVf0WgEsdHeNLbHEYAUGnGQOEuJD+0vifTcsvdlp6ig66lugI3ggIgUdU+fl+ITzXeiAudS+6+ecYLAzeK0bsGichk+goH3WSLjtN1nr9ghqXOymOGeEcs+2tNrCRSggAdRJYsR60EQk0IvXWjrxE1wIDAQAB";			
			agencyId = "888002189990010";
			merid = "666666879930014";
			
			String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqDtgUtVhGXbmgW" +
                    "pDZSy6ZV/RaASx0d40tscRgBQacZA4S4kP7S+J9Nyy92WnqKDrqW6AjeCAiBR1T5" +
                    "+X4hPNd6IC51L7r55xgsDN4rRuwaJyGT6CgfdZIuO03Wev2CGpc7KY4Z4Ryz7a02" +
                    "sJFKCAB1ElixHrQRCTQi9daOvETXAgMBAAECgYAR7dV2x+ST5vNR+Id9usj1DXeW" +
                    "JRlAz4K1zIo7tu4bTtMC4pFEahSAU6K4s94homCYGAP/4rC/jDBe7AXi0EXgSmCV" +
                    "uNoDLlpqze1HkDa9Oe0WRa56pbTtd9th/Tse/b1H2eWSDHnhPWJsF046P32kKXKm" +
                    "6F1MZs34+y1YgtjMIQJBANZBR5O3lnjA8nRLO5MeIYGwOurmw94+y4wi1gjltnou" +
                    "ATNrR6lYFGunZQkgA2qLuv/swB8klHvKl+eAUtaJx2cCQQDLvLhj1pORshXOCw/y" +
                    "A4OAyc/u20N7S4lFAOItdoJTz7LaG4YAWGBkX1co1bTtkcHCBFVnD63TfnEYfSRP" +
                    "uGERAkBX/OMKNODkokhvnd3PYxZbjiYEBdT2Vk99M2k2qi+wKWhw12PMldF9DHef" +
                    "sbf1b4DSTUXxBDK+S8rqVXaviFGNAkAV0sU9jIKKHLVROMYgelfft75aK4py7ohp" +
                    "p8qSbBtRtvHFgyU7bDwHBF9ltF6JBA/pJGWxgHByMx0SLnVxRKLBAkEAmn5gPPJd" +
                    "2Wd1bVYuA+6QZafY65l3d7Yczkn1jHWN6nCr6kw65rGwMbwaK5OsmeepBffag19G" +
                    "v8zRaysGnY2oDw==";
            String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqg7YFLVYRl25oFqQ2UsumVf0W" +
                    "gEsdHeNLbHEYAUGnGQOEuJD+0vifTcsvdlp6ig66lugI3ggIgUdU+fl+ITzXeiAu" +
                    "dS+6+ecYLAzeK0bsGichk+goH3WSLjtN1nr9ghqXOymOGeEcs+2tNrCRSggAdRJY" +
                    "sR60EQk0IvXWjrxE1wIDAQAB";						
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("notify_url",notify_url);
			map.put("outOrderId",outOrderId);
			map.put("subject",subject);
			map.put("totalAmount",totalAmount);
			map.put("paytype",paytype);
			map.put("merid",merid);
			map.put("spbillCreateIp",spbillCreateIp); 

            Map<String,Object> m1=new HashMap<String,Object>();
            Map<String,Object> m2=new HashMap<String,Object>();
            m2.put("type","Wap");
            m2.put("wap_url","http://vip1.51mmk.com");
            m2.put("wap_name","OrderNO:"+orderid);
            m1.put("h5_info",m2);
            map.put("sceneInfo",m1);
            JSONObject js=new JSONObject(map);
            String json=js.toJSONString();
            System.out.println("组合的JSON是："+json);           						
			try {
				//签名前原文
	            String text= Md5Util.MD5(agencyId+json);
	            //System.out.println("----------text："+text);
	            String sign= SignUtil.sign(text,privateKey);
	            sign = replaceBlank(sign);
	            //System.out.println("----------sign："+sign);
	            sign = java.net.URLEncoder.encode(sign,"UTF-8");
	            String url = reqUrl + "?agencyId=" + agencyId + "&sign=" + sign;
	            //System.out.println("提交的URL带参数是："+url);
	            HttpPost httppost = new HttpPost(url);
	
	            InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(json.getBytes()));
	            reqEntity.setContentType("application/json;charset=UTF-8");
	            httppost.setEntity(reqEntity);
	            CloseableHttpResponse response = null;
	            String result =null;
	            try {
	                response = HttpClients.custom().build().execute(httppost, HttpClientContext.create());
	                HttpEntity entity = response.getEntity();
	                result = EntityUtils.toString(entity, "utf-8");
	                System.out.println(result);
	                
	                Map<String, Object> resMap = (Map<String,Object>)JSON.parseObject(result);
	                String code = (String)resMap.get("code");
					String msg = (String) resMap.get("msg");
					System.out.println("返回的msg==="+msg);
					// 获取data字符串					
					String resData = (String) resMap.get("data");
					// 获取sign签名串				
					String resSign = (String) resMap.get("sign");					
					// 还原签名原文
					String resText = Md5Util.MD5(agencyId + resData);
					// 开始对返回的数据进行验签
					Boolean b;
					try {
						b = SignUtil.verify(resSign, resText, publicKey);
						if (b) {							
							JSONObject res_json = JSONObject.parseObject(resData);							
							if(code.equals("0000")){
								System.out.println("URL获取成功"+res_json.get("mwebUrl"));
								String dirc_url = (String)res_json.get("mwebUrl");
								setAttr("direct_url",dirc_url);
								renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
							}							
						} else {
							System.out.println("验签不通过");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}		
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if (response != null){
	                        response.close();
	                    }


	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }	            

			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
			}
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_sand(){
		System.out.println("杉德支付回调");
		String status=getPara("status");
		String merId = getPara("merId");		
		//String buyerId = getPara("buyerId");
		//String buyerAccount = getPara("buyerAccount");
		String totalAmount = getPara("totalAmount");
		String outOrderId = getPara("outOrderId");
		String orderId = getPara("orderId");		
		String channelNo = getPara("channelNo");				
		String sign = getPara("sign");	
			
		try{										
			String hmac = sign;			
			int gateway_id = 129;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("status",status);
			//params.put("buyerId",buyerId);//买家编号
			//params.put("buyerAccount",buyerAccount);
			params.put("totalAmount",totalAmount);
			params.put("outOrderId",outOrderId);//银行订单号
			params.put("orderId",orderId);//合作方订单号
			params.put("channelNo",channelNo);//渠道流水号
			params.put("merId",merId);
			
			String urlstr=SignUtil.getUrlParamsByMap(SignUtil.sortMapByKey(params));
			System.out.println("排序后的Text:"+urlstr);
			urlstr =Md5Util.MD5(urlstr);			
			//Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			//StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			//SignUtils.buildPayParams(sb,new_map,false);
			//String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8").toUpperCase();						
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);
			
			String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqg7YFLVYRl25oFqQ2UsumVf0W" +
                    "gEsdHeNLbHEYAUGnGQOEuJD+0vifTcsvdlp6ig66lugI3ggIgUdU+fl+ITzXeiAu" +
                    "dS+6+ecYLAzeK0bsGichk+goH3WSLjtN1nr9ghqXOymOGeEcs+2tNrCRSggAdRJY" +
                    "sR60EQk0IvXWjrxE1wIDAQAB";
			if(!SignUtil.verify(sign, urlstr,publicKey)){	
				System.out.println("验签不通过");//验签不通过
			}else{
				if(status != null && "0000".equals(status)){
					if(true){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){							
						String r6_Order = outOrderId;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= channelNo;//交易号
						String ro_BankOrderId = orderId;//请求序列号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间						
						//System.out.println("生成的rp_PayDate=："+rp_PayDate);						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = totalAmount;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="127";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?";
						result += "r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;
						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
							System.out.println("数据存储成功");
						}else{
							renderText("success");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("success");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				 }else{
					 renderText("fail");
					 System.out.println("返回状态错误："+status); 
				 }
			}
		} catch (Exception e) {
			renderText("fail");
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	/* *****李枫微信对接（杉德）*************/
	public void getway_sand2(int id) throws IOException {
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "SD2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "SD2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "wxpaywap";//默认为微信通道
				orderid = "SD2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String agencyId = mch_key; 
			String merid = mch_id;			
			
			Order order = new Order();
			Date date = new Date();
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", hmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();	
			
			String outOrderId = orderid;
			String totalAmount= p3_Amt;					
			String paytype="01";
			String subject = "GameCoin";
			String spbillCreateIp  = user_ip;
			//String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqDtgUtVhGXbmgWpDZSy6ZV/RaASx0d40tscRgBQacZA4S4kP7S+J9Nyy92WnqKDrqW6AjeCAiBR1T5+X4hPNd6IC51L7r55xgsDN4rRuwaJyGT6CgfdZIuO03Wev2CGpc7KY4Z4Ryz7a02sJFKCAB1ElixHrQRCTQi9daOvETXAgMBAAECgYAR7dV2x+ST5vNR+Id9usj1DXeWJRlAz4K1zIo7tu4bTtMC4pFEahSAU6K4s94homCYGAP/4rC/jDBe7AXi0EXgSmCVuNoDLlpqze1HkDa9Oe0WRa56pbTtd9th/Tse/b1H2eWSDHnhPWJsF046P32kKXKm6F1MZs34+y1YgtjMIQJBANZBR5O3lnjA8nRLO5MeIYGwOurmw94+y4wi1gjltnouATNrR6lYFGunZQkgA2qLuv/swB8klHvKl+eAUtaJx2cCQQDLvLhj1pORshXOCw/yA4OAyc/u20N7S4lFAOItdoJTz7LaG4YAWGBkX1co1bTtkcHCBFVnD63TfnEYfSRPuGERAkBX/OMKNODkokhvnd3PYxZbjiYEBdT2Vk99M2k2qi+wKWhw12PMldF9DHefsbf1b4DSTUXxBDK+S8rqVXaviFGNAkAV0sU9jIKKHLVROMYgelfft75aK4py7ohpp8qSbBtRtvHFgyU7bDwHBF9ltF6JBA/pJGWxgHByMx0SLnVxRKLBAkEAmn5gPPJd2Wd1bVYuA+6QZafY65l3d7Yczkn1jHWN6nCr6kw65rGwMbwaK5OsmeepBffag19Gv8zRaysGnY2oDw==";			
			//String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqg7YFLVYRl25oFqQ2UsumVf0WgEsdHeNLbHEYAUGnGQOEuJD+0vifTcsvdlp6ig66lugI3ggIgUdU+fl+ITzXeiAudS+6+ecYLAzeK0bsGichk+goH3WSLjtN1nr9ghqXOymOGeEcs+2tNrCRSggAdRJYsR60EQk0IvXWjrxE1wIDAQAB";			
			agencyId = "888002189990025";
			merid = "666666879930066";
			
			String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALQNKJ/FIjMKOY10"+
				"4HbIGF++6S5Nuw7qBYgEK9NKSYjQmSYoMsVlg1wNhbFdxzehC3iQpKQHLFWI0N5+"+
				"zcv1th5shzVsWMOxNpLQVslKLMN7TPJcvEAjP97Wwmi7LpRjCmFr18NC6saXvOip"+
				"lNQHXJeu8jXd7Yefw6mytv5pVJztAgMBAAECgYEAs89EV7J+gexfJ4amN9mChfPZ"+
				"WTWlbrKFTZ2kysCwiSKRH4sbkQjc04xpjITz0/lPxFrnISxMuSsGNHz8NwkukqyR"+
				"8tOccuVfp1vX87F5eqU9ov3W6mpez48JOMTsMTjRay7lBknekLhQPGsmh5037ivP"+
				"5Ek9aygakS3mvDp1/QECQQDd1M+GjqAjVTn6zJRlbiiPQDCenKbudK9ztXSMTyBO"+
				"89B+hqNHzn54218onsLWJzv8BC7RFTte+1uFcEdjOfgtAkEAz8jlJXmBpQiqdsix"+
				"l7I/mkuw5ew3fUyZcD6oRO3835ETj2X/5E+t/5/1fEWGbk2TiiocXvV/x8V07Pny"+
				"zjlvwQJAUCY3ARgBNiLx+vL13xwJHZEEwBo7mkagqf7y6EsikkQ3na+JyUULnyEr"+
				"5aJPJU8xtiHQxIyWUgAhljn2IRzt1QJAPYEiyvfvdzFce/KmFtgNWZfEEhrcxfsR"+
				"hBcX6edkLa0c1BqGJ0eqonrvyEwwIaBW5tPWOAmFPFXnaWoHBCIeQQJBAKdAWuWe"+
				"iRGHvy4IpbRFl3mW251DcELdFb98/vUXJHOcO8PLpimKNI/t/nrNqiegTSh64lDf"+
				"1Olcm+08fSAo1zo=";
            String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0DSifxSIzCjmNdOB2yBhfvuku"+
				"TbsO6gWIBCvTSkmI0JkmKDLFZYNcDYWxXcc3oQt4kKSkByxViNDefs3L9bYebIc1"+
				"bFjDsTaS0FbJSizDe0zyXLxAIz/e1sJouy6UYwpha9fDQurGl7zoqZTUB1yXrvI1"+
				"3e2Hn8Opsrb+aVSc7QIDAQAB";						
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("notify_url",notify_url);
			map.put("outOrderId",outOrderId);
			map.put("subject",subject);
			map.put("totalAmount",totalAmount);
			map.put("paytype",paytype);
			map.put("merid",merid);
			map.put("spbillCreateIp",spbillCreateIp); 

            Map<String,Object> m1=new HashMap<String,Object>();
            Map<String,Object> m2=new HashMap<String,Object>();
            m2.put("type","Wap");
            m2.put("wap_url","http://vip.ehaopay.com");
            m2.put("wap_name","OrderNO:"+orderid);
            m1.put("h5_info",m2);
            map.put("sceneInfo",m1);
            JSONObject js=new JSONObject(map);
            String json=js.toJSONString();
            System.out.println("组合的JSON是："+json);           						
			try {
				//签名前原文
	            String text= Md5Util.MD5(agencyId+json);
	            System.out.println("----------text："+text);
	            String sign= SignUtil.sign(text,privateKey);
	            sign = replaceBlank(sign);
	            System.out.println("----------sign："+sign);
	            sign = java.net.URLEncoder.encode(sign,"UTF-8");
	            String url = reqUrl + "?agencyId=" + agencyId + "&sign=" + sign;
	            System.out.println("提交的URL带参数是："+url);
	            HttpPost httppost = new HttpPost(url);
	
	            InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(json.getBytes()));
	            reqEntity.setContentType("application/json;charset=UTF-8");
	            httppost.setEntity(reqEntity);
	            CloseableHttpResponse response = null;
	            String result =null;
	            try {
	                response = HttpClients.custom().build().execute(httppost, HttpClientContext.create());
	                HttpEntity entity = response.getEntity();
	                result = EntityUtils.toString(entity, "utf-8");
	                System.out.println(result);
	                
	                Map<String, Object> resMap = (Map<String,Object>)JSON.parseObject(result);
	                String code = (String)resMap.get("code");
					String msg = (String) resMap.get("msg");
					System.out.println("返回的msg==="+msg);
					// 获取data字符串					
					String resData = (String) resMap.get("data");
					// 获取sign签名串				
					String resSign = (String) resMap.get("sign");					
					// 还原签名原文
					String resText = Md5Util.MD5(agencyId + resData);
					// 开始对返回的数据进行验签
					Boolean b;
					try {
						b = SignUtil.verify(resSign, resText, publicKey);
						if (b) {							
							JSONObject res_json = JSONObject.parseObject(resData);							
							if(code.equals("0000")){
								System.out.println("URL获取成功"+res_json.get("mwebUrl"));
								String dirc_url = (String)res_json.get("mwebUrl");
								setAttr("direct_url",dirc_url);
								renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
							}							
						} else {
							System.out.println("验签不通过");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}		
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if (response != null){
	                        response.close();
	                    }


	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }	            

			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
			}
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_sand2(){
		System.out.println("杉德支付回调");
		String status=getPara("status");
		String merId = getPara("merId");		
		//String buyerId = getPara("buyerId");
		//String buyerAccount = getPara("buyerAccount");
		String totalAmount = getPara("totalAmount");
		String outOrderId = getPara("outOrderId");
		String orderId = getPara("orderId");		
		String channelNo = getPara("channelNo");				
		String sign = getPara("sign");	
			
		try{										
			String hmac = sign;			
			int gateway_id = 130;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");	
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("status",status);
			//params.put("buyerId",buyerId);//买家编号
			//params.put("buyerAccount",buyerAccount);
			params.put("totalAmount",totalAmount);
			params.put("outOrderId",outOrderId);//银行订单号
			params.put("orderId",orderId);//合作方订单号
			params.put("channelNo",channelNo);//渠道流水号
			params.put("merId",merId);
			
			String urlstr=SignUtil.getUrlParamsByMap(SignUtil.sortMapByKey(params));
			System.out.println("排序后的Text:"+urlstr);
			urlstr =Md5Util.MD5(urlstr);			
			//Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			//StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			//SignUtils.buildPayParams(sb,new_map,false);
			//String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8").toUpperCase();						
			//nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);
			
            String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0DSifxSIzCjmNdOB2yBhfvuku"+
				"TbsO6gWIBCvTSkmI0JkmKDLFZYNcDYWxXcc3oQt4kKSkByxViNDefs3L9bYebIc1"+
				"bFjDsTaS0FbJSizDe0zyXLxAIz/e1sJouy6UYwpha9fDQurGl7zoqZTUB1yXrvI1"+
				"3e2Hn8Opsrb+aVSc7QIDAQAB";		
			if(!SignUtil.verify(sign, urlstr,publicKey)){	
				System.out.println("验签不通过");//验签不通过
			}else{
				if(status != null && "0000".equals(status)){
					if(true){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){							
						String r6_Order = outOrderId;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= channelNo;//交易号
						String ro_BankOrderId = orderId;//请求序列号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间						
						//System.out.println("生成的rp_PayDate=："+rp_PayDate);						
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = totalAmount;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="127";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						//result += "?p1_MerId=" + user_merId;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;
						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
							System.out.println("数据存储成功");
						}else{
							renderText("success");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("success");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				 }else{
					 renderText("fail");
					 System.out.println("返回状态错误："+status); 
				 }
			}
		} catch (Exception e) {
			renderText("fail");
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	/**********小谢微信对接（平安）*************/
	public void getway_pingan(int id) throws IOException {
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
				return;
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);			
			String pmt_tag = "";

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "PINGALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				pmt_tag = "AlipayPAZH";
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "PINGWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				pmt_tag = "Weixin";
			}else{
				pd_FrpId = "wxpaywap";//默认为微信通道
				orderid = "PINGWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				pmt_tag = "Weixin";
			}
			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String agencyId = mch_key; 
			String merid = mch_id;			
			
			Order order = new Order();
			Date date = new Date();
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", hmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();
			
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			BigDecimal amt = a.multiply(b).setScale(0);
			
			String open_id = TestParams.OPEN_ID;
			String timestamp = new Date().getTime() / 1000 + "";    // 时间		    
		    String out_no = orderid;
		    String original_amount = amt.toString();
		    String trade_amount = amt.toString();
		    //String notify_url = notify_url;
		    String pmt_name = "有疑问，请联系客服";
		    String ord_name="GameCoin";
		 // 初始化参数        

	        try {
	            // 固定参数
	            TreeMap<String, String> postmap = new TreeMap<String, String>();    // 请求参数的map
	            postmap.put("open_id", open_id);
	            postmap.put("timestamp", timestamp);
	            
	            TreeMap<String, String> datamap = new TreeMap<String, String>();    // data参数的map
	            datamap.put("out_no", out_no);
	            datamap.put("pmt_tag", pmt_tag);
	            datamap.put("pmt_name", pmt_name);
	            datamap.put("ord_name", ord_name);
	            datamap.put("original_amount", original_amount+"");	            
	            datamap.put("trade_amount", trade_amount+"");	            
	            datamap.put("notify_url", notify_url);            
	            /**
	             * 1 data字段内容进行AES加密，再二进制转十六进制(bin2hex)
	             */
	            TLinx2Util.handleEncrypt(datamap, postmap);
	            /**
	             * 2 请求参数签名 按A~z排序，串联成字符串，先进行sha1加密(小写)，再进行md5加密(小写)，得到签名
	             */
	            TLinx2Util.handleSign(postmap);
	            /**
	             * 3 请求、响应
	             */
	            String rspStr = TLinx2Util.handlePost(postmap, TestParams.PAYORDER);
	            /**
	             * 4 验签  有data节点时才验签
	             */
	            //JSONObject respObject = JSONObject.fromObject(rspStr);
	            JSONObject respObject = JSONObject.parseObject(rspStr);
	            Object dataStr    = respObject.get("data");
	            
	            if (!rspStr.isEmpty() && ( dataStr != null )) {
	                if (TLinx2Util.verifySign(respObject)) {    // 验签成功

	                    /**
	                     * 5 AES解密，并hex2bin
	                     */
	                    String respData = TLinxAESCoder.decrypt(dataStr.toString(), TestParams.OPEN_KEY);
	                    
	                    JSONObject respObj = JSONObject.parseObject(respData);
	                    
	                    String trade_qrcode = (String) respObj.get("trade_qrcode");
	                    
	                    redirect(trade_qrcode);
	                } else {
	                    System.out.println("=====验签失败=====");
	                }
	            } else {
	                System.out.println("=====没有返回data数据=====");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
            
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_pingan(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");						
			Map<String, String> params  = new TreeMap<String, String>();

	        // 取出所有参数是为了验证签名
	        Enumeration<String> parameterNames = this.getRequest().getParameterNames();

	        while (parameterNames.hasMoreElements()) {
	            String parameterName = parameterNames.nextElement();
	            params.put(parameterName, this.getRequest().getParameter(parameterName));
	        }
	        JSONObject paramsObject = (JSONObject) JSON.toJSON(params);
	        boolean flag = TLinx2Util.verifySign(paramsObject);
	        System.out.println("===========回调参数是：" + paramsObject.toString());			
			int gateway_id = 131;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");
			
			String ord_no = paramsObject.getString("ord_no");
			String out_no = paramsObject.getString("out_no");			
			String status = paramsObject.getString("status");
			String amount = paramsObject.getString("amount");		

			if(!flag){
				System.out.println("数字签名错误！"); 
			}else{
				if(status != null && "1".equals(status)){							
					if(amount!=null){
						String r6_Order = out_no;//订单号
						String r2_TrxId= ord_no;//交易号						
						//String ro_BankOrderId = orderNo;//第三方订单号
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf.format(new Date());
						System.out.println("回调的订单号是=："+r6_Order);
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = amount;	
						BigDecimal a = new BigDecimal(100);
						BigDecimal b = new BigDecimal(r3_Amt);
						
						r3_Amt = b.divide(a).setScale(2).toString();//分转成元的单位
						
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="1016";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("notify_success");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("notify_success");
						}																								
					}else{
						System.out.println("返回金额有误！");
					}
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_zhaohang(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        
		
		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="Z"+sdf.format(new Date());
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			orderid="Z"+sdf.format(new Date());			
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		Boolean flag = order.save();	
		if(flag){
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String merchantOutOrderNo=orderid;
			String merid = gateway_merid;			
			String noncestr=String.valueOf(date.getTime());
			String notifyUrl=notify_url;
			BigDecimal orderMoney=new BigDecimal(p3_Amt).setScale(2);
			String orderTime= sdf2.format(date);			
			System.out.println("gateway_merid=:"+gateway_merid);
			String sign="";
			Map<String, String> paramMap = new HashMap<String, String>();
			 paramMap.put("merid", merid);// 商户支付Key
			 paramMap.put("merchantOutOrderNo", merchantOutOrderNo);			 
			 paramMap.put("noncestr", noncestr);			 
			 paramMap.put("notifyUrl",notifyUrl);				 
			 paramMap.put("orderMoney", orderMoney.toString());	 
			 paramMap.put("orderTime", orderTime);			 			 
			///// 签名及生成请求API的方法///
			System.out.println("sign=:"+1111111111);
			sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),"&key="+gateway_key, "UTF-8");
			System.out.println("sign=:"+sign);
			
			String result = "http://jh.yizhibank.com/api/alipayAppOrder?";
			result += "merchantOutOrderNo="+merchantOutOrderNo;
			result += "&merid="+merid;
			result += "&noncestr="+noncestr;
			result += "&notifyUrl="+notifyUrl;
			result += "&orderMoney="+orderMoney;
			result += "&orderTime="+orderTime;		
			result += "&sign=" + sign;  
			System.out.println("提交的URL:"+result);
			getResponse().sendRedirect(result);
		}else{
			System.out.println("客户签名正确");
			renderText("签名失败");
		}
		}else{
			renderText("订单号重复，请重新发起请求");
		}
	}		
	
	public void callback_zhaohang(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");	
			String merchantOutOrderNo = getPara("merchantOutOrderNo");
			String merid = getPara("merid");
			String msg = getPara("msg");			
			String noncestr = getPara("noncestr");
			String orderNo = getPara("orderNo");			
			String payResult = getPara("payResult");
			String sign = getPara("sign");			
			
			int gateway_id = 128;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");
			 Map<String, String> paramMap = new HashMap<String, String>();
			 paramMap.put("merchantOutOrderNo", merchantOutOrderNo);
			 paramMap.put("merid", merid);// 商户支付Key			 			 
			 paramMap.put("msg", msg);			 
			 paramMap.put("noncestr",noncestr);				 
			 paramMap.put("orderNo", orderNo);	 
			 paramMap.put("payResult", payResult);			 			 
			///// 签名及生成请求API的方法///
			String s_sign = SignUtils.sign(SignUtils.formatUrlMap(paramMap),"&key="+key, "UTF-8");

			if(!sign.equals(s_sign)){
				System.out.println("数字签名错误！"); 
			}else{
				if(payResult != null && "1".equals(payResult)){	
					JSONObject jsonObject= JSONObject.parseObject(msg);
					String total_fee=jsonObject.getString("payMoney");		
					if(total_fee!=null){
						String r6_Order = merchantOutOrderNo;//订单号

						String r2_TrxId= orderNo;//交易号						
						//String ro_BankOrderId = orderNo;//第三方订单号
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf.format(new Date());

						System.out.println("生成的r6_Order=："+r6_Order);

						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = total_fee;					
						
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="1016";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					}else{
						System.out.println("返回金额有误！");
					}
				} 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_duobao(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号	
		String cardno="";
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";	
			cardno="44";
			orderid="DBALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		} else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pd_FrpId = "wxwap";	
			cardno="41";
			orderid="DBWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");			
			String body = "游戏道具购买";
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			
			
			String customerid=gateway_merid;
			String sdcustomno=orderid;
			String orderAmount=a.multiply(b).setScale(0).toString();
			String noticeurl=notify_url;
			String backurl="http://netpay.fz222.com/web/Result"; // 页面通知返回url
			String sign="";
			String mark= "GameCharge";
			String remarks=body;
			String Zftype="1";
			
			String Md5str="customerid="+customerid+"&sdcustomno="+sdcustomno+"&orderAmount="+orderAmount+"&cardno="+cardno+"&noticeurl=" +noticeurl+"&backurl="+backurl;
			sign = SignUtils.sign(Md5str,gateway_key,"UTF-8").toUpperCase();
			String result = reqUrl+"?";
			result += "customerid="+customerid;
			result += "&sdcustomno="+sdcustomno;
			result += "&orderAmount="+orderAmount;
			result += "&cardno="+cardno;			
			result += "&noticeurl="+noticeurl;
			result += "&backurl="+backurl;			 
			result += "&mark="+mark;
			result += "&remarks="+remarks; 
			result += "&Zftype="+Zftype; 	
			result += "&sign="+sign;
			//System.out.println("提交的URL:"+result);
			try {
				getResponse().sendRedirect(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_duobao(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			System.out.println("支付宝WAP-多宝通回调接收");
			
			String state = getPara("state");
			String customerid = getPara("customerid");
			String sd51no = getPara("sd51no");
			String sdcustomno = getPara("sdcustomno");
			String ordermoney = getPara("ordermoney");
			String cardno = getPara("cardno");
			String mark = getPara("mark");
			String hmac = getPara("sign");
			String resign = getPara("resign");
			String des = getPara("des");			
			//System.out.println("服务器返回给平台的数字签名是====："+hmac);
			//System.out.println("银行返回来的商户订单号==========："+r6_Order);

			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+sdcustomno+"'";
			System.out.println("sql=:"+sql);
			List<Order> bb = Order.dao.find(sql.toString());
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");
			int gateway_id = 115;
			
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");

			if(r1_Code1==1){//防止服务器重复发送消息
				renderText("<result>1</result>");
			}else{	
				
				//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
				//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
				String sbOld ="customerid="+mch_id+"&sd51no="+sd51no+"&sdcustomno="+sdcustomno+"&mark="+mark;				
				//根据返回数据加工计算客户的数字签名Hmac
				String nhmac = SignUtils.sign(sbOld,"&key="+mch_key,"UTF-8").toUpperCase(); //平台数据签名
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
				String rp_PayDate=sdf2.format(new Date());			
				String r0_Cmd="Buy";
				String r1_Code=state;
				String r2_TrxId = sdcustomno;//订单流水号
				String r3_Amt = ordermoney;
				String r4_Cur="CNY";
				String r5_Pid=mark;
				String r6_Order=sdcustomno;
				String r7_Uid="115";//通道id
				String r8_MP="charge";				

				String  ssb= "";
				ssb += user_merId;
				ssb += r0_Cmd;
				ssb += r1_Code;
				ssb += r2_TrxId;
				ssb += r3_Amt;
				ssb += r4_Cur;
				ssb += r5_Pid;
				ssb += user_r6_order;
				ssb += r7_Uid;//商品描述
				ssb += r8_MP;
				ssb += "1";        
				ssb += rp_PayDate;
				String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
				//给客户的回调URL，带参数
				String result = "";
				result += UserCallback;
				result += "?r0_Cmd=" + r0_Cmd;
				result += "&r1_Code=" + r1_Code;
				result += "&r2_TrxId=" + r2_TrxId;
				result += "&r3_Amt=" + r3_Amt;
				result += "&r4_Cur=" + r4_Cur;
				result += "&r5_Pid=" + r5_Pid;
				result += "&r6_Order=" + user_r6_order;
				result += "&r7_Uid=" + r7_Uid;
				result += "&r8_MP=" + r8_MP;
				result += "&r9_BType=1";
				result += "&rp_PayDate=" +rp_PayDate;
				result += "&hmac=" + rehmac;
				//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				System.out.println("充值成功回调给客户的URL=："+result);
				CibService cibservice = new CibService(); 
				if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					System.out.println("数据库保存状态=："+flag);
					if (nhmac.equals(hmac)) {  
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						try {
							hClient.executeMethod(post);
							returnStr = post.getResponseBodyAsString();
						} catch (HttpException e) {				
							e.printStackTrace();
						} catch (IOException e) {				
							e.printStackTrace();
						}			
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
						}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
						}
					}else{
					System.out.println(r6_Order+"同步通知成功");
					renderText("<result>1</result>");
					}				
				}else {      
					System.out.print("签名失败");
					renderText("<result>签名失败</result>");
				}
			}else{
				renderText("<result>1</result>");
			}
		 }			
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}
	}
	
	public void getway_duobao2(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号	
		String cardno="";
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";	
			cardno="44";
			orderid="DB2ALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		} else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pd_FrpId = "wxwap";	
			cardno="41";
			orderid="DB2WX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");			
			String body = "游戏道具购买";
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			
			
			String customerid=gateway_merid;
			String sdcustomno=orderid;
			String orderAmount=a.multiply(b).setScale(0).toString();
			String noticeurl=notify_url;
			String backurl="http://netpay.fz222.com/web/Result"; // 页面通知返回url
			String sign="";
			String mark= "GameCharge";
			String remarks=body;
			String Zftype="1";
			
			String Md5str="customerid="+customerid+"&sdcustomno="+sdcustomno+"&orderAmount="+orderAmount+"&cardno="+cardno+"&noticeurl=" +noticeurl+"&backurl="+backurl;
			sign = SignUtils.sign(Md5str,gateway_key,"UTF-8").toUpperCase();
			String result = reqUrl+"?";
			result += "customerid="+customerid;
			result += "&sdcustomno="+sdcustomno;
			result += "&orderAmount="+orderAmount;
			result += "&cardno="+cardno;			
			result += "&noticeurl="+noticeurl;
			result += "&backurl="+backurl;			 
			result += "&mark="+mark;
			result += "&remarks="+remarks; 
			result += "&Zftype="+Zftype; 	
			result += "&sign="+sign;
			//System.out.println("提交的URL:"+result);
			try {
				getResponse().sendRedirect(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_duobao2(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			System.out.println("支付宝WAP-多宝通回调接收");
			
			String state = getPara("state");
			String customerid = getPara("customerid");
			String sd51no = getPara("sd51no");
			String sdcustomno = getPara("sdcustomno");
			String ordermoney = getPara("ordermoney");
			String cardno = getPara("cardno");
			String mark = getPara("mark");
			String hmac = getPara("sign");
			String resign = getPara("resign");
			String des = getPara("des");			
			//System.out.println("服务器返回给平台的数字签名是====："+hmac);
			//System.out.println("银行返回来的商户订单号==========："+r6_Order);

			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+sdcustomno+"'";
			System.out.println("sql=:"+sql);
			List<Order> bb = Order.dao.find(sql.toString());
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");
			int gateway_id = 130;
			
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");

			if(r1_Code1==1){//防止服务器重复发送消息
				renderText("<result>1</result>");
			}else{	
				
				//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
				//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
				String sbOld ="customerid="+mch_id+"&sd51no="+sd51no+"&sdcustomno="+sdcustomno+"&mark="+mark;				
				//根据返回数据加工计算客户的数字签名Hmac
				String nhmac = SignUtils.sign(sbOld,"&key="+mch_key,"UTF-8").toUpperCase(); //平台数据签名
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
				String rp_PayDate=sdf2.format(new Date());			
				String r0_Cmd="Buy";
				String r1_Code=state;
				String r2_TrxId = sdcustomno;//订单流水号
				String r3_Amt = ordermoney;
				String r4_Cur="CNY";
				String r5_Pid=mark;
				String r6_Order=sdcustomno;
				String r7_Uid="130";//通道id
				String r8_MP="charge";				

				String  ssb= "";
				ssb += user_merId;
				ssb += r0_Cmd;
				ssb += r1_Code;
				ssb += r2_TrxId;
				ssb += r3_Amt;
				ssb += r4_Cur;
				ssb += r5_Pid;
				ssb += user_r6_order;
				ssb += r7_Uid;//商品描述
				ssb += r8_MP;
				ssb += "1";        
				ssb += rp_PayDate;
				String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
				//给客户的回调URL，带参数
				String result = "";
				result += UserCallback;
				result += "?r0_Cmd=" + r0_Cmd;
				result += "&r1_Code=" + r1_Code;
				result += "&r2_TrxId=" + r2_TrxId;
				result += "&r3_Amt=" + r3_Amt;
				result += "&r4_Cur=" + r4_Cur;
				result += "&r5_Pid=" + r5_Pid;
				result += "&r6_Order=" + user_r6_order;
				result += "&r7_Uid=" + r7_Uid;
				result += "&r8_MP=" + r8_MP;
				result += "&r9_BType=1";
				result += "&rp_PayDate=" +rp_PayDate;
				result += "&hmac=" + rehmac;
				//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				System.out.println("充值成功回调给客户的URL=："+result);
				CibService cibservice = new CibService(); 
				if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					System.out.println("数据库保存状态=："+flag);
					if (nhmac.equals(hmac)) {  
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						try {
							hClient.executeMethod(post);
							returnStr = post.getResponseBodyAsString();
						} catch (HttpException e) {				
							e.printStackTrace();
						} catch (IOException e) {				
							e.printStackTrace();
						}			
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
						}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
						}
					}else{
					System.out.println(r6_Order+"同步通知成功");
					renderText("<result>1</result>");
					}				
				}else {      
					System.out.print("签名失败");
					renderText("<result>签名失败</result>");
				}
			}else{
				renderText("<result>1</result>");
			}
		 }			
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}
	}
	
	public void getway_ahpay(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		//String Pe_Rurl = getPara("pe_Rurl","");
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		//sbOld += Pe_Rurl;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号	
		String cardno="";
		Date date = new Date();	
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";	
			cardno="44";
			orderid="AHPALI"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		} else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pd_FrpId = "wxwap";	
			cardno="41";
			orderid="AHPWX"+sdf.format(new Date())+CryptTool.getPasswordOfNumber(4);
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			//System.out.println("客户签名正确");			
			String body = "游戏道具购买";
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			
			
			String customerid=gateway_merid;
			String sdcustomno=orderid;
			String orderAmount=a.multiply(b).setScale(0).toString();
			String noticeurl=notify_url;
			String backurl="http://netpay.fz222.com/web/Result"; // 页面通知返回url
			String sign="";
			String mark= "GameCharge";
			String remarks=body;
			String Zftype="1";
			
			String Md5str="customerid="+customerid+"&sdcustomno="+sdcustomno+"&orderAmount="+orderAmount+"&cardno="+cardno+"&noticeurl=" +noticeurl+"&backurl="+backurl;
			sign = SignUtils.sign(Md5str,gateway_key,"UTF-8").toUpperCase();
			String result = reqUrl+"?";
			result += "customerid="+customerid;
			result += "&sdcustomno="+sdcustomno;
			result += "&orderAmount="+orderAmount;
			result += "&cardno="+cardno;			
			result += "&noticeurl="+noticeurl;
			result += "&backurl="+backurl;			 
			result += "&mark="+mark;
			result += "&remarks="+remarks; 
			result += "&Zftype="+Zftype; 	
			result += "&sign="+sign;
			//System.out.println("提交的URL:"+result);
			try {
				getResponse().sendRedirect(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_ahpay(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			System.out.println("支付宝WAP-多宝通回调接收");
			
			String state = getPara("state");
			String customerid = getPara("customerid");
			String sd51no = getPara("sd51no");
			String sdcustomno = getPara("sdcustomno");
			String ordermoney = getPara("ordermoney");
			String cardno = getPara("cardno");
			String mark = getPara("mark");
			String hmac = getPara("sign");
			String resign = getPara("resign");
			String des = getPara("des");			
			//System.out.println("服务器返回给平台的数字签名是====："+hmac);
			//System.out.println("银行返回来的商户订单号==========："+r6_Order);

			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+sdcustomno+"'";
			System.out.println("sql=:"+sql);
			List<Order> bb = Order.dao.find(sql.toString());
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");
			int gateway_id = 19;
			
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");

			if(r1_Code1==1){//防止服务器重复发送消息
				renderText("<result>1</result>");
			}else{	
				
				//BigDecimal old_r3_Amt = new BigDecimal(r3_Amt);
				//old_r3_Amt = old_r3_Amt.setScale(1,BigDecimal.ROUND_HALF_UP);
				String sbOld ="customerid="+mch_id+"&sd51no="+sd51no+"&sdcustomno="+sdcustomno+"&mark="+mark;				
				//根据返回数据加工计算客户的数字签名Hmac
				String nhmac = SignUtils.sign(sbOld,"&key="+mch_key,"UTF-8").toUpperCase(); //平台数据签名
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
				String rp_PayDate=sdf2.format(new Date());			
				String r0_Cmd="Buy";
				String r1_Code=state;
				String r2_TrxId = sdcustomno;//订单流水号
				String r3_Amt = ordermoney;
				String r4_Cur="CNY";
				String r5_Pid=mark;
				String r6_Order=sdcustomno;
				String r7_Uid="115";//通道id
				String r8_MP="charge";				

				String  ssb= "";
				ssb += user_merId;
				ssb += r0_Cmd;
				ssb += r1_Code;
				ssb += r2_TrxId;
				ssb += r3_Amt;
				ssb += r4_Cur;
				ssb += r5_Pid;
				ssb += user_r6_order;
				ssb += r7_Uid;//商品描述
				ssb += r8_MP;
				ssb += "1";        
				ssb += rp_PayDate;
				String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
				//给客户的回调URL，带参数
				String result = "";
				result += UserCallback;
				result += "?r0_Cmd=" + r0_Cmd;
				result += "&r1_Code=" + r1_Code;
				result += "&r2_TrxId=" + r2_TrxId;
				result += "&r3_Amt=" + r3_Amt;
				result += "&r4_Cur=" + r4_Cur;
				result += "&r5_Pid=" + r5_Pid;
				result += "&r6_Order=" + user_r6_order;
				result += "&r7_Uid=" + r7_Uid;
				result += "&r8_MP=" + r8_MP;
				result += "&r9_BType=1";
				result += "&rp_PayDate=" +rp_PayDate;
				result += "&hmac=" + rehmac;
				//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
				System.out.println("充值成功回调给客户的URL=："+result);
				CibService cibservice = new CibService(); 
				if(r1_Code1!=1){//匹配订单号，金额以银行回传金额为准
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					Boolean flag =cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					System.out.println("数据库保存状态=："+flag);
					if (nhmac.equals(hmac)) {  
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						try {
							hClient.executeMethod(post);
							returnStr = post.getResponseBodyAsString();
						} catch (HttpException e) {				
							e.printStackTrace();
						} catch (IOException e) {				
							e.printStackTrace();
						}			
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
						asynchronous = new Asynchronous();
						asynchronous.set("orderid", r6_Order);
						asynchronous.set("status", "N");
						asynchronous.set("url", result);
						asynchronous.save();
						}else{
						asynchronous.set("url",result);
						asynchronous.set("status", "N");
						asynchronous.update();
						}
					}else{
					System.out.println(r6_Order+"同步通知成功");
					renderText("<result>1</result>");
					}				
				}else {      
					System.out.print("签名失败");
					renderText("<result>签名失败</result>");
				}
			}else{
				renderText("<result>1</result>");
			}
		 }			
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}
	}
	
	/*********************新真扬支付********************************/
	public void getway_zhengyang(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "ZHYALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "ZHYWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "ZHYWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}		

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");			
			
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", nhmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();	
			
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);
			String area=mch_id;//商户号
			String area_notify_url=notify_url;
			String area_out_trade_no = orderid;
			String body="GameCoin";													
			String total_fee = a.multiply(b).setScale(0).toString();//元转分
			String callback_url = "http://vip.haofpay.com/web/Result/";//地址
			String attach = String.valueOf(date.getTime());
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("area",area);			
			//map.put("area_notify_url", area_notify_url);
			map.put("area_out_trade_no", area_out_trade_no);
			map.put("body", body);      //商品描述			
			map.put("total_fee", total_fee);
			//map.put("callback_url", callback_url);			
			//map.put("attach", attach);
			
			map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			String preStr = SignUtils.formatUrlMap(map);	
			//System.out.println("xxxxxxxx="+preStr+"&key="+mch_key);
			String sign  = SignUtils.sign(preStr, "&key="+mch_key, "UTF-8").toUpperCase();//字符串转换成大写;			
			System.out.println("计算的Sign=:"+sign);
			String result = reqUrl+"?";
			result += "area="+area;
			result += "&area_notify_url="+area_notify_url;
			result += "&area_out_trade_no="+area_out_trade_no;
			result += "&body="+body;			
			result += "&total_fee="+total_fee;
			result += "&callback_url="+callback_url;			 
			result += "&attach="+attach;		
			result += "&sign="+sign;
			System.out.println("提交的URL:"+result);
			try {
				getResponse().sendRedirect(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_zhengyang(){
		System.out.println("真扬支付回调");//SUCCESS 表示成功
		
		String result_code = getPara("result_code");
		String area_out_trade_no = getPara("area_out_trade_no");
		String out_transaction_id = getPara("out_transaction_id");
		String attach  = getPara("attach ");
		String total_fee = getPara("total_fee");
		String time_end = getPara("time_end");			
		String sign = getPara("sign");	
		try{										
			String hmac = sign;			
			int gateway_id = 132;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("result_code", result_code);
			map.put("area_out_trade_no",area_out_trade_no);
			map.put("out_transaction_id", out_transaction_id);
			map.put("attach", attach);
			map.put("total_fee", total_fee);
			map.put("time_end", time_end);			
						
			map = SignUtils.paraFilter(map);//过滤掉空值和sign			
			String preStr = SignUtils.formatUrlMap(map);			
			String nhmac  = SignUtils.sign(preStr, "&key="+key, "UTF-8").toUpperCase();//字符串转换成大写;
			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(result_code != null){
					if(result_code.equals("SUCCESS")){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));
					//if(data != null){							
						String r6_Order = area_out_trade_no;//订单号
						//String service = "alipaywap";//map.get("service");//交易类型
						String r2_TrxId= out_transaction_id;//交易号
						String ro_BankOrderId = out_transaction_id;//请求序列号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间						
						System.out.println("生成的rp_PayDate=："+rp_PayDate);						
						String ru_Trxtime=time_end;//交易结果记录时间
						BigDecimal a = new BigDecimal(100);
						BigDecimal b = new BigDecimal(total_fee);						
						String r3_Amt = b.divide(a).setScale(2).toString();//分转化为元						
						
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						//BigDecimal amt = new BigDecimal(r3_Amt);
					
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="127";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;
						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("success");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("success");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				 } 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	

	/********************金游支付********************************/
	public void getway_jinyou(int id) throws IOException {

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;
		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);

			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				orderid = "JYOALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				orderid = "JYOWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "JYOWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");			
			
			Date date = new Date();	
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", nhmac);			
			order.set("CreateTime",sdf2.format(date));
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();	
			
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(p3_Amt);	
			String total_fee = a.multiply(b).setScale(0).toString();//元转分
			
			
			String backEndUrl=notify_url;
			String merId=mch_id;//商户号
			String termId="WEB";
			String termIp=user_ip;
			String orderId = orderid;
			String orderTime = sdf2.format(date);
			String orderBody= orderid;
			String orderDetail = "wap_url=http://vip.haofpay.com&wap_name=宜宾邦购";
			String txnAmt = total_fee;
			String currencyType="156";
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("backEndUrl",backEndUrl);
			map.put("merId", merId);
			map.put("termId",termId);
			map.put("termIp",termIp);
			map.put("orderId",orderId);
			map.put("orderTime",orderTime);
			map.put("orderBody",orderBody);
			map.put("orderDetail",orderDetail);
			map.put("txnAmt",txnAmt);
			map.put("currencyType",currencyType);
			
			map = SignUtils.paraFilter(map);//过滤掉空值和sign
			String preStr = SignUtils.formatUrlMap(map);
			String signAture  = SignUtils.sign(preStr, "&key="+mch_key, "UTF-8").toUpperCase();//字符串转换成大写;
			map.put("signAture", signAture);
			
			JSONObject dataobj = (JSONObject) JSON.toJSON(map);
			String sendData = dataobj.toJSONString();
			
			String b64ReqStr = null;
			try {
				b64ReqStr = Base64.encode(sendData.getBytes("utf-8")).replaceAll("\\+", "#");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			//生成最后的报文
			String finalB64ReqStr = "sendData=" + b64ReqStr;
			String returnStr = MysendPost(reqUrl,finalB64ReqStr);
			//String returnStr = MysendPost(reqUrl,finalB64ReqStr);
			String respB64Str = returnStr.substring(9);
			//base64解码,并对一些特殊字符进行置换
			byte [] respJsBs = com.zzzhifu.sign.Base64.decode(respB64Str.replaceAll("#","+"));
			String respJsStr = null;
			try {
				respJsStr = new String(respJsBs,"utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}				
			//解析json
			JSONObject respJs =JSONObject.parseObject(respJsStr);				
			System.out.println("JSON=:"+respJs.toJSONString());
			String mwebUrl = respJs.getString("mwebUrl");
			String respCode = respJs.getString("respCode");				
			//String prepayid = respJs.getString("prepayid");
			if(respCode.equals("00")){
				redirect(mwebUrl);	
			}else{
				renderText("支付接口维护中");
			}
			
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_jinyou(){
		System.out.println("京游支付回调");//SUCCESS 表示成功
		String respB64Str = getPara("sendData");
		byte [] respJsBs = com.zzzhifu.sign.Base64.decode(respB64Str.replaceAll("#","+"));
		String respJsStr = null;
		try {
			respJsStr = new String(respJsBs,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		//解析json
		JSONObject respJs =JSONObject.parseObject(respJsStr);				
		System.out.println("JSON=:"+respJs.toJSONString());			
		
		String origOrderId = respJs.getString("origOrderId");
		String origTxnSeqId = respJs.getString("origTxnSeqId");
		String settleAmt = respJs.getString("settleAmt");		
		String signAture = respJs.getString("signAture");	
		//String transFlag = respJs.getString("transFlag");
		String transactionId = respJs.getString("transactionId");
		String txnState = respJs.getString("txnState");
		
		respJs.remove("signAture");		
		Map<String,String> map = (Map)respJs;	
				
		try{										
			String hmac = signAture;			
			int gateway_id = 133;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");						
			
			String result_code=txnState;
			String pd_FrpId="";		
			String r6_Order = origOrderId;//订单号
			
			String r2_TrxId= origTxnSeqId;//交易号
			String ro_BankOrderId = transactionId;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间						
			Date date = new Date();				
			String ru_Trxtime=sdf2.format(date);//交易结果记录时间
			BigDecimal a = new BigDecimal(100);
			BigDecimal b = new BigDecimal(settleAmt);						
			String r3_Amt = b.divide(a).setScale(2).toString();//分转化为元	
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();						
			Order o = bb.get(0); 
			
			pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");
			
			map = SignUtils.paraFilter(map);//过滤掉空值和sign			
			String preStr = SignUtils.formatUrlMap(map);			
			String nhmac  = SignUtils.sign(preStr, "&key="+key, "UTF-8").toUpperCase();//字符串转换成大写;
			
			System.out.println("接收的数字签名："+hmac);
			System.out.println("计算的数字签名："+nhmac);
			if(!hmac.equals(nhmac)){
				System.out.println("数字签名错误！"); 
			}else{
				if(result_code != null){
					if(result_code.equals("00")){
					//JSONObject data =(JSONObject) JSONObject.parse(map.get("data"));					
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="1013";
						String r7_Uid="133";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?p1_MerId=" + user_merId;
						result += "&r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;
						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}else{
							renderText("SUCCESS");
						}
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}							
						}else{
							renderText("SUCCESS");
							System.out.println(r6_Order+"同步通知成功");
							
						}						
					} 
				 } 
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			System.out.println("服务器故障，稍后再试！");
			e.printStackTrace();
		}	
	}
	
	public void gateway_yimadai(int gateway_id) throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码			
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");
		//System.out.println("客户的KEY值是："+key);
		//System.out.println("sbOld=："+sbOld);
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://688.haofpay.com/pay/callback_yeka";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();	
		String pm="";
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pm = "alipay";			
			orderid="YMD"+date.getTime();
		}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
			pm = "wxwap";			
			orderid="YM2"+date.getTime();
		}else{
			pm="alipay";
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		boolean flag = order.save();
		if(flag){
		  if (nhmac.equals( hmac)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String merchantOutOrderNo=orderid;
			String merid=gateway_merid;
			String noncestr=String.valueOf(date.getTime());
			String notifyUrl=notify_url;
			String orderMoney=p3_Amt;
			String orderTime=sdf2.format(date);			
			String sign="";
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchantOutOrderNo",merchantOutOrderNo);
			map.put("merid", gateway_merid);
			map.put("noncestr",noncestr);
			map.put("notifyUrl",notifyUrl);			
			map.put("orderMoney",orderMoney);
			map.put("orderTime",orderTime);
						
			map = SignUtils.paraFilter(map);//过滤掉空值和sign
			String preStr = SignUtils.formatUrlMap(map);
			sign  = SignUtils.sign(preStr, "&key="+gateway_key, "UTF-8");			
	        String result = "";
	        result += reqUrl;
	        result += "?merchantOutOrderNo=" + merchantOutOrderNo;
	        result += "&merid=" + merid;
	        result += "&noncestr=" + noncestr;
	        result += "&notifyUrl=" + notifyUrl;
	        result += "&orderMoney=" + orderMoney;
	        result += "&orderTime=" + orderTime;	         
	        result += "&sign=" + sign;	
	         
			 redirect(result);
			
		 }else{
			renderText("签名失败");
		 }
		}else{
			renderText("订单已存在，无法继续支付");
		}
	}
	
	public static String doEncrypt(Map<String, Object> map,String mchntKey) throws UnsupportedEncodingException {
		Object[] keys =  map.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder originStr = new StringBuilder();
		for(Object key:keys){
			if(null!=map.get(key)&&!map.get(key).toString().equals("")&&!"signature".equals(key))
			originStr.append(key).append("=").append(map.get(key)).append("&");
		}
		originStr.append("key=").append(mchntKey);
		String sign = DigestUtils.md5Hex(originStr.toString().getBytes("utf-8"));
		return sign;
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static String getSignParam(Map<String, String> params) {
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		buildPayParams(buf, params, false);
		String result = buf.toString();
		return result;
	}
	
	public static String getSignParam2(Map<String, Object> params) {
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		buildPayParams2(buf, params, false);
		String result = buf.toString();
		return result;
	}

	public static void buildPayParams(StringBuilder sb,
		Map<String, String> payParams, boolean encoding) {
		List<String> keys = new ArrayList<String>(payParams.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			sb.append(key).append("=");
			if (encoding) {
				try {
					sb.append(java.net.URLEncoder.encode(payParams.get(key), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {				
				sb.append(payParams.get(key));
			}
			sb.append("&");
		}
		sb.setLength(sb.length() - 1);
	}
	
	public static void buildPayParams2(StringBuilder sb,
			Map<String, Object> payParams, boolean encoding) {
			List<String> keys = new ArrayList<String>(payParams.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				sb.append(key).append("=");
				if (encoding) {
					try {
						sb.append(java.net.URLEncoder.encode(payParams.get(key).toString(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {				
					sb.append(payParams.get(key));
				}
				sb.append("&");
			}
			sb.setLength(sb.length() - 1);
		}
	
	public static String getKeyedDigest(String strSrc, String key) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF-8"));

			String result="";
			byte[] temp;
			temp=md5.digest(key.getBytes("UTF-8"));
			for (int i=0; i<temp.length; i++){
				result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}            
			return result;

		} catch (NoSuchAlgorithmException e) {            
			e.printStackTrace();

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String MysendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			//conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
			conn.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded; charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static String MysendPostXML(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Content-Type",  "application/xml; charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/*************威富通-自动生成（2000——4000）*******************/
	public void getway_swiftpass_model(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}
			//System.out.println("传给微信的值（单位分）："+r3_Amt);
			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			String order_tap = gateway.getStr("order_tap");
			
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				orderid = order_tap+"ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				orderid = order_tap+"WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.toLowerCase().equals("weixin")){//QQ钱包支付
				pd_FrpId = "pay.tenpay.wappay";
				orderid = order_tap+"QQ" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				renderText("通道未授权");
				return;
			}				

			String note = "客服微信:13728447023";
			Map<String,String> map = new HashMap<String, String>();
			map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			map.put("sign_type", "MD5");
			map.put("total_fee", r3_Amt.toString());
			map.put("version", "2.0");
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="iOS_WAP";
				String mch_app_name="游戏平台";
				String mch_app_id="http://vip.917xue.com";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String sign = MD5.sign(sb.toString(), "&key=" + mch_key, "utf-8");
			if(pd_FrpId.equals("pay.tenpay.wappay")){
				sign = sign.toUpperCase();
			}
			new_map.put("sign", sign);
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML2(new_map),"utf-8");				
				httpPost.setEntity(entityParams);				
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
					//res = XmlUtils.toXml(resultMap);
					System.out.println("服务器返回数据："+resultMap.toString());
					String code_url = formatString(resultMap.get("code_url"));
					//System.out.println("以重定向方式发送了CODE_URL="+code_url);
					if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
						renderText("充值线路繁忙，请稍稍再试");
					}else if(pd_FrpId.equals("pay.tenpay.wappay")){
						String sign_2 = resultMap.get("sign");
						resultMap.remove("sign");
			            StringBuilder buf = new StringBuilder((resultMap.size() +1) * 10);
			            SignUtils.buildPayParams(buf,resultMap,false);
			            String preStr = buf.toString();
			            String signRecieve = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			            			            
			            if(sign_2.equals(signRecieve)){
			            	if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
								String pay_info = formatString(resultMap.get("pay_info"));
								if(!pay_info.equals("")){										
									redirect(pay_info);								
								}else{
									renderText("网络繁忙，请稍后再试！");
								}									
							}else{
								renderText(formatString(resultMap.get("message")));
							}
			            }else{
			            	renderText("签名验证不通过");
			            }
					}else{
						if(resultMap.containsKey("sign")){
							if(!SignUtils.checkParam(resultMap, mch_key)){
								renderText("验证签名不通过");
							}else{
								if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
									String pay_info = formatString(resultMap.get("pay_info"));
									if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){										
										redirect(pay_info);
									}else if(pd_FrpId.equals("pay.alipay.native")){
										redirect(code_url);
									}else{
										renderText("网络繁忙，请稍后再试！");
									}									
								}else{
									renderText(formatString(resultMap.get("message")));
								}
							}
						}else{
							renderText("数字认证不通过");
						}
					}
				}else{
					renderText("操作失败");
					//res = "操作失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_swiftpass_model(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/xml");
			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			} 		
						
			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+map.get("out_trade_no")+"' limit 1";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			if(len>0){						
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			int gateway_id = o.getInt("gateway_id");
			String user_key = Payment.dao.findById(user_merId).getStr("key");			
			
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");
			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(hmac.equals(nhmac)){			
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("trade_type");//交易类型
						String r2_TrxId= map.get("transaction_id");//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						WftRateSave wftsave = new WftRateSave(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							wftsave.SwiftpassUpdate2(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}																								
					} 
				}else{
					System.out.println("数字签名错误！"); 
				}
			}else{
				System.out.println("数字签名错误！"); 
			}
			}else{
				renderText("订单不存在！"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/*********************宝希商务********************************/
	public void getway_baoxishangwu(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
		String hmac = formatString(getPara("hmac"));//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		//System.out.println("生成的客户签名是："+nhmac);
		//System.out.println("接受到的签名是=："+hmac);

		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			//BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}
			//System.out.println("传给微信的值（单位分）："+p3_Amt);
			String api_type="";
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "pay.alipay.native";
				api_type="Alipay_code";
				orderid = "BXIALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "pay.weixin.wappay";//微信通道编码
				api_type="wappay";
				orderid = "BXIWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				api_type="wappay";
				pd_FrpId = "pay.weixin.wappay";//默认为微信通道
				orderid = "BXIWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}			

			//String mch_id = SwiftpassConfig.mch_id;
			//String mch_key = SwiftpassConfig.key;
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			 
			String note = "QQ2353354300";
			Map<String,String> map = new HashMap<String, String>();
			map.put("api_type", api_type);
			//map.put("attch", p0_Cmd);     //附加信息
			map.put("body", note);      //商品描述
			String sys_callback = "http://user.haofpay.com/web/Result/";//地址
			map.put("callback_url", sys_callback);   //前台显示地址
			//map.put("charset", "UTF-8");			
			map.put("mch_create_ip", user_ip);			
			map.put("mch_id", mch_id);
			map.put("notify_url", notify_url);
			map.put("nonce_str", String.valueOf(new Date().getTime()));
			map.put("out_trade_no", orderid);
			map.put("service", pd_FrpId);			
			//map.put("sign_type", "MD5");
			map.put("total_fee", p3_Amt);
			map.put("version", "2.0");
			//map.put("key", mch_key);
			if(pd_FrpId.equals("pay.weixin.wappay")){
				String device_info="AND_SDK";
				String mch_app_name="GameCoin";
				String mch_app_id="com.tencent.tmgp.sgame";
				map.put("device_info", device_info);
				map.put("mch_app_name", mch_app_name);
				map.put("mch_app_id", mch_app_id);
			}			
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign			
			String sign = MD5.sign(SignUtils.formatUrlMap(new_map), "&key=" + mch_key, "utf-8");
			//计算sign			
			new_map.put("sign", sign.toUpperCase());			
			
			Date date = new Date();	
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",sdf2.format(date));
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			//String res = null;
			try {
				//System.out.println("reqUrl=:"+reqUrl);
				//System.out.println("new_map=:"+new_map.toString());
				String retrun_result ="["+MysendPostXML(reqUrl,XmlUtils.parseXML3(new_map))+"]";
				//System.out.println("req_xml=:"+XmlUtils.parseXML3(new_map));
				//System.out.println("retrun_result=:"+retrun_result);				
				
				JSONArray json =  JSONArray.parseArray((retrun_result));				
				JSONObject job = json.getJSONObject(0);								
				//String res_code = job.get("pay_info").toString();				
				//System.out.println("res_code====:"+res_code);
				Map<String,String> resultMap = (Map)job;
				//resultMap====:{"sign":"03B6FDC84912E4D27918CD1736000C5B","result_code":"0","mch_id":"102513832947","status":"0","sign_type":"MD5","charset":"UTF-8","pay_info":"https://statecheck.swiftpass.cn/pay/wappay?token_id=2c7744a87977365b781504a5c7f5a6cc1&service=pay.weixin.wappayv2","nonce_str":"1507979646932","version":"2.0"}
				//System.out.println("resultMap====:"+resultMap.toString());
				String code_url="";
				if(code_url.equals("") && pd_FrpId.equals("pay.alipay.native")){
					renderText("充值线路繁忙，请稍稍再试");
				}else{					
					if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))){													
						String pay_info = formatString(resultMap.get("pay_info"));
						if(!pay_info.equals("") && pd_FrpId.equals("pay.weixin.wappay")){
							//System.out.println("提交的URL地址激活=："+pay_info);
							redirect(pay_info);
						}else if(pd_FrpId.equals("pay.alipay.native")){
							redirect(code_url);
						}else{
							renderText("网络繁忙，请稍后再试！");
						}	
					}						
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常！！！");
				//res = "系统异常";
			} finally {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			}			
			//getResponse().getWriter().write(res);
		}else{
			renderText("客户签名失败");
		}
	}
	
	public void callback_baoxi(){
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				System.out.println("err:通道传递参数错误"); 
			}  			
			System.out.println("回调的xml:"+rec_xml);
			if(formatString(rec_xml).equals("")){
				System.out.println("err:未获得通道参数:"+rec_xml); 
			}

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 141;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				System.out.println("回调参数发生错误");
			}		
			
			String hmac = map.get("attach");						
			String p3_Amt = map.get("total_fee");
			String r6_Order = formatString(map.get("out_trade_no"));//订单号
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.p3_Amt from orders o where o.orderid='"+r6_Order+"' limit 1";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			BigDecimal r3_Amt = o.getBigDecimal("p3_Amt");
			int user_merId = o.getInt("p1_MerId");
			System.out.println("p3_Amt========="+p3_Amt);
			System.out.println("r3_Amt========="+r3_Amt);
			
			BigDecimal aa = new BigDecimal(p3_Amt);
			BigDecimal ab = r3_Amt;
			if(aa.compareTo(ab)!=0){
				p3_Amt="0";
				r3_Amt=new BigDecimal(0);
			}
			Map<String, String> map_sign = new HashMap<String, String>();
			map_sign.put("out_trade_no", map.get("out_trade_no"));
			map_sign.put("total_fee", p3_Amt);			
			//Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign						
			String nhmac = MD5.sign(SignUtils.formatUrlMap(map_sign), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写			
			System.out.println("baoxi微信或者支付宝传递回的sign=："+hmac);
			System.out.println("baoxi平台根据返回参数计算的sign=："+nhmac);
			
			if(hmac.equals(nhmac)){
				String status = map.get("status");
				if(status != null && "0".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "0".equals(result_code)){	
						
						String service = formatString(map.get("trade_type"));//交易类型
						String r2_TrxId= formatString(map.get("transaction_id"));//威富通交易号
						String ro_BankOrderId = formatString(map.get("out_transaction_id"));//第三方订单号
						String rp_PayDate = formatString(map.get("time_end"));//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间						
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);																									
										
						String user_key = Payment.dao.findById(user_merId).getStr("key");						
						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid= String.valueOf(gateway_id);//新增
						String r7_Uid="BUY";
						String r8_MP="charge";
						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += r3_Amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + r3_Amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("baoxi充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						boolean flag=false;
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							flag = cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt.toString(),gateway_id);	
						}else{
							renderText("success");
						}
						if(flag){
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("success");
						}																								
					  }else{
						  System.out.println("更新数据库失败！"); 
					  }
					}
				} 
			}else{
				System.out.println("-数字签名错误-！"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*********************好付-start********************************/

	public void getway_haofu() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=142;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHSALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHSWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHSWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("912558282537705472");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Abbyhs321ABC");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Abbyhs321ABC");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "912558282537705472");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.haofpay.com");
	            js.put("wap_name","服装街");
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==ret==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 142;
			BoingPay.setAppId("912558282537705472");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Abbyhs321ABC");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Abbyhs321ABC");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());

	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="142";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付-end***********************************************/
	
	/*********************好付2-start********************************/

	public void getway_haofu2() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=143;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHS2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHS2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHS2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("923071878384328704");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang2";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Abbyhs32123A");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Abbyhs32123A");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "923071878384328704");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.haofpay.com");
	            js.put("wap_name","服装街");
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==ret==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu2(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 143;
			BoingPay.setAppId("923071878384328704");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang2";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Abbyhs32123A");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Abbyhs32123A");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());

	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="143";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付2-end***********************************************/
	
	/*********************好付3-start********************************/

	public void getway_haofu3() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=144;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHS3ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHS3WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHS3WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("923099817893175296");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang3";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123ZShangs321ABC");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123ZShangs321ABC");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "923099817893175296");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.ehaopay.net");
	            js.put("wap_name","服装街");
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==ret==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu3(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 144;
			BoingPay.setAppId("923099817893175296");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang3";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123ZShangs321ABC");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123ZShangs321ABC");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());
	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="144";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付3-end***********************************************/
	
	/*********************好付4-start********************************/

	public void getway_haofu4() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=145;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHS4ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHS4WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHS4WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("918384480819879936");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang4";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WXaabc123ZShangs321ABC");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WXaabc123ZShangs321ABC");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "918384480819879936");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.ehaopay.net");
	            js.put("wap_name","服装街");
	            
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==rethaofu4==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu4(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 144;
			BoingPay.setAppId("918384480819879936");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang4";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WXaabc123ZShangs321ABC");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WXaabc123ZShangs321ABC");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());
	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="144";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付4-end***********************************************/
	
	/*********************好付5-start********************************/

	public void getway_haofu5() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=146;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHS5ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHS5WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHS5WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("928183718776938496");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang5";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXinzs5123Abbyhs321ABC");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXinzs5123Abbyhs321ABC");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "928183718776938496");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.ehaopay.net");
	            js.put("wap_name","服装街");
	            
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==rethaofu4==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu5(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 146;
			BoingPay.setAppId("928183718776938496");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang5";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXinzs5123Abbyhs321ABC");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXinzs5123Abbyhs321ABC");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());
	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="144";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付5-end***********************************************/
	
	/*********************好付6-start********************************/

	public void getway_haofu6() {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();											
			int id=153;
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = "ZHS6ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = "ZHS6WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = "ZHS6WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String notify_url = gateway.getStr("notify_url");
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId("931416250213482496");//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\zhaoshang6";
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Azhaos6byhs321ABC");//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Azhaos6byhs321ABC");
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", "931416250213482496");//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://user.ehaopay.net");
	            js.put("wap_name","服装街");
	            
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        System.out.println("==rethaofu4==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_haofu6(){
		System.out.println("好付支付回调");

        try {
        	int gateway_id = 153;
			BoingPay.setAppId("931416250213482496");//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl("http://soa.boingpay.com/service/soa");//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\zhaoshang6";
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", "WanXin123Azhaos6byhs321ABC");//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", "WanXin123Azhaos6byhs321ABC");
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());
	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			String r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid="144";
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************好付6-end***********************************************/
	
	
	/*********************zhaoshang_model-start********************************/

	public void getway_zhaoshang_model(int id) {
		try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();		
			String p0_Cmd = formatString(getPara("p0_Cmd"));
			String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
			String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
			String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
			String p4_Cur = formatString(getPara("p4_Cur"));
			String p5_Pid = formatString(getPara("p5_Pid"));
			String p6_Pcat =formatString(getPara("p6_Pcat"));	//
			String p7_Pdesc = formatString(getPara("p7_Pdesc"));
			String p8_Url = formatString(getPara("p8_Url"));
			String p9_SAF = formatString(getPara("p9_SAF"));  
			String pa_MP = formatString(getPara("pa_MP"));
			String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
			String pr_NeedResponse = formatString(getPara("pr_NeedResponse"));//系统默认为1       
			String hmac = getPara("hmac");//数据签名      
	
			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse; 		
			//根据用户客户ID查找出KEY
			String key = Payment.dao.findById(p1_MerId).getStr("key");
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
			//System.out.println("生成的客户签名是："+nhmac);
			//System.out.println("接受到的签名是=："+hmac);
	
			if(nhmac.equals(hmac)){
				String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				String orderid = "";//平台订单号
				if(p3_Amt.equals("")){
					renderText("金额不正确");
				}
				//System.out.println("传给微信的值（单位分）："+r3_Amt);

				int gateway_id = id;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				String req_url = gateway.getStr("req_url");
				String notify_url = gateway.getStr("notify_url");
				String gateway_key = gateway.getStr("gateway_key");
				String gateway_merid = gateway.getStr("gateway_merid");
				String order_tap = gateway.getStr("order_tap");
	
				if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
					pd_FrpId = "alipaywap";
					orderid = order_tap+"ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					pd_FrpId = "wxwap";//微信通道编码
					orderid = order_tap+"WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}else{
					pd_FrpId = "wxpaywap";//默认为微信通道
					orderid = order_tap + "WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
				}			
	
				//String mch_id = SwiftpassConfig.mch_id;
				//String mch_key = SwiftpassConfig.key;
				
				Order order = new Order();
				Date date = new Date();
				order.set("orderid", orderid);
				order.set("p0_Cmd", "Buy");//平台默认值
				order.set("p1_MerId", p1_MerId);
				order.set("p2_Order", p2_Order);
				order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
				order.set("p4_Cur", "CNY");
				order.set("p5_Pid", "充值");
				order.set("p6_Pcat", p6_Pcat);
				order.set("p7_Pdesc", "充值");
				order.set("p8_Url", p8_Url);
				order.set("p9_SAF", 0);
				order.set("pa_MP", "充值");
				order.set("pd_FrpId", pd_FrpId);	
				order.set("pr_NeedResponse", 1);//系统默认值是1
				order.set("hmac", hmac);			
				order.set("CreateTime",date);
				order.set("success", 2);
				order.set("r1_Code", 2);
				order.set("lock", 1);
				order.set("ip", user_ip);
				order.set("gateway_id", gateway_id);
				order.save();	
				
				String outOrderId = orderid;
	          //此下单接口由于配置原因，使用另外一套测试参数，所以在此重新配置下
	            //String path = ; //项目路径
	            BoingPay.setAppId(gateway_merid);//系统接入方APPID，可以是渠道商身份或者商户身份
	            BoingPay.setServerUrl(req_url);//对接版本
	            BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
	            try {
	            	String path = "D:\\cert\\"+order_tap;
	                BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", gateway_key);//DSA公钥，DSA私钥
	    			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", gateway_key);
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}//RSA公钥，RSA私钥
	
	
	            //开始下单业务
	            BigDecimal amtbig = new BigDecimal(100);
	            BigDecimal amtbig1 = new BigDecimal(p3_Amt);
	            Map<String, Object> order_map = new HashMap<String, Object>();
	            order_map.put("mch_app_id", gateway_merid);//商户应用编号
	            order_map.put("out_order_no", outOrderId);//商户订单号
	            order_map.put("pay_interface_no","3004");
	            order_map.put("pay_context","MWEB");
	            order_map.put("order_name", "订单名称");//订单名称
	            order_map.put("pay_amount", amtbig.multiply(amtbig1.setScale(2)));//支付金额（单位：分）
	            order_map.put("back_url", notify_url);//后台通知地址
	            order_map.put("order_desc", "order_desc");//订单描述
	            order_map.put("user_ip", user_ip);
	
	            JSONObject js = new JSONObject();
	            js.put("type","WAP");
	            js.put("wap_url","http://vip.ehaopay.net");
	            js.put("wap_name","服装街");
	            
	            
	            order_map.put("scene_info", js.toString());
	            order_map.put("remark", "QQ");//备注
	
	            JSONObject res_json;
	    		try {
	    			res_json = BoingPay.getCashier().call("cod.pay.order.native_pay", order_map);
	    	        //System.out.println("==rethaofu4==" + res_json);
	    	        
	    	        String rest = formatString(res_json.getString("response"));
					
					if(rest.equals("")){
						//System.out.println("URL获取成功"+res_json.get("pay_url"));
						String dirc_url = (String)res_json.get("pay_url");
						setAttr("direct_url",dirc_url);
						renderJsp("/WEB-INF/pay/must/xiaoxPayType.jsp");
					}else {
						renderText("系统异常！");
					}
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
			}else{
				renderText("客户签名失败");
			}
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callback_zhaoshang_model(){

        try {

            JSONObject params = new JSONObject();
        	Map requestParams = getRequest().getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            if (params.containsKey("event")) {
                JSONObject event = JSONObject.parseObject(params.getString("event"));
                params.put("event", event);
            }
            JSONObject event_temp = params.getJSONObject("event");
            String r6_Order = event_temp.getString("out_order_no");

			//查询订单列表中对应的订单号状态  
			String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url,o.gateway_id from orders o where o.orderid='"+r6_Order+"'";							
			List<Order> bb = Order.dao.find(sql.toString());
			int len = bb.size();
			Order o = bb.get(0);        
			String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
			int  r1_Code1=o.getInt("r1_Code");        //支付结果         
			String UserCallback = o.get("p8_Url");    //取得用户的回传地址
			String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
			int user_merId = o.getInt("p1_MerId");
			int gateway_id = o.getInt("gateway_id");
			

			Gateway gateway = Gateway.dao.findById(gateway_id);
			String notify_url = gateway.getStr("notify_url");
			String gateway_key = gateway.getStr("gateway_key");
			String gateway_merid = gateway.getStr("gateway_merid");
			String order_tap = gateway.getStr("order_tap");
			
			BoingPay.setAppId(gateway_merid);//系统接入方APPID，可以是渠道商身份或者商户身份
	        BoingPay.setServerUrl(notify_url);//对接版本
	        BoingPay.setApiVersion("2.0");//DSA公钥，DSA私钥
        	String path = "D:\\cert\\"+order_tap;
            BoingPay.setRSAKey(path + "\\cloud_rsa_public.cer", path + "\\rsa_private_key.pfx", gateway_key);//DSA公钥，DSA私钥
			BoingPay.setDSAKey(path + "\\cloud_dsa_public.cer", path + "\\dsa_private_key.pfx", gateway_key);
			
			JSONObject result1 = BoingPay.getCashier().getNotifyResult(getRequest(), getResponse());
	        JSONObject event = result1.getJSONObject("event");
		
			String totalAmount = event.getString("pay_amount");
			String outOrderId = event.getString("out_order_no");
			String channel_trade_no = event.getString("channel_trade_no");
			String order_no = event.getString("order_no");
				
			BigDecimal a1 = new BigDecimal(totalAmount);
			BigDecimal a2 = new BigDecimal(100);
			totalAmount = a1.divide(a2).setScale(2).toString();
			
			r6_Order = outOrderId;//订单号
			String r2_TrxId= order_no;//交易号
			String ro_BankOrderId = channel_trade_no;//请求序列号
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			String rp_PayDate = sdf2.format(new Date());//支付完成时间					
			String ru_Trxtime=rp_PayDate;//交易结果记录时间
			String r3_Amt = totalAmount;
		
			String user_key = Payment.dao.findById(user_merId).getStr("key");

			String r0_Cmd="Buy";
			int r1_Code=1;
			String r4_Cur="CNY";
			String r5_Pid="1013666";
			String r7_Uid=""+gateway_id;
			String r8_MP="charge";

			String  ssb= "";
			ssb += user_merId;
			ssb += r0_Cmd;
			ssb += r1_Code;
			ssb += r2_TrxId;
			ssb += r3_Amt;
			ssb += r4_Cur;
			ssb += r5_Pid;
			ssb += user_r6_order;
			ssb += r7_Uid;//商品描述
			ssb += r8_MP;
			ssb += "1";        
			ssb += rp_PayDate;
			String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
			//给客户的回调URL，带参数
			String result = "";
			result += UserCallback;
			result += "?";
			result += "r0_Cmd=" + r0_Cmd;
			result += "&r1_Code=" + r1_Code;
			result += "&r2_TrxId=" + r2_TrxId;
			result += "&r3_Amt=" + r3_Amt;
			result += "&r4_Cur=" + r4_Cur;
			result += "&r5_Pid=" + r5_Pid;
			result += "&r6_Order=" + user_r6_order;
			result += "&r7_Uid=" + r7_Uid;
			result += "&r8_MP=" + r8_MP;
			result += "&r9_BType=1";
			result += "&rp_PayDate=" +rp_PayDate;
			result += "&hmac=" + rehmac;
			CibService cibservice = new CibService(); 
			if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
				boolean flag = cibservice.MustUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
				System.out.println("数据存储成功");
			}else{
				renderText("SUCCESS");
			}
			HttpClient hClient = new HttpClient(); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(301100);
			PostMethod post = null;
			post = new PostMethod(result);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";
			hClient.executeMethod(post);
			returnStr = post.getResponseBodyAsString();
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("status", "N");
					asynchronous.set("url", result);
					asynchronous.save();
				}else{
					asynchronous.set("url",result);
					asynchronous.set("status", "N");
					asynchronous.update();
				}							
			}else{
				renderText("SUCCESS");
				System.out.println(r6_Order+"同步通知成功");
				
			}					
					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/*********************************zhaoshang_model end***********************************************/
	
	/*********************测试通道********************************/
	public void getway_hjy(int id) throws IOException {		

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();											

		String p0_Cmd = formatString(getPara("p0_Cmd"));
		String p1_MerId = formatString(getPara("p1_MerId"));  //商户ID
		String p2_Order = formatString(getPara("p2_Order"));	//商户订单号
		String p3_Amt = formatString(getPara("p3_Amt"));		//支付金额
		String p4_Cur = formatString(getPara("p4_Cur"));
		String p5_Pid = formatString(getPara("p5_Pid"));
		String p6_Pcat =formatString(getPara("p6_Pcat"));	//
		String p7_Pdesc = formatString(getPara("p7_Pdesc"));
		String p8_Url = formatString(getPara("p8_Url"));
		String p9_SAF = formatString(getPara("p9_SAF"));  
		String pa_MP = formatString(getPara("pa_MP"));
		String pd_FrpId = formatString(getPara("pd_FrpId"));//支付通道编码
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");//数据签名      

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse; 		
		//根据用户客户ID查找出KEY
		String key = Payment.dao.findById(p1_MerId).getStr("key");
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 
		
		if(nhmac.equals(hmac)){
			String user_ip = CryptTool.getIpAddr(getRequest());//终端IP地址
			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号
			BigDecimal r3_Amt=new BigDecimal("100");//换算为元的金额
			if(p3_Amt.equals("")){
				renderText("金额不正确");
			}else{
				r3_Amt = new BigDecimal(p3_Amt).multiply(r3_Amt).setScale(0);
			}			
			int wap_type = 0;//支付编码
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";
				wap_type = 2;
				orderid = "HJYALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				pd_FrpId = "wxwap";//微信通道编码
				wap_type = 1;
				orderid = "HJYWX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else if(pd_FrpId.toLowerCase().equals("weixin")){//QQ钱包支付
				pd_FrpId = "qq";
				wap_type = 6;
				orderid = "HJYQQ" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+p1_MerId;
			}else{
				renderText("通道未授权");
				return;
			}
			int gateway_id = id;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String mch_key = gateway.getStr("gateway_key");
			String notify_url = gateway.getStr("notify_url");
			String reqUrl = gateway.getStr("req_url");
			
			String partner_id=mch_id;
			String app_id = "4108";
			BigDecimal money = r3_Amt;
			String out_trade_no = orderid;
			String subject=orderid;
			subject= java.net.URLEncoder.encode(subject,"UTF-8");
			String qn = "zyap4108_57093_100";
			Map<String,String> map = new HashMap<String, String>();
			map.put("app_id", app_id);     		  //应用ID
			map.put("money", money.toString());			  //金额，单位：分
			map.put("out_trade_no", out_trade_no);//订单号      
			map.put("partner_id",partner_id);	  //商户ID
			map.put("qn",qn);	
			map.put("subject", subject);
			map.put("wap_type", String.valueOf(wap_type));						
			
			//Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			String preStr = SignUtils.formatUrlMap(map);			
			String sign  = SignUtils.sign(preStr, "&key="+mch_key, "UTF-8");		
			sign = sign.toUpperCase();
			Date date = new Date();	

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId", p1_MerId);
			order.set("p2_Order", p2_Order);
			order.set("p3_Amt", p3_Amt);//数据库中的金额是以元为单位
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", sign);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();				
			String result = reqUrl+"?";
			result += "app_id="+app_id;
			result += "&money="+money;			
			result += "&out_trade_no="+out_trade_no;
			result += "&partner_id="+partner_id;
			result += "&subject="+subject;
			result += "&qn="+qn;			
			result += "&wap_type="+wap_type;
			result += "&sign="+sign;
			//System.out.println("提交的参数=："+result); 
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(result);								
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if(response != null && response.getEntity() != null){					
					String location = null;
				    int responseCode = 0;			      			           
				    responseCode = response.getStatusLine().getStatusCode();
				    System.out.println("responseCode:"+responseCode);
				    if(responseCode==200){
				    	org.apache.http.Header locationHeader = response.getFirstHeader("Location");
				        if(locationHeader!=null){
					       location = locationHeader.getValue();
					       redirect(location);
					       //System.out.println("location-200:"+location);
				        }
				    }else if(responseCode==302){
				        org.apache.http.Header locationHeader = response.getFirstHeader("Location");
				        if(locationHeader!=null){
				        	location = locationHeader.getValue();
				        	//System.out.println("location-302:"+location);
				        	redirect(location);
				        }
				   }
				}else{
					renderText("操作失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderText("系统异常");
				//res = "系统异常";
			}			
		}else{
			renderText("客户签名失败");
		}
	}

	public void callback_hjy(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");
			String code = formatString(getPara("code"));
			String app_id = formatString(getPara("app_id")); 	
			String pay_way = formatString(getPara("pay_way"));			
			String out_trade_no = formatString(getPara("out_trade_no"));			
			String invoice_no = formatString(getPara("invoice_no"));			
			String up_invoice_no = formatString(getPara("up_invoice_no"));//银⾏或微信⽀付流⽔号			
			String money =formatString(getPara("money"));			
			String qn = formatString(getPara("qn"));			
			String nhmac = formatString(getPara("sign"));
			
			Map<String,String> map = new HashMap<String, String>();			
			map.put("app_id", app_id); 
			map.put("code", code);
			map.put("invoice_no", invoice_no);
			map.put("money",money);
			map.put("out_trade_no",out_trade_no);	
			map.put("pay_way", pay_way);
			map.put("qn", qn);
			map.put("up_invoice_no", up_invoice_no);	
			int gateway_id = 146;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String key = gateway.getStr("gateway_key");
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign
			String preStr = SignUtils.formatUrlMap(new_map);			
			String hmac  = SignUtils.sign(preStr, "&key="+key, "UTF-8");		
			hmac = hmac.toUpperCase();
		if(hmac.equals(nhmac)){			
				if(code != "" && "0".equals(code)){
					if(true){	
						String r6_Order = out_trade_no;//订单号
						//String service = "wxwap";//交易类型
						String r2_TrxId= up_invoice_no;//威富通交易号
						String ro_BankOrderId = up_invoice_no;//第三方订单号
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
						String rp_PayDate = sdf2.format(new Date());//支付完成时间	
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = money;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"' limit 1";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="BUY";
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						//System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.SwiftpassUpdate(r2_TrxId, r6_Order, ro_BankOrderId, ru_Trxtime, ru_Trxtime, pd_FrpId,amt.toString(),gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(1110000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(3011000);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							//System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("0");
						}																								
					} 
				}else{
					renderText("数字签名错误！"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_xinhuo(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");		
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://user.ehaopay.net/web/Result/";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="XH1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="XH1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="G"+gateway_id+"qq2491893265";
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();

			if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
				map.put("method", "mbupay.wxpay.mweb");
			}else{
				map.put("method", "mbupay.alipay.wap");
			}
			map.put("version", "2.0.0");
			//map.put("charset", "utf-8");
			map.put("appid", "ca2017110810000322");
			map.put("out_trade_no", out_trade_no);
			map.put("mch_id",gateway_merid);//m20170517000010420
			map.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("notify_url", notify_url);
			map.put("spbill_create_ip", user_ip);
			map.put("is_mwebplus", "1");
			
			String mch_key=gateway_key;//f5029029907c04b812d97ff28709a508
			//System.out.println("map====:"+map);
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);

			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			System.out.println("preStr:"+preStr);

			String sign = MD5.sign(preStr, "&key=" + mch_key, "utf-8").toUpperCase();
			//System.out.println("计算的数字签名是："+sign);
			map.put("sign", sign);

			//System.out.println("请求参数:" + com.mustpay.util.XmlUtils.parseXML(map));			
			String res = "";
			//System.out.println("提交地址=："+reqUrl);
			try {
				res = com.mustpay.util.CorefireHttpPost.connect(reqUrl, map);
				//System.out.println("请求后返回数据：" + res);
				Map<String,String> resultMap = com.mustpay.util.XmlUtils.xml2map(res, "xml");
				res = XmlUtils.toXml(resultMap);
				System.out.println("中信银行服务器返回数据1："+res);
				System.out.println("中信银行服务器--银盛返回数据："+resultMap);
				String return_code = resultMap.get("return_code");
				if(return_code.equals("SUCCESS")){
					if(resultMap.containsKey("sign")){
						//Map<String,String> new_map = com.mustpay.util.SignUtils.paraFilter(resultMap);//过滤掉空值和map
						resultMap.remove("device_info");
						resultMap.remove("err_code");
						resultMap.remove("err_code_des");
						if(!SignUtils.checkParam(resultMap, mch_key)){
							//res = "";
							renderText("通道验证签名不通过");
						}else{
							if("SUCCESS".equals(resultMap.get("result_code"))){	
								String prepayId = resultMap.get("prepay_id");
								String mweb_url= resultMap.get("mweb_url");
								System.out.println(prepayId);
								setAttr("out_trade_no",out_trade_no);
								setAttr("subject",subject);			
								setAttr("total_fee",total_fee);						
								setAttr("body",body);
								setAttr("prepayId", prepayId);
								setAttr("mweb_url", mweb_url);
								renderJsp("/WEB-INF/pay/must/xhPayType.jsp");									
							}else{
								renderText("服务器链接失败");
							}
						}
					}else{
						renderText("通道签名失败");
					}
				}else{
					renderText("服务器链接失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			renderText("签名失败");
		}
	}

	public void callback_xinhuo1(){		
		try {
			String rec_xml="";
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	

			try{
				BufferedReader reader = this.getRequest().getReader();
				String line = null;
				while((line = reader.readLine()) != null){
					rec_xml +=line;
				}
				reader.close();	         
			}catch(Exception ex){
				renderText("传递参数错误"); 
			}  

			//System.out.println(rec_xml);		    

			//String p1_MerId = XmlUtils.MyXMLvalue(rec_xml,"mch_id");//通道ID和KEY
			//String key = SwiftpassConfig.key;
			int gateway_id = 150;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			//String mch_id = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");

			Map<String, String> map = new HashMap<String, String>();     	 
			try {
				Element ment=SignUtils.readerXml(rec_xml,"UTF-8"); 
				map = XmlUtils.toMap(ment);
			} catch (Exception e) {
				renderHtml("回调参数发生错误");
			}
			String hmac = map.get("sign");				
			Map<String,String> new_map = SignUtils.paraFilter(map);//过滤掉空值和sign				
			StringBuilder sb = new StringBuilder((new_map.size() +1) * 10);
			SignUtils.buildPayParams(sb,new_map,false);
			String nhmac = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			nhmac = nhmac.toUpperCase();//字符串转换成大写
			//System.out.println("微信或者支付宝传递回的sign=："+hmac);
			//System.out.println("平台根据返回参数计算的sign=："+nhmac);

			if(!hmac.equals(nhmac)){
				renderText("数字签名错误！"); 
			}else{
				String status = map.get("return_code");
				if(status != null && "SUCCESS".equals(status)){
					String result_code = map.get("result_code");
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = map.get("out_trade_no");//订单号
						String service = map.get("method");//交易类型
						String r2_TrxId= map.get("pass_trade_id>");//威富通交易号
						String ro_BankOrderId = map.get("transaction_id");//支付宝订单号
						String rp_PayDate = map.get("time_end");//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = map.get("total_fee");
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						BigDecimal yb = new BigDecimal(100);
						amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");
						//System.out.println("用户信息：user_merId=："+user_merId+"；user_key=："+user_key);
						//System.out.println("用户回调地址：UserCallback=："+UserCallback);

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="116";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("<xml><return_code>SUCCESS</return_code></xml>");
						}																								
					} 
				}else{
					renderText("由于网络原因，通讯失败，请联系客服解决此问题");
				}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getway_youfu1(int gateway_id) throws IOException{
		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");		
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://user.ehaopay.net/web/Result/";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="YF1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="YF1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String mch_key=gateway_key;//f5029029907c04b812d97ff28709a508
			
			String md5str = "partner="+gateway_merid+"&banktype=ALIPAYWAP&paymoney="+total_fee+"&ordernumber="+out_trade_no+"&callbackurl="+notify_url+mch_key;

			//System.out.println("====md5str====:"+md5str);
			
			String sign = MD5.sign(md5str, "", "utf-8").toLowerCase();
			
			String pay_url = reqUrl + "?banktype=ALIPAYWAP"+
					"&partner="+gateway_merid+
            		"&paymoney="+total_fee+
            		"&ordernumber="+out_trade_no+
            		"&callbackurl="+notify_url+
            		"&hrefbackurl="+notify_url+
            		"&attach=pay"+
            		"&sign="+sign;
			
			//System.out.println("====sign====:"+sign);
			//System.out.println("====pay_url====:"+pay_url);
			redirect(pay_url);
		}else{
			renderText("签名失败");
		}
	}

	public void callback_youfu1(){
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			
			int gateway_id = 151;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			

			String partner = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");	
			String orderstatus = getPara("orderstatus");// 支付状态
			String ordernumber = getPara("ordernumber");// 订单号
			String sysnumber = getPara("sysnumber"); //通道生成订单号
			String paymoney = getPara("paymoney");//付款金额
			String sign = getPara("sign");//字符加密串
			String attach = getPara("attach");//订单描述
			

			String md5str = "partner="+partner+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&paymoney="+paymoney+key;
//System.out.println("===cccccc=md5str====="+md5str);
			String me_sign = MD5.sign(md5str, "", "utf-8").toLowerCase();
			
			if (!sign.equals(me_sign))//签名正确
            {
				//System.out.println("===cccccc=数字签名错误=====");
				//验证失败
            	renderText("数字签名错误！"); 
            }else{
				String result_code = orderstatus;
				if(result_code != null && "1".equals(result_code)){						

					String r6_Order = ordernumber;//订单号
					String r2_TrxId= sysnumber;//威富通交易号
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String rp_PayDate = df.format(date);//支付完成时间
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					String r3_Amt = paymoney;
					//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
					//BigDecimal amt = new BigDecimal(r3_Amt);
					//BigDecimal yb = new BigDecimal(100);
					//amt = amt.divide(yb);																				
					//查询订单列表中对应的订单号状态  
					String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
					List<Order> bb = Order.dao.find(sql.toString());
					int len = bb.size();
					Order o = bb.get(0);        
					String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
					int  r1_Code1=o.getInt("r1_Code");        //支付结果         
					String UserCallback = o.get("p8_Url");    //取得用户的回传地址
					String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
					int user_merId = o.getInt("p1_MerId");
					String user_key = Payment.dao.findById(user_merId).getStr("key");

					String r0_Cmd="Buy";
					int r1_Code=1;
					String r4_Cur="CNY";
					String r5_Pid="";
					String r7_Uid="116";//通道id
					String r8_MP="charge";

					String  ssb= "";
					ssb += user_merId;
					ssb += r0_Cmd;
					ssb += r1_Code;
					ssb += r2_TrxId;
					ssb += r3_Amt;
					ssb += r4_Cur;
					ssb += r5_Pid;
					ssb += user_r6_order;
					ssb += r7_Uid;//商品描述
					ssb += r8_MP;
					ssb += "1";        
					ssb += rp_PayDate;
					String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
					//给客户的回调URL，带参数
					String result = "";
					result += UserCallback;
					result += "?r0_Cmd=" + r0_Cmd;
					result += "&r1_Code=" + r1_Code;
					result += "&r2_TrxId=" + r2_TrxId;
					result += "&r3_Amt=" + r3_Amt;
					result += "&r4_Cur=" + r4_Cur;
					result += "&r5_Pid=" + r5_Pid;
					result += "&r6_Order=" + user_r6_order;
					result += "&r7_Uid=" + r7_Uid;
					result += "&r8_MP=" + r8_MP;
					result += "&r9_BType=1";
					result += "&rp_PayDate=" +rp_PayDate;
					result += "&hmac=" + rehmac;

					//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
					System.out.println("充值成功回调给客户的URL=："+result);
					CibService cibservice = new CibService(); 
					if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
						cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					}	
					HttpClient hClient = new HttpClient(); 
					HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
					managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
					managerParams.setSoTimeout(301100);
					PostMethod post = null;
					post = new PostMethod(result);
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
					post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
					String returnStr = "";
					hClient.executeMethod(post);
					returnStr = post.getResponseBodyAsString();
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
							asynchronous = new Asynchronous();
							asynchronous.set("orderid", r6_Order);
							asynchronous.set("status", "N");
							asynchronous.set("url", result);
							asynchronous.save();
						}else{
							asynchronous.set("url",result);
							asynchronous.set("status", "N");
							asynchronous.update();
						}
					}else{
						System.out.println(r6_Order+"同步通知成功");
						renderText("ok");
					}
				}
			}
			//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//=========================中铁2start=======================================
	public void getway_zhongtie2(int gateway_id) throws IOException{

		this.getRequest().setCharacterEncoding("UTF-8");		
		this.getResponse().setCharacterEncoding("UTF-8");	
		//PrintWriter out = getResponse().getWriter();									
		//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
		String p0_Cmd = getPara("p0_Cmd");
		String p1_MerId = getPara("p1_MerId");  //商户ID
		String p2_Order = getPara("p2_Order");	//商户订单号
		String p3_Amt = getPara("p3_Amt");		//支付金额
		String p4_Cur = getPara("p4_Cur");
		String p5_Pid = getPara("p5_Pid");
		String p6_Pcat =getPara("p6_Pcat");	//
		String p7_Pdesc = getPara("p7_Pdesc");
		String p8_Url = getPara("p8_Url");
		String p9_SAF = getPara("p9_SAF");  
		String pa_MP = getPara("pa_MP");
		String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
		String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
		String hmac = getPara("hmac");			//数据签名        

		String sbOld = "";
		sbOld += p0_Cmd;
		sbOld += p1_MerId;
		sbOld += p2_Order;
		sbOld += p3_Amt;
		sbOld += p4_Cur;
		sbOld += p5_Pid;
		sbOld += p6_Pcat;
		sbOld += p7_Pdesc;
		sbOld += p8_Url;
		sbOld += p9_SAF;
		sbOld += pa_MP;
		sbOld += pd_FrpId;
		sbOld += pr_NeedResponse;  
		String key="";
		//根据用户客户ID查找出KEY
		key = Payment.dao.findById(p1_MerId).getStr("key");		
		String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
		//替换返回地址
		//String sys_callback = "http://user.ehaopay.net/web/Result/";
		String user_ip = CryptTool.getIpAddr(getRequest());
		Order order = new Order();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
		String orderid = "";//平台订单号			
		Date date = new Date();		
		if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
			pd_FrpId = "alipaywap";			
			orderid="ZT2ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
			pd_FrpId = "wxwap";			
			orderid="ZT2WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
		}else{
			renderText("通道未授权！");
		}
		//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
		//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
		Gateway gateway = Gateway.dao.findById(gateway_id);
		String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
		String gateway_key = gateway.getStr("gateway_key");	
		String reqUrl = gateway.getStr("req_url");
		String notify_url = gateway.getStr("notify_url");

		order.set("orderid", orderid);
		order.set("p0_Cmd", "Buy");//平台默认值
		order.set("p1_MerId",p1_MerId);
		order.set("p2_Order",p2_Order);
		order.set("p3_Amt", getPara("p3_Amt"));
		order.set("p4_Cur", "CNY");
		order.set("p5_Pid", "充值");
		order.set("p6_Pcat", p6_Pcat);
		order.set("p7_Pdesc", "充值");
		order.set("p8_Url", p8_Url);
		order.set("p9_SAF", 0);
		order.set("pa_MP", "充值");
		order.set("pd_FrpId", pd_FrpId);	
		order.set("pr_NeedResponse", 1);//系统默认值是1
		order.set("hmac", hmac);			
		order.set("CreateTime",date);
		order.set("success", 2);
		order.set("r1_Code", 2);
		order.set("lock", 1);
		order.set("ip", user_ip);
		order.set("gateway_id", gateway_id);
		order.save();		
		if (nhmac.equals( hmac)) {
			System.out.println("客户签名正确");
			String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
			String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
			String contact_us="G"+gateway_id+"qq2491893265";
			String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
			String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
			String nonce_str = sdf.format(new Date());//随机字符串
			Map<String,String> map = new HashMap<String, String>();

			if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
				map.put("pay_bankcode", "H5WEIXINPAY");
			}else{
				map.put("pay_bankcode", "H5WEIXINPAY");
			}
			map.put("pay_memberid", gateway_merid);
			map.put("pay_orderid", out_trade_no);
			//map.put("pay_amount", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
			String pay_amount = String.valueOf(new BigDecimal(total_fee));
			map.put("pay_amount", pay_amount);
			SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String applydate = sdfg.format(new Date());
			map.put("pay_applydate", applydate);
			map.put("pay_callbackurl", notify_url);
			map.put("pay_notifyurl", notify_url);
			
			Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
			StringBuilder buf = new StringBuilder((params.size() +1) * 10);
			com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
			String preStr = buf.toString();
			System.out.println("preStr:"+preStr);

			String sign = MD5.sign(preStr, "&key=" + gateway_key, "utf-8").toUpperCase();
			//System.out.println("计算的数字签名是："+sign);			
			setAttr("pay_memberid",gateway_merid);
			setAttr("tongdao","Zlpay");
			setAttr("pay_orderid",out_trade_no);			
			setAttr("pay_amount",pay_amount);						
			setAttr("pay_applydate",applydate);
			setAttr("notify_url", notify_url);
			setAttr("pay_md5sign", sign);
			setAttr("reqUrl",reqUrl);
			renderJsp("/WEB-INF/pay/must/zhongtie.jsp");
			
		}else{
			renderText("签名失败");
		}
	}
	
	public void callback_zhongtie2(){		
		try {
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");
			this.getResponse().setCharacterEncoding("UTF-8");	
			
			int gateway_id = 152;
			Gateway gateway = Gateway.dao.findById(gateway_id);
			

			String partner = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String key = gateway.getStr("gateway_key");	
			
			String memberid = getPara("memberid");// 支付状态
			String orderid = getPara("orderid");// 订单号
			String amount = getPara("amount"); //通道生成订单号
			String datetime = getPara("datetime");//付款金额
			String returncode = getPara("returncode");//付款金额
			String sign = getPara("sign");//字符加密串
			
//			SimpleDateFormat asfg = new SimpleDateFormat("yyyyMMddHHmmss");
//			Date datetime2 = null;
//			datetime2 = asfg.parse(datetime1);
//			SimpleDateFormat asf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String datetime = asf.format(datetime2);
			

			String md5str = "amount="+amount+"&datetime="+datetime+"&memberid="+partner+"&orderid="+orderid+"&returncode="+returncode;
//System.out.println("===cccccc=md5str====="+md5str);
			String me_sign = MD5.sign(md5str, "&key="+key, "utf-8").toUpperCase();
			//System.out.println("===计算的me_sign====="+me_sign);
			//System.out.println("===接收的sign====="+sign);
			if (!sign.equals(me_sign))//签名正确
            {
//				System.out.println("===cccccc=数字签名错误=====");
				//验证失败
            	renderText("数字签名错误！"); 
            }else{
				String result_code = returncode;
				if(result_code != null && "00".equals(result_code)){						

					String r6_Order = orderid;//订单号
					String r2_TrxId= "688sy"+Calendar.getInstance().getTimeInMillis();//威富通交易号
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String rp_PayDate = df.format(date);//支付完成时间
					//String rp_PayDate = datetime;
					String ru_Trxtime=rp_PayDate;//交易结果记录时间
					String r3_Amt = amount;
					//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
					BigDecimal amt = new BigDecimal(r3_Amt);
					//BigDecimal yb = new BigDecimal(100);
					//amt = amt.divide(yb);																				
					//查询订单列表中对应的订单号状态  
					String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
					List<Order> bb = Order.dao.find(sql.toString());
					int len = bb.size();
					Order o = bb.get(0);        
					String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
					int  r1_Code1=o.getInt("r1_Code");        //支付结果         
					String UserCallback = o.get("p8_Url");    //取得用户的回传地址
					String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
					int user_merId = o.getInt("p1_MerId");
					String user_key = Payment.dao.findById(user_merId).getStr("key");

					String r0_Cmd="Buy";
					int r1_Code=1;
					String r4_Cur="CNY";
					String r5_Pid="";
					String r7_Uid="152";//通道id
					String r8_MP="charge";

					String  ssb= "";
					ssb += user_merId;
					ssb += r0_Cmd;
					ssb += r1_Code;
					ssb += r2_TrxId;
					ssb += amt;
					ssb += r4_Cur;
					ssb += r5_Pid;
					ssb += user_r6_order;
					ssb += r7_Uid;//商品描述
					ssb += r8_MP;
					ssb += "1";        
					ssb += rp_PayDate;
					String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
					//给客户的回调URL，带参数
					String result = "";
					result += UserCallback;
					result += "?r0_Cmd=" + r0_Cmd;
					result += "&r1_Code=" + r1_Code;
					result += "&r2_TrxId=" + r2_TrxId;
					result += "&r3_Amt=" + amt;
					result += "&r4_Cur=" + r4_Cur;
					result += "&r5_Pid=" + r5_Pid;
					result += "&r6_Order=" + user_r6_order;
					result += "&r7_Uid=" + r7_Uid;
					result += "&r8_MP=" + r8_MP;
					result += "&r9_BType=1";
					result += "&rp_PayDate=" +rp_PayDate;
					result += "&hmac=" + rehmac;

					//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
					System.out.println("充值成功回调给客户的URL=："+result);
					CibService cibservice = new CibService(); 
					if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
						cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
					}	
					HttpClient hClient = new HttpClient(); 
					HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
					managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
					managerParams.setSoTimeout(301100);
					PostMethod post = null;
					post = new PostMethod(result);
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
					post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
					String returnStr = "";
					hClient.executeMethod(post);
					returnStr = post.getResponseBodyAsString();
					if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
						System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
						Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
						if(asynchronous==null){
							asynchronous = new Asynchronous();
							asynchronous.set("orderid", r6_Order);
							asynchronous.set("status", "N");
							asynchronous.set("url", result);
							asynchronous.save();
						}else{
							asynchronous.set("url",result);
							asynchronous.set("status", "N");
							asynchronous.update();
						}
					}else{
						System.out.println(r6_Order+"同步通知成功");
						renderText("OK");
					}
				}
			}
			//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//=========================中铁2-end=======================================
	
	
	//=========================建设银行start=======================================
		public void getway_jiansheyh1(int gateway_id) throws IOException{

			try{
			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();									
			//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
			String p0_Cmd = getPara("p0_Cmd");
			String p1_MerId = getPara("p1_MerId");  //商户ID
			String p2_Order = getPara("p2_Order");	//商户订单号
			String p3_Amt = getPara("p3_Amt");		//支付金额
			String p4_Cur = getPara("p4_Cur");
			String p5_Pid = getPara("p5_Pid");
			String p6_Pcat =getPara("p6_Pcat");	//
			String p7_Pdesc = getPara("p7_Pdesc");
			String p8_Url = getPara("p8_Url");
			String p9_SAF = getPara("p9_SAF");  
			String pa_MP = getPara("pa_MP");
			String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
			String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
			String hmac = getPara("hmac");			//数据签名        

			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse;  
			String key="";
			//根据用户客户ID查找出KEY
			key = Payment.dao.findById(p1_MerId).getStr("key");		
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
			//替换返回地址
			//String sys_callback = "http://user.ehaopay.net/web/Result/";
			String user_ip = CryptTool.getIpAddr(getRequest());
			Order order = new Order();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号			
			Date date = new Date();		
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";			
				orderid="JH1ALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
			}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
				pd_FrpId = "wxwap";			
				orderid="JH1WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
			}else{
				renderText("通道未授权！");
			}
			//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
			//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String gateway_key = gateway.getStr("gateway_key");	
			String reqUrl = gateway.getStr("req_url");
			String notify_url = gateway.getStr("notify_url");

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId",p1_MerId);
			order.set("p2_Order",p2_Order);
			order.set("p3_Amt", getPara("p3_Amt"));
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", hmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();		
			if (nhmac.equals( hmac)) {
				System.out.println("客户签名正确");
				String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
				String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
				String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String url = reqUrl;
		        

	            Map<String,String> apiRequest = new HashMap();
	            apiRequest.put("requestNo", out_trade_no);
	            apiRequest.put("merchantNo", "1051100110061615");
	            apiRequest.put("orderAmount", total_fee);
	            apiRequest.put("payTool", "WECHAT_H5");
	            apiRequest.put("orderDate", sdf1.format(new Date()));
	            apiRequest.put("productName", "有疑问联系客服：13728447023");
	            apiRequest.put("serverCallbackUrl", notify_url);
	            apiRequest.put("clientIp", user_ip);

		        JSONObject js = new JSONObject();
	            js.put("type","Wap");
	            js.put("wap_url","http://user.haofpay.com");
	            js.put("wap_name","服装街");
		        JSONObject js1 = new JSONObject();
		        js1.put("h5_info", js);
	            apiRequest.put("sceneInfo", js1.toJSONString());
		        
				StringBuffer buffer = new StringBuffer();
				for (Map.Entry<String, String> entry : apiRequest.entrySet()) {
					buffer.append((String)entry.getValue());
				}
				String sign = Digest.hmacSign(buffer.toString(), gateway_merid);
				apiRequest.put("hmac", sign);
				
				String paramJsonStr = JSON.toJSONString(apiRequest);
				
				//System.out.println("paramJsonStr=================="+paramJsonStr);
				
				String data =  AESUtil.encrypt(paramJsonStr, gateway_key.substring(0, 16));
				
				TreeMap<String, String> httpRequestParamMap = new TreeMap();
				httpRequestParamMap.put("data", data);
				httpRequestParamMap.put("appKey", gateway_merid);
				
				String res = ""; 
				if (url.contains("https")) {
					res = HttpsUtil.httpMethodPost(url, httpRequestParamMap, "UTF-8");
				} else {
					res = HttpUtil.httpMethodPost(url, httpRequestParamMap, "UTF-8");
				}
				
				//System.out.println("zhaoshang_res================"+res);
				
				String req_str = AESUtil.decrypt(res, gateway_key.substring(0, 16));
				//System.out.println("zhaoshang_res================"+req_str);
				
				JSONObject jsobj = JSON.parseObject(req_str);
				
				if(jsobj.getString("code").equals("1")){
				
					String redirectUrl = jsobj.getString("redirectUrl");
					
					//redirect(redirectUrl);

					setAttr("reqUrl",redirectUrl);
					renderJsp("/WEB-INF/pay/must/jiansheyh.jsp");
				}else{
					renderText("网络通信异常！");
				}
			}else{
				renderText("签名失败");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void callback_jiansheyh1(){		
			try {
				this.getRequest().setCharacterEncoding("UTF-8");
				this.getResponse().setCharacterEncoding("utf-8");
				this.getResponse().setContentType("text/xml");
				this.getResponse().setCharacterEncoding("UTF-8");	
				
				int gateway_id = 155;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				

				String partner = gateway.getStr("gateway_merid");//获得通道的ID号和Key
				String key = gateway.getStr("gateway_key");	
				
				
				String rec_xml="";
				this.getRequest().setCharacterEncoding("UTF-8");
				this.getResponse().setCharacterEncoding("UTF-8");
				this.getResponse().setContentType("text/xml");
				try{
					BufferedReader reader = this.getRequest().getReader();
					String line = null;
					while((line = reader.readLine()) != null){
						rec_xml +=line;
					}
					reader.close();	         
				}catch(Exception ex){
					renderText("传递参数错误"); 
				}
				
				//System.out.println("rec_xml==========="+rec_xml);
				
				String[] data = rec_xml.split("=");
				//System.out.println("data[1]================="+data[1]);
				//System.out.println("data[key]================="+key);
				//解密数据
				String req_str = AESUtil.decrypt(data[1], key.substring(0, 16));
				
				JSONObject jsobj = JSON.parseObject(req_str);
				
				//System.out.println("jsobj==========="+jsobj.toJSONString());
				
				
				//{"message":"成功","trxRequestNo":"JH1WX201711301622220222075155","orderNo":"11171130162222878193","status":"SUCCESS","orderAmount":"0.10","payTool":"WECHAT_H5","paidAmount":"0.10","openid":"o0yn8wEm40PfFYQY8IzABvleC6ro","merchantNo":"1051100110061615","payway":"微信零钱","code":"1"}
				
				String trxRequestNo = jsobj.getString("trxRequestNo");
				String orderNo = jsobj.getString("orderNo");
				String result_code = jsobj.getString("status");
				String amount = jsobj.getString("paidAmount");
				
					if(result_code != null && "SUCCESS".equals(result_code)){						

						String r6_Order = trxRequestNo;//订单号
						String r2_TrxId= orderNo;//威富通交易号
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						Date date = new Date();
						String rp_PayDate = df.format(date);//支付完成时间
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = amount;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						//BigDecimal yb = new BigDecimal(100);
						//amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="155";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("SUCCESS");
						}
					}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//=========================建设银行1-end=======================================
		
		
		
		//=========================中铁2start=======================================
		public void getway_zhongtie3(int gateway_id) throws IOException{

			this.getRequest().setCharacterEncoding("UTF-8");		
			this.getResponse().setCharacterEncoding("UTF-8");	
			//PrintWriter out = getResponse().getWriter();									
			//String PostUrl = "https://gate.yekapay.com:8080/GateWay/Bank.aspx";
			String p0_Cmd = getPara("p0_Cmd");
			String p1_MerId = getPara("p1_MerId");  //商户ID
			String p2_Order = getPara("p2_Order");	//商户订单号
			String p3_Amt = getPara("p3_Amt");		//支付金额
			String p4_Cur = getPara("p4_Cur");
			String p5_Pid = getPara("p5_Pid");
			String p6_Pcat =getPara("p6_Pcat");	//
			String p7_Pdesc = getPara("p7_Pdesc");
			String p8_Url = getPara("p8_Url");
			String p9_SAF = getPara("p9_SAF");  
			String pa_MP = getPara("pa_MP");
			String pd_FrpId = getPara("pd_FrpId");//支付通道编码	
			String pr_NeedResponse = getPara("pr_NeedResponse");//系统默认为1       
			String hmac = getPara("hmac");			//数据签名        

			String sbOld = "";
			sbOld += p0_Cmd;
			sbOld += p1_MerId;
			sbOld += p2_Order;
			sbOld += p3_Amt;
			sbOld += p4_Cur;
			sbOld += p5_Pid;
			sbOld += p6_Pcat;
			sbOld += p7_Pdesc;
			sbOld += p8_Url;
			sbOld += p9_SAF;
			sbOld += pa_MP;
			sbOld += pd_FrpId;
			sbOld += pr_NeedResponse;  
			String key="";
			//根据用户客户ID查找出KEY
			key = Payment.dao.findById(p1_MerId).getStr("key");		
			String nhmac = DigestUtil.hmacSign(sbOld, key); //生成数据签名(客户) 		
			//替换返回地址
			//String sys_callback = "http://user.ehaopay.net/web/Result/";
			String user_ip = CryptTool.getIpAddr(getRequest());
			Order order = new Order();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
			String orderid = "";//平台订单号			
			Date date = new Date();		
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				pd_FrpId = "alipaywap";			
				orderid="LHTALI" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
			}else if(pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wxwap")){
				pd_FrpId = "wxwap";			
				orderid="ZT3WX" + sdf.format(new Date())+ CryptTool.getPasswordOfNumber(4)+gateway_id;
			}else{
				renderText("通道未授权！");
			}
			//根据客户ID号查找对应开通的通道ID，并且根据通道ID查找出通道的KEY
			//int gateway_id = PersonGateway.dao.findById(p1_MerId).getInt("gateway_id");		
			Gateway gateway = Gateway.dao.findById(gateway_id);
			String gateway_merid = gateway.getStr("gateway_merid");//获得通道的ID号和Key
			String gateway_key = gateway.getStr("gateway_key");	
			String reqUrl = gateway.getStr("req_url");
			String notify_url = gateway.getStr("notify_url");

			order.set("orderid", orderid);
			order.set("p0_Cmd", "Buy");//平台默认值
			order.set("p1_MerId",p1_MerId);
			order.set("p2_Order",p2_Order);
			order.set("p3_Amt", getPara("p3_Amt"));
			order.set("p4_Cur", "CNY");
			order.set("p5_Pid", "充值");
			order.set("p6_Pcat", p6_Pcat);
			order.set("p7_Pdesc", "充值");
			order.set("p8_Url", p8_Url);
			order.set("p9_SAF", 0);
			order.set("pa_MP", "充值");
			order.set("pd_FrpId", pd_FrpId);	
			order.set("pr_NeedResponse", 1);//系统默认值是1
			order.set("hmac", hmac);			
			order.set("CreateTime",date);
			order.set("success", 2);
			order.set("r1_Code", 2);
			order.set("lock", 1);
			order.set("ip", user_ip);
			order.set("gateway_id", gateway_id);
			order.save();		
			if (nhmac.equals( hmac)) {
				System.out.println("客户签名正确");
				String out_trade_no =  new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
				String subject = new String(p0_Cmd.getBytes("ISO-8859-1"),"UTF-8");
				String contact_us="G"+gateway_id+"qq2491893265";
				String total_fee = new String(p3_Amt.getBytes("ISO-8859-1"),"UTF-8");
				String body = new String(contact_us.getBytes("ISO-8859-1"),"UTF-8");			
				String nonce_str = sdf.format(new Date());//随机字符串
				Map<String,String> map = new HashMap<String, String>();

				map.put("area", gateway_merid);
				map.put("area_out_trade_no", out_trade_no);
				map.put("subject", "wx:13728447023");
				//map.put("pay_amount", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue()));//将元转换为分
				String pay_amount = String.valueOf(new BigDecimal(total_fee));
				map.put("total_fee", pay_amount);
				
				
				Map<String,String> params = com.mustpay.util.SignUtils.paraFilter(map);
				StringBuilder buf = new StringBuilder((params.size() +1) * 10);
				com.mustpay.util.SignUtils.buildPayParams(buf,params,false);
				String preStr = buf.toString();
				System.out.println("preStr:"+preStr);

				String sign = MD5.sign(preStr, "&key=" + gateway_key, "utf-8").toUpperCase();
				System.out.println("计算的数字签名是："+sign);
								
				map.put("is_jump", "0");
				map.put("area_notify_url", notify_url);
				
				String result = "";
				result += reqUrl;
				result += "?area=" + gateway_merid;
				result += "&area_notify_url=" + notify_url;
				result += "&area_out_trade_no=" + out_trade_no;
				result += "&subject=" + "wx:13728447023";
				result += "&total_fee=" + pay_amount;
				result += "&sign=" + sign;
				result += "&is_jump=" + "0";
				result += "&body=" + "wx:13728447023";
				
				System.out.println("zhongtie3=====result======="+result);
				
				HttpClient hClient = new HttpClient(); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(301100);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				
				System.out.println("zhongtie3=====returnStr======="+returnStr);
				//"code":"1","msg":"下单成功","code_url

				JSONObject jsobj = JSON.parseObject(returnStr);
				
				if(jsobj.getString("code").equals("1")){
					redirect(jsobj.getString("code_url"));
				}else{
					renderText("通道通信异常！");
				}				
			}else{
				renderText("签名失败");
			}
		}
		
		public void callback_zhongtie3(){		
			try {
				this.getRequest().setCharacterEncoding("UTF-8");
				this.getResponse().setCharacterEncoding("utf-8");
				this.getResponse().setContentType("text/xml");
				this.getResponse().setCharacterEncoding("UTF-8");	
				
				int gateway_id = 156;
				Gateway gateway = Gateway.dao.findById(gateway_id);
				

				String partner = gateway.getStr("gateway_merid");//获得通道的ID号和Key
				String key = gateway.getStr("gateway_key");	
				
				String trade_status = getPara("trade_status");// 支付状态
				String area_out_trade_no = getPara("area_out_trade_no");// 订单号
				String trade_no = getPara("trade_no"); //通道生成订单号
				String total_fee = getPara("total_fee");//付款金额
				String gmt_payment = getPara("gmt_payment");//完成时间
				String sign = getPara("sign");//字符加密串
				
//				SimpleDateFormat asfg = new SimpleDateFormat("yyyyMMddHHmmss");
//				Date datetime2 = null;
//				datetime2 = asfg.parse(datetime1);
//				SimpleDateFormat asf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String datetime = asf.format(datetime2);
				

				//String md5str = "amount="+amount+"&datetime="+datetime+"&memberid="+partner+"&orderid="+orderid+"&returncode="+returncode;
				String md5str = "area_out_trade_no="+area_out_trade_no+"&gmt_payment="+gmt_payment+"&total_fee="+total_fee+"&trade_no="+trade_no+"&trade_status=SUCCESS";
				System.out.println("===计算的md5str====="+md5str);
				
				String me_sign = MD5.sign(md5str, "&key="+key, "utf-8").toUpperCase();
				System.out.println("===计算的me_sign====="+me_sign);
				System.out.println("===接收的sign====="+sign);
				if (!sign.equals(me_sign))//签名正确
	            {
					System.out.println("===cccccc=数字签名错误=====");
					//验证失败
	            	renderText("数字签名错误！"); 
	            }else{
					String result_code = trade_status;
					//if(result_code == "SUCCESS" && "SUCCESS".equals(result_code)){

						String r6_Order = area_out_trade_no;//订单号
						String r2_TrxId= trade_no;//威富通交易号
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						Date date = new Date();
						String rp_PayDate = df.format(date);//支付完成时间
						//String rp_PayDate = datetime;
						String ru_Trxtime=rp_PayDate;//交易结果记录时间
						String r3_Amt = total_fee;
						//System.out.println("返回参数service=："+service+";ro_BankOrderId=:"+ro_BankOrderId);
						BigDecimal amt = new BigDecimal(r3_Amt);
						//BigDecimal yb = new BigDecimal(100);
						//amt = amt.divide(yb);																				
						//查询订单列表中对应的订单号状态  
						String sql = "select o.pd_FrpId,o.r1_Code,o.p2_Order,o.p1_MerId,o.p8_Url from orders o where o.orderid='"+r6_Order+"'";							
						List<Order> bb = Order.dao.find(sql.toString());
						int len = bb.size();
						Order o = bb.get(0);        
						String pd_FrpId = o.get("pd_FrpId");      //支付通道编码
						int  r1_Code1=o.getInt("r1_Code");        //支付结果         
						String UserCallback = o.get("p8_Url");    //取得用户的回传地址
						String user_r6_order = o.get("p2_Order"); //取得用户的订单号        
						int user_merId = o.getInt("p1_MerId");
						String user_key = Payment.dao.findById(user_merId).getStr("key");

						String r0_Cmd="Buy";
						int r1_Code=1;
						String r4_Cur="CNY";
						String r5_Pid="";
						String r7_Uid="156";//通道id
						String r8_MP="charge";

						String  ssb= "";
						ssb += user_merId;
						ssb += r0_Cmd;
						ssb += r1_Code;
						ssb += r2_TrxId;
						ssb += amt;
						ssb += r4_Cur;
						ssb += r5_Pid;
						ssb += user_r6_order;
						ssb += r7_Uid;//商品描述
						ssb += r8_MP;
						ssb += "1";        
						ssb += rp_PayDate;
						String rehmac = DigestUtil.hmacSign(ssb, user_key); //返回给客户的数据签名
						//给客户的回调URL，带参数
						String result = "";
						result += UserCallback;
						result += "?r0_Cmd=" + r0_Cmd;
						result += "&r1_Code=" + r1_Code;
						result += "&r2_TrxId=" + r2_TrxId;
						result += "&r3_Amt=" + amt;
						result += "&r4_Cur=" + r4_Cur;
						result += "&r5_Pid=" + r5_Pid;
						result += "&r6_Order=" + user_r6_order;
						result += "&r7_Uid=" + r7_Uid;
						result += "&r8_MP=" + r8_MP;
						result += "&r9_BType=1";
						result += "&rp_PayDate=" +rp_PayDate;
						result += "&hmac=" + rehmac;

						//System.out.println("充值成功后更新数据库所需的参数："+"r2_TrxId=:"+r2_TrxId+"--r6_Order=:"+r6_Order+"--ro_BankOrderId=:"+r7_Uid+"--rp_PayDate=:"+rp_PayDate+"--ru_Trxtime=:"+ru_Trxtime+"--pd_FrpId=:"+pd_FrpId);   
						System.out.println("充值成功回调给客户的URL=："+result);
						CibService cibservice = new CibService(); 
						if(r1_Code1!=1 && len==1){//匹配订单号，金额以银行回传金额为准
							cibservice.MustUpdate(r2_TrxId, r6_Order, r7_Uid, ru_Trxtime, ru_Trxtime, pd_FrpId,r3_Amt,gateway_id);	
						}	
						HttpClient hClient = new HttpClient(); 
						HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();// 设置连接超时时间(单位毫秒)
						managerParams.setConnectionTimeout(111000);// 设置读数据超时时间(单位毫秒)
						managerParams.setSoTimeout(301100);
						PostMethod post = null;
						post = new PostMethod(result);
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
						post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
						String returnStr = "";
						hClient.executeMethod(post);
						returnStr = post.getResponseBodyAsString();
						if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
							System.out.println("同步通知不成功,保存订单并且加入到异步通知策略中！");
							Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
							if(asynchronous==null){
								asynchronous = new Asynchronous();
								asynchronous.set("orderid", r6_Order);
								asynchronous.set("status", "N");
								asynchronous.set("url", result);
								asynchronous.save();
							}else{
								asynchronous.set("url",result);
								asynchronous.set("status", "N");
								asynchronous.update();
							}
						}else{
							System.out.println(r6_Order+"同步通知成功");
							renderText("OK");
						}
					}
				//}
				//此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//=========================中铁2-end=======================================
	
	
	public static byte[] encryptByPublicKeyByPKCS1Padding(byte[] data, String publicKey)throws Exception {
		//System.out.println("--------提交数据RSA加密-----");
		byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		
		//System.out.println("--------提交数据publicK-----"+publicK);
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null){
	        Security.addProvider(new BouncyCastleProvider());
	    }else{
	    	//System.out.println("--------BouncyCastleProvider.PROVIDER_NAME-----:"+BouncyCastleProvider.PROVIDER_NAME);
	    }
		Cipher cipher=null;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		//System.out.println("-----------------inputLen-------------------"+inputLen);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > 117) {
				cache = cipher.doFinal(data, offSet, 117);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * 117;
		}		
		byte[] encryptedData = out.toByteArray();		
		out.close();
		return encryptedData;
				
	}

	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
	
	public static void main(String[] args) {		
		//Weixin||AlipayPAZH
		int max=900000000;
        int min=100000000;
        Random random = new Random();
        String s = random.nextInt(max)%(max-min+1) + min + "";
        
        queryOrder(s, "Weixin", null, "现金支付测试1",1,
        		0, 0, 1,null, 
        		null, null, null,null, null, 
        		null, "http://local.ttg.cn:8400/tlinx2apidemo1/callback/scanpay_cashier/payResult");
	}
	
	/**
     * Method queryOrderList
     * Description 说明：
     *
     * @param outNo 说明：
     * @param pmtTag 说明：
     * @param pmtName 说明：
     * @param ordName 说明：
     * @param originalAmount 说明：
     * @param discountAmount 说明：
     * @param ignoreAmount 说明：
     * @param tradeAmount 说明：
     * @param tradeAccount 说明：
     * @param tradeNo 说明：
     * @param tradeResult 说明：
     * @param remark 说明：
     * @param authCode 说明：
     * @param tag 说明：
     * @param jumpUrl 说明：
     * @param notifyUrl 说明：
     */
    public static void queryOrder(String outNo, String pmtTag, String pmtName, String ordName, Integer originalAmount,
                               Integer discountAmount, Integer ignoreAmount, Integer tradeAmount, String tradeAccount,
                               String tradeNo, String tradeResult, String remark, String authCode, String tag,
                               String jumpUrl, String notifyUrl) {

        // 初始化参数
        String timestamp = new Date().getTime() / 1000 + "";    // 时间

        try {
            // 固定参数
            TreeMap<String, String> postmap = new TreeMap<String, String>();    // 请求参数的map

            postmap.put("open_id", TestParams.OPEN_ID);
            postmap.put("timestamp", timestamp);

            TreeMap<String, String> datamap = new TreeMap<String, String>();    // data参数的map

            datamap.put("out_no", outNo);
            datamap.put("pmt_tag", pmtTag);
            datamap.put("pmt_name", pmtName);
            datamap.put("ord_name", ordName);
            datamap.put("original_amount", originalAmount+"");
            datamap.put("discount_amount", discountAmount+"");
            datamap.put("ignore_amount", ignoreAmount+"");
            datamap.put("trade_amount", tradeAmount+"");
            datamap.put("trade_account", tradeAccount);
            datamap.put("trade_no", tradeNo);
            datamap.put("trade_result", tradeResult);
            datamap.put("remark", remark);
            datamap.put("auth_code", authCode);
            datamap.put("tag", tag);
            datamap.put("jump_url", jumpUrl);
            datamap.put("notify_url", notifyUrl);

            /**
             * 1 data字段内容进行AES加密，再二进制转十六进制(bin2hex)
             */
            TLinx2Util.handleEncrypt(datamap, postmap);

            /**
             * 2 请求参数签名 按A~z排序，串联成字符串，先进行sha1加密(小写)，再进行md5加密(小写)，得到签名
             */
            TLinx2Util.handleSign(postmap);

            /**
             * 3 请求、响应
             */
            String rspStr = TLinx2Util.handlePost(postmap, TestParams.PAYORDER);

            System.out.println("返回字符串=" + rspStr);

            /**
             * 4 验签  有data节点时才验签
             */
            //JSONObject respObject = JSONObject.fromObject(rspStr);
            JSONObject respObject = JSONObject.parseObject(rspStr);
            Object dataStr    = respObject.get("data");

            if (!rspStr.isEmpty() && ( dataStr != null )) {
                if (TLinx2Util.verifySign(respObject)) {    // 验签成功

                    /**
                     * 5 AES解密，并hex2bin
                     */
                    String respData = TLinxAESCoder.decrypt(dataStr.toString(), TestParams.OPEN_KEY);
                    
                    JSONObject respObj = JSONObject.parseObject(respData);

                    System.out.println("==================响应data内容:" + respObj);
                } else {
                    System.out.println("=====验签失败=====");
                }
            } else {
                System.out.println("=====没有返回data数据=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
