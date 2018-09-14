package com.pay.ease.client.controller;

import com.jfinal.core.Controller;

public class Ease extends Controller {
	
	public void received1(){
		renderJsp("/WEB-INF/pay/ease/received1.jsp");
	}
	
	public void received2(){
		renderJsp("/WEB-INF/pay/ease/received2.jsp");
	}
	
	public void paymentreceived1(){
		renderJsp("/WEB-INF/pay/ease/paymentreceived1.jsp");
	}
}
