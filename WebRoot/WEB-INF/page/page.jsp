<%@page import="com.pay.yeepay.server.Configuration"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String adminurl = Configuration.getInstance().getValue("adminurl");
	String adminbasePath = basePath+adminurl;
%>