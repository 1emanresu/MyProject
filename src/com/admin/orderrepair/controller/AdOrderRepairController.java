package com.admin.orderrepair.controller;

import com.admin.net.service.AdNetService;
import com.admin.orderrepair.service.AdOrderRepairService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Order;

@Before(AdLoginSeInterceptor.class)
public class AdOrderRepairController extends Controller {
	
	public void index() {
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


		Page<Order> page;

		if(netstarttime.equals("")&&netendtime.equals("")&&netorderid.equals("")&&netname.equals("")&&p2_Order.equals("")){
			setAttr("orderlist",null);
			setAttr("totalCount", 0);
			
		}else{
			page=AdNetService.service.getNetPage(pageNum, numPerPage, netstarttime, netendtime, netparticipate, netstate, netorderid, netname, netlock, 1, p2_Order,minmoney, maxmoney);
			setAttr("pageNum", pageNum);
			setAttr("numPerPage",numPerPage);
			
			setAttr("orderlist", page.getList());
			setAttr("totalCount", page.getTotalRow());
		}
		
		
		
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
	
	
	
	public void repairList() {
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

		setAttr("pageNum", pageNum);
		setAttr("numPerPage",numPerPage);
		
		setAttr("orderlist", page.getList());
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
		
		renderJsp("/WEB-INF/admin/jsp/repair/repairList.jsp");
	}
	
	

	public void repairs(){
		try {
			String[] netlist = getParaValues("netlist");
			for (String netid : netlist) {
				AdNetService.service.repair(netid);
			}
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav4\", \"forwardUrl\":\"\", \"rel\":\"nav4\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
		
	}
	
}
