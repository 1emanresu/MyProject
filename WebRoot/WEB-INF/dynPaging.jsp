<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<script type="text/javascript">
$(document).ready(function(){
	$("li#top2").addClass("active");
	$("#content").html('${dynamic.content}');
});
</script>
</head>
<body style="font-family: Microsoft YaHei">
<jsp:include page="navigation1.jsp"></jsp:include>

		
		
		<div class="ban" style="background:url(<%=basePath%>/static/images/banner_gonggao.jpg) center top no-repeat;"></div>
		
		
	<div class="mmain">
   <div class="wrap">
   
    <div class="mleft">
      <div class="lnav_tit">
       
      
      <p><img src="<%=basePath%>/static/images/lnav_tit2.png" width="207" height="141" /></p>
      <h2><span>公司动态</span>News</h2>
       
       
       
       
      </div>
    </div>
    
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 
      
       	<span class="red">公司动态</span>
       
       
      </div>
     <div class="mcon">
        <div class="mr_tit"><h2><c:out value="${dynamic.title}"></c:out></h2>
        </div>
          <div class="solution newsdt">
             <span class="time"><c:out value="${dynamic.createtime}"></c:out></span>
             <p id="content"></p>
        </div>
      </div>
    </div>
      <!--mright end-->
   <div class="cls"></div>
   </div>
  </div>
	
	
  
  <!--foot s-->
  
  <div class="cls"></div>
  <jsp:include page="page/footer1.jsp"></jsp:include>
</body>
</html>