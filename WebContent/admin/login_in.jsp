<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>


    <h1>管理者　ログイン</h1>

    <form action="Login.action" method="get">
	<p>名前<input type="text" name="admin_name"></p>
	<p>パスワード<input type="text" name="password"></p>
	<p><input type="submit" value="ログイン"></p>
	</form>

<%@include file="../footer.html" %>