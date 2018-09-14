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

<!-- Mirrored from www.kuaiyinpay.com/index_new/aboutus.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:30 GMT -->
<!-- Added by HTTrack -->
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<!-- /Added by HTTrack -->
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
<jsp:include page="../WEB-INF/navigation1.jsp"></jsp:include>

	<!-- 	<div class="ban">
		<img src="static/images/solution_ban.jpg" />
	</div> -->
	<div class="ban"
		style="background: url(../static/newImages/about_ban.jpg) center top no-repeat;"></div>
	<div class="mmain">
		<div class="wrap">
			<div class="mleft">

				<div class="lnav_tit">
					<p>
						<img src="../static/images/lnav_tit1.png" width="207" height="141" />
					</p>
					<h2>
						<span>关于快银</span>About us
					</h2>
				</div>

			</div>
			<!--mleft end-->
			<div class="mright">
				<div class="curr">
					<a href="#">首页</a> > <span class="red">关于快银</span>
				</div>
				<div class="mcon">
					<div class="mr_tit">
						<h2>关于快银</h2>
					</div>
					<div class="about">
						<p class="em">广东快银网络信息技术有限公司（简称“快银支付”）是一家专业从事互联网支付及移动支付产品研发和运营的第三方支付公司。公司拥有完全自主知识产权、独立研发的第三方支付系统，确保支付过程的简捷安全。</p>
						<p class="em">快银支付立足广州，以珠三角经济区内众多企业为市场支点，向全球范围内的中小企业、商家及个人提供优质高效的电子支付、资金结算管理服务，现有注册资金1亿元。</p>
						<p class="em">快银支付针对中小型企业用户商务活动电子化的需求，利用信息化平台为其打造专业高效的中短期现金流管理解决方案，推出代收代付、B2B网关、移动收款等支付产品；支持人民币网银支付、快捷支付、移动支付、信用卡支付等支付方式，为企业提供经济、快捷、安全的电子支付服务，充分帮助企业在电子商务活动中快速获取和优化资金流。通过与客户的深度合作，资源优化整合，快银支付为客户构筑财务管理、金融理财、市场营销等服务于一体的共享平台，积极推动各合作企业以及支付行业的快速向前发展。</p>
						<p class="em">快银支付针对个人用户推出的电子钱包等产品，能实现便民缴费、信用卡还款、金融理财、优惠券等增值服务，助您引领移动生活潮流。</p>
						<p class="em">快银支付产品服务范围涉及互联网支付、移动支付、跨境支付等多领域，与国内绝大多数银行和国际信用卡组织、通讯运营商、知名电商等建立了紧密的合作关系，通过第三方支付行业数据安全标准(PCI
							DSS)、人民银行指定检测机构银行卡检测中心认证，建立了合规且严密的风险控制制度，完全自主知识产权、独立研发的第三方支付系统，真正践行“简安之道，财富之路”的企业口号。</p>
						<p class="em">快银支付坚持以客户需求为导向,配备专业的运营团队、7x24小时在线的全天候客服体系，公司根据客户的需要，提供标准安全的电子支付产品或度身定制个性化的支付服务。</p>
						<p class="em" style="border-bottom: none;">快银支付将持续以“为行业客户快速准确定制支付解决方案，创新研发电子支付服务产品，推动各行业电子商务的发展”为发展源动力，依托先进的支付技术平台、创新的服务理念，为国内外中小企业和个人提供完整的电子支付和资金清算外包服务，从而实现快银支付“成为业界领先、受人尊重的信息化金融服务提供商”的企业愿景。</p>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/aboutus.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:34 GMT -->
</html>