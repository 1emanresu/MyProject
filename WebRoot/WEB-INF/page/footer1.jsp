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
	<div class="foot">
  
    <div class="wrap">
    
    
     <div class="ourparter">
     
       <h2><a href="<%=basePath %>/index_new/bank.jsp">我们的合作银行</a></h2>
       <p>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_boc.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_abc.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_icbc.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_ccb.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_cmb.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_cib.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_ecitic.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_ceb.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_bankcomm.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_psbc.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_cmbc.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_pingan.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_cgb.png" /></a>
          <a href="#"><img src="<%=basePath %>/static/new/images/bkpic_spdb.png" /></a>
         
          
       </p>
     
     </div>
     
     <div class="lxus">
       
       <div class="lxus_con">
         <h2>联系我们</h2>
         <p>热线电话：<%=systemss.getStr("system_telephone") %></p>
         <p>公司地址：<%=systemss.getStr("system_address") %></p>
       </div>
     
     </div>
     
     <div class="cls"></div>
    
    <p class="ft1"><a href="<%=basePath %>/index/aboutus.jsp">关于快银</a> |  <a href="<%=basePath %>/index_new/help/merchantsAccess.jsp">帮助中心</a> |  <a href="<%=basePath %>/index_new/contact.jsp">联系我们</a> |  <a href="#">友情链接</a>
    	 |  <a href="http://www.anquan.org/s/www.kuaiyinpay.com" name="2Ahv2VAaa3iILUScBWN6p2aKj0xEe4Oi8rfnmbJD0ZsCbnGIy5" >安全联盟</a>
    </p>
    <p class="ft2">Copyright ? 2009-2013 网站备案号：<%=systemss.getStr("system_filings") %> <%=systemss.getStr("system_company") %>版权所有 </p>
  </div>
  </div>
</body>
</html>