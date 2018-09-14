package com.vo;

import com.jfinal.plugin.activerecord.Model;

public class Payment extends Model<Payment> {
	public static final Payment dao = new Payment();
	
	private int id;
	private String payment;
	private String key;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
