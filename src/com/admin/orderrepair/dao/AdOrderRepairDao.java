package com.admin.orderrepair.dao;

import com.alibaba.common.lang.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdOrderRepairDao {
	

	public Page<Record> getOrderRepairPage(int pageNumber, int pageSize,
			String netstarttime, String netendtime, String netparticipate,
			String netstate, String netorderid, String netname, String netlock,
			int employeeid, String p2_Order, String minmoney, String maxmoney) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append(" select o.orderid, o.p1_MerId, o.p2_Order, o.p3_Amt, o.amount, ");
		selsql.append(" o.p5_Pid, o.pd_FrpId, o.p8_Url, o.ip, o.r1_Code, o.success, o.CreateTime, o.lock ");
		frosql.append(" from orders_repair o ");
		if (employeeid == 1) {
			frosql.append(" where 1=1");
		} else {
			frosql.append(" where 1=1");
		}
		
		 if (!netparticipate.equals("")) {
			 if(netparticipate.equals("1")){
				 frosql.append(" and o.pd_frpId='alipaywap'");
			 }else if(netparticipate.equals("2")){
				 frosql.append(" and o.pd_frpId='pay.tenpay.wappay'");
			 }else if(netparticipate.equals("3")){
				 frosql.append(" and o.pd_frpId in ('pay.weixin.wappay','wxwap') ");
			 }
		
		}
		
		if (!netstate.equals("")) {
			frosql.append(" and o.r1_Code='" + netstate+"'");
		}
		if (!netorderid.equals("")) {
			frosql.append(" and o.orderid like '%" + netorderid + "%'");
		}
		if (!p2_Order.equals("")) {
			frosql.append(" and o.p2_Order like '%" + p2_Order + "%'");
		}
		if (!netname.equals("")) {
			frosql.append(" and o.p1_MerId ='" + netname +"'");
		}
		if (!netlock.equals("")) {
			frosql.append(" and o.lock='" + netlock+"'");
		}
		if (!netstarttime.equals("")  ) {
			frosql.append(" and o.CreateTime >= '" + netstarttime + "'");
		}
		if(!netendtime.equals("")){
			frosql.append(" and o.CreateTime < '" + netendtime + "'");
		}
		if(StringUtil.isNotEmpty(minmoney) && StringUtil.isNotEmpty(maxmoney)){
			frosql.append(" and o.p3_Amt >= "+minmoney+" and o.p3_Amt < "+maxmoney+" ");
		}
		frosql.append(" ORDER BY o.CreateTime DESC");
		System.out.println(selsql.toString()+frosql.toString());
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize,selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	
}
