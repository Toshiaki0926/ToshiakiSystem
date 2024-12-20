<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="beans.Component"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部品編集ページ</title>
<link rel="stylesheet" type="text/css" href="./css/editComponent.css">
</head>
<body>

	<%
	Component component = (Component) request.getAttribute("component");
	%>

	<div class="container">
		<h1>
			部品名：<%=component.getComponent_description()%></h1>
		<form action="UpdateComponentServlet" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label>更新する説明:</label> <input type="text"
					name="componentDescription" required />
			</div>
			<a href="./AdminServlet" class="styled-button">戻る</a>
			<button type="submit" class="styled-button">更新</button>
		</form>
		<br>
		<form action="DeleteComponentServlet" method="post"
			enctype="multipart/form-data">
			<button type="submit" class="styled-button">削除</button>
		</form>
	</div>
</body>
</html>