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
                <li><a href="fastGoods.jsp" class="hover">快速消费品</a></li>
                <li><a href="e-commerce.jsp">电商交易市场</a></li>
                <li><a href="gamesIndustry.jsp">游戏行业</a></li>
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
      <div class="curr"><a href="#">首页</a> > 解决方案 > <span class="red">快速消费品</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>快速消费品</h2></div>
        <div class="solcon">
         	<p>快银支付对快速消费品供应链资金支付环节研究发现，其支付结算所存在的问题主要有：</p>
			<p class="pline">上、中、下游供应商数量多，支付需求迥异</p>
			<p class="pline">传统结算方式渠道不统一，严重割裂资金流和业务信息流</p>
			<p class="pline">财务繁琐，资金利用效率低下</p>
			<p class="solimg"><img src="../../static/newImages/solimg.jpg" width="174" height="31" /></p>
			<p class="pline">快银支付为企业提供自动化资金归集、订单管理和对账服务，让企业少投入多产出，分担财务压力，促进企业高效运作。</p>
			<p class="pline">快银支付解决方案将提供为货款支付、货款回笼、资金结算、查询对账等全面服务。</p>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/solutionPlan/fastGoods.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:48 GMT -->
</html>