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
         <li><a href="societyRecruit.jsp">社会招聘</a></li>
         <li class="hover"><a href="campusRecruit.jsp">校园招聘</a></li>
         <li><a href="snsRecruit.jsp">SNS招聘</a></li>
       </ul>
      </div>
      
    </div>
    <!--mleft end-->
    <div class="mright">
      <div class="curr"><a href="#">首页</a> > 加入我们 > <span class="red">校园招聘</span></div>
      <div class="mcon">
        <div class="mr_tit"><h2>校园招聘</h2></div>
        <div class="joincon2">
               <div class="job1">
             <div class="jobtitle"><span class="job_t4">招聘流程 <i>process</i></span></div>
               <div class="process"><img src="<%=basePath%>/static/newImages/process.jpg" width="605" height="147" /></div>
            </div>
          <div class="job1 job1con">
            <div class="jobtitle"><span class="job_t2">招聘职位 <i>job</i></span></div>
            <ul class="joblist2">
            
                <li class="hover">
               	  <div class="jobbox_tt">
                   	  <h2>客户服务专员</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1. 接受在线咨询与电话，引导接入与使用快银产品.<br />
                    2. 接受商户业务处理请求，协助处理平台操作事宜.<br />
                  	3. 登记受理消费者、商户投诉，提高满意度.<br />
                  	4. 协助公司维护客户资源与客户关系.<br />
                  我们期待您:<br />
                    1. 大专及以上学历.<br />
                    2. 性情稳定，处事冷静，条理性较强，语言表达能力与应变能力较强.<br />
                    3. 善于学习与总结，从问题中收获成长.<br />
                    4. 有创业实干精神，能跟随创业公司共同成长.<br />
                  	5. 接受轮班,安排任务.
                  
                  </div>
                </li>
           
               	<li>
               	  <div class="jobbox_tt">
                   	  <h2>销售</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                    工作职责:<br />
                    1. 收集整理客户资料.<br />
                    2. 电话拜访与服务.<br />
                  	3. 整理与挖掘市场资料.<br />
                  	4、协助商务部门内务管理<br />
                  我们期待您:<br />
                    1. 本科及以上学历.<br />
                    2. 良好的沟通、协调、商务谈判和公关能力.<br />
                  	3. 强烈的团队合作及服务意识，全力支持团队成员.
                  </div>
                </li>
                
                <li>
               	  <div class="jobbox_tt">
                   	  <h2>开发工程师</h2>
                      <span><a href="mailto:hr@kuaiyinpay.com">应聘该职位</a></span>
                      <i></i>
                  </div>
                  <div class="zzbox">
                  我们期待您:<br />
                    1. 熟练掌握JAVA、C++等开发技术 .<br />
                    2. 熟练掌握MySQL和Oracle等常用数据库.<br />
                  	3. 良好的沟通能力和团队合作精神.
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

<!-- Mirrored from www.kuaiyinpay.com/index_new/joinUs/campusRecruit.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:59 GMT -->
</html>