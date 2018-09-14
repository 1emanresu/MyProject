<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#empiflock option[value='${emp.iflock}']").attr("selected", true);
</script>
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/employee/upEmp" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input name="empid" type="hidden" value="${empid}"/>
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>添加动态</legend>
					<dl class="nowrap">
						<dt>账号：</dt>
						<dd>
							<input type="text" value="${emp.account}" name="account" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>名称：</dt>
						<dd>
							<input type="text" value="${emp.name}" name="name" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>部门：</dt>
						<dd>
							<input id="inputOrg1" name="power.powerid" value="${emp.powerid}" type="hidden" /> 
							<input class="required" name="power.name" value="${pow.name}" type="text" postField="keyword" suggestFields="powerid,name" 
								suggestUrl="<%=adminbasePath %>/employee/getPowerjson" lookupGroup="power" /> 
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>手机号：</dt>
						<dd>
							<input type="text" value="${emp.phone}" name="phone" class="required phone" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>地址：</dt>
						<dd>
							<input type="text" value="${emp.address}" name="address" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>邮箱：</dt>
						<dd>
							<input type="text" value="${emp.email}" name="email" class="required email" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>提成：</dt>
						<dd>
							<input type="text" value="${emp.commission}" name="commission" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>状态：</dt>
						<dd>
							<select class="combox" name="iflock" id="empiflock" style="width: 100px">
								<option value="1">正常</option>
								<option value="2">锁定</option>
							</select>
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