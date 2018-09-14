package com.manager.rolemanager.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.manager.rolemanager.service.RoleService;
import com.vo.Role;
import com.vo.Roleperm;

public class RoleController extends Controller {

	public void index() {
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String rolename = "";
		if (!getPara("rolename").equals("")) {
			rolename = getPara("rolename");
		}
		System.out.println(rolename);
		setAttr("rolename", rolename);
		Page<Record> rolePage = RoleService.roleservice.rolePage(pageNum, numPerPage, rolename);
		setAttr("pageNum", rolePage.getPageNumber());
		setAttr("numPerPage", rolePage.getPageSize());
		setAttr("totalCount", rolePage.getTotalRow());
		setAttr("pageNumShown", rolePage.getTotalPage());
		setAttr("rolelist", rolePage.getList());
		renderJsp("/WEB-INF/admin/jsp/rolemanager.jsp");
	}

	public void showAddRole() {
		renderJsp("/WEB-INF/admin/jsp/manager/addrole.jsp");
	}

	public void addRole() {
		String role_id = getPara("roleid");
		String role_name = getPara("rolename");
		String depict = getPara("depict");
		if (role_name.equals("") && role_id.equals("")) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"请填写编号或角色名称\"}");
		} else {
			try {
				Role role = new Role();
				role.set("role_id", role_id);
				role.set("role_name", role_name);
				role.set("role_depict", depict);
				role.save();
				renderJson(
						"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav41\", \"forwardUrl\":\"\", \"rel\":\"nav41\", \"callbackType\":\"closeCurrent\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void delRole() {
		try {
			int roleid = getParaToInt("roleid");
			Role.dao.deleteById(roleid);
			Db.update("DELETE FROM role WHERE role_id=" + roleid);
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav41\", \"forwardUrl\":\"\", \"rel\":\"nav41\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
			e.printStackTrace();
		}
	}

	public void showRolePerm() {
		try {
			int roleid = getParaToInt("roleid");
			List<Record> navlist = RoleService.roleservice.getRolePerm(roleid);
			setAttr("navlist", navlist);
			setAttr("roleid", roleid);
			renderJsp("/WEB-INF/admin/jsp/manager/role_private.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upRolePermNav() {
		try {
			String[] rolejson = getParaValues("rolejson");
			int roleid = getParaToInt("roleid");
			Db.update("DELETE FROM role_perm WHERE role_id=" + roleid);
			Roleperm rolenav = null;
			for (String role : rolejson) {
				rolenav = new Roleperm();
				rolenav.set("role_id", roleid);
				rolenav.set("navid", role);
				rolenav.save();	
			}
			renderJson(
					"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav15\", \"forwardUrl\":\"\", \"rel\":\"nav15\", \"callbackType\":\"closeCurrent\"}");
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"301\", \"message\":\"系统错误\"}");
			e.printStackTrace();
		}

	}
}
