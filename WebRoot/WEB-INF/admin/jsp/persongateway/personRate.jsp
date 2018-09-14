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

	<form id="pagerForm" method="post" action="<%=adminbasePath%>/gateway/personRate">
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden"name="pageNum" value="1" />
		<input type="hidden"name="numPerPage" value="${numPerPage}" />
	</form>
	
	<div class="pageHeader">
		<form rel="pagerForm" action="<%=adminbasePath%>/gateway/personRate"
			onsubmit="return navTabSearch(this)" method="post">
			<input type="hidden" name="pageNum" value="1" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
			<div class="searchBar">
				<ul class="searchContent">
				    <li style="width: 210px"><label style="width: 40px">用户id:</label>
					<input type="text" value="${id}" name="id"/></li>	
					
					
				</ul>
				<div class="subBar">
					<ul class=" wid_b_100 t_align_c">
						<li class="in_block"  style="float:none">
							<div class="buttonActive">
								<div class="buttonContent " >
									<button type="submit">检索</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div> 
	
	<div class="pageContent">
		
		<table class="table"  width="1200" layoutH="110">
			<thead>
				<tr>
					
					<th width="100px">用户编号</th>
					<th width="100px">qq钱包费率</th>
					
					
					<th width="100px">微信费率</th>
					<th width="100px">支付宝费率</th>
					<th width="130px">网银支付费率</th>
					<th width="100px">提现手续费</th>
					
					<th width="80px">修改费率</th>
				</tr>
			</thead>
	
			<tbody>
				<c:forEach items="${getView1}" var="view1">

					<tr>
						
						<td align="middle"><c:out value="${view1.id}"></c:out></td>
						<td align="middle"><c:out value="${view1.qqwx} %"></c:out></td>
						
						<td align="middle"><c:out value="${view1.cibsm} %"></c:out></td>
						<td align="middle"><c:out value="${view1.mustali} %"></c:out></td>
						<td align="middle">
							<c:if test="${!empty view1.banking}">
								${view1.banking } % -${(1-view1.banking)*100 } %
							</c:if>
							<c:if test="${empty view1.banking}">
								无
							</c:if>
						</td>
						<td align="middle"><c:out value="${view1.refund}"></c:out></td>
						
						<td align="middle">
							<!-- <a class="btnEdit" title="修改费率" href="<%=adminbasePath%>/person/showPersonState?id=${per.id}" target="dialog" rel="perstate${per.id}" width="410" height="200"><span>编辑</span></a> -->
							<a class="btnEdit" style="margin-left:23px;" title="修改费率" href="<%=adminbasePath%>/person/editPersonRate?id=${view1.id}" target="dialog" rel="" width="450" height="380"><span>编辑</span></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="10" <c:if test="${numPerPage==10 }">selected</c:if>>10</option>
					<option value="20" <c:if test="${numPerPage==20 }">selected</c:if>>20</option>
					<option value="50" <c:if test="${numPerPage==50 }">selected</c:if>>50</option>
					<option value="100" <c:if test="${numPerPage==100 }">selected</c:if>>100</option>
					<option value="200" <c:if test="${numPerPage==200 }">selected</c:if>>200</option>
				</select> <span>条，共${totalCount}条</span>	
						
			</div>

			<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}"   currentPage="${pageNum}"></div>
		</div>

	</div>


</body>
</html>