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
	<div class="pageFormContent" layoutH="0">
		<form action="<%=adminbasePath %>/power/addPower" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<fieldset>
			<legend>添加权限</legend>
			<dl class="nowrap">
				<dt>名称：</dt>
				<dd>
					<input type="text" name="name"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>说明：</dt>
				<dd>
					<input type="text" name="explain"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dd>
					<div class="button"><div class="buttonContent"><button type="submit">提交</button></div></div>
				</dd>
			</dl>
		</fieldset>
		</form>
	</div>
	</div>
</body>
</html>