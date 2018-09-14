package com.admin.refund.controller;

import java.math.BigDecimal;

import com.admin.customerlog.serivce.AdCustomerLogService;
import com.admin.refund.service.AdRefundService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jsp.exchange.service.ExchangeService;
import com.tool.StringUtil;
import com.vo.Balance;
import com.vo.Person;
import com.vo.Refund;

@Before(AdLoginSeInterceptor.class)
public class AdRefundController extends Controller {

	public void index() {
		try {
			int pageNum = getParaToInt("pageNum");
			int numPerPage=1;
			if(getParaToInt("numPerPage")!=null){
				numPerPage = getParaToInt("numPerPage");
			}
			String accountname="";
			String refustarttime = "";
			String refuendtime = "";
			String refustate = "";
			String refuid = "";
			String id = "";
			String settlementauthority = "";
			String bankType = "";
			if (getPara("accountname")!=null&&!getPara("accountname").equals("")) {
				accountname = getPara("accountname");
			}   
			if (!getPara("refustarttime").equals("")) {
				refustarttime = getPara("refustarttime");
			}     
			if (!getPara("refuendtime").equals("")) {
				refuendtime = getPara("refuendtime");
			}
			if (StringUtil.isNotEmpty(getPara("refustate")) && !getPara("refustate").equals("0")) {
				refustate = getPara("refustate");
			}
			if (getPara("refuid")!=null&&!getPara("refuid").equals("")) {
				refuid = getPara("refuid");
			}
			try {
				if (!getPara("id").equals("")&&getPara("id")!=null) {
					id = getPara("id");
				}
			} catch (Exception e) {
				
			}
			
			if (!getPara("settlementauthority").equals("0")) {
				settlementauthority = getPara("settlementauthority");
			}
			if(StringUtil.isNotEmpty(getPara("bankType")) && !getPara("bankType").equals("0")){
				bankType = getPara("bankType").trim();
			}
			//System.out.println("======"+bankType);
			setAttr("refustarttime", refustarttime);
			setAttr("refuendtime", refuendtime);
			setAttr("refustate", refustate);
			setAttr("accountname", accountname);
			setAttr("refuid", refuid);
			setAttr("id", id);
			setAttr("settlementauthority", settlementauthority);
			setAttr("bankType", bankType);
			Page<Record> refundPage = AdRefundService.serivce.getRefundPageRe(pageNum, numPerPage, refustarttime, refuendtime,
					refustate, refuid, id, settlementauthority,accountname,bankType);
			setAttr("pageNum", refundPage.getPageNumber());
			setAttr("numPerPage", refundPage.getPageSize());
			setAttr("totalCount", refundPage.getTotalRow());
//			setAttr("pageNumShown", refundPage.getTotalPage());
			setAttr("orderlist", refundPage.getList());
			setAttr("bankList", AdRefundService.serivce.findRefundBank());
			setAttr("cardcode", ExchangeService.exchangeService.getCardCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/refund.jsp");
	}

	public String getUrl(String id, String refustate, String refustarttime, String refuendtime, String refuid,
			String settlementauthority) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select r.*, pe.name, se.payable");
		frosql.append(" FROM refund r");
		frosql.append(" LEFT JOIN (select s.*, p.payable from settlement_account s ");
		frosql.append(" LEFT JOIN participate p");
		frosql.append(" ON s.codeid = p.codeid) se");
		frosql.append(" on r.refund_types = se.account_types and se.id=r.id");
		frosql.append(" LEFT JOIN person pe");
		frosql.append(" on r.id=pe.id");
		frosql.append(" where 1=1");
		if (!refustate.equals("")) {
			frosql.append(" and r.refund_state=" + refustate);
		}
		if (!refuid.equals("")) {
			frosql.append(" and r.refuid like '%" + refuid + "%'");
		}
		if (!id.equals("")) {
			frosql.append(" and r.id =" + id);
		}
		if (!settlementauthority.equals("")) {
			frosql.append(" and r.settlementauthority=" + settlementauthority);
		}
		if (!refustarttime.equals("") && !refuendtime.equals("")) {
			frosql.append(" and r.createtime >= '" + refustarttime + "' and r.createtime < '" + refuendtime + "'");
		}
		frosql.append(" ORDER BY r.createtime DESC,refund_id");
		return frosql.toString();
	}

	public void showRefund() {
		String refuid = getPara("refuid");
		Refund refund = Refund.dao.findById(refuid);
		Person per = Person.dao.findById(refund.getInt("id"));
		setAttr("refuname", per.get("name"));
		setAttr("refund", refund);
		renderJsp("/WEB-INF/admin/jsp/refund/showRefund.jsp");
	}

	public void upRefund() {
		try {
			String refundid = getPara("refund_id");
			System.out.println(refundid);
			Refund refund = Refund.dao.findById(refundid);
			if (refund.getInt("refund_state") == 2) {
				String explain = getPara("explain");
				int refund_state = getParaToInt("refund_state");
				//退款用户的id
				int id = refund.getInt("id");
				System.out.println(id);
				Balance balance = Balance.dao.findById(id);
				//提交的退款金额
				BigDecimal refund_amount = refund.getBigDecimal("refund_amount");
				//提现手续费
				BigDecimal refund_fees = refund.getBigDecimal("refund_fees");
				System.out.println("refund_fees====="+refund_fees);
				BigDecimal amount = balance.getBigDecimal("amount");
				BigDecimal waitamount = balance.getBigDecimal("waitamount");
				if (refund_state == 1) {
					balance.set("amount", amount.subtract(refund_amount));
					balance.set("waitamount", waitamount.subtract(refund_amount));
					balance.set("refundamout", balance.getBigDecimal("refundamout").add(refund_amount));
					//提现成功保存客户日志
					AdCustomerLogService.service.saveCustomerLog(id, 3, 1, refund_amount, null, null, null, balance.getBigDecimal("settlement"));
				} else if (refund_state == 3) {
					balance.set("waitamount", waitamount.subtract(refund_amount));
					BigDecimal settlement = balance.getBigDecimal("settlement").add(refund_amount).add(refund_fees);
					System.out.println("settlement=余额===="+settlement);
					balance.set("settlement", settlement);
					//提现失效，可提现金额回到客户账上
					AdCustomerLogService.service.saveCustomerLog(id, 3, 0,refund_amount, null, null, null, settlement);
				}
				balance.update();
				refund.set("refund_state", refund_state);
				refund.set("explain", explain);
				refund.update();
				renderJson(
						"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav7\", \"forwardUrl\":\"\", \"rel\":\"nav7\", \"callbackType\":\"closeCurrent\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"订单已经处理，请不要重新\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void refundDeductions() {
		try {
			String[] refun = getParaValues("refundlist");
			for (String refundid : refun) {
				if(!refundid.isEmpty()){
					Refund refund = Refund.dao.findById(refundid);
					if (refund.getInt("refund_state") == 2) {
						String explain = getPara("explain");
						int state = 1;
						int id = refund.getInt("id");
						Balance balance = Balance.dao.findById(id);
						BigDecimal refund_amount = refund.getBigDecimal("refund_amount");
						BigDecimal amount = balance.getBigDecimal("amount");
						BigDecimal waitamount = balance.getBigDecimal("waitamount");
						if (state == 1) {
							balance.set("amount", amount.subtract(refund_amount));
							balance.set("waitamount", waitamount.subtract(refund_amount));
							balance.set("refundamout", balance.getBigDecimal("refundamout").add(refund_amount));
							//提现成功保存客户日志
							AdCustomerLogService.service.saveCustomerLog(id, 3, 1, refund_amount, null, null, null, balance.getBigDecimal("settlement"));
						}
						balance.update();
						refund.set("refund_state", 1);
						refund.set("explain", explain);
						refund.update();
						renderJson(
								"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav7\", \"forwardUrl\":\"\", \"rel\":\"nav7\"}");
					}
				}
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	public void refundDeductionsfail() {
		String[] refun = getParaValues("refundlist");
		for (String refundid : refun) {
			try {
				Refund refund = Refund.dao.findById(refundid);
				if (refund.getInt("refund_state") == 2) {
					String explain = getPara("explain");
					int state = 3;
					int id = refund.getInt("id");
					Balance balance = Balance.dao.findById(id);
					BigDecimal refund_amount = refund.getBigDecimal("refund_amount");
					BigDecimal waitamount = balance.getBigDecimal("waitamount");
					BigDecimal refund_fees = refund.getBigDecimal("refund_fees");
					if (state == 3) {
						balance.set("waitamount", waitamount.subtract(refund_amount));
						BigDecimal s = null;
						try {
							s = balance.getBigDecimal("settlement");
							if (s == null) {
								s = new BigDecimal("0");
							}
						} catch (Exception e) {
							s = new BigDecimal("0");
						}
						BigDecimal settlement = s.add(refund_amount).add(refund_fees);
						balance.set("settlement", settlement);
						//提现失效，可提现金额回到客户账上
						AdCustomerLogService.service.saveCustomerLog(id, 3, 0,refund_amount, null, null, null, settlement);
					}
					balance.update();
					refund.set("refund_state", 3);
					refund.set("explain", explain);
					refund.update();
					renderJson(
							"{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav7\", \"forwardUrl\":\"\", \"rel\":\"nav7\"}");
				}
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
				e.printStackTrace();
			}
		}
	}
}
