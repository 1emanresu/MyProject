package com.jsp.settlement.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.settlement.service.ExcelExportUtil.Pair;
import com.vo.Refund;


public class ExportService{
	
	public static ExportService service = new ExportService();

	/**
	 * 导出数据
	 * @param response
	 * @param request
	 * @param member
	 */
	public void export(HttpServletResponse response, HttpServletRequest request, List<Refund> members) {
		
		List<Pair> titles = new ArrayList<Pair>();
		
		String id = "用户id";
		String id_key = "id";
		titles.add(new Pair(id_key, id));
		
		String account_namevalue = "收款人姓名";
		String account_namekey = "account_name";
		titles.add(new Pair(account_namekey, account_namevalue));
		
		String accountvalue = "收款人银行账号";
		String accountkey = "account";
		titles.add(new Pair(accountkey, accountvalue));
		String shengmingvalue = "收款人开户行的省名";
		String shengmingkey = "branchsheng";
		titles.add(new Pair(shengmingkey, shengmingvalue));
		String shimingvalue = "收款人开户行的市名";
		String shimingkey = "branchshi";
		titles.add(new Pair(shimingkey, shimingvalue));
		String branchvalue = "开户行";
        String branchkey = "branch";
        titles.add(new Pair(branchkey, branchvalue));
		String net_namevalue = "收款方银行名称";
		String net_namekey = "net_name";
		titles.add(new Pair(net_namekey, net_namevalue));
		String refund_amountvalue = "金额";
        String refund_amountkey = "refund_amount";
        titles.add(new Pair(refund_amountkey, refund_amountvalue));
		String idvalue = "商户订单号";
		String idkey = "refund_id";
		titles.add(new Pair(idkey, idvalue));
		
		// 特殊处理
//		for (Member member : Member) {
//			
//		}
//		
		ExcelExportUtil.exportByRecord(response, request, "提现记录", titles , members);
	}
}
