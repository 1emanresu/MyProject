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

<!-- 	<div class="ban">
		<img src="static/images/solution_ban.jpg" />
	</div> -->
	 <div class="ban" style="background:url(../static/images/banner_lianxi.jpg) center top no-repeat;"></div>	
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="../static/images/lnav_tit5.png" width="207" height="141" /></p>
       <h2><span>联系快银</span><b class="b">Customer service</b></h2>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > <span class="red">联系快银</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>联系我们</h2></div>
        <div class="about">
          <ul class="lxuslist">
            <li>
               <p class="red">联系我们有多种方式，总有一款适合您！</p>
            </li>
          <li>
             <h2>您可以给我们打电话</h2>
             <p>热线电话：4008-237-238   </p>
             <p>7*24小时客服在线守候</p>
           </li>
           <li>
             <h2>也可以通过微信、微博等社交媒体</h2>
             <p>微信号：kuaiyinpayKY</p>
             <p><img src="../static/newImages/weixin3.jpg" style="width: 131px; height: 131px;"/></p>
             <p style="padding:3px 0;">新浪微博：<a href="http://weibo.com/kuaiyinpay">http://weibo.com/kuaiyinpay</a></p>
             <p><img src="../static/newImages/weixin1.jpg" style="width: 131px; height: 131px;"/></p> 
             <p style="padding:3px 0;">腾讯微博：<a href="http://t.qq.com/kuaiyinpay">http://t.qq.com/kuaiyinpay</a></p>
             <p><img src="../static/newImages/weixin2.jpg" style="width: 131px; height: 131px;"/></p> 
           </li>
            <li style=" border-bottom:none;">
             <h2>还可以实地考察</h2>
             <p>广东快银网络信息技术有限公司</p>
             <p>地址：广州市天河区大观中路新塘大街28号祺禾商贸园B区2001室   510663 </p>
             <p style="padding:6px 0 10px 0;"><img src="../static/images/map1.jpg"  /></p> 
           </li>
          </ul>
        </div>
      </div>
      <!--mright end-->
   <div class="cls"></div>
   </div>
  </div>
  </div>
  
	
  
  <!--foot s-->
  
  <div class="cls"></div>
 <jsp:include page="../WEB-INF/page/footer1.jsp"></jsp:include>
</body>
</html>