<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
  <!--top s-->


  <div class="top">
  
    <div class="wrap">
    
       <ul class="toplist">
       
         <li class="spa"><b class="b">全国热线电话：<%=systemss.getStr("system_telephone") %></b></li>
         <li><a href="<%=basePath %>/index_new/aboutus.jsp">关于万鑫</a></li>
         <li><a href="<%=basePath %>/index_new/contact.jsp">联系万鑫</a></li>
         <li><a href="<%=basePath %>/index_new/help/merchantsAccess.jsp">帮助中心</a></li>
         <li><a href="<%=basePath %>/index_new/serviceOnline.jsp">在线客服</a></li>
       
       </ul>
    
    <div class="cls"></div>
    </div>
  
  </div>
	<!--top end-->
  
  <div class="head">
  
    
    <div class="wrap">
    
      <h1><a href="#"><img src="<%=basePath %>/static/new/images/logo.jpg" width="157" height="44" /></a></h1>
      <div class="nav">
      	<iframe style="z-index:-10001;border:1px;position:absolute;top:0;left:0;width:400px;height:300px;filter:alpha(opacity=0);"></iframe>
        <ul class="navlist">
           <li class="active"><span class="list1"><a id="indexPage" href="<%=basePath%>">首页</a></span></li>
           <li><span><a id="products" href="<%=basePath %>/index_new/account/person.jsp">万鑫产品</a></span>
               <span class="bgnav"></span>
              
             <div class="nbox" style="display:none;">
              <iframe style="z-index:-1;border:1px;position:absolute;top:0;left:0;width:100%;filter:alpha(opacity=0);"></iframe>
               
               <span class="nav_l">
               
               <h2>快银账户</h2>
               <p><a href="<%=basePath %>/index_new/account/person.jsp">个人账户</a></p>
               <p><a href="<%=basePath %>/index_new/account/merchant.jsp">企业账户</a></p>
                
                <h2 class="spa">支付工具</h2>
                <p><a href="<%=basePath %>/index_new/payTools/rmbGateway.jsp">人民币网关支付</a></p>
                <p><a href="<%=basePath %>/index_new/payTools/rmbShotcut.jsp">人民币快捷支付</a></p>
                <p><a href="<%=basePath %>/index_new/payTools/mobileHasCard.jsp">移动有卡支付</a></p>
                <p><a href="<%=basePath %>/index_new/payTools/mobileNoCard.jsp">移动无卡支付</a></p>
                <p><a href="<%=basePath %>/index_new/payTools/creditCard.jsp">外币信用卡支付</a></p>
                
               </span>
               
               <span class="nav_r">
                 <h2>企业应用</h2>
                 <p><a href="<%=basePath %>/index_new/businessApp/fundCollection.jsp">对公代扣</a></p>
                <p><a href="<%=basePath %>/index_new/businessApp/agencyReceipt.jsp">代收代付</a></p>
                <!-- <p><a href="index_new/businessApp/agencyPay.jsp">委托代付</a></p> -->
                <p><a href="<%=basePath %>/index_new/businessApp/B2BGateway.jsp">B2B网关</a></p>
                
                <h2 class="spa">便民应用</h2>
                <p><a href="<%=basePath %>/index_new/applicableService/creditCardPayment.jsp">信用卡还款</a></p>
                <p><a href="<%=basePath %>/index_new/applicableService/lotteryBetting.jsp">彩票投注</a></p>
                <p><a href="<%=basePath %>/index_new/applicableService/superTransfer.jsp">超级转账</a></p>
               <p><a href="<%=basePath %>/index_new/applicableService/ticketsCenter.jsp">票务中心</a></p>
               <p><a href="<%=basePath %>/index_new/applicableService/Top-upPayment.jsp">充值缴费</a></p>
               </span>                
               
             </div>
           
           </li>
           <li><span><a id="project" href="<%=basePath %>/index_new/solutionPlan/bigGoodsTrad.jsp">解决方案</a></span>
           <span class="bgnav"></span>
           <div class="nbox" style="display:none;">
            <span class="nav_l">
               <p><a href="<%=basePath %>/index_new/solutionPlan/bigGoodsTrad.jsp">大宗商品交易</a></p>
                <p><a href="<%=basePath %>/index_new/solutionPlan/industrySupply.jsp">行业供应链</a></p>
                <!-- <p><a href="index_new/solutionPlan/MicroCreditLoan.jsp">小微信贷</a></p> -->
                <p><a href="<%=basePath %>/index_new/solutionPlan/fundsTrusteeship.jsp">资金托管</a></p>
                <p><a href="<%=basePath %>/index_new/solutionPlan/fastGoods.jsp">快速消费品</a></p>
                <p><a href="<%=basePath %>/index_new/solutionPlan/e-commerce.jsp">电商交易市场</a></p>
               </span>
               <span class="nav_r">
                <p><a href="<%=basePath %>/index_new/solutionPlan/gamesIndustry.jsp">游戏行业</a></p>
                 <p><a href="<%=basePath %>/index_new/solutionPlan/insuranceBusiness.jsp">保险行业</a></p>
                 <p><a href="<%=basePath %>/index_new/solutionPlan/educationSector.jsp">教育行业</a></p>
                 <p><a href="<%=basePath %>/index_new/solutionPlan/airlineTicketing.jsp">航空票务</a></p>
                 <p><a href="<%=basePath %>/index_new/solutionPlan/hotelIndustry.jsp">酒店行业</a></p>
                 <p><a href="<%=basePath %>/index_new/solutionPlan/otherIndustry.jsp">其它</a></p>
               </span>     
             </div>
          </li>
           <li><span><a id="safe" href="<%=basePath %>/index_new/safeguard.jsp">安全保障</a></span></li>
        </ul>
      </div>
    
    <div class="cls"></div>
    </div>
  
  
  
  </div>
  <!--head end-->
</body>
</html>