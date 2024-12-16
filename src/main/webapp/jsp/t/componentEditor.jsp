<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.CodeLine"%>

<!DOCTYPE html>
<html>
<head>
<title>コード行一覧ページ</title>
<link rel="stylesheet" type="text/css" href="./css/componentEditor.css">
</head>
<body>
	<div class="header">
		<h1>コード行一覧</h1>
	</div>
	<br>
	<div class="button-container">
		<a href="./AdminServlet">戻る</a>
	</div>
	<br>

	<%
	List<CodeLine> cList = (List<CodeLine>) request.getAttribute("CodeList");
	%>

	<%
	if (cList != null && !cList.isEmpty()) {
	%>
	<form action="ComponentSetPageServlet" method="get">
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
				for (CodeLine c : cList) {
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
			<input type="submit" value="部品設定">
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
