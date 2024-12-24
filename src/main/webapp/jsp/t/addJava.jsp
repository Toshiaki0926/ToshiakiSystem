<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>問題登録ページ</title>
<link rel="stylesheet" type="text/css" href="./css/addJava.css">
</head>
<body>
	<div class="container">
		<h1>問題登録</h1>
		<form action="AddJavaServlet" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label>ファイル選択:</label> <input type="file" name="javaFile" required />
			</div>
			<div class="form-group">
				<label>問題名:</label> <input type="text" name="fileName" required />
			</div>
			<a href="./AdminServlet" class="styled-button">戻る</a>
			<button type="submit" class="styled-button">登録</button>
		</form>
	</div>
</body>
</html>
