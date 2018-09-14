package com.admin.login.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vo.Employees;
import com.vo.Navigation;
public class AdLoginDao {
	public boolean login(String account, String password) {
		boolean boo = false;
		// password="2286793b6c13369cee83cb285237ef95";
		
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(account);
		account = m.replaceAll("").trim();
		Matcher mp = p.matcher(password);
		password = mp.replaceAll("").trim();
		
		String sql = "select p.account from employee p where p.account = '" + account
				+ "' and p.password = '" + password + "' and p.iflock=1";
		
		
		List<Employees> employees = Employees.dao.find(sql);
		if (employees.size() > 0) {
			boo = true;
		}
		return boo;
	}

	public Employees getEmployees(String account) {
		List<Employees> employees = Employees.dao
				.find("select e.employeeid, e.powerid, e.phone, e.address, e.creationtime, e.email, e.account, e.iflock, e.name from employee e where e.account='"
						+ account + "'");
		return employees.get(0);
	}

	public List<Navigation> getUserprem(String account) {
		List<Navigation> userprem = Navigation.dao
				.find("select u.navid  from  user_perm u LEFT JOIN employee as e on e.employeeid=u.employee_id where e.account='"
						+ account + "'");
		List<Navigation> roleprem = Navigation.dao
				.find("select p.navid  from role_perm as p LEFT JOIN role_user as r on p.role_id=r.role_id LEFT JOIN employee as e on r.employeeid=e.employeeid  where e.account='"
						+ account + "'");
		userprem.addAll(roleprem);
		userprem=new ArrayList<Navigation>(new LinkedHashSet<>(userprem));
		return userprem;
	}

}
