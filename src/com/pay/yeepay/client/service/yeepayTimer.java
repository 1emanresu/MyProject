package com.pay.yeepay.client.service;

import java.util.Timer;
import java.util.TimerTask;

import com.vo.Order;

public class yeepayTimer extends TimerTask {
	private Timer myTimer = null;
	private String orderid = "";
	

	public yeepayTimer(String orderid,Timer myTimer){
		this.orderid = orderid;
		this.myTimer = myTimer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Order order = Order.dao.findById(orderid);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code!=1){
			if(r1_Code!=3){
				order.set("r1_Code", 4);
				order.update();
			}
		}
		myTimer.cancel();
	}
}
