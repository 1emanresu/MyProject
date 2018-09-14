package com.admin.statistics.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.admin.statistics.service.AdStatisticsService;
import com.alibaba.fastjson.JSON;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.pay.yeepay.server.Configuration;
import com.vo.Employees;

@Before(AdLoginSeInterceptor.class)
public class AdStatisticsController extends Controller {
	
	public void index(){
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
		String time = ft.format(date);
		setAttr("time1", time+"-1");
		setAttr("time2", time+"-30");
		renderJsp("/WEB-INF/admin/jsp/statistics.jsp");
	}
	
	public void showStatistics(){
		String time1 = getPara("time1");
		String time2 = getPara("time2");
		Employees emp = getSessionAttr("adminsession");
		List<Record> carstatis = AdStatisticsService.service.getStatistiCard(time1, time2,emp.getInt("employeeid"));
		List<Record> netstatis = AdStatisticsService.service.getStatistiNet2(time1, time2,emp.getInt("employeeid"));
		List<Record> circlipstatis = AdStatisticsService.service.getStatistiCirclip(time1, time2,emp.getInt("employeeid"));
		List<Record> rechargestatis = AdStatisticsService.service.getStatistiRecharge(time1, time2,emp.getInt("employeeid"));
		List<Record> refundstatis = AdStatisticsService.service.getStatistiRefund(time1, time2,emp.getInt("employeeid"));
		setAttr("time1", time1);
		setAttr("time2", time2);
		setAttr("carstatis", carstatis.get(0));
		setAttr("netstatis", netstatis.get(0));
		setAttr("circlipstatis", circlipstatis.get(0));
		setAttr("rechargestatis", rechargestatis.get(0));
		setAttr("refundstatis", refundstatis.get(0));
		renderJsp("/WEB-INF/admin/jsp/statistics/showStatistics.jsp");
	}
	
	public void showStatieCard(){
		try {
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = getPara("time1");
			String time2 = getPara("time2");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
			String[] cardtime = new String[(int)quot];
			BigDecimal[] p3_Amt = new BigDecimal[(int)quot];
			BigDecimal[] amount = new BigDecimal[(int)quot];
			BigDecimal[] deducted = new BigDecimal[(int)quot];
			Record card = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 24 * 60 * 60 * 1000);
				cardtime[i-1] = "'"+startime+"'";
				card = AdStatisticsService.service.getStatistiCard(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(card.get("p3_Amt")==null){
					p3_Amt[i-1] = new BigDecimal("0");
				}else{
					p3_Amt[i-1] = card.getBigDecimal("p3_Amt");
				}
				if(card.get("amount")==null){
					amount[i-1] = new BigDecimal("0");
				}else{
					amount[i-1] = card.getBigDecimal("amount");
				}
				if(card.get("deducted")==null){
					deducted[i-1] = new BigDecimal("0");
				}else{
					deducted[i-1] = card.getBigDecimal("deducted");
				}
			}
			setAttr("time1", time1);
			setAttr("time2", time2);
			setAttr("cardtime", cardtime);
			setAttr("p3_Amt", p3_Amt);
			setAttr("amount", amount);
			setAttr("deducted", deducted);
			renderJsp("/WEB-INF/admin/jsp/statistics/showCardStatement.jsp");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			String adminurl = Configuration.getInstance().getValue("adminurl");
			redirect(adminurl);
			e.printStackTrace();
		}
	}

	public void showStatieNet(){
		try {
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = getPara("time1");
			String time2 = getPara("time2");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
			String[] nettime = new String[(int)quot];
			BigDecimal[] p3_Amt = new BigDecimal[(int)quot];
			BigDecimal[] amount = new BigDecimal[(int)quot];
			BigDecimal[] deducted = new BigDecimal[(int)quot];
			Record net = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 24 * 60 * 60 * 1000);
				nettime[i-1] = "'"+startime+"'";
				net = AdStatisticsService.service.getStatistiNet2(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(net.get("p3_Amt")==null){
					p3_Amt[i-1] = new BigDecimal("0");
				}else{
					p3_Amt[i-1] = net.getBigDecimal("p3_Amt");
				}
				if(net.get("amount")==null){
					amount[i-1] = new BigDecimal("0");
				}else{
					amount[i-1] = net.getBigDecimal("amount");
				}
				if(net.get("deducted")==null){
					deducted[i-1] = new BigDecimal("0");
				}else{
					deducted[i-1] = net.getBigDecimal("deducted");
				}
			}
			setAttr("time1", time1);
			setAttr("time2", time2);
			setAttr("nettime", JSON.toJSON(nettime));
			setAttr("p3_Amt", JSON.toJSON(p3_Amt));
			setAttr("amount", JSON.toJSON(amount));
			setAttr("deducted", JSON.toJSON(deducted));
			renderJsp("/WEB-INF/admin/jsp/statistics/showNetStatement.jsp");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			String adminurl = Configuration.getInstance().getValue("adminurl");
			redirect(adminurl);
			e.printStackTrace();
		}
	}
	
	public void showStatieCirclip(){
		try {
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = getPara("time1");
			String time2 = getPara("time2");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
			String[] circliptime = new String[(int)quot];
			BigDecimal[] actual = new BigDecimal[(int)quot];
			BigDecimal[] accountvalue = new BigDecimal[(int)quot];
			BigDecimal[] deducted = new BigDecimal[(int)quot];
			Record circlip = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 24 * 60 * 60 * 1000);
				circliptime[i-1] = "'"+startime+"'";
				circlip = AdStatisticsService.service.getStatistiCirclip(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(circlip.get("actual")==null){
					actual[i-1] = new BigDecimal("0");
				}else{
					actual[i-1] = circlip.getBigDecimal("actual");
				}
				if(circlip.get("accountvalue")==null){
					accountvalue[i-1] = new BigDecimal("0");
				}else{
					accountvalue[i-1] = circlip.getBigDecimal("accountvalue");
				}
				if(circlip.get("deducted")==null){
					deducted[i-1] = new BigDecimal("0");
				}else{
					deducted[i-1] = circlip.getBigDecimal("deducted");
				}
			}
			setAttr("time1", time1);
			setAttr("time2", time2);
			setAttr("circliptime", circliptime);
			setAttr("actual", actual);
			setAttr("accountvalue", accountvalue);
			setAttr("deducted", deducted);
			renderJsp("/WEB-INF/admin/jsp/statistics/showCirclipStatement.jsp");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			String adminurl = Configuration.getInstance().getValue("adminurl");
			redirect(adminurl);
			e.printStackTrace();
		}
	}
	
	public void showStatieRecharge(){
		try {
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = getPara("time1");
			String time2 = getPara("time2");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
			String[] rechargetime = new String[(int)quot];
			BigDecimal[] p3_Amt = new BigDecimal[(int)quot];
			BigDecimal[] actual = new BigDecimal[(int)quot];
			BigDecimal[] deducted = new BigDecimal[(int)quot];
			Record recharge = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 24 * 60 * 60 * 1000);
				rechargetime[i-1] = "'"+startime+"'";
				recharge = AdStatisticsService.service.getStatistiRecharge(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(recharge.get("p3_Amt")==null){
					p3_Amt[i-1] = new BigDecimal("0");
				}else{
					p3_Amt[i-1] = recharge.getBigDecimal("p3_Amt");
				}
				if(recharge.get("actual")==null){
					actual[i-1] = new BigDecimal("0");
				}else{
					actual[i-1] = recharge.getBigDecimal("actual");
				}
				if(recharge.get("deducted")==null){
					deducted[i-1] = new BigDecimal("0");
				}else{
					deducted[i-1] = recharge.getBigDecimal("deducted");
				}
			}
			setAttr("time1", time1);
			setAttr("time2", time2);
			setAttr("rechargetime", rechargetime);
			setAttr("p3_Amt", p3_Amt);
			setAttr("actual", actual);
			setAttr("deducted", deducted);
			renderJsp("/WEB-INF/admin/jsp/statistics/showRechargeStatement.jsp");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			String adminurl = Configuration.getInstance().getValue("adminurl");
			redirect(adminurl);
			e.printStackTrace();
		}
	}
	
	public void showStatieRefund(){
		try {
			long quot = 0;
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time1 = getPara("time1");
			String time2 = getPara("time2");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime()-date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
			String[] refundtime = new String[(int)quot];
			BigDecimal[] refund_amount = new BigDecimal[(int)quot];
			Record recharge = null;
			String startime = time1;
			Employees emp = getSessionAttr("adminsession");
			for(int i=1; i<=quot; i++){
				String endtime = ft.format(ft.parse(startime).getTime() + 24 * 60 * 60 * 1000);
				refundtime[i-1] = "'"+startime+"'";
				recharge = AdStatisticsService.service.getStatistiRefund(startime, endtime,emp.getInt("employeeid")).get(0);
				startime = endtime;
				if(recharge.get("refund_amount")==null){
					refund_amount[i-1] = new BigDecimal("0");
				}else{
					refund_amount[i-1] = recharge.getBigDecimal("refund_amount");
				}
			}
			setAttr("time1", time1);
			setAttr("time2", time2);
			setAttr("refundtime", refundtime);
			setAttr("refund_amount", refund_amount);
			renderJsp("/WEB-INF/admin/jsp/statistics/showRefundStatement.jsp");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			String adminurl = Configuration.getInstance().getValue("adminurl");
			redirect(adminurl);
			e.printStackTrace();
		}
	}
}
