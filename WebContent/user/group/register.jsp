<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../header.html" %>

<h1>グループ新規登録</h1>

<form action="<%=request.getContextPath()%>/Adpay/GroupRegister.action" method="post">

    <p>代表者名：
        <input type="text" name="leader_name" maxlength="20" required>
    </p>

    <p>メールアドレス（ログインID）：
        <input type="email" name="leader_address" maxlength="30" required>
    </p>

    <p>パスワード：
        <input type="password" name="password" maxlength="15" required>
    </p>

    <p>代表者電話番号：
        <input type="text" name="leader_tel" maxlength="15" required>
    </p>

    <p><input type="submit" value="登録"></p>

</form>

<%@ include file="../../footer.html" %>
