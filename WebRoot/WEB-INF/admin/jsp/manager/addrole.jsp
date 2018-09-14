<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body layoutH="400">
<div class="pageContent" >
	<div class="pageFormContent" layoutH="0">
		<form action="<%=adminbasePath %>/rolemanager/addRole" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<fieldset>
			<legend>添加角色</legend>
			<dl class="nowrap">
				<dt>角色编号：</dt>
				<dd>
					<input type="text" name="roleid"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>角色名称：</dt>
				<dd>
					<input type="text" name="rolename"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd>
					<input type="text" name="depict"/>
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