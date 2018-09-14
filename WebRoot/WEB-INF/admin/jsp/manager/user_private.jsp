
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageContent">
		<form method="post"
			action="<%=adminbasePath%>/usermanager/upEmpPermNav"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<div class="pageFormContent" layoutH="58">
				<input type="hidden" name="employeeid" value="${employeeid }" />
				<ul class="tree treeFolder treeCheck expand">
					<c:forEach items="${navlist}" varStatus="l" var="nav">
						<c:if test="${nav.node==1}">
							<li><c:if test="${empty nav.checked}">
									<a tname="empjson" tvalue="${nav.navid }"><c:out
											value="${nav.name}"></c:out></a>
								</c:if>
								<c:if test="${!empty nav.checked}">
									<a tname="empjson" tvalue="${nav.navid }" checked="true"><c:out
											value="${nav.name}"></c:out></a>
								</c:if>
								<ul>
									<c:forEach items="${navlist}" var="navl">
										<c:if test="${nav.node==1}">
											<c:if test="${nav.navid==navl.ownership}">
												<li><c:if test="${empty navl.checked}">
														<a tname="empjson" tvalue="${navl.navid }"><c:out
																value="${navl.name}"></c:out></a>
													</c:if>
													<c:if test="${!empty navl.checked}">
														<a tname="empjson" tvalue="${navl.navid }" checked="true"
															class="parent"><c:out value="${navl.name}"></c:out></a>
													</c:if>
													<ul>
														<c:forEach items="${navlist}" var="navls">
															<c:if test="${navl.navid==navls.ownership}">
																<li><c:if test="${empty navls.checked}">
																		<a tname="empjson" tvalue="${navls.navid }"><c:out
																				value="${navls.name}"></c:out></a>
																	</c:if> <c:if test="${!empty navls.checked}">
																		<a tname="empjson" tvalue="${navls.navid }"
																			checked="true" class="parent"><c:out
																				value="${navls.name}"></c:out></a>
																	</c:if></li>
															</c:if>
														</c:forEach>
													</ul></li>
											</c:if>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:if>
					</c:forEach>
				</ul>
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
