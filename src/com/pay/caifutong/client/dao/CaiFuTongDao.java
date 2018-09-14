package com.pay.caifutong.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class CaiFuTongDao {

	public String getCaiFuParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.payment_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
