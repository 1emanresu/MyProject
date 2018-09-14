package com.vo;
import com.jfinal.plugin.activerecord.Model;
public class Customer extends Model<Customer>{
	
	public static final Customer dao = new Customer();
	
	private int id;
	private String username;
	private String password;
	private String realname;	
	private String address;
	private String telno;
	private String cardid;
	private String time;
	private int ifrock;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public int getIfrock() {
		return ifrock;
	}
	public void setIfrock(int ifrock) {
		this.ifrock = ifrock;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
