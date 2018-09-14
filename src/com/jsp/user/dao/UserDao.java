package com.jsp.user.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.vo.Balance;
import com.vo.Logrecord;
import com.vo.Order;
import com.vo.Payment;
import com.vo.Person;
import com.vo.Rate;

public class UserDao {
	public List<Logrecord> getLogrecords(int id){
		return Logrecord.dao.find("SELECT * FROM logrecord l where l.id="+id+" ORDER BY l.exittime DESC");
	}
	
	public List<Balance> getBalance(int id){
		return Balance.dao.find("select * from balance b where b.id = "+id);
	}
	
	public Balance BalanceSettlement(int id){
		return Balance.dao.findById(id);
	}
	
	public List<Rate> getRate(int id){
		return Rate.dao.find("select * from rate r where r.id = "+id);
	}
	
	public List<Payment> getPaymList(int id){
		return Payment.dao.find("select p.id, p.key from payment p where p.id="+id);
	}
	
	public boolean getPaymentboo(int id, String payment){
		boolean boo = false;
		String str="select * from payment p where p.id="+id+" and p.payment='"+payment+"'";
		List<Payment> payList=  Payment.dao.find(str);
		if(payList.size()>0){
			boo = true;
		}
		System.out.println(boo);
		return boo;
	}
	
	public boolean getPasswordboo(int id, String password){
		boolean boo = false;
		List<Person> personList=  Person.dao.find("select p.email from person p where p.id="+id+" and p.password='"+password+"'");
		if(personList.size()>0){
			boo = true;
		}
		return boo;
	}
	
	public Long getOrdersCount(int p1_MerId){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String startime = ft.format(date);
		String endtime = ft.format(date.getTime() + 24 * 60 * 60 * 1000);
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT COUNT(*) from orders o where o.r1_Code = 1 and o.CreateTime >= '"+startime+"' and o.CreateTime < '"+endtime+"' ");
		if(p1_MerId!=0){
			sb.append(" and p1_MerId="+p1_MerId);
		}
		Long cou = Db.queryLong(sb.toString());
		return cou;
	}
	
	public Long getCardOrdersCount(int p1_MerId){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String startime = ft.format(date);
		String endtime = ft.format(date.getTime() + 24 * 60 * 60 * 1000);
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT COUNT(*) from cardorder o where o.r1_Code = 1 and o.CreateTime >= '"+startime+"' and o.CreateTime < '"+endtime+"' ");
		if(p1_MerId!=0){
			sb.append(" and p1_MerId="+p1_MerId);
		}
		
		Long cou = Db.queryLong(sb.toString());
		return cou;
	}
	
	public Long getCirclipCount(int p1_MerId){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String startime = ft.format(date);
		String endtime = ft.format(date.getTime() + 24 * 60 * 60 * 1000);
		//Long cou = Db.queryLong("SELECT COUNT(*) from circlip c where c.result=2000 and c.datetime >= '"+startime+"' and c.datetime < '"+endtime+"'");
		//return cou;
		
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT COUNT(*) from cardorder o where o.r1_Code = 1 and o.CreateTime >= '"+startime+"' and o.CreateTime < '"+endtime+"' ");
		if(p1_MerId!=0){
			sb.append(" and p1_MerId="+p1_MerId);
		}
		
		Long cou = Db.queryLong(sb.toString());
		return cou;
	}
	
	public Long getRechargeCount(int p1_MerId){
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String startime = ft.format(date);
		String endtime = ft.format(date.getTime() + 24 * 60 * 60 * 1000);
		//Long cou = Db.queryLong("SELECT COUNT(*) FROM recharge r where r.r1_Code=1 and r.CreateTime >= '"+startime+"' and r.CreateTime < '"+endtime+"'");
		//return cou;
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT COUNT(*) from cardorder o where o.r1_Code = 1 and o.CreateTime >= '"+startime+"' and o.CreateTime < '"+endtime+"' ");
		if(p1_MerId!=0){
			sb.append(" and p1_MerId="+p1_MerId);
		}
		
		Long cou = Db.queryLong(sb.toString());
		return cou;
	}
	
	public List<Order> getResultALI(int id,String starttime,String endtime){
		String sql = "select ru_Trxtime,sum(p3_Amt) as amt from orders where  orderid like '%ALI%' and r1_Code=1 and p1_MerId="+ id;
		if (!starttime.equals("")) {
			sql += " and ru_Trxtime> '" + starttime + "' ";
		}
		if (!endtime.equals("")) {
			sql += " and  ru_Trxtime<'" + endtime + "'";
		}
		sql += " GROUP BY ru_Trxtime desc";
		return Order.dao.find(sql);
	}
	
	public List<Order> getResultWX(int id,String starttime,String endtime){
		String sql = "select ru_Trxtime,sum(p3_Amt) as amt from orders where  orderid like '%WX%' and r1_Code=1 and p1_MerId="+ id;
		if (!starttime.equals("")) {
			sql += " and ru_Trxtime> '" + starttime + "' ";
		}
		if (!endtime.equals("")) {
			sql += " and  ru_Trxtime<'" + endtime + "'";
		}
		sql += " GROUP BY ru_Trxtime desc";
		return Order.dao.find(sql);
	}
	
	
	/**
	 * 获取下级的代理用户
	 * 2017-10-24  : 添加了一个superior 字段
	 * @param level  下级的  级别， 默认请写1
	 */
	public List<Person> getAll( int superior,int level) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT p.*, e.name AS empname");
		frosql.append(" from person p");
		frosql.append(" LEFT JOIN employee_person ep");
		frosql.append(" ON p.id = ep.id");
		frosql.append(" LEFT JOIN employee e");
		frosql.append(" ON ep.employeeid = e.employeeid");
		frosql.append(" where 1=1 ");
		
		if (superior!=0) {
			frosql.append(" and p.superior=" + superior);
		}
		frosql.append(" ORDER BY p.time DESC");
		
		//System.out.println("sql:"+selsql+frosql);
		List<Person>  list = Person.dao.find(selsql.toString() + frosql.toString());
		
		int listSize=list.size();
		
		for (int i = 0; i < listSize; i++) {	
			list.get(i).put("level",level);
			
			
			if(list.get(i).getInt("isAgent")==1){
				//如果是代理用户      递归查询出  下级的集合数据   并将其添加到集合中
				List<Person>  childs=getAll(list.get(i).getInt("id"),level+1);
				if(childs!=null){
					for (Person p: childs) {
						list.add(p);
					}
				}
			}
		}
		
		if(list.size()==0){
			return null;
		}
		return list;
	}
}
