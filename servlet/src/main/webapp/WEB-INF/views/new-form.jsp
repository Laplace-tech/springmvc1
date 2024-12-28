<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Title</title>
</head>
<!-- 상대경로 사용, [현재 URL이 속한 계층 경로 + /save] -->
<!-- send message to : resource servlet-mvc/members/save -->
<!-- send message to : resource frontcontroller/v1/members/save -->
<body>
	<form action="save" method="post">
		username : <input type = "text" name="username" />
		age: <input type="text" name="age" />
		<button type="submit">전송</button>
	</form>
</body>
</html>