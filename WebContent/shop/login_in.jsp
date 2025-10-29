<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>
<h1>店舗　ログイン</h1>
<%
    // Servletで設定されたmsgを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/StoreLogin.action" method="post">
    <p>電話番号：<input type="text" name="store_tel" required></p>
    <p>パスワード：<input type="password" name="password" required></p>
    <p><input type="submit" value="ログイン"></p>
</form>

    <p><a href="<%=request.getContextPath()%>/shop/register.jsp">新規登録はこちら</a></p>

<%@include file="../footer.html" %>