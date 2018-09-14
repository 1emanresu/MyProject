package com.pay.cib.client.dao;

import java.util.List;

import com.vo.Order;
import com.vo.Payment;

public class CibDao {
	public boolean setOrder(Order order){
		return order.save();
	}
	
	public List<Payment> eqPayment(String id){
		List list = Payment.dao.find("select * from payment p where p.id='"+id+"'");
		return list;
	}
}
