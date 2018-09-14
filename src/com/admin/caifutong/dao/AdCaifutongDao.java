package com.admin.caifutong.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdCaifutongDao {

	public Page<Record> getCaifutongOrder(int pageNumber, int pageSize ,int employeeid, String sp_billno, String starttime, String endtime, String pay_result){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT c.*");
		frosql.append(" from caifutongorder c");
		frosql.append(" LEFT JOIN caifutong ca");
		frosql.append(" ON c.purchaser_id = ca.caifu_account where 1=1");
//		if(employeeid==1){
//			frosql.append(" where 1=1");
//		}else{
//			frosql.append(" WHERE ca.id in (SELECT ep.id from employee_person ep WHERE ep.employeeid="+employeeid+")");
//		}
		if(!sp_billno.equals("")){
			frosql.append(" and c.sp_billno='"+sp_billno+"'");
		}
		if(!pay_result.equals("4")){
			if(pay_result.equals("3")){
				frosql.append(" and c.pay_result!='0' and c.pay_result!='2' ");
			}else{
				frosql.append(" and c.pay_result='"+pay_result+"'");
			}
		}
		if(!starttime.equals("")&&!endtime.equals("")){
			frosql.append(" and c.date >= '"+starttime+"' and c.date < '"+endtime+"'");
		}
		frosql.append(" ORDER BY c.date desc");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
}
