package com.jsp.statement.controller;

import java.util.List;

import com.interceptor.LoginInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jsp.statement.service.StatementService;
import com.vo.Balance;
import com.vo.Person;

@Before(LoginInterceptor.class)
public class StatementController extends Controller {
	public void index(){
		Person per = getSessionAttr("session");
		List<Balance> balist = StatementService.service.getBalance(per.getInt("id"));
		setAttr("netamount", balist.get(0).get("netamount"));
		setAttr("cardamount", balist.get(0).get("cardamount"));
		renderJsp("/WEB-INF/jsp/statement1.jsp");
	}
}
