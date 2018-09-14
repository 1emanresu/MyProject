package com.pay.cib.client.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.jfinal.plugin.activerecord.Db;
import com.pay.cib.client.dao.CibDao;
import com.vo.Asynchronous;
import com.vo.Balance;
//import com.vo.Employee_Person;
//import com.vo.Employees;
import com.vo.Order;
import com.vo.Payment;
import com.vo.Rate;
import com.yeepay.DigestUtil;

public class CibService extends CibDao{

	public static CibService service = new CibService();	

	public boolean netcallback(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");
			System.out.println("充值金额是=："+p3_Amt+"-----对应的商户ID号是=："+p1_MerId);			
			BigDecimal rate = new BigDecimal("0.00");
			if(srvice.equals("pay.weixin.native")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("netease");
				System.out.println("-------充值类似是native的费率是------"+rate);
			}else{
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("travel");
				System.out.println("-------这里是其他充值方式，并且费率是：------"+rate);
			}

			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);

			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;
				System.out.println("-------订单表更新成功！！！！！！！！！！------");
			}else{
				flag=false;
				status=false;
				System.out.println("-------订单表更新失败！！！！！！！！！！------");
			}

			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){
				System.out.println("-------用户账户表更新成功！！！！！！！！！！------");
				status = true;
			}else{
				status=false;
				System.out.println("-------用户账户表更新失败！！！！！！！！！！------");
			}
			//代理费用暂时不考虑
			//			Employee_Person empper = Employee_Person.dao.findById(p1_MerId);
			//			if(empper!=null){
			//				int employeeid = empper.getInt("employeeid");
			//				Employees emp = Employees.dao.findById(employeeid);
			//				emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(amount.multiply(emp.getBigDecimal("commission"))));
			//				emp.update();
			//			}
		}
		return status;
	}

	public boolean SwiftpassUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");//客户编号					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);			
			System.out.println("得到传递的通道是==："+gateway_id);

			if(gateway_id>0){
				if(pd_FrpId.equals("pay.alipay.native") || pd_FrpId.equals("PAY.ALIPAY.NATIVE")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("shenzhouxing");					
				}else if(pd_FrpId.equals("pay.weixin.wappay") || pd_FrpId.equals("PAY.WEIXIN.WAPPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("telecom");
				}else{//默认微信H5支付
					rate = new BigDecimal("0.978");
				}
			}else{
				rate = new BigDecimal("0.978");//不是这个通道的哈
			}

			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt",p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}		

			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}

	public boolean CibUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");//客户编号					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);			
			System.out.println("得到传递的通道是==："+gateway_id);

			//if(gateway_id==16 || gateway_id==15 || gateway_id==14){
			if(pd_FrpId.equals("pay.weixin.native") || pd_FrpId.equals("PAY.WEIXIN.NATIVE")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("junwang");					
			}else if(pd_FrpId.equals("pay.weixin.app") || pd_FrpId.equals("PAY.WEIXIN.APP")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("tianhong");					
			}else if(pd_FrpId.equals("pay.alipay.app") || pd_FrpId.equals("PAY.ALIPAY.APP")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("tianhong");
			}else if(pd_FrpId.equals("pay.alipay.native") || pd_FrpId.equals("PAY.ALIPAY.NATIVE")){
				String alism = r6_Order.substring(0, 6);
				if(alism.equals("CIBASM")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");
				}else{
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("sohu");
				}
				
			}else{
				new BigDecimal("0.98");//通道
			}
			if(rate==null){
				rate = new BigDecimal("0.98");
			}

			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt",p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}

			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}
	
	public boolean MustUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");//客户编号					
			BigDecimal rate = new BigDecimal("0.98");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//if(gateway_id==16 || gateway_id==15 || gateway_id==14){
			if(pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIPAYWAP")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("mustali");					
			}else if(pd_FrpId.equals("aliwap") || pd_FrpId.equals("ALIWAP")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("mustali");
			}else if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
				rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");				
			}else{
				rate = new BigDecimal("0.98");//通道
			}
			if(rate==null){
				rate = new BigDecimal("0.98");
			}
			System.out.println("该通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt",p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}

			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}

	public boolean zfapiUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//			PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//			Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//			int function = gateway.getInt("gateway_function");
			System.out.println("得到传递的通道是==："+gateway_id);
			if(gateway_id==17){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");					
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("travel");					
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("travel");
				}
			}else if(gateway_id==18  || gateway_id==25){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("qqcoins");			 		
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("ALIWAP") || pd_FrpId.equals("aliwap")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("journey");					
				}else if(pd_FrpId.equals("WEIXIN") || pd_FrpId.equals("weixin")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("longitudinal");//微信扫码费率
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("0.97");
				}
			}else{
				rate = new BigDecimal("0.97");
			}			

			BigDecimal amount = p3_Amt.multiply(rate).setScale(3);
			BigDecimal deducted = p3_Amt.subtract(amount).setScale(3);	
			order.set("p3_Amt",p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", "");
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);			
			String sql = "UPDATE  orders set amount="+amount+",deducted="+deducted+",p3_Amt="+p3_Amt+",ro_BankOrderId='"+ro_BankOrderId+"',r2_TrxId='"+r2_TrxId+"',r1_Code=1 where orderid='"+r6_Order+"'";
			System.out.println("SQL=:"+sql);
			int f = Db.update(sql);			
			if(f>0){
				flag = true;
				System.out.println("更新数据库成功！！！");
				status=true;				
			}else{
				flag=false;
				status=false;
				System.out.println("更新数据库失败！！！");
			}

			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){
				System.out.println("更新数据库成功22222222！！！");
				status = true;
			}else{
				System.out.println("更新数据库失败2222222222！！！");
				status=false;				
			}
		}
		return status;
	}

	public boolean Fz222Update(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");

		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//int gateway_id = pergetGateway.getInt("gateway_id");
			//int function = gateway.getInt("gateway_function");			
			if(gateway_id==19){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wx") || pd_FrpId.equals("WX")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");	
					System.out.println("微信传递的通道费率是=："+rate);
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("theworld");	
					System.out.println("支付宝传递的通道费率是=："+rate);
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("grand");
					System.out.println("传递的通道编码错误："+rate);
				}
			}else{
				rate = new BigDecimal("0.97");
			}
			System.out.println("传递的通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt", p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}
			System.out.println("------------------------------------------------------------------------");
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}
	
	public boolean GengYouFuUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");

		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//int gateway_id = pergetGateway.getInt("gateway_id");
			//int function = gateway.getInt("gateway_function");			
			if(gateway_id==28){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wx") || pd_FrpId.equals("WX")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");	
					System.out.println("微信传递的通道费率是=："+rate);
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("theworld");	
					System.out.println("支付宝传递的通道费率是=："+rate);
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("grand");
					System.out.println("传递的通道编码错误："+rate);
				}
			}else{
				rate = new BigDecimal("0.97");
			}
			System.out.println("传递的通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt", p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}			
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}	
	
	public boolean XINFUTONGUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");

		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//int gateway_id = pergetGateway.getInt("gateway_id");
			//int function = gateway.getInt("gateway_function");			
			if(gateway_id==29){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wx") || pd_FrpId.equals("WX")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");	
					System.out.println("微信传递的通道费率是=："+rate);
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("theworld");	
					System.out.println("支付宝传递的通道费率是=："+rate);
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("grand");
					System.out.println("传递的通道编码错误："+rate);
				}
			}else{
				rate = new BigDecimal("0.97");
			}
			System.out.println("传递的通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt", p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}			
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}
	
	public boolean JyypayUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");

		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//int gateway_id = pergetGateway.getInt("gateway_id");
			//int function = gateway.getInt("gateway_function");			
			if(gateway_id==41){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wx") || pd_FrpId.equals("WX")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");	
					System.out.println("微信传递的通道费率是=："+rate);
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("theworld");	
					System.out.println("支付宝传递的通道费率是=："+rate);
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("grand");
					System.out.println("传递的通道编码错误："+rate);
				}
			}else{
				rate = new BigDecimal("0.97");
			}
			System.out.println("传递的通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt", p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}			
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}
	
	public boolean QifpayUpdate(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
		Order order = Order.dao.findById(r6_Order);
		boolean flag = false;
		boolean  status =false;
		int r1_Code = order.getInt("r1_Code");
		String pd_FrpId = order.getStr("pd_FrpId");

		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");					
			BigDecimal rate = new BigDecimal("0.00");
			BigDecimal sys_Amt = new BigDecimal(r3_Amt);

			if(p3_Amt.compareTo(sys_Amt)>0){//避免实际充值金额比记录金额小的情况
				p3_Amt = sys_Amt;
			}
			System.out.println("得到传递的金额是==："+p3_Amt);

			//PersonGateway pergetGateway = PersonGateway.dao.findById(p1_MerId);
			//Gateway gateway = Gateway.dao.findById(pergetGateway.getInt("gateway_id"));
			//int gateway_id = pergetGateway.getInt("gateway_id");
			//int function = gateway.getInt("gateway_function");			
			if(gateway_id==42){
				if(pd_FrpId.equals("wxwap") || pd_FrpId.equals("WXWAP") || pd_FrpId.equals("wx") || pd_FrpId.equals("WX")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");	
					System.out.println("微信传递的通道费率是=："+rate);
				}else if(pd_FrpId.equals("ALIPAYWAP") || pd_FrpId.equals("alipaywap") || pd_FrpId.equals("alipay") || pd_FrpId.equals("ALIPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("theworld");	
					System.out.println("支付宝传递的通道费率是=："+rate);
				}else{//默认微信H5支付
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("grand");
					System.out.println("传递的通道编码错误："+rate);
				}
			}else{
				rate = new BigDecimal("0.97");
			}
			System.out.println("传递的通道的费率是=："+rate);
			BigDecimal amount = p3_Amt.multiply(rate);
			BigDecimal deducted = p3_Amt.subtract(amount);	
			order.set("p3_Amt", p3_Amt);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			if(order.update()){
				flag = true;
				status=true;				
			}else{
				flag=false;
				status=false;				
			}			
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");//结算权限1：T+0;2：T+1
			balance.set("amount", balance.getBigDecimal("amount").add(amount));//充值金额累加			
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));//网银金额累加，本通道仅仅支持网银模式			
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));//可提现金额累加
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));//可结算金额
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));//新进资金，但暂时不能结算
			}
			if(flag && balance.update()){				
				status = true;
			}else{
				status=false;				
			}
		}
		return status;
	}

	public boolean asynchronous(String r6_Order){
		boolean boo = false;
		try {
			for(int i=0; i<5; i++){
				Order order = Order.dao.findById(r6_Order);
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
				sValue.append("2");
				String sNewString =  DigestUtil.hmacSign(sValue.toString(), payment.getStr("key"));
				HttpClient hClient = new HttpClient();
				//hClient.getParams().setContentCharset( "GBK "); 
				HttpConnectionManagerParams managerParams = hClient
						.getHttpConnectionManager().getParams();
				// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);
				// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);

				String r5_Pid = formatString(order.getStr("p5_Pid"));

				r5_Pid = java.net.URLEncoder.encode(r5_Pid,"gbk");

				String r8_MP = formatString(order.getStr("pa_MP"));

				r8_MP = java.net.URLEncoder.encode(r8_MP,"gbk");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String url = "?p1_MerId="+order.getInt("p1_MerId").toString();
				url += "&r0_Cmd="+order.getStr("p0_Cmd");
				url += "&r1_Code="+order.getInt("r1_Code").toString();
				url += "&r2_TrxId="+order.getStr("orderid");
				url += "&r3_Amt="+order.getBigDecimal("p3_Amt").toString();
				url += "&r4_Cur="+order.getStr("p4_Cur");
				url += "&r5_Pid="+r5_Pid;
				url += "&r6_Order="+order.getStr("p2_Order");
				url += "&r7_Uid=";
				url += "&r8_MP="+r8_MP;
				url += "&r9_BType=2";
				url += "&rb_BankId="+order.getStr("pd_FrpId");
				url += "&ro_BankOrderId="+order.getStr("ro_BankOrderId");
				url += "&rp_PayDate="+format.format(order.getTimestamp("rp_PayDate"));
				url += "&rq_CardNo=";
				url += "&ru_Trxtime="+format.format(order.getTimestamp("ru_Trxtime"));
				url += "&hmac="+sNewString;
				//System.out.println(r6_Order+"=========异步通知开始=========="+order.getStr("p8_Url")+url);
				Asynchronous asynchronous = Asynchronous.dao.findById(r6_Order);
				if(asynchronous==null){
					asynchronous = new Asynchronous();
					asynchronous.set("orderid", r6_Order);
					asynchronous.set("url", order.getStr("p8_Url")+url);
					asynchronous.save();
				}else{
					asynchronous.set("url", order.getStr("p8_Url")+url);
					asynchronous.update();
				}
				PostMethod post = null;
				post = new PostMethod(order.getStr("p8_Url")+url);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=GBK");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				String[] re = returnStr.split("\n");
				for(String st:re){
					st = st.trim();
					if(st.length()>7){
						st = st.substring(0, 7);
					}
					if(st.toUpperCase().equals("SUCCESS")){
						order.set("success", 1);
						order.update();
						boo = true;
						//System.out.println(r6_Order+"===================异步通知成功");
						break;
					}
				}
				if(boo){
					//System.out.println(r6_Order+"===================异步通知成功");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boo;
	}

	public void failure(String r6_Order, String ru_Trxtime){
		Order order = Order.dao.findById(r6_Order);
		order.set("r1_Code", 3);
		order.set("ru_Trxtime", ru_Trxtime);
		order.update();
	}

	public String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}
}

