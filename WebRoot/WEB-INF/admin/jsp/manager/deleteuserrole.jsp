<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

jQuery(document).ready(function(){
    $(".deleteUserRole").click(function(){
    	var user = $(this).attr("id").split("submit_")[1];
    	var userRoleId = user.split(",")[0];
    	var employeeid = $(this).attr("id").split(",")[1];
    	jQuery.ajax({
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            url: '<%=adminbasePath%>/usermanager/delUserRolename/',
            data:{"roleid":userRoleId,"employeeid":employeeid},
            error: function() { 
    	 			alertMsg.error('删除角色关联失败！');
    			},
    		success: function() { 
    	    		// 删除已分配
    	    		$("#userRoleRow_" + userRoleId).remove();
    			}
        });	
    	
    });
    
});
</script>
<div class="pageContent" layoutH="0" >

	<fieldset>
		<legend>已分配角色</legend>
		<table class="list" width="100%">
			<thead>
				<tr>
				    <th>用户编号</th>
					<th width="150">角色名称</th>
					<th>删除角色</th>
				</tr>
			</thead>
			<tbody id="Rolename">
				<c:forEach var="role" items="${Rolename}">			
				<tr id="userRoleRow_${role.role_id}">
				    <td>${role.employeeid}</td>
					<td>${role.role_name}</td>
					<td>					
						<div class="button"><div class="buttonContent"><button id="submit_${role.role_id},${role.employeeid}" class="deleteUserRole">删除关系</button></div></div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</div>


