<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin_login.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<!-- 管理者アイコン画像 -->
<div class="admin-image">
    <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="管理者イメージ">
</div>

<h1>管理者ログイン画面</h1>

<%
    String error = (String) request.getAttribute("error");
    if (error != null && !error.isEmpty()) {
%>
    <p class="error-msg"><%= error %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/AdminLogin.action" method="post">
    <div class="input-group">
        <p>名前</p>
        <input type="text" name="adminName"
               placeholder="名前を入力してください"
               required
               pattern="^[A-Za-z0-9ぁ-んァ-ヶ一-龥ーａ-ｚＡ-Ｚ０-９]+$"
               title="記号は使用できません。英数字または日本語で入力してください">
    </div>

    <div class="input-group">
        <p>パスワード</p>
        <input type="password" name="password"
               placeholder="パスワードを入力してください"
               required
               pattern="^[A-Za-z0-9ａ-ｚＡ-Ｚ０-９]+$"
               title="記号は使用できません。英数字のみ入力してください">
    </div>

    <div class="submit-wrapper">
        <input type="submit" value="ログイン">
    </div>
</form>

<div class="form-links">
    <a href="javascript:history.back();">ホームに戻る</a>
</div>

<%@ include file="../footer.html" %>
