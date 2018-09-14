package com.jsp.indexcard.controller;

import com.jfinal.core.Controller;

public class IndexCardController extends Controller {

	public void index(){
		renderJsp("/WEB-INF/card.jsp");
	}
}
