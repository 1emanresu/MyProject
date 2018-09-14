package com.pay.ips.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class IpsDao {

	public String getIpsParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ips_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
