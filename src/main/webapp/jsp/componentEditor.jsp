<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.CodeLine"%>

<!DOCTYPE html>
<html>
<head>
<title>Code list page</title>
<style>
table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 8px;
	text-align: left;
	border: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
}

input[type="checkbox"] {
    transform: scale(2);  /* チェックボックスのサイズを1.5倍に */
    margin: 0;  /* マージン調整 */
}

input[type="submit"] {
    padding: 12px 20px;  /* 上下12px、左右20pxの余白 */
    font-size: 18px;  /* フォントサイズを18pxに */
    cursor: pointer;  /* ポインタカーソルに変更 */
}
</style>
</head>
<body>
	<label>コード行一覧</label>
	<br>
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
		<!-- 部品設定ボタン -->
		<br> <input type="submit"
			value="部品設定">
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
