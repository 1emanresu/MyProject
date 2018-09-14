package com.pay.cardyee.client.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.vo.CardCode;
import com.vo.Payment;

public class CardyeeDao {
	
	public List<Payment> eqPayment(String id){
		List list = Payment.dao.find("select * from payment p where p.id='"+id+"'");
		return list;
	}
	
	public String getParticipate(String participate){
		List<CardCode> list = CardCode.dao.find("select c.participate from cardcode c, participate p where c.codeid = p.codeid and p.participate = '"+participate+"'");
		return list.get(0).getStr("participate");
	}
	
	public Record getRate(String cardcode, int id){
		StringBuffer sql = new StringBuffer();
		sql.append("select r.*, c.rate");
		sql.append(" from rate r, cardcode c");
		sql.append(" where c.participate = '"+cardcode+"' and r.id="+id);
		return Db.findFirst(sql.toString());
	}
	
	public List<String> getSingResult(String orderid){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.result");
		sql.append(" from singlecard s");
		sql.append(" where s.orderid='"+orderid+"'");
		return Db.query(sql.toString());
	}
}
