
package com.admin.dopersons.controller;

import java.util.ArrayList;
import java.util.List;

import com.admin.dopersons.service.DopresonsService;
import com.admin.orderrepair.service.AdOrderRepairService;
import com.admin.statistics.service.AdStatisticsService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.Employees;
import com.vo.Order;

@Before(AdLoginSeInterceptor.class)
public class DopresonsController extends Controller {

	public void index() {
		int pageNum = getParaToInt("pageNum");
		String netstarttime = "";
		String netendtime = "";
		String netparticipate = "";
		String netstate = "";
		String netorderid = "";
		String netname = "";
		String netlock = "";
		if (!getPara("netstarttime").equals("")) {
			netstarttime = getPara("netstarttime");
		}
		if (!getPara("netendtime").equals("")) {
			netendtime = getPara("netendtime");
		}
		if (StringUtil.isNotEmpty(getPara("netparticipate")) && !getPara("netparticipate").equals("0")) {
			netparticipate = getPara("netparticipate");
		}
		if (StringUtil.isNotEmpty(getPara("netstate")) && !getPara("netstate").equals("0")) {
			netstate = getPara("netstate");
		}
		if (!getPara("netorderid").equals("")) {
			netorderid = getPara("netorderid");
		}
		if (!getPara("netname").equals("")) {
			netname = getPara("netname");
		}
		if (StringUtil.isNotEmpty(getPara("netlock")) && !getPara("netlock").equals("0")) {
			netlock = getPara("netlock");
		}
		System.out.println("pageNum=:" + pageNum);
		setAttr("netstarttime", netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("netorderid", netorderid);
		setAttr("netname", netname);
		setAttr("netlock", netlock);
		renderJsp("/WEB-INF/admin/jsp/dopersons.jsp");
	}
	
	
	public void orderRepair() {
		int pageNum = 1;
		int numPerPage=20;
		
		
		String netstarttime = "";
		String netendtime = "";
		String netparticipate = "";
		String netstate = "";
		String netorderid = "";
		String netname = "";
		String netlock = "";
		String p2_Order = "";
		String minmoney = "";
		String maxmoney = "";
		
		if(StringUtil.isNotEmpty(getPara("numPerPage"))){
			numPerPage=getParaToInt("numPerPage");
		}
		if(StringUtil.isNotEmpty(getPara("pageNum"))){
			pageNum=getParaToInt("pageNum");
		}
		
		
		if (StringUtil.isNotEmpty(getPara("netstarttime"))) {
			netstarttime = getPara("netstarttime").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netendtime"))) {
			netendtime = getPara("netendtime").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netparticipate")) && !getPara("netparticipate").equals("0")) {
			netparticipate = getPara("netparticipate");
		}
		if (StringUtil.isNotEmpty(getPara("netstate"))&&!getPara("netstate").equals("0")) {
			netstate = getPara("netstate").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netorderid"))) {
			netorderid = getPara("netorderid").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netname"))) {
			netname = getPara("netname").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netlock"))&&!getPara("netlock").equals("0")) {
			netlock = getPara("netlock").trim();
		}
		if (StringUtil.isNotEmpty(getPara("p2_Order"))) {
			p2_Order = getPara("p2_Order").trim();
		}
		if (StringUtil.isNotEmpty(getPara("minmoney"))) {
			minmoney = getPara("minmoney").trim();
		}
		if (StringUtil.isNotEmpty(getPara("maxmoney"))) {
			maxmoney = getPara("maxmoney").trim();
		}


		Page<Record> page=AdOrderRepairService.service.getOrderRepairPage(pageNum, numPerPage, netstarttime, netendtime, netparticipate, netstate, netorderid, netname, netlock, 1, p2_Order,minmoney, maxmoney);

		setAttr("orderlist", page.getList());
		setAttr("pageNum", pageNum);
		setAttr("numPerPage",numPerPage);
		setAttr("totalCount", page.getTotalRow());
		
		setAttr("netstarttime",netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("netorderid", netorderid);
		setAttr("netname", netname);
		setAttr("p2_Order", p2_Order);
		setAttr("minmoney", minmoney);
		setAttr("maxmoney", maxmoney);
		
		renderJsp("/WEB-INF/admin/jsp/orderRepair.jsp");
	}

	public void MyQueryList() {
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String netstarttime = "";
		String netendtime = "";
		String netparticipate = "";
		String netstate = "";
		String netorderid = "";
		String netname = "";
		String netlock = "";
		String p2_Order = "";
		if (!getPara("netstarttime").equals("")) {
			netstarttime = getPara("netstarttime");
		}
		if (!getPara("netendtime").equals("")) {
			netendtime = getPara("netendtime");
		}
		if (!getPara("netorderid").equals("")) {
			netorderid = getPara("netorderid");		
		}
		System.out.println("netstarttime=:" + netstarttime + ";订单号：" + p2_Order);
		Employees emp = getSessionAttr("adminsession");
		setAttr("netstarttime", netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("netorderid", netorderid);
		System.out.println(netorderid);
		setAttr("netname", netname); 
		setAttr("netlock", netlock);
		setAttr("p2_Order", p2_Order);
		Page<Order> orderpage = DopresonsService.service.getNetPage(pageNum, numPerPage, netstarttime, netendtime,
				netparticipate, netstate, netorderid, netname, netlock, emp.getInt("employeeid"), p2_Order);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		List<Record> netstatis = AdStatisticsService.service.getStatistiNet(netstarttime, netendtime, netparticipate,
				netstate, netorderid, netname, netlock, p2_Order,null,null);
		setAttr("netstatis", netstatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/dopersons.jsp");
	}

	public void retunet() {
		String[] orderids = getParaValues("netlist");
		for (String orderid : orderids) {
			try {
				Order order = (Order) Order.dao.findById(orderid);
				if (order.getInt("r1_Code").intValue() == 1) {
					System.out.println("发送通知的订单号是==：" + orderid);
					boolean boo = com.pay.Resend.asynchronous(orderid);
					if (boo) {
						renderJson(
								"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav31\", \"forwardUrl\":\"\", \"rel\":\"nav31\"}");
					} else {
						renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
					}
				} 
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		}
	}
	
	public void retunetAll() {  
		try{
			List<Order> list=DopresonsService.service.getNetAll(getPara("netstarttime"),getPara("netendtime"),getPara("netorderid"));
			System.out.println(list);
		    List<String> orderId= new ArrayList<String>();
		    for (Order order : list) {				
				orderId.add(order.get("orderid").toString());
			}
			for(String orderid : orderId) {				
				try {				
					Order order = (Order) Order.dao.findById(orderid);
					System.out.println(orderid);
					if (order.getInt("r1_Code").intValue() == 1) {
						System.out.println("发送通知的订单号是==：" + orderid);
						boolean boo = com.pay.Resend.asynchronous(orderid);
						if (boo) {
							renderJson(
									"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav32\", \"forwardUrl\":\"\", \"rel\":\"nav32\"}");
						} else {
							renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
						}
					} 
				} catch (Exception e) {
					renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
