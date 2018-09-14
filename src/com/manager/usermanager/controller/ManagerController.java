package com.manager.usermanager.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.manager.usermanager.service.ManagerService;
import com.vo.Employees;
import com.vo.Userperm;

public class ManagerController extends Controller {

	public void index() {
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String account = "";
		if (!getPara("account").equals("")) {
			account = getPara("account");
		}
		setAttr("account", account);
		Page<Record> userPage = ManagerService.managerService.getUserPage(pageNum, numPerPage, account);
		setAttr("pageNum", userPage.getPageNumber());
		setAttr("numPerPage", userPage.getPageSize());
		setAttr("totalCount", userPage.getTotalRow());
		setAttr("pageNumShown", userPage.getTotalPage());
		setAttr("emplist", userPage.getList());
		renderJsp("/WEB-INF/admin/jsp/usermanager.jsp");
	}

	public void delUser() {
		try {
			int employeeid = getParaToInt("employeeid");
			Employees.dao.deleteById(employeeid);
			Db.update("delete from employee  where employeeid=" + employeeid);
			Db.update("delete from role_user where employeeid=" + employeeid);
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav40\", \"forwardUrl\":\"\", \"rel\":\"nav40\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void findUserRolename() {
		try {
			int employeeid = getParaToInt("employeeid");
			List<Record> Rolename = ManagerService.managerService.getRolename(employeeid);
			setAttr("Rolename", Rolename);
			renderJsp("/WEB-INF/admin/jsp/manager/deleteuserrole.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delUserRolename() {
		try {
			int roleid = getParaToInt("roleid");
			int employeeid = getParaToInt("employeeid");
			Db.update("delete from role_user where role_id=" + roleid + " and employeeid= " + employeeid);
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav40\", \"forwardUrl\":\"\", \"rel\":\"nav40\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void findRolename() {
		try {
			int employeeid = getParaToInt("employeeid");
			List<Record> Rolename = ManagerService.managerService.gethaveRolename(employeeid);
			List<Record> Roles = ManagerService.managerService.getnullRolename(employeeid);
			setAttr("Roles", Roles);
			setAttr("Rolename", Rolename);
			setAttr("employeeid", employeeid);
			renderJsp("/WEB-INF/admin/jsp/manager/addUserRolename.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUserRolename() {
		try {
			int roleid = getParaToInt("roleid");
			int employeeid = getParaToInt("employeeid");
			Db.update("insert into role_user value(" + employeeid + "," + roleid + ")");
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav40\", \"forwardUrl\":\"\", \"rel\":\"nav40\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
		}
	}

	public void showEmpPerm() {
		try {
			int employeeid = getParaToInt("employeeid");
			List<Record> navlist = ManagerService.managerService.getUserPerm(employeeid);
			setAttr("navlist", navlist);
			setAttr("employeeid", employeeid);
			renderJsp("/WEB-INF/admin/jsp/manager/user_private.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upEmpPermNav() {
		try {
			String[] empjson = getParaValues("empjson");
			int employeeid = getParaToInt("employeeid");
			Db.update("DELETE FROM user_perm WHERE employee_id="+employeeid);
			Userperm usernav = null;
			for (String emp : empjson) {
				usernav = new Userperm();
				usernav.set("employee_id", employeeid);
				usernav.set("navid", emp);
				usernav.save();
			}
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav15\", \"forwardUrl\":\"\", \"rel\":\"nav15\", \"callbackType\":\"closeCurrent\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"系统错误\"}");
			e.printStackTrace();
		}

	}
}
