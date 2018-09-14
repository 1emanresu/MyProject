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
	 <div class="ban" style="background:url(<%=basePath%>/static/newImages/product_ban.jpg) center top no-repeat;"></div>	
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="<%=basePath%>/static/images/lnav_tit4.png" width="207" height="141" /></p>
       <h2><span>快银产品</span>Product</h2>
      </div>
      
      <div class="lnav">
       <ul class="lnavlist">
         <li  onclick="setTab('lnav',1,4)" ><a href="javascript:void(0);" id="lnav1">快银账户</a>
           <div class="lbox" id="con_lnav_1" style="display:none;">
             <ul>
                <li ><a href="../account/person.jsp">个人账户</a></li>
                <li ><a href="../account/merchant.jsp">企业账户</a></li>
             </ul>
           </div>
         </li>
         <li class="hover" onclick="setTab('lnav',2,4)" ><a href="javascript:void(0);" id="lnav2" class="hover">支付工具</a>
          <div class="lbox" id="con_lnav_2" style="display:block;">
             <ul>
               <li ><a href="rmbGateway.jsp">人民币网关支付</a></li>
               <li ><a href="rmbShotcut.jsp">人民币快捷支付</a></li>
               <li class="hover"><a href="mobileHasCard.jsp">移动有卡支付</a></li>
               <li ><a href="mobileNoCard.jsp">移动无卡支付</a></li>
               <li ><a href="creditCard.jsp">外币信用卡支付</a></li>
             </ul>
           </div>
         </li>
          <li onclick="setTab('lnav',3,4)" ><a href="javascript:void(0);" id="lnav3">企业应用</a>
          <div class="lbox" id="con_lnav_3" style="display:none">
             <ul>
                <li ><a href="../businessApp/fundCollection.jsp">对公代扣</a></li>
                <li ><a href="../businessApp/agencyReceipt.jsp">代收代付</a></li>
                <!-- <li ><a href="index_new/businessApp/agencyPay.jsp">委托代付</a></li> -->
               	<li ><a href="../businessApp/B2BGateway.jsp">B2B网关</a></li>
             </ul>
           </div>
         </li>
         <li onclick="setTab('lnav',4,4)" ><a href="javascript:void(0);" id="lnav4">便民应用</a>
          <div class="lbox" id="con_lnav_4" style="display:none">
             <ul>
               <li ><a href="../applicableService/creditCardPayment.jsp">信用卡还款</a></li>
               <li ><a href="../applicableService/lotteryBetting.jsp">彩票投注</a></li>
               <li ><a href="../applicableService/superTransfer.jsp">超级转账</a></li>
               <li ><a href="../applicableService/ticketsCenter.jsp">票务中心</a></li>
               <li ><a href="../applicableService/Top-upPayment.jsp">充值缴费</a></li>
             </ul>
           </div>
         </li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > <a href="../account/person.jsp">快银产品 </a> > 支付工具 > <span class="red">移动有卡支付</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>移动有卡支付</h2></div>
		<div class="procon">
           	<div class="proabout">
                <div class="prott"><img src="../../static/newImages/prott.jpg" width="121" height="33" /></div>
                 <p class="prop1">基于定制刷卡槽的移动终端（平板电脑、手机等）或音频插卡设备，在移动端</p>
                 <p class="prop2">进行刷卡支付的付款方式。</p>
            </div>
            <div class="protitle"><img src="../../static/newImages/prott2.jpg" width="608" height="34" /></div>
            <div class="prodebox">
              <p align="center"><img src="../../static/newImages/prode7.jpg" /></p>
            </div>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/payTools/mobileHasCard.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:43 GMT -->
</html>