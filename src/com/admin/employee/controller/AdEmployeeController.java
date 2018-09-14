package com.admin.employee.controller;

import java.util.Date;
import java.util.List;

import com.admin.employee.service.AdEmployeeService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.MD5Utils;
import com.tool.StringUtil;
import com.vo.Employees;
import com.vo.Power;

@Before(AdLoginSeInterceptor.class)
public class AdEmployeeController extends Controller {
	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String powerid = "";
		String starttime = "";
		String endtime = "";
		String iflock = "";
		String employeeid = "";
		if(StringUtil.isNotEmpty(getPara("powerid")) && !getPara("powerid").equals("0")){
			powerid = getPara("powerid");
		}
		if(!getPara("starttime").equals("")){
			starttime = getPara("starttime");
		}
		if(!getPara("endtime").equals("")){
			endtime = getPara("endtime");
		}
		if(!getPara("employeeid").equals("")){
			employeeid = getPara("employeeid");
		}
		if(StringUtil.isNotEmpty(getPara("iflock")) && !getPara("iflock").equals("0")){
			iflock = getPara("iflock");
		}
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("iflock", iflock);
		Page<Record> empPage = AdEmployeeService.service.getRechargePage(pageNum, numPerPage, starttime, endtime, iflock, employeeid, powerid);
		setAttr("pageNum", empPage.getPageNumber());
		setAttr("numPerPage", empPage.getPageSize());
		setAttr("totalCount", empPage.getTotalRow());
		setAttr("pageNumShown", empPage.getTotalPage());
		setAttr("emplist", empPage.getList());
		setAttr("powerlist", AdEmployeeService.service.getPowerlist());
		renderJsp("/WEB-INF/admin/jsp/employee.jsp");
	}
	
	public void showEmployee(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String powerid = "";
		String starttime = "";
		String endtime = "";
		String iflock = "";
		String employeeid = "";
		if(StringUtil.isNotEmpty(getPara("powerid")) && !getPara("powerid").equals("0")){
			powerid = getPara("powerid");
			
		}
		if(!getPara("starttime").equals("")){
			starttime = getPara("starttime");
		}
		if(!getPara("endtime").equals("")){
			endtime = getPara("endtime");
		}
		if(!getPara("employeeid").equals("")){
			employeeid = getPara("employeeid");
		}
		if(StringUtil.isNotEmpty(getPara("iflock")) && !getPara("iflock").equals("0")){
			iflock = getPara("iflock");
		}
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("iflock", iflock);
		Page<Record> empPage = AdEmployeeService.service.getRechargePage(pageNum, numPerPage, starttime, endtime, iflock, employeeid, powerid);
		setAttr("pageNum", empPage.getPageNumber());
		setAttr("numPerPage", empPage.getPageSize());
		setAttr("totalCount", empPage.getTotalRow());
		setAttr("pageNumShown", empPage.getTotalPage());
		setAttr("emplist", empPage.getList());
		renderJsp("/WEB-INF/admin/jsp/employee/showEmployee.jsp");
	}
	
	public void showAddEmp(){
		renderJsp("/WEB-INF/admin/jsp/employee/addEmployee.jsp");
	}
	
	public void getPowerjson(){
		List<Power> powerlist = AdEmployeeService.service.getPowerlist();
		String json = JsonKit.toJson(powerlist, 2);
		renderJson(json);
	}
	
	public void addEmployee(){
		String account = getPara("account");
		String name = getPara("name");
		String password = getPara("password");
		String phone = getPara("phone");
		String address = getPara("address");
		String email = getPara("email");
		boolean boo = AdEmployeeService.service.ifAccount(account);
		if(boo){
			renderJson("{\"statusCode\":\"300\", \"message\":\"账号已存在请重新填写！\"}");
		}else{
			try {
				Employees employee = new Employees();
				employee.set("password", MD5Utils.createMD5(password));
				employee.set("phone", phone);
				employee.set("address", address);
				employee.set("creationtime", new Date());
				employee.set("email", email);
				employee.set("account", account);
				employee.set("name", name);
				employee.set("haveroyalty", 0);
				employee.set("iflock", 1);
				employee.save();
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\"}");
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
				e.printStackTrace();
			}
		}
	}
	
	public void delEmployee(){
		try {
			int empid = getParaToInt("empid");
			boolean boo = Employees.dao.deleteById(empid);
			if(boo){
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav14\", \"forwardUrl\":\"\", \"rel\":\"nav14\"}");
			}else{
				renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			e.printStackTrace();
		}
	}
	
	public void showEmp(){
		int empid = getParaToInt("empid");
		Employees emp = Employees.dao.findById(empid);
		setAttr("emp", emp);
		setAttr("pow", Power.dao.findById(emp.getInt("powerid")));
		setAttr("empid", empid);
		renderJsp("/WEB-INF/admin/jsp/employee/showEmp.jsp");
	}
	
	public void upEmp(){
		try {
			int empid = getParaToInt("empid");
			String powerid = getPara("power.powerid");
			String account = getPara("account");
			String name = getPara("name");
			String phone = getPara("phone");
			String address = getPara("address");
			String email = getPara("email");
			String commission = getPara("commission");
			int iflock = getParaToInt("iflock");
			Employees employee =  Employees.dao.findById(empid);
			employee.set("powerid", powerid);
			employee.set("phone", phone);
			employee.set("address", address);
			employee.set("email", email);
			employee.set("account", account);
			employee.set("name", name);
			employee.set("commission", commission);
			employee.set("iflock", iflock);
			boolean boo = employee.update();
			if(boo){
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav14\", \"forwardUrl\":\"\", \"rel\":\"nav14\", \"callbackType\":\"closeCurrent\"}");
			}else{
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			e.printStackTrace();
		}
	}
	
	public void showEmpPass(){
		int empid = getParaToInt("empid");
		setAttr("empid", empid);
		renderJsp("/WEB-INF/admin/jsp/employee/showEmpPass.jsp");
	}
	
	public void upEmpPass(){
		try {
			int empid = getParaToInt("empid");
			boolean boo = Employees.dao.findById(empid).set("password", MD5Utils.createMD5(getPara("password"))).update();
			if(boo){
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav14\", \"forwardUrl\":\"\", \"rel\":\"nav14\", \"callbackType\":\"closeCurrent\"}");
			}else{
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			e.printStackTrace();
		}
	}
	
	public void showPerEmp(){
		try {
			int empid = getParaToInt("empid");
			int pageNum = getParaToInt("pageNum");
			int numPerPage = getParaToInt("numPerPage");
			String pername = getPara("pername");
			setAttr("pername", pername);
			setAttr("empid", empid);
			Page<Record> orderpage = AdEmployeeService.service.getPerPage(pageNum,numPerPage,pername,empid);
			setAttr("pageNum", orderpage.getPageNumber());
			setAttr("numPerPage", orderpage.getPageSize());
			setAttr("totalCount", orderpage.getTotalRow());
			setAttr("pageNumShown", orderpage.getTotalPage());
			setAttr("orderlist", orderpage.getList());
			renderJsp("/WEB-INF/admin/jsp/employee/showPerEmp.jsp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
