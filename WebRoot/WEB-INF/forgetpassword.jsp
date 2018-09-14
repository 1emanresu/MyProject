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
	$(document).ready(function() {
		var num = 300;
		var interv = "";
		function changeTime() {
			if (num > 0) {
				$("input#sms").val(num+'秒');
				num = num - 1;
			}else{
				clearInterval(interv);
				$("input#sms").removeAttr("disabled");
				$("input#sms").val("重新获取验证码");
			}
		}
		$("input#sms").click(function(){
			var phone = $("#phone").val();
			$.ajax({
				type: "post", 
				url: "<%=basePath%>/register/getSms", 
				data: "phone="+phone ,
				success: function(msg){
					if(msg=="100"){
						num = 300;
						$("input#sms").attr("disabled","disabled");
						interv = setInterval(changeTime,1000);
					}else{
						alert(msg);
					}
				}
			}); 
			});
		$("#huge").change(function() {
			var huge = $("#huge").val();
			if(huge==0){
				$("#idcard").fadeIn();
				$("#licence").fadeOut();
			}else{
				$("#idcard").fadeOut();
				$("#licence").fadeIn();
			}
		});
		 var demo = $("#forgetpasswordform").Validform({
			tiptype:2,
			postonce:true,
			ajaxPost:true,
			ignoreHidden:true,
			callback:function(data){
				if(data.status=="y"){
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/";
					},1000);
				}else{
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/forgetpassword";
															}, 1000);
												}
											}
										});

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
				<h3 class="demo-panel-title">更改密码</h3>
			</div>
			<div class="demo-content">
				<p>
					填写注册的手机号<span class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改密码<span
						class="fui-arrow-right"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改成功
				</p>
				<form class="form-horizontal" id="forgetpasswordform"
					action="<%=basePath%>/register/upPassword" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label">手机号码：</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="phone" name="phone"
								datatype="m" ajaxurl="<%=basePath%>/register/getphotepass" nullmsg="请填写手机号码！" errormsg="您填写的手机号码不正确 ！"
								placeholder="手机号码">
						</div>
						<div align="left" class="col-sm-5">
							<span class="help-block Validform_checktip"> * </span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">短信验证：</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" datatype="*"
								ajaxurl="<%=basePath%>/register/valiSms" nullmsg="请填写验证号！"
								errormsg="您填写的验证号不对不正确 ！" placeholder="验证码">
						</div>
						<div align="left" class="col-sm-2">
							<span class="help-block Validform_checktip"> * </span>
						</div>
						<div class="col-sm-3">
							<input type="button" id="sms" class="btn btn-info" value="获取验证码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">登陆密码：</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" name="password"
								datatype="*6-20" errormsg="密码范围在6~20位之间！" placeholder="登陆密码">
						</div>
						<div align="left" class="col-sm-5">
							<span class="help-block Validform_checktip"> *
								6-20个字符，请注意区分大小写</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">确认密码：</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" datatype="*"
								recheck="password" errormsg="您两次输入的账号密码不一致！" placeholder="确认密码">
						</div>
						<div align="left" class="col-sm-5">
							<span class="help-block Validform_checktip"> * 请与前一密码保持一致</span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" class="btn btn-primary"></input>
						</div>
					</div>
				</form>
			</div>
			<!-- /.demo-content-wide -->
		</div>
		<!-- /.demo-row -->

	</div>
	<%-- <jsp:include page="page/footer.jsp"></jsp:include> --%>
</body>
</html>