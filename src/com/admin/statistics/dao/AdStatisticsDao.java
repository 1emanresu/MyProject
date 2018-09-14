package com.admin.statistics.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
public class AdStatisticsDao {
	
	/**
	 * 统计网银
	 */
	public List<Record> getStatistiNetold(String starttime, String endtime,
			String netparticipate, String netstate, String netorderid,
			String p1_MerId, String netlock, String p2_Order,String minmoney,String maxmoney) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select SUM(o.deducted) as deducted, SUM(o.amount) as amount, SUM(o.p3_Amt) as p3_Amt ");
		sql.append(" from orders_history o ");
		sql.append(" where 1=1 ");
		if (!netstate.equals("")) {
			sql.append(" and o.r1_Code=" + netstate);
		}
		if (!netorderid.equals("")) {
			sql.append(" and o.orderid like '%" + netorderid + "%'");
		}
		if (!p2_Order.equals("")) {
			sql.append(" and o.p2_Order like '%" + p2_Order + "%'");
		}
		if (!p1_MerId.equals("")) {
			sql.append(" and o.p1_MerId =" + p1_MerId + "");
		}
		if (!netlock.equals("")) {
			sql.append(" and o.lock=" + netlock);
		}
		if (!starttime.equals("") && !endtime.equals("")) {
			sql.append(" and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "'");
		}
		if (StringUtil.isNotEmpty(minmoney) && StringUtil.isNotEmpty(maxmoney)) {
			sql.append(" and o.p3_Amt >= "+minmoney+" and o.p3_Amt < "+maxmoney+" ");
		}
		sql.append(" ORDER BY o.CreateTime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}

	/**
	 * 统计网银
	 */
	public List<Record> getStatistiNet(String starttime, String endtime,
			String netparticipate, String netstate, String netorderid,
			String p1_MerId, String netlock, String p2_Order,String minmoney,String maxmoney) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select SUM(o.deducted) as deducted, SUM(o.amount) as amount, SUM(o.p3_Amt) as p3_Amt ");
		sql.append(" from orders o ");
		sql.append(" where 1=1 ");
		if (!netstate.equals("")) {
			sql.append(" and o.r1_Code=" + netstate);
		}
		if (!netorderid.equals("")) {
			sql.append(" and o.orderid like '%" + netorderid + "%'");
		}
		if (!p2_Order.equals("")) {
			sql.append(" and o.p2_Order like '%" + p2_Order + "%'");
		}
		if (!p1_MerId.equals("")) {
			sql.append(" and o.p1_MerId =" + p1_MerId + "");
		}
		if (!netlock.equals("")) {
			sql.append(" and o.lock=" + netlock);
		}
		if (!starttime.equals("") && !endtime.equals("")) {
			sql.append(" and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "'");
		}
		if (StringUtil.isNotEmpty(minmoney) && StringUtil.isNotEmpty(maxmoney)) {
			sql.append(" and o.p3_Amt >= "+minmoney+" and o.p3_Amt < "+maxmoney+" ");
		}
		sql.append(" ORDER BY o.CreateTime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
	
	
	public List<Record> getStatistiNet2(String starttime, String endtime, int employeeid){
		StringBuffer sql = new StringBuffer();
		sql.append("select SUM(o.deducted) as deducted, SUM(o.amount) as amount, SUM(o.p3_Amt) as p3_Amt");
		sql.append(" from orders o");
		sql.append(" LEFT JOIN employee_person ep");
		sql.append(" ON ep.id = o.p1_MerId");
		sql.append(" where o.r1_Code=1");
//		if(employeeid!=1){
//			sql.append(" AND ep.employeeid="+employeeid);
//		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sql.append(" and o.CreateTime >= '"+starttime+"' and o.CreateTime < '"+endtime+"'");
		}
		sql.append(" ORDER BY o.CreateTime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
	
	/**
	 * 统计点卡
	 */
	public List<Record> getStatistiCard(String starttime, String endtime, int employeeid){
		StringBuffer sql = new StringBuffer();
		sql.append("select SUM(o.deducted) as deducted, SUM(o.amount) as amount, SUM(o.p3_Amt) as p3_Amt");
		sql.append(" from cardorder o");
		sql.append(" LEFT JOIN employee_person ep");
		sql.append(" ON ep.id = o.p1_MerId");
		sql.append(" where o.r1_Code=1");
//		if(employeeid!=1){
//			sql.append(" AND ep.employeeid="+employeeid);
//		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sql.append(" and o.CreateTime >= '"+starttime+"' and o.CreateTime < '"+endtime+"'");
		}
		sql.append(" ORDER BY o.CreateTime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
	
	/**
	 * 退款统计
	 */
	public List<Record> getStatistiRefund(String starttime, String endtime, int employeeid){
		StringBuffer sql = new StringBuffer();
		sql.append("select SUM(r.refund_amount) as refund_amount from refund r ");
		sql.append(" LEFT JOIN employee_person ep");
		sql.append(" ON ep.id = r.id");
		sql.append(" where r.refund_state=1");
//		if(employeeid!=1){
//			sql.append(" AND ep.employeeid="+employeeid);
//		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sql.append(" and r.createtime >= '"+starttime+"' and r.createtime < '"+endtime+"'");
		}
		sql.append(" ORDER BY r.createtime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
	
	/**
	 * 充值统计
	 */
	public List<Record> getStatistiRecharge(String starttime, String endtime, int employeeid){
		StringBuffer sql = new StringBuffer();
		sql.append("select SUM(r.p3_Amt) AS p3_Amt, SUM(r.amount) as amount, SUM(r.deducted) as deducted ");
		sql.append(" from recharge r");
		sql.append(" LEFT JOIN employee_person ep");
		sql.append(" ON ep.id = r.p1_MerId");
		sql.append(" where r.r1_Code=1");
//		if(employeeid!=1){
//			sql.append(" AND ep.employeeid="+employeeid);
//		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sql.append(" and r.CreateTime >= '"+starttime+"' and r.CreateTime < '"+endtime+"'");
		}
		sql.append(" ORDER BY r.CreateTime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
	
	/**
	 * 销卡统计
	 */
	public List<Record> getStatistiCirclip(String starttime, String endtime, int employeeid){
		StringBuffer sql = new StringBuffer();
		sql.append("select SUM(c.amount) AS amount, SUM(c.actual) AS actual, SUM(c.accountvalue) AS accountvalue, SUM(c.deducted) AS deducted ");
		sql.append(" FROM circlip c");
		sql.append(" LEFT JOIN employee_person ep");
		sql.append(" ON ep.id = c.id");
		sql.append(" where c.result='2000'");
//		if(employeeid!=1){
//			sql.append(" AND ep.employeeid="+employeeid);
//		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sql.append(" and c.datetime >= '"+starttime+"' and c.datetime < '"+endtime+"'");
		}
		sql.append(" ORDER BY c.datetime");
		List<Record> amount = Db.find(sql.toString());
		return amount;
	}
}
