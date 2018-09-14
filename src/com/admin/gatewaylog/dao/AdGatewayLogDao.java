package com.admin.gatewaylog.dao;

import java.math.BigDecimal;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.GatewayLog;

public class AdGatewayLogDao {
	
	/**
	 * @description 保存通道统计的日志<br/>
	 * @methodName saveGatewayLog<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月31日下午4:48:01<br/>
	 * @param gateway_id
	 * @param gateway_amount
	 * @param gateway_rate_amount
	 * @param ali_amount
	 * @param wx_amount
	 * @param qq_amount
	 * @param ali_rate_amount
	 * @param wx_rate_amount
	 * @param qq_rate_amount
	 * @return
	 */
	public boolean saveGatewayLog(int gateway_id,BigDecimal gateway_amount,BigDecimal gateway_rate_amount,BigDecimal ali_amount,BigDecimal wx_amount,BigDecimal qq_amount,BigDecimal ali_rate_amount,BigDecimal wx_rate_amount,BigDecimal qq_rate_amount){
		GatewayLog log = new GatewayLog();
		log.set("gateway_id", gateway_id);
		log.set("gateway_amount", gateway_amount);
		log.set("gateway_rate_amount", gateway_rate_amount);
		log.set("ali_amount", ali_amount);
		log.set("wx_amount", wx_amount);
		log.set("qq_amount", qq_amount);
		log.set("ali_rate_amount", ali_rate_amount);
		log.set("wx_rate_amount", wx_rate_amount);
		log.set("qq_rate_amount", qq_rate_amount);
		return log.save();
	}
	
	/**
	 * @description 查询通道的日志信息<br/>
	 * @methodName queryGatewayLog<br/>
	 * @author jack<br/>
	 * @createTime 2017年11月1日下午1:24:15<br/>
	 * @param pageNumber
	 * @param pageSize
	 * @param gateway_id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public Page<Record> queryGatewayLog(int pageNumber, int pageSize,String gateway_id,String starttime,String endtime) {
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		select.append(" select g.gateway_id,w.gateway_name,g.gateway_amount,g.gateway_rate_amount,g.ali_amount,g.wx_amount,g.qq_amount,g.ali_rate_amount,g.wx_rate_amount,g.qq_rate_amount,g.create_time ");
		from.append(" from gateway_log g ");
		from.append(" left join gateway w on g.gateway_id = w.gateway_id ");
		from.append(" where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			from.append(" and g.gateway_id = "+gateway_id+" ");
		}
		if(StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)){
			from.append(" and g.create_time >= '"+starttime+"' and g.create_time < '"+endtime+"' ");
		}
		from.append(" order by g.create_time desc ");
		Page<Record> page = Db.paginate(pageNumber, pageSize,select.toString(), from.toString());
		return page;
	}

}
