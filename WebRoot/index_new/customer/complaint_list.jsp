<%@page import="com.vo.Systemss"%>
<%@page import="com.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	Customer map = (Customer) request.getSession().getAttribute("session");
	if(map==null || map.equals("")){
		out.println("<script>alert('未登录用户不能提交，请先登录或注册！'</script>");
		response.sendRedirect("/index_new/customer/login.jsp");
	}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:36 GMT -->
<!-- Added by HTTrack -->
<!-- /Added by HTTrack -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<meta name="description" content="<%=systemss.getStr("system_keyword")%>" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/new/css/style.css" />
<link rel="stylesheet" href="<%=basePath%>/css/validform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/top.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/jquery-ui-1.9.2.custom.min.css" />
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/front.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jqnav.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/tab.js"></script>
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/ComplaintList/">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="starttime" value="${starttime}" />
		<input type="hidden" name="endtime" value="${endtime }" />		
	</form>
	<jsp:include page="../../WEB-INF/navigation1.jsp"></jsp:include>
	<div class="ban" style="background:url(<%=basePath%>/static/images/help_ban.jpg) center top no-repeat;"></div>
	<div class="mmain">
		<div class="wrap">
			<div class="mleft">

				<div class="lnav_tit">
					<p>
						<img src="<%=basePath%>/static/images/lnav_help.png" width="207"
							height="141" />
					</p>
					<h2>
						<span>投诉中心</span>Complaint Center
					</h2>
				</div>

				<div class="lnav">
					<ul class="lnavlist2">
						<li><a href="<%=basePath%>/index_new/customer/index.jsp">注册投诉账号</a></li>
						<li><a href="<%=basePath%>/index_new/customer/login.jsp">登录投诉系统</a></li>
						<li><a href="<%=basePath%>/index_new/customer/complaint.jsp">投诉资料提交</a></li>
						<li  class="hover" id="flow"><a href="<%=basePath%>/customer/ComplaintList?pageNum=1&numPerPage=30&starttime=&endtime=&id=&user_id=<%=map.get("id")%>">我的投诉清单</a></li>
						<li><a href="<%=basePath%>/customer/quit">退出</a></li>
					</ul>
				</div>
			</div>
			<!--mleft end-->
			<div class="mright">
				<div class="curr">
					<a href="#">首页</a> > 投诉中心 > <span class="red">投诉资料列表</span>
				</div>
				<div class="mcon">
					<div class="mr_tit">
						<h2>投诉资料列表</h2>
					</div>					
					<div class="about">
						<table class="table" width="600" layoutH="138">
			<thead>
				<tr>
					<th>序列号</th>					
					<th>投诉标题</th>
					<th>提交时间</th>
					<th>是否处理</th>					
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${perlist}" var="per">
					<tr target="id" rel="${per.id }">
						<td><c:out value="${per.id}"></c:out></td>											
						<td><c:out value="${per.title}"></c:out></td>
						<td><c:out value="${per.record_date}"></c:out></td>
						<td>
							<c:if test="${per.status_customer=='1'}">已处理</c:if>
							<c:if test="${per.status_customer=='0'}">处理中</c:if>
						</td>	
						<td>
						 	<a title="查看" target="dialog" rel="person${per.id}" href="/customer/showCustomer?id=${per.id}" class="btnView"  width="1178" height="721">查看</a>
							<%if(map.getInt("ifrock")==2){%>						 
						 	<a class="btnEdit" title="回复" href="/customer/showCustomerState?id=${per.id}" target="dialog" rel="perstate${per.id}" width="410" height="200"><span>回复</span></a>
						 	<%}%>
						 </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
					</div>
				</div>
				<!--mright end-->
				<div class="cls"></div>
			</div>
		</div>
	</div>


		<!--foot s-->

		<div class="cls"></div>
		<jsp:include page="../../WEB-INF/page/footer1.jsp"></jsp:include>
</body>
<!-- 菜单选中结束 -->

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:37 GMT -->
</html>
<%
	}
%>