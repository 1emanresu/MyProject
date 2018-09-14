<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统一下单收银台</title>
</head>
<body>
	<form action="${PostUrl}" method="post" name="form">
		<table width="100%" height="100%" align="center">
			<tr>
				<td><div style="font-size:16px;">您本次充值金额是：${pay_amt}</div></td>
			</tr>			
			<tr>
				<td>
					<input type="hidden" name="version" id="version" value="${version}">
					<input type="hidden" name="is_phone" id="is_phone" value="${is_phone}">
					<input type="hidden" name="is_frame" id="is_frame" value="${is_frame}">
					<input type="hidden" name="meta_option" id="meta_option" value="${meta_option}">
					<input type="hidden" name="agent_id" id="version" value="${agent_id}">
					
					<input type="hidden" name="agent_bill_id" id="agent_bill_id" value="${agent_bill_id}">
					<input type="hidden" name="agent_bill_time" id="agent_bill_time" value="${agent_bill_time}">
					<input type="hidden" name="pay_type" id="pay_type" value="${pay_type}">
					<input type="hidden" name="pay_amt" id="pay_amt" value="${pay_amt}">
					<input type="hidden" name="notify_url" id="notify_url" value="${notify_url}">
					<input type="hidden" name="return_url" id="return_url" value="${return_url}">
					<input type="hidden" name="user_ip" id="user_ip" value="${user_ip}">
					<input type="hidden" name="goods_name" id="goods_name" value="${goods_name}">
					<input type="hidden" name="goods_num" id="goods_num" value="${goods_num}">
					<input type="hidden" name="goods_note" id="goods_note" value="${goods_note}">
					<input type="hidden" name="remark" id="remark" value="${remark}">
					<input type="hidden" name="sign" id="sign" value="${sign}">
				</td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="确认本次充值并提交" style=""></td>
			</tr>
		</table>		
	</form>
</body>
</html>
