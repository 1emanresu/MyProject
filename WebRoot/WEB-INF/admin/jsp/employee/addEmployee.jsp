<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/employee/addEmployee" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>添加用户</legend>
					<dl class="nowrap">
						<dt>账号：</dt>
						<dd>
							<input type="text" name="account" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>名称：</dt>
						<dd>
							<input type="text" name="name" class="required" />
						</dd>
					</dl>
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
					<dl class="nowrap">
						<dt>手机号：</dt>
						<dd>
							<input type="text" name="phone" class="required phone" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>地址：</dt>
						<dd>
							<input type="text" name="address" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>邮箱：</dt>
						<dd>
							<input type="text" name="email" class="required email" />
						</dd>
					</dl>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>