<%@page import="com.vo.System_qq"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	List<System_qq> systemqqlist = System_qq.dao.find("select * from system_qq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("li#top1").addClass("active");
		$("#indexform").Validform({
			tiptype : 1,
			postonce:true,
			ajaxPost:true,
			ignoreHidden:true,
			callback:function(data){
				if(data.status=="y"){
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/user";
					},1000);
				}else{
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/";
					},1000);
				}
			}
		});
	});
</script>
<style type="text/css">
	.loginrewj{
		color: #bfc9ca;
	  	font-size: 13px;
	  	margin-left: 40px;
	}
</style>
</head>
<body style="font-family: Microsoft YaHei;overflow-x:hidden;">
	<div class="row demo-row">
		<div class="col-md-12">
			<jsp:include page="navigation.jsp"></jsp:include>
			<div class="bs-example">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>
					<div class="carousel-inner">
						<div class="item active">
							<img src="<%=basePath%>/images/end_user_focused1.jpg">
						</div>
						<div class="item">
							<img src="<%=basePath%>/images/focused2.jpg">
						</div>
						<div class="item">
							<img src="<%=basePath%>/images/focused3.jpg">
						</div>
					</div>
					<a class="left carousel-control" href="#carousel-example-generic"
						data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left"></span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<div class="row demo-tiles">
		<div class="col-md-3">
			<div class="tile">
				<div class="login-form">
					<form action="<%=basePath %>/login/login" id="indexform" method="post">
						<div class="form-group">
							<input type="text" name="email" class="form-control login-field" value=""
								placeholder="请输入邮箱" id="login-name" datatype="*"
								errormsg="请输入邮箱" /> <label class="login-field-icon fui-user"
								for="login-name"></label>
						</div>
						<div class="form-group">
							<input type="password" name="password" class="form-control login-field" value=""
								placeholder="请输入密码" id="login-pass" datatype="*"
								errormsg="请输入密码" /> <label class="login-field-icon fui-lock"
								for="login-pass"></label>
						</div>
						<button type="submit" class="btn btn-primary btn-lg btn-block">登陆</button>
					</form>
					<div style="margin-top: 15px">
					<a class="loginrewj" href="<%=basePath%>/register">注册</a> 
					<a style="float: left;" class="loginrewj" href="<%=basePath%>/forgetpassword">忘记密码</a>
					</div>
				</div>
		          <div class="share mrl palette palette-turquoise">
		            <ul>
		              <%
		              	System_qq  systemqq = null;
		              	for(int i=0; i<systemqqlist.size(); i++){
		              		if(i==4){
		              			break;
		              		}
		              		systemqq = systemqqlist.get(i);
		              %>
		              <li>
		                <label class="share-label" for="share-toggle2"><%=systemqq.getStr("qq_name") %>：</label>
		                <a target="_blank"
								href="http://wpa.qq.com/msgrd?v=3&uin=<%=systemqq.getStr("qq_number") %>&site=qq&menu=yes"><img
								border="0" src="http://wpa.qq.com/pa?p=2:<%=systemqq.getStr("qq_number") %>:41"
								alt="商务合作咨询－恭候您咨询" title="<%=systemss.getStr("system_firm") %>客服－恭候您咨询"></a>
		              </li>
		              <%
		            	}
		              %>
		              
		            </ul>
		          </div> <!-- /share -->
			</div>
		</div>

		<div class="col-md-3">
			<div class="tile">
				<img src="<%=basePath%>/images/icons/png/Infinity-Loop.png"
					alt="Infinity-Loop" class="tile-image">
				<h3 class="tile-title"><%=systemss.getStr("system_firm") %>动态</h3>
				<br>
				<p align="left" style="margin-left: 25px;height: 220px" >
					<c:forEach items="${dynamicList}" varStatus="l" var="dynamic">
						<strong>
							<a href="<%=basePath%>/dynamic/getDynamic?dynamicid=${dynamic.dynamicid}">
								<c:if test="${fn:length(dynamic.title)<=17}">
									<c:out value="${dynamic.title}"></c:out>
								</c:if>
								<c:if test="${fn:length(dynamic.title)>17}">
									<c:out value="${fn:substring(dynamic.title,0,16)}..."></c:out>
								</c:if>
							</a>
						</strong><br>
	      			</c:forEach>
				</p>
				<a class="btn btn-primary btn-large btn-block"
					href="<%=basePath%>/dynamic?pageNum=1">更多信息</a>
			</div>
		</div>

		<div class="col-md-3">
			<div class="tile">
				<img src="<%=basePath%>/images/icons/png/Pensils.png" alt="Pensils"
					class="tile-image">
				<h3 class="tile-title">大接口合作</h3>
				<br>
				<p align="left" style="height: 220px"><%=systemss.getStr("system_firm") %>提供专业稳定的储值卡寄售和交易接口，全程提供技术协助，欢迎有实力的合作伙伴来洽谈合作。<%=systemss.getStr("system_firm") %>卡盟卡充值接口适用于网络游戏支付、第三方卡支付网站、电子商务网站、手机支付应用等。<%=systemss.getStr("system_firm") %>卡盟可帮助您迅速搭建各类综合充值/缴费平台，也可以为您的网络应用提供卡充值功能。申请<%=systemss.getStr("system_firm") %>接口流程简单，操作方便。</p>
				<a class="btn btn-primary btn-large btn-block"
					href="<%=basePath%>/inTerFace">更多信息</a>
			</div>
		</div>

		<div class="col-md-3">
			<div class="tile">
				<img src="<%=basePath%>/images/icons/png/Chat.png" alt="Chat"
					class="tile-image">
				<h3 class="tile-title">了解<%=systemss.getStr("system_firm") %></h3>
				<br>
				<p align="left" style="height: 220px">全新模式创造新型网络生活方式
					<%=systemss.getStr("system_firm") %>卡盟是国内知名的储值卡寄售和网游的卡交易平台，隶属<%=systemss.getStr("system_company") %>，自2009年至今，<%=systemss.getStr("system_firm") %>卡盟已经为国内三十多家合作伙伴提供了卡充值接口服务，2011年，<%=systemss.getStr("system_firm") %>卡盟开始正式进入B2C收卡供货业务，面向个人用户提供供稳定、快速、安全、有保障的在线点卡供货、寄售、回收服务。</p>
				<a class="btn btn-primary btn-large btn-block"
					href="<%=basePath%>/about">更多信息</a>
			</div>

		</div>
	</div>
	<h3 align="center" class="demo-panel-title">
		主流点卡余额查询<small>(以下查询网址均为官方，千万不要去非点卡官方网站查询点卡余额，以免被盗)</small>
	</h3>
	<div class="row demo-tiles" style="">
		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_03.png" alt="Compas"
					class="tile-image big-illustration"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://www.10086.com/" target="_blank">官方查询入口</a>
			</div>
		</div>

		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_03.png" alt="Infinity-Loop"
					class="tile-image"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://www.10086.com/" target="_blank">官方查询入口</a>
			</div>
		</div>

		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_05.png" alt="Pensils"
					class="tile-image"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://pay.sdo.com/" target="_blank">官方查询入口</a>
			</div>
		</div>

		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_07.png" alt="Chat"
					class="tile-image"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://pay.ztgame.com/m.php?type=query_card" target="_blank">官方查询入口</a>
			</div>

		</div>

		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_09.png" alt="Chat"
					class="tile-image"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://pay.wanmei.com/e/jsp/query/queryUsedCard.jsp" target="_blank">官方查询入口</a>
			</div>

		</div>
		
		<div class="col-md-2">
			<div class="tile">
				<img src="<%=basePath%>/images/homeIcon_11.png" alt="Chat"
					class="tile-image"> <a
					class="btn btn-primary btn-large btn-block"
					href="http://iservice.10010.com/oftenInfo.html?menuId=000400010004" target="_blank">官方查询入口</a>
			</div>

		</div>
	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>