<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="navbar navbar-inverse" style="margin-bottom: -10px">
		<div class="navbar-collapse collapse navbar-collapse-01 container">
			<ul class="nav navbar-nav navbar-left">
				<li><a href="#fakelink"><img style="height: 20px"
						src="<%=basePath%>/images/esulogo.png"
						class="img-rounded img-responsive"></a></li>
				<li id="top1"><a href="<%=basePath%>/">首页</a></li>
				<li id="top2"><a href="<%=basePath%>/dynamic?pageNum=1">公告动态</a>
				<li id="top3"><a href="<%=basePath%>/safetysecurity">安全保障</a></li>
				<li id="top4"><a href="<%=basePath%>/indexcard">支持卡种</a></li>
				<li id="top5"><a href="<%=basePath%>/help">帮助</a></li>
				<li id="top6"><a href="<%=basePath%>/inTerFace">接口合作</a></li>
				<li id="top7"><a href="<%=basePath%>/about">关于我们</a></li>
			</ul>
		</div>
	</div>
</body>
</html>