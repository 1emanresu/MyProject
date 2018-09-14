<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#upiflock option[value='${person.iflock}']").attr("selected", true);
$("#upifnet option[value='${person.ifnet}']").attr("selected", true);
</script>
</head>
<body>
	<div class="pageFormContent">
		<form action="<%=adminbasePath%>/person/upPersonState" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" name="id" value="${person.id}">
			<fieldset>
				<legend>用户状态</legend>
				<dl>
					<dt>锁定状态</dt>
					<dd>
						<select class="combox" name="iflock" id="upiflock">
							<option value="1">正常</option>
							<option value="2">锁定</option>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>网银状态</dt>
					<dd>
						<select class="combox" name="ifnet" id="upifnet">
							<option value="1">开通</option>
							<option value="2">未开通</option>
						</select>
					</dd>
				</dl>
				<dl class="nowrap">
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"">修改</button>
						</div>
					</div>
				</dl>
			</fieldset>
		</form>
	</div>
</body>
</html>