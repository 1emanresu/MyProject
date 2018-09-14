package com.pay.baopay.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class BaopayDao {
	
	public String getBaopayParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.baopay_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
