package com.pay.ecpss.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class EcpssDao {

	public String getEcpssParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ecpss_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
