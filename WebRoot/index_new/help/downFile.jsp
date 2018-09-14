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

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/downFile.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:42 GMT -->
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
         <li><a href="merchantsAccess.jsp">商户接入流程</a></li>
         <li><a href="faq.jsp">常见问题</a></li>
         <li class="hover" id="downFile"><a href="downFile.jsp">接入文档下载</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 帮助中心 > <span class="red">接入文档下载</span></div>
      <div class="mcon">
         <div class="mr_tit"><h2>接入文档下载</h2></div>
         <div class="about">
         	 <ul class="safelist">
            <li>
           		 快银支付接入指引：<br />
            <h2>1.注册快银账号，提交实名认证材料；</h2>
            <h2>2.客户经理电话联系，了解公司概况并进入实名认证审核流程；</h2>
            <h2>3.实名认证审核通过，客户经理发送服务协议与签约指引(本文档)；</h2>
            <h2>4.将收到的服务协议打印两份后分别加盖公章、签名；</h2>
            <h2>5.将营业执照副本复印件、法人代表身份证复印件、公司税务登记证复印件加盖公章。如未有营业执 <br />
				照，可以组织机构代码证替代；</h2>
			<h2>6.将两份服务协议与以上证照材料一同快递至快银公司(地址：广州市天河区大观中路新塘大街28号<br />
				祺禾商贸园B2209室快银公司，收件人：丘振兴/18922382822)；</h2>
			<h2>7.我们接收到您的签约材料后，会在1个工作日内审核完毕并开通支付，届时您的客户经理会通知到您；</h2>
			<h2>8.签约完毕，如需技术协助，可联系客户经理确认。</h2> 
			</li>

			<li style="border-bottom:0"><a href="../../static/doc/kuaiyinpay.doc"><img src="../../static/images/adown.jpg" width="170" height="47" /></a></li>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/downFile.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:30:43 GMT -->
</html>