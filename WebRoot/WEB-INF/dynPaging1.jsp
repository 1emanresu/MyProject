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
	$("#content").html('${dynamic.content}');
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
      		<div class="demo-type-example">
	      		<span class="demo-text-note"><c:out value="${dynamic.createtime}"></c:out></span>
	          <blockquote>
	          	<small><c:out value="${dynamic.title}"></c:out></small>
	            <p id="content"></p>
	          </blockquote>
      		</div>
      	</div><!-- /.demo-content-wide -->
      </div><!-- /.demo-row -->
      
	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>