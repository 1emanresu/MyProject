package com.pay.ips.client.controller;

import com.jfinal.core.Controller;

public class IpsController extends Controller {

	public void redirect(){
		renderJsp("/WEB-INF/pay/ips/redirect.jsp");
	}
	
	public void Merchanturl(){
		renderJsp("/WEB-INF/pay/ips/OrderReturn.jsp");
	}
	
	public void ServerUrl(){
		renderJsp("/WEB-INF/pay/ips/asynOrderReturn.jsp");
	}
	
	public void paymentMerchanturl(){
		renderJsp("/WEB-INF/pay/ips/paymentOrderReturn.jsp");
	}
	
	public void paymentServerUrl(){
		renderJsp("/WEB-INF/pay/ips/paymentasynOrderReturn.jsp");
	}
}
