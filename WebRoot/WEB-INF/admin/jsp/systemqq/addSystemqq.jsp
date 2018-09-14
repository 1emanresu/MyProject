<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#addtakeaway_state option[value='${takeaway.takeaway_state}']").attr("selected", true);
</script>
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/system/upSystemQQ" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" name="qq_id" value="${system_qq.qq_id}" />
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>添加QQ</legend>
					<dl class="nowrap">
						<dt>QQ号码：</dt>
						<dd>
							<input type="text" name="qq_number" value="${system_qq.qq_number }" class="required" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>QQ名称：</dt>
						<dd>
							<input type="text" name="qq_name" value="${system_qq.qq_name }" class="required" />
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