<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user.css">
<h1>AdPay</h1>
<p>個人ログイン画面</p>
<%
    // Servletで設定されたmsgを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/UserLogin.action" method="post">
    <p>メールアドレス<input type="text" name="address" required placeholder="メールアドレスを入力してください"></p>
    <p>パスワード<input type="password" name="password" required placeholder="パスワードを入力してください"></p>
    <p><input type="submit" value="ログイン"></p>
</form>

<p><a href="<%=request.getContextPath()%>/user/register.jsp">一般の新規登録はこちら</a></p>
<p><input type="button" value="戻る" onclick="history.back();">

<%@ include file="../footer.html" %>
