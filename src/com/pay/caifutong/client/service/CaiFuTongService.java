package com.pay.caifutong.client.service;

import java.math.BigDecimal;

import com.pay.caifutong.client.dao.CaiFuTongDao;
import com.vo.CaifutongOrder;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Order;
import com.vo.Rate;
import com.vo.Recharge;

public class CaiFuTongService extends CaiFuTongDao {

	public static CaiFuTongService service = new CaiFuTongService();
	
	public void caifutongOrder(String sp_billno, String transaction_id, String purchaser_id, String date, String total_fee, String bus_args){
	    
		CaifutongOrder caifuOrder =  CaifutongOrder.dao.findById(sp_billno);
		if(caifuOrder==null){
		    caifuOrder = new CaifutongOrder();
	        caifuOrder.set("sp_billno", sp_billno);
	        caifuOrder.set("transaction_id", transaction_id);
	        caifuOrder.set("purchaser_id", purchaser_id);
	        caifuOrder.set("date", date);
	        caifuOrder.set("total_fee", new BigDecimal(total_fee).divide(BigDecimal.valueOf(100)));
	        caifuOrder.set("bus_args", bus_args);
	        caifuOrder.set("pay_result", "2");
	        caifuOrder.save();
		}else{
            caifuOrder.set("transaction_id", transaction_id);
            caifuOrder.set("purchaser_id", purchaser_id);
            caifuOrder.set("date", date);
            caifuOrder.set("total_fee", new BigDecimal(total_fee).divide(BigDecimal.valueOf(100)));
            caifuOrder.set("bus_args", bus_args);
            caifuOrder.set("pay_result", "2");
            caifuOrder.update();
		}
	}
	
	public void caifutongOrderreturn(String sp_billno, String pay_result, String pay_info, String refund_id){
		CaifutongOrder caifuorder = CaifutongOrder.dao.findById(sp_billno);
		caifuorder.set("pay_result", pay_result);
		caifuorder.set("pay_info", pay_info);
		caifuorder.set("refund_id", refund_id);
		caifuorder.update();
	}
	
	public void netcallback(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime){
		Order order = Order.dao.findById(r6_Order);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");
			BigDecimal banking = Rate.dao.findById(p1_MerId).getBigDecimal("banking");
			BigDecimal amount = p3_Amt.multiply(banking);
			BigDecimal deducted = p3_Amt.subtract(amount);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			order.update();
			Employee_Person empper = Employee_Person.dao.findById(p1_MerId);
			if(empper!=null){
				int employeeid = empper.getInt("employeeid");
				Employees emp = Employees.dao.findById(employeeid);
				emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(deducted.multiply(emp.getBigDecimal("commission"))));
				emp.update();
			}
		}
	}
	
	public void paymentnetcallback(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime){
		Recharge order = Recharge.dao.findById(r6_Order);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");
			BigDecimal banking = Rate.dao.findById(p1_MerId).getBigDecimal("banking");
			BigDecimal amount = p3_Amt.multiply(banking);
			BigDecimal deducted = p3_Amt.subtract(amount);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			order.update();
			Employee_Person empper = Employee_Person.dao.findById(p1_MerId);
			if(empper!=null){
				int employeeid = empper.getInt("employeeid");
				Employees emp = Employees.dao.findById(employeeid);
				emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(deducted.multiply(emp.getBigDecimal("commission"))));
				emp.update();
			}
		}
	}
}