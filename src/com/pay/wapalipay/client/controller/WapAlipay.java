package com.pay.wapalipay.client.controller;

import com.jfinal.core.Controller;

public class WapAlipay extends Controller {

	public void notify_url(){
		renderJsp("/WEB-INF/pay/wapalipay/notify_url.jsp");
	}
	
	public void call_back_url(){
		renderJsp("/WEB-INF/pay/wapalipay/call_back_url.jsp");
	}
	
	public void paymentnotify_url(){
		renderJsp("/WEB-INF/pay/wapalipay/paymentnotify_url.jsp");
	}
	
	public void paymentcall_back_url(){
		renderJsp("/WEB-INF/pay/wapalipay/paymentcall_back_url.jsp");
	}
}
