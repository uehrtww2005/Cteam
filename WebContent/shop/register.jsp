<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>店舗新規登録</h1>

<%
    // RegisterAction から送られる msg（リクエスト属性）を取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<!-- ★ 画像アップロードがあるので enctype を multipart/form-data に変更！ -->
<form action="<%=request.getContextPath()%>/Adpay/StoreRegister.action" method="post" enctype="multipart/form-data">

    <p>店舗名：<input type="text" name="store_name" maxlength="10" required></p>

    <p>住所：<input type="text" name="store_address" maxlength="30" required></p>

    <p>電話番号：<input type="text" name="store_tel" maxlength="15" required></p>

    <p>パスワード：<input type="password" name="password" maxlength="15" required></p>

    <p>パスワード確認：<input type="password" name="password_confirm" required></p>

    <!-- ★ 画像アップロード欄を追加 -->
    <p>店舗外観写真：<input type="file" name="store_image" accept="image/*" required></p>

    <p><input type="submit" value="登録"></p>
</form>

<p><input type="button" value="戻る" onclick="history.back();"></p>

<%@ include file="../footer.html" %>
