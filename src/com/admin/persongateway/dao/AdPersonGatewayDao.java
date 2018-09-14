package com.admin.persongateway.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.PersonGateway;

public class AdPersonGatewayDao {
	public List<PersonGateway> getPersonGateway() {
		try {
			List<PersonGateway> list = PersonGateway.dao.find("select pg.id,pg.title from person_gateway pg");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Page<Record> getPersonGateway20171010(int pageNum,int pageSize,String id) {
			String sql="SELECT pg.id,pg.title,r.`banking`,r.`refund`,r.`cibsm`,r.`mustali`,r.`qqwx`  ";
			String from =" FROM person_gateway pg JOIN rate r ON pg.`id`=r.id where 1=1 ";
			
			if(StringUtil.isNotEmpty(id)){
				
				from+=" and r.id=?";
				//System.out.println("sql:"+sql+from);
				return Db.paginate(pageNum, pageSize, sql, from, id);
				
			}
			
			//System.out.println("sql:"+sql+from);
			return Db.paginate(pageNum, pageSize, sql, from);
			
			
	}

}
