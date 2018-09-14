<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<h2 class="contentTitle">通道列表</h2>
	<div class="pageFormContent" layoutH="42">
		<div style="border: 1px solid #B8D0D6; padding:20px; margin:10px">
			<form action="<%=adminbasePath%>/gateway/upGateway" method="post" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
				<input type="hidden" name="gateway_id" value="${gateway_id }" />
				<fieldset>
					<legend>通道编号：${gateway_id }</legend>
					<dl style="width: 300px">
						<dt style="width: 60px">通道名称：</dt>
						<dd>${gateway_name }</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">商户编号：</dt>
						<dd>
							<input type="text" name="gateway_merid" value="${gateway_merid }" />
						</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">今日额度：</dt>
						<dd>
							<input type="text" name="amount_size" value="${amount_size }" />
						</dd>
					</dl>
					<dl style="width:300px">
						<dt style="width: 60px">状态：</dt>
						<dd style="margin-top:-5px;">
							<select name="status" style="padding:5px;">
								<option value="0">关闭</option>
								<option value="1">开启</option>
							</select>
						</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">开始时间：</dt>
						<dd>
							<input type="text" name="start_time" value="${start_time }" class="date" dateFmt="yyyy-MM-dd HH:mm:ss"/>
						</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">结束时间：</dt>
						<dd>
							<input type="text" name="end_time" value="${end_time }" class="date" dateFmt="yyyy-MM-dd HH:mm:ss"/>
						</dd>
					</dl>
					<div class="hei_5 clear_both"></div>
					<dl style="width: 300px">
						<dt style="width: 80px">支付宝费率：</dt>
						<dd style="width:200px;">
							<input type="text" name="ali" value="${ali}"  style="width:55px" />&nbsp;%
						</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">微信费率：</dt>
						<dd>
							<input type="text" name="weixin" value="${weixin}"  style="width:55px" />&nbsp;%
						</dd>
					</dl>
					<dl style="width: 300px">
						<dt style="width: 60px">qq费率：</dt>
						<dd>
							<input type="text" name="qq" value="${qq}"  style="width:55px" />&nbsp;%
						</dd>
					</dl>
					<dl>
						<dt style="width: 60px">商户密钥：</dt>
						<dd>
							<input type="text" style="width:446px" name="gateway_key" value="${gateway_key }" />
						</dd>
					</dl>
					<div class="hei_10 clear_both"></div>
					<div class="t_align_c wid_b_100 ">
						<span class="in_block">
							<div class="button">
								<div class="buttonContent">
									<button type="submit">修改</button>
								</div>
							</div>
							<div class="clear_both"></div>
						
						</span>
					</div>
					
				</fieldset>
			</form>
			<div style="clear:both;"></div>
		</div>
	</div>
</body>
</html>