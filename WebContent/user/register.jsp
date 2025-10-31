<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register.css">
<h1>AdPay</h1>
<p>ユーザー新規登録</p>

<%
    // RegisterAction から送られる msg（リクエスト属性）を取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>


<form action="<%=request.getContextPath()%>/Adpay/Register.action" method="post">

    <p><label>ユーザー名</label><input type="text" name="user_name" maxlength="10" required></p>

    <p><label>メールアドレス</label><input type="text" name="address" maxlength="30" required></p>

    <p><label>電話番号</label><input type="text" name="user_tel" maxlength="15" required></p>

	 <p>
	    <label>性別</label>
	    <span class="gender-options">
	        <label><input type="radio" name="gender" value="1" required> 男性</label>
	        <label><input type="radio" name="gender" value="2" required> 女性</label>
	        <label><input type="radio" name="gender" value="0" required> 回答したくない</label>
	    </span>
	</p>


    <p><label>パスワード</label><input type="password" name="password" maxlength="15" required></p>

    <p><label>パスワード確認</label><input type="password" name="password_confirm" required></p>

    <p><input type="submit" value="登録"></p>
</form>


<p><input type="button" value="戻る" onclick="history.back();">

<%@ include file="../footer.html" %>
