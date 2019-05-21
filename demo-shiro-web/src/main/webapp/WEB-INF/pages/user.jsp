<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
	</head>
	<body>
           用户管理 
         <shiro:hasPermission name="user:add">
             <button>添加用户</button>
         </shiro:hasPermission>
         
         <shiro:hasPermission name="user:update">
             <button>修改用户</button>
         </shiro:hasPermission>
         
         <shiro:hasPermission name="user:del">
             <button>删除用户</button>
         </shiro:hasPermission>
	</body>
</html>