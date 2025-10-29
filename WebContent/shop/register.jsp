<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>店舗新規登録</h1>

<form action="<%=request.getContextPath()%>/Adpay/StoreRegister.action" method="post">

	<p>店舗名：<input type="text" name="store_name" maxlength="10"required></p>

    <p>住所：<input type="text" name="store_address" maxlength="30" required></p>

    <p>電話番号：<input type="text" name="store_tel" maxlength="15" required></p>

    <p>パスワード：<input type="password" name="password" maxlength="15" required></p>


    <p><input type="submit" value="登録"></p>
</form>

<%@ include file="../footer.html" %>
