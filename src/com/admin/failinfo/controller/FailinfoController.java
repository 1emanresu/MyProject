package com.admin.failinfo.controller;

import java.util.List;

import com.admin.failinfo.service.FailinfoService;
import com.admin.statistics.service.AdStatisticsService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.Employees;
import com.vo.Order;

public class FailinfoController extends Controller {
	public void index() {
		
		try {
			int pageNum=0;
			if(getParaToInt("pageNum")!=null){
				 pageNum = getParaToInt("pageNum");
			}
			
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

			System.out.println("pageNum=:" + pageNum);

			setAttr("netstarttime", netstarttime);
			setAttr("netendtime", netendtime);
			setAttr("netparticipate", netparticipate);
			setAttr("netstate", netstate);
			setAttr("netorderid", netorderid);
			setAttr("netname", netname);
			setAttr("netlock", netlock);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/failinfo.jsp");
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
		if (StringUtil.isNotEmpty(getPara("netparticipate")) && !getPara("netparticipate").equals("0")) {
			netparticipate = getPara("netparticipate");
		}
		if (StringUtil.isNotEmpty(getPara("netstate")) && !getPara("netstate").equals("0")) {
			netstate = getPara("netstate");
		}
		if (!getPara("netorderid").equals("")) {
			netorderid = getPara("netorderid");
		}
		if (!getPara("p2_Order").equals("")) {
			p2_Order = getPara("p2_Order");
		}
		if (!getPara("netname").equals("")) {
			netname = getPara("netname");
		}

		System.out
				.println("netstarttime=:" + netstarttime + ";订单号：" + p2_Order);

		Employees emp = getSessionAttr("adminsession");
		setAttr("netstarttime", netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("netorderid", netorderid);
		setAttr("netname", netname);
		setAttr("netlock", netlock);
		setAttr("p2_Order", p2_Order);
		Page<Order> orderpage = FailinfoService.service.getNetPage(pageNum,
				numPerPage, netstarttime, netendtime, netparticipate, netstate,
				netorderid, netname, netlock, emp.getInt("employeeid"),
				p2_Order);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		List<Record> netstatis = AdStatisticsService.service.getStatistiNet(
				netstarttime, netendtime, netparticipate, netstate, netorderid,
				netname, netlock, p2_Order,null,null);
		setAttr("netstatis", netstatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/failinfo.jsp");
	}

	
	public void doretunet() {
		
		try {
			String[] orderids = getParaValues("netlist");
			for (String orderid : orderids) {
				Order order = (Order) Order.dao.findById(orderid);
				if (order.getInt("r1_Code").intValue() == 2) {
					System.out.println("发送通知的订单号是==：" + orderid);
					boolean boo = FailinfoService.service.ordershandle(orderid);
					if (boo) {
						boo = com.pay.Resend.asynchronous(orderid);
					}
					if (boo) {
						renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav38\", \"forwardUrl\":\"\", \"rel\":\"nav38\"}");
					} else {
						renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}
}
