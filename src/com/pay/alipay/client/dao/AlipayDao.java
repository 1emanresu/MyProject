package com.pay.alipay.client.dao;

import com.jfinal.plugin.activerecord.Db;

public class AlipayDao {

	public String getAlipayParticipate(String pd_FrpId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p.alipay_participate from participate p where p.participate = '"+pd_FrpId+"'");
		String bankId = Db.queryStr(sql.toString());
		return bankId;
	}
}
