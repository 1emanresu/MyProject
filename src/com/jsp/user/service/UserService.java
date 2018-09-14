package com.jsp.user.service;

import java.text.DecimalFormat;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.user.dao.UserDao;
import com.vo.Person;
import com.vo.Rate;

public class UserService extends UserDao {
	public static UserService userService = new UserService();
	
	/**
	 * 根据下级用户的id  和自身获取收益该  下级用户每一单的相关收益率
	 * @param id  下级用户id
	 * @param person	session中保存的用户
	 * @return  该用户对自己的收益
	 */
	public Rate getIncome(int id,Person person){
		Rate income=new Rate();
		int child=id;
	
		String sql="SELECT id ,superior, paths FROM ( "
				+ "SELECT id,superior,  "
				+ "@pathnodes:= IF( superior =0,',0', "
				+ "CONCAT_WS(',', "
				+ "IF( LOCATE( CONCAT('|',superior,':'),@pathall) > 0  , "
				+ "SUBSTRING_INDEX( SUBSTRING_INDEX(@pathall,CONCAT('|',superior,':'),-1),'|',1)  "
				+ ",@pathnodes ) ,superior  ) )paths  "
				+ ",@pathall:=CONCAT(@pathall,'|',id,':', @pathnodes ,'|') pathall  "
				+ "FROM  person,  "
				+ "(SELECT   @pathall:='',@pathnodes:='') vv "
				+ " ORDER BY  superior,id "
				+ " ) src where id="+id
				+ " ORDER BY id";
		
		
		
		List<Record> list= Db.find(sql);
		if(list.size()!=0&&list.get(0)!=null){
			
			String path=list.get(0).get("paths");
			//System.out.println("path:"+path);
			
			String str=","+person.get("id");
			int index=path.indexOf(str);
			
			if(path.length()>str.length()+index){
				path=path.substring(index+str.length()+1);
				if(path.indexOf(",")!=-1){
					path=path.substring(0,path.indexOf(","));
				}
				child=Integer.parseInt(path);
			}
			
		}
		
		Rate childRate=Rate.dao.findById(child);
		Rate personRate=Rate.dao.findById(person.getInt("id"));
		
		DecimalFormat df = new DecimalFormat("#.0000");
		income.set("banking",df.format(personRate.getDouble("banking")-childRate.getDouble("banking")));
		income.set("cibsm",df.format(personRate.getDouble("cibsm")-childRate.getDouble("cibsm")));
		income.set("mustali",df.format(personRate.getDouble("mustali")-childRate.getDouble("mustali")));
		income.set("qqwx",df.format(personRate.getDouble("qqwx")-childRate.getDouble("qqwx")));
		
		return income;
	}
	
}
