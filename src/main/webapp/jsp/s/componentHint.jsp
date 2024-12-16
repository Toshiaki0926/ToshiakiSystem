<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		ヒント:
	</h1>
	<pre>
        <%=request.getAttribute("HintCode")%>
    </pre>
	<a href="./JavaListServlet">戻る</a>
</body>
</html>