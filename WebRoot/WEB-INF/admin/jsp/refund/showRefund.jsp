<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$("#refund_state option[value='${refund.refund_state}']").attr("selected",
			true);
</script>
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/refund/upRefund"
			class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" value="${refund.refund_id }" name="refund_id" />
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>提现订单信息</legend>
					<dl>
						<dt>用户：</dt>
						<dd>
							<c:out value="${refuname}"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>退款订单：</dt>
						<dd>
							<c:out value="${refund.refund_id }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>退款金额：</dt>
						<dd>
							<c:out value="${refund.refund_amount }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>实际退款金额：</dt>
						<dd>
							<c:out value="${refund.refund_actual }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>手续费：</dt>
						<dd>
							<c:out value="${refund.refund_fees }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>申请时间：</dt>
						<dd>
							<c:out value="${refund.createtime }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>提现权限：</dt>
						<dd>
							<c:choose>
								<c:when test="${refund.settlementauthority==1 }">T+0</c:when>
								<c:when test="${refund.settlementauthority==2 }">T+1</c:when>
							</c:choose>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>提款账户</legend>
					<dl>
						<dt>姓名：</dt>
						<dd>
							<c:out value="${refund.account_name }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>收款人开户行的省名：</dt>
						<dd>
							<c:out value="${refund.branchsheng }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>收款人开户行的市名：</dt>
						<dd>
							<c:out value="${refund.branchshi }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>开户支行/网络账号：</dt>
						<dd>
							<c:out value="${refund.branch }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>账号：</dt>
						<dd>
							<c:out value="${refund.account }"></c:out>
						</dd>
					</dl>
					<dl>
						<dt>银行/网络账号：</dt>
						<dd>
							<c:out value="${refund.net_name }"></c:out>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>提现操作</legend>
					<dl class="nowrap">
						<dt>提现状态：</dt>
						<dd>
							<c:choose>
								<c:when test="${refund.refund_state==2 }">
									<input type="radio" name="refund_state" id="refund_state" value="1" />成功
									<input type="radio" name="refund_state" id="refund_state" value="2" checked/>处理中
									<input type="radio" name="refund_state" id="refund_state" value="3" />失败									
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${refund.refund_state==1 }">成功</c:when>
										<c:when test="${refund.refund_state==2 }">处理中</c:when>
										<c:when test="${refund.refund_state==3 }">失败</c:when>
									</c:choose>
								</c:otherwise>
							</c:choose>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="buttonContent">提交</button>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>提现说明：</dt>
						<dd>
							<c:choose>
								<c:when test="${refund.refund_state==2 }">
									<textarea name="explain" cols="80" rows="3">
										<c:out value="${refund.explain }"></c:out> </textarea>
								</c:when>
								
								<c:otherwise>
									<c:out value="${refund.explain }"></c:out>
								</c:otherwise>
							</c:choose>							
						</dd>
					</dl>					
				</fieldset>
			</div>			
		</form>
	</div>
</body>
</html>