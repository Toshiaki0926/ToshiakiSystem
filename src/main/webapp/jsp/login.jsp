<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインページ</title>
<link rel="stylesheet" type="text/css" href="./css/login.css">
</head>
<body>
	<div class="container">
		<h1>ログインページ</h1>
		<form action="./LoginServlet" method="post">
			<div class="form-group">
				<label>ログインID:</label> <input type="text" name="user_id" required />
			</div>
			<div class="form-group">
				<label>パスワード:</label> <input type="password" name="password" required />
			</div>
			<button type="submit" class="styled-button">ログイン</button>
		</form>
	</div>
</body>

</html>