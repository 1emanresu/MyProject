package com.pay.dinpay.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class DinpayDao {

	public String getDinpayParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.dinpay_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
