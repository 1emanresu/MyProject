package com.admin.recharge.controller;

import java.util.List;

import com.admin.recharge.service.AdRechargeService;
import com.admin.statistics.service.AdStatisticsService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.Employees;
import com.vo.Participate;
import com.vo.Person;
import com.vo.Recharge;

@Before(AdLoginSeInterceptor.class)
public class AdRechargeController extends Controller {

	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String rechstarttime = "";
		String rechendtime = "";
		String rechparticipate = "";
		String rechstate = "";
		String rechid = "";
		String rechname = "";
		if(!getPara("rechname").equals("")){
			rechname = getPara("rechname");
		}
		if(!getPara("rechid").equals("")){
			rechid = getPara("rechid");
		}
		if(!getPara("rechstarttime").equals("")){
			rechstarttime = getPara("rechstarttime");
		}
		if(!getPara("rechendtime").equals("")){
			rechendtime = getPara("rechendtime");
		}
		if(StringUtil.isNotEmpty(getPara("rechparticipate")) && !getPara("rechparticipate").equals("0")){
			rechparticipate = getPara("rechparticipate");
		}
		if(StringUtil.isNotEmpty(getPara("rechstate")) && !getPara("rechstate").equals("0")){
			rechstate = getPara("rechstate");
		}
		Employees emp = getSessionAttr("adminsession");
		setAttr("rechstarttime", rechstarttime);
		setAttr("rechendtime", rechendtime);
		setAttr("rechparticipate", rechparticipate);
		setAttr("rechstate", rechstate);
		setAttr("rechid", rechid);
		setAttr("rechname", rechname);
		Page<Record> orderpage = AdRechargeService.service.getRechPage(pageNum, numPerPage, rechstarttime, rechendtime, rechparticipate, rechstate, rechid, rechname,emp.getInt("employeeid"));
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		List<Record> rechargestatis = AdStatisticsService.service.getStatistiRecharge(rechstarttime, rechendtime,emp.getInt("employeeid"));
		setAttr("recharge", rechargestatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/recharge.jsp");
	}
	
	public void showRech(){
		String rechid = getPara("rechid");
		Recharge recharge = Recharge.dao.findById(rechid);
		Person per = Person.dao.findById(recharge.getInt("p1_MerId"));
		List<Participate> particiList = ExchangeService.exchangeService.getPartici();
		String pdname = "";
		for (Participate partic : particiList) {
			if (partic.getStr("participate").equals(recharge.getStr("pd_FrpId"))) {
				pdname = partic.get("payable");
				break;
			}
		}
		setAttr("pdname", pdname);
		setAttr("rechname", per.get("name"));
		setAttr("recharge", recharge);
		renderJsp("/WEB-INF/admin/jsp/recharge/showRecharge.jsp");
	}
}
