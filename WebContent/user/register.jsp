<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register.css">
<h1>AdPay</h1>
<p>ユーザー新規登録</p>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/UserRegister.action" method="post" onsubmit="return validatePasswords();">

    <!-- ユーザー名：< > 禁止 -->
    <p>
        <label>ユーザー名</label>
        <input type="text" name="user_name" maxlength="10" required
               pattern="^[^<>]+$"
               title="ユーザー名に < や > は使用できません"
               placeholder="ユーザー名を入力してください">
    </p>

    <!-- メールアドレス：形式チェック + < > 禁止 -->
    <p>
        <label>メールアドレス</label>
        <input type="email" name="address" maxlength="30" required
               pattern="^[^<>]+$"
               title="メールアドレスに < や > は使用できません"
               placeholder="example@mail.com">
    </p>

    <!-- 電話番号：数字＋ハイフン形式チェック -->
    <p>
        <label>電話番号</label>
        <input type="text" name="user_tel" maxlength="15" required
               pattern="^0\d{1,3}-\d{1,4}-\d{1,4}$"
               title="電話番号は 例：090-1234-5678 の形式で入力してください"
               placeholder="例：090-1234-5678">
    </p>

    <!-- 性別 -->
    <p>
        <label>性別</label>
        <span class="gender-options">
            <label><input type="radio" name="gender" value="1" required> 男性</label>
            <label><input type="radio" name="gender" value="2" required> 女性</label>
            <label><input type="radio" name="gender" value="0" required> 回答したくない</label>
        </span>
    </p>

    <!-- パスワード -->
    <p>
        <label>パスワード</label>
        <input type="password" name="password" maxlength="15" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="半角英数字で入力してください">
    </p>

    <!-- パスワード確認 -->
    <p>
        <label>パスワード確認</label>
        <input type="password" name="password_confirm" required
               pattern="^[A-Za-z0-9]+$"
               title="半角英数字のみ入力できます"
               placeholder="もう一度パスワードを入力してください">
    </p>

    <p><input type="submit" value="登録"></p>
</form>

<p><input type="button" value="戻る" onclick="history.back();"></p>

<!-- JS: パスワード一致チェック -->
<script>
function validatePasswords() {
    const pw = document.querySelector('input[name="password"]').value;
    const pwc = document.querySelector('input[name="password_confirm"]').value;
    if (pw !== pwc) {
        alert("パスワードが一致しません。");
        return false;
    }
    return true;
}
</script>

<%@ include file="../footer.html" %>
