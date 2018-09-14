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
		<div class="pageFormContent" layoutH="58">
			<fieldset>
				<legend>时间查询</legend>
				<form action="<%=adminbasePath%>/statistics/showStatistics" class="pageForm required-validate"  method="post" onsubmit="return divSearch(this, 'statisticsBox');" >
					<dl style="width: 200px">
						<dt style="width: 50px">时间：</dt>
						<dd style="width: 100px">
							<input type="text" name="time1" value="${time1}" class="date"
								dateFmt="yyyy-MM-dd" />
						</dd>
					</dl>
					<dl style="width: 200px">
						<dt style="width: 10px">-</dt>
						<dd style="width: 150px">
							<input type="text" name="time2" value="${time2}" class="date"
								dateFmt="yyyy-MM-dd" />
						</dd>
					</dl>
					<dl style="width: 100px">
						<dt>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div>
						</dt>
					</dl>
				</form>
			</fieldset>
			<fieldset>
				<legend>统计信息</legend>
				<dl>
					<dt>点卡交易金额：</dt>
					<dd>${carstatis.p3_Amt }（元）</dd>
				</dl>

				<dl>
					<dt>点卡收益金额：</dt>
					<dd>${carstatis.deducted }（元）</dd>
				</dl>
				<dl>
					<dt>网银交易金额：</dt>
					<dd>${netstatis.p3_Amt }（元）</dd>
				</dl>

				<dl>
					<dt>网银收益金额：</dt>
					<dd>${netstatis.deducted }（元）</dd>
				</dl>
				<dl>
					<dt>销卡交易金额：</dt>
					<dd>${circlipstatis.actual }（元）</dd>
				</dl>

				<dl>
					<dt>商户销卡扣除金额：</dt>
					<dd>${circlipstatis.deducted }（元）</dd>
				</dl>
				<dl>
					<dt>充值交易金额：</dt>
					<dd>${rechargestatis.p3_Amt }（元）</dd>
				</dl>

				<dl>
					<dt>商户充值扣除金额：</dt>
					<dd>${rechargestatis.deducted }（元）</dd>
				</dl>
				<dl>
					<dt>退款交易金额：</dt>
					<dd>${refundstatis.refund_amount }（元）</dd>
				</dl>
				<dl class="nowrap" style="width: 400px">
					<dt style="width: 150px">商户点卡实际到账金额：</dt>
					<dd style="width: 150px">${carstatis.amount }（元）</dd>
				</dl>
				<dl class="nowrap" style="width: 400px">
					<dt style="width: 150px">商户网银实际交易金额：</dt>
					<dd style="width: 150px">${netstatis.amount }（元）</dd>
				</dl>
				<dl class="nowrap" style="width: 400px">
					<dt style="width: 150px">商户销卡实际交易金额：</dt>
					<dd style="width: 150px">${circlipstatis.accountvalue }（元）</dd>
				</dl>
				<dl class="nowrap" style="width: 400px">
					<dt style="width: 150px">商户充值实际交易金额：</dt>
					<dd style="width: 150px">${rechargestatis.amount }（元）</dd>
				</dl>
			</fieldset>
		</div>
	</div>
</body>
</html>