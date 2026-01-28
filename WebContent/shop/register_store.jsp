<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register_store.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<div class="store-image">
    <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="店舗登録アイコン">
</div>

<h1>店舗新規登録</h1>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p class="error-msg"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/StoreRegister.action" method="post" enctype="multipart/form-data">
    <div class="input-group">
        <p>店舗名</p>
        <input type="text" name="store_name" maxlength="10" required
               pattern="^[^<>]+$"
               title="店舗名に < や > は使用できません"
               placeholder="店舗名を入力してください">
    </div>

    <div class="input-group">
        <p>住所</p>
        <input type="text" name="store_address" maxlength="30" required
               pattern="^[^<>]{10,30}$"
               title="住所は10文字以上30文字以内で入力してください。また、＜＞などの記号は使わないでください。"
               placeholder="例：東京都港区芝公園４丁目２−８ザ・タワー101号室">
    </div>

    <div class="input-group">
        <p>電話番号</p>
        <input type="text" name="store_tel" maxlength="13" required
               pattern="^0\d{1,3}-\d{1,4}-\d{1,4}$"
               title="例：090-1234-5678 の形式で入力してください"
               placeholder="例：090-1234-5678">
    </div>

    <div class="input-group">
        <p>パスワード</p>
        <input type="password" name="password" maxlength="15" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="パスワードを入力してください">
    </div>

    <div class="input-group">
        <p>パスワード確認</p>
        <input type="password" name="password_confirm" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="もう一度パスワードを入力してください">
    </div>

    <div class="input-group">
        <p>店舗外観写真</p>
        <input type="file" name="store_image" accept="image/*" required>
    </div>

    <div class="submit-wrapper">
        <input type="submit" value="登録">
    </div>
</form>

<!-- フォーム外のリンク -->
<div class="form-links">
    <a href="<%=request.getContextPath()%>/shop/register_store.jsp">ログイン画面に戻る</a>
</div>

<%@ include file="../footer.html" %>
