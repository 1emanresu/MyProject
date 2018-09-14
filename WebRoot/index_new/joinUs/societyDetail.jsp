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
         <li class="hover"><a href="societyRecruit.jsp">社会招聘</a></li>
         <li><a href="campusRecruit.jsp">校园招聘</a></li>
         <li><a href="snsRecruit.jsp">SNS招聘</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
     <div class="mright">
      <div class="curr"><a href="#">首页</a> >  加入我们 > 社会招聘 >  <span class="red">资讯详情</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>春季人才交流会专场招聘信息</h2>
        </div>
          <div class="jobcon2">
             <span class="time">2013年12月02日16:17</span>
             <div class="ybtable">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
  				<tr>
    				<td width="19%" rowspan="2" class="bgtd1">第一场</td>
    				<td width="9%" class="bgtd3" align="center">时间</td>
    				<td width="72%" class="bgtd3">2014年2月16日（周日）9:00-14:00</td>
  				</tr>
  				<tr>
    				<td class="bgtd4" align="center">地点</td>
    				<td class="bgtd4" >广州市天河路198号南方人才市场精典大厦七楼 </td>
  				</tr>
  				<tr>
    				<td rowspan="2" class="bgtd1">第二场</td>
    				<td class="bgtd3" align="center">时间</td>
    				<td class="bgtd3">2014年3月1日（周六）9:00-14:00</td>
  				</tr>
  				<tr>
    				<td class="bgtd4" align="center"  >地点</td>
    				<td class="bgtd4" >广州市天河路198号南方人才市场精典大厦六楼</td>
  				</tr>
  				<tr>
    				<td class="bgtd2">应聘方式</td>
    				<td colspan="2" class="bgtd5"><p>1.查阅公司网站上及专业招聘网站相关职位信息后投递简历至hr@kuaiyinpay.com邮箱，公司将尽快进行简历筛选并通过电话预约面试；</p>
      				2.2月16日、3月1日当天携带个人简历，亲临招聘会现场与面试官当面沟通。</td>
    			</tr>
			</table>
        </div>
           <span class="red" style="font-size: 13px;">温馨提示：</span><br />
        您有关于任何职位信息咨询，可拨打快银支付人力资源中心电话：020-85288288转1126、1136，可关注新浪微博：快银支付-HR了解最新招聘动态信息。</div>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/joinUs/societyDetail.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:50 GMT -->
</html>