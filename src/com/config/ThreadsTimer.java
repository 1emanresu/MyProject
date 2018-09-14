package com.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.admin.customerlog.serivce.AdCustomerLogService;
import com.admin.gatewaylog.service.AdGatewayLogService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Balance;
import com.vo.DayReport;
import com.vo.Gateway;
import com.vo.GatewayRate;
import com.vo.Order;
import com.vo.Person;
import com.vo.Rate;

public class ThreadsTimer extends TimerTask {

	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("计时器运行中"+new Date()+"START");
		
		//String today = sdf.format(date);
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime())+" 00:00:00";
		cal.add(Calendar.DATE,-1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime())+" 00:00:00";		
		//获取所有用户
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*");
		sql.append(" from balance b");
		List<Balance> balance = Balance.dao.find(sql.toString());
		
		//获取所有通道
		StringBuffer gateway_sql = new StringBuffer();
		gateway_sql.append(" select w.gateway_id ");
		gateway_sql.append(" from gateway w");
		List<Gateway> gateway = Gateway.dao.find(gateway_sql.toString());
		
		//获取当天日期
		SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
		String to_date = todate.format(new Date());
		System.out.println(to_date);
		
		for (Balance ba : balance) {
			int p1_MerId = ba.getInt("id");
			System.out.println("计算器运行过程ID:" + p1_MerId);

			if (ba.getInt("settlementauthority") == 2) {
				String personSql = " select p.id,p.isAgent,p.superior from person p where p.id = "+p1_MerId+" and p.isAgent is not null ";
				List<Person> persons = Person.dao.find(personSql);
				String isAgent = "";
				if(persons != null && persons.size()>0){
					Person person = persons.get(0);
					if(StringUtil.isNotEmpty(person.get("isAgent").toString())){
						isAgent = person.get("isAgent").toString();
					}
				}
				//客户之前的余额
				BigDecimal settlement = ba.getBigDecimal("settlement");
				// 客户每天新进的金额
				BigDecimal newamount = new BigDecimal(0);
				// 客户最后的金额
				BigDecimal nowAmount = new BigDecimal(0);
				//每天支付宝统计的金额
				BigDecimal aliResult = new BigDecimal(0);
				//每天微信统计的金额
				BigDecimal weixinResult = new BigDecimal(0);
				//每天QQ统计的金额
				BigDecimal qqResult = new BigDecimal(0);
				//如果该用户是代理用户
				if (StringUtil.isNotEmpty(isAgent) && isAgent.equals("1")) {
					//统计每天新进金额
					String aliSql = " select sum(p3_Amt) as aliAmount from orders where  r1_Code=1 and p1_MerId = "+p1_MerId+" and pd_FrpId like '%ali%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
					BigDecimal aliAmount = Db.queryBigDecimal(aliSql);
					if(aliAmount == null){
						aliAmount = new BigDecimal(0);
					}
					String weixinSql = " select sum(p3_Amt) as weixinAmount from orders where  r1_Code=1 and p1_MerId = "+p1_MerId+" and pd_FrpId like '%weixin%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
					BigDecimal weixinAmount = Db.queryBigDecimal(weixinSql);
					if(weixinAmount == null){
						weixinAmount = new BigDecimal(0);
					}
					String tenpaySql = " select sum(p3_Amt) as tenpayAmount from orders where  r1_Code=1 and p1_MerId = "+p1_MerId+" and pd_FrpId like '%tenpay%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
					BigDecimal tenpayAmount = Db.queryBigDecimal(tenpaySql);
					if(tenpayAmount == null){
						tenpayAmount = new BigDecimal(0);
					}
					String wxSql = " select sum(p3_Amt) as wxAmount from orders where  r1_Code=1 and p1_MerId = "+p1_MerId+" and pd_FrpId like '%wx%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
					BigDecimal wxAmount = Db.queryBigDecimal(wxSql);
					if(wxAmount  == null){
						wxAmount = new BigDecimal(0);
					}
					
					//查询用户费率
					String rateSql = " select r.id,r.cibsm,r.mustali,r.qqwx from rate r where r.id = "+p1_MerId+" ";
					List<Rate> rates = Rate.dao.find(rateSql);
					if(rates != null && rates.size()>0){
						//阿里费率
						BigDecimal aliRate = rates.get(0).getBigDecimal("mustali");
						if(aliRate != null){
							aliResult = aliAmount.multiply(aliRate);
							newamount = newamount.add(aliResult);
						}
						//微信费率
						BigDecimal weixinRate = rates.get(0).getBigDecimal("cibsm");
						if(weixinRate != null){
							weixinResult = weixinAmount.add(wxAmount).multiply(weixinRate);
							newamount = newamount.add(weixinResult);
						}
						//QQ费率
						BigDecimal qqRate = rates.get(0).getBigDecimal("qqwx");
						if(qqRate != null){
							qqResult = tenpayAmount.multiply(qqRate);
							newamount = newamount.add(qqResult);
						}
					}
				//不是代理用户
				} else {
					String sum_sql = "select sum(amount) as amount from orders where CreateTime>='"+yesterday+"' and CreateTime<'"+today+"' and r1_Code=1 and p1_MerId="+p1_MerId+"";
					//System.out.println(sum_sql);
					List<Order> list = Order.dao.find(sum_sql);
					Order o = list.get(0);
					newamount = o.getBigDecimal("amount");				
					if (newamount == null) {
						newamount = new BigDecimal(0);
					}
				}
				System.out.println("打印今日实际金额-------" + settlement + "---------" + newamount);
				nowAmount = settlement.add(newamount);
				ba.set("settlement", nowAmount);
				ba.set("newamount", 0);
				ba.update();
				// 保存客户新增金额的日志
				AdCustomerLogService.service.saveCustomerLog(p1_MerId, 2,4,newamount,aliResult,weixinResult,qqResult, nowAmount);
			}

			//每天统计数据到day_report表
			for(Gateway wg:gateway){
				int gateway_id = wg.getInt("gateway_id");
				
				//获取成功条数
				String sum_success_sql = "select sum(p3_Amt) as p3_Amt, count(orderid) as order_num, sum(amount) as user_settlement_amount,sum(deducted) as user_profit_amount from orders where CreateTime>='"+yesterday+"' and CreateTime<'"+today+"' and r1_Code=1 and p1_MerId="+p1_MerId+" and gateway_id ="+gateway_id+"";
				List<Record> success_list = Db.find(sum_success_sql);
				BigDecimal success_newamount= new BigDecimal(0);
				Record success_o = success_list.get(0);
				success_newamount = success_o.get("p3_Amt"); //成功金额
				if(success_newamount==null){
					success_newamount= new BigDecimal(0);
				}
				
				//结算金额
				BigDecimal settlement_newamount= new BigDecimal(0);
				Record settlement_o = success_list.get(0);
				settlement_newamount = settlement_o.get("user_settlement_amount"); //成功金额
				if(settlement_newamount==null){
					settlement_newamount= new BigDecimal(0);
				}
				
				//收益金额
				BigDecimal profit_newamount= new BigDecimal(0);
				Record profit_o = success_list.get(0);
				profit_newamount = profit_o.get("user_profit_amount"); //成功金额
				if(profit_newamount==null){
					profit_newamount= new BigDecimal(0);
				}
				
				Long order_success_num = success_o.getLong("order_num"); //总条数
				
				if(order_success_num != 0){
					
					//获取总数
					String sum_sql = "select sum(p3_Amt) as p3_Amt, count(orderid) as order_num from orders where CreateTime>='"+yesterday+"' and CreateTime<'"+today+"' and p1_MerId="+p1_MerId+" and gateway_id ="+gateway_id+"";
					List<Record> list = Db.find(sum_sql);
					BigDecimal allamount= new BigDecimal(0);
					Record o = list.get(0);
					allamount = o.get("p3_Amt");  //总金额
					if(allamount==null){
						allamount= new BigDecimal(0);
					}
					Long order_num = o.getLong("order_num"); //总条数
														
					//金额成功率
					BigDecimal additional_amount= new BigDecimal(0);
					if((!success_newamount.equals(BigDecimal.ZERO)) && (!allamount.equals(BigDecimal.ZERO))){
						additional_amount = success_newamount.divide(allamount,3);
					}
	
					//订单成功率
					BigDecimal additional_order_num = new BigDecimal(0);
					if(order_success_num!=0 && order_num!=0){
						BigDecimal deal_order_bigdecimal = new BigDecimal(order_success_num);
						BigDecimal all_order_bigdecimal = new BigDecimal(order_num);
						additional_order_num = deal_order_bigdecimal.divide(all_order_bigdecimal,4, RoundingMode.HALF_UP);
					}
				
					DayReport dayr = new DayReport();
					dayr.set("date",to_date);
					dayr.set("user_id",p1_MerId);
					dayr.set("gateway_id",gateway_id);
					dayr.set("all_amount",allamount);
					dayr.set("deal_amount",success_newamount);
					dayr.set("amounts_rate",additional_amount);
					dayr.set("all_order",order_num);
					dayr.set("deal_order",order_success_num);
					dayr.set("order_rate",additional_order_num);
					dayr.set("user_settlement_amount",settlement_newamount);
					dayr.set("user_profit_amount",profit_newamount);
					dayr.set("additional_order",0);
					dayr.set("additional_amount",0);
					dayr.set("create_time",today);
					dayr.set("update_user",null);
					dayr.set("update_tiem",null);
					dayr.save();
				}
			}
		}
		//统计每天通道跑的金额
		//从订单中将每天跑的通道分组查询出，只有当天跑了的通道才进行保存日志操作
		List<Order> gatewayList = Order.dao.find(" select o.gateway_id from orders o  where o.CreateTime >= '"+yesterday+"' and o.CreateTime < '"+today+"' group by o.gateway_id ");
		if(gatewayList != null && gatewayList.size()>0){
			for (Order way : gatewayList) {
				int gateway_id = way.getInt("gateway_id");
				String aliSql = " select sum(p3_Amt) as aliAmount from orders where  r1_Code=1 and gateway_id = "+gateway_id+" and pd_FrpId like '%ali%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
				//ali的金额
				BigDecimal ali_amount = Db.queryBigDecimal(aliSql);
				if(ali_amount == null){
					ali_amount = new BigDecimal(0);
				}
				//qq
				String tenpaySql = " select sum(p3_Amt) as tenpayAmount from orders where  r1_Code=1 and gateway_id = "+gateway_id+" and pd_FrpId like '%tenpay%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
				BigDecimal qq_amount = Db.queryBigDecimal(tenpaySql);
				if(qq_amount == null){
					qq_amount = new BigDecimal(0);
				}
				//微信
				String weixinSql = " select sum(p3_Amt) as weixinAmount from orders where  r1_Code=1 and gateway_id = "+gateway_id+" and pd_FrpId like '%weixin%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
				BigDecimal weixinAmount = Db.queryBigDecimal(weixinSql);
				if(weixinAmount == null){
					weixinAmount = new BigDecimal(0);
				}
				String wxSql = " select sum(p3_Amt) as wxAmount from orders where  r1_Code=1 and gateway_id = "+gateway_id+" and pd_FrpId like '%wx%' and CreateTime >= '"+yesterday+"' and CreateTime < '" + today + "' ";
				BigDecimal wxAmount = Db.queryBigDecimal(wxSql);
				if(wxAmount == null){
					wxAmount = new BigDecimal(0);
				}
				
				BigDecimal gateway_amount = ali_amount.add(qq_amount).add(weixinAmount).add(wxAmount);
				BigDecimal wx_amount = weixinAmount.add(wxAmount);
				BigDecimal gateway_rate_amount = new BigDecimal(0);
				BigDecimal ali_rate_amount = new BigDecimal(0);
				BigDecimal wx_rate_amount = new BigDecimal(0);
				BigDecimal qq_rate_amount = new BigDecimal(0);
				String rateSql = " select g.ali,g.weixin,g.qq from gateway_rate g where g.gateway_id = "+gateway_id+" ";
				List<GatewayRate> rates = GatewayRate.dao.find(rateSql);
				if(rates != null && rates.size()>0){
					BigDecimal aliRate = rates.get(0).getBigDecimal("ali");
					ali_rate_amount = ali_amount.multiply(aliRate);
					if(ali_rate_amount == null)
						ali_rate_amount = new BigDecimal(0);
					BigDecimal weixinRate = rates.get(0).getBigDecimal("weixin");
					wx_rate_amount = wx_amount.multiply(weixinRate);
					if(wx_rate_amount == null)
						wx_rate_amount = new BigDecimal(0);
					BigDecimal qqRate = rates.get(0).getBigDecimal("qq");
					qq_rate_amount = qq_amount.multiply(qqRate);
					if(qq_rate_amount == null)
						qq_rate_amount = new BigDecimal(0);
					gateway_rate_amount = ali_rate_amount.add(wx_rate_amount).add(qq_rate_amount);
				}
				AdGatewayLogService.service.saveGatewayLog(gateway_id, gateway_amount, gateway_rate_amount, ali_amount, wx_amount, qq_amount, ali_rate_amount, wx_rate_amount, qq_rate_amount);
			}
		}
		System.out.println("计时器运行中"+new Date()+"END");
	}
}
