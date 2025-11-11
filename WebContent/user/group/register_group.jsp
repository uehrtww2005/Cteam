<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register.css">

<h1>AdPay</h1>
<p>グループ新規登録</p>

<%
    // RegisterAction から送られる msg（リクエスト属性）を取得
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="<%=request.getContextPath()%>/Adpay/GroupRegister.action" method="post" onsubmit="return validateForm()">

    <p>
        <label>代表者名</label>
        <input type="text" name="leader_name" maxlength="20" required placeholder="代表者名を入力してください"
               pattern="^[^<>]+$" title="< や > は使用できません">
    </p>

    <p>
        <label>メールアドレス（ログインID）</label>
        <input type="email" name="leader_address" maxlength="30" required placeholder="メールアドレスを入力してください"
               pattern="^[^<>]+$" title="< や > は使用できません">
    </p>

	<p>
        <label>代表者電話番号</label>
        <input type="text" name="leader_tel" maxlength="13" required
               pattern="^0\d{1,3}-\d{1,4}-\d{1,4}$"
               title="電話番号は 例：090-1234-5678 の形式で入力してください"
               placeholder="例：090-1234-5678">
    </p>
    <p>
        <label>パスワード</label>
        <input type="password" name="password" maxlength="15" required placeholder="パスワードを入力してください"
               pattern="^[0-9A-Za-z]+$" title="半角英数字のみ使用できます">
    </p>

    <p>
        <label>パスワード確認</label>
        <input type="password" name="password_confirm" required placeholder="もう一度パスワードを入力してください">
    </p>

    <p><input type="submit" value="登録"></p>

</form>

<p><input type="button" value="戻る" onclick="history.back();"></p>

<script>
function validateForm() {
    const pw = document.querySelector('input[name="password"]').value;
    const pwc = document.querySelector('input[name="password_confirm"]').value;
    if (pw !== pwc) {
        alert("パスワードが一致しません。");
        return false;
    }
    return true;
}
</script>

<%@ include file="../../footer.html" %>
