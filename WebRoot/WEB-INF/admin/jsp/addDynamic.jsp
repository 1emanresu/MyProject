<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageFormContent" layoutH="0">
		<form action="<%=adminbasePath %>/dynamic/addDynamic" class="pageForm required-validate" method="post" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<fieldset>
			<legend>添加动态</legend>
			<dl class="nowrap">
				<dt>标题：</dt>
				<dd>
					<textarea name="title" class="required" cols="80" rows="2"></textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>内容：</dt>
				<dd>
					<textarea class="editor required" name="content" rows="29" cols="85"></textarea>
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
</body>
</html>