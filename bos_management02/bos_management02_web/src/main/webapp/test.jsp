<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<shiro:authenticated>
		当前用户已经通过认证了
	</shiro:authenticated>
	
	<shiro:hasPermission name="courierAction_pageQuery">
		你拥有courierAction_pageQuery权限
	</shiro:hasPermission>
	
	<shiro:hasRole name="admin">
		你拥有admin权限
	</shiro:hasRole>
</body>
</html>