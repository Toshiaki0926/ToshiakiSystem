<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.Source_file"%>

<!DOCTYPE html>
<html>
<head>
<title>問題一覧</title>
<link rel="stylesheet" type="text/css" href="./css/javaList.css">
</head>
<body>
	<div class="header">
		<h1>問題一覧</h1>
	</div>
	<br>

	<%
	List<Source_file> sList = (List<Source_file>) request.getAttribute("SourceList");
	%>

	<%
	if (sList != null) {
		for (Source_file s : sList) {
	%>
	<div class="problem-container">
		<label><%=s.getSource_Name()%></label>
		<form action="ComponentListPageServlet" method="get">
			<input type="hidden" name="source_id" value="<%=s.getSource_Id()%>">
			<input type="submit" value="部品を表示">
		</form>
	</div>
	<%
	}
	} else {
	%>
	<p style="text-align: center;">登録されたコードがありません。</p>
	<%
	}
	%>
</body>
</html>