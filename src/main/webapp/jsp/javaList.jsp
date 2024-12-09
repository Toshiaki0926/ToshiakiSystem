<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.Source_file"%>

<!DOCTYPE html>
<html>
<head>
<title>Code list page</title>
</head>
<body>
	<label>問題一覧</label>
	<br>
	<br>

	<% List<Source_file> sList = (List<Source_file>)request.getAttribute("SourceList"); %>

	<% 
        if (sList != null) { 
            for (Source_file s : sList) { 
    %>
	<div>
		<label><%= s.getSource_Name() %></label>
		<!-- コード表示ボタンを配置 -->
		<form action="ComponentListPageServlet" method="get" style="display: inline;">
			<input type="hidden" name="source_id" value="<%= s.getSource_Id() %>">
			<input type="submit" value="部品を表示">
		</form>
	</div>
	<br>
	<% 
            }
        } else {
    %>
	<p>登録されたコードがありません。</p>
	<% 
        } 
    %>
</body>
</html>