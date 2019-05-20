<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登录</title>
	</head>
	<body>
		<div>
            <font color="red">${errorMsg }</font>
		</div>
		<form action="${pageContext.request.contextPath }/user/login.do" method="post">
			账号: <input type="text" name="username" value="admin"/><br/>
			密码: <input type="password" name="password" value="admin"><br/>
			<input type="submit" value="login"/>
		</form>
	</body>
</html>