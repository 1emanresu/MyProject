<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function UpdateAlarm(gateway_id,isno,sumerrs,telno,type){
	var sum = document.getElementById(sumerrs).value;
	var telno = document.getElementById(telno).value;		
	var chkObjs = document.getElementsByName(isno);
    for(var i=0;i<chkObjs.length;i++){
        if(chkObjs[i].checked){
           isno = chkObjs[i].value; 
        }
    }
	var strs = gateway_id+"&isno="+isno+"&telno="+telno+"&sum="+sum+"&type="+type;
	$.ajax({
		url: '<%=adminbasePath%>/gateway/AlarmUpdate?gateway_id='+strs,
		type:'post',
		error:function(){
			alert('数据获取失败');
		},
		success : function(datas) {
			layer.msg('恭喜你,设置成功!', 5, {
			    rate: 'top',
			    type: 9,
			    shade: false
			});
		}
	});
}
</script>
</head>
<body>
	<h2 class="contentTitle">系统通道预警管理</h2>	 
	<div class="pageFormContent" layoutH="42">
		<div style="border: 1px solid #B8D0D6; padding: 5px; margin: 5px">
				<table class="table" width="900" layoutH="138">
					<thead>
						<tr align="center">
							<th>通道名称</th>
							<th>通道类型</th>
							<th>是否加入预警</th>
							<th>不处理条数</th>
							<th>短信提醒</th>
							<th>&nbsp;</th>
							<th>操作</th>
						</tr>
					</thead>						
					<tbody>
					
						<c:forEach items="${gatewaylist}" var="gateways">							
							<c:if test="${gateways.wxwap=='y'}">															
								<tr>																	
									<td><c:out value="${gateways.gateway_name}"></c:out></td>
									<td>微信H5</td>
									<td align="center">
										<input  type="radio" name="wxh5isno${gateways.gateway_id}" id="wxh5is${gateways.gateway_id}" value="y">是
										<input  type="radio" name="wxh5isno${gateways.gateway_id}" id="wxh5no${gateways.gateway_id}" value="n">否
									</td>
									<td align="center"><input type="text" name="wxh5sum${gateways.gateway_id}" id="wxh5sum${gateways.gateway_id}" value="${gateways.wxsum}"></td>
									<td align="center"><input type="text" name="wxh5tel${gateways.gateway_id}" id="wxh5tel${gateways.gateway_id}" value="${gateways.telno}"></td>																		
									<script type="text/javascript">										
										var wxwap_isno = "${gateways.wxwap_isno}";
										if (wxwap_isno == "y") {											
											$("input[type='radio'][id='wxh5is${gateways.gateway_id}']").attr("checked",true);//所有单选按钮都不选中							
										}else{
											$("input[type='radio'][id='wxh5no${gateways.gateway_id}']").attr("checked",true);
										}					                					            
									</script>
									<td><input type="hidden" name="gateway_id" value="${gateways.gateway_id}"></td>
									<td align="center"><button type="button" onClick="UpdateAlarm('${gateways.gateway_id}','wxh5isno${gateways.gateway_id}','wxh5sum${gateways.gateway_id}','wxh5tel${gateways.gateway_id}','wxwap_isno')">设置</button></td>																	
								</tr>								
							</c:if>
							
							<c:if test="${gateways.alipaywap=='y'}">
							<input type="hidden" name="gateway_id" value="${gateways.gateway_id}">
							<input type="hidden" name="alipaywap_isno" value="1">
								<tr>
									<td><c:out value="${gateways.gateway_name}"></c:out></td>
									<td>支付宝H5</td>
									<td align="center">
										<input  type="radio" name="alih5isno${gateways.gateway_id}" id="alih5is${gateways.gateway_id}" value="y">是
										<input  type="radio" name="alih5isno${gateways.gateway_id}" id="alih5no${gateways.gateway_id}" value="n">否
									</td>
									<td align="center"><input type="text" name="alih5sum${gateways.gateway_id}" id="alih5sum${gateways.gateway_id}" value="${gateways.aliwapsum}"></td>
									<td align="center"><input type="text" name="alih5tel${gateways.gateway_id}" id="alih5tel${gateways.gateway_id}" value="${gateways.telno}"></td>
									<td><input type="hidden" name="gateway_id" value="${gateways.gateway_id}"></td>
									<td align="center"><button type="button" onClick="UpdateAlarm('${gateways.gateway_id}','alih5isno${gateways.gateway_id}','alih5sum${gateways.gateway_id}','alih5tel${gateways.gateway_id}','alipaywap_isno')">设置</button></td>								
									<script type="text/javascript">										
										var alipaywap_isno = "${gateways.alipaywap_isno}";										
										if (alipaywap_isno == "y") {
											$("input[type='radio'][id='alih5is${gateways.gateway_id}']").attr("checked",true);//所有单选按钮都不选中	
										}else{
											$("input[type='radio'][id='alih5no${gateways.gateway_id}']").attr("checked",true);
										}					                					            
									</script>
								</tr>
							</c:if>	
								
							<c:if test="${gateways.weixin=='y'}">							
								<tr>
									<td><c:out value="${gateways.gateway_name}"></c:out></td>
									<td>微信扫码</td>
									<td align="center">
										<input  type="radio" name="wxsmisno${gateways.gateway_id}" id="wxsmis${gateways.gateway_id}" value="y">是
										<input  type="radio" name="wxsmisno${gateways.gateway_id}" id="wxsmno${gateways.gateway_id}" value="n">否
									</td>
									<td align="center"><input type="text" name="wxsmsum${gateways.gateway_id}" id="wxsmsum${gateways.gateway_id}" value="${gateways.weixinsum}"></td>
									<td align="center"><input type="text" name="wxsmtel${gateways.gateway_id}" id="wxsmtel${gateways.gateway_id}" value="${gateways.telno}"></td>
									<td><input type="hidden" name="gateway_id" value="${gateways.gateway_id}"></td>	
									<td align="center"><button type="button" onClick="UpdateAlarm('${gateways.gateway_id}','wxsmisno${gateways.gateway_id}','wxsmsum${gateways.gateway_id}','wxsmtel${gateways.gateway_id}','weixin_isno')">设置</button></td>									
									<script type="text/javascript">										
										var weixin_isno = "${gateways.weixin_isno}";										
										if (weixin_isno == "y") {
											$("input[type='radio'][id='wxsmis${gateways.gateway_id}']").attr("checked",true);//所有单选按钮都不选中
										}else{
											$("input[type='radio'][id='wxsmno${gateways.gateway_id}']").attr("checked",true);
										}					                					            
									</script>
								</tr>
							</c:if>	
								
							<c:if test="${gateways.alipay=='y'}">
								<tr>
									<td><c:out value="${gateways.gateway_name}"></c:out></td>
									<td>支付宝扫码</td>
									<td align="center">
										<input  type="radio" name="alismisno${gateways.gateway_id}" id="alismis${gateways.gateway_id}" value="y">是
										<input  type="radio" name="alismisno${gateways.gateway_id}" id="alismno${gateways.gateway_id}" value="n">否
									</td>
									<td align="center"><input type="text" name="alismsum${gateways.gateway_id}" id="alismsum${gateways.gateway_id}" value="${gateways.alipaysum}"></td>
									<td align="center"><input type="text" name="alismtel${gateways.gateway_id}" id="alismtel${gateways.gateway_id}" value="${gateways.telno}"></td>
									<td><input type="hidden" name="gateway_id" value="${gateways.gateway_id}"></td>	
									<td align="center"><button type="button" onClick="UpdateAlarm('${gateways.gateway_id}','alismisno${gateways.gateway_id}','alism5sum${gateways.gateway_id}','alismtel${gateways.gateway_id}','alipay_isno')">设置</button></td>								
									<script type="text/javascript">										
										var alipay_isno = "${gateways.alipay_isno}";										
										if (alipay_isno == "") {
											$("input[type='radio'][id='alismis${gateways.gateway_id}']").attr("checked",true);//所有单选按钮都不选中
										}else{
											$("input[type='radio'][id='alismno${gateways.gateway_id}']").attr("checked",true);
										}				                					            
									</script>
								</tr>
							</c:if>						
						</c:forEach>
					</tbody>
				</table>
		</div>
	</div>
</body>
</html>