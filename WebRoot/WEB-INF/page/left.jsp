<%@page import="com.vo.System_qq"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	List<System_qq> systemqqlist = System_qq.dao.find("select * from system_qq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="span2 main-menu-span">
		<div class="well nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<li class="nav-header hidden-tablet">导航</li>
				<li><a class="ajax-link" href="#" onclick="user()"> <i
						class="icon-user"></i> <span class="hidden-tablet"> 用户管理</span>
				</a></li>
				<li><a class="ajax-link" href="#" onclick="exchange()"><i
						class="icon-folder-open"></i><span class="hidden-tablet">
							交易管理</span></a></li>
				<li><a class="ajax-link" href="#" onclick="statement()"><i
						class="icon-list-alt"></i><span class="hidden-tablet"> 报表查看</span></a></li>
				<li><a class="ajax-link" href="#" onclick="payment()"><i
						class="icon-barcode"></i><span class="hidden-tablet"> 支付管理</span></a></li>
				<li><a class="ajax-link" href="#" onclick="settlement()"><i
						class="icon-briefcase"></i><span class="hidden-tablet">
							结算管理</span></a></li>
			</ul>
		</div>
		<br />
		<div class="well nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<li class="nav-header hidden-tablet">客服</li>
				<%
		              	System_qq systemqq = null;
		              	for(int i=0; i<systemqqlist.size(); i++){
		              		systemqq = systemqqlist.get(i);
		              %>
				<li>
				<a target="_blank"
								href="http://wpa.qq.com/msgrd?v=3&uin=<%=systemqq.getStr("qq_number") %>&site=qq&menu=yes"><img
								border="0" src="http://wpa.qq.com/pa?p=2:<%=systemqq.getStr("qq_number") %>:41"
								alt="商务合作咨询－恭候您咨询" title="<%=systemss.getStr("system_firm") %>客服－恭候您咨询"><span class="hidden-tablet"> <%=systemqq.getStr("qq_name") %></span></a>
				</li>
				<%
		              	}
				%>
			</ul>
		</div>
		<!--/.well -->
	</div>
	<!--/span-->
</body>
</html>