package com.jsp.exchange.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.interceptor.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.jsp.user.service.UserService;
import com.pay.cardyee.client.service.CardyeeService;
import com.vo.CardOrder;
import com.vo.Order;
import com.vo.Person;
import com.vo.Rate;

@Before(LoginInterceptor.class)
public class ExchangeController extends Controller {
	public void index(){
		Person person = getSessionAttr("session");
		setAttr("participate", ExchangeService.exchangeService.getParticipate());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		setAttr("ordertotal", ExchangeService.exchangeService.getOrdertotal(person.getInt("id"), new Date()));
		setAttr("cardordertotal", ExchangeService.exchangeService.getCardOrdertotal(person.getInt("id"), new Date()));
		renderJsp("/WEB-INF/jsp/exchange1.jsp");
	}
	
	public void getNet(){
		int page = getParaToInt("page");
		String netstarttime = "";
		String netendtime = "";
		String netparticipate = "";
		String netstate = "";
		String netid = "";
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}
		if(!getPara("netparticipate").equals("0")){
			netparticipate = getPara("netparticipate");
		}
		if(!getPara("netstate").equals("0")){
			netstate = getPara("netstate");
		}
		if(!getPara("netid").equals("")){
			netid = getPara("netid");
		}
		Person per = getSessionAttr("session");
		Page<Order> orderPage = ExchangeService.exchangeService.getOrder(per.getInt("id"), page, netstarttime, netendtime, netparticipate, netstate, netid);
		List<Order> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		renderJson(json);
	}
	
	public void getQueryCount(){
		int page = getParaToInt("page");
		String netstarttime = "";
		String netendtime = "";
		String netstate = "";
		String netid = "";
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}
		if(!getPara("netstate").equals("0")){
			netstate = getPara("netstate");
		}
		if(!getPara("netid").equals("")){
			netid = getPara("netid");
		}
		Person per = getSessionAttr("session");
		Page<Order> orderPage = ExchangeService.exchangeService.getOrder2(per.getInt("id"),page,netstarttime,netendtime,netstate,netid);
		List<Order> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		System.out.println("json==??==="+json);
		renderJson(json);
	}
	
	public void getQueryCount20171030(){
		
		Person person=getSessionAttr("session");
		
		int page = getParaToInt("page");
		String netstarttime = "";
		String netendtime = "";
		String netstate = "";
		String netid = "";
		int id = getParaToInt("id");
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}
		if(!getPara("netstate").equals("0")){
			netstate = getPara("netstate");
		}
		if(!getPara("netid").equals("")){
			netid = getPara("netid");
		}
		if(!getPara("netid").equals("")){
			netid = getPara("netid");
		}
	
		Page<Order> orderPage = ExchangeService.exchangeService.getOrder2(id,page,netstarttime,netendtime,netstate,netid);
		List<Order> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		System.out.println("json==??==="+json);
		
		
		
		Map map = new HashMap<String,Object>();
		map.put("list",list);
		
		//获取相关的收益率列表
		Rate rate=UserService.userService.getIncome(id, person);
		map.put("rate", rate);
		//查询获取总的收益
		Record record = ExchangeService.exchangeService.getQueryOrdertotal20171031(id, netstarttime, netendtime,netstate,netid,rate);
		
		map.put("record", record);
		renderJson(map);	
		
	}
	
	public void getMyCount(){
		String netstarttime = "";
		String netendtime = "";
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}		
		Person per = getSessionAttr("session");
		Record list = ExchangeService.exchangeService.getUserOrdertotal(per.getInt("id"), netstarttime, netendtime);
		
		String json =list.toJson();
		renderJson(json);
	}
	
	public void getUserCount(){
		String netstarttime = "";
		String netendtime = "";
		String netstate = "";
		String netid = "";
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}
		if(!getPara("netid").equals("")){
			netid = getPara("netid");
		}
		if(!getPara("netstate").equals("0")){
			netstate = getPara("netstate");
		}
		Person per = getSessionAttr("session");
		Record list = ExchangeService.exchangeService.getQueryOrdertotal(per.getInt("id"), netstarttime, netendtime,netstate,netid);
		String json =list.toJson();
		System.out.println("###############=="+json);
		renderJson(json);
	}
	
	
	public void getRefundCount(){
		String refundstarttime = "";
		String refundendtime = "";
		String refundstate = "";
		String refundid = "";
		String account_name = "";
		if (!getPara("refundstarttime").equals("")) {
			refundstarttime = getPara("refundstarttime");
		}
		if (!getPara("refundendtime").equals("")) {
			refundendtime = getPara("refundendtime");
		}
		if (!getPara("refundstate").equals("0")) {
			refundstate = getPara("refundstate");
		}
		if (!getPara("refundid").equals("")) {
			refundid = getPara("refundid");
		}
		if (!getPara("account_name").equals("")) {
			account_name = getPara("account_name");
		}
		Person per = getSessionAttr("session");
		Record list = ExchangeService.exchangeService.getQueryRefundtotal(per.getInt("id"), refundstarttime, refundendtime,refundstate,refundid,account_name);
		String json =list.toJson();
		System.out.println("###############=="+json);
		renderJson(json);
	}
	
	public void getCard(){
		int page = getParaToInt("cardpages");
		String cardstarttime = "";
		String cardendtime = "";
		String cardparticipate = "";
		String cardstate = "";
		String cardid = "";
		if(!getPara("cardstarttime").equals("")){
			cardstarttime = getPara("cardstarttime");
		}
		if(!getPara("cardendtime").equals("")){
			cardendtime = getPara("cardendtime");
		}
		if(!getPara("cardparticipate").equals("0")){
			cardparticipate = getPara("cardparticipate");
		}
		if(!getPara("cardstate").equals("0")){
			cardstate = getPara("cardstate");
		}
		if(!getPara("cardid").equals("")){
			cardid = getPara("cardid");
		}
		Person per = getSessionAttr("session");
		Page<CardOrder> CardPage = ExchangeService.exchangeService.getCardOrder(per.getInt("id"), page, cardstarttime, cardendtime, cardparticipate, cardstate, cardid);
		List<CardOrder> orderList = CardPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(CardPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		renderJson(json);
	}
	
	public void getSing(){
		String singid = getPara("singid");
		List<Record> singlist = ExchangeService.exchangeService.getSingOrder(singid);
		String json = JsonKit.toJson(singlist, 3);
		renderJson(json);
	}
	
	public void cardReturn(){
		String orderid = getPara("orderid");
		try {
			CardyeeService.service.asynchronous(orderid);
			renderText("通知成功！");
		} catch (Exception e) {
			renderText("通知失败！");
			e.printStackTrace();
		}
	}
}