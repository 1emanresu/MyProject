<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java"
	import="com.admin.gateway.service.AdGatewayService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function checkAll(name) {
		var el = document.getElementsByTagName('input');
		var len = el.length;
		for (var i = 0; i < len; i++) {
			if ((el[i].type == 'checkbox') && el[i].name == name) {
				el[i].checked = true;
			}
		}
	}
	function clearAll(name) {
		var el = document.getElementsByTagName('input');
		var len = el.length;
		for (var i = 0; i < len; i++) {
			if ((el[i].type == 'checkbox') && el[i].name == name) {
				el[i].checked = false;
			}
		}
	}
</script>
</head>
<body>
	<div class="pageContent">
		<table class="table" height="138">
			<thead>
				<tr>
					<th width="70px"><input type="checkbox"
						onclick="if(this.checked==true){checkAll('view1list');}else{clearAll('view1list');}" />全选</th>
					<th width="100px">用户编号</th>
					<th width="100px">支付宝编号</th>
					<th width="100px">支付宝名称</th>
					<th width="100px">微信编号</th>
					<th width="100px">微信名称</th>
					<th width="100px">QQ编号</th>
					<th width="100px">QQ名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${getView1}" var="view1">

					<tr>
						<td align="middle"><input name="view1list" id="id${view1.id}"
							type="checkbox" value="${view1.id}" /> 单选</td>
						<td align="middle"><c:out value="${view1.id}"></c:out></td>
						<td align="middle"><c:out value="${view1.aliid}"></c:out></td>
						<td align="middle"><c:out value="${view1.aliname}"></c:out></td>
						<td align="middle"><c:out value="${view1.wxid}"></c:out></td>
						<td align="middle"><c:out value="${view1.wxname}"></c:out></td>
						<td align="middle"><c:out value="${view1.weixinid}"></c:out></td>
						<td align="middle"><c:out value="${view1.weixinname}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<script type="text/javascript">
			$('#alipay').click(
					function() {
						var kk="<%=adminbasePath%>/gateway/upView/";
				
						$("#alipay").attr("href",kk+ $('#ALI option:selected').val());
					});
			$('#wxpay').click(
					function() {
						var kk="<%=adminbasePath%>/gateway/upView/";
				$("#wxpay").attr("href", kk + $('#WX option:selected').val());
			});

			$('#weixin').click(
					function() {
						var kk="<%=adminbasePath%>/gateway/upView/";
				$("#weixin").attr("href", kk + $('#WEIXIN option:selected').val());
			});
			
		</script>
		<br />
		<table>
			<tr>
				<td><select id="ALI">
						<option value=""></option>
						<c:forEach items="${alipaylist}" var="alipaywap">
							<option value="${alipaywap.gateway_id}-alipaywap"><c:out
									value="${alipaywap.gateway_name}"></c:out></option>
						</c:forEach>

				</select></td>
				<td width='30px'></td>
				<td><a id="alipay" title="确实要对这些商户切换通道吗?" target="selectedTodo"
					rel="view1list" href="<%=adminbasePath%>/gateway/upView/"
					class="delete"><span><button>支付宝通道一键切换</button></span></a></td>
			
				<td width='30px'></td>
				<td><select id="WX">
						<option value=""></option>
						<c:forEach items="${wxpaylist}" var="wxpaywap">
							<option value="${wxpaywap.gateway_id}-wxwap">
								<c:out value="${wxpaywap.gateway_name}"></c:out>
							</option>

						</c:forEach>
				</select></td>
				<td width='30px'></td>
				<td><a id="wxpay" title="确实要对这些商户切换通道吗?" target="selectedTodo"
					rel="view1list" href="<%=adminbasePath%>/gateway/upView/"
					class="delete"><span><button>微信通道一键切换</button></span></a></td>
					
					
				<td width='30px'></td>
				<td><select id="WEIXIN">
						<option value=""></option>
						<c:forEach items="${weixinlist}" var="weixin">
							<option value="${weixin.gateway_id}-weixin">
								<c:out value="${weixin.gateway_name}"></c:out>
							</option>

						</c:forEach>
				</select></td>
				<td width='30px'></td>
				<td><a id="weixin" title="确实要对这些商户切换通道吗?" target="selectedTodo"
					rel="view1list" href="<%=adminbasePath%>/gateway/upView/"
					class="delete"><span><button>QQ通道一键切换</button></span></a></td>
				
				</tr>

		</table>

	</div>


</body>
</html>