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

<!-- 	<div class="ban">
		<img src="static/images/solution_ban.jpg" />
	</div> -->
	 <div class="ban" style="background:url(../../static/newImages/solution_ban.jpg) center top no-repeat;"></div>	
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
      <div class="lnav_tit">
       <p><img src="../../static/images/lnav_tit3.png" width="207" height="141" /></p>
       <h2><span>解决方案</span>The solution</h2>
      </div>
      <div class="lnav">
       <ul class="lnavlist">
         <li ><a href="bigGoodsTrad.jsp">大宗商品交易</a></li>
                <li><a href="industrySupply.jsp">行业供应链</a></li>
                <!-- <li><a href="index_new/solutionPlan/MicroCreditLoan.jsp">小微信贷</a></li> -->
                <li><a href="fundsTrusteeship.jsp">资金托管</a></li>
                <li><a href="fastGoods.jsp">快速消费品</a></li>
                <li><a href="e-commerce.jsp">电商交易市场</a></li>
                <li><a href="gamesIndustry.jsp" class="hover">游戏行业</a></li>
                <li><a href="insuranceBusiness.jsp">保险行业</a></li>
               <li><a href="educationSector.jsp">教育行业</a></li>
               <li><a href="airlineTicketing.jsp">航空票务</a></li>
               <li><a href="hotelIndustry.jsp">酒店行业</a></li>
               <li><a href="otherIndustry.jsp">其它</a></li>
       </ul>
      </div>
    </div>
    <!--mleft end-->
    
    
    
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 解决方案 > <span class="red">游戏行业</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>游戏行业</h2></div>
        <div class="solcon">
         	<p>快银支付对游戏行业的资金支付环节进行研究发现，其支付结算所存在的问题主要有：</p>
			<p class="pline">支付零散，不利于企业系统管理</p>
			<p class="pline">支付与游戏环节相互抢夺时间，导致游戏玩家流失</p>
			<p class="solimg"><img src="../../static/newImages/solimg.jpg" width="174" height="31" /></p>
			<p class="pline">快银支付针对各类游戏运营商与代理商，提供一条龙的收款、发货、游戏兑换功能。</p>
			<p class="pline">为不同类型的游戏平台、不同技术水平的游戏商户，量身定制充值支付方式及游戏运营配套服务。</p>
			<p class="pline">通过包月预扣、帐号支付、快捷付等支付方式，保证与提升游戏充值的成功率。</p>
        </div>
        <div class="solbox">
           <h3><img src="../../static/newImages/fangantt.jpg" width="611" height="34" /></h3>
         <img src="../../static/newImages/fangan1.jpg" /></div>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/solutionPlan/gamesIndustry.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:48 GMT -->
</html>