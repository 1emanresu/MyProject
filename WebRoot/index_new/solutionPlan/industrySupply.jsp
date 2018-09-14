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
                <li><a href="industrySupply.jsp" class="hover">行业供应链</a></li>
                <!-- <li><a href="index_new/solutionPlan/MicroCreditLoan.jsp">小微信贷</a></li> -->
                <li><a href="fundsTrusteeship.jsp">资金托管</a></li>
                <li><a href="fastGoods.jsp">快速消费品</a></li>
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
      <div class="curr"><a href="#">首页</a> > 解决方案 > <span class="red">行业供应链</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>行业供应链</h2></div>
        <div class="solcon">
         	<p>快银支付对行业供应链资金支付环节研究发现，其支付结算所存在的问题主要有：</p>
			<p class="pline">客户分布广，传统支付效率低</p>
			<p class="pline">客户支付能力参差不齐，结算耗时成本高</p>
			<p class="pline">财务复杂，资金使用效率低</p>
			<p class="pline">人工操作风险高、效率低</p>
			<p class="pline">跨行交易繁琐，且成本增大</p>
			<p class="solimg"><img src="../../static/newImages/solimg.jpg" width="174" height="31" /></p>
			<p class="pline">快银支付提供多样化、一体化解决方案，解决客户分散，集合各家银行资源组合多种支付方式，全面覆盖行业供应链上、中、下游，满足不同企业不同支付需求。</p>
			<p class="pline">快银支付平台T+1的结算方式，收款方第二天即可收到款项，高效解决结算时间长，利息损失等问题。</p>
			<p class="pline">快银支付为企业提供自动化资金归集、订单管理和对账服务，帮助简化财务流程进行科学管理，提高资金使用率。</p>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/solutionPlan/industrySupply.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:48 GMT -->
</html>