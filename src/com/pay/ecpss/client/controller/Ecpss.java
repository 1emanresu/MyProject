package com.pay.ecpss.client.controller;

import com.jfinal.core.Controller;

public class Ecpss extends Controller {
	public void ReturnURL(){
		renderJsp("/WEB-INF/pay/ecpss/return.jsp");
	}
	
	public void AdviceURL(){
		renderJsp("/WEB-INF/pay/ecpss/advice.jsp");
	}
	
	public void paymentReturnURL(){
		renderJsp("/WEB-INF/pay/ecpss/paymentreturn.jsp");
	}
	
	public void paymentAdviceURL(){
		renderJsp("/WEB-INF/pay/ecpss/paymentadvice.jsp");
	}
}
