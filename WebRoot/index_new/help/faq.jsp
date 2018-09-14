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

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/faq.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:42 GMT -->
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
       <p><img src=".<%=basePath%>/static/images/lnav_help.png" width="207" height="141" /></p>
       <h2><span>帮助中心</span>Help center</h2>
      </div>
      
      <div class="lnav">
       <ul class="lnavlist2">
         <li><a href="merchantsAccess.jsp">商户接入流程</a></li>
         <li class="hover" id="ploblem"><a href="faq.jsp">常见问题</a></li>
         <li><a href="downFile.jsp">接入文档下载</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 帮助中心 > <span class="red">常见问题</span></div>
      <div class="mcon">
         <div class="mr_tit"><h2>常见问题</h2></div>
         <div class="about">
         	<ul class="safelist">
            <li>
             <h2 class="b">1.怎么使用快银的服务？</h2>
             <p>答：快银为商户提供支付接入服务，请您注册快银账号并提交实名认证材料，我们将尽快联系您进行需求确认，同时为您审核开放支付服务。您将拿到快银支付接口文档，用于支付界面的开发、接入与使用，帮助您提高资金流转效率与效益。</p>
            </li>
            <li>
             <h2 class="b">2.快银的费率如何计算？</h2>
             <p>答：快银根据您的接入资质、行业性质、交易规模等因素综合确定起始费率值，具体资料请您咨询在线客服或您的客户经理。</p>
            </li>
            <li>
             <h2 class="b">3.快银提供的结算周期是多久？</h2>
             <p>答：快银支付提供T+1结算，如您有其他需求，请咨询在线客服或您的客户经理。</p>
            </li>
            <li>
             <h2 class="b">4.接入快银支付需要什么资质？</h2>
             <p>答：公司商户需提交营业执照、法人身份证、税务登记证、银行结算账户开户许可证。其中线上认证需上传营业执照与法人身份证，线下纸质签约需提交加盖公司公章的营业执照、法人代表身份证、税务登记证及服务协议。具体流程将由客户经理引导进行；如您非公司商户，请向在线客服咨询。</p>
            </li>
            <li style="border-bottom:0">
             <h2 class="b">5.接入过程需要多久？</h2>
             <p>您注册并提交实名认证后，我们将在1个工作日内完成审核开放，最快只需一小时。</p>
            </li>
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
	<!-- 菜单选中结束 -->	

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/faq.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:42 GMT -->
</html>