package com.admin.userrequestlog.controller;

import com.admin.userrequestlog.service.AdUserRequestLogService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;

@Before(AdLoginSeInterceptor.class)
public class AdUserRequestLogController extends Controller{

	public void index(){
		
		int pageNum = 1;
		int numPerPage=20;
		
		
		String netstarttime = "";
		String netendtime = "";
		String uname ="";
		String name ="";

		
		int netemployeeid = 0;
		int netid = 0;

		
		if(StringUtil.isNotEmpty(getPara("numPerPage"))){
			numPerPage=getParaToInt("numPerPage");
		}
		if(StringUtil.isNotEmpty(getPara("pageNum"))){
			pageNum=getParaToInt("pageNum");
		}
		
		if(StringUtil.isNotEmpty(getPara("netid"))){
			netid=getParaToInt("netid");
		}
		if(StringUtil.isNotEmpty(getPara("netemployeeid"))){
			netemployeeid=getParaToInt("netemployeeid");
		}
		
		
		if (StringUtil.isNotEmpty(getPara("netstarttime"))) {
			netstarttime = getPara("netstarttime").trim();
		}
		if (StringUtil.isNotEmpty(getPara("netendtime"))) {
			netendtime = getPara("netendtime").trim();
		}
		
		if (StringUtil.isNotEmpty(getPara("uname"))) {
			uname = getPara("uname").trim();
		}
		if (StringUtil.isNotEmpty(getPara("name"))) {
			name = getPara("name").trim();
		}
		
		Page<Record> page=AdUserRequestLogService.service.pageLogs(pageNum, numPerPage, netstarttime, netendtime, netemployeeid,netid,uname,name);

		
		
		setAttr("logs", page.getList());
		setAttr("pageNum", pageNum);
		setAttr("numPerPage",numPerPage);
		setAttr("totalCount", page.getTotalRow());
		
		if(netemployeeid!=0){
			setAttr("netemployeeid",netemployeeid);
		}
		setAttr("netstarttime",netstarttime);
		setAttr("netendtime", netendtime);
		setAttr("uname", uname);
		
		renderJsp("/WEB-INF/admin/jsp/user/userRequestLog.jsp");
		
	}
}
