<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
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
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>员工管理</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>
					<div layoutH="50"
						style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
						<ul class="tree treeFolder">
							<li><a href="<%=adminbasePath %>/employee/showEmployee?pageNum=1&numPerPage=20&starttime=&endtime=&id=&iflock=0&employeeid=&powerid=" target="ajax" rel="jbsxBox">员工部门</a>
								<ul>
									<c:forEach items="${powerlist}" var="power">
										<c:if test="${power.powerid!=1}">
											<li><a href="<%=adminbasePath %>/employee/showEmployee?pageNum=1&numPerPage=20&starttime=&endtime=&id=&iflock=0&employeeid=&powerid=${power.powerid }" target="ajax" rel="employeebox"><c:out value="${power.name}"></c:out> </a></li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>

					<div id="employeebox" class="unitBox" style="margin-left: 246px;">
						<jsp:include page="employee/showEmployee.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</body>
</html>