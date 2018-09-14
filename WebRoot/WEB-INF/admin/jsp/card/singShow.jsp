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
					<dt>销卡编号</dt>
					<dd>
						<c:out value="${singlecard.orderno }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>卡类代码</dt>
					<dd>
						<c:out value="${singlecard.cardcode }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>卡类</dt>
					<dd>
						<c:out value="${payable }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>金额</dt>
					<dd>
						<c:out value="${singlecard.value }"></c:out>（元）
					</dd>
				</dl>
				<dl>
					<dt>实际金额</dt>
					<dd>
						<c:out value="${singlecard.actual }"></c:out>（元）
					</dd>
				</dl>
				<%-- <dl>
					<dt>xml</dt>
					<dd>
						<c:out value="${circlip.xml }"></c:out>
					</dd>
				</dl> --%>

				<dl>
					<dt>接口结算</dt>
					<dd>
						<c:out value="${singlecard.accountvalue }"></c:out>（元）
					</dd>
				</dl>
				<dl>
					<dt>扣除金额</dt>
					<dd>
						<c:out value="${singlecard.deducted }"></c:out>（元）
					</dd>
				</dl>
				<dl>
					<dt>充值卡的卡号</dt>
					<dd>
						<c:out value="${singlecard.cardno }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>充值卡密码</dt>
					<dd>
						<c:out value="${singlecard.cardpass }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>创建时间</dt>
					<dd>
						<c:out value="${singlecard.datetime }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>交易签名</dt>
					<dd>
						<c:out value="${singlecard.sign }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>订单流水编号</dt>
					<dd>
						<c:out value="${singlecard.billid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>交易结果</dt>
					<dd>
						<c:choose>
							<c:when test="${singlecard.result==2000}">
								成功
							</c:when>
							<c:when test="${singlecard.result==2001}">
								处理中
							</c:when>
							<c:when test="${singlecard.result!=2000&&singlecard.result!=2001}">
								失败
							</c:when>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>交易情况说明</dt>
					<dd>
						<c:out value="${singlecard.info }"></c:out>
					</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>