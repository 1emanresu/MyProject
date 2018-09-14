<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<footer>
	<div class="container" style="background:#ccc">
		<div class="row">
			<div class="col-md-12">
				<p align="center">
					Copyright ? 2010-2017 <%=systemss.getStr("system_company") %>版权所有<br />
					网站备案号：<%=systemss.getStr("system_filings") %><br /> 
				</p>
				<p align="center">
					<img src="<%=basePath%>/images/110.jpg">					
					<img src="<%=basePath%>/images/dianshang.jpg"> 
					<img src="<%=basePath%>/images/jingying.jpg"> 
					<img src="<%=basePath%>/images/tdflag1.jpg">
				</p>
			</div>

			
		</div>
	</div>
	</footer>
</body>
</html>