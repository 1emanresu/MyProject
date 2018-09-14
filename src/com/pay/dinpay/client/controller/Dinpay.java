package com.pay.dinpay.client.controller;

import com.jfinal.core.Controller;


public class Dinpay extends Controller {

	public void dinpayToMer(){
		renderJsp("/WEB-INF/pay/dinpay/DinpayToMer.jsp");
	}
	
	public void paymentDinpayToMer(){
		renderJsp("/WEB-INF/pay/dinpay/paymentDinpayToMer.jsp");
	}
}
