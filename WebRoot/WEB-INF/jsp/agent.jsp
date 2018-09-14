<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Logrecord"%>
<%@page import="com.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/page/upage1.jsp"></jsp:include>

<link rel="stylesheet" href="<%=basePath %>/css/fang.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/pager.css"/>


<script type="text/javascript" src="<%=basePath %>/js/pager.js"></script>
<script src="<%=basePath%>/js/ajaxs/UserOrder.js"></script>

<script src="<%=basePath %>/dwz/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath %>/js/messages_bs_zh.js" type="text/javascript"></script>

<style>


.add-customer{height:95%;}
.add-customer .content-left{float:left;width:50%;height:100%;}
.add-customer .content-right{float:right;width:50%;height:100%;}
.add-customer dl{margin-bottom:14px;}
.add-customer h5{text-align:center;color:#a03c4d;margin-bottom:7px;}

.in_block{ display:inline-block;}
.mar_l_5{ margin-left:5px;}
.font_18{font-size:18px;}

span.error {display: inline-block;line-height:25px;background:#F00;color:#FFF; margin-top:4px;}

</style>
</head>
<body style="font-family: Microsoft YaHei">
	<jsp:include page="../page/top1.jsp"></jsp:include>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <sol-sm-
					class="menu-text"></sol-sm->
			</a>

			<jsp:include page="../page/left1.jsp"></jsp:include>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">代理管理</li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active">
										<a data-toggle="tab" href="#info">
											<i class="green icon-home bigger-110"></i> 代理列表
										 </a>
									</li>
									<li class="">
										<a data-toggle="tab" href="#income">
											<i class="green icon-signin bigger-110"></i> 收益统计
										 </a>
									</li>
									<c:if test="${session.isAgent==1 }">
										<li ><a data-toggle="tab" href="#addPerson">
												<i class="green icon-user bigger-110"></i> 开户
										</a></li>
									</c:if>
									
								</ul>

								<div class="tab-content">
									<div id="info" class="tab-pane in active">
										<div class="row">

											<div class="col-sm-12">
												
											</div>
										</div>
										<table class="table" width="1200" layoutH="500">
											<thead>
												<tr>
													<th>商户id</th>
													<th>邮箱</th>
													<!-- <th>商户类型</th> -->
													<th>企业名称</th>
													<th>可结算余额</th>
													<th>微信通道</th>
													<th>微信费率(%)</th>
													<th>支付宝通道</th>
													<th>支付宝费率(%)</th>
													<th>提现手续费(笔)</th>
													<!-- <th>登陆状态</th> -->
													<th>锁定状态</th>
													<th>网银状态</th>
													<th>上级代理Id</th>
													<th>代理层级</th>
													<th>代理用户</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${childList}" var="per">
													<tr onClick="">
														<td><c:out value="${per.id}"></c:out></td>
														<td><c:out value="${per.email}"></c:out></td>
														<%-- <td>
														<c:choose>
															<c:when test="${per.huge==1}">
																企业
															</c:when>
															<c:otherwise>
																个人
															</c:otherwise>
														</c:choose></td> --%>
														<td>
															<c:choose>
																<c:when test="${per.name==''}">
																	未选择
																</c:when>
																<c:otherwise>
																	<c:out value="${per.name}"></c:out>
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															<c:out value="${per.settlement}"></c:out>
														</td>
														<td>
															<c:out value="${per.wxwap}"></c:out>
														</td>
														<td>
															<c:out value="${(1-per.rate.cibsm)*100}"></c:out>
														</td>
														<td>
															<c:out value="${per.alipaywap}"></c:out>
														</td>
														<td>
															<c:out value="${(1-per.rate.mustali)*100}"></c:out>
														</td>
														<td>
															<c:out value="${per.rate.refund}"></c:out>
														</td>
													
														<%-- <td>
														<c:choose>
															<c:when test="${per.iflogin==1}">
																登陆
															</c:when>
															<c:otherwise>
																未登录
															</c:otherwise>
														</c:choose>
														</td> --%>
														<td>
														<c:choose>
															<c:when test="${per.iflock==1}">
																正常
															</c:when>
															<c:otherwise>
																锁定
															</c:otherwise>
														</c:choose></td>
														<td>
														<c:choose>
															<c:when test="${per.ifnet==1}">
																开通
															</c:when>
															<c:otherwise>
																未开通
															</c:otherwise>
														</c:choose></td>
														
														<td><c:out value="${per.superior}"></c:out></td>
														
														<td><c:out value="${per.level}"></c:out></td>
														<td>
															<c:choose>
																<c:when test="${per.isAgent==1}">
																	是
																</c:when>
																<c:otherwise>
																	否
																</c:otherwise>
															</c:choose>
														
														</td>
														<td class="">
															<c:if test="${per.level==1}">
																<button type="button" data-toggle="modal" data-target="#modal_rate" style="width:50px;" onclick="get_val('${per.id}');">更改</button>
															</c:if>
														
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										
										
									</div>
									
									
									
									<div id="income" class="tab-pane in">
										<div class="row">
											<form action="<%=basePath %>/user/getQueryCount" method="post" class="ajax_form">
												<div class="col-sm-3">
													<span>日期：<input name="netstarttime"
														data-date-format="yyyy-mm-dd" style="width: 100px"
														type="text" class="netstarttime" Placeholder="开始时间"
														value=""></span>—<span><input name="netendtime"
														id="netendtime" style="width: 100px"
														data-date-format="yyyy-mm-dd" type="text"
														class="netendtime" Placeholder="结束时间" value="">
													</span>
												</div>
												<div class="col-sm-6">
													<input type="button" name="today" value=" 今天 "
														onClick="MyDate(1)"> <input type="button"
														name="yesteday" value=" 昨天 " onClick="MyDate(2)"> <input
														type="button" name="week" value=" 本周 " onClick="MyDate(4)">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class="btn btn-small btn-primary" type="button" style="line-height: 10px;margin-bottom: 10px;" value="查询" onclick="ajax_form(this);">
												</div>
											</form>
<script>

//ajax 提交表单
function ajax_form(ele){

	$.post($(ele).parents("form.ajax_form")[0].action,$(ele).parents("form.ajax_form").serialize(),function(result){
		
		if (result.amttote != null)result.amttote = result.amttote.toFixed(2);
		if (result.amounttote != null)result.amounttote = result.amounttote.toFixed(2);
		if (result.income != null)result.income = result.income.toFixed(2);
		
		var str = "订单总数：<span id='pre-totalCount'>";
		str +=  result.tote + "</span>条 ";
		str += "充值金额："+ result.amttote + "（元）";
		str += "结算金额：" + result.amounttote + "（元）";	
		
		
		str += "<span class='in_block wid_80'></span>收益：" + result.income + "（元）";	
		
		$("#person_count").html(str);
		
		var str = "";
		var data=result.list;
		for (var i = 0; i < result.list.length; i++) {
			str += "<tr>";
			str += "<td><span Style='text-align:left'>" + data[i].id
			+ "</span></td>";
			str += "<td><span Style='text-align:left'>" + data[i].email
			+ "</span></td>";

			str += "<td Style='text-align:center'><span>"
				+ data[i].webName + "</span></td>";
			str += "<td Style='text-align:center'><span>"
				+ data[i].superior + "</span></td>";
			str += "<td Style='text-align:center'><span>" + data[i].level + "</span></td>";
			str += "<td Style='text-align:center'><span>";
			
			if(data[i].isAgent=="1"){
				str+="是";
			}else{
				str+="否";
			}
			str+= "</span></td>";
			
			
							
			var amttote = "0";
			if (data[i].amttote != null) {
				amttote = data[i].amttote;
			}				
			var amounttote = "0";
			if (data[i].amounttote != null) {
				amounttote = data[i].amounttote;
			}
			var income = "0";
			if (data[i].income != null) {
				income = data[i].income;
			}
			
			str += "<td Style='text-align:center'><span>"
				+ data[i].tote + "</span></td>";
			
			str += "<td Style='text-align:center'><span>" +amttote + "</span></td>";
			str += "<td Style='text-align:center'><span>" + amounttote + "</span></td>";
			str += "<td Style='text-align:center'><span>" + income + "</span></td>";
			str += "<td Style='text-align:center'><button  data-toggle= 'modal' data-target='#modal_info' style='width:50px;' "; 
			str +="onclick='get_val("+data[i].id+");'>详情</button></td>";
			
			str += "</tr>";
		}
		$("#person_tbody").html(str);
		
		
		
	})
}

</script>
											<div class="col-sm-12">
												<div class="alert alert-warning" id="person_count">
												订单总数：0 条 
												充值金额：0（元）
												结算金额：0（元）
											</div>
											</div>
										</div>
										
										
										<table class="table" width="1200" layoutH="500">
											<thead>
												<tr>
													<th>商户id</th>
													<th>邮箱</th>
													<th>企业名称</th>
													<th>上级代理Id</th>
													<th>代理层级</th>
													<th>代理用户</th>
													<th>订单总数</th>
													<th>充值金额</th>
													<th>结算金额</th>
													<th>收益</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody  id="person_tbody">
												
											</tbody>
										</table>
										
										
									</div>
									
									
									
									<c:if test="${session.isAgent==1 }">
									<div id="addPerson" class="tab-pane in ">
										<div class="row">
										
											<div class="col-sm-12">
												
											</div>
										</div>
								
										<form id="myForm" action="<%=basePath%>/user/addPerson"  enctype="multipart/form-data" class="pageForm required-validate" method="post">
										<input type="hidden" name="addpersonToken" value="${addpersonToken}" />
										<div class="pageFormContent" layoutH="58">
											<fieldset class="add-customer">
												
												<table class=" l_hei_40 mar_l_40">
													<tr>
												    	<td class="t_align_r">邮箱：</td>
												      
												   	    <td>
												        	<input type="text" name="email" class="required email wid_235 mar_l_5 l_hei_25" />
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">密码：</td>
												      
												   	    <td>
												        	<input  id="password" type="password" name="password" id="password" minlength="6" class="required alphanumeric wid_235 mar_l_5 l_hei_25" />
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">确认密码：</td>
												      
												   	    <td>
												        	<input type="password" name="repassword" class="required  mar_l_5 l_hei_25 wid_235" equalto="#password" />
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">支付密码：</td>
												      
												   	    <td>
												        	<input type="password" name="payment" class="required  mar_l_5 l_hei_25 wid_235" minlength="6" maxlength="20" />
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">网站名称：</td>
												      
												   	    <td>
												        	<input type="text" name="webName" class="required mar_l_5 l_hei_25 wid_235" />
												            <span class="red mar_l_5 in_block font_18">*</span>
												            <span class="col_90"> 个人填写姓名公司填写公司名称</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">网站网址：</td>
												      
												   	    <td>
												        	http://</span><input type="text" name="website" class="required mar_l_5 l_hei_25" style="width:195px;"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">所属行业：</td>
												      
												   	    <td>
												        	<select name="herolist" class=" mar_l_5 l_hei_25 hei_30 wid_120">
												                <option value="0" selected="selected">党政机关</option>
												                <option value="1">商业</option>
												                <option value="2">制造业</option>
												                <option value="3">服务业</option>
												                <option value="4">农业牧渔业</option>
												                <option value="5">其它行业</option>
												            </select>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r">用户类型：</td>
												      
												   	    <td>
												        	<select name="huge" class=" mar_l_5 l_hei_25 hei_30 wid_120"  onChange="selectNext(this);">
												                <option value="0" selected="selected">个人</option>
												                <option value="1">企业</option>
												            </select>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												     <tr class="idcard">
												    	<td class="t_align_r">身 份 证：</td>
												      
												   	    <td>
												        	 <input type="text" name="idcard" class=" mar_l_5 l_hei_25 wid_235"  />
												             <span class="red mar_l_5 in_block font_18">*</span>          
												        </td>
												    </tr>
												    
												     <tr  class="hidd">
												    	<td class="t_align_r">营业执照：</td>
												      
												   	    <td>
												        	<input type="text" name="licence" class=" mar_l_5 l_hei_25 wid_235" />
												            <span class="red mar_l_5 in_block font_18">*</span>     
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r v_align_t">身份证照片：</td>
												    	<td>
												        	      <dl class="in_block">
															           <dt>
															               <div class="thumbnail cursor wid_150">     	
															                    <img src="<%=basePath%>/img/addImg.png" onclick="idcardFile1.click();" />
															                    <input type="file" id="idcardFile1" name="idcardFile1"  onchange="changeImg(this);" class="hide" />
															                </div>
															            </dt>
															            <dd class="t_align_c">
															                正面
															            </dd>
															        
															        </dl>
															        <dl  class="in_block mar_l_40">
															            <dt>
															               <div class="thumbnail cursor wid_150">     	
															                    <img src="<%=basePath%>/img/addImg.png" onclick="idcardFile2.click();" />
															                    <input type="file" id="idcardFile2" name="idcardFile2"  onchange="changeImg(this);" class="hide" />
															                </div>
															            </dt>
															            <dd class="t_align_c">
															                背面
															            </dd>
															    	</dl>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r v_align_t">营业执照照片：</td>
												    	<td>
											        	    <div class="thumbnail cursor wid_150" >     	
													            <img src="<%=basePath%>/img/addImg.png" onclick="licenceFile.click();" />
											           		    <input type="file" name="licenceFile" id="licenceFile"  onchange="changeImg(this);" class="hide" />
													        </div>
												        </td>
												    </tr>
												    <tr>
												    	<td class="t_align_r v_align_t">企业开户许可证：</td>
												    	<td>
											        	    <div class="thumbnail cursor wid_150" >     	
													            <img src="<%=basePath%>/img/addImg.png" onclick="openAccount.click();" />
											           		    <input type="file" name="openAccount" id="openAccount"  onchange="changeImg(this);" class="hide" />
													        </div>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r v_align_t">网络文化经营许可证：</td>
												    	<td>
											        	    <div class="thumbnail cursor wid_150" >     	
													            <img src="<%=basePath%>/img/addImg.png" onclick="webCulture.click();" />
											           		    <input type="file" name="webCulture" id="webCulture"  onchange="changeImg(this);" class="hide" />
													        </div>
												        </td>
												    </tr>
												    
												    <tr>
												    	<td class="t_align_r v_align_t">域名证书：</td>
												    	<td>
											        	    <div class="thumbnail cursor wid_150" >     	
													            <img src="<%=basePath%>/img/addImg.png" onclick="domainName.click();" />
											           		    <input type="file" name="domainName" id="domainName"  onchange="changeImg(this);" class="hide" />
													        </div>
												        </td>
												    </tr>
												    
												    
												    
												    <tr>
												    	<td class="t_align_r">手机号码：</td>
												      
												   	    <td>
												        	<input type="text" name="phone" class="required phone  mar_l_5 l_hei_25 wid_235"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												     <tr>
												    	<td class="t_align_r">公司/个人：</td>
												      
												   	    <td>
												        	<input type="text" name="name"  class="required mar_l_5 l_hei_25 wid_235"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												            <span class="col_90">请输入个人或公司名称</span>
												        </td>
												    </tr>
												    
												    <tr>
												        <td class="t_align_r">联 系 人：</td>
												        <td>
												            <input type="text" name="contacts"  class="required mar_l_5 l_hei_25 wid_235"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    
												     <tr>
												    	<td class="t_align_r">联系地址：</td>
												      
												   	    <td>
												        	<input type="text" name="address"  class="required mar_l_5 l_hei_25 wid_235"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												     <tr>
												        <td class="t_align_r">QQ号码：</td>
												        <td>
												            <input type="text" name="qq"  class="required mar_l_5 l_hei_25 wid_235"/>
												            <span class="red mar_l_5 in_block font_18">*</span>
												        </td>
												    </tr>
												    <c:if test="${session.permission==1 }">
													     <tr>
													    	<td class="t_align_r">是否是代理人：</td>
													      
													   	    <td>
													        	<input type="checkbox" name="isAgent" value="1" onChange="showPerm(this);"/>
													          
													        </td>
													    </tr>
													    
													     <tr class="hidd">
													    	<td class="t_align_r">下级用户代理权限：</td>
													      
													   	    <td>
													        	<input type="checkbox" name="permission" value="1"/>
													           
													        </td>
													    </tr>
												    </c:if>
													<tr>
														<td colspan="2">
															<div class="clearfix form-actions">
																<button type="submit" class="btn btn-primary">确认提交</button>
																<a  class="btn"  onClick="clearForm()">重新填写</a>
															</div>
														</td>
													</tr>
												</table>
											
											</fieldset>
										</div>
										
										
									</form>
										
									</div>
									</c:if>
									
								</div>
							</div>
						</div>
						
						<!-- /sol-sm- -->

						<div class="col-sm-12" style="margin-top:30px;">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /sol-sm- -->
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
	
	
	<!-- 模态框修改费率 -->
	<div class="modal fade" id="modal_rate" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">×</span></button>
	                <h4 class="modal-title">更改用户费率</h4>
	                <h5 class="text-danger">*如果只修改某一项，其它的可以不填写，*</h5>
	            </div>
	            <div class="modal-body">
	                <form action="<%=basePath %>/user/upRate" method="post" class="ajax_form">
	                    <input type="hidden" name="id" value="">
	                
	                    <div class="form-group">
	                        <label class="control-label">qq钱包费率：</label>
	                        <input type="text" class="form-control" name="qqwx">
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label">微信费率：</label>
	                        <input type="text" class="form-control" name="cibsm">
	                    </div>
	                    
	                     <div class="form-group">
	                        <label class="control-label">支付宝费率：</label>
	                        <input type="text" class="form-control" name="mustali">
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label">网银支付：</label>
	                        <input type="text" class="form-control" name="banking">
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label">提现手续费：</label>
	                        <input type="text" class="form-control" name="refund">
	                    </div>
	                    
			            <div class="modal-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			                <button type="button" class="btn btn-primary" onclick="subForm(this);" >确认提交</button>
			            </div>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	
	
	<!-- 模态框展示用户订单详情 -->
	<div class="modal fade" id="modal_info" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
	    <div class="modal-dialog" role="document" style="width:1200px;">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">×</span></button>
	                <h4 class="modal-title">查看订单</h4>
	      
	            </div>
	            <div class="modal-body">
	               <div id="info" class="tab-pane in active">
						<div class="row">
							<div class="col-sm-3">
								商户订单号：
								<input style="width: 150px" name="netid" id="netid" type="text" Placeholder="订单编号" value="" />
							</div>
							
							<div class="col-sm-3">
								<span>日期：<input name="netstarttime" id="netstarttime"
									data-date-format="yyyy-mm-dd" style="width: 100px"
									type="text" class="netstarttime" Placeholder="开始时间"
									value=""></span>—<span><input name="netendtime"
									id="netendtime" style="width: 100px"
									data-date-format="yyyy-mm-dd" type="text"
									class="netendtime" Placeholder="结束时间" value="">
								</span>
							</div>
							<div class="col-sm-3">
								<input type="button" name="today" value=" 今天 "
									onClick="MyDate(1)"> <input type="button"
									name="yesteday" value=" 昨天 " onClick="MyDate(2)"> <input
									type="button" name="week" value=" 本周 " onClick="MyDate(4)">
							</div>
							<div class="col-sm-3">
								订单状态：<select name="netstate" id="netstate"
									style="width: 80px">
									<option value="0">请选择</option>
									<option value="1">成功</option>
									<option value="2">处理中</option>
									<option value="3">失败</option>
									<option value="4">失效</option>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="btn btn-small btn-primary" type="button" style="line-height: 10px;margin-bottom: 10px;"
									onclick="net(1)" value="查询">
							</div>

							<div class="col-sm-12">
								<div class="alert alert-warning" id="my_count">
									订单总数：${ordertotal.tote}条 
									充值金额：${ordertotal.amttote }（元）
									结算金额：${ordertotal.amounttote}（元）
								</div>
							</div>
						</div>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th style="text-align: center">商户订单号</th>
									<th style="text-align: center">银行订单号</th>
									<th style="text-align: center">订单金额</th>
									<th style="text-align: center">结算金额</th>
									<th style="text-align: center">手续费</th>
									<th style="text-align: center">收益</th>
									<th style="text-align: center">订单日期</th>
									<th style="text-align: center">状态</th>
									
								</tr>
							</thead>
							<tbody id="net" style="background: #FFFFFF">
							</tbody>
						</table>
						
						<div  class="pagination" id="dl_pagination"></div>
					</div>
	            
	            </div>
	        </div>
	    </div>
	</div>
	
	
	
	
	<!-- /.main-container -->

	<!--/.fluid-container-->
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			if('${message}'!=''){
				alert('${message}')	;
			}
	
			$('.netstarttime').datepicker({
				autoclose : true
			
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			$('.netendtime').datepicker({
				autoclose : true
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			$('.netstarttime2').datepicker({
				autoclose : true
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			$('.netendtime2').datepicker({
				autoclose : true
			}).next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			
			//添加表单校验
			$("#myForm").validate();
		});
		
		//图片回显
		function changeImg(ele){
			var img=$(ele).prev("img")[0];
			if(ele.files[0]){
				
				if((ele.files[0].type).indexOf("image/")==-1){ 
					
					 alert("请上传图片!");
					 ele.value=null;
					 return;  
				} 
				
				 var reader = new FileReader();
				 reader.onload = function(evt){img.src = evt.target.result; }
				 reader.readAsDataURL(ele.files[0]);	
			}
			$(ele).parents("div.thumbnail").removeClass("wid_150").addClass("wid_280");
		}

		
		
		//ajax 提交表单
		function subForm(ele){
		
			$.post($(ele).parents("form.ajax_form")[0].action,$(ele).parents("form.ajax_form").serialize(),function(result){
				alert(result.message);
				window.location.reload();
				
			})
		}
		
		function clearForm(){
			$("#myForm")[0].reset();
			$("tr.idcard").show();
			$("tr.hidd").hide();

		}
		
		function selectNext(ele){

			if($(ele).val()=="0"){	
				$(ele).parent().parent().next().show();
				$(ele).parent().parent().next().next().hide();		
			}else if($(ele).val()=="1"){	
				$(ele).parent().parent().next().hide();
				$(ele).parent().parent().next().next().show();		
			}
		}
		
		function showPerm(ele){
			
			$(ele).parent().parent().next().toggle();
			$(ele).parent().parent().next().find("input[type=checkbox]")[0].checked=false;
		
		}
		
		
		function get_val(val){
			
			$("input[name=id]").val(val);
			$("#net").html("");
			$("#my_count").html("");
		}
		
		
		function net(page){
			
			$("#net").html("");
			$("#my_count").html("");
			
			var netstarttime = $("#netstarttime").val();
			var netendtime = $("#netendtime").val();
			var netstate = $("#netstate").val();
			var netid = $("#netid").val();
			var id = $("input[name=id]").val();
			$.ajax({
				url : basePath + "/exchange/getQueryCount20171030",
				data : 'page=' + page + '&&netstarttime=' + netstarttime
				+ '&&netendtime=' + netendtime + '&&netstate=' + netstate
				+ '&&netid=' + netid+ '&&id=' + id,
				type : 'post',
				dataType : 'json',
				error : function() {
					alert('数据获取失败');
				},
				success : function(result) {
					var datas=result.list;
					var data = datas[0];
					netpage = datas[1];
					var st = "<ul class='ajax-loaders'>";
					st += "<li><img src='" + basePath
					+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
					$("#net").html(st);
					var str = "";					
					for (var i = 0; i < data.length; i++) {
						str += "<tr>";
						str += "<td><span Style='text-align:left'>" + data[i].p2_Order
						+ "</span></td>";
						str += "<td><span Style='text-align:left'>" + data[i].orderid
						+ "</span></td>";

						var amt = data[i].p3_Amt;	
						
						var shouyi=0;
						if(data[i].r1_Code==1){
							if(data[i].pd_FrpId=="alipaywap"){
								shouyi=amt*result.rate.mustali;
							}else if(data[i].pd_FrpId=="pay.weixin.wappay"){
								shouyi=amt*result.rate.cibsm;
							}else if(data[i].pd_FrpId=="pay.tenpay.wappay"){
								shouyi=amt*result.rate.qqwx;
							}else if(data[i].pd_FrpId=="wxwap"){
								shouyi=amt*result.rate.cibsm;
							}
							shouyi=shouyi.toFixed(2);
						}
						
						var amount = "0";
						if (data[i].amount != null) {
							amount = data[i].amount;
						}				
						var deducted = "0";
						if (data[i].deducted != null) {
							deducted = data[i].deducted;
						}
						
						
						str += "<td Style='text-align:center'><span>"
							+ amt + "</span></td>";
						str += "<td Style='text-align:center'><span>"
							+ amount + "</span></td>";
						str += "<td Style='text-align:center'><span>" + deducted + "</span></td>";
						str += "<td Style='text-align:center'><span>" + shouyi + "</span></td>";
						str += "<td Style='text-align:center'><span>"
							+ data[i].CreateTime + "</span></td>";
						if (data[i].r1_Code == 1) {
							str += "<td class='center'><span class='label label-success'>成功</span></td>";
						} else if (data[i].r1_Code == 2) {
							str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
						} else if (data[i].r1_Code == 3) {
							str += "<td class='center'><span class='label label-warning'>失败</span></td>";
						} else if (data[i].r1_Code == 4) {
							str += "<td class='center'><span class='label label-warning'>失效</span></td>";
						}
						
						str += "</tr>";
					}
					$("#net").html(str);
					
					
					var record=result.record;
					var amttote = record.amttote;
					var amounttote=record.amounttote;
					var income=record.income;
					if(amttote==null || amttote==""){
						amttote=0;
					}
					if(amounttote==null || amounttote==""){
						amounttote=0;
					}
					if(income==null || income==""){
						income=0;
					}
					
					var str = "订单总数：<span id='pre-totalCount'>";
					str +=  record.tote + "</span>条 ";
					str += "充值金额："+ amttote + "（元）";
					str += "结算金额：" + amounttote + "（元）";	
					str += "<span class='in_block wid_80'></span>该用户对您造成的收益：" + income + "（元）";	
					
					$("#my_count").html(str);
					var page_num = Math.ceil(record.tote/10);
					
					loadDataByPage.initPage(page_num,page);
					
				}
			});
			
			
		};
		
		function mycount() {
			var start_time = $("#netstarttime2").val();
			var end_time = $("#netendtime2").val();
			var netstate = $("#netstate").val();
			var netid = $("#netid").val();
			var id = $("input[name=id]").val();
			
			$.ajax({
				url : basePath + "/exchange/getMyCount",
				data : 'netstarttime=' + start_time+ '&&netendtime=' + end_time,
				type : 'post',
				dataType : 'json',
				error : function() {
					alert('数据获取失败');
				},
				success : function(data) {	
					var amttote = data.amttote;
					var amounttote=data.amounttote;
					if(amttote==null || amttote==""){
						amttote=0;
					}
					if(amounttote==null || amounttote==""){
						amounttote=0;
					}
					var str = "<tr>";
					str += "<td Style='text-align:center'><span>"
						+ data.tote + "</span></td>";
					str += "<td Style='text-align:center'><span>"
						+ amttote + "</span></td>";
					str += "<td Style='text-align:center'><span>" + amounttote + "</span></td>";				
					str += "</tr>";
					$("#usernet").html(str);
				}
			});
		}
		
		
		
		
		
		var loadDataByPage = {
				initPage:function(itemCount,pageNo){
					var $this = this;
					Page({
						num:itemCount,		//页码数
						startnum:pageNo,				//指定页码
						elem:$('#dl_pagination'),		//指定的元素
						callback:function(n){	//回调函数
							net(n);
						}
					});
				},
				
			};
		
		
		
		
	
		function getDay(day) {
			var today = new Date();
	
			var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24
					* day;
			today.setTime(targetday_milliseconds); //注意，这行是关键代码    
			var tYear = today.getFullYear();
			var tMonth = today.getMonth();
			var tDate = today.getDate();
			tMonth = doHandleMonth(tMonth + 1);
			tDate = doHandleMonth(tDate);
			return tYear + "-" + tMonth + "-" + tDate;
		}
		function doHandleMonth(month) {
			var m = month;
			if (month.toString().length == 1) {
				m = "0" + month;
			}
			return m;
		}
	
		function MyDate(v) {
			if (v == 1) {
				$(".netstarttime").val(getDay(0));
				$(".netendtime").val(getDay(1));
			} else if (v == 2) {
				$(".netstarttime").val(getDay(-1));
				$(".netendtime").val(getDay(0));
			} else {
				var date = new Date(); //当前日期
				var now = date.getDay();
				if(now==0){
					now = 7;	
				}
				var first = 1 - now;
				$(".netstarttime").val(getDay(first));
				$(".netendtime").val(getDay(1));
			}
		}
	
		function MyDate2(v) {
			if (v == 1) {
				document.getElementById("netstarttime2").value = getDay(0);
				document.getElementById("netendtime2").value = getDay(1);
			} else if (v == 2) {
				document.getElementById("netstarttime2").value = getDay(-1);
				document.getElementById("netendtime2").value = getDay(0);
			} else {
				var date = new Date(); //当前日期
				var now = date.getDay();
				if(now==0){
					now = 7;	
				}
				var first = 1 - now;			
				document.getElementById("netstarttime2").value = getDay(first);
				document.getElementById("netendtime2").value = getDay(1);
			}
		}
	</script>
</body>
</html>