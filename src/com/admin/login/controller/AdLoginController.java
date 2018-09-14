package com.admin.login.controller;

import java.util.ArrayList;
import java.util.List;

import com.admin.login.service.AdLoginService;
import com.jfinal.core.Controller;
import com.pay.yeepay.server.Configuration;
import com.tool.Backstage;
import com.tool.MD5Utils;
import com.vo.Employees;
import com.vo.Navigation;
public class AdLoginController extends Controller {
	public void login(){
		boolean boo = false;
		String account = getPara("account");
		String password = getPara("password");
		try {
			boo = AdLoginService.service.login(account,MD5Utils.createMD5(password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (boo) {
			Employees employees = AdLoginService.service.getEmployees(account);
			List<Navigation> privates = AdLoginService.service.getUserprem(account);
			List<Integer>  navid = new ArrayList<>();
			for (Navigation navids : privates) {	
				navid.add((Integer) navids.get("navid"));
			}
			setSessionAttr("privatesession", navid);
			setSessionAttr("adminsession", employees);
			String path = getRequest().getContextPath();
			String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path;
			new Backstage().setEmpurl(basePath);
			renderJson("{\"info\":\"登陆成功！\",\"status\":\"y\"}");
		} else {
			renderJson("{\"info\":\"登陆失败！\",\"status\":\"n\"}");
		}
		
	}
	
	public void exit(){
		String adminurl = Configuration.getInstance().getValue("adminurl");
		removeSessionAttr("adminsession");
		redirect(adminurl);
	}
	
	public void adminlogin(){	
		String adminurl = Configuration.getInstance().getValue("adminurl");
		String account = getPara("account");
		String password = getPara("password");
	    boolean boo = false;
		boo = AdLoginService.service.login(account, password);
		if(boo){
			Employees employees = AdLoginService.service.getEmployees(account);
			setSessionAttr("adminsession", employees);
			redirect(adminurl);
		}
	}
}
