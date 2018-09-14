package com.pay.ease.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class EaseDao {

	public String getEaseParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ease_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
