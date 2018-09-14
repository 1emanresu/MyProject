package com.admin.card.controller;

import java.util.List;

import com.admin.card.service.AdCardService;
import com.admin.statistics.service.AdStatisticsService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.pay.cardyee.client.service.CardyeeService;
import com.tool.StringUtil;
import com.vo.CardOrder;
import com.vo.Employees;
import com.vo.Person;
import com.vo.SingleCard;

@Before(AdLoginSeInterceptor.class)
public class AdCardController extends Controller {
	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String cardstarttime = "";
		String cardendtime = "";
		String cardparticipate = "";
		String cardstate = "";
		String cardorderid = "";
		String cardname = "";
		String cardlock = "";
		if(!getPara("cardstarttime").equals("")){
			cardstarttime = getPara("cardstarttime");
		}
		if(!getPara("cardendtime").equals("")){
			cardendtime = getPara("cardendtime");
		}
		if(StringUtil.isNotEmpty(getPara("cardparticipate")) && !getPara("cardparticipate").equals("0")){
			cardparticipate = getPara("cardparticipate");
		}
		if(StringUtil.isNotEmpty(getPara("cardstate")) && !getPara("cardstate").equals("0")){
			cardstate = getPara("cardstate");
		}
		if(!getPara("cardorderid").equals("")){
			cardorderid = getPara("cardorderid");
		}
		if(!getPara("cardname").equals("")){
			cardname = getPara("cardname");
		}
		if(StringUtil.isNotEmpty(getPara("cardlock")) && !getPara("cardlock").equals("0")){
			cardlock = getPara("cardlock");
		}
		Employees emp = getSessionAttr("adminsession");
		setAttr("cardstarttime", cardstarttime);
		setAttr("cardendtime", cardendtime);
		setAttr("cardparticipate", cardparticipate);
		setAttr("cardstate", cardstate);
		setAttr("cardorderid", cardorderid);
		setAttr("cardname", cardname);
		setAttr("cardlock", cardlock);
		Page<CardOrder> orderpage = AdCardService.service.getCardPage(pageNum, numPerPage, cardstarttime, cardendtime, cardparticipate, cardstate, cardorderid, cardname, cardlock, emp.getInt("employeeid"));
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		List<Record> carstatis = AdStatisticsService.service.getStatistiCard(cardstarttime, cardendtime,emp.getInt("employeeid"));
		setAttr("carstatis", carstatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/card.jsp");
	}
	
	public void showCard(){
		String cardid = getPara("cardid");
		CardOrder cardOrder = CardOrder.dao.findById(cardid);
		Person per = Person.dao.findById(cardOrder.getInt("p1_MerId"));
		String payable = Db.queryStr("SELECT p.payable from participate p where p.participate = '"+cardOrder.getStr("pd_FrpId")+"'");
		setAttr("payable", payable);
		setAttr("cardname", per.get("name"));
		setAttr("cardorder", cardOrder);
		renderJsp("/WEB-INF/admin/jsp/card/showCard.jsp");
	}
	
	public void showSinglist(){
		String cardid = getPara("cardid");
		List<Record> singlist = AdCardService.service.getSinglist(cardid);
		setAttr("singlist", singlist);
		renderJsp("/WEB-INF/admin/jsp/card/showSing.jsp");
	}
	
	public void showSing(){
		String singid = getPara("singid");
		SingleCard singlecard = SingleCard.dao.findById(singid);
		String payable = Db.queryStr("SELECT c.payable from cardcode c where c.participate = '"+singlecard.getStr("cardcode")+"'");
		setAttr("payable", payable);
		setAttr("singlecard", singlecard);
		renderJsp("/WEB-INF/admin/jsp/card/singShow.jsp");
	}
	
	public void adcardReturn(){
		String orderid = getPara("orderid");
		try {
			CardyeeService.service.asynchronous(orderid);
			renderText("通知成功！");
		} catch (Exception e) {
			renderText("通知失败！");
			e.printStackTrace();
		}
	}
	
	public void cardDeduction() {
		try {
			String cardorderid = getPara("cardorderid");
			boolean boo = AdCardService.service.carddeduction(cardorderid);
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav5\", \"forwardUrl\":\"\", \"rel\":\"nav5\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}
	
	public void cardDeductions() {
		try {
			String[] cardlist = getParaValues("cardlist");
			for (String cardid : cardlist) {
				AdCardService.service.carddeduction(cardid);
			}
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav5\", \"forwardUrl\":\"\", \"rel\":\"nav5\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void cardRecovery() {
		try {
			String cardorderid = getPara("cardorderid");
			boolean boo = AdCardService.service.cardRecovery(cardorderid);
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav5\", \"forwardUrl\":\"\", \"rel\":\"nav5\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}
	
}
