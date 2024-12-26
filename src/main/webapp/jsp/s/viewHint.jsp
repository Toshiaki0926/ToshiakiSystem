<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ヒント表示ページ</title>
<link rel="stylesheet" type="text/css" href="./css/viewHint.css">
</head>
<body>
	<div class="header">
		<h1>ヒント</h1>
	</div>

	<div class="container">
		<div class="form-group">
			<%
			String codeHint = (String) request.getAttribute("CodeHint");
			if (codeHint == null) {
				%>
			<p>設定された部品がありません。</p>
			<%
			} else {
				%>
			<pre><%=codeHint%></pre>
			<%
			}
			%>
		</div>
	</div>

	<div class="buttons">
		<a href="./ReturnComponentListPageServlet" class="styled-button">戻る</a>
		<a href="./ComponentSlicePageServlet" class="styled-button">一行ずつ見る</a>
	</div>
</body>
</html>
