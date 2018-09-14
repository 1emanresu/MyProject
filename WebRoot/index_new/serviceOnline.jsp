<%@page import="com.vo.System_qq"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	List<System_qq> systemqqlist = System_qq.dao
			.find("select * from system_qq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script type="text/javascript" src="<%=basePath%>/static/js/QQOnline.js"></script>
</head>
<body>

	<jsp:include page="../WEB-INF/navigation1.jsp"></jsp:include>

	<!-- 	<div class="ban">
		<img src="static/images/solution_ban.jpg" />
	</div> -->
	<div class="ban"
		style="background: url(../static/images/banner_lianxi.jpg) center top no-repeat;"></div>
	<div class="mmain">
		<div class="wrap">
			<div class="mleft">

				<div class="lnav_tit">
					<p>
						<img src="../static/images/lnav_tit7.png" width="207" height="141" />
					</p>
					<h2>
						<span>在线客服</span><b class="b">Customer service</b>
					</h2>
				</div>

			</div>
			<!--mleft end-->
			<div class="mright">
				<div class="curr">
					<a href="#">首页</a> > <span class="red">在线客服</span>
				</div>
				<div class="mcon">
					<div class="mr_tit">
						<h2>在线客服</h2>
					</div>
					<div class="news">
						<ul class="onlinelist">
							<li>
							<p>
								<%
									System_qq systemqq = null;
									for (int i = 0; i < systemqqlist.size(); i++) {
										systemqq = systemqqlist.get(i);
								%>
								
									<span class="qq"><a target="_blank"
										href="http://wpa.qq.com/msgrd?v=3&uin=<%=systemqq.getStr("qq_number")%>&site=qq&menu=yes"><img
											qq="<%=systemqq.getStr("qq_number")%>"
											src="../static/images/qq1.jpg" width="23" height="22"
											align="absmiddle" /> <%=systemqq.getStr("qq_name")%></a></span>

									<%
										}
									%>
								</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!--mright end-->
			<div class="cls"></div>
		</div>
	</div>



	<!--foot s-->

	<div class="cls"></div>
	<jsp:include page="../WEB-INF/page/footer1.jsp"></jsp:include>
</body>
</html>