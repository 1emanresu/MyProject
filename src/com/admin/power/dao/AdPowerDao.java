package com.admin.power.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vo.Navigation;

public class AdPowerDao {
	public Page<Record> getPowerPage(int pageNumber, int pageSize , String powerid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select *");
		frosql.append("from power p");
		frosql.append(" where 1=1");
		if(!powerid.equals("")){
			frosql.append(" and p.powerid="+powerid);
		}
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public List<Record> getPowerNavid(int powerid){
		StringBuffer sql = new StringBuffer();
		sql.append("select n.*, p.navid as checked");
		sql.append(" from navigation n");
		sql.append(" LEFT JOIN powerid_navid p");
		sql.append(" on n.navid = p.navid and p.powerid="+powerid);
		sql.append(" ORDER BY n.navid");
		return Db.find(sql.toString());
	}
	
	/**
	 * @description 根据用户id查询出当前用户的三级节点是否有访问的权限<br/>
	 * @methodName findChildNavid<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月12日下午2:48:22<br/>
	 * @param powerid
	 * @return
	 */
	public List<Record> findChildNavid(int powerid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select n.*,p.navid as checked from navigation n ");
		sql.append(" left join powerid_navid p on n.navid = p.navid and p.powerid="+powerid+" ");
		sql.append(" where node = 3 order by n.navid asc ");
		System.out.println(sql.toString());
		return Db.find(sql.toString());
	}
	
	public List<Navigation> findParentNode(){
		StringBuffer sql = new StringBuffer();
		sql.append(" select navid,node,ownership,`name` ");
		sql.append(" from navigation where node in (1,2) ");
		return Navigation.dao.find(sql.toString());
	}
	
	public List<Navigation> findNavigationByNavid(String navid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select ownership from navigation where navid = "+navid+" ");
		return Navigation.dao.find(sql.toString());
	}
}
