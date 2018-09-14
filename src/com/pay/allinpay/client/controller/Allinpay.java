package com.pay.allinpay.client.controller;

import com.jfinal.core.Controller;

public class Allinpay extends Controller {

	public void pickupUrl(){
		renderJsp("/WEB-INF/pay/allinpay/pickup.jsp");
	}
	
	public void receiveUrl(){
		renderJsp("/WEB-INF/pay/allinpay/receive.jsp");
	}
	
	public void paymentpickupUrl(){
		renderJsp("/WEB-INF/pay/allinpay/paymentpickup.jsp");
	}
	
	public void paymentreceiveUrl(){
		renderJsp("/WEB-INF/pay/allinpay/paymentreceive.jsp");
	}
}
