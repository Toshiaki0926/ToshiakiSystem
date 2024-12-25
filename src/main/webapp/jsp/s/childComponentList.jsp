<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.Component"%>

<!DOCTYPE html>
<html>
<head>
<title>子部品一覧</title>
<link rel="stylesheet" type="text/css" href="./css/childComponentList.css">
</head>
<body>
	<div class="header">
		<h1>子部品一覧</h1>
	</div>
	<br>
	<div class="button-container">
		<a href="javascript:history.back();">戻る</a>
	</div>
	<br>

	<%
	List<Component> components = (List<Component>) request.getAttribute("Components");
	%>
	<%
	if (components != null) {
		for (Component c : components) {
	%>
	<div class="problem-container">
		<label><%=c.getComponent_description()%></label>
		<!-- コード表示ボタンを配置 -->
		<form action="ViewHintPageServlet" method="get"
			style="display: inline;">
			<input type="hidden" name="component_id"
				value="<%=c.getComponent_id()%>"> <input type="submit"
				value="ヒントを見る">
		</form>
		<form action="ComponentListPageServlet" method="get"
			style="display: inline;">
			<input type="submit" value="詳細を見る">
		</form>
	</div>
	<br>
	<%
	}
	} else {
	%>
	<p>登録された部品がありません。</p>
	<%
	}
	%>
</body>
</html>