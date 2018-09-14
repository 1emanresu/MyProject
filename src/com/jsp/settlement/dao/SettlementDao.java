package com.jsp.settlement.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.tool.StringUtil;
import com.vo.Refund;
import com.vo.SettlementAccount;

public class SettlementDao {
	public List<SettlementAccount> getSettlAccount(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.account_types, s.account_name, s.branch, s.account, p.payable as codeid, s.branchsheng, s.branchshi ");
		sql.append(" from settlement_account s");
		sql.append(" LEFT JOIN participate p");
		sql.append(" on s.codeid=p.codeid");
		sql.append(" where s.id=" + id);
		//System.out.println("settlement-====="+sql.toString());
		List<SettlementAccount> sett = SettlementAccount.dao.find(sql.toString());
		return sett;
	}

	public boolean ifSettleAcount(int id, int account_types) {
		boolean too = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(*) from settlement_account s where s.id=" + id+ " and s.account_types=" + account_types);
		List<SettlementAccount> sett = SettlementAccount.dao.find(sql.toString());
		long count = Db.queryLong(sql.toString());
		if (count > 0) {
			too = true;
		}
		return too;
	}

	public boolean upSettleAcount(int id, int account_types,String account_name, String branch, String account, int codeid, String branchsheng, String branchshi) {
		int count = Db.update("update settlement_account set account_name='"
				+ account_name + "',branch='" + branch + "',account='"
				+ account + "',codeid='" + codeid + "',branchsheng='"+branchsheng+"',branchshi='"+branchshi+"' where id=" + id
				+ " and account_types=" + account_types);
		return count == 1;
	}

	public boolean inserSettleAcount(int id, int account_types,String account_name, String branch, String account, int codeid, String branchsheng,String branchshi) {
		boolean too = false;
		try {
			SettlementAccount sett = new SettlementAccount();
			sett.set("id", id);
			sett.set("account_types", account_types);
			sett.set("account_name", account_name);
			sett.set("branch", branch);
			sett.set("account", account);
			sett.set("codeid", codeid);
			sett.set("branchsheng", branchsheng);
			sett.set("branchshi", branchshi);
			too = sett.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return too;
	}

	public Page<Refund> getRefund(int id, int page, String refundstarttime,
			String refundendtime, String refundstate, String refundid, String account_name) {
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("SELECT r.*");
		sqlfrom.append(" from refund r");
		sqlfrom.append(" where r.id = " + id);
		if (!refundstate.equals("")) {
			sqlfrom.append(" and r.refund_state=" + refundstate);
		}
		if (!refundid.equals("")) {
			sqlfrom.append(" and r.refund_id like '%" + refundid + "%'");
		}
		if (!account_name.equals("")) {
			sqlfrom.append(" and r.account_name like '%" + account_name + "%'");
		}
		if (!refundstarttime.equals("") && !refundendtime.equals("")) {
			sqlfrom.append(" and r.createtime >= '" + refundstarttime+ "' and r.createtime < '" + refundendtime + "'");
		}
		sqlfrom.append(" ORDER BY r.createtime DESC");
		Page<Refund> orderPage = null;
		try {
			orderPage = Refund.dao.paginate(page, 10, sqlselect.toString(),sqlfrom.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderPage;
	}

	public long numrefund(String starttime, String endtime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(*)");
		sql.append(" from refund r");
		sql.append(" where r.createtime >= '" + starttime + "' and r.createtime < '" + endtime + "'");
		long l = -1;
		try {
			l = Db.queryLong(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	public int getSettlAccountTypes(int id,String branch) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.account_types");
		sql.append(" from settlement_account s");		
		sql.append(" where s.id=" + id+" and s.branch='"+branch+"'");
		System.out.println("查询的SQL语句是==："+sql.toString());
		List<SettlementAccount> list = SettlementAccount.dao.find(sql.toString());
		int result=0;
		if(list.size()>0){
			SettlementAccount sett = list.get(0);
			result = sett.getInt("account_types");
		}else{
			result=0;
		}
		return result;
	}
	/**
	 * @description 按照用户id查询出该用户所有的银行卡<br/>
	 * @methodName findCardById<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月15日下午2:52:15<br/>
	 * @param id
	 * @return
	 */
	public List<SettlementAccount> findCardById(int id){
		StringBuffer selectSql = new StringBuffer();
		StringBuffer fromSql = new StringBuffer();
		selectSql.append(" select s.primary_id,s.account_types, s.account_name, s.branch, s.account, p.payable as codeid, s.branchsheng, s.branchshi  ");
		fromSql.append(" from settlement_account s ");
		fromSql.append(" LEFT JOIN participate p on s.codeid=p.codeid ");
		fromSql.append(" where s.id="+id+" and s.account_types = 1 ");
		System.out.println("cards===="+selectSql.toString()+fromSql.toString());
		return SettlementAccount.dao.find(selectSql.toString()+fromSql.toString());
	}
	
	/**
	 * @description 根据主键id和用户id更新<br/>
	 * @methodName updateCardById<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月15日下午7:18:31<br/>
	 * @param primary_id
	 * @param id
	 * @param account_name
	 * @param branch
	 * @param account
	 * @param branchsheng
	 * @param branchshi
	 * @param codeid
	 * @return
	 */
	public boolean updateCardById(String primary_id,int account_types,String account_name,String branch,String account,String branchsheng,String branchshi,String codeid){
		SettlementAccount settlementAccount = SettlementAccount.dao.findById(primary_id);
		if(account_types != 0){
			settlementAccount.set("account_types", account_types);
		}
		if(StringUtil.isNotEmpty(account_name)){
			settlementAccount.set("account_name", account_name);
		}
		if(StringUtil.isNotEmpty(branch)){
			settlementAccount.set("branch", branch);
		}
		if(StringUtil.isNotEmpty(account)){
			settlementAccount.set("account", account);
		}
		if(StringUtil.isNotEmpty(codeid)){
			settlementAccount.set("codeid", codeid);
		}
		if(StringUtil.isNotEmpty(branchsheng)){
			settlementAccount.set("branchsheng", branchsheng);
		}
		if(StringUtil.isNotEmpty(branchshi)){
			settlementAccount.set("branchshi", branchshi);
		}
		return settlementAccount.update();
	}
}