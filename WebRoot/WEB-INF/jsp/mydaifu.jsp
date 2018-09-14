<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.jsp.user.service.UserService"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@page import="com.vo.Balance"%>
<%@page import="com.jfinal.core.Controller"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html>
<%
Person map = (Person) request.getSession().getAttribute("session");
int user_id =map.getInt("id");
Balance settlement = UserService.userService.BalanceSettlement(user_id);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
<script type="text/javascript">
function check(){	
    var file=document.getElementById("Myexcel").value;
    var payment=document.getElementById("payment").value;
    if(file=="" || file==null){
        alert("请选择需要上传的文件！");
        return false;
    }else{
        var okk=true;
        $.ajax({
        	url:"<%=basePath%>/settlement/checkPayment?payment="+payment,
            type:"post",            
            dataType:"json",
            success:function(data){            	
            	if(data.status=="n"){      			
        			window.location.href="<%=basePath%>/settlement/daifu";
        		}else{        			
        			return true;
        		}
            }
        });
        return okk;
    }
}
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<jsp:include page="../page/top1.jsp"></jsp:include>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed');
			} catch (e) {
			}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			<jsp:include page="../page/left1.jsp"></jsp:include>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed');
						} catch (e) {
						}
					</script>
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">代付管理</li>
					</ul>
				</div>
				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i> 代付申请
									</a></li>
									<li><a data-toggle="tab" href="#five"> <i
											class="green icon-home bigger-110"></i> 模板下载
									</a></li>
								</ul>								
								<div class="tab-content">
									<div style="color:blue">可结算金额：<%=settlement.get("settlement") %> 元</div>
									<h5 class="text-danger">温馨提示：代付申请请减去单笔手续费&nbsp;<span style="color:red;font-weight:800;">${refund_rate.refund }</span>&nbsp;元</h5>
									<div id="info" class="tab-pane in active">
										<div class="row">
											<div class="col-sm-6">
												<fieldset>
													<form id="MyUpload" name="MyUpload" action="<%=basePath%>/settlement/importExcel" method="post"
														enctype="multipart/form-data" onsubmit="return check()">
														<input type="hidden" name="withdrawalToken" value="${withdrawalToken}" />
														<div class="clearfix form-actions">
															<input type="file" name="excelexcel" id="Myexcel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/><br>															
																支付密码：<input type="text" name="payment" id="payment" datatype="n"
																	nullmsg="请输入支付密码！" errormsg="输入支付密码有误，请重新输入！" /><span id="mymsg"></span>																															
															<br><br><input type="submit" class="btn btn-primary"
																value="确认上传">
														</div>
													</form>
												</fieldset>
												<div style="color:blue;">结算规则以商户约定为准，为确保提现成功，请保证可用余额资金充足</div>
											</div>
										</div>
									</div>
									<div id="five" class="tab-pane ${msg}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>如需代付操作，请按照模板样式填写</th>
												</tr>
											</thead>
											<tbody id="refund">
												<tr>
													<td>下载地址: <a href="<%=basePath%>/settlement/download">下载模板</a></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- /span -->
						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /span -->
					</div>
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="../page/switch.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
	<!--/.fluid-container-->
</body>
</html>