package com.pay.allinpay.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class AllinpayDao {

	
	public String getAllinpayParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.allinpay_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
