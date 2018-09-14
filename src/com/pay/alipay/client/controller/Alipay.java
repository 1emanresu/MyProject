package com.pay.alipay.client.controller;

import com.jfinal.core.Controller;

public class Alipay extends Controller {
	
	public void notify_url(){
		renderJsp("/WEB-INF/pay/alipay/notify_url.jsp");
	}
	
	public void return_url(){
		renderJsp("/WEB-INF/pay/alipay/return_url.jsp");
	}
	
	public void paymentnotify_url(){
		renderJsp("/WEB-INF/pay/alipay/paymentnotify_url.jsp");
	}
	
	public void paymentreturn_url(){
		renderJsp("/WEB-INF/pay/alipay/paymentreturn_url.jsp");
	}
}
