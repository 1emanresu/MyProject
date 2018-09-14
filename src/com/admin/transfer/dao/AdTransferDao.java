package com.admin.transfer.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdTransferDao {

	public Page<Record> getTransferPage(int pageNumber, int pageSize, String starttime, String endtime, String transfer_id, String pname, String pename){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT t.*, p.name AS pname, pe.name AS pename");
		frosql.append(" FROM transfer t");
		frosql.append(" LEFT JOIN person p");
		frosql.append(" ON t.transfer_usero=p.id");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" ON t.transfer_usert=pe.id");
		frosql.append(" where 1=1");
		if(!transfer_id.equals("")){
			frosql.append(" and t.transfer_id="+transfer_id);
		}
		if(!pname.equals("")){
			frosql.append(" and p.name like '%"+pname+"%'");
		}
		if(!pename.equals("")){
			frosql.append(" and pe.name like '%"+pename+"%'");
		}
		if(!starttime.equals("")&&!endtime.equals("")){
			frosql.append(" and t.transfer_time >= '"+starttime+"' and t.transfer_time < '"+endtime+"'");
		}
		frosql.append(" ORDER BY t.transfer_time DESC");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
}
