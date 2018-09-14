<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("input[name='settlementauthority'][value='${balance.settlementauthority}']").attr("checked",true); 
$("#herolist option[value='${person.herolist}']").attr("selected", true);
$("#huge option[value='${person.huge}']").attr("selected", true);
$("#upiflock option[value='${person.iflock}']").attr("selected", true);
$("#upifnet option[value='${person.ifnet}']").attr("selected", true);

	function changeKey(a,b){
		var me = document.getElementById(a).value;
		tome = me*100;
		fv = parseFloat(100-tome).toFixed(2);
		var md = document.getElementById(b);
		md.value = fv;
	}
	
	function changeKey2(a,b){
		var me = document.getElementById(a).value;
		tome = me/100;
		fv = parseFloat(1-tome).toFixed(3);
		var md = document.getElementById(b);
		md.value = fv;
	}

	function upkey(){
		$.ajax({
			url: '<%=adminbasePath%>/person/getkey',
			type:'post',
			error:function(){
				alert('数据获取失败');
			},
			success:function(data){
				$("#key").val(data);
			}
		})
	}
</script>
</head>
<body>
	<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}

.td1 {
	width: 90px;
}
</style>

	<div class="pageContent" style="padding: 5px">
		<div class="panel" defH="40">
			<h1>商户基本信息</h1>
			<div>
				<table>
					<tr>
						<td class="td1" align="right">商户id：</td>
						<td>${person.id}</td>
						<td class="td1" align="right">邮箱：</td>
						<td>${person.email }</td>
						<td class="td1" align="right">手机号码：</td>
						<td>${person.phone }</td>
						<td class="td1" align="right">商户类型：</td>
						<td>
							<c:choose>
								<c:when test="${person.huge==1}">
									企业
								</c:when>
								<c:otherwise>
									个人
								</c:otherwise>
							</c:choose>
						</td>
						<td class="td1" align="right">锁定状态：</td>
						<td>
							<c:choose>
								<c:when test="${person.iflock==1}">
									正常
								</c:when>
								<c:otherwise>
									锁定
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><br></td>
					</tr>
					<tr>
						<td class="td1" align="right">企业名称：</td>
						<td>${person.name }</td>
						<td class="td1" align="right">QQ号码：</td>
						<td>${person.qq }</td>
						<td class="td1" align="right">注册时间：</td>
						<td>${person.time }</td>
						<td class="td1" align="right">联系人：</td>
						<td>${person.contacts }</td>
						<td class="td1" align="right">网银状态：</td>
						<td>
							<c:choose>
								<c:when test="${person.ifnet==1}">
									开通
								</c:when>
								<c:otherwise>
									未开通
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="divider"></div>
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>用户订单</span></a></li>
						<%
							//if(employeeid==1){
						%>
						<li><a href="javascript:;"><span>用户密钥</span></a></li>
						<li><a href="javascript:;"><span>用户余额</span></a></li>
						<li><a href="javascript:;"><span>用户信息</span></a></li>
						<li><a href="javascript:;"><span>用户费率</span></a></li>
						<li><a href="javascript:;"><span>用户提现账户</span></a></li>
						<li><a href="javascript:;"><span>用户通道</span></a></li>
						<li><a href="javascript:;"><span>代理用户</span></a></li>
						<c:if test="${person.iflock==0 }">
							<li><a href="javascript:;"><span>审核资料</span></a></li>
						</c:if>
						<%
							//}
						%>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div layoutH="146"
						style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
						<ul class="tree treeFolder">
							<li><a href="javascript">用户订单</a>
								<ul>
									<li><a
										href="<%=adminbasePath %>/person/showNet?pageNum=1&numPerPage=15&netstarttime=&netendtime=&netparticipate=0&netstate=0&id=${person.id }"
										target="ajax" rel="jbsxBox">网银订单</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showCard?pageNum=1&numPerPage=20&cardstarttime=&cardendtime=&cardparticipate=0&cardstate=0&id=${person.id }"
										target="ajax" rel="jbsxBox">点卡订单</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showRecharge?pageNum=1&numPerPage=15&rechstarttime=&rechendtime=&rechparticipate=0&rechstate=0&id=${person.id }"
										target="ajax" rel="jbsxBox">充值订单</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showCirclip?pageNum=1&numPerPage=15&circstarttime=&circendtime=&circparticipate=0&circstate=0&id=${person.id }"
										target="ajax" rel="jbsxBox">销卡订单</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showRefund?pageNum=1&numPerPage=15&refustarttime=&refuendtime=&refustate=0&id=${person.id }"
										target="ajax" rel="jbsxBox">提现订单</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showAddedamount?pageNum=1&numPerPage=15&addestarttime=&addeendtime=&id=${person.id }"
										target="ajax" rel="jbsxBox">加款记录</a></li>
									<li><a
										href="<%=adminbasePath %>/person/showLog?pageNum=1&numPerPage=15&logstarttime=&logendtime=&id=${person.id }"
										target="ajax" rel="jbsxBox">登陆记录</a></li>
								</ul>
							</li>
						</ul>
					</div>
					<div id="jbsxBox" class="unitBox" style="margin-left: 246px;">
						<!--#include virtual="list1.html" -->
					</div>
				</div>
				<%
					//if(employeeid==1){
				%>
				<div layoutH="146" align="center" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent" align="center">
					<fieldset>
					<legend>用户密钥</legend>
					<form action="<%=adminbasePath %>/person/upPerPass" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<dl class="nowrap">
							<dt>登陆密码：</dt>
							<dd>
								<input name="password" style="width: 230px" value="" type="text" />
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>支付密码：</dt>
							<dd>
								<input name="payment" style="width: 230px" value="" type="text" />
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>接口key：</dt>
							<dd>
								<input name="key" style="width: 230px" value="${payment.key }" type="text" id="key" />
								<div class="buttonActive">
									<div class="buttonContent">
										<button onclick="upkey()" type="button">重新获取</button>
									</div>
								</div>
							</dd>
						</dl>
						<dl class="nowrap">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div>
						</dl>
					</form>
					</fieldset>
					</div>
				</div>
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent">
						<fieldset>
							<legend>用户余额</legend>
							<dl>
								<dt>今日实际金额：</dt>
								<dd><c:out value="${todayamount }"></c:out></dd>
							</dl>
							<dl>
								<dt>可结算金额：</dt>
								<dd><c:out value="${balance.settlement }"></c:out></dd>
							</dl>
							
							<dl>
								<dt>账户余额：</dt>
								<dd><c:out value="${todayamount+balance.settlement}"></c:out></dd>
							</dl>													
							<dl>
								<dt>已结算总金额：</dt>
								<dd><c:out value="${balance.refundamout }"></c:out></dd>
							</dl>
							<dl>
								<dt>手续费总金额：</dt>
								<dd><c:out value="${balance.deductednet }"></c:out></dd>
							</dl>
						</fieldset>
						<form action="<%=adminbasePath %>/person/upPersonMoney" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<input type="hidden" name="uppersonmoney" value="${uppersonmoney}" />
						<fieldset>
							<legend>添加金额</legend>
							<dl class="nowrap">
								<dt>金额：</dt>
								<dd>
									<input type="text" name="money" class="required" />
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">提交</button>
									</div>
								</div>
							</dl>
						</fieldset>
						</form>
						<form action="<%=adminbasePath %>/person/upSettAuthor" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>提款权限</legend>
							<dl class="nowrap">
								<dt>提款权限：</dt>
								<dd><label><input type="radio" name="settlementauthority" value="1" />T+0</label>
									<label><input type="radio" name="settlementauthority" value="2" />T+1</label></dd>
							</dl>
							<dl class="nowrap">
								<dt>最低提现金额：</dt>
								<dd>
									<input type="text" name="cashleast" value="${balance.cashleast }" />
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">修改</button>
									</div>
								</div>
							</dl>
						</fieldset>
						</form>
					</div>
				</div>
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent">
					<form action="<%=adminbasePath %>/person/upPerPerson" class="pageForm required-validate"  method="post" onsubmit="return validateCallback(this, dialogAjaxDone)">
					<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>用户信息</legend>
							<dl>
								<dt>邮箱</dt>
								<dd><input type="text" name="email" value="${person.email }"/></dd>
							</dl>
							<dl>
								<dt>客户备注</dt>
								<dd><input type="text" name="webName" value="${person.webName }"/></dd>
							</dl>
							<dl>
								<dt>网站网址</dt>
								<dd><input type="text" name="website" value="${person.website }"/></dd>
							</dl>
							<dl>
								<dt>企业名称</dt>
								<dd><input type="text" name="name" value="${person.name }"/></dd>
							</dl>
							<dl>
								<dt>联系人</dt>
								<dd><input type="text" name="contacts" value="${person.contacts }"/></dd>
							</dl>
							<dl>
								<dt>身份证/营业执照</dt>
								<dd><input type="text" name="idcard" value="${person.idcard }"/></dd>
							</dl>
							<dl>
								<dt>联系地址</dt>
								<dd><input type="text" name="address" value="${person.address }"/></dd>
							</dl>
							<dl>
								<dt>手机号码</dt>
								<dd><input type="text" name="phone" value="${person.phone }"/></dd>
							</dl>
							<dl>
								<dt>用户QQ号码</dt>
								<dd><input type="text" name="qq" value="${person.qq }"/></dd>
							</dl>
							<dl>
								<dt>行业</dt>
								<dd><select class="combox" name="herolist" id="herolist">
									<option value="0">党政机关</option>
									<option value="1">商业</option>
									<option value="2">制造业</option>
									<option value="3">服务业</option>
									<option value="4">农业牧渔也</option>
									<option value="5">其它行业</option>
								</select></dd>
							</dl>
							<dl>
								<dt>账户类型</dt>
								<dd><select class="combox" name="huge" id="huge">
									<option value="0">个人</option>
									<option value="1">企业</option>
								</select></dd>
							</dl>
							<dl>
								<dt>锁定状态</dt>
								<dd><select class="combox" name="iflock" id="upiflock">
									<option value="1">正常</option>
									<option value="2">锁定</option>
								</select></dd>
							</dl>
							<dl>
								<dt>网银状态</dt>
								<dd><select class="combox" name="ifnet" id="upifnet">
									<option value="1">开通</option>
									<option value="2">未开通</option>
								</select></dd>
							</dl>
						
							
							<dl>
								<dt>网银状态</dt>
								<dd><select class="combox" name="ifnet" id="upifnet">
									<option value="1">开通</option>
									<option value="2">未开通</option>
								</select></dd>
							</dl>
							<dl>
								<dt>是否是代理人：</dt>
								<dd>
									<c:if test="${person.isAgent==1 }">
										<input type="checkbox" name="isAgent" value="1" checked />
									</c:if>
									<c:if test="${person.isAgent!=1 }">
										<input type="checkbox" name="isAgent" value="1" />
									</c:if>
								</dd>
							</dl>
							
							<dl>
							<dt>下级用户代理权限：</dt>
								<dd>
									<c:if test="${person.permission==1 }">
										<input type="checkbox" name="permission" value="1"checked class="font_20"/>
									</c:if>
									<c:if test="${person.permission!=1 }">
										<input type="checkbox" name="permission" value="1"  class="font_20"/>
									</c:if>
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">修改</button>
									</div>
								</div>
							</dl>
						</fieldset>
					</form>
					</div>
				</div>
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent">
						<form action="<%=adminbasePath %>/person/upRate" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${id}">
						<fieldset>
						<legend>用户费率</legend>
						
						 <dl>
							<dt>qq钱包费率</dt>
							<dd><input type="text" name="qqwx" value="${rate.qqwx }"/></dd>
						</dl>
						<dl>
							<dt>微信费率</dt>
							<dd><input type="text" name="cibsm" value="${rate.cibsm }"/></dd>
						</dl>
						
						<dl>
							<dt>支付宝费率</dt>
							<dd><input type="text" name="mustali" value="${rate.mustali }"/></dd>
						</dl>
						 <dl>
							<dt>网银支付</dt>
							<dd><input style="width:60px;" type="text" name="banking" id="banking" value="${rate.banking}" onkeyup="changeKey('banking','banking2')"/>
							 <input style="width:10px;text-align:center;background:#FFF;border:0px;" type="text" name="banking1" value="——"/>
							<input style="width:50px;" type="text" name="banking2" id="banking2" value="${(1-rate.banking)*100 }" onkeyup="changeKey2('banking2','banking')"/>
							%
							</dd>
						</dl>  
						<dl>
							<dt>提现手续费</dt>
							<dd><input type="text" name="refund" value="${rate.refund }"/></dd>
						</dl>
						
						
						
						<dl class="nowrap">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">修改</button>
								</div>
							</div>
						</dl>
						
						</fieldset>
						</form>
					</div>
				</div>
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent">
<!-- 						<form action="<%=adminbasePath %>/person/upBankAccount" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)"> -->
<!-- 							<input type="hidden" name="id" value="${person.id}"> -->
							<fieldset>
								<legend>银行账户</legend>
								<c:choose>
									<c:when test="${empty bank}">
									<dl>
										<dd style="color:purple;">该用户尚未绑定银行卡</dd>
									</dl>
									</c:when>
									<c:otherwise>
										<table class="table" width="1000" layoutH="500">
											<thead>
												<tr>
													<th>账户姓名</th>
													<th>银行类型</th>
													<th>银行卡号</th>
													<th>开户行省名</th>
													<th>开户行市名</th>
													<th>开户支行</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${bank}" var="bk">
													<tr>
														<td>${bk.account_name }</td>
														<td>${bk.payable }</td>
														<td>${bk.account }</td>
														<td>${bk.branchsheng }</td>
														<td>${bk.branchshi }</td>
														<td>${bk.branch }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:otherwise>
								</c:choose>
							</fieldset>
<!-- 						</form> -->
<!-- 						<form action="<%=adminbasePath %>/person/upNetwork" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)"> -->
<!-- 							<input type="hidden" name="id" value="${person.id}"> -->
							<fieldset>
								<legend>网络账户</legend>
								<c:choose>
									<c:when test="${empty bank}">
									<dl>
										<dd style="color:purple;">该用户尚未绑定网络账户</dd>
									</dl>
									</c:when>
									<c:otherwise>
										<table class="table" width="1000" layoutH="500">
											<thead>
												<tr>
													<th>账户姓名</th>
													<th>账户类型</th>
													<th>收款账号</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${network}" var="net">
													<tr>
														<td>${net.account_name }</td>
														<td>${net.branch }</td>
														<td>${net.account }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:otherwise>
								</c:choose>
							</fieldset>
<!-- 						</form> -->
					</div>
				</div>
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					<div class="pageFormContent">
						<!--  <form action="<%=adminbasePath %>/person/upGateway" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>通道选择</legend>
							<dl class="nowrap">
								<dt>通道列表：</dt>
								<dd>
									<script type="text/javascript">
										$("input[name='gateway_id'][value='${gateway.gateway_id}']").attr("checked",true); 
									</script>
									<c:forEach items="${gatewaylist}" var="gateways">
										<label><input type="radio" name="gateway_id" value="${gateways.gateway_id }" /><c:out value="${gateways.gateway_name }"></c:out></label>
									</c:forEach>
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit"">修改</button>
								</div>
								</div>
							</dl>
						</fieldset>
						</form>
						-->
						<form action="<%=adminbasePath %>/person/upGateway1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>微信H5支付</legend>
							<dl class="nowrap">
								<dt>可选通道列表：</dt>
								<dd>
									<script type="text/javascript">
										var titles = "${gateway.title}";
										var ts = titles.split(";");
										var len = ts.length;
										var wxwap_id;
										var aliwap_id;
										var weixin_id;
										var alipay_id;
										for(var i=0;i<len;i++){											
											ss = ts[i].split("-");
											if(ss[1]=="wxwap" || ss[1]=="WXWAP"){
												wxwap_id = ss[0]+"-wxwap";												
											}else if(ss[1]=="alipaywap" || ss[1]=="ALIPAYWAP"){
												aliwap_id = ss[0]+"-alipaywap";;
											}else if(ss[1]=="WEIXIN" || ss[1]=="weixin"){
												weixin_id = ss[0]+"-weixin";;
											}else if(ss[1]=="alipay" || ss[1]=="ALIPAY"){
												alipay_id = ss[0]+"-alipay";
											}else{
												wxwap_id="";
												aliwap_id="";
												weixin_id="";
												alipay_id="";
											}
										}																			
										$("input[name='paygete_id1'][value='"+wxwap_id+"']").attr("checked",true); 
									</script>
									<c:forEach items="${gatewaylist}" var="gateways">
										<c:if test='${gateways.wxwap=="y"}'><label><input type="radio" name="paygete_id1" value="${gateways.gateway_id}-wxwap" /><c:out value="${gateways.gateway_name }微信H5"></c:out></label></c:if>
									</c:forEach>
									<label><input type="radio" name="paygete_id1" value="0-wxwap" /> 关闭通道
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit"">修改</button>
								</div>
								</div>
							</dl>
						</fieldset>
						</form>
						<form action="<%=adminbasePath %>/person/upGateway2" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>支付宝H5支付</legend>
							<dl class="nowrap">
								<dt>可选通道列表：</dt>
								<dd>
									<script type="text/javascript">
										$("input[name='paygete_id2'][value='"+aliwap_id+"']").attr("checked",true);  
									</script>
									<c:forEach items="${gatewaylist}" var="gateways">
										<c:if test='${gateways.alipaywap=="y"}'><label><input type="radio" name="paygete_id2" value="${gateways.gateway_id}-alipaywap" /><c:out value="${gateways.gateway_name }支付宝H5"></c:out></label></c:if>
									</c:forEach>
									<label><input type="radio" name="paygete_id2" value="0-alipaywap" /> 关闭通道
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit"">修改</button>
								</div>
								</div>
							</dl>
						</fieldset>
						</form>
						<form action="<%=adminbasePath %>/person/upGateway3" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>微信扫码支付</legend>
							<dl class="nowrap">
								<dt>可选通道列表：</dt>
								<dd>
									<script type="text/javascript">
										$("input[name='paygete_id3'][value='"+weixin_id+"']").attr("checked",true); 
									</script>
									<c:forEach items="${gatewaylist}" var="gateways">
										<c:if test='${gateways.weixin=="y"}'><label><input type="radio" name="paygete_id3" value="${gateways.gateway_id}-weixin" /><c:out value="${gateways.gateway_name }微信扫码"></c:out></label></c:if>
									</c:forEach>
									<label><input type="radio" name="paygete_id3" value="0-weixin" /> 关闭通道
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit"">修改</button>
								</div>
								</div>
							</dl>
						</fieldset>
						</form>
						<form action="<%=adminbasePath %>/person/upGateway4" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
						<input type="hidden" name="id" value="${person.id}">
						<fieldset>
							<legend>支付宝扫码支付</legend>
							<dl class="nowrap">
								<dt>可选通道列表：</dt>
								<dd>
									<script type="text/javascript">
										$("input[name='paygete_id4'][value='"+alipay_id+"']").attr("checked",true); 
									</script>
									<c:forEach items="${gatewaylist}" var="gateways">
										<c:if test='${gateways.alipay=="y"}'><label><input type="radio" name="paygete_id4" value="${gateways.gateway_id}-alipay" /><c:out value="${gateways.gateway_name }支付宝扫码"></c:out></label></c:if>
									</c:forEach>
									<label><input type="radio" name="paygete_id4" value="0-alipay" /> 关闭通道
								</dd>
							</dl>
							<dl class="nowrap">
								<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit"">修改</button>
								</div>
								</div>
							</dl>
						</fieldset>
						</form>						
						
					</div>
				</div>
				
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					
					<!-- 代理相关信息 -->
					
					<div class="pageFormContent">
							<fieldset>
								<legend>下级用户</legend>
								
										
									<table class="table" width="1200" layoutH="500">
										<thead>
											<tr>
												<th>商户id</th>
												<!-- <th>邮箱</th> -->
												<th>商户类型</th>
												<th>企业名称</th>
												<th>可结算余额</th>
												<th>微信通道</th>
												<th>微信费率(%)</th>
												<th>支付宝通道</th>
												<th>支付宝费率(%)</th>
												<th>提现手续费(笔)</th>
												<th>管理员工</th>
												<th>注册时间</th>
												<th>登陆状态</th>
												<th>锁定状态</th>
												<th>网银状态</th>
												<th>上级代理Id</th>
												<th>代理层级</th>
												<th>代理用户</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${childList}" var="per">
												<tr onClick="">
													<td><c:out value="${per.id}"></c:out></td>
													<%-- <td><c:out value="${per.email}"></c:out></td> --%>
													<td>
													<c:choose>
														<c:when test="${per.huge==1}">
															企业
														</c:when>
														<c:otherwise>
															个人
														</c:otherwise>
													</c:choose></td>
													<td>
														<c:choose>
															<c:when test="${per.name==''}">
																未选择
															</c:when>
															<c:otherwise>
																<c:out value="${per.name}"></c:out>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:out value="${per.settlement}"></c:out>
													</td>
													<td>
														<c:out value="${per.wxwap}"></c:out>
													</td>
													<td>
														<c:out value="${(1-per.rate.cibsm)*100}"></c:out>
													</td>
													<td>
														<c:out value="${per.alipaywap}"></c:out>
													</td>
													<td>
														<c:out value="${(1-per.rate.mustali)*100}"></c:out>
													</td>
													<td>
														<c:out value="${per.rate.refund}"></c:out>
													</td>
													<td><c:out value="${per.empname}"></c:out></td>
													<td><c:out value="${per.time}"></c:out></td>
													<td>
													<c:choose>
														<c:when test="${per.iflogin==1}">
															登陆
														</c:when>
														<c:otherwise>
															未登录
														</c:otherwise>
													</c:choose></td>
													<td>
													<c:choose>
														<c:when test="${per.iflock==1}">
															正常
														</c:when>
														<c:otherwise>
															锁定
														</c:otherwise>
													</c:choose></td>
													<td>
													<c:choose>
														<c:when test="${per.ifnet==1}">
															开通
														</c:when>
														<c:otherwise>
															未开通
														</c:otherwise>
													</c:choose></td>
													
													<td><c:out value="${per.superior}"></c:out></td>
													
													<td><c:out value="${per.level}"></c:out></td>
													<td>
														<c:choose>
															<c:when test="${per.isAgent==1}">
																是
															</c:when>
															<c:otherwise>
																否
															</c:otherwise>
														</c:choose>
													
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								
							</fieldset>




					</div>
				</div>
				
				<c:if test="${person.iflock==0 }">
				<div layoutH="146" style="display: block; overflow: auto; width:100%; border: solid 1px #CCC; background: #fff">
					
					<!-- 代理相关信息 -->
					
					<div class="pageFormContent">
							<fieldset>
								<legend>身份证</legend>
									<div class="mar_20">
										<span class="in_block t_align_c">
						                    <img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.idcardfile1}" onclick="idcardFile1.click();" class="wid_280"/><br/>
						               	 	正面
								          
								        </span>
								        <span class="in_block t_align_c">
								             	
						                    <img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.idcardfile2}" onclick="idcardFile1.click();" class="wid_280" /><br/>
						                	背面
								           
								    	</span>	
									</div>
									
							</fieldset>
							<div class="hei_40"></div>
							<fieldset>
								<legend>营业执照</legend>
								<img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.licenceFile}" />
								
							</fieldset>
							
							<div class="hei_40"></div>
							<fieldset>
								<legend>企业开户许可证</legend>
								<img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.openAccount}" />
								
							</fieldset>
							
							<div class="hei_40"></div>
							<fieldset>
								<legend>网络文化经营许可证</legend>
								<img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.webCulture}" />
								
							</fieldset>
							
							<div class="hei_40"></div>
							<fieldset>
								<legend>域名证书</legend>
								<img src="<%=basePath%>/upimg/check/${imgfile.phone}/${imgfile.domainName}" />
								
							</fieldset>

					</div>
				</div>
				</c:if>
				
				<%
					//}
				%>
			</div>
			<script>
				wxwap_id="";
				aliwap_id="";
				weixin_id="";
				alipay_id="";
			</script>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</body>
</html>