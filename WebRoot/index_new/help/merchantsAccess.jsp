<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:36 GMT -->
<!-- Added by HTTrack --><meta http-equiv="content-type" content="text/html;charset=utf-8" /><!-- /Added by HTTrack -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<META name="Keywords" content="<%=systemss.getStr("system_keyword")%>" />
<meta name="description"
	content="<%=systemss.getStr("system_keyword")%>" />
<link rel="stylesheet" type="text/css" href="static/new/css/style.css" />
<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/static/css/reset.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/static/css/top.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/static/css/jquery-ui-1.9.2.custom.min.css" />
	<script type="text/javascript"
		src="<%=basePath%>/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/front.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/static/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/jqnav.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/tab.js"></script>
</head>
<body>

  <jsp:include page="../../WEB-INF/navigation1.jsp"></jsp:include>

	<div class="ban" style="background:url(<%=basePath%>/static/images/help_ban.jpg) center top no-repeat;"></div>
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="<%=basePath%>/static/images/lnav_help.png" width="207" height="141" /></p>
       <h2><span>帮助中心</span>Help center</h2>
      </div>
      
      <div class="lnav">
       <ul class="lnavlist2">
         <li class="hover" id="flow"><a href="merchantsAccess.jsp">商户接入流程</a></li>
         <li><a href="faq.jsp">常见问题</a></li>
         <li><a href="downFile.jsp">接入文档下载</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 帮助中心 > <span class="red">商户接入流程</span></div>
      <div class="mcon">
         <div class="mr_tit"><h2>商户接入流程</h2></div>
       
         <div class="about">
         
         </div>
      </div>
      </div>
      <!--mright end-->
   <div class="cls"></div>
   </div>
  </div>
  
	
  
  <!--foot s-->
  
  <div class="cls"></div>
  <jsp:include page="../../WEB-INF/page/footer1.jsp"></jsp:include>
</body>
	<!-- 菜单选中结束 -->	

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:37 GMT -->
</html>