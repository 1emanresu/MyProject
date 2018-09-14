package com.admin.customerlog.controller;
import com.admin.customerlog.serivce.AdCustomerLogService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;

@Before(AdLoginSeInterceptor.class)
public class AdCustomerLogController extends Controller {
	
	public void index(){
		try {
			int pageNum = getParaToInt("pageNum");
			int numPerPage=1;
			if(getParaToInt("numPerPage") != null){
				numPerPage = getParaToInt("numPerPage");
			}
			String customer_id="";
			String customerstarttime = "";
			String customerendtime = "";
			String operatType = "";
			String refundType = "";
			if(StringUtil.isNotEmpty(getPara("customer_id"))){
				customer_id = getPara("customer_id");
			}
			if(StringUtil.isNotEmpty(getPara("customerstarttime"))){
				customerstarttime = getPara("customerstarttime");
			}
			if(StringUtil.isNotEmpty(getPara("customerendtime"))){
				customerendtime = getPara("customerendtime");
			}
			if(StringUtil.isNotEmpty(getPara("operatType"))){
				operatType = getPara("operatType");
			}
			if(StringUtil.isNotEmpty(getPara("refundType"))){
				refundType = getPara("refundType");
			}
			Page<Record> customerPage = AdCustomerLogService.service.findCustomerLog(pageNum, numPerPage, customer_id, customerstarttime, customerendtime,operatType,refundType);
			setAttr("pageNum", customerPage.getPageNumber());
			setAttr("numPerPage", customerPage.getPageSize());
			setAttr("totalCount", customerPage.getTotalRow());
			setAttr("customerList", customerPage.getList());
			setAttr("customer_id", customer_id);
			setAttr("customerstarttime", customerstarttime);
			setAttr("customerendtime", customerendtime);
			setAttr("operatType", operatType);
			setAttr("refundType", refundType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/customer/customerLog.jsp");
	}
}
