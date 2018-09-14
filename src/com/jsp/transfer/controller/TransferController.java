package com.jsp.transfer.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.interceptor.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.transfer.service.TransferService;
import com.tool.MD5Utils;
import com.vo.Balance;
import com.vo.Payment;
import com.vo.Person;
import com.vo.Transfer;

@Before(LoginInterceptor.class)
public class TransferController extends Controller {
	public void index(){
		Person per = getSessionAttr("session");
		setAttr("balance", Balance.dao.findById(per.getInt("id")));
		renderJsp("/WEB-INF/jsp/transfer.jsp");
	}
	
	public void addTransfer(){
		Person per = getSessionAttr("session");
		Balance baro =Balance.dao.findById(per.getInt("id"));
		String transfer_amount = getPara("transfer_amount");
		String transfer_usert = getPara("transfer_usert");
		String transfer_explain = getPara("transfer_explain");
		String payment = getPara("payment");
		String paypassword = "";
		try {
			paypassword = MD5Utils.createMD5(MD5Utils.createMD5(payment));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		BigDecimal amount = new BigDecimal(transfer_amount);
		Balance bart = Balance.dao.findById(transfer_usert);
		BigDecimal settlement = baro.getBigDecimal("settlement");
		Payment pay = Payment.dao.findById(per.getInt("id"));
		String payme = pay.getStr("payment");
		if(!payme.equals(paypassword)){
			renderJson("{\"info\":\"安全密码不正确\",\"status\":\"n\"}");
		}else if(amount.compareTo(settlement)==1){
			renderJson("{\"info\":\"提交失败！提交金额大于可结算金额\",\"status\":\"n\"}");
		}else if(per.getInt("id")==Integer.parseInt(transfer_usert)){
			renderJson("{\"info\":\"提交失败！不能给自己转账\",\"status\":\"n\"}");
		}else if(bart==null){
			renderJson("{\"info\":\"提交失败！无该用户\",\"status\":\"n\"}");
		}else{
			Transfer transfer = new Transfer();
			transfer.set("transfer_usero", per.getInt("id"));
			transfer.set("transfer_usert", transfer_usert);
			transfer.set("transfer_time", new Date());
			transfer.set("transfer_explain", transfer_explain);
			transfer.set("transfer_amount", transfer_amount);
			boolean boo = transfer.save();
			
			if(boo) {
				baro.set("settlement", baro.getBigDecimal("settlement").subtract(amount));
				baro.set("amount", baro.getBigDecimal("amount").subtract(amount));
				baro.update();
				bart.set("settlement", bart.getBigDecimal("settlement").add(amount));
				bart.set("amount", bart.getBigDecimal("amount").add(amount));
				bart.update();
				renderJson("{\"info\":\"提交成功！\",\"status\":\"y\"}");
			} else {
				renderJson("{\"info\":\"提交失败！\",\"status\":\"n\"}");
			}
		}
	}
	
	public void getTransfer(){
		int page = getParaToInt("page");
		String starttime = "";
		String endtime = "";
		String transferid = "";
		if(!getPara("starttime").equals("")){
			starttime = getPara("starttime");
		}
		if(!getPara("endtime").equals("")){
			endtime = getPara("endtime");
		}
		if(!getPara("transferid").equals("")){
			transferid = getPara("transferid");
		}
		Person per = getSessionAttr("session");
		Page<Record> orderPage = TransferService.service.getTransfer(per.getInt("id"), page, starttime, endtime, transferid);
		List<Record> orderList = orderPage.getList();
		List list = new ArrayList();
		list.add(orderList);
		list.add(orderPage.getTotalPage());
		String json = JsonKit.toJson(list, 3);
		renderJson(json);
	}
}
