package com.admin.caifutong.controller;

import java.math.BigDecimal;

import com.admin.caifutong.service.AdCaifutongService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vo.Caifutong;
import com.vo.CaifutongOrder;
import com.vo.Employees;
import com.vo.Gateway;
import com.vo.Order;
import com.vo.System_interface;

@Before(AdLoginSeInterceptor.class)
public class AdCaifutongController extends Controller {

	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String sp_billno = getPara("sp_billno");
		if(sp_billno==null){
			sp_billno = "";
		}
		String starttime = getPara("starttime");
		if(starttime==null){
			starttime = "";
		}
		String endtime = getPara("endtime");
		if(endtime==null){
			endtime = "";
		}
		String pay_result = getPara("pay_result");
		if(pay_result==null){
			pay_result = "4";
		}
		setAttr("pay_result", pay_result);
		setAttr("sp_billno", sp_billno);
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		Employees emp = getSessionAttr("adminsession");
		Page<Record> orderpage = AdCaifutongService.service.getCaifutongOrder(pageNum, numPerPage, emp.getInt("employeeid"), sp_billno, starttime, endtime, pay_result);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/caifutong.jsp");
	}
	
	public void getCaifutong(){
		String sp_billno = getPara("sp_billno");
		CaifutongOrder order = CaifutongOrder.dao.findById(sp_billno);
		setAttr("caifutong", order);
		renderJsp("/WEB-INF/admin/jsp/caifutong/showCaifutong.jsp");
	}
	
	public void caifutongreturn(){
		String sp_billno = getPara("sp_billno");
		StringBuffer url = new StringBuffer();
		Gateway gate = Gateway.dao.findById(4);
		CaifutongOrder caiorder = CaifutongOrder.dao.findById(sp_billno);
		Order order = Order.dao.findById(sp_billno);
		System_interface systeminterface = System_interface.dao.findById(1);
		String httpurl = systeminterface.getStr("system_interface_httpurl");
		url.append(httpurl+"/clientSplitRollback?");
		url.append("&bargainor_id="+gate.getStr("gateway_merid"));
		url.append("&key="+gate.getStr("gateway_key"));
		url.append("&sp_billno="+sp_billno);
		url.append("&transaction_id="+caiorder.getStr("transaction_id"));
		String id = order.get("p1_MerId");
		Caifutong caifutong = Caifutong.dao.findById(id);
		BigDecimal caifu_rate = caifutong.getBigDecimal("caifu_rate");
		BigDecimal total_fee = caiorder.getBigDecimal("total_fee");
		BigDecimal amt1 = total_fee.multiply(caifu_rate);
		BigDecimal amt2 = total_fee.subtract(amt1);
		String total_fees = total_fee.multiply(BigDecimal.valueOf(100)).toString();
		if(total_fees.indexOf(".") > 0){  
			total_fees = total_fees.replaceAll("0+?$", "");//去掉多余的0  
			total_fees = total_fees.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&total_fee="+total_fee);
		String amts1 = amt1.multiply(BigDecimal.valueOf(100)).toString();
		if(amts1.indexOf(".") > 0){  
			amts1 = amts1.replaceAll("0+?$", "");//去掉多余的0  
			amts1 = amts1.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts1="+amts1);
		String amts2 = amt2.multiply(BigDecimal.valueOf(100)).toString();
		if(amts2.indexOf(".") > 0){  
			amts2 = amts2.replaceAll("0+?$", "");//去掉多余的0  
			amts2 = amts2.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
		url.append("&amts2="+amts2);
		String caifutongemial = systeminterface.getStr("system_interface_caifutongemial");
		url.append("&caifutongemial="+caifutongemial);
		url.append("&caifu_account="+caifutong.getStr("caifu_account"));
		redirect(url.toString());
	}
}
