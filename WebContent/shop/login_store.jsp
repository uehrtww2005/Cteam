<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/shop.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<!-- 店舗イメージ画像 -->
<div class="store-image">
    <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="店舗イメージ">
</div>

<h1>店舗ログイン</h1>

<%
    // サーブレットから送られるメッセージを取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/StoreLogin.action" method="post">
    <div class="input-group">
        <p>電話番号</p>
        <input type="text" name="store_tel" required placeholder="電話番号を入力してください">
    </div>

    <div class="input-group">
        <p>パスワード</p>
        <input type="password" name="password" required placeholder="パスワードを入力してください">
    </div>

    <div class="submit-wrapper">
        <input type="submit" value="ログイン">
    </div>
</form>

<!-- フォーム外のリンク -->
<div class="form-links">
    <a href="<%=request.getContextPath()%>/shop/register_store.jsp">新規登録はこちら</a>
    <a href="javascript:history.back();">ホームに戻る</a>
</div>

<%@ include file="../footer.html" %>
