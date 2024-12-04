<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Add page</title>
</head>
<body>
	<h1>Javaファイル登録</h1>
	<form action="JavaAddServlet" method="post"
		enctype="multipart/form-data">
		<label>ファイル選択:</label> <input type="file" name="javaFile" required /><br>
		<label>ファイル名:</label> <input type="text" name="fileName" required /><br>
		<button type="submit">登録</button>
	</form>
	<br>
	<br>
	<a href="./AdminServlet">戻る</a>
</body>
</html>