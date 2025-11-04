<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register.css">
<h1>AdPay</h1>
<p>店舗新規登録</p>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/StoreRegister.action" method="post" enctype="multipart/form-data">

    <!-- 店舗名：記号もOK -->
    <p>
        <label>店舗名</label>
        <input type="text" name="store_name" maxlength="10" required
               pattern="^[^<>]+$"
               title="店舗名に < や > は使用できません"
               placeholder="店舗名を入力してください">
    </p>

    <!-- 住所：文字と数字のみ（全角・半角OK） -->
    <p>
        <label>住所</label>
        <input type="text" name="store_address" maxlength="30" required
               pattern="^[0-9A-Za-z０-９ぁ-んァ-ン一-龥ー]+$"
               title="文字と数字のみ入力できます（記号は不可）"
               placeholder="住所を入力してください">
    </p>

    <!-- 電話番号：数字とハイフンのみ、形式チェックあり -->
    <p>
        <label>電話番号</label>
        <input type="text" name="store_tel" maxlength="15" required
               pattern="^0\d{1,2}-\d{1,4}-\d{1,4}$"
               title="電話番号は 例：090-1234-5678 の形式で入力してください"
               placeholder="例：090-1234-5678">
    </p>

    <!-- パスワード：半角英数字のみ -->
    <p>
        <label>パスワード</label>
        <input type="password" name="password" maxlength="15" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="パスワードを入力してください">
    </p>

    <!-- パスワード確認 -->
    <p>
        <label>パスワード確認</label>
        <input type="password" name="password_confirm" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="もう一度パスワードを入力してください">
    </p>

    <!-- 画像アップロード -->
    <p>
        <label>店舗外観写真</label>
        <input type="file" name="store_image" accept="image/*" required>
    </p>

    <p><input type="submit" value="登録"></p>
</form>

<p><input type="button" value="戻る" onclick="history.back();"></p>

<%@ include file="../footer.html" %>