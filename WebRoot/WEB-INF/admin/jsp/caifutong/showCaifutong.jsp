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

			<input type="hidden" name="sp_billno" value="${caifutong.sp_billno}">
			<fieldset>
				<legend>订单信息</legend>
				<dl>
					<dt>订单号</dt>
					<dd>
						<c:out value="${caifutong.sp_billno }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>财付通订单号</dt>
					<dd>
						<c:out value="${caifutong.transaction_id }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>用户财付通账号</dt>
					<dd>
						<c:out value="${caifutong.purchaser_id }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户时间</dt>
					<dd>
						<c:out value="${caifutong.date }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>订单总金额</dt>
					<dd>
						<c:out value="${caifutong.total_fee }"></c:out>（元）
					</dd>
				</dl>
				
				<dl>
					<dt>分账回退结果</dt>
					<dd>
						<c:out value="${caifutong.pay_result }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>分账回退结果信息</dt>
					<dd>
						<c:choose>
							<c:when test="${caifutong.pay_info==0}">退款成功</c:when>
							<c:when test="${caifutong.pay_info==2}">订单处理中</c:when>
							<c:otherwise>订单失败</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>退款单ID</dt>
					<dd>
						<c:out value="${caifutong.refund_id }"></c:out>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt>业务参数</dt>
					<dd>
						<c:out value="${caifutong.bus_args }"></c:out>
					</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>