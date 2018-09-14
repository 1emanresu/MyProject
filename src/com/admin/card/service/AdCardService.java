package com.admin.card.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.admin.card.dao.AdCardDao;
import com.vo.Balance;
import com.vo.CardOrder;

public class AdCardService extends AdCardDao {
	public static AdCardService service = new AdCardService();
	
	public boolean carddeduction(String cardorderid){
		boolean boo = false;
		CardOrder order = CardOrder.dao.findById(cardorderid);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code==1){
			int p1_MerId = order.getInt("p1_MerId");
			BigDecimal amount = order.getBigDecimal("amount");
			BigDecimal deducted = order.getBigDecimal("deducted");
			Balance balance = Balance.dao.findById(p1_MerId);
			balance.set("amount", balance.getBigDecimal("amount").subtract(amount));
			balance.set("cardamount", balance.getBigDecimal("cardamount").subtract(amount));
			balance.set("deductedcard", balance.getBigDecimal("deductedcard").subtract(deducted));
			Date carddate = order.getTimestamp("CreateTime");
			if(balance.getInt("settlementauthority")==2){
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				String cardda = formatter.format(carddate);
				String da = formatter.format(date);
				if(cardda.equals(da)){
					balance.set("newamount", balance.getBigDecimal("newamount").subtract(amount));
				}else{
					balance.set("settlement", balance.getBigDecimal("settlement").subtract(amount));
				}
			}else if(balance.getInt("settlementauthority")==1){
				balance.set("settlement", balance.getBigDecimal("settlement").subtract(amount));
			}
			order.set("lock", 2);
			boo = order.update() && balance.update();
		}
		return boo;
	}
	
	public boolean cardRecovery(String cardorderid){
		boolean boo = false;
		CardOrder order = CardOrder.dao.findById(cardorderid);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code==1){
			int p1_MerId = order.getInt("p1_MerId");
			BigDecimal amount = order.getBigDecimal("amount");
			BigDecimal deducted = order.getBigDecimal("deducted");
			Balance balance = Balance.dao.findById(p1_MerId);
			balance.set("amount", balance.getBigDecimal("amount").add(amount));
			balance.set("cardamount", balance.getBigDecimal("cardamount").add(amount));
			balance.set("deductedcard", balance.getBigDecimal("deductedcard").add(deducted));
			Date carddate = order.getTimestamp("CreateTime");
			if(balance.getInt("settlementauthority")==2){
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				String cardda = formatter.format(carddate);
				String da = formatter.format(date);
				if(cardda.equals(da)){
					balance.set("newamount", balance.getBigDecimal("newamount").add(amount));
				}else{
					balance.set("settlement", balance.getBigDecimal("settlement").add(amount));
				}
			}else if(balance.getInt("settlementauthority")==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));
			}
			order.set("lock", 1);
			boo = order.update() && balance.update();
		}
		return boo;
	}
}
