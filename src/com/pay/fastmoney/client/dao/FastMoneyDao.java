package com.pay.fastmoney.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class FastMoneyDao {

	
	public String getFastParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.fastmoney_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
