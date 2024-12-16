<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部品登録</title>
<link rel="stylesheet" href="./css/componentAdd.css">
</head>
<body>
	<div class="container">
		<h1>部品登録</h1>
		<form action="ComponentAddServlet" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label>部品の説明:</label> <input type="text" name="componentDescription"
					required />
			</div>
			<a href="./AdminServlet" class="styled-button">戻る</a>
			<button type="submit" class="styled-button">登録</button>
		</form>
	</div>
</body>
</html>
