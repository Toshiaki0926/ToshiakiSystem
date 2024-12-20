<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.CodeLine"%>
<%@ page import="beans.ComponentList"%>
<%@ page import="beans.Component"%>

<!DOCTYPE html>
<html>
<head>
<title>子部品選択ページ</title>
<link rel="stylesheet" type="text/css" href="./css/selectCodeLine.css">
</head>
<body>
	<div class="header">
		<h1>部品のコード</h1>
	</div>
	<br>
	<div class="button-container">
		<a href="./ReturnCodeLinePageServlet">戻る</a>
	</div>
	<br>

	<%
	List<CodeLine> comLines = (List<CodeLine>) request.getAttribute("ComponentLines");
	%>

	<%
	if (comLines != null && !comLines.isEmpty()) {
	%>
	<form action="SetChildComponentPageServlet" method="get">
		<!-- フォームを追加 -->
		<table>
			<thead>
				<tr>
					<th>行番号</th>
					<th>選択</th>
					<th>コード</th>
					<th>説明</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (CodeLine c : comLines) {
				%>
				<tr>
					<td><%=c.getLineNumber()%></td>
					<td><input type="checkbox" name="selectedCodes"
						value="<%=c.getLineId()%>"></td>
					<td><%=c.getCode()%></td>
					<td><%=c.getDescription()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<div class="button-center">
			<input type="submit" value="子部品設定">
		</div>
	</form>
	<%
	} else {
	%>
	<p>登録されたコードがありません。</p>
	<%
	}
	%>
</body>
</html>
