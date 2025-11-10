<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/shop.css">
<p>店舗ログイン画面</p>
<%
    // Servletで設定されたmsgを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/StoreLogin.action" method="post">
    <p>電話番号 <input type="text" name="store_tel" required placeholder="電話番号を入力してください"></p>
    <p>パスワード <input type="password" name="password" required placeholder="パスワードを入力してください"></p>
    <p><input type="submit" value="ログイン"></p>
</form>

    <p><a href="<%=request.getContextPath()%>/shop/register_store.jsp">新規登録はこちら</a></p>
    <p><input type="button" value="戻る" onclick="history.back();">

<%@include file="../footer.html" %>