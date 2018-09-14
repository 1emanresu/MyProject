package com.jsp.phonerecharge.service;

import java.util.Timer;
import java.util.TimerTask;

import com.vo.Onlineorder;

public class PhoneRechargeTimer extends TimerTask {
	private Timer myTimer = null;
	private String sporder_id = "";

	public PhoneRechargeTimer(String sporder_id,Timer myTimer){
		this.sporder_id = sporder_id;
		this.myTimer = myTimer;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Onlineorder order = Onlineorder.dao.findById(sporder_id);
		String ret_code = order.get("ret_code");
		if(!ret_code.equals("1")){
			if(!ret_code.equals("9")){
				order.set("ret_code", 9);
				order.set("err_msg", "订单失效");
				order.update();
			}
		}
		myTimer.cancel();
	}

}
