package com.vo;

import com.jfinal.plugin.activerecord.Model;

public class AccountList extends Model<AccountList>{
	
	private static final long serialVersionUID = 100001L;
	public static final AccountList dao = new AccountList();
	
	private int id;
	private String user_id;
	private String account;
	private String privatekey;
	private String mch_key;
	private String mark;	
	private long rec_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPrivatekey() {
		return privatekey;
	}
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}
	public String getMch_key() {
		return mch_key;
	}
	public void setMch_key(String mch_key) {
		this.mch_key = mch_key;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public long getRec_date() {
		return rec_date;
	}
	public void setRec_date(long rec_date) {
		this.rec_date = rec_date;
	}
}
