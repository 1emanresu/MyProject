package com.admin.gatewaylog.controller;
import com.admin.gatewaylog.service.AdGatewayLogService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
@Before(AdLoginSeInterceptor.class)
public class AdGatewayLogController extends Controller{
	public void index(){
		try {
			int pageNum = getParaToInt("pageNum");
			int numPerPage=1;
			if(getParaToInt("numPerPage") != null){
				numPerPage = getParaToInt("numPerPage");
			}
			String gateway_id = "";
			String starttime = "";
			String endtime = "";
			if(StringUtil.isNotEmpty(getPara("gateway_id"))){
				gateway_id = getPara("gateway_id");
			}
			if(StringUtil.isNotEmpty(getPara("starttime"))){
				starttime = getPara("starttime");
			}
			if(StringUtil.isNotEmpty(getPara("endtime"))){
				endtime = getPara("endtime");
			}
			Page<Record> page = AdGatewayLogService.service.queryGatewayLog(pageNum, numPerPage, gateway_id, starttime, endtime);
			setAttr("gateway_id", gateway_id);
			setAttr("starttime", starttime);
			setAttr("endtime", endtime);
			setAttr("pageNum", page.getPageNumber());
			setAttr("numPerPage", page.getPageSize());
			setAttr("totalCount", page.getTotalRow());
			setAttr("gatewayLog", page.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/gatewaylog/gatewaylog.jsp");
	}
	
}
