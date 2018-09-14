<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$("li#top2").addClass("active");
	var pageNum = ${pageNum};
	var totalpage = ${totalpage};
	if(pageNum==1){
		$("#previous").addClass("disabled");
	}else{
		$("#previous").removeClass("disabled");
	}
	if (pageNum == totalpage) {
		$("#next").addClass("disabled");
	} else {
		$("#next").removeClass("disabled");
	}
});
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<%-- <div class="row demo-row">
		<div class="col-md-12">
			<jsp:include page="navigation.jsp"></jsp:include>
		</div>
	</div> --%>
	<div class="container">
		<div class="demo-row typography-row">
      	<div class="demo-title">
      		<h3 class="demo-panel-title">公告动态</h3>
      	</div>
      	<div class="demo-content">
      		<c:forEach items="${dynamiclist}" varStatus="l" var="dynamic">
      		<div class="demo-type-example">
	      		<span class="demo-text-note"><c:out value="${dynamic.createtime}"></c:out></span>
	          <blockquote>
	          	<small><a href="<%=basePath%>/dynamic/getDynamic?dynamicid=${dynamic.dynamicid}"><c:out value="${dynamic.title}"></c:out></a></small>
	          </blockquote>
      		</div>
      		</c:forEach>
      		<div class="demo-type-example">
				<a id="previous" href="<%=basePath%>/dynamic?pageNum=${pageNum-1}" class="btn btn-primary fui-arrow-left"></a>
		        <a href="#fakelink" class="btn btn-default disabled">${pageNum}</a>
		        <a id="next" href="<%=basePath%>/dynamic?pageNum=${pageNum+1}" class="btn btn-primary fui-arrow-right"></a>
      		</div>
      	</div><!-- /.demo-content-wide -->
      </div><!-- /.demo-row -->
      
	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>