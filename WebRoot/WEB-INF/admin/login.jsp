<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/Plug.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("#loginform").Validform({
			tiptype : 1,
			postonce:true,
			ajaxPost:true,
			ignoreHidden:true,
			callback:function(data){
				if(data.status=="y"){
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=adminbasePath%>";
					},1000);
				}else{
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=adminbasePath%>";
					}, 1000);
				}
			}
		});
	});
</script>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	 <style type="text/css">
		.login-screen {
		  width:864px;
		  background-color: #1abc9c;
		  min-height: 330px;
		  max-width: 360px;
		  padding: 123px 199px 33px 306px;
		}
		.login-form {
		  width:359px;
		  height:213px;
		  padding-top:24px;
		  padding-right:50px;
		  padding-bottom:20px;
		  padding-left:23px;
		  position:relative;
		  background-color:rgb(237, 239, 241);
		}
	</style>
<![endif]-->
</head>
<body style="font-family: Microsoft YaHei">
	<div class="container">
		<div class="login">
			<div class="login-screen">
				<div class="login-icon">
					<img src="<%=basePath%>/images/icons/png/Clipboard.png" />
				</div>
				<div class="login-form">
					<form action="<%=adminbasePath%>/login/login" id="loginform" method="post">
						<div class="form-group">
							<input type="text" name="account" class="form-control login-field"
								value="" placeholder="请输入邮箱" id="login-name" datatype="*"
								errormsg="请输入邮箱" /> <label class="login-field-icon fui-user"
								for="login-name"></label>
						</div>
						<div class="form-group">
							<input type="password" name="password"
								class="form-control login-field" value="" placeholder="请输入密码"
								id="login-pass" datatype="*" errormsg="请输入密码" /> <label
								class="login-field-icon fui-lock" for="login-pass"></label>
						</div>
						<button type="submit" class="btn btn-primary btn-lg btn-block">登陆</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>