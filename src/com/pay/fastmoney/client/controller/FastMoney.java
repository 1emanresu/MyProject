package com.pay.fastmoney.client.controller;

import com.jfinal.core.Controller;
import com.pay.yeepay.client.service.YeepayService;

public class FastMoney extends Controller {

	public void callback() {
		renderJsp("/WEB-INF/pay/fastmoney/receive.jsp");
	}

	public void fastreturn() {
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
