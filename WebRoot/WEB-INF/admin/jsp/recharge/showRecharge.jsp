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

			<input type="hidden" name="netorderid" value="${recharge.rechargeid}">
			<fieldset>
				<legend>订单信息</legend>
				<dl>
					<dt>订单编号</dt>
					<dd>
						<c:out value="${recharge.rechargeid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>业务类型</dt>
					<dd>
						<c:out value="${recharge.p0_Cmd }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户名称</dt>
					<dd>
						<c:out value="${rechname }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户编号</dt>
					<dd>
						<c:out value="${recharge.p1_MerId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付结果</dt>
					<dd>
						<c:choose>
							<c:when test="${recharge.r1_Code==1}">成功</c:when>
							<c:when test="${recharge.r1_Code==2}">处理中</c:when>
							<c:when test="${recharge.r1_Code==3}">失败</c:when>
							<c:when test="${recharge.r1_Code==4}">失效</c:when>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>支付交易流水号</dt>
					<dd>
						<c:out value="${recharge.r2_TrxId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户订单号</dt>
					<dd>
						<c:out value="${recharge.p2_Order }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付金额</dt>
					<dd>
						<c:out value="${recharge.p3_Amt }"></c:out>
						（元）
					</dd>
				</dl>
				<dl>
					<dt>交易币种</dt>
					<dd>
						<c:out value="${recharge.p4_Cur }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商品名称</dt>
					<dd>
						<c:out value="${recharge.p5_Pid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商品种类</dt>
					<dd>
						<c:out value="${recharge.p6_Pcat }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商品描述</dt>
					<dd>
						<c:out value="${recharge.p7_Pdesc }"></c:out>
					</dd>
				</dl>

				<dl>
					<dt>送货地址</dt>
					<dd>
						<c:out value="${recharge.p9_SAF }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付银行</dt>
					<dd>
						<c:out value="${pdname }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付通道编码</dt>
					<dd>
						<c:out value="${recharge.pd_FrpId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户扩展信息</dt>
					<dd>
						<c:out value="${recharge.pa_MP }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>银行订单号</dt>
					<dd>
						<c:out value="${recharge.ro_BankOrderId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付成功时间</dt>
					<dd>
						<c:out value="${recharge.rp_PayDate }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>交易结果通知时间</dt>
					<dd>
						<c:out value="${recharge.ru_Trxtime }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>应答机制</dt>
					<dd>
						<c:out value="${recharge.pr_NeedResponse }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>创建时间</dt>
					<dd>
						<c:out value="${recharge.CreateTime }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>签名数据</dt>
					<dd>
						<c:out value="${recharge.hmac }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>用户ip地址</dt>
					<dd>
						<c:out value="${recharge.ip }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>实际金额</dt>
					<dd>
						<c:out value="${recharge.amount }"></c:out>
						（元）
					</dd>
				</dl>
				<dl>
					<dt>扣除金额</dt>
					<dd>
						<c:out value="${recharge.deducted }"></c:out>
						（元）
					</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>