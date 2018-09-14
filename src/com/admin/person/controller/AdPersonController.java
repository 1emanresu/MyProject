package com.admin.person.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.admin.gateway.service.AdGatewayService;
import com.admin.person.service.AdPersonService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.jsp.register.service.RegisterService;
import com.jsp.settlement.service.SettlementService;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.tool.StringUtil;
import com.vo.Addedamount;
import com.vo.Balance;
import com.vo.Caifutong;
import com.vo.CardOrder;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Imgfile;
import com.vo.Order;
import com.vo.Payment;
import com.vo.Person;
import com.vo.PersonGateway;
import com.vo.Rate;
import com.vo.SettlementAccount;

@Before(AdLoginSeInterceptor.class)
public class AdPersonController extends Controller {
	public void index() {
		
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String starttime = "";
		String endtime = "";
		String id = "";
		String iflock = "";
		String iflogin = "";
		String ifnet = "";

		if (!getPara("starttime").equals("")) {
			starttime = getPara("starttime");
		}
		if (!getPara("endtime").equals("")) {
			endtime = getPara("endtime");
		}
		if (!getPara("id").equals("")) {
			id = getPara("id");
		}
		if (StringUtil.isNotEmpty(getPara("iflock")) && !getPara("iflock").equals("0")) {
			iflock = getPara("iflock");
		}
		if (StringUtil.isNotEmpty(getPara("iflogin")) && !getPara("iflogin").equals("0")) {
			iflogin = getPara("iflogin");
		}
		if (StringUtil.isNotEmpty(getPara("ifnet")) && !getPara("ifnet").equals("0")) {
			ifnet = getPara("ifnet");
		}

		Employees emp = getSessionAttr("adminsession");
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("id", id);
		setAttr("iflock", iflock);
		setAttr("iflogin", iflogin);
		setAttr("ifnet", ifnet);
		Page<Person> orderpage = AdPersonService.service.getPerson(pageNum, numPerPage, starttime, endtime, id, iflock,
				iflogin, ifnet, emp.getInt("employeeid"));
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		List<Person> list = orderpage.getList();
		List<Person> list2 = AdPersonService.service.getPersonlist(list);
		setAttr("perlist", list2);
		renderJsp("/WEB-INF/admin/jsp/person.jsp");
	}
	
	
	public void check() {
		
		int pageNum = 1;
		int numPerPage = 20;
		
		if(StringUtil.isNotEmpty(getPara("pageNum"))){
			pageNum = getParaToInt("pageNum");
		}
		if(StringUtil.isNotEmpty(getPara("numPerPage"))){
			numPerPage = getParaToInt("numPerPage");
		}
	
		String id = "";
	

		if(StringUtil.isNotEmpty(getPara("id"))){
			id = getPara("id");
		}
		
		Employees emp = getSessionAttr("adminsession");
		setAttr("id", id);
		

		Page<Person> orderpage = AdPersonService.service.getPerson(pageNum, numPerPage, "", "", id, "0",
				"", "", emp.getInt("employeeid"));
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		List<Person> list = orderpage.getList();
		List<Person> list2 = AdPersonService.service.getPersonlist(list);
		setAttr("perlist", list2);
		renderJsp("/WEB-INF/admin/jsp/person/check.jsp");
	}
	
	
	public void getPersonList() {
		
		int pageNum = 20;
		int numPerPage =1;
		int superior=0;
		
		String starttime = "";
		String endtime = "";
		String id = "";
		String iflock = "";
		String iflogin = "";
		String ifnet = "";
		
		if(StringUtil.isNotEmpty("pageNum")){
			pageNum=getParaToInt("pageNum");
		}
		
		if(StringUtil.isNotEmpty("numPerPage")){
			numPerPage=getParaToInt("numPerPage");
		}
		

		if(StringUtil.isNotEmpty("superior")){
			superior=getParaToInt("superior");
		}
		

		if (!getPara("starttime").equals("")) {
			starttime = getPara("starttime");
		}
		if (!getPara("endtime").equals("")) {
			endtime = getPara("endtime");
		}
		if (!getPara("id").equals("")) {
			id = getPara("id");
		}
		if (StringUtil.isNotEmpty(getPara("iflock")) && !getPara("iflock").equals("0")) {
			iflock = getPara("iflock");
		}
		if (StringUtil.isNotEmpty(getPara("iflogin")) && !getPara("iflogin").equals("0")) {
			iflogin = getPara("iflogin");
		}
		if (StringUtil.isNotEmpty(getPara("ifnet")) && !getPara("ifnet").equals("0")) {
			ifnet = getPara("ifnet");
		}

		Employees emp = getSessionAttr("adminsession");
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("id", id);
		setAttr("iflock", iflock);
		setAttr("iflogin", iflogin);
		setAttr("ifnet", ifnet);
		Page<Person> orderpage = AdPersonService.service.getPerson(pageNum, numPerPage, starttime, endtime, id, iflock,
				iflogin, ifnet, emp.getInt("employeeid"),superior);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		List<Person> list = orderpage.getList();
		List<Person> list2 = AdPersonService.service.getPersonlist(list);
		setAttr("perlist", list2);
		renderJsp("/WEB-INF/admin/jsp/person.jsp");
	}


	public void showPerson() {
		int id = getParaToInt("id");
		List<SettlementAccount> settlaccountlist = AdPersonService.service.getSettlAccount(id);
		List<SettlementAccount> bank = new ArrayList<>();
		List<SettlementAccount> network = new ArrayList<>();
		if (settlaccountlist.size() > 0) {
			for (SettlementAccount account : settlaccountlist) {
				if (account.getInt("account_types") == 1) {
					bank.add(account);
				} else if (account.getInt("account_types") == 2) {
					network.add(account);
				}
			}
		}
		Person person=Person.dao.findById(id);
		setAttr("id", id);
		setAttr("bank", bank);
		setAttr("network", network);
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		setAttr("person", person);
		setAttr("todayamount", AdPersonService.service.getNewAccount(id));
		setAttr("payment", Payment.dao.findById(id));
		setAttr("balance", Balance.dao.findById(id));
		setAttr("rate", Rate.dao.findById(id));
		setAttr("gatewaylist", AdGatewayService.service.getGateway());
		setAttr("gateway", PersonGateway.dao.findById(id));
		setAttr("caifutong", Caifutong.dao.findById(id));
		List<Record> list=Db.find("select * from imgfile where phone='"+person.get("phone")+"' limit 1");
		if(list.size()>0)setAttr("imgfile",list.get(0));
		
		setAttr("childList",AdPersonService.service.getPersonlist(AdPersonService.service.getAll(id,1)));
		// setAttr("wxwaplist",AdGatewayService.service.getGatewayWxwap());
		createToken("uppersonmoney", 30 * 60);
		renderJsp("/WEB-INF/admin/jsp/person/showPerson.jsp");
		
		
	}
	
	public void getChildJson(){
		
		if(StringUtil.isEmpty(getPara("superior"))){
			renderText("请输入一个id!");
		}else{
			
			int superior=getParaToInt("superior");
			
			
			List<Person> list=AdPersonService.service.getPersonlist(AdPersonService.service.getAll(superior,1));
			
			renderJson(list);
		}
		
	}

	public void showPersonState() {
		setAttr("person", Person.dao.findById(getParaToInt("id")));
		renderJsp("/WEB-INF/admin/jsp/person/perState.jsp");
	}

	public void upPersonState() {
		int id = getParaToInt("id");
		int iflock = getParaToInt("iflock");
		int ifnet = getParaToInt("ifnet");
		try {
			Person.dao.findById(id).set("iflock", iflock).set("ifnet", ifnet).update();
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav12\", \"forwardUrl\":\"\", \"rel\":\"nav12\", \"callbackType\":\"closeCurrent\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			e.printStackTrace();
		}
	}

	public void showNet() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String netstarttime = "";
		String netendtime = "";
		String netparticipate = "";
		String netstate = "";
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
		setAttr("netstarttime", netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("netparticipate", netparticipate);
		setAttr("netstate", netstate);
		setAttr("id", id);
		Page<Order> orderpage = AdPersonService.service.getNetPage(pageNum, numPerPage, netstarttime, netendtime,
				netparticipate, netstate, id);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		renderJsp("/WEB-INF/admin/jsp/person/perNet.jsp");
	}

	public void showCard() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String cardstarttime = "";
		String cardendtime = "";
		String cardparticipate = "";
		String cardstate = "";
		if (!getPara("cardstarttime").equals("")) {
			cardstarttime = getPara("cardstarttime");
		}
		if (!getPara("cardendtime").equals("")) {
			cardendtime = getPara("cardendtime");
		}
		if (StringUtil.isNotEmpty(getPara("cardparticipate")) && !getPara("cardparticipate").equals("0")) {
			cardparticipate = getPara("cardparticipate");
		}
		if (StringUtil.isNotEmpty(getPara("cardstate")) && !getPara("cardstate").equals("0")) {
			cardstate = getPara("cardstate");
		}
		setAttr("cardstarttime", cardstarttime);
		setAttr("cardendtime", cardendtime);
		setAttr("cardparticipate", cardparticipate);
		setAttr("cardstate", cardstate);
		setAttr("id", id);
		Page<CardOrder> orderpage = AdPersonService.service.getCardPage(pageNum, numPerPage, cardstarttime, cardendtime,
				cardparticipate, cardstate, id);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		renderJsp("/WEB-INF/admin/jsp/person/perCard.jsp");
	}

	public void showRecharge() {
		try {

			int id = getParaToInt("id");
			int pageNum = getParaToInt("pageNum");
			int numPerPage = getParaToInt("numPerPage");
			String rechstarttime = "";
			String rechendtime = "";
			String rechparticipate = "";
			String rechstate = "";
			if (!getPara("rechstarttime").equals("")) {
				rechstarttime = getPara("rechstarttime");
			}
			if (!getPara("rechendtime").equals("")) {
				rechendtime = getPara("rechendtime");
			}
			if (StringUtil.isNotEmpty(getPara("rechparticipate")) && !getPara("rechparticipate").equals("0")) {
				rechparticipate = getPara("rechparticipate");
			}
			if (StringUtil.isNotEmpty(getPara("rechstate")) && !getPara("rechstate").equals("0")) {
				rechstate = getPara("rechstate");
			}
			setAttr("rechstarttime", rechstarttime);
			setAttr("rechendtime", rechendtime);
			setAttr("rechparticipate", rechparticipate);
			setAttr("rechstate", rechstate);
			setAttr("id", id);
			Page<Record> orderpage = AdPersonService.service.getRechargePage(pageNum, numPerPage, rechstarttime,
					rechendtime, rechparticipate, rechstate, id);
			setAttr("pageNum", orderpage.getPageNumber());
			setAttr("numPerPage", orderpage.getPageSize());
			setAttr("totalCount", orderpage.getTotalRow());
			setAttr("pageNumShown", orderpage.getTotalPage());
			setAttr("orderlist", orderpage.getList());
			setAttr("partici", ExchangeService.exchangeService.getPartici());
			renderJsp("/WEB-INF/admin/jsp/person/perRecharge.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showCirclip() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String circstarttime = "";
		String circendtime = "";
		String circparticipate = "";
		String circstate = "";
		if (!getPara("circstarttime").equals("")) {
			circstarttime = getPara("circstarttime");
		}
		if (!getPara("circendtime").equals("")) {
			circendtime = getPara("circendtime");
		}
		if (StringUtil.isNotEmpty(getPara("circparticipate")) && !getPara("circparticipate").equals("0")) {
			circparticipate = getPara("circparticipate");
		}
		if (StringUtil.isNotEmpty(getPara("circstate")) && !getPara("circstate").equals("0")) {
			circstate = getPara("circstate");
		}
		setAttr("circstarttime", circstarttime);
		setAttr("circendtime", circendtime);
		setAttr("circparticipate", circparticipate);
		setAttr("circstate", circstate);
		setAttr("id", id);
		Page<Record> orderpage = null;
		try {
			orderpage = AdPersonService.service.getCirclipPage(pageNum, numPerPage, circstarttime, circendtime,
					circparticipate, circstate, id);
			setAttr("pageNum", orderpage.getPageNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		renderJsp("/WEB-INF/admin/jsp/person/perCirclip.jsp");
	}

	public void showRefund() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String refustarttime = "";
		String refuendtime = "";
		String refustate = "";
		if (!getPara("refustarttime").equals("")) {
			refustarttime = getPara("refustarttime");
		}
		if (!getPara("refuendtime").equals("")) {
			refuendtime = getPara("refuendtime");
		}
		if (StringUtil.isNotEmpty(getPara("refustate")) && !getPara("refustate").equals("0")) {
			refustate = getPara("refustate");
		}
		setAttr("refustarttime", refustarttime);
		setAttr("refuendtime", refuendtime);
		setAttr("refustate", refustate);
		setAttr("id", id);
		Page<Record> orderpage = AdPersonService.service.getRefundPage(pageNum, numPerPage, refustarttime, refuendtime,
				refustate, id);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		setAttr("partici", ExchangeService.exchangeService.getPartici());
		renderJsp("/WEB-INF/admin/jsp/person/perRefund.jsp");
	}

	public void showLog() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String logstarttime = "";
		String logendtime = "";
		if (!getPara("logstarttime").equals("")) {
			logstarttime = getPara("logstarttime");
		}
		if (!getPara("logendtime").equals("")) {
			logendtime = getPara("logendtime");
		}
		setAttr("logstarttime", logstarttime);
		setAttr("logendtime", logendtime);
		setAttr("id", id);
		Page<Record> orderpage = AdPersonService.service.getLogPage(pageNum, numPerPage, logstarttime, logendtime, id);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/person/perLog.jsp");
	}

	public void showAddedamount() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String addestarttime = "";
		String addeendtime = "";
		if (!getPara("addestarttime").equals("")) {
			addestarttime = getPara("addestarttime");
		}
		if (!getPara("addeendtime").equals("")) {
			addeendtime = getPara("addeendtime");
		}
		setAttr("addestarttime", addestarttime);
		setAttr("addeendtime", addeendtime);
		setAttr("id", id);
		Page<Record> orderpage = AdPersonService.service.getAddePage(pageNum, numPerPage, addestarttime, addeendtime,
				id);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/person/perAddedamount.jsp");
	}

	public void showEmp() {
		int id = getParaToInt("id");
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String empname = getPara("empname");
		setAttr("id", id);
		setAttr("empname", empname);
		Page<Record> orderpage = AdPersonService.service.getEmpPage(pageNum, numPerPage, empname);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/person/showEmpPerson.jsp");
	}

	public void getkey() {
		String key = CryptTool.getPassword(32);
		renderText(key);
	}

	public void upPerPass() {
		int id = getParaToInt("id");
		String password = getPara("password");
		String payment = getPara("payment");
		String key = getPara("key");
		if (password.equals("") && payment.equals("")) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"密码或者支付密码没有填写\"}");
		} else {
			try {
				if (!password.equals("")) {
					Person.dao.findById(id).set("password", MD5Utils.createMD5(password)).update();
				}
				if (!payment.equals("")) {
					Payment.dao.findById(id).set("payment", MD5Utils.createMD5(payment)).set("key", key).update();
				}
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
						+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
				e.printStackTrace();
			}
		}
	}

	public void upPersonMoney() {
		boolean booToken = validateToken("uppersonmoney");
		int id = getParaToInt("id");
		String money = getPara("money");
		if (booToken) {
			try {
				Balance balance = Balance.dao.findById(id);
				balance.set("amount", balance.getBigDecimal("amount").add(new BigDecimal(money)));
				balance.set("settlement", balance.getBigDecimal("settlement").add(new BigDecimal(money)));
				balance.update();
				Addedamount addedamount = new Addedamount();
				addedamount.set("addedamount_amount", new BigDecimal(money));
				addedamount.set("addedamount_time", new Date());
				addedamount.set("id", id);
				addedamount.save();
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
						+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
				e.printStackTrace();
			}
		} else {
			renderJson("{\"statusCode\":\"301\", \"message\":\"请不要重复提交\"}");
		}
	}

	public void upSettAuthor() {
		int id = getParaToInt("id");
		int settlementauthority = getParaToInt("settlementauthority");
		String cashleast = getPara("cashleast");
		try {
			Balance balance = Balance.dao.findById(id);
			if (settlementauthority == 1) {
				BigDecimal newamount = balance.getBigDecimal("newamount");
				BigDecimal settlement = balance.getBigDecimal("settlement");
				balance.set("settlement", settlement.add(newamount));
				balance.set("newamount", 0);
			}
			balance.set("cashleast", cashleast);
			balance.set("settlementauthority", settlementauthority);
			balance.update();
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			e.printStackTrace();
		}
	}

	public void upPerPerson() {
		int id = getParaToInt("id");
		String email = getPara("email");
		String webName = getPara("webName");
		String website = getPara("website");
		int herolist = getParaToInt("herolist");
		int huge = getParaToInt("huge");
		String name = getPara("name");
		String contacts = getPara("contacts");
		String idcard = getPara("idcard");
		String address = getPara("address");
		String phone = getPara("phone");
		String qq = getPara("qq");
		int iflock = getParaToInt("iflock");
		int ifnet = getParaToInt("ifnet");
		int isAgent=0;
		int permission=0;
		
		
		if(StringUtil.isNotEmpty(getPara("isAgent"))){
			isAgent=getParaToInt("isAgent");
		}
		if(StringUtil.isNotEmpty(getPara("permission"))){
			permission=getParaToInt("permission");
		}
		
		
		if (email.equals("") && webName.equals("") && website.equals("") && name.equals("") && contacts.equals("")
				&& idcard.equals("") && address.equals("") && phone.equals("") && qq.equals("")) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"信息没有填写完整!\"}");
		} else {
			try {				
				Person person = Person.dao.findById(id);
				
				person.set("isAgent", isAgent);
				person.set("permission", permission);
				person.set("email", email);
				person.set("webName", webName);
				person.set("website", website);
				person.set("herolist", herolist);
				person.set("huge", huge);
				person.set("name", name);
				person.set("contacts", contacts);
				person.set("idcard", idcard);
				person.set("address", address);
				person.set("phone", phone);
				person.set("qq", qq);
				person.set("iflock", iflock);
				person.set("ifnet", ifnet);
				person.update();
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
						+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
				e.printStackTrace();
			}
		}
	}
	
	
	public void editPersonRate(){
		int id = getParaToInt("id");
		Rate rate = Rate.dao.findById(id);
		setAttr("rate",rate);
		renderJsp("/WEB-INF/admin/jsp/person/editPersonRate.jsp");
		
	}

	public void upRate() {
		int id = getParaToInt("id");
		String qqwx = getPara("qqwx");
		String cibsm = getPara("cibsm");
		String mustali = getPara("mustali");
		String banking = getPara("banking");
		String refund = getPara("refund");
		
		
		
		if (qqwx.equals("") && cibsm.equals("") && mustali.equals("") && banking.equals("") && refund.equals("")  ) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"信息没有填写完整!\"}");
		} else {
			try {
				Rate rate = Rate.dao.findById(id);
				
				rate.set("qqwx", qqwx);
				rate.set("cibsm", cibsm);
				rate.set("mustali", mustali);
				if(!banking.equals("")){
					rate.set("banking", banking);
				}
				rate.set("refund", refund);
				
				
				
				rate.update();
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
						+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
				
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
				e.printStackTrace();
			}
		}
	}

	public void upBankAccount() {
		try {
			int id = getParaToInt("id");
			int account_types = 1;
			String account_name = getPara("account_name");
			String branch = getPara("branch");
			String account = getPara("account");
			String branchsheng = getPara("branchsheng");
			String branchshi = getPara("branchshi");
			int codeid = getParaToInt("codeid");
			boolean too = false;
			boolean boo = false;

			boo = SettlementService.service.ifSettleAcount(id, account_types);

			if (boo) {
				too = SettlementService.service.upSettleAcount(id, account_types, account_name, branch, account, codeid,
						branchsheng, branchshi);
			} else {
				too = SettlementService.service.inserSettleAcount(id, account_types, account_name, branch, account,
						codeid, branchsheng, branchshi);
			}
			if (too) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
						+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void upNetwork() {
		boolean too = false;
		boolean boo = false;
		int account_types = 2;
		int id = getParaToInt("id");
		String branch = getPara("branch");
		String account_name = getPara("account_name");
		String account = getPara("account");
		boo = SettlementService.service.ifSettleAcount(id, account_types);
		if (boo) {
			too = SettlementService.service.upSettleAcount(id, account_types, account_name, branch, account, 0, branch,
					branch);
		} else {
			too = SettlementService.service.inserSettleAcount(id, account_types, account_name, branch, account, 0,
					branch, branch);
		}
		if (too) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway() {
		int id = getParaToInt("id");
		int gateway_id = getParaToInt("gateway_id");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("gateway_id", gateway_id);
			boo = pergate.save();
		} else {
			pergate.set("gateway_id", gateway_id);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway1() {
		int id = getParaToInt("id");
		String gateway_id = getPara("paygete_id1");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String new_gate_id = "";
		String new_gate_pd = "";
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("title", gateway_id);
			boo = pergate.save();
		} else {
			String title = pergate.get("title");
			String[] ts = title.split(";");
			String[] gws = gateway_id.split("-");
			boolean status = false;
			String gate_id = gws[0];
			String gate_pd = gws[1];
			String newtitle = "";
			new_gate_id = gate_id;
			new_gate_pd = gate_pd;
			for (int i = 0; i < ts.length; i++) {
				String[] temp = ts[i].split("-");
				if (temp[1].equals(gate_pd)) {
					temp[0] = gate_id;
					if (!gate_id.equals("0")) {
						if (newtitle.equals("")) {
							newtitle = temp[0] + "-" + temp[1];
						} else {
							newtitle += ";" + temp[0] + "-" + temp[1];
						}
						status = true;
					}
				} else {
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
				}
			}
			if (newtitle.equals("") && !status) {
				newtitle = new_gate_id + "-" + new_gate_pd;
			}
			if (!newtitle.equals("") && !status) {
				newtitle += ";" + new_gate_id + "-" + new_gate_pd;
			}
			System.out.println(newtitle);
			pergate.set("title", newtitle);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway2() {
		int id = getParaToInt("id");
		String gateway_id = getPara("paygete_id2");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String new_gate_id = "";
		String new_gate_pd = "";
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("paygete_id2", gateway_id);
			boo = pergate.save();
		} else {
			String title = pergate.get("title");
			String[] ts = title.split(";");
			String[] gws = gateway_id.split("-");
			boolean status = false;
			String gate_id = gws[0];
			String gate_pd = gws[1];
			String newtitle = "";
			new_gate_id = gate_id;
			new_gate_pd = gate_pd;
			for (int i = 0; i < ts.length; i++) {
				String[] temp = ts[i].split("-");
				if (temp[1].equals(gate_pd)) {
					temp[0] = gate_id;
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
					status = true;
				} else {
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
				}
			}
			if (newtitle.equals("") && !status) {
				newtitle = new_gate_id + "-" + new_gate_pd;
			}
			if (!newtitle.equals("") && !status) {
				newtitle += ";" + new_gate_id + "-" + new_gate_pd;
			}
			System.out.println(newtitle);
			pergate.set("title", newtitle);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway3() {
		int id = getParaToInt("id");
		String gateway_id = getPara("paygete_id3");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String new_gate_id = "";
		String new_gate_pd = "";
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("paygete_id3", gateway_id);
			boo = pergate.save();
		} else {
			String title = pergate.get("title");
			String[] ts = title.split(";");
			String[] gws = gateway_id.split("-");
			boolean status = false;
			String gate_id = gws[0];
			String gate_pd = gws[1];
			String newtitle = "";
			new_gate_id = gate_id;
			new_gate_pd = gate_pd;
			for (int i = 0; i < ts.length; i++) {
				String[] temp = ts[i].split("-");
				if (temp[1].equals(gate_pd)) {
					temp[0] = gate_id;
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
					status = true;
				} else {
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
				}
			}
			if (newtitle.equals("") && !status) {
				newtitle = new_gate_id + "-" + new_gate_pd;
			}
			if (!newtitle.equals("") && !status) {
				newtitle += ";" + new_gate_id + "-" + new_gate_pd;
			}
			System.out.println(newtitle);
			pergate.set("title", newtitle);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway4() {
		int id = getParaToInt("id");
		String gateway_id = getPara("paygete_id4");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		String new_gate_id = "";
		String new_gate_pd = "";
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("paygete_id4", gateway_id);
			boo = pergate.save();
		} else {
			String title = pergate.get("title");
			String[] ts = title.split(";");
			String[] gws = gateway_id.split("-");
			boolean status = false;
			String gate_id = gws[0];
			String gate_pd = gws[1];
			String newtitle = "";
			new_gate_id = gate_id;
			new_gate_pd = gate_pd;
			for (int i = 0; i < ts.length; i++) {
				String[] temp = ts[i].split("-");
				if (temp[1].equals(gate_pd)) {
					temp[0] = gate_id;
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
					status = true;
				} else {
					if (newtitle.equals("")) {
						newtitle = temp[0] + "-" + temp[1];
					} else {
						newtitle += ";" + temp[0] + "-" + temp[1];
					}
				}
			}
			if (newtitle.equals("") && !status) {
				newtitle = new_gate_id + "-" + new_gate_pd;
			}
			if (!newtitle.equals("") && !status) {
				newtitle += ";" + new_gate_id + "-" + new_gate_pd;
			}
			System.out.println(newtitle);
			pergate.set("title", newtitle);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGateway5() {
		int id = getParaToInt("id");
		int gateway_id = getParaToInt("paygete_id5");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("paygete_id5", gateway_id);
			boo = pergate.save();
		} else {
			pergate.set("paygete_id5", gateway_id);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upGatewayTitle() {
		int id = getParaToInt("id");
		String title = getPara("title");
		String titletime = getPara("titletime");
		boolean boo = false;
		PersonGateway pergate = PersonGateway.dao.findById(id);
		if (pergate == null) {
			pergate = new PersonGateway();
			pergate.set("id", id);
			pergate.set("title", title);
			pergate.set("titletime", titletime);
			boo = pergate.save();
		} else {
			pergate.set("title", title);
			pergate.set("titletime", titletime);
			boo = pergate.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upEmpPerson() {
		int id = getParaToInt("id");
		int empid = getParaToInt("empid");
		boolean boo = false;
		Employee_Person empper = Employee_Person.dao.findById(id);
		if (empper == null) {
			empper = new Employee_Person();
			empper.set("id", id);
			empper.set("employeeid", empid);
			boo = empper.save();
		} else {
			empper.set("employeeid", empid);
			boo = empper.update();
		}
		if (boo) {
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav12\", \"forwardUrl\":\"\", \"rel\":\"nav12\", \"callbackType\":\"closeCurrent\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void upCaifutong() {
		int id = getParaToInt("id");
		String caifu_account = getPara("caifu_account");
		String caifu_rate = getPara("caifu_rate");
		boolean boo = false;
		Caifutong caifu = Caifutong.dao.findById(id);
		if (caifu == null) {
			caifu = new Caifutong();
			caifu.set("id", id);
			caifu.set("caifu_account", caifu_account);
			caifu.set("caifu_rate", caifu_rate);
			boo = caifu.save();
		} else {
			caifu.set("caifu_account", caifu_account);
			caifu.set("caifu_rate", caifu_rate);
			boo = caifu.update();
		}
		if (boo) {
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id
					+ "\", \"forwardUrl\":\"\", \"rel\":\"person" + id + "\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void showAddPerson() {
		createToken("addpersonToken", 30 * 60);
		renderJsp("/WEB-INF/admin/jsp/person/perAddPerson.jsp");
	}

	public void addPerson() {
		boolean bootoken = validateToken("addpersonToken");
		Person person = new Person();
		Payment pay = new Payment();
		String email = getPara("email");
		String phone = getPara("phone");
		boolean emailboo = RegisterService.service.getEmail(email);
		boolean phoneboo = RegisterService.service.getPhote(phone);
		if (!bootoken) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"请不要重新提交\"}");
		} else if (!emailboo) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"邮箱已存在\"}");
		} else if (!phoneboo) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"手机号已存在\"}");
		} else {
			person.set("email", getPara("email"));
			String password = getPara("password");
			try {
				person.set("password", MD5Utils.createMD5(password));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String payment = getPara("payment");
			try {
				pay.set("payment", MD5Utils.createMD5(MD5Utils.createMD5(payment)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtil.isNotEmpty(getPara("webName"))){
				person.set("webName",getPara("webName"));
			}
			person.set("website","http://"+getPara("website"));
			person.set("herolist", getParaToInt("herolist"));
			person.set("huge", getParaToInt("huge"));
			int huge = getParaToInt("huge");
			
			if(StringUtil.isNotEmpty(getPara("name"))){
				person.set("name", getPara("name"));
			}
			if(StringUtil.isNotEmpty(getPara("contacts"))){
				person.set("contacts", getPara("contacts"));
			}
			String idcard = getPara("idcard");
			String licence = getPara("licence");
			
			if (huge == 0) {
				person.set("idcard", idcard);
			} else {
				person.set("idcard", licence);
			}
			
			if(StringUtil.isNotEmpty(getPara("address"))){
				person.set("address", getPara("address"));
			}
			if(StringUtil.isNotEmpty(getPara("phone"))){
				person.set("phone", getPara("phone"));
			}
			if(StringUtil.isNotEmpty(getPara("qq"))){
				person.set("qq", getPara("qq"));
			}
			
			if(StringUtil.isNotEmpty(getPara("isAgent"))){
				person.set("isAgent", getPara("isAgent"));
			}
			
			if(StringUtil.isNotEmpty(getPara("permission"))&&getPara("isAgent").equals("1")){
				
				person.set("permission", getPara("permission"));
			}
			
			if(StringUtil.isNotEmpty(getPara("superior"))){
				int superior=getParaToInt("superior");
				
				boolean superiorboo=RegisterService.service.getId(superior);
				if(superiorboo){
					
					person.set("superior", superior);
				}else{
					
					renderJson("{\"statusCode\":\"300\", \"message\":\"上级代理人不存在！\"}");
				}
			}
			
			person.set("time", new Date());
			person.set("iflogin", 2);
			person.set("iflock", 1);
			person.set("ifnet", 2);
			String key = CryptTool.getPassword(32);
			pay.set("key", key);
			boolean res = RegisterService.service.enroll(person, pay);
			if (res) {
				Employees employees = getSessionAttr("adminsession");
				Employee_Person employee_Person = new Employee_Person();
				employee_Person.set("id", person.getInt("id"));
				employee_Person.set("employeeid", employees.getInt("employeeid"));
				employee_Person.save();
				renderJson(
						"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav12\", \"forwardUrl\":\"\", \"rel\":\"nav12\", \"callbackType\":\"closeCurrent\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		}
	}
	
	
	
	
	
	public void checkPerson(){
		int  id = getParaToInt("id");
		boolean boo=true;
		
		Person person=Person.dao.findById(id);
		if(person.get("iflock")!=null&&person.get("iflock").toString().equals("0")){
			person.set("iflock", 1);
			boo=person.update();
		}
		
		
		if(boo){
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\"}");	
		}else{
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		} 
	}
}
