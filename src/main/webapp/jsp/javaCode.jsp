<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Code hint page</title>
</head>
<body>
	<h1>
		コード表示:
		<%=request.getAttribute("fileName")%></h1>
	<pre>
        <%=request.getAttribute("modifiedCode")%>
    </pre>
	<a href="./JavaListServlet">戻る</a>
</body>
</html>