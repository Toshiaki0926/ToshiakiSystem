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
<title>コード行一覧ページ</title>
<link rel="stylesheet" type="text/css" href="./css/selectCodeLine.css">
</head>
<body>
	<div class="header">
		<h1>コード行一覧</h1>
	</div>
	<br>
	<div class="button-container">
		<a href="./AdminServlet">戻る</a>
		<a href="./DeleteJavaServlet">このソースコードを削除</a>
	</div>
	<br>

	<%
	List<CodeLine> codeList = (List<CodeLine>) request.getAttribute("CodeList");
	List<ComponentList> comList = (List<ComponentList>) request.getAttribute("ComponentList");
	List<Component> components = (List<Component>) request.getAttribute("Components");
	%>

	<%
	if (codeList != null && !codeList.isEmpty()) {
	%>
	<form action="SetComponentPageServlet" method="get">
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
				for (CodeLine c : codeList) {
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

	<label>現在登録中の部品</label>

	<%
	if (comList != null && !comList.isEmpty()) {
	%>
	<form action="SelectChildCodeLinePageServlet" method="get">
		<!-- フォームを追加 -->
		<table>
			<thead>
				<tr>
					<th>親部品</th>
					<th>該当コード</th>
					<th>部品名</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<%
				String description = null;
				for (ComponentList c : comList) {
					for (Component component : components) {
						if (component.getComponent_id() == c.getComponent_id()) {
							description = component.getComponent_description();
						}
					}
				%>
				<tr>
					<td>
						<!-- 親部品がnullかチェック、nullなら「null」と表示 --> <%
						if (c.getParent_id() != null) {
						%> <%=c.getParent_id()%> <%
						} else {
						%> <%= "なし" %> <%
						}
						%>
					</td>
					<td><%=c.getComponent_code()%></td>
					<td><%=description%></td>
					<td><button type="submit" name="selectedListId"
						value="<%=c.getList_id()%>"> 子部品登録</button>
						<input type="hidden" name="selectedComponentId" 
						value="<%=c.getComponent_id()%>">
						<input type="hidden" name="selectedComponentCode" 
						value="<%=c.getComponent_code()%>">
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
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
