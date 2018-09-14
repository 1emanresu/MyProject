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
<jsp:include page="../../WEB-INF/navigation1.jsp"></jsp:include>

	<div class="ban" style="background:url(<%=basePath%>/static/images/join_ban.jpg) center top no-repeat;"></div>
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="<%=basePath%>/static/images/lnav_join.png" width="207" height="141" /></p>
       <h2><span>加入我们</span>Join us</h2>
      </div>
      
      <div class="lnav">
       <ul class="lnavlist2">
         <li><a href="societyRecruit.jsp">社会招聘</a></li>
         <li><a href="campusRecruit.jsp">校园招聘</a></li>
         <li class="hover"><a href="snsRecruit.jsp">SNS招聘</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
   <div class="mright">
      <div class="curr"><a href="#">首页</a> > 加入我们 > <span class="red">SNS招聘</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>SNS招聘</h2></div>
        <div class="joincon">
          <div class="sns1">
              <ul class="snslist">
                <li><a href="http://www.dajie.com/corp/3439135"><img src="<%=basePath%>/static/newImages/sns1.gif" width="102" height="40" />
                 <span>大街网</span></a>
                </li>
                <li><a href="http://weibo.com/kuaiyinhr"><img src="<%=basePath%>/static/newImages/sns2.gif" width="120" height="40" />
                <span>快银微博</span></a>
                </li>
              </ul>
         </div>
            <ul class="snslist2">
               <li><span><img src="<%=basePath%>/static/newImages/weixin1.jpg" width="131" height="131" /></span><p>快银支付HR新浪微博</p></li>
               <li><span><img src="<%=basePath%>/static/newImages/weixin2.jpg" width="131" height="131" /></span><p>腾讯微博</p></li>
               <li><span><img src="<%=basePath%>/static/newImages/weixin3.jpg" width="131" height="131" /></span><p>快银支付微信</p></li>
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
  <jsp:include page="../../WEB-INF/page/footer1.jsp"></jsp:include>
</body>

<!-- Mirrored from www.kuaiyinpay.com/index_new/joinUs/snsRecruit.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:59 GMT -->
</html>