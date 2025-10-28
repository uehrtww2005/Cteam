<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>ユーザー新規登録</h1>

<form action="<%=request.getContextPath()%>/Adpay/Register.action" method="get">

    <p>ユーザー名：<input type="text" name="user_name" maxlength="10" required></p>

    <p>パスワード：<input type="password" name="password" maxlength="15" required></p>

    <p>メールアドレス：<input type="text" name="address" maxlength="30"></p>

    <p>電話番号：<input type="text" name="user_tel" maxlength="15"></p>

    <p>性別：
    <label><input type="radio" name="gender" value="1" required> 男性</label>
    <label><input type="radio" name="gender" value="2"> 女性</label>
    <label><input type="radio" name="gender" value="0"> 回答したくない</label>
    </p>


    <p><input type="submit" value="登録"></p>
</form>

<%@ include file="../footer.html" %>
