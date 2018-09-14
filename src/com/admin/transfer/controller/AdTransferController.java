package com.admin.transfer.controller;

import com.admin.transfer.service.AdTransferService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdTransferController extends Controller {

	public void index(){
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		String starttime = "";
		String endtime = "";
		String transfer_id = "";
		String pname = "";
		String pename = "";
		if (!getPara("starttime").equals("")) {
			starttime = getPara("starttime");
		}
		if (!getPara("endtime").equals("")) {
			endtime = getPara("endtime");
		}
		if (!getPara("transfer_id").equals("")) {
			transfer_id = getPara("transfer_id");
		}
		if (!getPara("pname").equals("")) {
			pname = getPara("pname");
		}
		if (!getPara("pename").equals("")) {
			pename = getPara("pename");
		}
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("transfer_id", transfer_id);
		setAttr("pname", pname);
		setAttr("pename", pename);
		Page<Record> orderpage = AdTransferService.service.getTransferPage(pageNum, numPerPage, starttime, endtime, transfer_id, pname, pename);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("orderlist", orderpage.getList());
		renderJsp("/WEB-INF/admin/jsp/transfer.jsp");
	}
}
