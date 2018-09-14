package com.jsp.settlement.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.admin.customerlog.serivce.AdCustomerLogService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.function.Yunsms;
import com.interceptor.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.jsp.exchange.service.ExchangeService;
import com.jsp.login.service.LoginService;
import com.jsp.settlement.dao.SettlementDao;
import com.jsp.settlement.service.ExportService;
import com.jsp.settlement.service.SettlementService;
import com.jsp.user.service.UserService;
import com.pay.yeepay.server.DigestUtil;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.tool.StringUtil;
import com.vo.Balance;
import com.vo.Gateway;
import com.vo.Order;
import com.vo.Participate;
import com.vo.Payment;
import com.vo.Person;
import com.vo.Rate;
import com.vo.Refund;
import com.vo.SettlementAccount;
@Before(LoginInterceptor.class)
public class SettlementController extends Controller {
	public void index() {
		Person per = getSessionAttr("session");
		List<SettlementAccount> settlaccountlist = SettlementService.service.getSettlAccount(per.getInt("id"));
		List<SettlementAccount> userCards = SettlementService.service.findCardById(per.getInt("id"));
		SettlementAccount bank = null;
		SettlementAccount network = null;
		if (settlaccountlist.size() > 0) {
			for (SettlementAccount re : settlaccountlist) {
				if (re.getInt("account_types") == 1) {
					bank = re;
				} else if (re.getInt("account_types") == 2) {
					network = re;
				}
			}
		}
		int id = per.getInt("id");
		setAttr("balance", Balance.dao.findById(id));
		setAttr("refund_rate",Rate.dao.findById(id));
		setAttr("per", per);
		setAttr("bank", bank);
		setAttr("network", network);
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("userCards",userCards);
		createToken("withdrawalToken", 30 * 60);
		renderJsp("/WEB-INF/jsp/settlement1.jsp");
	}
	
	public void upSettleAcount() {
		int account_types = getParaToInt("account_types");
		Person per = getSessionAttr("session");
		String account_name = per.get("contacts");
		String branch;
		String account;
		String branchsheng;
		String branchshi;
		boolean too = false;
		boolean boo = false;
		if (account_types == 1) {
			int codeid = getParaToInt("codeid");
			branch = getPara("branch");
			account = getPara("account");
			branchsheng = getPara("branchsheng");
			branchshi = getPara("branchshi");
			too = SettlementService.service.inserSettleAcount(per.getInt("id"), account_types, account_name, branch,
						account, codeid, branchsheng, branchshi);
		} else if (account_types == 2) {
			branch = "支付宝";
			account = getPara("account");
			boo = SettlementService.service.ifSettleAcount(per.getInt("id"), account_types);
			if (boo) {
				too = SettlementService.service.upSettleAcount(per.getInt("id"), account_types, account_name, branch,
						account, 0, branch, branch);
			} else {
				too = SettlementService.service.inserSettleAcount(per.getInt("id"), account_types, account_name, branch,
						account, 0, branch, branch);
			}
		}
		if (too) {
			renderJson("{\"info\":\"提交成功！\",\"status\":\"y\"}");
		} else {
			renderJson("{\"info\":\"提交失败！\",\"status\":\"n\"}");
		}
	}
	
	/**
	 * @description 更新用户账户的方法<br/>
	 * @methodName updateUserCard<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月15日下午4:48:32<br/>
	 */
	public void updateUserCard(){
		try {
			int account_types = getParaToInt("account_types");
			System.out.println("account_type===="+account_types);
			Person per = getSessionAttr("session");
			int id = per.getInt("id");
			String account_name;
			String branch;
			String account;
			String branchsheng;
			String branchshi;
			String codeid;
			boolean result = false;
			if(account_types == 1){
				String primary_id = getPara("primary_id");
				account_name = getPara("account_name");
				branch = getPara("branch");
				account = getPara("account");
				branchsheng = getPara("branchsheng");
				branchshi = getPara("branchshi");
				codeid = getPara("codeid");
				result = SettlementService.service.updateCardById(primary_id, account_types, account_name, branch, account, branchsheng, branchshi, codeid);
			}else if(account_types == 2){
				branch = "支付宝";
				account_name = getPara("account_name");
				account = getPara("account");
				result = SettlementService.service.upSettleAcount(per.getInt("id"), account_types, account_name, branch,
						account, 0, branch, branch);
			}
			if (result) {
				index();
			} else {
				renderJson("{\"info\":\"提交失败！\",\"status\":\"n\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 删掉用户绑定的卡<br/>
	 * @methodName delUsercard<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月18日下午2:16:11<br/>
	 */
	public void delUsercard(){
		try {
			Person per = getSessionAttr("session");
			int id = per.getInt("id");
			int primary_id = getParaToInt("primary_id");
			boolean boo = SettlementAccount.dao.deleteById(primary_id);
			if(boo){
				index();
			}else{
				renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}
	
	public void withdrawal() {
		boolean bootoken = validateToken("withdrawalToken");
		Person per = getSessionAttr("session");
		Balance balance = Balance.dao.findById(per.getInt("id"));
		int settlementauthority = balance.getInt("settlementauthority");
		String refund_types = getPara("refund_types");
		String refund_amount = getPara("refund_amount");
		String payment = getPara("payment");
		String remark = getPara("remark");
		
		System.out.println("支付密码===" + payment);
		try {
			payment = MD5Utils.createMD5(payment);
			System.out.println("支付密码加密后===" + payment);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//账户可提余额
		BigDecimal settlement = balance.getBigDecimal("settlement");
		BigDecimal b = BigDecimal.valueOf(0);
		//提交的提现金额
		BigDecimal withdrawals = new BigDecimal(refund_amount);
		//提现最小金额
		BigDecimal cashleast = balance.getBigDecimal("cashleast");
		long num = -1;
		SimpleDateFormat format_sd = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		Date Nowdate = new Date();
		String mydate = format_sd.format(Nowdate);
		if (settlementauthority == 2) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			Date star = new Date();
			String starttime = sd.format(star);
			String endtime = "";
			try {
				endtime = sd.format(sd.parse(starttime).getTime() + 24 * 60 * 60 * 1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			num = SettlementService.service.numrefund(starttime, endtime);

		}
		//查询该用户的费率
		Rate rate = Rate.dao.findById(per.getInt("id"));
		//单笔提现手续费
		BigDecimal refund_rate = rate.getBigDecimal("refund");
		//可提余额-提交申请的余额-手续费
		BigDecimal lastMoney = settlement.subtract(withdrawals).subtract(refund_rate);
		if (!bootoken) {
			renderJson("{\"info\":\"请不要重复提交！\",\"status\":\"n\"}");
		} else if (num > 0) {
			renderJson("{\"info\":\"今天提现一次，无法提现！\",\"status\":\"n\"}");
		} else if (refund_types.equals("")) {
			renderJson("{\"info\":\"账户未绑定！\",\"status\":\"n\"}");
		} else if (withdrawals.compareTo(cashleast) == -1) {
			renderJson("{\"info\":\"提交金额太小无法提现！\",\"status\":\"n\"}");
		} else if (settlement.compareTo(b) == -1 || settlement.compareTo(b) == 0) {
			renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
		} else if (settlement.compareTo(withdrawals) == -1) {
			renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
		} else if (!UserService.userService.getPaymentboo(per.getInt("id"), payment)) {
			renderJson("{\"info\":\"支付密码不正确！\",\"status\":\"n\"}");
		} else if(lastMoney.compareTo(b) == -1){
			renderJson("{\"statusCode\":\"300\",\"info\":\"对不起！账户可提余额不足！\",\"status\":\"n\"}");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSSmmm");
			String refund_id = "TX" + sdf.format(new Date()) + CryptTool.getPasswordOfNumber(4) + per.get("id");
			
			List<SettlementAccount> settlaccountlist = SettlementService.service.getSettlAccount(per.getInt("id"));
			SettlementAccount bank = null;
			for (SettlementAccount re : settlaccountlist) {
				if (re.getInt("account_types") == Integer.parseInt(refund_types)) {
					bank = re;
				}
			}
			Refund refund = new Refund();
			refund.set("refund_id", refund_id);
			refund.set("refund_amount", withdrawals);
			refund.set("refund_actual", withdrawals);
			refund.set("refund_types", refund_types);
			refund.set("createtime", new Date());
			refund.set("explain", "提现处理中");
			refund.set("refund_state", 2);
			refund.set("id", per.get("id"));
			refund.set("settlementauthority", settlementauthority);
			refund.set("refund_fees", rate.getBigDecimal("refund"));
			refund.set("account_name", bank.get("account_name"));
			refund.set("branch", bank.get("branch"));
			refund.set("account", bank.get("account"));
			refund.set("branchsheng", bank.get("branchsheng"));
			refund.set("branchshi", bank.get("branchshi"));
			refund.set("remark", remark);
			
			if (refund_types.equals("1")) {
				refund.set("net_name", bank.get("codeid"));
			} else if (refund_types.equals("2")) {
				refund.set("net_name", bank.get("branch"));
			}
			refund.save();
			String phone = "18530113163,13288888136,13302988905";
			//String phone = "15071381706";
			String content = "【聚优支付】温馨提示:用户：【" + per.get("name") + "】于" + mydate + "提交了" + 1 + "条提现申请，合计金额为："
					+ refund_amount + "元。系统自动信息，不用回复！";
			try {
				Yunsms.sms(phone, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			BigDecimal nowAmount = settlement.subtract(withdrawals).subtract(refund_rate);
			balance.set("settlement", nowAmount);
			balance.set("waitamount", withdrawals.add(balance.getBigDecimal("waitamount")));
			balance.update();
			//保存客户提现申请的操作的日志
			AdCustomerLogService.service.saveCustomerLog(per.getInt("id"), 0,2, withdrawals,null,null,null,nowAmount);
			renderJson("{\"info\":\"提交成功！\",\"status\":\"y\"}");
		}
	}
	
	
	/**
	 * 单笔提现
	 */
	@Clear(LoginInterceptor.class)
	public void withdrawalPost() {
		try {
			getResponse().setHeader("Access-Control-Allow-Origin", "*");
			getRequest().setCharacterEncoding("gbk");
		
			int p1_MerId=0;
			if(StringUtil.isNotEmpty(getPara("p1_MerId"))){
				p1_MerId = getParaToInt("p1_MerId");
			}
			
			String key="";
			if(StringUtil.isNotEmpty(getPara("key"))){
				key = getPara("key");
			}
		
			String account="";			//账号（包括银行卡号和 支付宝账号）
			if(StringUtil.isNotEmpty(getPara("account"))){
				account = getPara("account");
			}
			String refund_amount="";		  //提现金额
			if(StringUtil.isNotEmpty(getPara("refund_amount"))){
				refund_amount = getPara("refund_amount");
			}
			String payment="";  			 //支付密码
			if(StringUtil.isNotEmpty(getPara("payment"))){
				payment = getPara("payment");
			}
			String remark=""; 				 //备注
			if(StringUtil.isNotEmpty(getPara("remark"))){
				remark = getPara("remark");
			}
			String hmac=getPara("hmac");  //验签码
			
			if(p1_MerId==0&&key==""&&account==""&&refund_amount==""&&payment==""&&remark==""||hmac==null){
				renderJson("参数错误!");
				return;
			}
			
			Person per=Person.dao.findById(p1_MerId);
			if (per!=null) {

				//获取用户的
				List<Payment> paylist = Payment.dao.find("select * from payment p where p.id='"+p1_MerId+"'");
				Payment pay = paylist.get(0);
				String myKey = pay.getStr("key");  //获取用户的秘钥
				String hmacs = DigestUtil.getHmac(new String[] { formatString(p1_MerId+""),
						formatString(key), formatString(account),
						formatString(refund_amount), formatString(payment),
						formatString(remark)}, myKey);
				if (!hmac.equals(hmacs)||!key.equals(myKey)) {
					renderJson("签名校验失败或未知错误!");
					return;
				}
				
				
				
				Balance balance = Balance.dao.findById(per.getInt("id"));
				int settlementauthority = balance.getInt("settlementauthority");  //提现方式
				//账户类型
				String refund_types=Db.queryStr(" SELECT account_types FROM settlement_account WHERE account="+account+" and id="+per.getInt("id"));
			
				//System.out.println("支付密码===" + payment);
				
				payment = MD5Utils.createMD5(payment);
				//System.out.println("支付密码加密后===" + payment);
			
				//账户可提余额
				BigDecimal settlement = balance.getBigDecimal("settlement");
				BigDecimal b = BigDecimal.valueOf(0);
				//提交的提现金额
				BigDecimal withdrawals = new BigDecimal(refund_amount);
				//提现最小金额
				BigDecimal cashleast = balance.getBigDecimal("cashleast");
				
				SimpleDateFormat format_sd = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
				Date Nowdate = new Date();
				String mydate = format_sd.format(Nowdate);
				//查询该用户的费率
				Rate rate = Rate.dao.findById(per.getInt("id"));
				//单笔提现手续费
				BigDecimal refund_rate = rate.getBigDecimal("refund");
				//可提余额-提交申请的余额-手续费
				BigDecimal lastMoney = settlement.subtract(withdrawals).subtract(refund_rate);
				
				if (StringUtil.isEmpty(refund_types)) {
					renderJson("{\"info\":\"账户未绑定！\",\"status\":\"n\"}");
				} else if (withdrawals.compareTo(cashleast) == -1) {
					renderJson("{\"info\":\"提交金额太小无法提现！\",\"status\":\"n\"}");
				} else if (settlement.compareTo(b) == -1 || settlement.compareTo(b) == 0) {
					renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
				} else if (settlement.compareTo(withdrawals) == -1) {
					renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
				} else if (!UserService.userService.getPaymentboo(per.getInt("id"), payment)) {
					renderJson("{\"info\":\"支付密码不正确！\",\"status\":\"n\"}");
				} else if(lastMoney.compareTo(b) == -1){
					renderJson("{\"statusCode\":\"300\",\"info\":\"对不起！账户可提余额不足！\",\"status\":\"n\"}");
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSSmmm");
					String refund_id = "TX" + sdf.format(new Date()) + CryptTool.getPasswordOfNumber(4) + per.get("id");
					
					List<SettlementAccount> settlaccountlist = SettlementService.service.getSettlAccount(per.getInt("id"));
					SettlementAccount bank = null;
					for (SettlementAccount re : settlaccountlist) {
						if (re.get("account").equals(account)) {
							bank = re;
						}
					}
					Refund refund = new Refund();
					refund.set("refund_id", refund_id);
					refund.set("refund_amount", withdrawals);
					refund.set("refund_actual", withdrawals);
					refund.set("refund_types", refund_types);
					refund.set("createtime", new Date());
					refund.set("explain", "提现处理中");
					refund.set("refund_state", 2);
					refund.set("id", per.get("id"));
					refund.set("settlementauthority", settlementauthority);
					refund.set("refund_fees", rate.getBigDecimal("refund"));
					refund.set("account_name", bank.get("account_name"));
					refund.set("branch", bank.get("branch"));
					refund.set("account", bank.get("account"));
					refund.set("branchsheng", bank.get("branchsheng"));
					refund.set("branchshi", bank.get("branchshi"));
					refund.set("remark", remark);
					
					if (refund_types.equals("1")) {
						refund.set("net_name", bank.get("codeid"));
					} else if (refund_types.equals("2")) {
						refund.set("net_name", bank.get("branch"));
					}
					refund.save();
					String phone = "18530113163,13288888136,13302988905";
					//String phone = "15071381706";
					String content = "【聚优支付】温馨提示:用户：【" + per.get("name") + "】于" + mydate + "提交了" + 1 + "条提现申请，合计金额为："
							+ refund_amount + "元。系统自动信息，不用回复！";
					try {
						Yunsms.sms(phone, content);
					} catch (Exception e) {
						e.printStackTrace();
					}
					BigDecimal nowAmount = settlement.subtract(withdrawals).subtract(refund_rate);
					balance.set("settlement", nowAmount);
					balance.set("waitamount", withdrawals.add(balance.getBigDecimal("waitamount")));
					balance.update();
					//保存客户提现申请的操作的日志
					AdCustomerLogService.service.saveCustomerLog(per.getInt("id"), 0,2, withdrawals,null,null,null,nowAmount);
					renderJson("{\"info\":\"SUCCESS\",\"status\":\"y\"}");
				}
				
				
			}else{
				
				renderJson("抱歉，无该用户！");
			}
		
		
		
		} catch (Exception e) {
			renderJson("未知错误!");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	/**
	 * 多笔提现
	 */
	@Before(Tx.class)
	@Clear(LoginInterceptor.class)
	public void manyWithdrawalPost() {
		
		try {
			getResponse().setHeader("Access-Control-Allow-Origin", "*");
			getRequest().setCharacterEncoding("gbk");
		
			int p1_MerId=0;
			if(StringUtil.isNotEmpty(getPara("p1_MerId"))){
				p1_MerId = getParaToInt("p1_MerId");
			}
			
			String key="";
			if(StringUtil.isNotEmpty(getPara("key"))){
				key = getPara("key");
			}
			
			
			String refund_arr=getPara("refund_arr");
			
			JSONArray ja=JSONArray.parseArray(refund_arr);
			
			
		
			
			String payment="";  			 //支付密码
			if(StringUtil.isNotEmpty(getPara("payment"))){
				payment = getPara("payment");
			}
				
			String remark=""; 				 //备注
			if(StringUtil.isNotEmpty(getPara("remark"))){
				remark = getPara("remark");
			}
			String hmac=getPara("hmac");  //验签码
			
			if(p1_MerId==0&&key==""&&payment==""&&remark==""&&refund_arr==""||hmac==null){
				renderText("参数错误!");
				return;
			}
			
			Person per=Person.dao.findById(p1_MerId);
			if (per!=null) {

				//获取用户的
				List<Payment> paylist = Payment.dao.find("select * from payment p where p.id='"+p1_MerId+"'");
				Payment pay = paylist.get(0);
				String myKey = pay.getStr("key");  //获取用户的秘钥
				
				// 签名
				StringBuffer sb=new StringBuffer();
				sb.append(formatString(p1_MerId+""));
				sb.append(formatString(key));
				sb.append(formatString(payment));
				sb.append(formatString(remark));
				
				for (int i = 0; i < ja.size(); i++) {
					JSONObject jo=new JSONObject(ja.getJSONObject(i));
					sb.append(jo.getString("account"));
					sb.append(jo.getString("refund_amount"));
					sb.append(jo.getString("refund_num"));
				}
				
				String hmacs = DigestUtil.hmacSign(sb.toString(), myKey);
				if (!hmac.equals(hmacs)||!key.equals(myKey)) {
					renderJson("签名校验失败或未知错误!");
					return;
				}
				
				payment = MD5Utils.createMD5(payment);
				
				for (int j = 0; j < ja.size(); j++) {
					JSONObject jo=new JSONObject(ja.getJSONObject(j));
					String account=jo.getString("account");			//账号（包括银行卡号和 支付宝账号）				
					String refund_amount=jo.getString("refund_amount");		  //提现金额
					int refund_num=jo.getInteger("refund_num");		  //提现笔数

					Balance balance = Balance.dao.findById(per.getInt("id"));
					int settlementauthority = balance.getInt("settlementauthority");  //提现方式
					//账户类型
					String refund_types=Db.queryStr(" SELECT account_types FROM settlement_account WHERE account="+account+" and id="+per.getInt("id"));
				
					//账户可提余额
					BigDecimal settlement = balance.getBigDecimal("settlement");
					BigDecimal b = BigDecimal.valueOf(0);
					//提交的提现金额
					BigDecimal withdrawals = new BigDecimal(refund_amount);
					//提现最小金额
					BigDecimal cashleast = balance.getBigDecimal("cashleast");
					
					SimpleDateFormat format_sd = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
					Date Nowdate = new Date();
					String mydate = format_sd.format(Nowdate);
					//查询该用户的费率
					Rate rate = Rate.dao.findById(per.getInt("id"));
					//单笔提现手续费
					BigDecimal refund_rate = rate.getBigDecimal("refund");
					
					BigDecimal sum=BigDecimal.valueOf(Double.parseDouble(refund_amount)*refund_num);
					
					//可提余额-提交申请的余额-手续费
					BigDecimal lastMoney = settlement.subtract(sum).subtract(refund_rate.multiply(BigDecimal.valueOf( refund_num)));
					
					if (StringUtil.isEmpty(refund_types)) {
						renderJson("{\"info\":\"账户未绑定！\",\"status\":\"n\"}");
					} else if (withdrawals.compareTo(cashleast) == -1) {
						renderJson("{\"info\":\"提交金额太小无法提现！\",\"status\":\"n\"}");
					} else if (settlement.compareTo(b) == -1 || settlement.compareTo(b) == 0) {
						renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
					} else if (settlement.compareTo(withdrawals) == -1) {
						renderJson("{\"info\":\"账户可用余额不足！\",\"status\":\"n\"}");
					} else if (!UserService.userService.getPaymentboo(per.getInt("id"), payment)) {
						renderJson("{\"info\":\"支付密码不正确！\",\"status\":\"n\"}");
					} else if(lastMoney.compareTo(b) == -1){
						renderJson("{\"statusCode\":\"300\",\"info\":\"对不起！账户可提余额不足！\",\"status\":\"n\"}");
					} else {
						
						List<SettlementAccount> settlaccountlist = SettlementService.service.getSettlAccount(per.getInt("id"));
						SettlementAccount bank = null;
						for (SettlementAccount re : settlaccountlist) {
							if (re.get("account").equals(account)) {
								bank = re;
							}
						}
						Refund refund = new Refund();
						
						refund.set("refund_amount", withdrawals);
						refund.set("refund_actual", withdrawals);
						refund.set("refund_types", refund_types);
						refund.set("createtime", new Date());
						refund.set("explain", "提现处理中");
						refund.set("refund_state", 2);
						refund.set("id", per.get("id"));
						refund.set("settlementauthority", settlementauthority);
						refund.set("refund_fees", rate.getBigDecimal("refund"));
						refund.set("account_name", bank.get("account_name"));
						refund.set("branch", bank.get("branch"));
						refund.set("account", bank.get("account"));
						refund.set("branchsheng", bank.get("branchsheng"));
						refund.set("branchshi", bank.get("branchshi"));
						refund.set("remark", remark);
						
						if (refund_types.equals("1")) {
							refund.set("net_name", bank.get("codeid"));
						} else if (refund_types.equals("2")) {
							refund.set("net_name", bank.get("branch"));
						}
						
						//多次提现申请
						for (int i = 0; i <refund_num ; i++) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSSmmm");
							String refund_id = "TX" + sdf.format(new Date()) + CryptTool.getPasswordOfNumber(4) + per.get("id");
							refund.set("refund_id", refund_id);
							refund.save();
						}
						
						
						String phone = "18530113163,13288888136,13302988905";
						//String phone = "15071381706";
						
						
						String content = "【聚优支付】温馨提示:用户：【" + per.get("name") + "】于" + mydate + "提交了" +refund_num + "条提现申请，单笔金额为："
								+ refund_amount + "元，合计："
								+ sum + "元。。系统自动信息，不用回复！";
						try {
							Yunsms.sms(phone, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
						
						BigDecimal nowAmount = settlement.subtract(sum).subtract(refund_rate.multiply(BigDecimal.valueOf( refund_num)));
						balance.set("settlement", nowAmount);
						balance.set("waitamount", sum.add(balance.getBigDecimal("waitamount")));
						balance.update();
						//保存客户提现申请的操作的日志
						
						for (int i = 0; i <refund_num; i++) {
							AdCustomerLogService.service.saveCustomerLog(per.getInt("id"), 0,2, withdrawals,null,null,null,nowAmount);
						}
						
						renderJson("{\"info\":\"SUCCESS\",\"status\":\"y\"}");
					}
				
				}
				
			}else{
				
				renderJson("抱歉，无该用户！");
			}
		
		
		
		} catch (Exception e) {
			renderJson("未知错误!");
			e.printStackTrace();
		}
		
		
	}
	
	

	
	/**
	 * 根据订单号查询订单
	 */
	@Clear(LoginInterceptor.class)
	public void ordersSelect() {
		
		try {
			getResponse().setHeader("Access-Control-Allow-Origin", "*");
			getRequest().setCharacterEncoding("gbk");
		
			int p1_MerId=0;
			if(StringUtil.isNotEmpty(getPara("p1_MerId"))){
				p1_MerId = getParaToInt("p1_MerId");
			}
			
			String key="";
			if(StringUtil.isNotEmpty(getPara("key"))){
				key = getPara("key");
			}
			
			String orderid=getPara("orderid");
			
			String hmac=getPara("hmac");  //验签码
			
			if(p1_MerId==0&&key==""||orderid==null||hmac==null){
				renderJson("参数错误!");
				return;
			}
			
			Person per=Person.dao.findById(p1_MerId);
			if (per!=null) {

				//获取用户的
				List<Payment> paylist = Payment.dao.find("select * from payment p where p.id='"+p1_MerId+"'");
				Payment pay = paylist.get(0);
				String myKey = pay.getStr("key");  //获取用户的秘钥
				String hmacs = DigestUtil.getHmac(new String[] { formatString(p1_MerId+""),
						formatString(key), formatString(orderid)}, myKey);
				if (!hmac.equals(hmacs)||!key.equals(myKey)) {
					renderJson("签名校验失败或未知错误!");
					return;
				}
				
				
				Order orders=Order.dao.findFirst("select * from orders where orderid='"+orderid+"' and p1_MerId="+p1_MerId);
				if(orders==null){
					renderJson("订单编号错误!");
					return;
				}
				
				String msg="";
				int state=orders.getInt("r1_Code");
				if(state==1){
					 msg="成功";	 
				}else if(state==2){
					msg="处理中";	
				}else{
					msg="失败";
				}
				
				renderJson("{\"state\":\""+state+"\", \"message\":\""+msg+"\"}");
				
			}else{
				
				renderJson("抱歉，无该用户！");
			}
		
		
		
		} catch (Exception e) {
			renderJson("未知错误!");
			e.printStackTrace();
		}
	
	}
	
	
	
	/**
	 * 根据订单号查询订单
	 */
	@Clear(LoginInterceptor.class)
	public void manyOrdersSelect() {
		
		try {
			getResponse().setHeader("Access-Control-Allow-Origin", "*");
			getRequest().setCharacterEncoding("gbk");
		
			int p1_MerId=0;
			if(StringUtil.isNotEmpty(getPara("p1_MerId"))){
				p1_MerId = getParaToInt("p1_MerId");
			}
			
			String key="";
			if(StringUtil.isNotEmpty(getPara("key"))){
				key = getPara("key");
			}
			
			String[] orderids=getParaValues("orderids");
			
			String hmac=getPara("hmac");  //验签码
			
			if(p1_MerId==0&&key==""||orderids==null||orderids.length==0||hmac==null){
				renderJson("参数错误!");
				return;
			}
			
			Person per=Person.dao.findById(p1_MerId);
			if (per!=null) {

				//获取用户的
				List<Payment> paylist = Payment.dao.find("select * from payment p where p.id='"+p1_MerId+"'");
				Payment pay = paylist.get(0);
				String myKey = pay.getStr("key");  //获取用户的秘钥
				
				StringBuffer sb=new StringBuffer();
				sb.append(formatString(p1_MerId+""));
				sb.append(formatString(key));
				for (int i = 0; i < orderids.length; i++) {
					sb.append(orderids[i]);
				}
				String hmacs = DigestUtil.hmacSign(sb.toString(), myKey);
				
				if (!hmac.equals(hmacs)||!key.equals(myKey)) {
					renderJson("签名校验失败或未知错误!");
					return;
				}
				sb=new StringBuffer("SELECT orderid,r1_Code state,( CASE WHEN r1_Code=1 THEN '成功' WHEN r1_Code=2 THEN '处理中' ELSE '失败' END  ) info FROM orders where p1_MerId="+p1_MerId+" and orderid in ( '"+orderids[0]+"'");
				for (int i = 1; i < orderids.length; i++) {
					sb.append(",'"+orderids[i]+"' ");
				}
				
				sb.append(")");
				
				List<Order> orders=Order.dao.find(sb.toString());
				if(orders==null){
					renderJson("订单编号错误!");
					return;
				}
				
				renderJson(orders);
				
			}else{
				
				renderJson("抱歉，无该用户！");
			}
		
		
		
		} catch (Exception e) {
			renderText("未知错误!");
			e.printStackTrace();
		}
	
	}
	
	public void getRefund() {
		int page = getParaToInt("refundpage");
		String refundstarttime = "";
		String refundendtime = "";
		String refundstate = "";
		String refundid = "";
		String account_name = "";
		if (!getPara("refundstarttime").equals("")) {
			refundstarttime = getPara("refundstarttime");
		}
		if (!getPara("refundendtime").equals("")) {
			refundendtime = getPara("refundendtime");
		}
		if (!getPara("refundstate").equals("0")) {
			refundstate = getPara("refundstate");
		}
		if (!getPara("refundid").equals("")) {
			refundid = getPara("refundid");
		}
		if (!getPara("account_name").equals("")) {
			account_name = getPara("account_name");
		}
		Person per = getSessionAttr("session");
		Page<Refund> orderPage = SettlementService.service.getRefund(per.getInt("id"), page, refundstarttime,refundendtime, refundstate, refundid, account_name);
		List<Refund> orderList = orderPage.getList();
		List<Object> list = new ArrayList<Object>();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		// sSystem.out.println("------Ajax启动查询数据-----"+json);
		renderJson(json);
	}
	
	@Clear
	public void showExpor() throws UnsupportedEncodingException {
		StringBuffer frosql = new StringBuffer();
		String refustarttime = getPara("refustarttime");
		String refuendtime = getPara("refuendtime");
		String refustate = getPara("refustate");
		String id = getPara("id");
		String accountname = getPara("accountname");
		accountname = new String(accountname.getBytes("ISO-8859-1"), "UTF-8");
//		System.out.println(accountname);
		//银行类型
		String bankType = getPara("bankType");
		if(StringUtil.isNotEmpty(bankType) && !bankType.equals("0")){
			bankType = new String(bankType.getBytes("ISO-8859-1"), "UTF-8");
		}
		frosql.append("SELECT * FROM refund r WHERE 1=1");
		if (!refustate.equals("0")) {
			frosql.append(" and r.refund_state=" + refustate);
		}
		if (!accountname.equals("")) {
			frosql.append(" and r.account_name like '%" + accountname + "%'");
		}
		if (!id.equals("")) {
			frosql.append(" and r.id=" + id);
		}
		if(StringUtil.isNotEmpty(bankType) && !bankType.equals("0")){
			frosql.append(" and net_name = '"+bankType+"' ");
		}
		if (!refustarttime.equals("") && !refuendtime.equals("")) {
			frosql.append(" and r.createtime >= '" + refustarttime + "' and r.createtime < '" + refuendtime + "'");
		}
		frosql.append(" ORDER BY r.createtime DESC,refund_id");
		//System.out.println("sql===="+frosql.toString());
		List<Refund> list = Refund.dao.find(frosql.toString()); // 查询数据
		// 导出
		ExportService.service.export(getResponse(), getRequest(), list);
		renderNull();
	}
	@Clear
	public void showExpor1() throws UnsupportedEncodingException {
		String starttime = getPara("starttime");
		String endtime = getPara("endtime");
		String btn = getPara("btn");
		System.out.println(new String(btn.getBytes("iso-8859-1"), "utf-8"));
		System.out.println(btn);
		List<Order> list = null;
		List<Order> wx = null;
		List<Order> ali = null;
		try {
			if (starttime != null && endtime != null) {
				if (btn.equals("") || btn.equals("0")) {
					wx = Order.dao
							.find("(select o.p1_MerId,sum(o.p3_Amt) as 'wxamt',sum(o.amount) as 'wxamo' from test.orders o WHERE o.orderid like '%WX%' and o.r1_Code=1  "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
									+ "' group by o.p1_MerId)");
					ali = Order.dao
							.find("(select o.p1_MerId,sum(o.p3_Amt) as 'aliamt',sum(o.amount) as 'aliamo' from test.orders o WHERE o.orderid like '%ALI%' and o.r1_Code=1  "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
									+ "' group by o.p1_MerId)");
					list = Order.dao
							.find("select p.id,p.name,sum(o.p3_Amt) as amt,sum(o.amount) as amount,sum(o.deducted) as deducted from person p ,test.orders o where o.r1_Code=1 and p.id=o.p1_MerId "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
									+ "' group by p.id;");
				} else if (!btn.equals("")) {
					Integer btn1 = null;

					try {
						btn1 = Integer.parseInt(btn);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					list = Order.dao
							.find("select p.id,p.name,sum(o.p3_Amt) as amt,sum(o.amount) as amount,sum(o.deducted) as deducted from person p ,test.orders o where o.r1_Code=1 and p.id=o.p1_MerId "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' "
									+ "and p.id=" + btn1 + " group by p.id;");
					wx = Order.dao
							.find("(select o.p1_MerId,sum(o.p3_Amt) as 'wxamt',sum(o.amount) as 'wxamo' from test.orders o WHERE o.orderid like '%WX%' and o.r1_Code=1  "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' "
									+ "and o.p1_MerId=" + btn1 + " group by o.p1_MerId)");
					ali = Order.dao
							.find("(select o.p1_MerId,sum(o.p3_Amt) as 'aliamt',sum(o.amount) as 'aliamo' from test.orders o WHERE o.orderid like '%ALI%' and o.r1_Code=1  "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' "
									+ "and o.p1_MerId=" + btn1 + " group by o.p1_MerId)");
				}
			}

			if (list != null) {
				for (Order lis : list) {
					lis.put("wxamt", "0.000");
					lis.put("wxamo", "0.000");
					lis.put("aliamo", "0.000");
					lis.put("aliamt", "0.000");
					int lisid = lis.getInt("id");
					if (wx != null) {
						for (Order w : wx) {
							if (lisid == w.getInt("p1_MerId")) {
								String wxamt = w.get("wxamt").toString();
								String wxamo = w.get("wxamo").toString();
								if (wxamt.equals("") || wxamt == null) {
									lis.put("wxamt", "0.000");
								}
								if (wxamo.equals("") || wxamo == null) {
									lis.put("wxamo", "0.000");
								}
								if (!(wxamt.equals("") || wxamt == null)) {
									lis.put("wxamt", wxamt);
								}
								if (!(wxamo.equals("") || wxamo == null)) {
									lis.put("wxamo", wxamo);
								}
								break;
							}
						}
					}
					if (ali != null) {
						for (Order al : ali) {
							if (lisid == al.getInt("p1_MerId")) {
								String aliamt = al.get("aliamt").toString();
								String aliamo = al.get("aliamo").toString();
								lis.put("aliamt", aliamt);
								lis.put("aliamo", aliamo);
								if (aliamt.equals("") || aliamt == null) {
									lis.put("aliamt", "0.000");
								}
								if (aliamo.equals("") || aliamo == null) {
									lis.put("aliamo", "0.000");
								}
								if (!(aliamt.equals("") || aliamt == null)) {
									lis.put("aliamt", aliamt);
								}
								if (!(aliamo.equals("") || aliamo == null)) {
									lis.put("aliamo", aliamo);
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据
		// 导出
		System.out.println(1);
//		ExportService.service.export1(getResponse(), getRequest(), list);
		renderNull();
	}

	@Clear
	public void showExpor2() throws UnsupportedEncodingException {
		try {
			List<Gateway> list = null;
			String starttime = getPara("starttime");
			String endtime = getPara("endtime");
			String btn = getPara("btn");
			if (btn.equals("wx")) {
				list = Gateway.dao
						.find("select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,test.orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id and g.wxwap='y' and o.orderid like '%WX%'"
								+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
								+ "' group by g.gateway_id ;");
			} else if (btn.equals("ali")) {
				list = Gateway.dao
						.find("select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,test.orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id and g.alipaywap='y' and o.orderid like '%ALI%'"
								+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
								+ "' group by g.gateway_id ;");
			} else if (btn.equals("all")) {
				list = Gateway.dao
						.find("select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,test.orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id "
								+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
								+ "' group by g.gateway_id ;");
			}
//			ExportService.service.export2(getResponse(), getRequest(), list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据
		// 导出
		renderNull();
	}

	public void checkPayment() {
		boolean flag = false;
		String str = "{\"info\":\"支付正确！\",\"status\":\"y\"}";
		Person per = getSessionAttr("session");
		String payment = getPara("payment");
		System.out.println("支付密码===" + payment);
		try {
			payment = MD5Utils.createMD5(payment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("支付密码加密后===" + payment);
		flag = UserService.userService.getPaymentboo(per.getInt("id"), payment);
		if (!flag) {
			str = "{\"info\":\"支付密码不正确！\",\"status\":\"n\"}";
			renderJson(str);
		} else {
			renderJson(str);
		}
	}

	public void importExcel() {
		Person per = getSessionAttr("session");
		System.out.println("Now begin to import Excel-");
		// 获取文件
		try {
			UploadFile file = getFile("excelexcel");
			System.out.println(file);
			String path = file.getUploadPath() + File.separator + file.getFileName();
			String payment = getPara("payment");
			payment = MD5Utils.createMD5(payment);
			// 判断是否重复提交
			boolean bootoken = validateToken("withdrawalToken");
			bootoken=true;
			int p1_MerId = per.getInt("id");// 获得用户ID
			Rate rate = Rate.dao.findById(p1_MerId);// 获得用户费率
			BigDecimal refund_rate = rate.getBigDecimal("refund");
			if (!bootoken) {
				renderJson("{\"info\":\"请不要重复提交！\",\"status\":\"n\"}");
			} else if (!UserService.userService.getPaymentboo(per.getInt("id"), payment)) {
				renderJson("{\"info\":\"支付密码不正确！\",\"status\":\"n\"}");
			} else if (bootoken) {
				// 处理导入数据
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssmmm");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String mydate = df.format(date);
				BigDecimal all_amount = new BigDecimal("0");// 提现金额
				int i = 0;
				boolean flag = false;
				List<Map<Integer, String>> list = dealDataByPath(path); // 分析EXCEL数据
				System.out.println("list.size====" + list.size());
				int gs = 1;
				for (Map<Integer, String> map : list) { // 遍历取出的数据，并保存
					Refund refund = new Refund();
					String refund_id = "DF" + sdf.format(new Date()) + CryptTool.getPasswordOfNumber(4) + p1_MerId + gs;// 1.生成订单号
					Map<Integer, String> r_map = list.get(i);
					System.out.println(r_map);
					String account_name = r_map.get(0);
					String account = r_map.get(1);
					String refund_amount = r_map.get(2);
					BigDecimal withdrawals = new BigDecimal(refund_amount);// 提现金额.
					all_amount = all_amount.add(withdrawals);
					String branchsheng = r_map.get(3);
					String branchshi = r_map.get(4);
					String branch = r_map.get(5);// 开户支行
					String net_name = r_map.get(6);// 银行名称
					String remark = r_map.get(7);// 备注信息
					// String bankname = net_name;
					// if(bankname.equalsIgnoreCase("微信")){
					// bankname = "微信";
					// }else if(bankname.equalsIgnoreCase("支付宝")){
					// bankname = "支付宝";
					// }else{
					// bankname = "网银";
					// }
					gs++;
					SettlementDao sett = new SettlementDao();
					// int refund_types = sett.getSettlAccountTypes(p1_MerId,
					// bankname);//根据账户ID和银行类型（如微信支付）查找出提款1：不限；2：一天只能提一次
					int refund_types = 0;// 默认银行代付
					// Balance b = new Balance();
					Balance balance = Balance.dao.findById(p1_MerId);
					int authority = balance.getInt("settlementauthority"); // 确定账号提现权限1：T+0；2：T+1

					refund.set("refund_id", refund_id);
					refund.set("refund_amount", refund_amount);
					refund.set("refund_actual", withdrawals);
					refund.set("refund_types", refund_types);
					refund.set("createtime", date);
					refund.set("explain", "失效数据");
					refund.set("refund_state", 3);// 提现状态1：已处理；2：处理中；3:未提交
					refund.set("id", p1_MerId);
					refund.set("settlementauthority", authority);// 1代表一天只能提款一次，2:无限制；
					refund.set("refund_fees", rate.getBigDecimal("refund"));// 每一笔的手续费
					refund.set("account_name", account_name);
					refund.set("branch", branch);
					refund.set("account", account);
					refund.set("branchsheng", branchsheng);
					refund.set("branchshi", branchshi);
					refund.set("net_name", net_name);
					refund.set("remark", remark);
					
					i++;
					if (refund.save()) {
						flag = true;
					} else {
						flag = false;
					}
				}
				String sql = "select * from refund where id=" + p1_MerId + " and refund_state=3 and createtime='"+ mydate + "'";
				System.out.print("打印查询的SQL:" + sql);
				List<Refund> refunds = Refund.dao.find(sql);
				setAttr("refunds", refunds);
				setAttr("user_id", p1_MerId);
				setAttr("createtime", mydate);
				setAttr("refund_state", 3);
				setAttr("list", "in apctive");
				setAttr("result", "in active");
				Balance c = Balance.dao.findById(p1_MerId);
				BigDecimal old_settlement = c.getBigDecimal("settlement");
				//提交的条数
				int count = list.size();
				BigDecimal bigdecimalCount = new BigDecimal(count);
				System.out.println("bcount====="+bigdecimalCount);
				//总手续费
				BigDecimal all_refund_rate = refund_rate.multiply(bigdecimalCount);
				//扣除手续费后的账户余额
				BigDecimal now_settlement = old_settlement.subtract(all_amount).subtract(all_refund_rate);
				setAttr("count",count);
				setAttr("old_settlement", old_settlement);
				//提交的总金额
				setAttr("amount", all_amount);
				//提交申请后的账户余额
				setAttr("now_settlement", now_settlement);
				//提现单笔手续费
				setAttr("refund_rate", refund_rate);
				//提现总手续费
				setAttr("all_refund_rate", all_refund_rate);
				System.out.print("得到数据" + count + "条");
				createToken("withdrawalToken", 30 * 60);
				render("/WEB-INF/jsp/list.jsp");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			renderJson("{\"info\":\"请先上传需要代付的excel数据！\",\"status\":\"n\"}");
		}
	}

	public void ViewResult() {
		try {
			String user_id = getPara("user_id");
			String refund_state = getPara("refund_state");
			String createtime = getPara("createtime");
			//本次提现总的手续费
			String all_refund_rate = getPara("all_refund_rate");
			//构造以字符串内容为值的BigDecimal类型的变量bd   
			BigDecimal bRate = new BigDecimal(all_refund_rate);   
			//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			bRate = bRate.setScale(3, BigDecimal.ROUND_HALF_UP);
			System.out.println("brate===="+bRate);
			BigDecimal all_amount = Db.queryBigDecimal("select SUM(refund_amount)  from refund where id=" + user_id
					+ " and refund_state=" + refund_state + " and createtime='" + createtime + "'");
			//提现合计金额
			all_amount = all_amount.setScale(2);
			System.out.println("1.打印本次提现合计金额:" + all_amount);

			long count = Db.queryLong("select Count(id) from refund where id=" + user_id + " and refund_state="
					+ refund_state + " and createtime='" + createtime + "'");

			System.out.println("传递过来的参数是=:" + all_amount + "-------count-------" + count);

			Balance balance = Balance.dao.findById(user_id);
			//账户可提余额
			BigDecimal old_settlement = balance.getBigDecimal("settlement");
			System.out.println("1.打印账户下的可提金额:" + old_settlement);

			BigDecimal now_sttlement = old_settlement.subtract(all_amount);
			//账户余额=可以余额-提现金额-手续费
			now_sttlement = now_sttlement.subtract(bRate);
			System.out.println("2.打印账户下的最新可提金额减掉手续费之后的:" + now_sttlement);
			// BigDecimal waitamount = old_settlement.subtract(all_amount);
			//判断客户的余额是否大于等于0
			if (now_sttlement.compareTo(new BigDecimal(0)) >= 0) {
				StringBuffer sql = new StringBuffer();
				sql.append("update refund set refund_state=2,`explain`=' 代付申请单 ' where id=" + user_id + " and refund_state="
						+ refund_state + " and createtime='" + createtime + "'");
				long l = Db.update(sql.toString());
				if (l >= 1) {
					// 在等待金额需要增加
					// 可提金额减少
					// 实际金额不变
					long db = Db.update("update balance b set b.settlement=" + now_sttlement + ",b.waitamount=b.waitamount+"
							+ all_amount + " where id=" + user_id + "");
					//System.out.print("============db===============：" + db);
					if (db > 0) {
						Person per = getSessionAttr("session");
						String user_name = per.getStr("name");
						//System.out.print("============webName===============" + user_name);
						String phone = "18530113163,13288888136,13302988905";
						//String phone = "15071381706";
						String content = "【聚优支付】温馨提示:用户：【" + user_name + "】于" + createtime + "提交了" + count + "条代付申请，合计金额为："
								+ all_amount + "元。系统自动信息，不用回复！";
						try {
							Yunsms.sms(phone, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//保存客户代付申请的日志
						AdCustomerLogService.service.saveCustomerLog(per.getInt("id"), 1,2, all_amount,null,null,null,now_sttlement);
						setAttr("count", count);
						setAttr("old_settlement", old_settlement);
						setAttr("amount", all_amount);
						setAttr("now_settlement", now_sttlement);
						setAttr("list", "");
						setAttr("result", "in active");
						setAttr("all_refund_rate",all_refund_rate);
						setAttr("LastResult", "恭喜您，本次操作成功！");
						render("/WEB-INF/jsp/list.jsp");
					} else {
						renderText("发生未知错误，请联系客服");
					}

				} else {
					renderText("代付不允许1条付款");
				}
			} else {
				renderText("可提余额不足");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void daifu() {
		Person per = getSessionAttr("session");
		createToken("withdrawalToken", 30 * 60);
		setAttr("refund_rate", Rate.dao.findById(per.getInt("id")));
		renderJsp("/WEB-INF/jsp/mydaifu.jsp");
	}

	/**
	 * 分析excel的内容
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("deprecation")
	private List<Map<Integer, String>> dealDataByPath(String path) {
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		// 工作簿
		HSSFWorkbook hwb = null;
		XSSFWorkbook xwb = null;
		try {
			hwb = new HSSFWorkbook(new FileInputStream(new File(path)));
			if (hwb != null) {
				useHSSFWorkbook(list, hwb);
			}
		} catch (Exception e) {
			try {
				xwb = new XSSFWorkbook(path);
				useXSSFWorkbook(list, xwb);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("return list;");
		return list;
	}

	private void useHSSFWorkbook(List<Map<Integer, String>> list, HSSFWorkbook hwb) {
		HSSFSheet sheet = hwb.getSheet("Sheet1");
		int r = sheet.getLastRowNum();
		for (int i = 1; i < r; i++) {// 第二行开始取值，第一行为标题行
			HSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
			Map<Integer, String> map = new HashMap<Integer, String>();
			int f = 0;
			for (short j = 0; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)
				// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (cell != null) {
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if (String.valueOf(cell.getNumericCellValue()) != null
								&& !String.valueOf(cell.getNumericCellValue()).equalsIgnoreCase("")) {
							map.put((int) j, String.valueOf(cell.getNumericCellValue()));
						} else {
							f = 1;
						}
					} else {
						if (cell.getStringCellValue() != null && !cell.getStringCellValue().equalsIgnoreCase("")) {
							map.put((int) j, cell.getStringCellValue());
						} else {
							f = 1;
						}
					}
				}
			}
			if (f == 0) {
				list.add(map);
			} else {
				break;
			}
		}
	}

	private void useXSSFWorkbook(List<Map<Integer, String>> list, XSSFWorkbook xwb) {
		System.out.println("web----" + xwb);
		try {
			XSSFSheet sheet = xwb.getSheet("Sheet1");
			// int r = sheet.getPhysicalNumberOfRows();
			int r = sheet.getLastRowNum();
			for (int i = 1; i < r; i++) {// 第二行开始取值，第一行为标题行
				XSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
				Map<Integer, String> map = new HashMap<Integer, String>();
				int f = 0;
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)
					// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (cell != null) {
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							if (String.valueOf(cell.getNumericCellValue()) != null
									&& !String.valueOf(cell.getNumericCellValue()).equalsIgnoreCase("")) {
								map.put(j, String.valueOf(cell.getNumericCellValue()));
							} else {
								f = 1;
							}
						} else {
							if (cell.getStringCellValue() != null && !cell.getStringCellValue().equalsIgnoreCase("")) {
								map.put(j, cell.getStringCellValue());
							} else {
								f = 1;
							}
						}
					}
				}
				if (f == 0) {
					list.add(map);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download() {
		try {
			String ExcelPath = PathKit.getWebRootPath() + "\\download\\RefundTable.xlsx";
			File excelFile = new File(ExcelPath);
			if (excelFile.isFile()) {
				renderFile(excelFile);
			} else {
				System.out.println("文件不存在或者路劲不正确" + ExcelPath);
			}
		} catch (Exception e) {
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
