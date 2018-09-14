package com.jsp.login.controller;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jsp.login.service.LoginService;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.vo.Logrecord;
import com.vo.Person;

public class LoginController extends Controller {
	public void login() {
		boolean boo = false;
		String email = getPara("email");
		String password = getPara("password");
		try {
			boo = LoginService.loginService.login(email,
					MD5Utils.createMD5(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (boo) {
			Person per = LoginService.loginService.getPerson(email);
			setSessionAttr("session", per);
			Logrecord log = new Logrecord().set("id", per.get("id"));
			log.set("landingip", CryptTool.getIpAddr(getRequest()));
			log.set("landingtime", new Date());
			log.set("exittime", new Date());
			log.save();
			per.set("iflogin", 1);
			per.update();
			setSessionAttr("logrecord", log);
			renderJson("{\"info\":\"登陆成功！\",\"status\":\"y\"}");
		} else {
			renderJson("{\"info\":\"登陆失败！\",\"status\":\"n\"}");
		}
	}

	public void quit() {
		Logrecord log =  getSessionAttr("logrecord");
		log.set("exittime", new Date());
		log.update();
		Person per = getSessionAttr("session");
		per.set("iflogin", 2);
		per.update();
		removeSessionAttr("logrecord");
		removeSessionAttr("session");
		redirect("/");
	}
}
