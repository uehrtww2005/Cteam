<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../header.html" %>

<h1>団体 ログイン</h1>

<%
    // Servletで設定されたmsgを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/GroupLogin.action" method="post">
    <p>メールアドレス：<input type="text" name="leader_address" required></p>
    <p>パスワード：<input type="password" name="password" required></p>
    <p><input type="submit" value="ログイン"></p>
</form>

<p><a href="<%=request.getContextPath()%>/user/group/register.jsp">団体の新規登録はこちら</a></p>
<p><input type="button" value="戻る" onclick="history.back();">

<%@ include file="../../footer.html" %>