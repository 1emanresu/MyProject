<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=adminbasePath%>/power/upPowerNav"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="58">
				<input type="hidden" name="powerid" value="${powerid }"/>
				<ul id="jack-treefolder" class="tree treeFolder treeCheck expand">
					<c:forEach items="${navlist}" varStatus="l" var="nav">
						<c:if test="${nav.node==1}">
							<li>
								<c:if test="${empty nav.checked}">
								<a tname="empjson" tvalue="${nav.navid }"><c:out value="${nav.name}"></c:out></a>
								</c:if>
								<c:if test="${!empty nav.checked}">
								<a tname="empjson" tvalue="${nav.navid }" checked="true"><c:out value="${nav.name}"></c:out></a>
								</c:if>
								<ul>
									<c:forEach items="${navlist}" var="navl">
										<c:if test="${nav.navid==navl.ownership}">
											<li>
												<c:if test="${empty navl.checked}">
												<a tname="empjson" tvalue="${navl.navid }"><c:out value="${navl.name}"></c:out></a>
												</c:if>
												<c:if test="${!empty navl.checked}">
												<a tname="empjson" tvalue="${navl.navid }" checked="true"><c:out value="${navl.name}"></c:out></a>
												</c:if>
												<ul>
													<c:forEach items="${childNavid}" var="three">
														<c:if test="${navl.navid==three.ownership}">
															<li>
																<c:if test="${empty three.checked}">
																<a tname="empjson" tvalue="${three.navid }"><c:out value="${three.name}"></c:out></a>
																</c:if>
																<c:if test="${!empty three.checked}">
																<a tname="empjson" tvalue="${three.navid }" checked="true"><c:out value="${three.name}"></c:out></a>
																</c:if>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<script type="text/javascript">
				function submitData(obj){
					var oidStr = ""; //定义一个字符串用来装值的集合  
					//jquery循环t2下的所有选中的复选框  
					$("#jack-treefolder input:checked").each(function(i, a) {
						//alert(a.value);  
						oidStr += a.value + ','; //拼接字符串  
						var parentVal = this.parent().siblings(".indeterminate").html();
						alert(parentVal);
					});
					alert(oidStr);
					//("indeterminate")
					return false;
				}
			</script>
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