package com.jsp.payment.dao;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.vo.CardCode;
import com.vo.Circlip;
import com.vo.Rate;
import com.vo.Recharge;

public class PaymentDao {

	public List<Rate> getRecharge(int id){
		return Rate.dao.find("select r.banking from rate r where r.id="+id);
	}
	
	public Page<Recharge> getRechOrder(int id, int page, String rechstarttime, String rechendtime, String rechparticipate, String rechstate, String rechid){
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("select r.rechargeid, r.p3_Amt, p.payable AS pd_FrpId, r.CreateTime, r.amount, r.r1_Code");
		sqlfrom.append(" FROM recharge r");
		sqlfrom.append(" LEFT JOIN participate p");
		sqlfrom.append(" on r.pd_FrpId=p.participate");
		sqlfrom.append(" where r.p1_MerId=?");
		if(!rechparticipate.equals("")){
			sqlfrom.append(" and p.codeid="+rechparticipate);
		}
		if(!rechstate.equals("")){
			sqlfrom.append(" and r.r1_Code="+rechstate);
		}
		if(!rechid.equals("")){
			sqlfrom.append(" and r.rechargeid like '%"+rechid+"%'");
		}
		if(!rechstarttime.equals("")&&!rechendtime.equals("")){
			sqlfrom.append(" and r.CreateTime >= '"+rechstarttime+"' and r.CreateTime < '"+rechendtime+"'");
		}
		sqlfrom.append(" ORDER BY r.CreateTime DESC");
		Page<Recharge> orderPage = null;
		try {
			orderPage = Recharge.dao.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return orderPage;
	}
	
	public Page<Circlip> getCircOrder(int id, int page, String circstarttime, String circendtime, String circparticipate, String circstate, String circid){
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("select c.circlipid, ca.payable AS cardcode, c.cardno, c.datetime, c.result, c.info, c.amount, c.actual, c.deducted");
		sqlfrom.append(" FROM circlip c");
		sqlfrom.append(" LEFT JOIN cardcode ca");
		sqlfrom.append(" on c.cardcode=ca.participate");
		sqlfrom.append(" where c.id=?");
		if(!circparticipate.equals("")){
			sqlfrom.append(" and ca.codeid="+circparticipate);
		}
		if(!circstate.equals("")){
			if(circstate.equals("3")){
				sqlfrom.append(" and c.result!='2001' and c.result!=2000");
			}else{
				sqlfrom.append(" and c.result='"+circstate+"'");
			}
		}
		if(!circid.equals("")){
			sqlfrom.append(" and c.circlipid like '%"+circid+"%'");
		}
		if(!circstarttime.equals("")&&!circendtime.equals("")){
			sqlfrom.append(" and c.datetime >= '"+circstarttime+"' and c.datetime < '"+circendtime+"'");
		}
		sqlfrom.append(" ORDER BY c.datetime DESC");
		Page<Circlip> orderPage = null;
		try {
			orderPage = Circlip.dao.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return orderPage;
	}
	
	public String getCardCode(int codeid){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.participate from cardcode c where c.codeid="+codeid);
		List<CardCode> codelist = CardCode.dao.find(sql.toString());
		return codelist.get(0).getStr("participate");
	}
	
	public BigDecimal getRate(String code, int id){
		StringBuffer sql = new StringBuffer();
		sql.append("select c.rate from cardcode c where c.participate="+code);
		List<CardCode> codelist = CardCode.dao.find(sql.toString());
		String sql1 = "select r.* from rate r where r.id="+id;
		List<Rate> ratelist = Rate.dao.find(sql1);
		String ratestr = codelist.get(0).getStr("rate");
		return ratelist.get(0).getBigDecimal(ratestr);
	}
}
