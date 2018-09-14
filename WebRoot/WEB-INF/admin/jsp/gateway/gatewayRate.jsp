<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/gateway/gatewayRate">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="iflock" value="${iflock}" />
		<input type="hidden" name="iflogin" value="${iflogin}" />
		<input type="hidden" name="ifnet" value="${ifnet}" />
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/gateway/gatewayRate"
			onsubmit="return navTabSearch(this)" method="post">
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>通道编号：</label>
						<input type="text" name="gateway_id" value="${gateway_id }"/>
					</li>
					<li>
						<label>通道名称：</label>
						<input type="text" name="gateway_name" value="${gateway_name }"/>
					</li>
					<li>
						<label>商户编号：</label>
						<input type="text" name="gateway_no" value="${gateway_no }"/>
					</li>
					<li style="width: 160px"><label>状态：</label>
						<select class="combox" name="gateway_status" id="iflock" style="width: 100px">
							<option value="">请选择</option>
							<option value="1">开启</option>
							<option value="0">关闭</option>
						</select>
					</li>
				</ul>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		
		<div class="panelBar">
			<ul class="toolBar">
			<%
			//if(employeeid==1){
			%>
				<li><a class="add"
						href="<%=adminbasePath%>/gateway/addGateway" rel="empadd"
						target=dialog width="900" height="600" mask=true><span>添加通道</span></a></li>
				</ul>
			<%
				//}
			%>
		</div>
		<table class="table" width="1150" layoutH="138">
			<thead>
				<tr>
					<th>通道编号</th>
					<th>通道名称</th>
					<th>商户编号</th>
					<th>支付宝费率</th>
					<th>微信费率</th>
					<th>qq费率</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${gatewaylist}" var="list">
					<tr>
						<td>${list.gateway_id }</td>
						<td>${list.gateway_name }</td>
						<td>${list.gateway_merid }</td>
						<td>${list.ali} %</td>
						<td>${list.weixin} %</td>
						<td>${list.qq} %</td>
						<td>
							<c:if test="${list.status == 1 }"><span style="color:red;font-weight:800;"><c:out value="已开启"></c:out></span></c:if>
							<c:if test="${list.status == 0 }"><span><c:out value="已关闭"></c:out></span></c:if>
						</td>
						<td>
							<a class="btnEdit" title="修改状态" href="<%=adminbasePath%>/gateway/editGateway?gateway_id=${list.gateway_id}" target="dialog" rel="perstate${per.id}" width="1000" height="360"><span>编辑</span></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20" <c:if test="${numPerPage==20 }">selected</c:if>>20</option>
					<option value="50" <c:if test="${numPerPage==50 }">selected</c:if>>50</option>
					<option value="100" <c:if test="${numPerPage==100 }">selected</c:if>>100</option>
					<option value="200" <c:if test="${numPerPage==200 }">selected</c:if>>200</option>
				</select> <span>条，共${totalCount}条</span>	
						
			</div>

			<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}"   currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>