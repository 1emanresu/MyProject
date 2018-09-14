package com.jsp;

import java.util.List;

import com.jfinal.core.Controller;
import com.vo.Dynamic;

public class index extends Controller {
	
	public void index(){
		List<Dynamic> dynamicList = Dynamic.dao.find("select d.dynamicid, d.title from dynamic d ORDER BY d.createtime DESC LIMIT 0,12");
		setAttr("dynamicList", dynamicList);
		renderJsp("/WEB-INF/index.jsp");
	}
	
	public void help(){
		renderJsp("/WEB-INF/help.jsp");
	}
	
	public void inTerFace(){
		renderJsp("/WEB-INF/interface.jsp");
	}
	
	public void about(){
		renderJsp("/WEB-INF/about.jsp");
	}
	
	public void forgetpassword(){
		renderJsp("/WEB-INF/forgetpassword.jsp");
	}
}
