<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.Component"%>

<!DOCTYPE html>
<html>
<head>
<title>Component list page</title>
</head>
<body>
	<label>部品一覧</label>
	<br>
	<br>

	<% 
	List<Component> components = (List<Component>)request.getAttribute("Components");
	%>
	<% 
        if (components != null) { 
            for (Component c : components) { 
    %>
	<div>
		<label><%= c.getComponent_description() %></label>
		<!-- コード表示ボタンを配置 -->
		<form action="ComponentHintPageServlet" method="get" style="display: inline;">
			<input type="hidden" name="component_id" value="<%= c.getComponent_id() %>">
			<input type="submit" value="ヒントを見る">
		</form>
		<form action="ComponentListsPageServlet" method="get" style="display: inline;">
			<input type="submit" value="詳細を見る">
		</form>
		<form action="ComponentListsPageServlet" method="get" style="display: inline;">
			<input type="submit" value="1行ずつ見る">
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