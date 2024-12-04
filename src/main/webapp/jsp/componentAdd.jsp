<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Component Add page</title>
</head>
<body>
	<h1>部品登録</h1>
	<form action="ComponentAddServlet" method="post"
		enctype="multipart/form-data">
		<label>部品の説明:</label> <input type="text" name="componentDescription"
			required /><br>
		<button type="submit">登録</button>
	</form>
	<br>
	<br>
	<a href="./AdminServlet">戻る</a>
</body>
</html>