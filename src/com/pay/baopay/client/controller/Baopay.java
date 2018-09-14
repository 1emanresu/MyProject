package com.pay.baopay.client.controller;

import com.jfinal.core.Controller;

public class Baopay extends Controller {
	
	public void post(){
		renderJsp("/WEB-INF/pay/baopay/post.jsp");
	}
	
	public void Merchant_url(){
		renderJsp("/WEB-INF/pay/baopay/merchant_url.jsp");
	}
	
	public void Return_url(){
		renderJsp("/WEB-INF/pay/baopay/return_url.jsp");
	}
	
	public void paymentbaomerchant(){
		renderJsp("/WEB-INF/pay/baopay/paymentmerchant_url.jsp");
	}
	
	public void paymentbaoreturn(){
		renderJsp("/WEB-INF/pay/baopay/paymentreturn_url.jsp");
	}
}
