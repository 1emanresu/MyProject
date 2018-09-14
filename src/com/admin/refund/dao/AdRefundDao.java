package com.admin.refund.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Refund;

public class AdRefundDao {

	public Page<Record> getRefundPage(int pageNumber, int pageSize, String refustarttime, String refuendtime,
			String refustate, String refuid, String id, String settlementauthority) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		/*selsql.append("select r.*, pe.name, se.payable");
		frosql.append(" FROM refund r");
		frosql.append(" LEFT JOIN (select s.*, p.payable from settlement_account s ");
		frosql.append("            LEFT JOIN participate p");
		frosql.append("            ON s.codeid = p.codeid) se");
		frosql.append(" on r.refund_types = se.account_types and se.id=r.id");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" on r.id=pe.id");
		frosql.append(" where 1=1");*/
		selsql.append("select r.*, pe.name");
		frosql.append(" FROM refund r");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" on r.id=pe.id");
		frosql.append(" where 1=1");
		if (!refustate.equals("")) {
			frosql.append(" and r.refund_state=" + refustate);
		}
		if (!refuid.equals("")) {
			frosql.append(" and r.refund_id like '%" + refuid + "%'");
		}
		if (!id.equals("")) {
			// frosql.append(" and pe.name like '%"+refuname+"%'");
			frosql.append(" and r.id=" + id + "");
		}
		if (!settlementauthority.equals("")) {
			frosql.append(" and r.settlementauthority=" + settlementauthority);
		}
		if (!refustarttime.equals("") && !refuendtime.equals("")) {
			frosql.append(" and r.createtime >= '" + refustarttime + "' and r.createtime < '" + refuendtime + "'");
		}
		frosql.append(" ORDER BY r.createtime DESC,refund_id");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	
	
	public Page<Record> getRefundPageRe(int pageNumber, int pageSize, String refustarttime, String refuendtime,
			String refustate, String refuid, String id, String settlementauthority,String accountname,String bankName) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		/*selsql.append("select r.*, pe.name, se.payable");
		frosql.append(" FROM refund r");
		frosql.append(" LEFT JOIN (select s.*, p.payable from settlement_account s ");
		frosql.append("            LEFT JOIN participate p");
		frosql.append("            ON s.codeid = p.codeid) se");
		frosql.append(" on r.refund_types = se.account_types and se.id=r.id");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" on r.id=pe.id");
		frosql.append(" where 1=1");*/
		
		selsql.append("select r.*, pe.name");
		frosql.append(" FROM refund r");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" on r.id=pe.id");
		frosql.append(" where 1=1");
		if (!refustate.equals("")) {
			frosql.append(" and r.refund_state=" + refustate);
		}
		if (!refuid.equals("")) {
			frosql.append(" and r.refund_id like '%" + refuid + "%'");
		}
		if (!id.equals("")) {
			// frosql.append(" and pe.name like '%"+refuname+"%'");
			frosql.append(" and r.id=" + id + "");
		}if (!accountname.equals("")) {
			// frosql.append(" and pe.name like '%"+refuname+"%'");
			frosql.append(" and r.account_name like '%" + accountname + "%'");
		}
		if (!settlementauthority.equals("")) {
			frosql.append(" and r.settlementauthority=" + settlementauthority);
		}
		if (StringUtil.isNotEmpty(bankName)) {
			frosql.append(" and r.net_name= '"+bankName+"' ");
		}
		if (!refustarttime.equals("") && !refuendtime.equals("")) {
			frosql.append(" and r.createtime >= '" + refustarttime + "' and r.createtime < '" + refuendtime + "'");
		}
		frosql.append(" ORDER BY r.createtime DESC,refund_id");
		//System.out.println("==refund========"+selsql.toString()+frosql.toString());
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}

	public boolean refunddeduction(String refundid) { 
		boolean boo = false;
		System.out.println(refundid);
		String str = "select * from refund where refund_id='" + refundid + "';";
		Refund refund = Refund.dao.find(str).get(0);
		System.out.println(refund + "null");
		refund.set("refund_state", 1);
		boo = refund.update();
		return boo;
	}
	
	public List<Refund> findRefundBank(){
		String sql = " select f.net_name from refund f group by net_name ";
		return Refund.dao.find(sql);
	}

}