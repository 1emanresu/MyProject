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

	<div class="ban" style="background:url(<%=basePath%>/static/images/join_ban.jpg) center top no-repeat;"></div>
  <div class="mmain">
   <div class="wrap">
    <div class="mleft">
    
      <div class="lnav_tit">
       <p><img src="<%=basePath%>/static/images/lnav_join.png" width="207" height="141" /></p>
       <h2><span>加入我们</span>Join us</h2>
      </div>
      
      <div class="lnav">
       <ul class="lnavlist2">
         <li class="hover"><a href="societyRecruit.jsp">社会招聘</a></li>
         <li><a href="campusRecruit.jsp">校园招聘</a></li>
         <li><a href="snsRecruit.jsp">SNS招聘</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 加入我们 > <span class="red">社会招聘</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>社会招聘</h2></div>
        <div class="joincon2">
        
        <div class="job1">
        <div class="jobtitle"><span class="job_t3">网站投递渠道 <i>Site delivery channels</i></span></div>
          <ul class="joblist3">
           <li><a href="http://www.dajie.com/corp/3439135"><span>大街网</span></a></li>
           <li><a href="http://search.51job.com/list/co,c,2840107,000000,10,1.html#syzw"><span>51 JOB</span></a></li>
           <li><a href="http://company.zhaopin.com/CC519451520.htm"><span>智联招聘</span></a></li>
           <li><a href="http://hr.paynews.net/hr/company/company-show.php?id=213"><span>支付人才网</span></a></li>
          </ul>
        
        
        </div>
               <div class="job1">
             <div class="jobtitle"><span class="job_t1">招聘资讯 <i>information</i></span></div>
              <ul class="joblist">
               <li><a href="societyDetail.jsp"><span>2014-02-12</span>2014年2月中旬-3月初将参加南方人才市场春季招聘会</a></li>
              </ul>
          	</div>
          	
          <div class="job1 job1con">
            <div class="jobtitle"><span class="job_t2">招聘职位 <i>job</i></span></div>
            <ul class="joblist2">
            
                <li class="hover">
               	  <div class="jobbox_tt">
                   	  <h2>开发工程师-C++</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1. 练掌握C++开发技术，精通网络通信编程 、多线程编程技术，三年以上独立开发项目经验.<br />
                    2. 熟练掌握MySQL和Oracle等常用数据库.<br />
                  	3. 具备独立设计系统的能力，良好的代码编写习惯和文档编写能力.<br />
                  	我们期待您:<br />
                    1. 具有良好的沟通能力和团队合作精神.<br />
                    2. 协助设计和开发符合业务需求的解决方案.<br />
                  	3. 负责模块设计和开发，要求符合业务需求、架构标准和其他非功能性需求.
                  
                  </div>
                </li>
                
               	<li>
               	  <div class="jobbox_tt">
                   	  <h2>开发工程师-JAVA</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1、精通JAVA、JSP编程技术（servlet、JSP、struts、SCOCKET等）,2年以上项目开发经验.<br />
                    2、熟练掌握javascript, MVC模式.<br />
                  	3、良好的沟通能力和团队合作精神.<br />
                  我们期待您:<br />
                    1. 曾在第三方支付、银行、保险、电信等相关行业项目开发经验者.<br />
                    2. 或，过往3年项目开发经验中，曾独立开发中小型项目.<br />
                  	3. 或，过往3年项目开发经验中，曾作为核心开发参与项目架构与开发.
                  </div>
                </li>
                
                <li>
               	  <div class="jobbox_tt">
                   	  <h2>客户服务专员</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1、接受在线咨询与电话，引导接入与使用快银产品.<br />
                    2、接受商户业务处理请求，协助处理平台操作事宜.<br />
                  	3、登记受理消费者、商户投诉，提高满意度.<br />
                  	4、协助公司维护客户资源与客户关系.<br/>
                 我们期待您:<br />
                    1、大专及以上学历.<br />
                    2、性情稳定，处事冷静，条理性较强，语言表达能力与应变能力较强.<br />
                  	3、善于学习与总结，从问题中收获成长.<br/>
                  	4、有创业实干精神，能跟随创业公司共同成长.<br/>
                  	5、接受轮班。安排任务.
                  </div>
                </li>
                
                <li>
               	  <div class="jobbox_tt">
                   	  <h2>商务合作经理/大客户经理</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1、负责公司平台产品在对外合作渠道规划、渠道开发及商务合作.<br />
                    2、独立开发具有潜力及影响力的商务拓展渠道.<br />
                  	3、负责维护大客户关系，深挖合作机会.<br />
                 我们期待您:<br />
                    1、本科及以上学历，3年以上相关工作经验.<br />
                    2、熟悉第三方支付、电子商务行业.<br />
                  	3、熟悉某一领域的商家资源.<br/>
                  	4、寻找更高的起点，期待与创业型公司共同发展.
                  </div>
                </li>
                
                
                <li>
               	  <div class="jobbox_tt">
                   	  <h2>产品经理</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1、收集所负责产品的市场或行业信息、竞争对手信息等产品相关的市场信息.<br />
                    2、根据市场需求和产品运营需求，制订产品的实施路线图，管理产品的生命周期.<br />
                  	3、根据产品实施路线图，完成产品定义.<br />
                  	4、制订所负责产品的推广计划，指导和协助运营经理完成产品资料及全方位推广.<br/>
                 我们期待您:<br />
                    1、计算机及相关专业本科学历.<br />
                    2、4年或以上金融行业产品管理经验.<br />
                  	3、具备产品设计、产品推广等知识和技能.<br/>
                  	4、具备计划性与执行能力，擅长管理产品的生命周期.
                  </div>
                </li>
                
                <li>
               	  <div class="jobbox_tt">
                   	  <h2>商务拓展经理</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1、清晰掌握展览、会议活动等相关市场拓展渠道.<br />
                    2、熟悉电子商务平台的操作，主要通过网络、展会、会议等方式拓展客户.<br />
                  	3、熟悉广东地区展览的运作与操作方式，协助评估展会项目，确定合作内容与商务谈判.<br />
                  	4、定期执行展览、会议的执行与效果评估.<br/>
                 我们期待您:<br />
                    1、两年以上展览与会议拓展销售工作经验.<br />
                    2、沟通能力、观察能力较强，能充分拓展人脉关系.<br />
                  	3、良好的执行与自我驱动能力.<br/>
                  	4、能适应弹性工作制，主动积极通过展览、会议等方式宣传与推广公司品牌.
                  </div>
                </li>
                
            </ul>
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/joinUs/societyRecruit.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:58 GMT -->
</html>