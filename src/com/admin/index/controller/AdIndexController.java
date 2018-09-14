package com.admin.index.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.admin.index.service.AdIndexService;
import com.admin.statistics.service.AdStatisticsService;
import com.alibaba.fastjson.JSON;
import com.interceptor.AdLoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.vo.Employees;
import com.vo.Navigation;

@Before(AdLoginInterceptor.class)
public class AdIndexController extends Controller {
	public void index(){
		try{
//			String adminname = "";
			List<Navigation> navlist = null;
			//获取redis 缓存对象user
//			Cache jedis = Redis.use("netpayCache");
//			String time = jedis.get("c_a_i_i_time").toString();
//			long c_a_i_i_time = Long.parseLong(time);
//			if(c_a_i_i_time + 5000 > System.currentTimeMillis()){
			//	Navigation[] obj = (Navigation[]) jedis.get("c_a_i_i_navlist");
			//	System.out.println(obj.toString());
//				String re="["+jedis.get("c_a_i_i_navlist")+"]";
//				System.out.println(re);
//				
//				JSONObject obj = (JSONObject) JSONObject.toJSON(re);
//				System.out.println("a20:"+obj.get("a20"));
//				
//			    navlist = JSON.parseArray("["+re+"]",Navigation.class);
//			    System.out.println("navlist:"+navlist.toString());
//			
//				//System.out.println(navlist.toString());
//				
//			    adminname = jedis.get("c_a_i_i_employee");
//				System.out.println(adminname);
//			}else{
//				Employees employee = getSessionAttr("adminsession");
//				adminname = employee.getStr("name");
	//			Employees employee = getSessionAttr("adminsession");
	//			List<Navigation> navlist = AdIndexService.service.getNavi(employee.getInt("employeeid"));
	//			List<Navigation> navlists = AdIndexService.service.getNavis(employee.getInt("employeeid"));
	//			navlist.addAll(navlists);
	//			navlist=new ArrayList<Navigation>(new LinkedHashSet<>(navlist));
	//			setAttr("navlist", navlist);
	//			setAttr("employee", employee);
				Employees employee = getSessionAttr("adminsession");
				navlist = AdIndexService.service.getUserNavid(employee.getInt("powerid"));
//				jedis.set("c_a_i_i_navlist",navlist);
//				jedis.set("c_a_i_i_employee",adminname);
				//System.out.println(navlist.toString());
//			}
			setAttr("navlist", navlist);
			setAttr("employee", employee);
			renderJsp("/WEB-INF/admin/index.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void adminindex(){
		try{
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			SimpleDateFormat today_df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			
			String time1 = today_df.format(new Date()) + " 00:00:00";
			String time2 = today_df.format(new Date()) + " 23:59:59";
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60;
			String[] nettime = new String[(int)quot];
			BigDecimal[] p3_Amt = new BigDecimal[(int)quot];
			BigDecimal[] amount = new BigDecimal[(int)quot];
			BigDecimal[] deducted = new BigDecimal[(int)quot];
			Record net = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 60 * 60 * 1000);
				nettime[i-1] = "'"+startime+"'";
				net = AdStatisticsService.service.getStatistiNet2(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(net.get("p3_Amt")==null){
					p3_Amt[i-1] = new BigDecimal("0");
				}else{
					p3_Amt[i-1] = net.getBigDecimal("p3_Amt");
				}
				if(net.get("amount")==null){
					amount[i-1] = new BigDecimal("0");
				}else{
					amount[i-1] = net.getBigDecimal("amount");
				}
				if(net.get("deducted")==null){
					deducted[i-1] = new BigDecimal("0");
				}else{
					deducted[i-1] = net.getBigDecimal("deducted");
				}
			}
			setAttr("nettime", JSON.toJSON(nettime));
			setAttr("p3_Amt", JSON.toJSON(p3_Amt));
			setAttr("amount", JSON.toJSON(amount));
			setAttr("deducted", JSON.toJSON(deducted));
			renderJsp("/WEB-INF/admin/jsp/index.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void getRefundcount(){
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT COUNT(*) FROM refund r WHERE r.refund_state=2");
	    long l = Db.queryLong(sql.toString());
	    renderJson(l);
	}
}