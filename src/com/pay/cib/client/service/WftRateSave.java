package com.pay.cib.client.service;

import java.math.BigDecimal;

import com.vo.Balance;
import com.vo.Order;
import com.vo.Rate;

public class WftRateSave {
	public boolean SwiftpassUpdate2(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime,String srvice,String r3_Amt,int gateway_id){
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
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("mustali");					
				}else if(pd_FrpId.equals("pay.weixin.wappay") || pd_FrpId.equals("PAY.WEIXIN.WAPPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("cibsm");
				}else if(pd_FrpId.equals("pay.tenpay.wappay") || pd_FrpId.equals("PAY.TENPAY.WAPPAY")){
					rate = Rate.dao.findById(p1_MerId).getBigDecimal("qqwx");
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
}
