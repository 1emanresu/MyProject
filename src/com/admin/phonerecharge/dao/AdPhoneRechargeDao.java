package com.admin.phonerecharge.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdPhoneRechargeDao {

	public Page<Record> getPhoneRechargePage(int pageNumber, int pageSize, String starttime, String endtime, String ret_code, String sporder_id){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT * ");
		frosql.append(" FROM onlineorder o");
		frosql.append(" where 1=1");
		if(!ret_code.equals("")){
			frosql.append(" and o.ret_code="+ret_code);
		}
		if(!sporder_id.equals("")){
			frosql.append(" and o.sporder_id like '%"+sporder_id+"%'");
		}
		if(!starttime.equals("")&&!endtime.equals("")){
			frosql.append(" and o.sporder_time >= '"+starttime+"' and o.sporder_time < '"+endtime+"'");
		}
		frosql.append(" ORDER BY o.sporder_time DESC");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
}
