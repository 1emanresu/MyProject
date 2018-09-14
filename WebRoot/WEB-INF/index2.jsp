<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<META name="Keywords" content="<%=systemss.getStr("system_keyword")%>" />
<meta name="description"
	content="<%=systemss.getStr("system_keyword")%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/css.css" />
<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
<style>
.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	bottom: 0%;
	left: 0%;
	width: 100%;
	height: 115%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .50;
	filter: alpha(opacity = 50);
}

.white_content {
	display: none;
	position: absolute;
	top: 25%;
	left: 30%;
	width: 100%;
	height: auto;
	padding: 0px;
	z-index: 1002;
	overflow: auto;
}
</style>
<script src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script type=text/javascript src="<%=basePath%>/js/slides.jquery.js"></script>
<script type=text/javascript src="<%=basePath%>/js/manage.sub.min.js"></script>
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
<script>
	$(function(){
		//滚动Banner图片的显示
		$('#slides').slides({
			preload: false,
			preloadImage: '/images/loading.gif',
			effect: 'fade',
			slideSpeed: 400,
			fadeSpeed: 100,
			play: 2000,
			pause: 100,
			hoverPause: true
		});
        	//$('#js-news').ticker();
    	});
	$(document).ready(function() {
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
					}, 1000);
				}
			}
		});
	});
</script>
<script src="<%=basePath%>/js/softkeyboard.js" language="JavaScript"></script>
<script src="<%=basePath%>/js/MD5.js" language="JavaScript"></script>
</head>

<body>
	<div id="wapper">
		<div id="header">
			<div class="top">
				<div class="logo fl">
					<h1 class="clearfix"><a href="#" hidden="#"></a></h1>
					<span></span>
				</div>
				<div class="nav fr">
					<ul>
						<li></li>
						<li></li>
						<li class="nav-in"></li>
					</ul>
				</div>
			</div>
		</div>

		<!-- 滚动图片 -->
		<div id="warp">
			<div id="slides" class="banner">
				<div class="login">
					<div class="login-box">
						<div id=con>
							<UL id=tags>
								<LI class="selectTag"><A onClick="selectTag('tagContent0',this)" href="javascript:void(0)">立即登录</A></LI>
								<LI><A href="<%=basePath%>/register" class="btn2">免费注册</A></LI>
							</UL>
							<div id=tagContent>
								<div class="tagContent selectTag" id="tagContent0">
									<div class="m-230 clearfix">
										<span id="error_mr"></span> <span class="cue" id="error_ts"
											style="display: none;"></span>
										<form action="<%=basePath%>/login/login" id="indexform" method="post">
											<input name="moni" type="hidden" value="" />
											<table width="230" border="0" cellspacing="0" cellpadding="0"
												class="fl" style="margin-top: 5px;">
												<tr>
													<td colspan="2"><input name="email" type="text"
														datatype="*" errormsg="请输入邮箱" value="手机号/用户名/邮箱" size="25"
														class="ipt-220" onfocus="this.value=''"
														onblur="javascript:if(this.value==''){this.value='手机号/用户名/邮箱'}else{document.getElementById('error_mr').style.display='block';document.getElementById('error_ts').style.display='none';}" /></td>
												</tr>
												<tr>
													<td colspan="2"><input name="showPwd" type="text"
														id="showPwd" value="请输入您的密码" size="25" class="ipt-220"
														onfocus=" if(this.value != '请输入您的密码') return;this.style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').value='';document.getElementById('password').focus();" />
														<input name="password" type="password" datatype="*" errormsg="请输入密码" id="password"
														value="" size="25" class="ipt-220" style="display: none;"
														onblur=" if(this.value !=''){document.getElementById('error_mr').style.display='block';document.getElementById('error_ts').style.display='none'; return;}this.style.display='none';document.getElementById('showPwd').style.display='block';document.getElementById('showPwd').value='请输入您的密码';" />

													</td>
												</tr>

												<tr>
													<td colspan="2"><span class="f3"></span><span
														class="fr"><a href="<%=basePath%>/forgetpassword">忘记密码？</a></span></td>
												</tr>
												<tr>
													<td colspan="2"><input name="submit" type="submit"
														id="submitButton" value="登 录" size="25" class="btn-220"
														style="width: 220px; height: 38px; line-height: 36px;" /></td>
												</tr>
											</table>
											<input name="vcode" type="hidden" id="vcode" value="" />
										</form>
									</div>
								</div>
								<div class=tagContent id=tagContent1>
									<div class="m-230 clearfix">
										<span id="error_zcmr"></span> <span class="ts" id="error_zcts"
											style="display: none;"></span>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="bannerImg">
					<div class="slides_container">
						<div id="banner_pic_1">
							<a href="#" target="_blank"><img src="images/banner.png"
								width="980" height="422" /></a>
						</div>
						<div style="DISPLAY: none" id="banner_pic_2">
							<a href="#" target="_blank"><img src="images/banner1.png"
								width="980" height="422" /></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 滚动图片 -->

		<div class="cl"></div>
		<div class="main">

			<div class="fun">
				<h3>
					为什么有超过<span>那么多</span>人使用我们的网站
				</h3>
				<div class="fun1 clearfix">
					<div class="fun1-m">
						<span class="clearfix"><img src="images/icon-3.png"
							width="60" height="66" /></span>
						<p class="clearfix">
							<span>安全便捷</span>多一“点”安全
						</p>
					</div>
					<div class="fun1-m">
						<span class="clearfix"><img src="images/icon-4.png"
							width="66" height="66" /></span>
						<p class="clearfix">
							<span>千万点卡</span>轻松一“点”，各式点卡
						</p>
					</div>
					<div class="fun1-m">
						<span class="clearfix"><img src="images/icon-5.png"
							width="67" height="66" /></span>
						<p class="clearfix">
							<span>即时结算</span>适合一“点”忙碌的你
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<p class="p-2">
				Copyright ? 2009-2013
				<%=systemss.getStr("system_company")%>版权所有<br /> 网站备案号：<%=systemss.getStr("system_filings")%><br />
			</p>
		</div>
	</div>
	<div class="backToTop"></div>
</body>
</html>

