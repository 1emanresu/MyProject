package com.admin.statement.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.DayReport;
import com.vo.Gateway;
import com.vo.Person;

public class AdStatementDao {
	/**
	 * @description 统计报表的分页查询的方法<br/>
	 * @methodName statementByPage<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:26:12<br/>
	 * @param pageNum
	 * @param pageSize
	 * @param user_id
	 * @param gateway_id
	 * @param statementStarttime
	 * @param statementEndtime
	 * @return
	 */
	public static Page<Record> statementByPage(int pageNum,int pageSize,String user_id,String gateway_id,String statementStarttime,String statementEndtime){
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer(); 
		select.append(" select r.user_id,r.gateway_id,r.all_amount,r.deal_amount,r.amounts_rate,r.all_order,r.deal_order,r.order_rate,r.additional_order,r.additional_amount,r.create_time,r.update_user,r.date,r.platform_profit_amount,r.user_settlement_amount,r.user_profit_amount ");
		from.append(" from day_report r where 1=1 ");
		if(StringUtil.isNotEmpty(user_id)){
			from.append(" and user_id = "+user_id+"");
		}
		if(StringUtil.isNotEmpty(gateway_id)){
			from.append(" and gateway_id = "+gateway_id+"");
		}
		if(StringUtil.isNotEmpty(statementStarttime) && StringUtil.isNotEmpty(statementEndtime)){
			from.append(" and date >= '"+statementStarttime+"' and date <'"+statementEndtime+"' ");
		}
		//System.out.println("sql====="+select.toString()+from.toString());
		Page<Record> dayReports = Db.paginate(pageNum, pageSize, select.toString(), from.toString());
		return dayReports;
	}
	
	/**
	 * @description 用户报表的方法<br/>
	 * @methodName statementUser<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:27:04<br/>
	 * @param user_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> profitorder(String statement_time){
		try{
		List<DayReport> dayReports = null;
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer(); 
		select.append(" select COUNT(orderid) AS count_order,SUM(p3_Amt) AS count_p3amount,SUM(amount) AS count_amount,SUM(deducted) AS count_deducted,p1_MerId,gateway_id");
		from.append(" from orders r where 1=1 ");
		if(StringUtil.isNotEmpty(statement_time)){
			SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
			Date dst = todate.parse(statement_time);
			Calendar date = Calendar.getInstance();
			date.setTime(dst);
			date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
			String strt = todate.format(date.getTime());
			from.append(" and `CreateTime` >= '"+strt+"' and `CreateTime` < '"+statement_time+"' ");
		}
		from.append(" AND r1_Code=1 GROUP BY p1_MerId,gateway_id");
		dayReports = DayReport.dao.find(select.toString()+from.toString());
		return dayReports;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * @description 用户报表的方法<br/>
	 * @methodName statementUser<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:27:04<br/>
	 * @param user_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> getdreportgroupuw(String user_id,String gateway_id,String statement_time){
		List<DayReport> dayReports = null;
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer(); 
		select.append(" select *");
		from.append(" from day_report r where 1=1 ");
		if(StringUtil.isNotEmpty(user_id)){
			from.append(" and user_id = "+user_id+"");
		}
		if(StringUtil.isNotEmpty(gateway_id)){
			from.append(" and gateway_id = '"+gateway_id+"' ");
		}
		if(StringUtil.isNotEmpty(statement_time)){
			from.append(" and `date` = '"+statement_time+"' ");
		}
		dayReports = DayReport.dao.find(select.toString()+from.toString());
		return dayReports;
	}
	
	/**
	 * @description 用户报表的方法<br/>
	 * @methodName statementUser<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:27:04<br/>
	 * @param user_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> statementUser(String user_id,String statement_time){
		List<DayReport> dayReports = null;
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer(); 
		select.append(" select r.date,r.user_id,r.gateway_id,r.all_amount,r.deal_amount,r.amounts_rate,r.all_order,r.deal_order,r.order_rate,r.platform_profit_amount,r.user_profit_amount,r.additional_order,r.additional_amount,r.platform_profit_amount,r.user_settlement_amount,r.user_profit_amount,r.additional_order,r.additional_amount,r.additional_deal_amount");
		from.append(" from day_report r where 1=1 ");
		if(StringUtil.isNotEmpty(user_id)){
			from.append(" and user_id = "+user_id+"");
		}
		if(StringUtil.isNotEmpty(statement_time)){
			from.append(" and `date` = '"+statement_time+"' ");
		}
		from.append(" order by user_id asc ");
		dayReports = DayReport.dao.find(select.toString()+from.toString());
		return dayReports;
	}
	
	/**
	 * @description 通道报表的查询通道的方法<br/>
	 * @methodName gateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:27:44<br/>
	 * @param gateway_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> gateway(String gateway_id,String statement_time){
		List<DayReport> dayReports = null;
		StringBuffer selectSql = new StringBuffer();
		StringBuffer fromSql = new StringBuffer();
		selectSql.append(" select date,user_id,gateway_id,all_amount,deal_amount,all_order,order_rate,amounts_rate,deal_order,platform_profit_amount,user_profit_amount,additional_order,additional_amount,platform_profit_amount,user_settlement_amount,user_profit_amount,additional_order,additional_amount,additional_deal_amount");
		fromSql.append(" from day_report where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			fromSql.append(" and gateway_id = '"+gateway_id+"' ");
		}
		if(StringUtil.isNotEmpty(statement_time)){
			fromSql.append(" and `date` = '"+statement_time+"' ");
		}
		fromSql.append(" order by gateway_id asc ");
		//System.out.println("gateway==="+selectSql.toString()+fromSql.toString());
		dayReports = DayReport.dao.find(selectSql.toString()+fromSql.toString());
		return dayReports;
	}
	
	/**
	 * @description 根据通道id求出通道金额的方法<br/>
	 * @methodName sumGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:25:57<br/>
	 * @param gateway_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> sumGateway(String gateway_id,String statement_time){
		List<DayReport> dayReports = null;
		StringBuffer selectSql = new StringBuffer();
		StringBuffer fromSql = new StringBuffer();
		selectSql.append(" select date,user_id,gateway_id,sum(all_amount) as all_all_amount,sum(deal_amount) as all_deal_amount,sum(all_order) as all_all_order,sum(deal_order) as all_deal_order ");
		fromSql.append(" from day_report where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			fromSql.append(" and gateway_id = '"+gateway_id+"' ");
		}
		if(StringUtil.isNotEmpty(statement_time)){
			fromSql.append(" and `date` = '"+statement_time+"' ");
		}
		fromSql.append(" group by gateway_id ");
		fromSql.append(" order by gateway_id asc ");
		//System.out.println("sumgateway==="+selectSql.toString()+fromSql.toString());
		dayReports = DayReport.dao.find(selectSql.toString()+fromSql.toString());
		return dayReports;
	}
	
	/**
	 * @description 根据通道id分组查询出各个通道的数量<br/>
	 * @methodName countGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:29:26<br/>
	 * @param gateway_id
	 * @param statement_time
	 * @return
	 */
	public static List<DayReport> countGateway(String gateway_id,String statement_time){
		List<DayReport> dayReports = null;
		StringBuffer selectSql = new StringBuffer();
		StringBuffer fromSql = new StringBuffer();
		selectSql.append(" select gateway_id,count(1) as countGateway ");
		fromSql.append(" from day_report where 1=1 ");
		if(StringUtil.isNotEmpty(gateway_id)){
			fromSql.append(" and gateway_id = '"+gateway_id+"' ");
		}
		if(StringUtil.isNotEmpty(statement_time)){
			fromSql.append(" and date = '"+statement_time+"' ");
		}
		fromSql.append(" group by gateway_id  order by gateway_id asc ");
		dayReports = DayReport.dao.find(selectSql.toString()+fromSql.toString());
		return dayReports;
	}
	
	/**
	 * @description 查询出所有的通道的方法<br/>
	 * @methodName findGateways<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:32:11<br/>
	 * @return
	 */
	public List<Gateway> findGateways(){
		String sql = " select gateway_id,gateway_name from gateway ";
		return Gateway.dao.find(sql);
	}
	
	/**
	 * @description 查询出所有的用户的名字<br/>
	 * @methodName findPersons<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月14日下午5:41:09<br/>
	 * @return
	 */
	public List<Person> findPersons(){
		String sql = " select id,webName from person ";
		return Person.dao.find(sql);
	}
}
