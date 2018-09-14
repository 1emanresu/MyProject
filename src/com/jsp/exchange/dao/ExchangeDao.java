package com.jsp.exchange.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vo.CardCode;
import com.vo.CardOrder;
import com.vo.Order;
import com.vo.Participate;
import com.vo.Rate;

public class ExchangeDao {
	public List<Participate> getParticipate(){
		return Participate.dao.find("select * from participate");
	}
	
	public List<Participate> getPartici(){
		return Participate.dao.find("select * from participate p where p.codeid <30");
	}
	
	public List<CardCode> getCardCode(){
		return CardCode.dao.find("SELECT * from cardcode");
	}
	
	public Page<Order> getOrder(int id, int page, String netstarttime, String netendtime, String netparticipate, String netstate, String netid){
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("select o.orderid, o.CreateTime, o.pd_FrpId, o.p8_Url, o.p3_Amt, o.r1_Code, o.success, o.amount, o.deducted");
		sqlfrom.append(" FROM orders o");
		//sqlfrom.append(" LEFT JOIN participate p");
		//sqlfrom.append(" on o.pd_FrpId=p.participate");
		sqlfrom.append(" where o.p1_MerId=? and o.lock=1");
		if(!netparticipate.equals("")){
			sqlfrom.append(" and p.codeid="+netparticipate);
		}
		if(!netstate.equals("")){
			sqlfrom.append(" and o.r1_Code="+netstate);
		}
		if(!netid.equals("")){
			sqlfrom.append(" and o.orderid like '%"+netid+"%'");
		}
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			sqlfrom.append(" and o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"'");
		}
		sqlfrom.append(" ORDER BY o.CreateTime DESC");
		Page<Order> orderPage = Order.dao.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		return orderPage;
	}
	
	public Record getQueryOrdertotal(int id, String netstarttime, String netendtime,String netstate, String netid){		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) AS tote,SUM(o.p3_Amt) AS amttote,SUM(o.amount) AS amounttote");
		sql.append(" FROM orders o");
		sql.append(" WHERE o.p1_MerId="+id+" and o.lock=1");
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			sql.append(" AND (o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"')");
		}
		if(!netid.equals("")){
			sql.append(" AND o.p2_Order LIKE '%"+netid+"%'");
		}
		if(!netstate.equals("")){
			sql.append(" AND o.r1_Code="+netstate);
		}
		System.out.println("----------："+sql.toString());
		return Db.findFirst(sql.toString());
	}
	
	/**
	 * 查询用户的订单统计
	 * @param id  用户id
	 * @param netstarttime  开始计算时间
	 * @param netendtime	结束时间
	 * @param netstate		订单状态
	 * @param netid		商户订单号
	 * @param rate		相关收益率
	 * @return
	 */
	public Record getQueryOrdertotal20171031(int id, String netstarttime, String netendtime,String netstate, String netid,Rate rate){		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) AS tote,SUM(o.p3_Amt) AS amttote,SUM(o.amount) AS amounttote");
		if(rate!=null){
			sql.append(" ,SUM( CASE  "
					+ " WHEN pd_FrpId='alipaywap'  AND r1_Code=1  THEN p3_Amt*"+rate.get("mustali")
					+ " WHEN pd_FrpId='pay.weixin.wappay' AND r1_Code=1  THEN p3_Amt*"+rate.get("cibsm")
					+ " WHEN pd_FrpId='pay.tenpay.wappay' AND r1_Code=1  THEN p3_Amt*"+rate.get("qqwx")
					+ " WHEN pd_FrpId='wxwap'  AND r1_Code=1  THEN p3_Amt*"+rate.get("cibsm")
					+ " else 0 "
					+ " END) income ");
		}
		sql.append(" FROM orders o");
		sql.append(" WHERE o.p1_MerId="+id+" and o.lock=1");
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			sql.append(" AND (o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"')");
		}
		if(!netid.equals("")){
			sql.append(" AND o.p2_Order LIKE '%"+netid+"%'");
		}
		if(!netstate.equals("")){
			sql.append(" AND o.r1_Code="+netstate);
		}
		System.out.println("----------："+sql.toString());
		return Db.findFirst(sql.toString());
	}
	
	
	
	public Record getQueryRefundtotal(int id,String refundstarttime, String refundendtime,String refundstate,String refundid,String account_name){		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) AS tote,SUM(o.refund_actual) AS amttote");
		sql.append(" FROM refund o");
		sql.append(" WHERE o.id="+id);
		if(!refundstarttime.equals("")&&!refundendtime.equals("")){
			sql.append(" AND (o.CreateTime >= '"+refundstarttime+"' and o.CreateTime < '"+refundendtime+"')");
		}
		if(!refundid.equals("")){
			sql.append(" AND o.refund_id LIKE '%"+refundid+"%'");
		}
		if(!refundstate.equals("")){
			sql.append(" AND o.refund_state LIKE '%"+refundstate+"%'");
		}
		if(!account_name.equals("")){
			sql.append(" AND o.account_name="+account_name);
		}
		System.out.println("----------："+sql.toString());
		return Db.findFirst(sql.toString());
	}
	
	public Page<Order> getOrder2(int id, int page, String netstarttime, String netendtime, String netstate, String netid){
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("select o.p2_Order, o.CreateTime, o.pd_FrpId, o.p8_Url, o.p3_Amt, o.r1_Code, o.success, o.amount, o.deducted,o.orderid");
		sqlfrom.append(" FROM orders o");
		sqlfrom.append(" where o.p1_MerId=? and o.lock=1");
		if(!netstate.equals("")){
			sqlfrom.append(" and o.r1_Code="+netstate);
		}
		if(!netid.equals("")){
			sqlfrom.append(" and o.p2_Order like '%"+netid+"%'");
		}
		if(!netstarttime.equals("")&&!netendtime.equals("")){
			sqlfrom.append(" and o.CreateTime >= '"+netstarttime+"' and o.CreateTime < '"+netendtime+"'");
		}
		sqlfrom.append(" ORDER BY o.CreateTime DESC");
		System.out.println("xxxxxxxxxxx="+sqlselect.toString());
		Page<Order> orderPage = Order.dao.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		return orderPage;
	}
	
	public Page<CardOrder> getCardOrder(int id, int page, String cardstarttime, String cardendtime, String cardparticipate, String cardstate, String cardid){
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("select o.orderid, o.CreateTime, p.payable AS pd_FrpId, o.p8_Url, o.p3_Amt, o.r1_Code, o.success, o.amount, o.deducted");
		sqlfrom.append(" FROM cardorder o");
		sqlfrom.append(" LEFT JOIN participate p");
		sqlfrom.append(" on o.pd_FrpId=p.participate");
		sqlfrom.append(" where o.p1_MerId=? and o.lock=1");
		if(!cardparticipate.equals("")){
			sqlfrom.append(" and p.codeid="+cardparticipate);
		}
		if(!cardstate.equals("")){
			sqlfrom.append(" and o.r1_Code="+cardstate);
		}
		if(!cardid.equals("")){
			sqlfrom.append(" and o.orderid like '%"+cardid+"%'");
		}
		if(!cardstarttime.equals("")&&!cardendtime.equals("")){
			sqlfrom.append(" and o.CreateTime >= '"+cardstarttime+"' and o.CreateTime < '"+cardendtime+"'");
		}
		sqlfrom.append(" ORDER BY o.CreateTime DESC");
		Page<CardOrder> orderPage = CardOrder.dao.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		return orderPage;
	}
	
	public Record getOrdertotal(int id, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startime = format.format(date);
		String endtime = format.format(cal.getTime());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS tote, SUM(o.p3_Amt) AS amttote, SUM(o.amount) AS amounttote");
		sql.append(" FROM orders o");
		sql.append(" WHERE o.p1_MerId="+id+" and o.lock=1 AND o.r1_Code=1");
		sql.append(" AND o.CreateTime >= '"+startime+"' AND o.CreateTime < '"+endtime+"'");
		return Db.findFirst(sql.toString());
	}
	
	public Record getUserOrdertotal(int id,String startime,String endtime){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS tote, SUM(o.p3_Amt) AS amttote, SUM(o.amount) AS amounttote");
		sql.append(" FROM orders o");
		sql.append(" WHERE o.p1_MerId="+id+" and o.lock=1 AND o.r1_Code=1");
		sql.append(" AND o.CreateTime >= '"+startime+"' AND o.CreateTime < '"+endtime+"'");
		System.out.println(sql.toString());
		return Db.findFirst(sql.toString());
	}
	
	public Record getCardOrdertotal(int id, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startime = format.format(date);
		String endtime = format.format(cal.getTime());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS tote, SUM(o.p3_Amt) AS amttote, SUM(o.amount) AS amounttote");
		sql.append(" FROM cardorder o");
		sql.append(" WHERE o.p1_MerId="+id+" and o.lock=1 AND o.r1_Code=1");
		sql.append(" AND o.CreateTime >= '"+startime+"' AND o.CreateTime < '"+endtime+"'");
		return Db.findFirst(sql.toString());
	}
	
	public Record getRechOrdertotal(int id, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startime = format.format(date);
		String endtime = format.format(cal.getTime());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS tote, SUM(r.p3_Amt) AS amttote, SUM(r.amount) AS amounttote");
		sql.append(" FROM recharge r");
		sql.append(" WHERE r.p1_MerId="+id+" and r.lock=1 AND r.r1_Code=1");
		sql.append(" AND r.CreateTime >= '"+startime+"' AND r.CreateTime < '"+endtime+"'");
		return Db.findFirst(sql.toString());
	}
	
	
	public Record getCircOrdertotal(int id, Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startime = format.format(date);
		String endtime = format.format(cal.getTime());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS tote, SUM(c.amount) AS amttote, SUM(c.actual) AS amounttote");
		sql.append(" FROM circlip c");
		sql.append(" WHERE c.id="+id+" AND c.result=2000");
		sql.append(" AND c.datetime >= '"+startime+"' AND c.datetime < '"+endtime+"'");
		return Db.findFirst(sql.toString());
	}
	
	public List<Record> getSingOrder(String singid){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.*, c.payable");
		sql.append(" FROM singlecard s");
		sql.append(" LEFT JOIN cardcode c");
		sql.append(" ON s.cardcode=c.participate");
		sql.append(" where s.orderid='"+singid+"'");
		sql.append(" ORDER BY s.datetime DESC");
		List<Record> singlist = Db.find(sql.toString());
		return singlist;
	}
}
