<%@page import="com.vo.Systemss"%>
<%@page import="com.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	Customer map = (Customer) request.getSession().getAttribute("session");
	if(map==null || map.equals("")){
		out.println("<script>alert('未登录用户不能提交，请先登录或注册！'</script>");
		response.sendRedirect("/index_new/customer/login.jsp");
	}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:36 GMT -->
<!-- Added by HTTrack -->
<!-- /Added by HTTrack -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=systemss.getStr("system_webName")%></title>
<meta name="description" content="<%=systemss.getStr("system_keyword")%>" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/new/css/style.css" />
<link rel="stylesheet" href="<%=basePath%>/css/validform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/top.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/jquery-ui-1.9.2.custom.min.css" />
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/front.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jqnav.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/tab.js"></script>

<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>

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
	});
</script>
<script type="text/javascript">
function check(){	
    var file=document.getElementById("imgFile").value;     
    if(file=="" || file==null){
        alert("请选择需要上传的文件！");
        return false;
    }else{
        var okk=true;
        $.ajax({
        	url:"<%=basePath%>/customer/imageUpload",
            type:"post",            
            dataType:"json",
            success:function(data){            	
            	if(data.status=="n"){      			
        			alert(data.info);
        		}else{
        			imgurl="<%=basePath %>/"+data.status;
        			lasturl = "<img alt='身份证正面示例' src='"+imgurl+"' width='240' height='180'/>";
        			document.getElementById("mycard").innerHTML=lasturl;
        			return false;
        		}
            },
            
        });
        return false;
    }
}

function formvalidate(){
	var orderid = document.getElementById("orderid").value;
	var price = document.getElementById("price").value;
	var title = document.getElementById("title").value;
	var result_user = document.getElementById("result_user").value;
	var validate_id = document.getElementById("validate_id").value;
	if(validate_id==""){
		alert("请输入手机验证码");
		return false;
	}
	$.ajax({ 
		type: "post", 
		url: "<%=basePath%>/customer/valiSms",
		data:"validate_id="+validate_id,
		success: function(msg){			
			if(msg=="y"){					
				$("#msg").html("验证码已通过");					
			}else{				
				$("#msg").html("验证码错误");
				return false;
			}
		} 
	});	
	
	validate = document.getElementById("msg").innerHTML;
	if(validate=="验证码错误"){
		return false;
	}	
	if(orderid==""){
		alert("请输入订单号");
		return false;
	}
	if(price==""){
		alert("请输入订单金额！");
		return false;
	}
	if(title==""){
		alert("请输入投诉标题");
		return false;
	}
	if(result_user==""){
		alert("请输入期望结果");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
	<jsp:include page="../../WEB-INF/navigation1.jsp"></jsp:include>
	<div class="ban" style="background:url(<%=basePath%>/static/images/help_ban.jpg) center top no-repeat;"></div>
	<div class="mmain">
		<div class="wrap">
			<div class="mleft">

				<div class="lnav_tit">
					<p>
						<img src="<%=basePath%>/static/images/lnav_help.png" width="207"
							height="141" />
					</p>
					<h2>
						<span>投诉中心</span>Complaint Center
					</h2>
				</div>

				<div class="lnav">
					<ul class="lnavlist2">
						<li><a href="<%=basePath%>/index_new/customer/index.jsp">注册投诉账号</a></li>
						<li><a href="<%=basePath%>/index_new/customer/login.jsp">登录投诉系统</a></li>
						<li class="hover" id="flow"><a href="<%=basePath%>/index_new/customer/complaint.jsp">投诉资料提交</a></li>
						<li><a href="<%=basePath%>/customer/ComplaintList?pageNum=1&numPerPage=30&starttime=&endtime=&id=&user_id=<%=map.get("id")%>">我的投诉清单</a></li>
						<li><a href="<%=basePath%>/customer/quit">退出</a></li>
					</ul>
				</div>
			</div>
			<!--mleft end-->
			<div class="mright">
				<div class="curr">
					<a href="#">首页</a> > 投诉中心 > <span class="red">提交投诉资料</span>
				</div>
				<div class="mcon">
					<div class="mr_tit">
						<h2>提交投诉资料</h2>
					</div>
					<form class="form-horizontal" id="registerform" action="<%=basePath%>/customer/SaveComplaint" name="myform" method="post" onsubmit="return formvalidate()">
						<div class="about">
							<div class="about">
								<ul class="safelist">									
									<li>您的姓名：<input type="text" name="real_name" id="real_name" value="<%=map.get("realname")%>" disabled="disabled"/>  *不可修改<br />
										<input type="hidden" name="user_id" id="user_id" value="<%=map.get("id")%>"/>
										<input type="hidden" name="realname" id="realname" value="<%=map.get("realname")%>"/>
									</li>
									<li>身份证号：<input type="text" name="card_id" id="card_id" value="<%=map.get("cardid")%>" disabled="disabled"/>  *不可修改<br />
										<input type="hidden" name="cardid" id="cardid" value="<%=map.get("cardid")%>"/>
									</li>
									<li>
									身份证上传:<br></br>														
									<input type="file" name="image" id="imageFile" />
									<button id="upload" onclick="return false;">身份证上传</button>
									<!-- 存储图片地址，并显示图片 -->
									<input type="hidden" name="pictureSrc" id="pictureSrc" />
									<div id="show"></div>
									<!-- 图片上传js文件,放到最后加载 -->
									<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
									<script type="text/javascript" src="<%=basePath%>/js/ajaxfileupload.js"></script>
									<script type="text/javascript" src="<%=basePath%>/js/upload.js"></script>
									身份证上传示例:<br></br>
									<img alt="身份证正面示例" src="<%=basePath %>/img/id1.png" width="240" height="180"/>
									<img alt="身份证反面示例" src="<%=basePath %>/img/id2.png" width="240" height="180"/><br></br>
									</li>
									 									
									<li>
										手机号码：
										<input type="text" class="form-control" id="phone" name="phone" datatype="m" value="<%=map.get("telno")%>" disabled="disabled"></input>
									</li>
									<li>
										短信验证：
										<input type="text" name="validate_id" id="validate_id" value=""/>															
										<input type="button" id="sms" value="获取验证码"/> &nbsp;*	
										<span id="msg" style="color:red"> *</span>																	
									</li>									
									<li>订 单 号 ：&nbsp;<input type="text" name="orderid" id="orderid" value=""/><br /></li>
									<li>订单金额 ：<input type="text" name="price" id="price" value=""/><br /></li>
									<li>
									充值截图:<br></br>
									<input type="file" name="image1" id="imageFile1" />
									<button id="upload1" onclick="return false;">充值截图上传</button>
									<!-- 存储图片地址，并显示图片 -->
									<input type="hidden" name="pic_charge" id="pic_charge" />
									<div id="showImg"></div>
									<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
									<script type="text/javascript" src="<%=basePath%>/js/ajaxfileupload.js"></script>    								
									<script type="text/javascript" src="<%=basePath%>/js/upload1.js"></script>
									</li>
									<li>投诉标题：<input type="text" name="title" id="title" value=""/><br/>
										<input type="hidden" name="img_orderid" id="img_orderid" value="img_orderid"/> 
									</li>
									<li>期望结果：<input type="text" name="result_user" id="result_user" value=""/><br/></li>
									<li>问题描述：<br></br><textarea name="content" id="content" style="width:500px;height:120px;"></textarea><br/></li>
									<li><input type="submit" name="submit" class="btn" value=" 提交 " /><br/></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
				<!--mright end-->
				<div class="cls"></div>
			</div>
		</div>
	</div>


		<!--foot s-->

		<div class="cls"></div>
		<jsp:include page="../../WEB-INF/page/footer1.jsp"></jsp:include>
</body>
<!-- 菜单选中结束 -->

<!-- Mirrored from www.kuaiyinpay.com/index_new/help/merchantsAccess.jsp by HTTrack Website Copier/3.x [XR&CO'2013], Sun, 30 Mar 2014 10:29:37 GMT -->
</html>
<%
	}
%>