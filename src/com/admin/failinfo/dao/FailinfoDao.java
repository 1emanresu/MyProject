package com.admin.failinfo.dao;

import java.math.BigDecimal;

import com.jfinal.plugin.activerecord.Page;
import com.vo.Order;
import com.vo.Rate;

public class FailinfoDao {
	public Page<Order> getNetPage(int pageNumber, int pageSize, String netstarttime, String netendtime, String netparticipate, String netstate, String netorderid, String netname, String netlock, int employeeid,String p2_Order){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select o.orderid, o.p1_MerId, o.p2_Order, o.p3_Amt, o.amount,");
		selsql.append(" o.p5_Pid, o.pd_FrpId, o.p8_Url, o.ip, o.r1_Code, o.success, o.CreateTime, o.lock");
		frosql.append(" from orders o where 1=1");
    
//		if(employeeid==1){
//			frosql.append(" where 1=1");
//		}else{
//			frosql.append(" where 1=1");
//			//frosql.append(" WHERE p.id in (SELECT ep.id from employee_person ep WHERE ep.employeeid="+employeeid+")");
//		}		
		frosql.append(" and o.r1_Code=2");
		if(!netparticipate.equals("")){
			frosql.append(" and pa.codeid="+netparticipate);
		}
		if(!netstate.equals("")){
			frosql.append(" and o.r1_Code=2");
		}
		if(!netorderid.equals("")){
			frosql.append(" and o.orderid like '%"+netorderid+"%'");
		}
		if(!p2_Order.equals("")){
			frosql.append(" and o.p2_Order like '%"+p2_Order+"%'");
		}
		if(!netname.equals("")){
			frosql.append(" and o.p1_MerId ="+netname+"");
		}
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			frosql.append(" and o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"'");
		}
		frosql.append(" ORDER BY o.CreateTime DESC");
		Page<Order> orderPage = Order.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public boolean ordershandle(String netorderid){
		boolean boo = false;
		
		try {
			Order order = Order.dao.findById(netorderid);
			int r1_Code = order.getInt("r1_Code");
			if(r1_Code==2){
				int p1_MerId = order.getInt("p1_MerId");
				BigDecimal amount = new BigDecimal("0");
				BigDecimal deducted = new BigDecimal("0");
				BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
				BigDecimal rate=new BigDecimal("0");
				Rate ra=null;
				if(netorderid.contains("WX")){
					ra=Rate.dao.findById(p1_MerId);					
					if(ra.getBigDecimal("cibsm")==null){
						rate=new BigDecimal("0.98");
					}else{
						rate=ra.getBigDecimal("cibsm");
					}
				}else if(netorderid.contains("ALI")){
					 ra=Rate.dao.findById(p1_MerId);
					if(ra.getBigDecimal("mustali")==null){
						rate=new BigDecimal("0.98");
					}else{
						rate=ra.getBigDecimal("mustali");
					}
				}
				amount =  rate.multiply(p3_Amt);
				deducted = p3_Amt.subtract(amount);
				order.set("amount", amount);
				order.set("deducted", deducted);
				order.set("r1_Code", 1);
				boo = order.update() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boo;
	}

}
