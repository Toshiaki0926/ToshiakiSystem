<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.Component"%>
<%@ page import="main.EscapeHtml"%>
<!-- Component クラスをインポート -->

<!DOCTYPE html>
<html>
<head>
<title>コード部品設定</title>
<link rel="stylesheet" type="text/css" href="./css/setComponent.css">
</head>
<body>
	<div class="header">
		<h1>コード部品設定</h1>
	</div>

	<form action="SetComponentServlet" method="POST" accept-charset="UTF-8">
		<table>
			<thead>
				<tr>
					<th>コード全体</th>
					<th>部品選択</th>
				</tr>
			</thead>
			<tbody>
				<%
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
				<input type="hidden" name="codes"
					value="<%=EscapeHtml.escapeHtml(codes)%>">
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
		<div class="button-center">
			<input type="submit" value="部品設定を保存">
		</div>
	</form>
</body>
</html>
