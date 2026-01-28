<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<div class="store-image">
    <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="ユーザーイメージ">
</div>

<h1>個人ログイン</h1>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
<p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/UserLogin.action" method="post">
    <div class="input-group">
        <p>メールアドレス</p>
        <input type="text" name="address" required placeholder="メールアドレスを入力してください">
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
    <a href="<%=request.getContextPath()%>/user/register_user.jsp">新規登録はこちら</a>
    <a href="<%=request.getContextPath()%>/Adpay/login_choise.jsp">ログイン選択に戻る</a>
</div>

<%@ include file="../footer.html" %>
