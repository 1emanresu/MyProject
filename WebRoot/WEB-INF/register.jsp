<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
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
			if(phone=="" || phone==null){
				alert("请先填写手机号码,再获取短信");
				return;
			}
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
		 var demo = $("#registerform").Validform({
			tiptype:2,
			postonce:true,
			ajaxPost:true,
			ignoreHidden:true,
			datatype:{//传入自定义datatype类型【方式二】;
				"idcard":function(gets,obj,curform,datatype){
					//该方法由佚名网友提供;
				
					var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
					var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
				
					if (gets.length == 15) {   
						return isValidityBrithBy15IdCard(gets);   
					}else if (gets.length == 18){   
						var a_idCard = gets.split("");// 得到身份证数组   
						if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
							return true;   
						}   
						return false;
					}
					return false;
					
					function isTrueValidateCodeBy18IdCard(a_idCard) {   
						var sum = 0; // 声明加权求和变量   
						if (a_idCard[17].toLowerCase() == 'x') {   
							a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
						}   
						for ( var i = 0; i < 17; i++) {   
							sum += Wi[i] * a_idCard[i];// 加权求和   
						}   
						valCodePosition = sum % 11;// 得到验证码所位置   
						if (a_idCard[17] == ValideCode[valCodePosition]) {   
							return true;   
						}
						return false;   
					}
					
					function isValidityBrithBy18IdCard(idCard18){   
						var year = idCard18.substring(6,10);   
						var month = idCard18.substring(10,12);   
						var day = idCard18.substring(12,14);   
						var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
						// 这里用getFullYear()获取年份，避免千年虫问题   
						if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
							return false;   
						}
						return true;   
					}
					
					function isValidityBrithBy15IdCard(idCard15){   
						var year =  idCard15.substring(6,8);   
						var month = idCard15.substring(8,10);   
						var day = idCard15.substring(10,12);
						var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
						// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
						if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
							return false;   
						}
						return true;
					}
					
				}
			},
			callback:function(data){
				if(data.status=="y"){
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/";
					},1000);
				}else{
					setTimeout(function(){
						$.Hidemsg(); 
						window.location.href="<%=basePath%>/register";
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
		<div class="row demo-tiles">
			<div class="col-md-11">
				<div class="tile">
					<form class="form-horizontal" id="registerform"
						action="<%=basePath%>/register/enroll" method="post">
						<h1 class="demo-panel-title" align="left">用户注册（请完整填写所有项目）</h1>
						<div class="form-group">
							<label class="col-sm-2 control-label">电子邮件：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="email"
									datatype="*,e" ajaxurl="<%=basePath%>/register/getEmail"
									nullmsg="请输入邮箱！" errormsg="请正确填写邮箱！" placeholder="电子邮件">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> *
									注册后不能修改，用于登录用户名</span>
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
							<label class="col-sm-2 control-label">支付密码：</label>
							<div class="col-sm-5">
								<input type="password" class="form-control" name="payment"
									datatype="*6-15" errormsg="密码范围在6~15位之间！" placeholder="支付密码">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * 申请支付的密码</span>
							</div>
						</div>
						<div class="form-group">
							<span class="help-block">接入网站信息</span> <label
								class="col-sm-2 control-label">网站名称：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="webName"
									datatype="*" errormsg="请输入网站名称" placeholder="网站名称">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * 个人填写姓名
									公司填写公司名称</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">网站网址：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="website"
									datatype="url" nullmsg="请输入网址！" errormsg="请正确填写网址！"
									placeholder="网站网址">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group">
							<span class="help-block">个人/企业信息</span> <label
								class="col-sm-2 control-label">所属行业：</label>
							<div class="col-sm-5">
								<select name="herolist" class="select-block">
									<option value="0" selected="selected">党政机关</option>
									<option value="1">商业</option>
									<option value="2">制造业</option>
									<option value="3">服务业</option>
									<option value="4">农业牧渔业</option>
									<option value="5">其它行业</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">用户类型：</label>
							<div class="col-sm-5">
								<select name="huge" id="huge" class="select-block">
									<option value="0" selected="selected">个人</option>
									<option value="1">企业</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">公司/个人：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="name" datatype="*"
									errormsg="请输入个人或公司名称" placeholder="公司/个人">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * 个人填写姓名
									公司填写公司名称</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">联 系 人：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="contacts"
									datatype="*" errormsg="请输入联系人名称" placeholder="联 系 人">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group" id="idcard">
							<label class="col-sm-2 control-label">身 份 证：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="idcard"
									datatype="idcard" nullmsg="请填写身份证号码！" errormsg="您填写的身份证号码不对！"
									placeholder="身 份 证">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group" id="licence" style="display: none;">
							<label class="col-sm-2 control-label">营业执照：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="licence"
									datatype="*15-15" errormsg="您填写的营业执照不对！" placeholder="营业执照">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">联系地址：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="address"
									datatype="*" errormsg="请填写联系地址" placeholder="联系地址">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">手机号码：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="phone" name="phone"
									datatype="m" ajaxurl="<%=basePath%>/register/getPhote" nullmsg="请填写手机号码！" errormsg="您填写的手机号码不正确 ！"
									placeholder="手机号码">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">短信验证：</label>
							<div class="col-sm-3">
								<input type="text" class="form-control"
									datatype="*" ajaxurl="<%=basePath%>/register/valiSms" nullmsg="请填写验证号！" errormsg="您填写的验证号不对不正确 ！"
									placeholder="验证码">
							</div>
							<div align="left" class="col-sm-2">
								<span class="help-block Validform_checktip"> * </span>
							</div>
							<div class="col-sm-3">
								<input type="button" id="sms" class="btn btn-info" value="获取验证码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">QQ号码：</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="qq" datatype="n"
									nullmsg="请填写QQ号码！" errormsg="您填写的QQ号码不正确 ！" placeholder="QQ号码">
							</div>
							<div align="left" class="col-sm-5">
								<span class="help-block Validform_checktip"> * </span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-5">
								<div class="checkbox">
									<label> <input type="checkbox" checked=""
										data-toggle="switch" />同意注册协议 点击查看注册协议
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" class="btn btn-primary"></input>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%-- <jsp:include page="page/footer.jsp"></jsp:include> --%>
</body>
</html>