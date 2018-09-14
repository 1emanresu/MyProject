package com.admin.customerlog.dao;

import java.math.BigDecimal;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.CustomerBalanceLog;

public class AdCustomerLogDao {
	
	/**
	 * @description 保存客户操作的日志的方法<br/>
	 * @methodName saveCustomerLog<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月23日下午6:11:03<br/>
	 * @param customer_id 客户id<br/>
	 * @param operation_type 操作类型<br/>
	 * @param refund_type 操作状态<br/>
	 * @param amount 涉及的金额<br/>
	 * @param aliResult 支付宝跑的金额<br/>
	 * @param weixinResult 微信跑的金额<br/>
	 * @param qqResult qq跑的金额<br/>
	 * @param settlement 客户账户的余额<br/>
	 * @return
	 */
	public boolean saveCustomerLog(int customer_id,int operation_type,int refund_type,BigDecimal amount,BigDecimal aliResult,BigDecimal weixinResult,BigDecimal qqResult,BigDecimal settlement){
		CustomerBalanceLog log = new CustomerBalanceLog();
		log.set("customer_id", customer_id);
		log.set("operation_type", operation_type);
		if(refund_type != 4){
			log.set("refund_type", refund_type);
		}
		if(amount == null){
			amount = new BigDecimal(0);
		}
		log.set("amount", amount);
		if(aliResult == null){
			aliResult = new BigDecimal(0);
		}
		log.set("ali_result", aliResult);
		if(weixinResult == null){
			weixinResult = new BigDecimal(0);
		}
		log.set("weixin_result", weixinResult);
		if(qqResult == null){
			qqResult = new BigDecimal(0);
		}
		log.set("qq_result", qqResult);
		if(settlement == null){
			settlement = new BigDecimal(0);
		}
		log.set("settlement", settlement);
		return log.save();
	}
	
	/**
	 * @description 根据用户id或者创建时间查询客户的log日志<br/>
	 * @methodName findCustomerLog<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月24日下午2:48:40<br/>
	 * @param pageNumber
	 * @param pageSize
	 * @param customer_id
	 * @param customerstarttime
	 * @param customerendtime
	 * @return
	 */
	public Page<Record> findCustomerLog(int pageNumber, int pageSize,String customer_id,String customerstarttime,String customerendtime,String operatType,String refundType){
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		select.append("  select c.customer_id,c.operation_type,c.create_time,c.amount,c.settlement,c.ali_result,c.weixin_result,c.qq_result,p.isAgent,c.refund_type ");
		from.append(" from customer_balance_log c ");
		from.append(" left join person p ");
		from.append(" on c.customer_id = p.id where 1=1 ");
		if(StringUtil.isNotEmpty(customer_id)){
			from.append(" and c.customer_id = "+customer_id+" ");
		}
		if(StringUtil.isNotEmpty(operatType)){
			from.append(" and c.operation_type = "+operatType+" ");
		}
		if(StringUtil.isNotEmpty(refundType)){
			from.append(" and c.refund_type = "+refundType+" ");
		}
		if(StringUtil.isNotEmpty(customerstarttime) && StringUtil.isNotEmpty(customerendtime)){
			from.append(" and c.create_time >= '"+customerstarttime+"' and c.create_time < '"+customerendtime+"' ");
		}
		from.append(" order by c.create_time desc ");
		//System.out.println(select.toString()+from.toString());
		Page<Record> records = Db.paginate(pageNumber, pageSize, select.toString(),from.toString());
		return records;
	}
	
}
