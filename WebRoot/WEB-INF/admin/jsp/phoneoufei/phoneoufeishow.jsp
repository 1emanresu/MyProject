<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageContent">
		<div class="pageFormContent">
			<fieldset>
				<legend>订单信息</legend>
				<dl>
					<dt>订单编号</dt>
					<dd>
						<c:out value="${onlineorder.sporder_id }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>面值</dt>
					<dd>
						<c:out value="${onlineorder.cardnum }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>订单时间</dt>
					<dd>
						<c:out value="${onlineorder.sporder_time }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>手机号</dt>
					<dd>
						<c:out value="${onlineorder.game_userid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付结果</dt>
					<dd>
						<c:choose>
							<c:when test="${onlineorder.ret_code==1}">成功</c:when>
							<c:when test="${onlineorder.ret_code==2}">处理中</c:when>
							<c:when test="${onlineorder.ret_code==9}">失效</c:when>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>MD5后字符串</dt>
					<dd>
						<c:out value="${onlineorder.md5_str }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>处理时间</dt>
					<dd>
						<c:out value="${onlineorder.ordersuccesstime }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>失败原因</dt>
					<dd>
						<c:out value="${onlineorder.err_msg }"></c:out>
					</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>