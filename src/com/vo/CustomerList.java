package com.vo;
import java.math.BigDecimal;

import com.jfinal.plugin.activerecord.Model;

public class CustomerList extends Model<CustomerList>{

	private static final long serialVersionUID = 1L;
	public static final CustomerList dao = new CustomerList();
	
	private int id;
	private int user_id;
	private String orderid;
	private String cardid;
	private BigDecimal price;
	private String realname;	
	private String img_cardid;
	private String img_orderid;
	private String title;
	private String content;	
	private String result_user;
	private String result_sys;
	private String status_customer;
	private String status_manager;
	private String status_leader;
	private String record_date;
	private String result_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getImg_cardid() {
		return img_cardid;
	}
	public void setImg_cardid(String img_cardid) {
		this.img_cardid = img_cardid;
	}
	public String getImg_orderid() {
		return img_orderid;
	}
	public void setImg_orderid(String img_orderid) {
		this.img_orderid = img_orderid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult_user() {
		return result_user;
	}
	public void setResult_user(String result_user) {
		this.result_user = result_user;
	}
	public String getResult_sys() {
		return result_sys;
	}
	public void setResult_sys(String result_sys) {
		this.result_sys = result_sys;
	}
	public String getStatus_customer() {
		return status_customer;
	}
	public void setStatus_customer(String status_customer) {
		this.status_customer = status_customer;
	}
	public String getStatus_manager() {
		return status_manager;
	}
	public void setStatus_manager(String status_manager) {
		this.status_manager = status_manager;
	}
	public String getStatus_leader() {
		return status_leader;
	}
	public void setStatus_leader(String status_leader) {
		this.status_leader = status_leader;
	}
	public String getRecord_date() {
		return record_date;
	}
	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}
	public String getResult_date() {
		return result_date;
	}
	public void setResult_date(String result_date) {
		this.result_date = result_date;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
