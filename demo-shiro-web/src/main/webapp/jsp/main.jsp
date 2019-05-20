<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>主页面</title>
	</head>
	<body>
                主页面 
      <br />
      <shiro:hasRole name="admin">
                    有 admin 角色的用户可以看到
       <shiro:principal></shiro:principal>
      </shiro:hasRole>
      <br />
      <shiro:hasPermission name="student:create">
                    有 student:create权限的用户可以看到
      </shiro:hasPermission>
	</body>
</html>