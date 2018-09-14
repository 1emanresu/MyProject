<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<meta name="Keywords" content="<%=systemss.getStr("system_keyword")%>" />
<meta name="description"
	content="<%=systemss.getStr("system_keyword")%>" />
<link rel="stylesheet" type="text/css" href="static/new/css/style.css" />
<link href="<%=basePath%>/css/validform.css" rel="stylesheet"/>
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
<jsp:include page="../WEB-INF/navigation1.jsp"></jsp:include>

	<div class="ban" style="background:url(../static/images/kuaiyin_product.jpg) center top no-repeat;"></div>
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="../static/images/lnav_bank.png" width="207" height="141" /></p>
       <h2><span>合作银行</span><b class="b">Cooperative bank</b></h2>
      </div>

    </div>
    <!--mleft end-->
    <div class="mright">
       <div class="curr"><a href="#">首页</a> > <span class="red">合作银行</span></div>
      <div class="mcon">
           <div class="bank">
         		<ul class="banklist">
                	<li><a  target="_blank"><img src="../static/images/bankPic/bank1.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank2.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank3.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank4.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank5.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank6.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank7.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank8.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank9.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank10.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank11.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank12.jpg" width="112" height="45" /></a></li>
                    <li><a  target="_blank"><img src="../static/images/bankPic/bank13.jpg" width="112" height="45" /></a></li>
                </ul>
         </div>
      </div>
      </div>
      <!--mright end-->
   <div class="cls"></div>
   </div>
  </div>
  
	
  
  <!--foot s-->
  
  <div class="cls"></div>
  <jsp:include page="../WEB-INF/page/footer1.jsp"></jsp:include>
</body>

<!-- Mirrored from www.kuaiyinpay.com/index_new/bank.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:05 GMT -->
</html>