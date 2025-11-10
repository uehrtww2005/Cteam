<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css">

<h1>AdPay</h1>
<p>管理者ログイン画面</p>

<%-- ▼ここでエラーメッセージを表示 --%>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red; font-weight:bold;"><%= error %></p>
<%
    }
%>

<form action="<%=request.getContextPath()%>/Adpay/AdminLogin.action" method="post">

    <p>
        名前
        <input type="text" name="adminName"
               placeholder="名前を入力してください"
               required
               pattern="^[A-Za-z0-9ぁ-んァ-ヶ一-龥ーａ-ｚＡ-Ｚ０-９]+$"
               title="記号は使用できません。英数字または日本語で入力してください。">
    </p>

    <p>
        パスワード
        <input type="password" name="password"
               placeholder="パスワードを入力してください"
               required
               pattern="^[A-Za-z0-9ａ-ｚＡ-Ｚ０-９]+$"
               title="記号は使用できません。英数字のみ入力してください。">
    </p>

    <p><input type="submit" value="ログイン"></p>
</form>

<p><input type="button" value="戻る" onclick="history.back();">

<%@ include file="../footer.html" %>
