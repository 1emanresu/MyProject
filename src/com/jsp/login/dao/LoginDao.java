package com.jsp.login.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vo.Person;

public class LoginDao {
	public boolean login(String email, String password){
		boolean boo = false;
		
		String regEx="[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
		Pattern p = Pattern.compile(regEx); 
		

		Matcher m = p.matcher(email);
		email = m.replaceAll("").trim();
		Matcher mp = p.matcher(password);
		password = mp.replaceAll("").trim();
		
		String sql = "select p.email from person p where p.email = '"+email+"' and p.password = '"+password+"' and p.iflock = 1";
		
		List<Person> persons = Person.dao.find(sql);
		if(persons.size()>0){
			boo = true;
		}
		return boo;
	}
	
	public boolean validate(int id){
		boolean boo = false;
		List<Person> persons = Person.dao.find("select p.id from person p where p.id = "+id+" and p.iflock = 1");
		if(persons.size()>0){
			boo = true;
		}
		return boo;
	}
	
	public Person getPerson(String email){
		List<Person> persons = Person.dao.find("select p.address, p.contacts, p.email, p.herolist, p.huge, p.id, p.idcard, p.name, p.phone, p.qq, p.webName, p.website,p.permission,p.superior,p.isAgent from person p where p.email = '"+email+"'");
		return persons.get(0);
	}
	
	public boolean Logrecord(com.vo.Logrecord log){
		boolean boo = log.save();
		return boo;
	}
}
