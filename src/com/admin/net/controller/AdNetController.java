package com.admin.net.controller;

import java.util.List;

import com.admin.net.service.AdNetService;
import com.admin.statistics.service.AdStatisticsService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.Asynchronous;
import com.vo.Employees;
import com.vo.Order;
import com.vo.Participate;
import com.vo.Person;

@Before(AdLoginSeInterceptor.class)
public class AdNetController extends Controller {
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
		System.out.println("pageNum=:"+pageNum);

		setAttr("netstarttime", netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("netorderid", netorderid);
		setAttr("netname", netname);
		setAttr("netlock", netlock);			
		renderJsp("/WEB-INF/admin/jsp/net.jsp");
	}
	
	
	public void orderList() {
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
		if (StringUtil.isNotEmpty(getPara("netparticipate"))&&!getPara("netparticipate").equals("0")) {
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


		Page<Order> page=AdNetService.service.getNetPageold(pageNum, numPerPage, netstarttime, netendtime, netparticipate, netstate, netorderid, netname, netlock, 1, p2_Order,minmoney, maxmoney);

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
		List<Record> netstatis = AdStatisticsService.service.getStatistiNetold(netstarttime, netendtime, netparticipate, netstate, netorderid,netname, netlock, p2_Order,minmoney,maxmoney);
		setAttr("netstatis", netstatis.get(0));
		
		renderJsp("/WEB-INF/admin/jsp/orderList.jsp");
	}
	
	
	public void MyQueryList() {
		try {
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
			String minmoney = "";
			String maxmoney = "";
			String pageType = "";
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
				netorderid = getPara("netorderid").toString();
			}
			if (!getPara("p2_Order").equals("")) {
				p2_Order = getPara("p2_Order");
			}
			if (!getPara("netname").equals("")) {
				netname = getPara("netname");
			}
			if (StringUtil.isNotEmpty(getPara("netlock")) && !getPara("netlock").equals("0")) {
				netlock = getPara("netlock");
			}
			if(StringUtil.isNotEmpty(getPara("minmoney"))){
				minmoney = getPara("minmoney");
			}
			if(StringUtil.isNotEmpty(getPara("maxmoney"))){
				maxmoney = getPara("maxmoney");
			}
			if(StringUtil.isNotEmpty(getPara("pageType"))){
				pageType = getPara("pageType");
			}
			Employees emp = getSessionAttr("adminsession");
			setAttr("netstarttime", netstarttime);
			setAttr("netendtime", netendtime);
			setAttr("netparticipate", netparticipate);
			setAttr("netstate", netstate);
			setAttr("netorderid", netorderid);
			setAttr("netname", netname);
			setAttr("netlock", netlock);
			setAttr("p2_Order", p2_Order);
			setAttr("minmoney", minmoney);
			setAttr("maxmoney", maxmoney);
			Page<Order> orderpage = AdNetService.service.getNetPage(pageNum,
					numPerPage, netstarttime, netendtime, netparticipate, netstate,
					netorderid, netname, netlock, emp.getInt("employeeid"),
					p2_Order,minmoney,maxmoney);
			setAttr("pageNum", orderpage.getPageNumber());
			setAttr("numPerPage", orderpage.getPageSize());
			setAttr("totalCount", orderpage.getTotalRow());
			setAttr("pageNumShown", orderpage.getTotalPage());
			setAttr("orderlist", orderpage.getList());
			setAttr("partici", ExchangeService.exchangeService.getPartici());
			List<Record> netstatis = AdStatisticsService.service.getStatistiNet(netstarttime, netendtime, netparticipate, netstate, netorderid,netname, netlock, p2_Order,minmoney,maxmoney);
			setAttr("netstatis", netstatis.get(0));
			if(StringUtil.isEmpty(pageType)){
				renderJsp("/WEB-INF/admin/jsp/net.jsp");
			}else if(StringUtil.isNotEmpty(pageType)){
				if(pageType.equals("success")){
					renderJsp("/WEB-INF/admin/jsp/net/netSuccess.jsp");
				}else if(pageType.equals("fail")){
					renderJsp("/WEB-INF/admin/jsp/net/netFail.jsp");
				}else if(pageType.equalsIgnoreCase("ALI")){
					renderJsp("/WEB-INF/admin/jsp/net/netAli.jsp");
				}else if(pageType.equalsIgnoreCase("WX")){
					renderJsp("/WEB-INF/admin/jsp/net/netWx.jsp");
				}
				setAttr("pageType",pageType);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showNet() {
		String netorderid = getPara("netorderid");
		Order order = Order.dao.findById(netorderid);
		Person per = Person.dao.findById(order.getInt("p1_MerId"));
		List<Participate> particiList = ExchangeService.exchangeService
				.getPartici();
		String pdname = "";
		for (Participate partic : particiList) {
			if (partic.getStr("participate").equals(order.getStr("pd_FrpId"))) {
				pdname = partic.get("payable");
				break;
			}
		}
		Asynchronous asynchronous = Asynchronous.dao.findById(netorderid);
		setAttr("pdname", pdname);
		setAttr("netname", per.get("name"));
		setAttr("netorder", order);
		setAttr("asynchronous", asynchronous);
		renderJsp("/WEB-INF/admin/jsp/net/showNet.jsp");
	}

	public void netDeduction() {
		try {
			String netorderid = getPara("netorderid");
			boolean boo = AdNetService.service.netdeduction(netorderid);
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav4\", \"forwardUrl\":\"\", \"rel\":\"nav4\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void netDeductions() {
		try {
			String[] netlist = getParaValues("netlist");
			for (String netid : netlist) {
				AdNetService.service.netdeduction(netid);
			}
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav4\", \"forwardUrl\":\"\", \"rel\":\"nav4\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void netRecovery() {
		try {
			String netorderid = getPara("netorderid");
			boolean boo = AdNetService.service.netRecovery(netorderid);
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav4\", \"forwardUrl\":\"\", \"rel\":\"nav4\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}
}
