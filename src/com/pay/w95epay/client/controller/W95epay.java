package com.pay.w95epay.client.controller;

import com.jfinal.core.Controller;
import com.pay.yeepay.client.service.YeepayService;

public class W95epay extends Controller {

	public void payresult(){
		renderJsp("/WEB-INF/pay/95epay/payresult.jsp");
	}
	
	public void paymentpayresult(){
		renderJsp("/WEB-INF/pay/95epay/paymentpayresult.jsp");
	}
	
	public void w95epayreturn() {
		try {
			String orderId = getPara("orderId");
			String path = getRequest().getContextPath();
			String basePath = getRequest().getScheme() + "://"
					+ getRequest().getServerName() + ":"
					+ getRequest().getServerPort() + path;
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					YeepayService.service.asynchronous(orderId);
				} else if (i == 1) {
					redirect(basePath + "/yeepay/yeeReturn?r6_Order=" + orderId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
