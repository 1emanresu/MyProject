package com.admin.card.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vo.CardOrder;

public class AdCardDao {
	public Page<CardOrder> getCardPage(int pageNumber, int pageSize, String cardstarttime, String cardendtime, String cardparticipate, String cardstate,String cardorderid,String cardname,String cardlock, int employeeid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select o.orderid, p.name as p1_MerId, o.p2_Order, o.p3_Amt, o.amount,");
		selsql.append(" o.p5_Pid, pa.payable as pd_FrpId, o.p8_Url, o.ip, o.r1_Code, o.success, o.CreateTime, o.lock");
		frosql.append(" from cardorder o");
		frosql.append(" LEFT JOIN person p");
		frosql.append(" on o.p1_MerId=p.id");
		frosql.append(" LEFT JOIN participate pa");
		frosql.append(" ON o.pd_FrpId=pa.participate where 1=1");
//		if(employeeid==1){
//			frosql.append(" where 1=1");
//		}else{
//			frosql.append(" WHERE p.id in (SELECT ep.id from employee_person ep WHERE ep.employeeid="+employeeid+")");
//		}
		if(!cardparticipate.equals("")){
			frosql.append(" and pa.codeid="+cardparticipate);
		}
		if(!cardstate.equals("")){
			if(cardstate.equals("1")){
				frosql.append(" and o.r1_Code="+cardstate);
			}else{
				frosql.append(" and o.r1_Code!=1");
			}
		}
		if(!cardorderid.equals("")){
			frosql.append(" and o.orderid like '%"+cardorderid+"%'");
		}
		if(!cardname.equals("")){
			frosql.append(" and p.name like '%"+cardname+"%'");
		}
		if(!cardlock.equals("")){
			frosql.append(" and o.lock="+cardlock);
		}
		if(!cardstarttime.equals("")&&!cardendtime.equals("")){
			frosql.append(" and o.CreateTime >= '"+cardstarttime+"' and o.CreateTime < '"+cardendtime+"'");
		}
		frosql.append(" ORDER BY o.CreateTime DESC");
		Page<CardOrder> orderPage = CardOrder.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public List<Record> getSinglist(String cardid){
		 StringBuffer sql = new StringBuffer();
		 sql.append("SELECT s.*, c.payable");
		 sql.append(" from singlecard s");
		 sql.append(" LEFT JOIN cardcode c");
		 sql.append(" ON s.cardcode=c.participate");
		 sql.append(" where s.orderid='"+cardid+"'");
		return Db.find(sql.toString());
	}
}
