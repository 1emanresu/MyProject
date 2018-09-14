package com.admin.system.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Order;

public class AdSystemDao {

	public Page<Record> getSystemQQPage(int pageNumber, int pageSize){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT *");
		frosql.append(" FROM system_qq s");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	/**
	 * @description 订单监控：根据时间和金额查询数据<br/>
	 * @methodName findOrdersByParam<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月21日下午5:21:10<br/>
	 * @param beforeTime
	 * @param nowTime
	 * @param minAmount
	 * @param maxAmount
	 * @return
	 */
	public List<Order> findOrdersByParam(String beforeTime,String nowTime,String minAmount,String maxAmount){
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		select.append(" select o.gateway_id,count(1) as countOrder,g.gateway_name ");
		from.append(" from orders o ");
		from.append(" left join gateway g on o.gateway_id = g.gateway_id where 1=1 ");
		if(StringUtil.isNotEmpty(minAmount) && StringUtil.isNotEmpty(maxAmount)){
			from.append(" and o.p3_Amt >= "+minAmount+" and o.p3_Amt <= "+maxAmount+" ");
		}
		if(StringUtil.isNotEmpty(beforeTime) && StringUtil.isNotEmpty(nowTime)){
			from.append(" and o.CreateTime between '"+beforeTime+"' and '"+nowTime+"' ");
		}
		from.append(" group by gateway_id ");
		//System.out.println("order====="+select.toString()+from.toString());
		return Order.dao.find(select.toString()+from.toString());
	}
	
	/**
	 * @description 订单监控：根据时间和金额查询出成功的订单<br/>
	 * @methodName successOrdersByParam<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月21日下午5:22:45<br/>
	 * @param beforeTime
	 * @param nowTime
	 * @param minAmount
	 * @param maxAmount
	 * @return
	 */
	public List<Order> successOrdersByParam(String beforeTime,String nowTime,String minAmount,String maxAmount){
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		select.append(" select o.gateway_id,count(1) as successOrder ");
		from.append(" from orders o ");
		from.append(" where o.r1_Code = 1 ");
		if(StringUtil.isNotEmpty(minAmount) && StringUtil.isNotEmpty(maxAmount)){
			from.append(" and o.p3_Amt >= "+minAmount+" and o.p3_Amt <= "+maxAmount+" ");
		}
		if(StringUtil.isNotEmpty(beforeTime) && StringUtil.isNotEmpty(nowTime)){
			from.append(" and o.CreateTime between '"+beforeTime+"' and '"+nowTime+"' ");
		}
		from.append(" group by gateway_id ");
		//System.out.println("success====="+select.toString()+from.toString());
		return Order.dao.find(select.toString()+from.toString());
	}
}