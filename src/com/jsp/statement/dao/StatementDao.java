package com.jsp.statement.dao;

import java.util.List;

import com.vo.Balance;

public class StatementDao {
	public List<Balance> getBalance(int id){
		return Balance.dao.find("select b.cardamount, b.netamount from balance b where b.id = "+id);
	}
}
