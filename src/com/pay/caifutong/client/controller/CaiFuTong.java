package com.pay.caifutong.client.controller;

import com.jfinal.core.Controller;
import com.pay.yeepay.client.service.YeepayService;

public class CaiFuTong extends Controller {
	
	public void splitPayRequest(){
		renderJsp("/WEB-INF/pay/caifutong/splitPayRequest.jsp");
	}

	public void splitPayResponse(){
		renderJsp("/WEB-INF/pay/caifutong/splitPayResponse.jsp");
	}
	
	public void paymentsplitPayResponse(){
		renderJsp("/WEB-INF/pay/caifutong/paymentsplitpayResponse.jsp");
	}
	
	public void clientSplitRollback(){
		renderJsp("/WEB-INF/pay/caifutong/clientSplitRollback.jsp");
	}
	
	public void caifureturn() {
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
