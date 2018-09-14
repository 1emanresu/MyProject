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
		<form action="<%=adminbasePath%>/employee/upEmpPass" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input name="empid" type="hidden" value="${empid}"/>
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>修改密码</legend>
					<dl class="nowrap">
						<dt>密码：</dt>
						<dd>
							<input type="password" name="password" id="password" class="required alphanumeric" minlength="6" maxlength="20" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>确认密码：</dt>
						<dd>
							<input type="password" name="repassword" class="required" equalto="#password" />
						</dd>
					</dl>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>