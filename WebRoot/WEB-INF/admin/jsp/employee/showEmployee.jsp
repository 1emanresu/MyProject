<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$("#iflock option[value='${iflock}']").attr("selected", true);
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=adminbasePath%>/employee/showEmployee" onsubmit="return divSearch(this, 'employeebox');">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="starttime" value="${starttime}" />
		<input type="hidden" name="endtime" value="${endtime }" />
		<input type="hidden" name="iflock" value="${iflock}" />
		<input type="hidden" name="employeeid" value="${employeeid }"/>
		<input type="hidden" name="powerid" value=""/>
	</form>

	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/employee/showEmployee"
			onsubmit="return divSearch(this, 'employeebox');" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="20" />
			<input type="hidden" name="powerid" value=""/>
			<div class="searchBar">
				<ul class="searchContent">
					<li>
						<label>员工编号：</label>
						<input type="text" name="employeeid" value="${employeeid }"/>
					</li>
					<li style="width: 400px"><label>日期：</label><input value="${starttime }" type="text" name="starttime" class="date" dateFmt="yyyy-MM-dd"/>
					--<input value="${endtime }" type="text" name="endtime" class="date" dateFmt="yyyy-MM-dd"/>
					</li>
					<li style="width: 160px"><label>锁定状态：</label>
						<select class="combox" name="iflock" id="iflock" style="width: 100px">
							<option value="0">请选择</option>
							<option value="1">正常</option>
							<option value="2">锁定</option>
						</select>
					</li>
				</ul>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add"
					href="<%=adminbasePath%>/employee/showAddEmp" rel="empadd"
					target="dialog" width="800" height="440" mask=true><span>添加</span></a></li>
				<li class="line">line</li>
				<li><a title="确实要删除这些记录吗?" target="ajaxTodo"
					href="<%=adminbasePath%>/employee/delEmployee?empid={empid}" class="delete"><span>删除</span></a></li>
				<li class="line">line</li>
				<li><a class="edit"
					href="<%=adminbasePath%>/employee/showEmpPass?empid={empid}" rel="empeditpass"
					target="dialog" width="800" height="200" mask=true><span>修改密码</span></a></li>
			</ul>
		</div>
		<table class="table" width="100%" layoutH="185" rel="employeebox">
			<thead>
				<tr>
					<th>员工编号</th>
					<th>账号</th>
					<th>部门</th>
					<th>手机号</th>
					<th>地址</th>
					<th>创建时间</th>
					<th>邮箱</th>
					<th>名称</th>
					<th>提成</th>
					<th>已经提成</th>
					<th>锁定状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${emplist}" var="emp">
					<tr target="empid" rel="${emp.employeeid }">
						<td><c:out value="${emp.employeeid}"></c:out></td>
						<td><c:out value="${emp.account}"></c:out></td>
						<td><c:out value="${emp.powername}"></c:out></td>
						<td><c:out value="${emp.phone}"></c:out></td>
						<td><c:out value="${emp.address}"></c:out></td>
						<td><c:out value="${emp.creationtime}"></c:out></td>
						<td><c:out value="${emp.email}"></c:out></td>
						<td><c:out value="${emp.name}"></c:out></td>
						<td><c:out value="${emp.commission}"></c:out></td>
						<td><c:out value="${emp.haveroyalty}"></c:out></td>
						<td>
						<c:choose>
							<c:when test="${emp.iflock==1}">
								正常
							</c:when>
							<c:otherwise>
								锁定
							</c:otherwise>
						</c:choose></td>
						<td>
						 	<a class="btnEdit" title="编辑" href="<%=adminbasePath%>/employee/showEmp?empid=${emp.employeeid}" width="800" height="440" mask=true target="dialog" rel="emp${emp.employeeid}"><span>编辑</span></a>
							<a class="btnView" title="查看客户" href="<%=adminbasePath%>/employee/showPerEmp?empid=${emp.employeeid}&pageNum=1&numPerPage=20&pername=" target="dialog" width="900" height="440" mask=true rel="emp${emp.employeeid}"><span>查看客户</span></a> 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({pageNum:this.value},{numPerPage:this.value},'employeebox')">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共${totalCount}条</span>
			</div>

			<div class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>