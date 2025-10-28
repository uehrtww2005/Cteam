<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>利用者 ログイン</h1>

<%
    // Servletで設定されたmsgを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/UserLogin.action" method="get">
    <p>メールアドレス：<input type="text" name="address" required></p>
    <p>パスワード：<input type="password" name="password" required></p>
    <p><input type="submit" value="ログイン"></p>
</form>

<p><a href="<%=request.getContextPath()%>/user/register.jsp">一般の新規登録はこちら</a></p>

<%@ include file="../footer.html" %>
