package com.admin.dynamic.controller;

import java.util.Date;

import com.admin.dynamic.serivce.AdDynamicService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.vo.Dynamic;
import com.vo.Employees;

@Before(AdLoginSeInterceptor.class)
public class AdDynamicController extends Controller {
	
	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String starttime = "";
		String endtime = "";
		String title = "";
		if(!getPara("starttime").equals("")){
			starttime = getPara("starttime");
		}
		if(!getPara("endtime").equals("")){
			endtime = getPara("endtime");
		}
		if(!getPara("title").equals("")){
			title = getPara("title");
		}
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("title", title);
		Employees employ = getSessionAttr("adminsession");
		Page<Dynamic> dynamicpage = AdDynamicService.service.getDynamicPage(pageNum, numPerPage, starttime, endtime, title, employ.getInt("employeeid"));
		setAttr("pageNum", dynamicpage.getPageNumber());
		setAttr("numPerPage", dynamicpage.getPageSize());
		setAttr("totalCount", dynamicpage.getTotalRow());
		setAttr("pageNumShown", dynamicpage.getTotalPage());
		setAttr("dynamiclist", dynamicpage.getList());
		renderJsp("/WEB-INF/admin/jsp/dynamic.jsp");
	}

	public void getaddDynamic(){
		renderJsp("/WEB-INF/admin/jsp/addDynamic.jsp");
	}
	
	public void getupDynamic(){
		int dynamicid = getParaToInt("dynamicid");
		Dynamic dynamic = Dynamic.dao.findById(dynamicid);
		setAttr("dynamic", dynamic);
		renderJsp("/WEB-INF/admin/jsp/upDynamic.jsp");
	}
	
	public void addDynamic(){
		String title = getPara("title");
		String content = getPara("content");
		if(title.equals("")&&title.equals("")){
			renderJson("{\"statusCode\":\"300\", \"message\":\"标题或者内容没有填写\"}");
		}else{
			try {
				Employees employee = getSessionAttr("adminsession");
				Dynamic dynamic = new Dynamic();
				dynamic.set("title", title);
				dynamic.set("content", content);
				dynamic.set("createtime", new Date());
				dynamic.set("employeeid", employee.get("employeeid"));
				dynamic.save();
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav9\", \"forwardUrl\":\"\", \"rel\":\"nav9\", \"callbackType\":\"closeCurrent\"}");
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
				e.printStackTrace();
			}
		}
	}
	
	public void delDynamic(){
		int dynamicid = 0;
		try {
			dynamicid = getParaToInt("dynamicid");
			Dynamic.dao.deleteById(dynamicid);
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav9\", \"forwardUrl\":\"\", \"rel\":\"nav9\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}
	
	public void upDynamic(){
		try {
			int dynamicid = getParaToInt("dynamicid");
			Dynamic.dao.findById(dynamicid).set("title", getPara("title")).set("content", getPara("content")).update();
			renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav9\", \"forwardUrl\":\"\", \"rel\":\"nav9\", \"callbackType\":\"closeCurrent\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
		}
	}
}
