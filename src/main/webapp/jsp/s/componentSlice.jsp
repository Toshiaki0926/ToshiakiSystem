<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.SliceCodeList"%>
<!DOCTYPE html>
<html>
<head>
<title>部品のコード分割</title>
<link rel="stylesheet" type="text/css" href="./css/componentSlice.css">
</head>
<body>
	<div class="header">
		<h1>部品のコード分割</h1>
	</div>
	<br>
	<div class="button-container">
		<a href="javascript:history.back();">戻る</a>
		<a href="./ReturnComponentListPageServlet">部品リストへ戻る</a>
		<a href="./ReturnComponentListPageServlet">コードを見る</a>
	</div>

	<%
	// Servletから受け取る属性
	List<SliceCodeList> sliceComponent = (List<SliceCodeList>) request.getAttribute("SliceComponent");
	int totalLines = (Integer) request.getAttribute("TotalLines");

	if (sliceComponent != null && !sliceComponent.isEmpty()) {
		// list_idごとにデータをグループ化するマップを作成
		Map<Integer, List<SliceCodeList>> groupedComponents = new HashMap<>();
		for (SliceCodeList scl : sliceComponent) {
			groupedComponents.computeIfAbsent(scl.getList_id(), k -> new ArrayList<>()).add(scl);
		}

		// list_idごとにテーブルを表示
		for (Map.Entry<Integer, List<SliceCodeList>> entry : groupedComponents.entrySet()) {
			int listId = entry.getKey();
			List<SliceCodeList> lines = entry.getValue();
	%>
	<table border="1">
		<thead>
			<tr>
				<th>行</th>
				<th>説明</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (SliceCodeList line : lines) {
			%>
			<tr>
				<td><%=line.getLine_number()%> / <%=totalLines%></td>
				<td><%=line.getDescription()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<br>
	<%
	}
	} else {
	%>
	<p>表示するデータがありません。</p>
	<%
	}
	%>
</body>
</html>
