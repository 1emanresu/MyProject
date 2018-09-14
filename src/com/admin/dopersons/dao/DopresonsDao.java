package com.admin.dopersons.dao;


import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.vo.Order;

public class DopresonsDao {
	
	public Page<Order> getNetPage(int pageNumber, int pageSize, String netstarttime, String netendtime, String netparticipate, String netstate, String netorderid, String netname, String netlock, int employeeid,String p2_Order){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select o.orderid, o.p1_MerId, o.p2_Order, o.p3_Amt, o.amount,");
		selsql.append(" o.p5_Pid, o.pd_FrpId, o.p8_Url, o.ip, o.r1_Code, o.success, o.CreateTime, o.lock");
		frosql.append(" from orders o");
		frosql.append(" where r1_Code=1");
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			frosql.append(" and o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"'");
		}
		if(!netorderid.equals("")){
			frosql.append(" and o.orderid like '%" + netorderid + "%'");
		}
		frosql.append(" ORDER BY o.CreateTime DESC");
		Page<Order> orderPage = Order.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	
	public  List<Order> getNetAll(String netstarttime, String netendtime, String netorderid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select o.orderid, o.p1_MerId, o.p2_Order, o.p3_Amt, o.amount,");
		selsql.append(" o.p5_Pid, o.pd_FrpId, o.p8_Url, o.ip, o.r1_Code, o.success, o.CreateTime, o.lock");
		frosql.append(" from orders o");
		frosql.append(" where r1_Code=1");
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			frosql.append(" and o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"'");
		}
		if(!netorderid.equals("")){
			frosql.append(" and o.orderid like '%" + netorderid + "%'"); 
		}
		frosql.append(" ORDER BY o.CreateTime DESC");
		String sql=selsql.toString()+" "+frosql.toString();
		System.out.println(sql);
		List<Order> order = Order.dao.find(sql);
		return order;
	}
}
