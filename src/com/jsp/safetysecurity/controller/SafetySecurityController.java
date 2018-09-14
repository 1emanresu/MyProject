package com.jsp.safetysecurity.controller;

import com.jfinal.core.Controller;

public class SafetySecurityController extends Controller {
	
	public void index(){
		renderJsp("/WEB-INF/safetySecurity.jsp");
	}
}
