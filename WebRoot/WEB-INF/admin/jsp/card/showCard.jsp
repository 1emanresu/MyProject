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
						<c:out value="${cardorder.orderid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>业务类型</dt>
					<dd>
						<c:out value="${cardorder.p0_Cmd }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>提交状态</dt>
					<dd>
						<c:choose>
							<c:when test="${cardorder.r1_Code==1}">提交成功</c:when>
							<c:otherwise>提交失败</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>商户名称</dt>
					<dd>
						<c:out value="${cardname }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户编号</dt>
					<dd>
						<c:out value="${cardorder.p1_MerId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>商户订单号</dt>
					<dd>
						<c:out value="${cardorder.p2_Order }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>错误代码</dt>
					<dd>
						<c:out value="${cardorder.rq_ReturnMsg }"></c:out>
					</dd>
				</dl>
				
				<dl>
					<dt>是否较验订单金额</dt>
					<dd>
						<c:out value="${cardorder.p4_verifyAmt }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>产品名称</dt>
					<dd>
						<c:out value="${cardorder.p5_Pid }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>产品类型</dt>
					<dd>
						<c:out value="${cardorder.p6_Pcat }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>产品描述</dt>
					<dd>
						<c:out value="${cardorder.p7_Pdesc }"></c:out>
					</dd>
				</dl>
				
				<dl>
					<dt>商户扩展信息</dt>
					<dd>
						<c:out value="${cardorder.pa_MP }"></c:out>
					</dd>
				</dl>

				<dl>
					<dt>卡面额组</dt>
					<dd>
						<c:out value="${cardorder.pa7_cardAmt }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>卡号组</dt>
					<dd>
						<c:out value="${cardorder.pa8_cardNo }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>卡密组</dt>
					<dd>
						<c:out value="${cardorder.pa9_cardPwd }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付通道名称</dt>
					<dd>
						<c:out value="${payable }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付通道编码</dt>
					<dd>
						<c:out value="${cardorder.pd_FrpId }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>应答机制</dt>
					<dd>
						<c:out value="${cardorder.pr_NeedResponse }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>创建时间</dt>
					<dd>
						<c:out value="${cardorder.CreateTime }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>签名数据</dt>
					<dd>
						<c:out value="${cardorder.hmac }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>用户ip地址</dt>
					<dd>
						<c:out value="${cardorder.ip }"></c:out>
					</dd>
				</dl>
				<dl>
					<dt>支付金额</dt>
					<dd>
						<c:out value="${cardorder.p3_Amt }"></c:out>（元）
					</dd>
				</dl>
				<dl>
					<dt>实际金额</dt>
					<dd>
						<c:out value="${cardorder.amount }"></c:out>（元）
					</dd>
				</dl>
				<dl>
					<dt>扣除金额</dt>
					<dd>
						<c:out value="${cardorder.deducted }"></c:out>
						（元）
					</dd>
				</dl>
				<dl>
					<dt>是否被扣单</dt>
					<dd>
						<c:choose>
							<c:when test="${cardorder.lock==1}">正常</c:when>
							<c:when test="${cardorder.lock==2}">被扣单</c:when>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>是否反馈</dt>
					<dd>
						<c:choose>
							<c:when test="${cardorder.success==1}">
									已通知
								</c:when>
							<c:when test="${cardorder.success==2}">
									未通知
							</c:when>
						</c:choose>
						<c:if test="${cardorder.r1_Code==1}">
							<a style="float: right;" class="button" target="_blank"
								href="<%=basePath %>/hscard/cardret?orderid=${cardorder.orderid}"><span>通知</span></a>
						</c:if>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt>商户接收数据的地址</dt>
					<dd>
						<c:out value="${cardorder.p8_Url }"></c:out>
					</dd>
				</dl>
				<dl class="nowrap">
					<dt>回调地址</dt>
					<dd>
						<%=basePath %>/hscard/cardret?orderid=${cardorder.orderid}
					</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>