<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>My JSP 'cluster.jsp' starting page</title>
<meta charset="utf-8">
<meta name="keywords" content="keyword1,keyword2,keyword3">
<meta name="description" content="This is my page">
<script type="text/javascript" src="<%=basePath%>/js/stringUtil.js"></script>
</head>
<body>
	<style type="text/css">
		.ul-content{padding:5px;}
		.ul-content li{padding:10px;}
	</style>
	<fieldset id="cluster-wx-content">
		<legend>微信集群&nbsp;&nbsp;&nbsp;<span style="color:red;font-size:14px;">集群金额总区间需在0--3000之间,其中某个区间已达到3000后面可以不选择</span></legend>
		<ul class="ul-content">
			<li class="cluster-list-0">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
			<li class="cluster-list-1">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
			<li class="cluster-list-2">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
		</ul>
		<div class="subBar" style="float:right;padding:10px;">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><button id="confirm-submitBtn-wx" type="button">确认提交</button></div>
					</div>
				</li>
			</ul>
		</div>
	</fieldset>
	<fieldset id="cluster-ali-content">
		<legend>支付宝集群&nbsp;&nbsp;&nbsp;<span style="color:red;font-size:14px;">集群金额总区间需在0--3000之间,其中某个区间已达到3000后面可以不选择</span></legend>
		<ul class="ul-content">
			<li class="cluster-list-0">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
			<li class="cluster-list-1">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
			<li class="cluster-list-2">
				集群：<select class="cluster-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<option value="1">集群1</option>
					<option value="2">集群2</option>
					<option value="3">集群3</option>
					<option value="4">集群4</option>
				</select>
				微信通道：<select class="gateway-select" onchange="jack_submitData.validationData();">
					<option value="0">--请选择--</option>
					<c:forEach items="${wxgateway }" var="wx">
						<option value="${wx.gateway_id }">${wx.gateway_name }</option>
					</c:forEach>
				</select>
				金额区间：
				<input type="text" readonly="readonly" class="cluster-minmoney" placeholder="请输入金额下限(大于0)" title="请选择集群和通道后，再输入"/>--
				<input type="text" readonly="readonly" class="cluster-maxmoney" placeholder="请输入金额上限" title="请选择集群和通道后，再输入"/>
			</li>
		</ul>
		<div class="subBar" style="float:right;padding:10px;">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><button id="confirm-submitBtn-ali" type="button">确认提交</button></div>
					</div>
				</li>
			</ul>
		</div>
	</fieldset>
	<script type="text/javascript">
		$(function(){
			$("#confirm-submitBtn-wx").on("click",function(){
				var jsonData = jack_submitData.validationData(1);
				//var html = $("#cluster-wx-content").find(".cluster-list li").html();
				if(jsonData){
					jack_submitData.saveData(jsonData,"WX");
				}
			});
			$("#confirm-submitBtn-ali").on("click",function(){
				jack_submitData.parentNode = $("#cluster-ali-content");
				var jsonData = jack_submitData.validationData(1);
				//var html = $("#cluster-wx-content").find(".cluster-list li").html();
				if(jsonData){
					jack_submitData.saveData(jsonData,"ALI");
				}
			});
		});
		var jack_submitData = {
			parentNode:$("#cluster-wx-content"),
			validationData:function(model){
				var ulDom = this.parentNode.find(".ul-content");
				var len = ulDom.children("li").length;
				var jsonData = {};
				for(var i=0;i < len;i++){
					var liDom = ulDom.find(".cluster-list-"+i+"");
					var cluster = liDom.find(".cluster-select").val();
					var gatewayid = liDom.find(".gateway-select").val();
					var minmoney = liDom.find(".cluster-minmoney").val();
					var maxmoney = liDom.find(".cluster-maxmoney").val();
					var clusterArr = cluster;
					var gatewayidArr = gatewayid;
					var moneyArr = minmoney+","+maxmoney;
					jsonData['cluster'+i] = clusterArr;
					jsonData['gatewayid'+i] = gatewayidArr;
					jsonData['money'+i] = moneyArr;
					if(minmoney*1 >= 3000 || maxmoney*1 >= 3000){
						break;
					}
					//验证选择框
					if(cluster==0){
						if(model)alertMsg.correct("请选择集群");
						return false;
					}else if(gatewayid==0){
						if(model)alertMsg.correct("请选择通道");
						return false;
					}
					liDom.find("input[type='text']").removeAttr("readonly").prev().focus();
					//验证金额区间
					if(model){
						if(isNotEmpty(minmoney) && isNotEmpty(maxmoney)){
							if(isNaN(minmoney) || isNaN(maxmoney)){
								alertMsg.correct("请填入正确的数字");
								return false;
							}else if(minmoney*1 > maxmoney*1 || minmoney*1 < 0){
								alertMsg.correct("金额区间填写错误");
								return false;
							}
						}else{
							alertMsg.correct("请将金额区间填写完整");
							return false;
						}
					}
				}
				return jsonData;
			},
			saveData:function(jsonData,cluster_type){
				jsonData.cluster_type = cluster_type;
				console.log(JSON.stringify(jsonData));
				$.ajax({
					type:"post",
					url:"<%=adminbasePath%>/cluster/saveClusterData",
					data:jsonData,
					error:function(){
						alertMsg.correct("保存错误，请重试");
					},
					success:function(data){
						alertMsg.correct(data.message);
					}
				});
			}
		};
	</script>
</body>
</html>
