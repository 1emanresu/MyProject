package com.jsp.dynamic.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jsp.dynamic.service.DynamicService;
import com.vo.Dynamic;

public class DynamicController extends Controller {
	public void index(){
		int pageNum = getParaToInt("pageNum");
		Page<Dynamic> dynamicPage = DynamicService.service.getDynamicPage(pageNum, 10);
		setAttr("pageNum", dynamicPage.getPageNumber());
		setAttr("totalpage", dynamicPage.getTotalPage());
		setAttr("dynamiclist", dynamicPage.getList());
		renderJsp("/WEB-INF/dynamic.jsp");
	}
	
	public void getDynamic(){
		int dynamicid = getParaToInt("dynamicid");
		Dynamic dynamic = DynamicService.service.getDynamic(dynamicid);
		setAttr("dynamic", dynamic);
		renderJsp("/WEB-INF/dynPaging.jsp");
	}
}
