<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("li#top6").addClass("active");
		$("#content").html('${dynamic.content}');
	});
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<div class="row demo-row">
		<div class="col-md-12">
			<jsp:include page="navigation.jsp"></jsp:include>
		</div>
	</div>
	<div class="container">
		<div class="demo-row typography-row">
			<div class="demo-title">
				<h3 class="demo-panel-title">接口合作</h3>
			</div>
			<div class="demo-content">
				<p><%=systemss.getStr("system_firm") %>提供专业稳定的卡寄售和消耗接口，全程提供技术协助，欢迎有实力的合作伙伴来洽谈合作。<%=systemss.getStr("system_firm") %>卡支付接口适用于网络游戏支付、第三方卡支付网站、电子商务网站、手机支付应用等。</p>
				<p><%=systemss.getStr("system_firm") %>可帮助您迅速搭建各类综合充值/缴费平台，也可以为您的网络应用提供卡支付功能。申请<%=systemss.getStr("system_firm") %>卡支付接口流程简单，操作方便。</p>
				<h6 class="demo-panel-title"><%=systemss.getStr("system_firm") %>结算接口的优势有：</h6>
				<div class="demo-type-example">
					<span class="demo-text-note">1</span>
					<blockquote>
						<p>快速寄售，最快5秒内完成交易；</p>
					</blockquote>
				</div>
				<div class="demo-type-example">
					<span class="demo-text-note">2</span>
					<blockquote>
						<p>全国寄售最高价，合作商可以获得更多的收益；</p>
					</blockquote>
				</div>
				<div class="demo-type-example">
					<span class="demo-text-note">3</span>
					<blockquote>
						<p>结算免手续费，行内独一家；</p>
					</blockquote>
				</div>
				<div class="demo-type-example">
					<span class="demo-text-note">4</span>
					<blockquote>
						<p>绑定结算账户的用户，第二天绑定生效以后，就可以随时结算；</p>
					</blockquote>
				</div>
				<div class="demo-type-example">
					<span class="demo-text-note">5</span>
					<blockquote>
						<p>24小时在线客服/技术支持服务；随时随地解答您的疑问；</p>
					</blockquote>
				</div>
				<div class="demo-type-example">
					<span class="demo-text-note">6</span>
					<blockquote>
						<p>专业的技术团队确保系统安全、稳定、流畅运行。</p>
					</blockquote>
				</div>
				<h6 class="demo-panel-title">接口的对接流程：</h6>
				<p>
					注册<span class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请<span
						class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核<span
						class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提供开发文档<span
						class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术全程支持<span
						class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时时结算统计
				</p>
			</div>
			<!-- /.demo-content-wide -->
		</div>
		<!-- /.demo-row -->

	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>