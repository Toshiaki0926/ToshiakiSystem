<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.Source_file"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
/* ボタンのスタイル */
input[type="submit"] {
    padding: 12px 20px;  /* ボタンの余白 */
    font-size: 18px;  /* フォントサイズ */
    cursor: pointer;  /* ポインタカーソル */
    background-color: #4CAF50;  /* 背景色 */
    color: black;  /* 文字色 */
    border: none;  /* 枠線なし */
    border-radius: 5px;  /* 角を丸く */
}

input[type="submit"]:hover {
    background-color: #45a049;  /* ホバー時の背景色 */
}

/* リンクのスタイル */
a {
    font-size: 18px;  /* フォントサイズ */
    color: #007BFF;  /* リンクの色 */
    text-decoration: none;  /* 下線なし */
}

a:hover {
    text-decoration: underline;  /* ホバー時に下線を表示 */
}

/* 文字のサイズを変更 */
label {
    font-size: 20px;  /* ラベルのフォントサイズ */
}

p {
    font-size: 16px;  /* パラグラフのフォントサイズ */
}
</style>
</head>
<body>
	<label>問題一覧</label>
	<br>
	<br>
	<a href="./JavaAddPageServlet">問題追加</a>
	<br>
	<br>
	<a href="./ComponentAddPageServlet">部品追加</a>
	<br>
	<br>

	<%
	List<Source_file> sList = (List<Source_file>) request.getAttribute("SourceList");
	%>

	<%
	if (sList != null) {
		for (Source_file s : sList) {
	%>
	<div>
		<label><%=s.getSource_Name()%></label>
		<!-- コード表示ボタンを配置 -->
		<form action="ComponentEditorPageServlet" method="get"
			style="display: inline;">
			<input type="hidden" name="source_id" value="<%=s.getSource_Id()%>">
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