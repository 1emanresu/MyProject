package com.admin.gateway.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Gateway;
import com.vo.GatewayRate;
import com.vo.PersonGateway;

public class AdGatewayDao {
	public List<PersonGateway> getPersonGateway() {
		List<PersonGateway> personGatewayList = PersonGateway.dao.find(
				"select person_gateway.* from person_gateway,gateway where person_gateway.gateway_id=gateway.gateway_id;");
		return personGatewayList;
	}

	public List<Gateway> getGateway() {
		List<Gateway> gatewaylist = Gateway.dao.find("select * from gateway");
		return gatewaylist;
	}
	
	/**
	 * @description 通道管理的查询数据方法<br/>
	 * @methodName getGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月6日下午5:29:47<br/>
	 * @param gateway_id
	 * @param gateway_name
	 * @param gateway_no
	 * @param gateway_status
	 * @return
	 */
	public List<Gateway> getGateway(String gateway_id,String gateway_name,String gateway_no,String gateway_status) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" select g.gateway_id,g.gateway_name,g.gateway_merid,g.status,g.start_time,g.end_time,g.amount_size,g.gateway_rate ");
		sBuffer.append(" from gateway g where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			sBuffer.append(" and gateway_id = "+gateway_id+" ");
		}
		if(StringUtil.isNotEmpty(gateway_name)){
			sBuffer.append(" and gateway_name like '%"+gateway_name+"%' ");
		}
		if(StringUtil.isNotEmpty(gateway_no)){
			sBuffer.append(" and gateway_merid = "+gateway_no+" ");
		}
		if(StringUtil.isNotEmpty(gateway_status)){
			sBuffer.append(" and status = "+gateway_status+" ");
		}
		List<Gateway> gatewaylist = Gateway.dao.find(sBuffer.toString());
		return gatewaylist;
	}
	
	
	public Page<Record> gatewayByPage(String gateway_id,String gateway_name,String gateway_no,String gateway_status,int pageNum,int pageSize) {
		StringBuffer select = new StringBuffer();
		select.append(" select g.gateway_id,g.gateway_name,g.gateway_merid,g.status,g.start_time,g.end_time,g.amount_size,r.ali,r.weixin,r.qq ,(SELECT SUM(o.p3_Amt) FROM orders o WHERE TO_DAYS(o.CreateTime) = TO_DAYS(NOW()) AND o.gateway_id=g.gateway_id) sumamt ");
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from gateway g left join gateway_rate r on g.gateway_id = r.gateway_id where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			sBuffer.append(" and g.gateway_id = "+gateway_id+" ");
		}
		if(StringUtil.isNotEmpty(gateway_name)){
			sBuffer.append(" and g.gateway_name like '%"+gateway_name+"%' ");
		}
		if(StringUtil.isNotEmpty(gateway_no)){
			sBuffer.append(" and g.gateway_merid = "+gateway_no+" ");
		}
		if(StringUtil.isNotEmpty(gateway_status)){
			sBuffer.append(" and g.status = "+gateway_status+" ");
		}
		//System.out.println(select.toString()+sBuffer.toString());
		Page<Record> page= Db.paginate(pageNum, pageSize, select.toString(), sBuffer.toString());
		return page;
	}

	public List<Gateway> findByGatewayId(String gateway_id){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" select g.gateway_id,g.gateway_name,g.gateway_merid,g.status,g.start_time,g.end_time,g.amount_size,g.gateway_key,r.ali,r.weixin,r.qq ");
		sBuffer.append(" from gateway g left join gateway_rate r on g.gateway_id = r.gateway_id "); 
		if(StringUtil.isNotEmpty(gateway_id)){
			sBuffer.append(" where g.gateway_id = "+gateway_id+" ");
		}
		//System.out.println(sBuffer.toString());
		List<Gateway> gatewayList = Gateway.dao.find(sBuffer.toString());
		return gatewayList;
	}
	
	public boolean insertIntoGateway(String gateway_name, String gateway_merid,
			String gateway_key, String wxwap, String weixin, String alipaywap,
			String alipay, String req_url, String notify_url,
			String start_time, String end_time, String amount_size,String gateway_rate) {
		boolean result = false;
		Gateway gateway = new Gateway();
		gateway.set("gateway_name",gateway_name);
		gateway.set("gateway_merid",gateway_merid);
		gateway.set("gateway_key",gateway_key);
		if(StringUtil.isNotEmpty(wxwap)){
			gateway.set("wxwap",wxwap);
		}
		if(StringUtil.isNotEmpty(weixin)){
			gateway.set("weixin",weixin);
		}
		if(StringUtil.isNotEmpty(alipaywap)){
			gateway.set("alipaywap",alipaywap);
		}
		if(StringUtil.isNotEmpty(alipay)){
			gateway.set("alipay",alipay);
		}
		gateway.set("req_url",req_url);
		gateway.set("notify_url",notify_url);
		if(StringUtil.isNotEmpty(start_time)){
			gateway.set("start_time",start_time);
		}
		if(StringUtil.isNotEmpty(end_time)){
			gateway.set("end_time",end_time);
		}
		
		if(StringUtil.isNotEmpty(amount_size)){
			gateway.set("amount_size",amount_size);
		}

		
		if(StringUtil.isNotEmpty(gateway_rate)){
			gateway.set("gateway_rate",gateway_rate);
		}
		
		
		result = gateway.save();
		
		gateway.set("gateway_function",gateway.get("gateway_id"));
		result = gateway.update();
		return result;
	}
	
	public List<Gateway> getGatewayWxwap() {
		List<Gateway> gatewaylist=null;
		try {
			gatewaylist = Gateway.dao.find("select * from gateway where wxwap='y'");
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}

	public List<Gateway> getGatewayAlipaywap() {
		List<Gateway> gatewaylist=null;
		try {
			gatewaylist = Gateway.dao.find("select * from gateway where alipaywap='y'");
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}
	
	public List<Gateway> getGatewayWeixin() {
		List<Gateway> gatewaylist=null;
		try {
			gatewaylist = Gateway.dao.find("select * from gateway where weixin='y'");
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}
	public List<Gateway> getGatewayWxwapByDate(String starttime,String endtime) {
		List<Gateway> gatewaylist=null;
		try {
			String str="select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id and g.wxwap='y' and o.orderid like '%WX%'"
					+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
					+ "' group by g.gateway_id ;";
			System.out.println(str);
			gatewaylist =Gateway.dao.find(str);
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}
	public List<Gateway> getGatewayAlipaywapByDate(String starttime,String endtime) {
		List<Gateway> gatewaylist=null;
		try {
			gatewaylist =Gateway.dao
					.find("select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id and g.alipaywap='y' and o.orderid like '%ALI%'"
							+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
							+ "' group by g.gateway_id ;");
			
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}
	public List<Gateway> getGatewayAllpaywapByDate(String starttime,String endtime) {
		List<Gateway> gatewaylist=null;
		try {
			String str="select g.gateway_id , g.gateway_name ,sum(o.p3_Amt) as amt ,sum(o.amount) as amount,sum(o.deducted) as deducted from gateway g,orders o where o.r1_Code=1 and g.gateway_id=o.gateway_id and "
					+ " o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime
					+ "' group by g.gateway_id ;";
					System.out.println(str);
			gatewaylist =Gateway.dao
					.find(str);
			
		} catch (Exception e) {
			return null;
		}
		return gatewaylist;
	}
	
	public boolean upGateway(int gateway_id,String amount_size,String gateway_merid,String start_time,String end_time,String status,String gateway_key,String ali,String weixin,String qq){
		Gateway gateway = Gateway.dao.findById(gateway_id);
		GatewayRate rate = GatewayRate.dao.findById(gateway_id);
		if(StringUtil.isNotEmpty(amount_size)){
			gateway.set("amount_size", amount_size);
		}
		if(StringUtil.isNotEmpty(gateway_merid)){
			gateway.set("gateway_merid", gateway_merid);
		}
		if(StringUtil.isNotEmpty(start_time)){
			gateway.set("start_time", start_time);
		}
		if(StringUtil.isNotEmpty(end_time)){
			gateway.set("end_time", end_time);
		}
		if(StringUtil.isNotEmpty(status)){
			gateway.set("status", status);
		}
		if(StringUtil.isNotEmpty(gateway_key)){
			gateway.set("gateway_key", gateway_key);
		}
		if(StringUtil.isNotEmpty(ali)){
			rate.set("ali", ali);
		}
		if(StringUtil.isNotEmpty(weixin)){
			rate.set("weixin", weixin);
		}
		if(StringUtil.isNotEmpty(qq)){
			rate.set("qq", qq);
		}
		return gateway.update() && rate.update();
	}
}
