<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Employees"%>
<%@page import="com.vo.Navigation"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../../../page/page.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	List<Navigation> navids = (List<Navigation>) request.getSession().getAttribute("privatesession");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

function changeKey(a,b){
	var me = document.getElementById(a).value;
	tome = me*100;
	fv = parseFloat(100-tome).toFixed(2);
	var md = document.getElementById(b);
	md.value = fv;
}

function changeKey2(a,b){
	var me = document.getElementById(a).value;
	tome = me/100;
	fv = parseFloat(1-tome).toFixed(3);
	var md = document.getElementById(b);
	md.value = fv;
}

function upkey(){
	$.ajax({
		url: '<%=adminbasePath%>/person/getkey',
		type:'post',
		error:function(){
			alert('数据获取失败');
		},
		success:function(data){
			$("#key").val(data);
		}
	})
}
</script>
</head>

<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/person/check">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/person/check"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="20" />
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>商户编号：</label>
						<input type="text" name="id" value="${id }"/>
					</li>
					
					
					
				</ul>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		
		<div class="panelBar">
			<ul class="toolBar">
					
		    	<%
			       if(navids.contains(49)==true){
			    %>
				<li>
					<%-- <%=adminbasePath%>/person/checkPerson --%>
					<a  target="ajaxTodo" href="<%=adminbasePath%>/person/checkPerson?id={id}"
					class="delete"><span>审核通过</span></a>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		
		<table class="table" width="1200" layoutH="138">
			<thead>
				<tr>
					
					<th>商户id</th>
					<th>邮箱</th>
					<th>商户类型</th>
					<th>企业名称</th>
					<th>可结算余额</th>
					<th>微信通道</th>
					<th>微信费率(%)</th>
					<th>支付宝通道</th>
					<th>支付宝费率(%)</th>
					<th>提现手续费(笔)</th>
					<th>注册时间</th>
					<th>锁定状态</th>
					<th>网银状态</th>
					<th>代理用户</th>
					<%
			            if(navids.contains(50)==true || navids.contains(57)==true){
			        %>
					<th>操作</th>
					<%
					    }
				    %>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${perlist}" var="per">
					<tr target="id" rel="${per.id }">
						
						<td><c:out value="${per.id}"></c:out></td>
						<td><c:out value="${per.email}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${per.huge==1}">
								企业
							</c:when>
							<c:otherwise>
								个人
							</c:otherwise>
						</c:choose></td>
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
						
						<td><c:out value="${per.time}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${per.iflock==0}">
								审核中
							</c:when>
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
						<td>
						<%
						    if(navids.contains(50)==true){
					    %>  
						 <a title="查看" target="dialog" rel="person${per.id}" href="<%=adminbasePath %>/person/showPerson?id=${per.id}" class="btnView"  width="1178" height="721">查看</a>
						<%
							 }
						%>
						<%
						     if(navids.contains(57)==true){
						%>					 
						 <a class="btnEdit" title="修改状态" href="<%=adminbasePath%>/person/showPersonState?id=${per.id}" target="dialog" rel="perstate${per.id}" width="410" height="200"><span>编辑</span></a>
						<%
							 }
						%>
						 </td>
					</tr>
				</c:forEach>
				<c:forEach items="${partici}" varStatus="l" var="par">
				<option value="${par.codeid}"><c:out value="${par.payable}"></c:out></option>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>				
			</div>
			<div class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>