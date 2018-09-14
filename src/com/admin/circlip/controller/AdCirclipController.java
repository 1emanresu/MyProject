package com.admin.circlip.controller;

import java.util.List;

import com.admin.circlip.service.AdCirclipService;
import com.admin.statistics.service.AdStatisticsService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.CardCode;
import com.vo.Circlip;
import com.vo.Employees;
import com.vo.Person;

@Before(AdLoginSeInterceptor.class)
public class AdCirclipController extends Controller {

	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String circstarttime = "";
		String circendtime = "";
		String circparticipate = "";
		String circstate = "";
		if(!getPara("circstarttime").equals("")){
			circstarttime = getPara("circstarttime");
		}
		if(!getPara("circendtime").equals("")){
			circendtime = getPara("circendtime");
		}
		if(StringUtil.isNotEmpty(getPara("circparticipate")) && !getPara("circparticipate").equals("0")){
			circparticipate = getPara("circparticipate");
		}
		if(StringUtil.isNotEmpty(getPara("circstate")) && !getPara("circstate").equals("0")){
			circstate = getPara("circstate");
		}
		Employees emp = getSessionAttr("adminsession");
		setAttr("circstarttime", circstarttime);
		setAttr("circendtime", circendtime);
		setAttr("circparticipate", circparticipate);
		setAttr("circstate", circstate);
		Page<Record> orderpage = AdCirclipService.service.getCircPage(pageNum, numPerPage, circstarttime, circendtime, circparticipate, circstate, emp.getInt("employeeid"));
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		List<Record> circlipstatis = AdStatisticsService.service.getStatistiCirclip(circstarttime, circendtime,emp.getInt("employeeid"));
		setAttr("circlipstatis", circlipstatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/circlip.jsp");
	}
	
	public void showCirclip(){
		String circlipid = getPara("circlipid");
		Circlip circlip = Circlip.dao.findById(circlipid);
		setAttr("circlip", circlip);
		CardCode cardcode = CardCode.dao.findById(circlip.getInt("cardid"));
		setAttr("payable", cardcode.get("payable"));
		Person per = Person.dao.findById(circlip.getInt("id"));
		setAttr("ciname", per.get("name"));
		renderJsp("/WEB-INF/admin/jsp/circlip/showCirclip.jsp");
	}
}
