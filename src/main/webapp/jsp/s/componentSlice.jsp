<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.CodeLine"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部品の行一覧</title>
<link rel="stylesheet" type="text/css" href="./css/componentSlice.css">
</head>
<body>
	<div class="header">
		<h1>部品の行一覧</h1>
	</div>
	<br>
	<br>

	<% List<CodeLine> cList = (List<CodeLine>)request.getAttribute("SliceComponent"); %>

	<%
	if (cList != null && !cList.isEmpty()) {
	%>
	<table>
		<thead>
			<tr>
				<th>コード</th>
				<th>説明</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (CodeLine c : cList) {
				%>
			<tr>
				<td><%=c.getCode()%></td>
				<td><%=c.getDescription()%></td>
			</tr>
			<%
				}
				%>
		</tbody>
	</table>
	<%
	} else {
	%>
	<p>登録されたコードがありません。</p>
	<%
	}
	%>
</body>
</html>