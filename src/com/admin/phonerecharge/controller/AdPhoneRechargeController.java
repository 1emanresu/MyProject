package com.admin.phonerecharge.controller;

import com.admin.phonerecharge.service.AdPhoneRechargeService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Onlineorder;

@Before(AdLoginSeInterceptor.class)
public class AdPhoneRechargeController extends Controller {

	public void index() {
		try {	
			int pageNum = getParaToInt("pageNum");
			int numPerPage = getParaToInt("numPerPage");
			String starttime = "";
			String endtime = "";
			String ret_code = "";
			String sporder_id = "";
			if (!getPara("starttime").equals("")) {
				starttime = getPara("starttime");
			}
			if (!getPara("endtime").equals("")) {
				endtime = getPara("endtime");
			}
			if (StringUtil.isNotEmpty(getPara("ret_code")) && !getPara("ret_code").equals("0")) {
				ret_code = getPara("ret_code");
			}
			if (!getPara("sporder_id").equals("")) {
				sporder_id = getPara("sporder_id");
			}
			setAttr("starttime",starttime);
			setAttr("endtime", endtime);
			setAttr("ret_code", ret_code);
			setAttr("sporder_id", sporder_id);
			Page<Record> orderpage = AdPhoneRechargeService.service.getPhoneRechargePage(pageNum, numPerPage, starttime, endtime, ret_code, sporder_id);
			setAttr("pageNum", orderpage.getPageNumber());
			setAttr("numPerPage", orderpage.getPageSize());
			setAttr("totalCount", orderpage.getTotalRow());
			setAttr("pageNumShown", orderpage.getTotalPage());
			setAttr("orderlist", orderpage.getList());
			renderJsp("/WEB-INF/admin/jsp/phoneoufei.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showPhoneRecharge(){
		String sporder_id = getPara("sporder_id");
		Onlineorder onlineorder = Onlineorder.dao.findById(sporder_id);
		setAttr("onlineorder", onlineorder);
		renderJsp("/WEB-INF/admin/jsp/phoneoufei/phoneoufeishow.jsp");
	}
	
}
