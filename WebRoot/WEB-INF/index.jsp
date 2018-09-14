<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>代理后台 - 688手游网</title> 

<script src="<%=basePath %>/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/nb/images/login.js"></script>
<link href="<%=basePath%>/nb/css/login2.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/validform.css" rel="stylesheet" />
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
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
				},1000);
			}
		}
	});
});
</script>
</head>
<body>
<h1>688手游网<sup>V2017</sup></h1>
<div class="login" style="margin-top:50px;">    
    <div class="header">
        <div class="switch" id="switch"><a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">快速登录</a>			
			<div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>        
    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 235px;">
            <!--登录-->
            <div class="web_login" id="web_login">                             
               <div class="login-box">            
			<div class="login_form">				
				<form id="indexform" action="<%=basePath%>/login/login" name="loginform" accept-charset="utf-8" method="post" class="loginForm">
				<input type="hidden" name="did" value="0"/>
               <input type="hidden" name="to" value="log"/>
                <div class="uinArea" id="uinArea">
                <label class="input-tips" for="u">帐号：</label>
                <div class="inputOuter" id="uArea">                    
                    <input name="email" type="text" value="" class="inputstyle" id="username_id" placeholder="请输入邮箱" id="login-name" datatype="*" errormsg="请输入邮箱" />
                </div>
                </div>
                <div class="pwdArea" id="pwdArea">
               <label class="input-tips" for="p">密码：</label> 
               <div class="inputOuter" id="pArea">
                    <input name="ocx_password" type="hidden" />
					<input name="srand_num" type="hidden" />
					<input id="dis_btn" type="hidden" value="1" />
                    <input name="password" type="password" value="" class="inputstyle"  id="password_id" placeholder="请输入密码" id="login-pass" datatype="*" errormsg="请输入密码" />
                </div>
                </div>               
                <div style="padding-left:50px;margin-top:20px;"><input type="submit" value="登 录" style="width:150px;" class="button_blue"/></div>
              </form>
           </div>           
            	</div>               
            </div>
            <!--登录end-->
  </div>
</div>
<div class="jianyi">*7x24小时服务QQ：1904178861</div>
</body></html>