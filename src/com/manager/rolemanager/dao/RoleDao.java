package com.manager.rolemanager.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class RoleDao {

	public Page<Record> rolePage(int pageNumber, int pageSize, String rolename) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append(" select * ");
		frosql.append(" FROM role r ");
		if (!rolename.equals("")) {
			frosql.append(" where r.role_name like '%" + rolename + "%'");
		}
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}

	public List<Record> getRolePerm(int roleid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select e.*, p.navid as checked  ");
		sql.append(" from navigation e");
		sql.append(" LEFT JOIN role_perm p");
		sql.append(" on e.navid = p.navid and p.role_id=" + roleid);
		sql.append(" group BY e.navid");
		return Db.find(sql.toString());
	}
}
