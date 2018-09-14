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

</head>
<body>
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
						<li class="hover" id="flow"><a href="<%=basePath%>/customer/ComplaintList?pageNum=1&numPerPage=30&starttime=&endtime=&id=&user_id=<%=map.get("id")%>">我的投诉清单</a></li>
						<li><a href="<%=basePath%>/customer/quit">退出</a></li>
					</ul>
				</div>
			</div>
			<!--mleft end-->
			<div class="mright">
				<div class="curr">
					<a href="#">首页</a> > 投诉中心 > <span class="red">客服投诉处理</span>
				</div>
				<div class="mcon">
					<div class="mr_tit">
						<h2>客服投诉处理</h2>
					</div>
					<form class="form-horizontal" id="registerform" action="<%=basePath%>/customer/UpdateComplaint" name="myform" method="post">
						<div class="about">
								<ul class="safelist">									
									<li>您的姓名：${realname }<br/></li>
									<li>身份证号：${cardid }<br /></li>																										
									<li>订 单 号 ：${orderid}<br /></li>									
									<li>投诉标题：${title }<br/> </li>
									<li>期望结果：${result_user }<br/></li>
									<li>问题描述：<br></br>${content}<br/></li>
									<li>客服回复：<br></br>
									<textarea name="result_sys" id="result_sys" style="width:500px;height:150px;text-align:left;font-size:14px;padding-top:0">
									  <c:if test="${status_customer=='1'}"><c:out value="${result_sys}"></c:out></c:if><c:if test="${status_customer=='0'}"><c:out value=""></c:out></c:if>
									  </textarea>
									</li>
									<li>
									<input type="hidden" name="id" value="${id }" /><br/>
									<input type="submit" name="submit" class="btn" value=" 提交 " /><br/></li>
								</ul>
						</div>
					</form>
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