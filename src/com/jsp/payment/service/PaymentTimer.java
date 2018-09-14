package com.jsp.payment.service;

import java.util.Timer;
import java.util.TimerTask;

import com.vo.Recharge;

public class PaymentTimer extends TimerTask {
	private Timer myTimer = null;
	private String rechargeid = "";

	public PaymentTimer(String rechargeid,Timer myTimer){
		this.rechargeid = rechargeid;
		this.myTimer = myTimer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Recharge order = Recharge.dao.findById(rechargeid);
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
