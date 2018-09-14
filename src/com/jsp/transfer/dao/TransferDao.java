package com.jsp.transfer.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class TransferDao {

	public Page<Record> getTransfer(int id, int page, String starttime, String endtime, String transferid){
		
		StringBuffer sqlselect = new StringBuffer();
		StringBuffer sqlfrom = new StringBuffer();
		sqlselect.append("SELECT t.*, p.name AS pname, pe.name AS pename ");
		sqlfrom.append(" FROM transfer t");
		sqlfrom.append(" LEFT JOIN person p");
		sqlfrom.append(" ON t.transfer_usero = p.id");
		sqlfrom.append(" LEFT JOIN person pe");
		sqlfrom.append(" ON t.transfer_usert = pe.id");
		sqlfrom.append(" WHERE t.transfer_usero=?");
		if(!transferid.equals("")){
			sqlfrom.append(" and t.transfer_id="+transferid+"%'");
		}
		if(!starttime.equals("")&&!endtime.equals("")){
			sqlfrom.append(" and t.transfer_time >= '"+starttime+"' and t.transfer_time < '"+endtime+"'");
		}
		sqlfrom.append(" ORDER BY t.transfer_time DESC");
		Page<Record> orderPage = null;
		try {
			orderPage = Db.paginate(page, 10, sqlselect.toString(), sqlfrom.toString(),id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return orderPage;
	}
}
