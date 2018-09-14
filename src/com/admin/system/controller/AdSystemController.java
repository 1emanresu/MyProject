package com.admin.system.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.admin.gateway.service.AdGatewayService;
import com.admin.system.service.AdSystemService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.MD5Utils;
import com.tool.StringUtil;
import com.vo.Employees;
import com.vo.Order;
import com.vo.System_interface;
import com.vo.System_qq;
import com.vo.Systemss;

@Before(AdLoginSeInterceptor.class)
public class AdSystemController extends Controller {

	public void index() {
		Systemss systemss = Systemss.dao.findById(1);
		setAttr("systemss", systemss);
		renderJsp("/WEB-INF/admin/jsp/systemss.jsp");
	}

	public void systemInterface() {
		System_interface systemInterface = System_interface.dao.findById(1);
		setAttr("systemInterface", systemInterface);
		renderJsp("/WEB-INF/admin/jsp/system_interface.jsp");
	}

	public void alarm() {
		Systemss systemss = Systemss.dao.findById(1);
		setAttr("systemss", systemss);
		setAttr("gatewaylist", AdGatewayService.service.getGateway());
		renderJsp("/WEB-INF/admin/alarm.jsp");
	}

	
	// 新增订单监控
	public void control() {
		renderJsp("/WEB-INF/admin/jsp/control.jsp");
	}
	
	public void startControl(){
		String currentTime = getPara("currentTime");
		String timeType = getPara("timeType");
		float successRate = 0;
		if(StringUtil.isNotEmpty(getPara("successRate"))){
			float rate = Float.parseFloat(getPara("successRate"));
			successRate = rate/100;
		}
		String minAmount = getPara("minAmount");
		String maxAmount = getPara("maxAmount");
		String flushTime = getPara("flushTime");
		//当前时间应该减去的时间
		int minusTime = Integer.parseInt(currentTime);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//当前时间
		String nowTime = sdFormat.format(new Date());
		//之前时间
		String beforeTime = "";
		if(StringUtil.isNotEmpty(currentTime) && StringUtil.isNotEmpty(timeType)){
			Calendar cal = Calendar.getInstance();
			if(timeType.equals("1")){
				cal.add(Calendar.HOUR, -minusTime);
			}else if(timeType.equals("2")){
				cal.add(Calendar.MINUTE, -minusTime);
			}
			beforeTime = sdFormat.format(cal.getTime());
		}
		List<Order> orderList = AdSystemService.service.findOrdersByParam(beforeTime, nowTime, minAmount, maxAmount);
		List<Order> successOrder = AdSystemService.service.successOrdersByParam(beforeTime, nowTime, minAmount, maxAmount);
		if(orderList != null && orderList.size() > 0){
			for (Order order : orderList) {
				order.put("successOrder", 0);
				String allId = order.get("gateway_id").toString();
				if(successOrder != null && successOrder.size() > 0){
					for (Order success : successOrder) {
						String successId = success.get("gateway_id").toString();
						if(allId.equals(successId)){
							order.put("successOrder", success.get("successOrder"));
							successOrder.remove(success);
							break;
						}
					}
				}
			}
		}
		if(orderList != null && orderList.size() > 0){
			for (Order order : orderList) {
				String countOrder = order.get("countOrder").toString();
				String successedOrder = order.get("successOrder").toString();
				int allOrder = Integer.parseInt(countOrder);
				int dealOrder = Integer.parseInt(successedOrder);
				DecimalFormat df=new DecimalFormat("0.000");
				String order_rate = df.format((float)dealOrder/allOrder);
				order.put("order_rate", order_rate);
				float orderRate = Float.parseFloat(order.get("order_rate").toString());
				if(orderRate<successRate){
					order.put("warning_color","pink");
					setAttr("startwarning", "warning");
				}
			}
		}
		setAttr("currentTime", currentTime);
		setAttr("timeType", timeType);
		setAttr("successRate", getPara("successRate"));
		setAttr("minAmount", minAmount);
		setAttr("maxAmount", maxAmount);
		setAttr("flushTime", flushTime);
		setAttr("orderList", orderList);
		renderJsp("/WEB-INF/admin/jsp/control.jsp");
	}
	
	public void setSystemss() {
		String system_webName = getPara("system_webName");
		String system_keyword = getPara("system_keyword");
		String system_included = getPara("system_included");
		String system_filings = getPara("system_filings");
		String system_company = getPara("system_company");
		String system_firm = getPara("system_firm");
		String system_address = getPara("system_address");
		String system_telephone = getPara("system_telephone");
		Systemss systemss = Systemss.dao.findById(1);
		boolean boo = false;
		if (systemss == null) {
			systemss = new Systemss();
			systemss.set("system_webName", system_webName);
			systemss.set("system_keyword", system_keyword);
			systemss.set("system_included", system_included);
			systemss.set("system_filings", system_filings);
			systemss.set("system_company", system_company);
			systemss.set("system_firm", system_firm);
			systemss.set("system_address", system_address);
			systemss.set("system_telephone", system_telephone);
			boo = systemss.save();
		} else {
			systemss.set("system_webName", system_webName);
			systemss.set("system_keyword", system_keyword);
			systemss.set("system_included", system_included);
			systemss.set("system_filings", system_filings);
			systemss.set("system_company", system_company);
			systemss.set("system_firm", system_firm);
			systemss.set("system_address", system_address);
			systemss.set("system_telephone", system_telephone);
			boo = systemss.update();
		}
		if (boo) {
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav25\", \"forwardUrl\":\"\", \"rel\":\"nav25\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void setSystemInterface() {
		String system_interface_usercode = getPara("system_interface_usercode");
		String system_interface_md5key = getPara("system_interface_md5key");
		String system_interface_fastmoneykey = getPara("system_interface_fastmoneykey");
		String system_interface_caifutongemial = getPara("system_interface_caifutongemial");
		String system_interface_alipayemail = getPara("system_interface_alipayemail");
		String system_interface_httpurl = getPara("system_interface_httpurl");
		String system_interface_smsurl = getPara("system_interface_smsurl");
		String system_interface_smsaccount = getPara("system_interface_smsaccount");
		String system_interface_smspassword = getPara("system_interface_smspassword");
		System_interface systemInterface = System_interface.dao.findById(1);
		boolean boo = false;
		if (systemInterface == null) {
			systemInterface = new System_interface();
			systemInterface.set("system_interface_usercode", system_interface_usercode);
			systemInterface.set("system_interface_md5key", system_interface_md5key);
			systemInterface.set("system_interface_fastmoneykey", system_interface_fastmoneykey);
			systemInterface.set("system_interface_caifutongemial", system_interface_caifutongemial);
			systemInterface.set("system_interface_alipayemail", system_interface_alipayemail);
			systemInterface.set("system_interface_httpurl", system_interface_httpurl);
			systemInterface.set("system_interface_smsurl", system_interface_smsurl);
			systemInterface.set("system_interface_smsaccount", system_interface_smsaccount);
			systemInterface.set("system_interface_smspassword", system_interface_smspassword);
			boo = systemInterface.save();
		} else {
			systemInterface.set("system_interface_usercode", system_interface_usercode);
			systemInterface.set("system_interface_md5key", system_interface_md5key);
			systemInterface.set("system_interface_fastmoneykey", system_interface_fastmoneykey);
			systemInterface.set("system_interface_caifutongemial", system_interface_caifutongemial);
			systemInterface.set("system_interface_alipayemail", system_interface_alipayemail);
			systemInterface.set("system_interface_httpurl", system_interface_httpurl);
			systemInterface.set("system_interface_smsurl", system_interface_smsurl);
			systemInterface.set("system_interface_smsaccount", system_interface_smsaccount);
			systemInterface.set("system_interface_smspassword", system_interface_smspassword);
			boo = systemInterface.update();
		}
		if (boo) {
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav26\", \"forwardUrl\":\"\", \"rel\":\"nav26\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void showSystemQQ() {
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		Page<Record> orderpage = AdSystemService.service.getSystemQQPage(pageNum, numPerPage);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/system_qq.jsp");
	}

	public void showSystemQQviw() {
		int qq_id = getParaToInt("qq_id");
		if (qq_id != 0) {
			System_qq system_qq = System_qq.dao.findById(qq_id);
			setAttr("system_qq", system_qq);
		}
		renderJsp("/WEB-INF/admin/jsp/systemqq/addSystemqq.jsp");
	}

	public void upSystemQQ() {
		String qq_id = getPara("qq_id");
		String qq_number = getPara("qq_number");
		String qq_name = getPara("qq_name");
		System_qq system_qq = System_qq.dao.findById(qq_id);
		boolean boo = false;
		if (system_qq == null) {
			system_qq = new System_qq();
			system_qq.set("qq_number", qq_number);
			system_qq.set("qq_name", qq_name);
			boo = system_qq.save();
		} else {
			system_qq.set("qq_number", qq_number);
			system_qq.set("qq_name", qq_name);
			boo = system_qq.update();
		}
		if (boo) {
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav27\", \"forwardUrl\":\"\", \"rel\":\"nav27\", \"callbackType\":\"closeCurrent\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}

	public void delSystemQQ() {
		String qq_id = getPara("qq_id");
		System_qq system_qq = System_qq.dao.findById(qq_id);
		boolean boo = system_qq.delete();
		if (boo) {
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav27\", \"forwardUrl\":\"\", \"rel\":\"nav27\"}");
		} else {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void upEmp() {
		try {
			String password = getPara("password");
			Employees emp = Employees.dao.findById(1);
			emp.set("password", MD5Utils.createMD5(password));
			boolean boo = false;
			boo = emp.update();
			if (boo) {
				renderJson(
						"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav25\", \"forwardUrl\":\"\", \"rel\":\"nav25\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}
}
