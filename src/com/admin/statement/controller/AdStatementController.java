package com.admin.statement.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.admin.statement.service.AdStatementService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.DayReport;
import com.vo.Gateway;
import com.vo.Person;
@Before(AdLoginSeInterceptor.class)
public class AdStatementController extends Controller{
	public void index(){
		try {
			int pageNum = getParaToInt("pageNum");
			int numPerPage=1;
			if(getParaToInt("numPerPage")!=null){
				numPerPage = getParaToInt("numPerPage");
			}
			String user_id = "";
			String gateway_id = "";
			String statementStarttime = "";
			String statementEndtime = "";
			if(StringUtil.isNotEmpty(getPara("user_id"))){
				user_id = getPara("user_id");
			}
			if(StringUtil.isNotEmpty(getPara("gateway_id"))){
				gateway_id = getPara("gateway_id");
			}
			if(StringUtil.isNotEmpty(getPara("statementStarttime"))){
				statementStarttime = getPara("statementStarttime");
			}
			if(StringUtil.isNotEmpty(getPara("statementEndtime"))){
				statementEndtime = getPara("statementEndtime");
			}
			
			Page<Record> dayReports = AdStatementService.service.statementByPage(pageNum,numPerPage,user_id,gateway_id,statementStarttime,statementEndtime);
			setAttr("user_id", user_id); 
			setAttr("gateway_id", gateway_id);
			setAttr("statementStarttime", statementStarttime);
			setAttr("statementEndtime", statementEndtime);
			setAttr("pageNum", dayReports.getPageNumber());
			setAttr("numPerPage", dayReports.getPageSize());
			setAttr("totalCount", dayReports.getTotalRow());
			setAttr("pageNumShown", dayReports.getTotalPage());
			setAttr("dayReports", dayReports.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/statement/statement.jsp");
	}
	
	public void profit(){
		try {
			String statement_time = "";
			if(StringUtil.isNotEmpty(getPara("statement_time"))){
				statement_time = getPara("statement_time");
			}
			if(StringUtil.isEmpty(getPara("statement_time"))){
				SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
				statement_time = todate.format(new Date());
			}
			List<DayReport> dayrepores = AdStatementService.service.profitorder(statement_time);
			
			for (DayReport dayReport : dayrepores) {
				BigDecimal count_deducted = (BigDecimal) dayReport.get("count_deducted");
				BigDecimal count_amount = (BigDecimal) dayReport.get("count_amount");
				BigDecimal count_p3amount = (BigDecimal) dayReport.get("count_p3amount");
				Long count_order = dayReport.get("count_order");
				int p1_MerId = dayReport.get("p1_MerId");
				int gateway_id = dayReport.get("gateway_id");
				
				String str_p1_MerId = p1_MerId+"";
				String str_gateway_id = gateway_id+"";
				
				List<DayReport> dayreporess = AdStatementService.service.getdreportgroupuw(str_p1_MerId,str_gateway_id,statement_time);
				
				DayReport dayr = dayreporess.get(0);
				
				//判断成交订单是否有增加
				int deal_order = dayr.getInt("deal_order");
				BigDecimal deal_amount = (BigDecimal) dayr.get("deal_amount");
				BigDecimal user_settlement_amount = (BigDecimal) dayr.get("user_settlement_amount");
				if(deal_order < count_order){
					dayr.set("deal_order", count_order);
					dayr.set("deal_amount", count_p3amount);
					dayr.set("user_profit_amount", count_deducted);
					dayr.set("user_settlement_amount", count_amount);
					
					dayr.set("additional_order", count_order - deal_order);
					dayr.set("additional_amount", count_p3amount.subtract(deal_amount));
					dayr.set("additional_deal_amount", count_amount.subtract(user_settlement_amount));
					dayr.update();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson("{\"statusCode\":\"1\", \"message\":\"更新成功\"}");
	}
	
	public void showUser(){
		try {
			String user_id = "";
			String statement_time = "";
			if(StringUtil.isNotEmpty(getPara("user_id"))){
				user_id = getPara("user_id");
			}
			if(StringUtil.isNotEmpty(getPara("statement_time"))){
				statement_time = getPara("statement_time");
			}
			if(StringUtil.isEmpty(getPara("statement_time"))){
				SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
				statement_time = todate.format(new Date());
			}
			List<DayReport> dayrepores = AdStatementService.service.statementUser(user_id, statement_time);
			List<DayReport> dayrepores1 = dayrepores;
			List<String> user_id_list = new ArrayList<String>();
			Map<String,Object> userMap = new HashMap<String, Object>();
			Map<String,Object> gatewayMap = new HashMap<String,Object>();
			//计算用户
			for (DayReport dayReport : dayrepores) {
				//System.out.println("dayReport.toString()"+dayReport.toString());
				Map<String,Object> tempMap = new HashMap<String, Object>();
				Map<String, Object> oldtempMap = new HashMap<String, Object>();
				String userid = dayReport.getInt("user_id").toString();
				//判断map中是否存在
				//System.out.println("userMap.get(userid)"+userMap.get(userid));
				
				if(wguseList(user_id_list,userid)){
					oldtempMap = (Map<String, Object>) userMap.get(userid);
					
					//累加数据
					BigDecimal all_amount = (BigDecimal) oldtempMap.get("all_amount");
					BigDecimal deal_amount = (BigDecimal) oldtempMap.get("deal_amount");
					BigDecimal all_order =  new BigDecimal(oldtempMap.get("all_order").toString());
					BigDecimal deal_order = new BigDecimal(oldtempMap.get("deal_order").toString());
					
					
					BigDecimal all_user_settlement_amount = (BigDecimal) oldtempMap.get("user_settlement_amount");
					BigDecimal all_user_profit_amount = (BigDecimal) oldtempMap.get("user_profit_amount");
	
					BigDecimal save_all_amount = dayReport.getBigDecimal("all_amount").add(all_amount);
					BigDecimal save_deal_amount = dayReport.getBigDecimal("deal_amount").add(deal_amount);
					BigDecimal save_all_order = new BigDecimal(dayReport.get("all_order").toString()).add(all_order);
					BigDecimal save_deal_order = new BigDecimal(dayReport.get("deal_order").toString()).add(deal_order);
					BigDecimal user_settlement_amount = dayReport.getBigDecimal("user_settlement_amount").add(all_user_settlement_amount);
					BigDecimal user_profit_amount = dayReport.getBigDecimal("user_profit_amount").add(all_user_profit_amount);
			
					tempMap.put("all_amount", save_all_amount);
					tempMap.put("deal_amount", save_deal_amount);
					tempMap.put("all_order", save_all_order);
					tempMap.put("deal_order", save_deal_order);
					tempMap.put("user_settlement_amount", user_settlement_amount);
					tempMap.put("user_profit_amount", user_profit_amount);
					
					//计算率
					if(save_deal_amount.compareTo(new BigDecimal(0))>0){
					    if(save_all_amount.compareTo(new BigDecimal(0))>0){
					    	tempMap.put("amount_rate", save_deal_amount.divide(save_all_amount,4, RoundingMode.HALF_UP).setScale(3,BigDecimal.ROUND_HALF_UP));
					    }else{
					    	tempMap.put("amount_rate", 0);
					    }
					}else{
						tempMap.put("amount_rate", 0);
					}
					if(save_deal_order.compareTo(new BigDecimal(0))>0){
						if(save_all_order.compareTo(new BigDecimal(0))>0){
							BigDecimal order_bigdecimal = save_deal_order.divide(save_all_order,4, RoundingMode.HALF_UP);
							tempMap.put("order_rate",order_bigdecimal.setScale(3,BigDecimal.ROUND_HALF_UP));
						}else{
							tempMap.put("order_rate",0);
						}	
					}else{
						tempMap.put("order_rate",0);
					}
					
					userMap.put(userid, tempMap);
					
				}else{

					BigDecimal all_amount = dayReport.getBigDecimal("all_amount");
					BigDecimal deal_amount = dayReport.getBigDecimal("deal_amount");
					
					int all_order = dayReport.getInt("all_order");
					int deal_order = dayReport.getInt("deal_order");
					
					BigDecimal user_settlement_amount = dayReport.getBigDecimal("user_settlement_amount");
					BigDecimal user_profit_amount = dayReport.getBigDecimal("user_profit_amount");
					
					tempMap.put("all_amount", all_amount);
					tempMap.put("deal_amount",deal_amount);
					tempMap.put("all_order", all_order);
					tempMap.put("deal_order", deal_order);
					tempMap.put("user_settlement_amount", user_settlement_amount);
					tempMap.put("user_profit_amount", user_profit_amount);

					//计算率
					if(deal_amount.compareTo(new BigDecimal(0))>0){
					    if(all_amount.compareTo(new BigDecimal(0))>0){
					    	tempMap.put("amount_rate", deal_amount.divide(all_amount,4, RoundingMode.HALF_UP).setScale(3,BigDecimal.ROUND_HALF_UP));
					    }else{
					    	tempMap.put("amount_rate", 0);
					    }
					}else{
						tempMap.put("amount_rate", 0);
					}
					if(deal_order!=0 && all_order!=0){

						BigDecimal deal_order_bigdecimal = new BigDecimal(deal_order);
						BigDecimal all_order_bigdecimal = new BigDecimal(all_order);
						BigDecimal order_bigdecimal = deal_order_bigdecimal.divide(all_order_bigdecimal,4, RoundingMode.HALF_UP);
						tempMap.put("order_rate", order_bigdecimal.setScale(3,BigDecimal.ROUND_HALF_UP));
					}else{
						tempMap.put("order_rate",0);
					}
					
					//保存新的数据
					user_id_list.add(userid);
					userMap.put(userid, tempMap);
				}
			}
			//计算用户对应的通道
			for (String wg_user_id : user_id_list) {
				List<DayReport> gatewayList = new ArrayList<DayReport>();
				for (DayReport tdayReport : dayrepores1) {
					int userid = tdayReport.getInt("user_id");
					int wg_userid = Integer.parseInt(wg_user_id);
					if(userid == wg_userid){
						gatewayList.add(tdayReport);
					}
				}
				gatewayMap.put(wg_user_id, gatewayList);
			}
			
			List<Gateway> gatewayName = AdStatementService.service.findGateways();
			Map<Integer,String> wgnameMap = new HashMap<Integer,String>();
			for(Gateway attribute : gatewayName) {
				wgnameMap.put(attribute.getInt("gateway_id"), attribute.getStr("gateway_name"));
			}
			
			List<Person> personName = AdStatementService.service.findPersons();
			Map<Integer,String> unameMap = new HashMap<Integer,String>();
			for(Person attribute : personName) {
				unameMap.put(attribute.getInt("id"), attribute.getStr("webName"));
			}
			

			BigDecimal count_all_order_bigdecimal = new BigDecimal(0);
			BigDecimal count_all_amount_bigdecimal = new BigDecimal(0);
			BigDecimal count_user_settlement_amount_bigdecimal = new BigDecimal(0);
			BigDecimal count_user_profit_amount_bigdecimal = new BigDecimal(0);
			
			String html = "";
			Iterator<Entry<String, Object>> entries = userMap.entrySet().iterator();
			while (entries.hasNext()) {
				html += "<tr>";
			    Entry<String, Object> entry = entries.next();
			    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			    
			    List<DayReport> templist = (List<DayReport>) gatewayMap.get(entry.getKey());
			    
			    int listsize = templist.size()+1;
			    
			    Map<String,Object> resmap = (Map<String, Object>) entry.getValue();
			    

			    count_all_order_bigdecimal = count_all_order_bigdecimal.add(new BigDecimal(resmap.get("deal_order").toString()));
			    count_all_amount_bigdecimal = count_all_amount_bigdecimal.add(new BigDecimal(resmap.get("deal_amount").toString()));
			    count_user_settlement_amount_bigdecimal = count_user_settlement_amount_bigdecimal.add(new BigDecimal(resmap.get("user_settlement_amount").toString()));
			    count_user_profit_amount_bigdecimal = count_user_profit_amount_bigdecimal.add(new BigDecimal(resmap.get("user_profit_amount").toString()));
			    
				html += "<td rowspan="+listsize+">"+entry.getKey()+"</td>";
				html += "<td rowspan="+listsize+">"+unameMap.get(Integer.parseInt(entry.getKey()))+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("user_settlement_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("user_profit_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("deal_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("amount_rate")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("deal_order")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("order_rate")+"</td>";
			    
				for(DayReport attribute : templist) {
					  html += "<td>"+attribute.get("gateway_id")+"</td>";
					  html += "<td>"+wgnameMap.get(attribute.get("gateway_id"))+"</td>";
					  html += "<td>"+attribute.get("user_settlement_amount")+"</td>";
					  html += "<td>"+attribute.get("user_profit_amount")+"</td>";
					  html += "<td>"+attribute.get("deal_amount")+"</td>";
					  html += "<td>"+attribute.get("amounts_rate")+"</td>";
					  html += "<td>"+attribute.get("deal_order")+"</td>";
					  html += "<td>"+attribute.get("order_rate")+"</td>";
					  html += "<td>"+attribute.get("additional_order")+"</td>";
					  html += "<td>"+attribute.get("additional_amount")+"</td>";
					  html += "<td>"+attribute.get("additional_deal_amount")+"</td>";
					  html += "</tr><tr>";
				}
				
			    html += "</tr>";
			}

			html += "<tr>";
			html += "<td colspan=18 style=\"color: red;\">成功订单："+count_all_order_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;成功金额："+count_all_amount_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;结算金额："+count_user_settlement_amount_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;平台利润："+count_user_profit_amount_bigdecimal+"</td>";
			html += "</tr>";

			setAttr("user_id", user_id);
			setAttr("statement_time", statement_time);
			setAttr("table_html",html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/statement/user.jsp");
	}
	
	private boolean wguseList(List<String> user_id_list, String userid) {
		// TODO Auto-generated method stub
		boolean flag = user_id_list.contains(userid);
		return flag;
		
	}

	public void showGateway(){
		String gateway_id = "";
		String statement_time = "";
		String html = "";
		try {
			if(StringUtil.isNotEmpty(getPara("gateway_id"))){
				gateway_id = getPara("gateway_id");
			}
			if(StringUtil.isNotEmpty(getPara("statement_time"))){
				statement_time = getPara("statement_time");
			}
			if(StringUtil.isEmpty(getPara("statement_time"))){
				SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
				statement_time = todate.format(new Date());
			}
			//获取网关数据
			List<DayReport> dayrepores = AdStatementService.service.gateway(gateway_id, statement_time);
			//预留查询结果
			List<DayReport> dayrepores1 = dayrepores;
			//存储网关id
			List<String> gateway_id_list = new ArrayList<String>();
			//存储用户数据
			Map<String,Object> userMap = new HashMap<String, Object>();
			//存储网关数据
			Map<String,Object> gatewayMap = new HashMap<String,Object>();
			//计算网关数据
			for (DayReport dayReport : dayrepores) {
				
				Map<String,Object> tempMap = new HashMap<String, Object>();
				//历史网关数据
				Map<String, Object> oldtempMap = new HashMap<String, Object>();
				//获取当前网关id
				String gatewayid = dayReport.getInt("gateway_id").toString();
				//判断map中是否存在当前网关id
				if(wguseList(gateway_id_list,gatewayid)){
					
					oldtempMap = (Map<String, Object>) gatewayMap.get(gatewayid);
					//累加数据
					BigDecimal all_amount = (BigDecimal) oldtempMap.get("all_amount");
					BigDecimal deal_amount = (BigDecimal) oldtempMap.get("deal_amount");
					BigDecimal all_order =  new BigDecimal(oldtempMap.get("all_order").toString());
					BigDecimal deal_order = new BigDecimal(oldtempMap.get("deal_order").toString());
					BigDecimal all_user_settlement_amount = (BigDecimal) oldtempMap.get("user_settlement_amount");
					BigDecimal all_user_profit_amount = (BigDecimal) oldtempMap.get("user_profit_amount");
					
	
					BigDecimal save_all_amount = dayReport.getBigDecimal("all_amount").add(all_amount);
					BigDecimal save_deal_amount = dayReport.getBigDecimal("deal_amount").add(deal_amount);
					BigDecimal save_all_order = new BigDecimal(dayReport.get("all_order").toString()).add(all_order);
					BigDecimal save_deal_order = new BigDecimal(dayReport.get("deal_order").toString()).add(deal_order);
					BigDecimal save_user_settlement_amount = dayReport.getBigDecimal("user_settlement_amount").add(all_user_settlement_amount);
					BigDecimal save_user_profit_amount = dayReport.getBigDecimal("user_profit_amount").add(all_user_profit_amount);
					
					tempMap.put("all_amount", save_all_amount);
					tempMap.put("deal_amount", save_deal_amount);
					tempMap.put("all_order", save_all_order);
					tempMap.put("deal_order", save_deal_order);
					tempMap.put("user_settlement_amount", save_user_settlement_amount);
					tempMap.put("user_profit_amount", save_user_profit_amount);
					
					//计算率
					if(save_deal_amount.compareTo(new BigDecimal(0))>0){
					    if(save_all_amount.compareTo(new BigDecimal(0))>0){
					    	tempMap.put("amount_rate", save_deal_amount.divide(save_all_amount,4, RoundingMode.HALF_UP).setScale(3,BigDecimal.ROUND_HALF_UP));
					    }else{
					    	tempMap.put("amount_rate", 0);
					    }
					}else{
						tempMap.put("amount_rate", 0);
					}
					if(save_deal_order.compareTo(new BigDecimal(0))>0){
						if(save_all_order.compareTo(new BigDecimal(0))>0){
							BigDecimal order_bigdecimal = save_deal_order.divide(save_all_order,4, RoundingMode.HALF_UP);
							tempMap.put("order_rate",order_bigdecimal.setScale(3,BigDecimal.ROUND_HALF_UP));
						}else{
							tempMap.put("order_rate",0);
						}	
					}else{
						tempMap.put("order_rate",0);
					}
					
					gatewayMap.put(gatewayid, tempMap);
					
				}else{
	
					BigDecimal all_amount = dayReport.getBigDecimal("all_amount");
					BigDecimal deal_amount = dayReport.getBigDecimal("deal_amount");
					
					int all_order = dayReport.getInt("all_order");
					int deal_order = dayReport.getInt("deal_order");
					
					BigDecimal all_user_settlement_amount = dayReport.getBigDecimal("user_settlement_amount");
					BigDecimal all_user_profit_amount = dayReport.getBigDecimal("user_profit_amount");
					
					tempMap.put("all_amount", all_amount);
					tempMap.put("deal_amount",deal_amount);
					tempMap.put("all_order", all_order);
					tempMap.put("deal_order", deal_order);
					tempMap.put("user_settlement_amount", all_user_settlement_amount);
					tempMap.put("user_profit_amount", all_user_profit_amount);
	
					//计算率
					if(deal_amount.compareTo(new BigDecimal(0))>0){
					    if(all_amount.compareTo(new BigDecimal(0))>0){
					    	tempMap.put("amount_rate", deal_amount.divide(all_amount,4, RoundingMode.HALF_UP).setScale(3,BigDecimal.ROUND_HALF_UP));
					    }else{
					    	tempMap.put("amount_rate", 0);
					    }
					}else{
						tempMap.put("amount_rate", 0);
					}
					if(deal_order!=0 && all_order!=0){
	
						BigDecimal deal_order_bigdecimal = new BigDecimal(deal_order);
						BigDecimal all_order_bigdecimal = new BigDecimal(all_order);
						BigDecimal order_bigdecimal = deal_order_bigdecimal.divide(all_order_bigdecimal,4, RoundingMode.HALF_UP);
						tempMap.put("order_rate", order_bigdecimal.setScale(3,BigDecimal.ROUND_HALF_UP));
					}else{
						tempMap.put("order_rate",0);
					}
					//保存新的数据
					gateway_id_list.add(gatewayid);
					gatewayMap.put(gatewayid, tempMap);
				}
			}
			//计算用户对应的通道
			for (String wg_user_id : gateway_id_list) {
				List<DayReport> gatewayList = new ArrayList<DayReport>();
				for (DayReport tdayReport : dayrepores1) {
					int gatewayid = tdayReport.getInt("gateway_id");
					int wg_userid = Integer.parseInt(wg_user_id);
					if(gatewayid == wg_userid){
						gatewayList.add(tdayReport);
					}
				}
				userMap.put(wg_user_id, gatewayList);
			}
			

			List<Gateway> gatewayName = AdStatementService.service.findGateways();
			Map<Integer,String> wgnameMap = new HashMap<Integer,String>();
			for(Gateway attribute : gatewayName) {
				wgnameMap.put(attribute.getInt("gateway_id"), attribute.getStr("gateway_name"));
			}
			
			List<Person> personName = AdStatementService.service.findPersons();
			Map<Integer,String> unameMap = new HashMap<Integer,String>();
			for(Person attribute : personName) {
				unameMap.put(attribute.getInt("id"), attribute.getStr("webName"));
			}
			
			BigDecimal count_all_order_bigdecimal = new BigDecimal(0);
			BigDecimal count_all_amount_bigdecimal = new BigDecimal(0);
			BigDecimal count_user_settlement_amount_bigdecimal = new BigDecimal(0);
			BigDecimal count_user_profit_amount_bigdecimal = new BigDecimal(0);
			
			Iterator<Entry<String, Object>> entries = gatewayMap.entrySet().iterator();
			while (entries.hasNext()) {
				html += "<tr>";
			    Entry<String, Object> entry = entries.next();
			    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			    
			    List<DayReport> templist = (List<DayReport>) userMap.get(entry.getKey());
			    int listsize = templist.size()+1;
			    Map<String,Object> resmap = (Map<String, Object>) entry.getValue();
			    
			    count_all_order_bigdecimal = count_all_order_bigdecimal.add(new BigDecimal(resmap.get("deal_order").toString()));
			    count_all_amount_bigdecimal = count_all_amount_bigdecimal.add(new BigDecimal(resmap.get("deal_amount").toString()));
			    count_user_settlement_amount_bigdecimal = count_user_settlement_amount_bigdecimal.add(new BigDecimal(resmap.get("user_settlement_amount").toString()));
			    count_user_profit_amount_bigdecimal = count_user_profit_amount_bigdecimal.add(new BigDecimal(resmap.get("user_profit_amount").toString()));
			    
				html += "<td rowspan="+listsize+">"+entry.getKey()+"</td>";
				html += "<td rowspan="+listsize+">"+wgnameMap.get(Integer.parseInt(entry.getKey()))+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("user_settlement_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("user_profit_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("deal_amount")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("amount_rate")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("deal_order")+"</td>";
				html += "<td rowspan="+listsize+">"+resmap.get("order_rate")+"</td>";
			    
				for(DayReport attribute : templist) {
					  html += "<td>"+attribute.get("user_id")+"</td>";
					  html += "<td>"+unameMap.get(attribute.get("user_id"))+"</td>";
					  html += "<td>"+attribute.get("deal_amount")+"</td>";
					  html += "<td>"+attribute.get("user_settlement_amount")+"</td>";
					  html += "<td>"+attribute.get("user_profit_amount")+"</td>";
					  html += "<td>"+attribute.get("amounts_rate")+"</td>";
					  html += "<td>"+attribute.get("deal_order")+"</td>";
					  html += "<td>"+attribute.get("order_rate")+"</td>";
					  html += "<td>"+attribute.get("additional_order")+"</td>";
					  html += "<td>"+attribute.get("additional_amount")+"</td>";
					  html += "<td>"+attribute.get("additional_deal_amount")+"</td>";
					  html += "</tr><tr>";
				}
				
			    html += "</tr>";
			}
			html += "<tr>";
			html += "<td colspan=18 style=\"color: red;\">成功订单："+count_all_order_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;成功金额："+count_all_amount_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;结算金额："+count_user_settlement_amount_bigdecimal+"&nbsp;&nbsp;&nbsp;&nbsp;平台利润："+count_user_profit_amount_bigdecimal+"</td>";
			html += "</tr>";

		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("gateway_id", gateway_id);
		setAttr("statement_time", statement_time);
		setAttr("table_html",html);
		
		renderJsp("/WEB-INF/admin/jsp/statement/gateway.jsp");
	}
	
	public void gateway(){
		String gateway_id = "";
		String statement_time = "";
		if(StringUtil.isNotEmpty(getPara("gateway_id"))){
			gateway_id = getPara("gateway_id");
		}
		if(StringUtil.isNotEmpty(getPara("statement_time"))){
			statement_time = getPara("statement_time");
		}
		if(StringUtil.isEmpty(getPara("statement_time"))){
			SimpleDateFormat todate = new SimpleDateFormat("yyyy-MM-dd");
			statement_time = todate.format(new Date());
		}
		List<DayReport> dayReports = AdStatementService.service.gateway(gateway_id, statement_time);
		List<DayReport> sumGateway = AdStatementService.service.sumGateway(gateway_id, statement_time);
		List<DayReport> countGateway = AdStatementService.service.countGateway(gateway_id, statement_time);
		List<Gateway> gatewayName = AdStatementService.service.findGateways();
		List<Person> personName = AdStatementService.service.findPersons();
		for (DayReport dayReport : dayReports) {
			String dayId = dayReport.get("gateway_id").toString();
			for (DayReport sumgateway : sumGateway) {
				String sumId = sumgateway.get("gateway_id").toString();
				if(dayId.equals(sumId)){
					//计算金额成功率
					BigDecimal all_all_amount = sumgateway.getBigDecimal("all_all_amount");
					BigDecimal all_deal_amount = sumgateway.getBigDecimal("all_deal_amount");
					BigDecimal all_deal_amount_rate = all_deal_amount.divide(all_all_amount,4,RoundingMode.HALF_UP);
					//计算订单成功率
					String all_all_order = sumgateway.get("all_all_order").toString();
					String all_deal_order = sumgateway.get("all_deal_order").toString();
					int allOrder = Integer.parseInt(all_all_order);
					int dealOrder = Integer.parseInt(all_deal_order);
					DecimalFormat df=new DecimalFormat("0.000");
					String all_deal_order_rate = df.format((float)dealOrder/allOrder);
					dayReport.put("all_all_amount",all_all_amount);
					dayReport.put("all_deal_amount",all_deal_amount);
					dayReport.put("all_deal_amount_rate",all_deal_amount_rate);
					
					dayReport.put("all_all_order",all_all_order);
					dayReport.put("all_deal_order",all_deal_order);
					
					dayReport.put("all_deal_order_rate",all_deal_order_rate);
					sumGateway.remove(sumgateway);
					break;
				}
			}
			for (DayReport count : countGateway) {
				String countId = count.get("gateway_id").toString();
				if(dayId.equals(countId)){
					dayReport.put("countGateway", count.get("countGateway"));
					countGateway.remove(count);
					break;
				}
			}
			for (Gateway gateway : gatewayName) {
				String wayId = gateway.get("gateway_id").toString();
				if(dayId.equals(wayId)){
					dayReport.put("gateway_name", gateway.get("gateway_name"));
					gatewayName.remove(gateway);
					break;
				}
			}
		}
		for (DayReport dayReport : dayReports) {
			String userId = dayReport.get("user_id").toString();
			for (Person person : personName) {
				String personId = person.get("id").toString();
				if(userId.equals(personId)){
					dayReport.put("user_name",person.get("webName"));
					break;
				}
			}
		}
		setAttr("gateway_id", gateway_id);
		setAttr("statement_time", statement_time);
		setAttr("dayReports", dayReports);
		renderJsp("/WEB-INF/admin/jsp/statement/gateway.jsp");
	}
}
