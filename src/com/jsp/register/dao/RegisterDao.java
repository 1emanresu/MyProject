package com.jsp.register.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.vo.Balance;
import com.vo.Payment;
import com.vo.Person;
import com.vo.PersonGateway;
import com.vo.Rate;

public class RegisterDao {
	public boolean enroll(Person person, Payment payment){
		boolean res = person.save();
		int id = person.get("id");
		payment.set("id", id);
		Balance balance = new Balance();
		balance.set("id", id);
		balance.set("amount", 0);
		balance.set("cardamount", 0);
		balance.set("deductedcard", 0);
		balance.set("netamount", 0);
		balance.set("deductednet", 0);
		balance.set("waitamount", 0);
		balance.set("newamount", 0);
		balance.set("settlement", 0);
		balance.set("settlementauthority", 2);
		balance.set("cashleast", 100);
		balance.set("refundamout", 0);
		balance.save();
		Rate rate = new Rate();
		rate.set("id", id);
		rate.set("banking", 0.97);
		
		//给下级代理设置利率
		if(person.get("superior")!=null&&person.getInt("superior")!=0){
			Rate r=Rate.dao.findById(person.getInt("superior"));
			if(r!=null){
				rate.set("banking", r.getDouble("banking")-0.001);
				rate.set("cibsm", r.getDouble("cibsm")-0.001);
				rate.set("mustali", r.getDouble("mustali")-0.001);
				rate.set("qqwx", r.getDouble("qqwx")-0.001);
			}
		}
		/*rate.set("netease", 0.86);
		rate.set("travel", 0.81);
		rate.set("qqcoins", 0.82);
		rate.set("journey", 0.85);
		rate.set("grand", 0.865);
		rate.set("theworld", 0.85);
		rate.set("perfect", 0.85);
		rate.set("chinaunicom", 0.95);
		rate.set("tianhong", 0.84);
		rate.set("sohu", 0.85);
		rate.set("junwang", 0.855);
		rate.set("shenzhouxing", 0.965);
		rate.set("telecom", 0.96);
		rate.set("longitudinal", 0.85);*/
		rate.set("refund", 2);
		rate.save();
		PersonGateway pergate = new PersonGateway();
		pergate.set("id", id);
		pergate.set("gateway_id", 7);
		pergate.set("paygete_id1", 7);
		pergate.set("paygete_id2", 7);
		pergate.set("paygete_id3", 7);
		pergate.set("paygete_id4", 7);
		pergate.set("paygete_id5", 7);
		pergate.save();
		boolean ret = payment.save();
		boolean boo = false;
		if(res&&ret){
			boo = true;
		}
		return boo;
	}
	
	public boolean getEmail(String email){
		boolean boo = true;
		List<Person> list = Person.dao.find("select p.email from person p where p.email='"+email+"'");
		if(list.size()>0){
			boo = false;
		}
		return boo;
	}
	
	public boolean getPhote(String phote){
		boolean boo = true;
		List<Person> list = Person.dao.find("select p.phone from person p where p.phone='"+phote+"'");
		if(list.size()>0){
			boo = false;
		}
		return boo;
	}
	
	public int getPerId(String phote){
		int id = Db.queryInt("select p.id from person p where p.phone='"+phote+"'");
		return id;
	}
	
	public boolean getPhotepass(String phote){
		boolean boo = false;
		List<Person> list = Person.dao.find("select p.phone from person p where p.phone='"+phote+"'");
		if(list.size()>0){
			boo = true;
		}
		return boo;
	}
	
	public boolean getId(int id){
		boolean boo = false;
		List<Person> list = Person.dao.find("select p.id from person p where p.id='"+id+"'");
		if(list.size()>0){
			boo = true;
		}
		return boo;
	}
}
