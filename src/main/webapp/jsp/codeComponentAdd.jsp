<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.Component"%>
<!-- Component クラスをインポート -->

<!DOCTYPE html>
<html>
<head>
<title>コード部品設定</title>
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

pre {
	margin: 0;
	white-space: pre-wrap; /* 改行を維持して折り返し */
	word-wrap: break-word; /* 長い文字列を折り返し */
}
</style>
</head>
<body>
	<h1>コード部品設定</h1>

	<form action="CodeComponentAddServlet" method="POST">
		<table>
			<thead>
				<tr>
					<th>コード全体</th>
					<th>部品選択</th>
				</tr>
			</thead>
			<tbody>
				<%
				// "source_id" は現在のソースコードid
				int sourceId = (int) request.getAttribute("source_id");
				// "Codes" は整形済みのコード全体
				String codes = (String) request.getAttribute("Codes");
				// "components" は部品の説明を含むリスト
				List<Component> components = (List<Component>) request.getAttribute("Components");

				if (codes != null && components != null) {
				%>
				<tr>
					<td><pre><%=codes%></pre> <!-- コード全体を表示 --></td>
					<td><select name="component">
							<%
							// 部品リストをループして表示
							for (Component component : components) {
							%>
							<option value="<%=component.getComponent_id()%>"><%=component.getComponent_description()%></option>
							<%
							}
							%>
					</select></td>
				</tr>
				<input type="hidden" name="codes" value="<%=codes%>">
				<!-- codes を送信 -->
				<%
				} else {
				%>
				<tr>
					<td colspan="2">表示するコードがありません。</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<br> <input type="hidden" name="source_id"
			value="<%=sourceId%>"><input type="submit"
			value="部品設定を保存">
	</form>
</body>
</html>
