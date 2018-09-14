<%@ page import="com.vo.Employees"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	</head>
	<body>
		<form id="pagerForm" method="post" action="<%=adminbasePath%>/system/startControl">
			<input type="hidden" name="currentTime" value="${currentTime}" />
			<input type="hidden" name="timeType" value="${timeType }" />
			<input type="hidden" name="successRate" value="${successRate}" />
			<input type="hidden" name="minAmount" value="${minAmount}" />
			<input type="hidden" name="maxAmount" value="${maxAmount}" />
			<input type="hidden" name="flushTime" value="${flushTime}" />
		</form>
		<div class="pageHeader">
			<form rel="pagerForm" action="<%=adminbasePath%>/system/startControl" onsubmit="return checkVal(this);" method="post">
				<div class="searchBar" id="search-content">
					<ul class="searchContent">
						<li style="width:400px"><label style="width:100px">当前多少时间内:</label>
							<input value="${currentTime}" type="text" name="currentTime"/>
							<select name="timeType" id="timeType">
								<option value="0">请选择</option>
								<option value="1">小时</option>
								<option value="2">分钟</option>
							</select>
						</li>
						<li style="width:260px"><label style="width:40px">成功率:</label>
							<input value="${successRate}" placeholder="请输入0到100之间的数字" type="text" name="successRate"/>%
						</li>
						<li style="width:500px"><label style="width:60px">订单金额:</label>
							<input value="${minAmount}" type="text" name="minAmount" placeholder="充值金额下限"/>--
							<input value="${maxAmount}" type="text" name="maxAmount" placeholder="充值金额上限"/>
						</li>
					</ul>
					<ul class="searchContent" style="margin-top:10px;">
						<li style="width:300px"><label style="width:100px">刷新时间(单位:秒)</label>
							<input value="${flushTime}" placeholder="建议填写5秒以上的时间" type="text" name="flushTime"/>
						</li>
					</ul>
					<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent"><button type="submit" id="start-listening">开启监听</button></div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<table class="table" width="1200" layoutH="138">
				<thead>
					<tr>
						<th>通道id</th>
						<th>通道名称</th>
						<th>订单数</th>
						<th>成功订单数</th>
						<th>订单成功率</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${orderList }" var="order">
						<tr data-color="${order.warning_color }">
							<td>${order.gateway_id }</td>
							<td>${order.gateway_name }</td>
							<td>${order.countOrder }</td>
							<td>${order.successOrder }</td>
							<td>${order.order_rate }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<audio id="audio" src="<%=basePath %>/mp3/jb.wav"></audio>
		<script type="text/javascript">
			$("#timeType option[value='${timeType}']").attr("selected", true);
			function checkVal($this){
				var timeType = $("#timeType").val();
				var currentTime = getValByName("currentTime");
				var minAmount = getValByName("minAmount");
				var maxAmount = getValByName("maxAmount");
				var flushTime = getValByName("flushTime");
				var successRate = getValByName("successRate");
				if(timeType == "0"){
					alertMsg.correct('请选择时间单位，小时或分钟');
					return false;
				}
				if(isEmpty(currentTime)){
					alertMsg.correct('请输入监控当前多少时间内的单');
					return false;
				}else if(isNaN(currentTime)){
					alertMsg.correct('请输入正确的时间');
					return false;
				}
				if(isEmpty(successRate)){
					alertMsg.correct('请输入订单成功率');
					return false;
				}else{ 
					if(!isEmpty(successRate)){
						if(isNaN(successRate)){
							alertMsg.correct('对不起！成功率只能为数字');
							return false;
						}else if(successRate*1 > 100 || successRate*1 < 0){
							alertMsg.correct('成功率只能输入0到100之间的数字');
							return false;
						}
					}
				}
				if(isEmpty(flushTime)){
					alertMsg.correct('请输入刷新时间');
					return false;
				}else if(isNaN(flushTime)){
					alertMsg.correct('刷新时间只能为整数');
					return false;
				}
				if(!isEmpty(minAmount) || !isEmpty(maxAmount)){
					if(!isNaN(minAmount) && !isNaN(maxAmount)){
						if(minAmount*1 > maxAmount*1){
							alertMsg.correct('请输入正确的金额区间');
							return false;
						}
					}else{
						alertMsg.correct('请输入正确的金额');
						return false;
					}
				}
				return navTabSearch($this);
			};
			$(function(){
				$("#tbody").find("tr").each(function(){
					var $this = $(this);
					var color = $this.data("color");
					$this.css("background",""+color+"");
				});
				var warningAudio = "${startwarning}";
				var audio = document.getElementById("audio");
				if(!isEmpty(warningAudio)){
					audio.play();
				}
			});
			
			function isEmpty(val) {
				val = $.trim(val);
				if (val == null)
					return true;
				if (val == undefined || val == 'undefined')
					return true;
				if (val == "")
					return true;
				if (val.length == 0)
					return true;
				if (!/[^(^\s*)|(\s*$)]/.test(val))
					return true;
				return false;
			}
			function getValByName(inputName){
				return $("#search-content").find("input[name='"+inputName+"']").val();
			};
			
			//自动刷新功能
			function loadData(){
				$("#start-listening").click();
			};
			var loadDataTime = getValByName("flushTime");
			if(!isEmpty(loadDataTime)){
				var timer = setTimeout(function(){
					loadData();
				},loadDataTime+"000");
			}
		</script>
	</body>
</html>