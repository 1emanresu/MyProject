package com.pay.w95epay.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class W95epayDao {

	public String getW95epayParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.w95epay_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
