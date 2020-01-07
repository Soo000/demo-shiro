<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
	   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>主页面</title>
	</head>
	<body>
		当前用户【<shiro:principal />】 &nbsp;&nbsp;&nbsp;<a href="/logout">登出</a>
	
		<shiro:hasRole name="admin">
            <a href="/user">用户管理</a>
		</shiro:hasRole>
	</body>
</html>