<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="beans.Source_file"%>
<%@ page import="beans.Component"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理ページ</title>
<link rel="stylesheet" type="text/css" href="./css/admin.css">
</head>
<body>
	<div class="header">
		<h1>管理ページ</h1>
	</div>
	<br>
	<br>
	<div class="button-container">
		<a href="./AddComponentPageServlet">部品追加</a>
		<a href="./AddJavaPageServlet">問題追加</a>
	</div>
	
	<div class="content">
		<!-- 左側に部品一覧を表示 -->
		<div class="left-sidebar">
			<label class="section-title">部品一覧</label>
			<% 
				List<Component> cList = (List<Component>) request.getAttribute("ComponentList");
				if (cList != null) {
			%>
			<ul>
				<% 
					for (Component c : cList) {
				%>
				<li>
					<div class="component-item">
						<label class="section-element"><%= c.getComponent_description() %></label>
						<form action="EditComponentPageServlet" method="get" style="display: inline;">
							<input type="hidden" name="component_id" value="<%= c.getComponent_id() %>">
							<input type="submit" value="編集">
						</form>
					</div>
				</li>
				<% 
					}
				%>
			</ul>
			<% 
				} else {
			%>
			<p>部品は登録されていません。</p>
			<% 
				}
			%>
		</div>

		<!-- 右側にソースコード一覧を表示 -->
		<div class="right-sidebar">
			<label class="section-title">問題一覧</label>
			<% 
				List<Source_file> sList = (List<Source_file>) request.getAttribute("SourceList");
				if (sList != null) {
			%>
			<ul>
				<% 
					for (Source_file s : sList) {
				%>
				<li>
					<div class="component-item">
						<label class="section-element"><%= s.getSource_Name() %></label>
						<form action="SelectCodeLinePageServlet" method="get" style="display: inline;">
							<input type="hidden" name="source_id" value="<%= s.getSource_Id() %>">
							<input type="submit" value="コードに部品を設定">
						</form>
					</div>
				</li>
				<% 
					}
				%>
			</ul>
			<% 
				} else {
			%>
			<p>登録されたコードがありません。</p>
			<% 
				}
			%>
		</div>
	</div>
</body>
</html>
