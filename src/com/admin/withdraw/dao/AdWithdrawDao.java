package com.admin.withdraw.dao;
import java.util.List;

import com.tool.StringUtil;
import com.vo.Refund;
public class AdWithdrawDao {
	
	public List<Refund> withdraw(String starttime,String endtime) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,count(*) as alltotal,createtime from refund ");
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			sql.append(" where createtime >= '"+starttime+"' and createtime < '"+endtime+"' ");
		}
		sql.append(" group by id order by id asc; ");
		System.out.println(sql.toString());
		List<Refund> refunds = Refund.dao.find(sql.toString());
		return refunds;
	}
	
	public List<Refund> countStatus(String starttime,String endtime){
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,sum(refund_amount) as allmoney from refund where refund_state in (1,2) ");
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			sql.append(" and createtime >= '"+starttime+"' and createtime < '"+endtime+"' ");
		}
		sql.append(" group by id order by id asc; ");
		System.out.println(sql.toString());
		List<Refund> refunds = Refund.dao.find(sql.toString());
		return refunds;
	}
	
	public List<Refund> successStatus(String starttime,String endtime){
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,sum(refund_amount) as successmoney,count(*) as successtotal from refund where refund_state = 1 ");
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			sql.append(" and createtime >= '"+starttime+"' and createtime < '"+endtime+"' ");
		}
		sql.append(" group by id order by id asc; ");
		System.out.println(sql.toString());
		List<Refund> refunds = Refund.dao.find(sql.toString());
		return refunds;
	}

	public List<Refund> dealStatus(String starttime,String endtime){
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,count(*) as dealtotal from refund where refund_state = 2 ");
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			sql.append(" and createtime >= '"+starttime+"' and createtime < '"+endtime+"' ");
		}
		sql.append(" group by id order by id asc; ");
		System.out.println(sql.toString());
		List<Refund> refunds = Refund.dao.find(sql.toString());
		return refunds;
	}
	
	public List<Refund> failStatus(String starttime,String endtime){
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,count(*) as failtotal from refund where refund_state = 3 ");
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			sql.append(" and createtime >= '"+starttime+"' and createtime < '"+endtime+"' ");
		}
		sql.append(" group by id order by id asc; ");
		System.out.println(sql.toString());
		List<Refund> refunds = Refund.dao.find(sql.toString());
		return refunds;
	}
}
