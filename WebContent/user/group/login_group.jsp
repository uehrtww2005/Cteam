<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/group.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<div class="store-image">
    <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="団体イメージ">
</div>

<h1>団体ログイン</h1>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
<p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/GroupLogin.action" method="post">
    <div class="input-group">
        <p>メールアドレス</p>
        <input type="text" name="leader_address" required placeholder="メールアドレスを入力してください">
    </div>
    <div class="input-group">
        <p>パスワード</p>
        <input type="password" name="password" required placeholder="パスワードを入力してください">
    </div>
    <div class="submit-wrapper">
        <input type="submit" value="ログイン">
    </div>
</form>

<div class="form-links">
    <a href="<%=request.getContextPath()%>/user/group/register_group.jsp">団体の新規登録はこちら</a>

    <a href="<%=request.getContextPath()%>/Adpay/login.jsp">ホームに戻る</a>
</div>

<%@ include file="../../footer.html" %>
