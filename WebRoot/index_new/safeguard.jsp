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

		<div class="ban" style="background:url(../static/images/safe_ban.jpg) center top no-repeat;"></div>	
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="../static/images/lnav_safe.png" width="207" height="141" /></p>
       <h2><span>安全保障</span><b class="b">Security</b></h2>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > <span class="red">安全保障</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>安全保障</h2></div>
         <div class="about">
          <ul class="safelist">
             <li>
               <p class="em">通过第三方支付行业数据安全标准(PCI DSS)、人民银行指定检测机构银行卡检测中心认证，建立了合规且严密的风险控制制度。拥有完全自主知识产权、独立研发的第三方支付系统。</p>
            </li>
          <li>
             <h2>A    &nbsp;&nbsp;&nbsp;非金融机构支付服务业务检测</h2>
			 <h2>B    &nbsp;&nbsp;&nbsp;PCI DSS</h2>
			 <h2>C    &nbsp;&nbsp;&nbsp;ISO/IEC 等认证</h2>
			 <h2>D    &nbsp;&nbsp;&nbsp;CFCA认证</h2>
           </li>
            <li style="border-bottom:none;">
            <img src="../static/images/safe1.jpg" width="621" height="328" /> </li>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/safeguard.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:49 GMT -->
</html>