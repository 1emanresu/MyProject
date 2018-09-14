package com.jsp.register.controller;

import java.util.Date;

import com.function.Yunsms;
import com.interceptor.SmsRegisterInterceptor;
import com.interceptor.UpdateInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jsp.register.service.RegisterService;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.vo.Customer;
import com.vo.Payment;
import com.vo.Person;

public class Register extends Controller {
	public void index(){
		renderJsp("/WEB-INF/register.jsp");
	}
	
	/**
	 * 注册
	 */
	@Before(UpdateInterceptor.class)
	public void enroll(){
		Person person = new Person();
		Payment pay = new Payment();
		person.set("email", getPara("email"));
		String password = getPara("password");
		try {
			person.set("password",MD5Utils.createMD5(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String payment = getPara("payment");
		try {
			pay.set("payment",MD5Utils.createMD5(payment));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person.set("webName",getPara("webName"));
		person.set("website",getPara("website"));
		person.set("herolist",getParaToInt("herolist"));
		person.set("huge",getParaToInt("huge"));
		int huge = getParaToInt("huge");
		person.set("name",getPara("name"));
		person.set("contacts",getPara("contacts"));
		String idcard = getPara("idcard");
		String licence = getPara("licence");
		if(huge==0){
			person.set("idcard",idcard);
		}else{
			person.set("idcard",licence);
		}
		person.set("address",getPara("address"));
		person.set("phone",getPara("phone"));
		person.set("qq",getPara("qq"));
		person.set("time", new Date());
		person.set("iflogin", 2);
		person.set("iflock", 1);
		person.set("ifnet", 2);
		String key = CryptTool.getPassword(32);
		pay.set("key", key);
		boolean res = RegisterService.service.enroll(person, pay);
		if(res){
			renderJson("{\"info\":\"注册成功！\",\"status\":\"y\"}");
		}else{
			renderJson("{\"info\":\"注册失败！\",\"status\":\"n\"}");
		}
	}
	
	public void SaveMsg(){
		Customer customer= new Customer();			
		String password = getPara("password");		
		try {
			customer.set("password",MD5Utils.createMD5(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.set("id", 12);
		customer.set("username", getPara("username"));
		customer.set("realname",getPara("realname"));
		customer.set("cardid",getPara("cardid"));		
		customer.set("address",getPara("address"));
		customer.set("telno",getPara("telno"));
		customer.set("time", new Date());
		customer.set("iflock", 1);		
		if(customer.save()){
			System.out.println("1111111111111111111111111111111=");
			renderJson("{\"info\":\"注册成功！\",\"status\":\"y\"}");
		}else{
			System.out.println("222222222222222222222222222222=");
			renderJson("{\"info\":\"注册失败！\",\"status\":\"n\"}");
		}
	}
	
	public void getEmail(){
		String email = getPara("param");
		boolean boo = RegisterService.service.getEmail(email);
		if(boo){
			renderText("y");
		}else{
			renderText("email已存在！");
		}
	}
	
	public void getPhote(){
		String phote = getPara("param");
		boolean boo = RegisterService.service.getPhote(phote);
		if(boo){
			renderText("y");
		}else{
			renderText("手机号已存在");
		}
	}
	
	public void getphotepass(){
		String phote = getPara("param");
		boolean boo = RegisterService.service.getPhotepass(phote);
		if(boo){
			renderText("y");
		}else{
			renderText("手机号不存在");
		}
	}
	
	@Before(UpdateInterceptor.class)
	public void upPassword(){
		try {
			String phone = getPara("phone");
			int id = RegisterService.service.getPerId(phone);
			Person person = Person.dao.findById(id);
			String password = getPara("password");
			person.set("password",MD5Utils.createMD5(password));
			boolean boo = person.update();
			if(boo){
				renderJson("{\"info\":\"更改成功！\",\"status\":\"y\"}");
			}else{
				renderJson("{\"info\":\"更改失败！\",\"status\":\"n\"}");
			}
		} catch (Exception e) {
			renderJson("{\"info\":\"更改失败！\",\"status\":\"n\"}");
			e.printStackTrace();
		}
	}
	
	@Before(SmsRegisterInterceptor.class)
	public void getSms(){
		String phone = getPara("phone");
		String key = CryptTool.getPasswordOfNumber(6);
		String content = "【聚优支付】:您的验证码是：【"+key+"】。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
		try {
			String res = Yunsms.sms(phone, content);
			if(res.equals("100")){
				setSessionAttr("sms", key);
				System.out.println(key);
				renderText(res);
			}else{
				renderText("短信获取失败，请联系工作人员");
			}
		} catch (Exception e) {
			renderText("短信获取失败，请联系工作人员");
			e.printStackTrace();
		}
	}
	
	public void valiSms(){
		String keys = getPara("param");
		String key = getSessionAttr("sms");
		if(keys.equals(key)){
			renderText("y");
		}else {
			renderText("验证码错误");
		}
	}
}
