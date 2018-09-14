package com.admin.withdraw.controller;
import java.util.List;

import com.admin.withdraw.service.AdWithdrawService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.tool.StringUtil;
import com.vo.Refund;
@Before(AdLoginSeInterceptor.class)
public class AdWithdrawController extends Controller {

	public void index() {
		try {
			String starttime = "";
			String endtime = "";
			if(StringUtil.isNotEmpty(getPara("withdrawstarttime"))){
				starttime = getPara("withdrawstarttime");
			}
			if(StringUtil.isNotEmpty(getPara("withdrawendtime"))){
				endtime = getPara("withdrawendtime");
			}
			List<Refund> refunds = AdWithdrawService.serivce.withdraw(starttime,endtime);
			List<Refund> countStatus = AdWithdrawService.serivce.countStatus(starttime,endtime);
			List<Refund> successStatus = AdWithdrawService.serivce.successStatus(starttime,endtime);
			List<Refund> dealStatus = AdWithdrawService.serivce.dealStatus(starttime,endtime);
			List<Refund> failStatus = AdWithdrawService.serivce.failStatus(starttime,endtime);
			if(refunds != null && refunds.size() > 0){
				for (Refund all : refunds) {
					Integer allid = all.get("id");
					all.put("allmoney",0.000);
					all.put("successmoney",0.000);
					all.put("successtotal",0);
					all.put("dealtotal",0);
					all.put("failtotal",0);
					if(countStatus != null && countStatus.size() > 0){
						for (Refund count : countStatus) {
							Integer countid = count.get("id");
							if(allid.toString().equals(countid.toString())){
								all.put("allmoney",count.get("allmoney"));
								countStatus.remove(count);
								break;
							}
						}
					}
					if(successStatus != null && successStatus.size() > 0){
						for (Refund success : successStatus) {
							Integer successid = success.get("id");
							if(allid.toString().equals(successid.toString())){
								all.put("successmoney",success.get("successmoney"));
								all.put("successtotal",success.get("successtotal"));
								successStatus.remove(success);
								break;
							}
						}
					}
					if(dealStatus != null && dealStatus.size() > 0){
						for (Refund deal : dealStatus) {
							Integer dealid = deal.get("id");
							if(allid.toString().equals(dealid.toString())){
								all.put("dealtotal",deal.get("dealtotal"));
								dealStatus.remove(deal);
								break;
							}
						}
					}
					if(failStatus != null && failStatus.size() > 0){
						for (Refund fail : failStatus) {
							Integer failid = fail.get("id");
							if(allid.toString().equals(failid.toString())){
								all.put("failtotal",fail.get("failtotal"));
								failStatus.remove(fail);
								break;
							}
						}
					}
				}
			}
			setAttr("withdraw",refunds);
			setAttr("starttime",starttime);
			setAttr("endtime",endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/withdraw.jsp");
	}
	
	public void testPay(){
		renderJsp("/WEB-INF/admin/jsp/testPay.jsp");
	}
	
}
