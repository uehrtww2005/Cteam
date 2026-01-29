<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register_group.css">

<div class="group-page-wrapper">

    <div class="group-image">
        <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="登録アイコン">
    </div>

    <h1>団体新規登録</h1>

    <!-- ▼ エラーメッセージ表示（JS / Action 共通） -->
	<%
	    String msg = (String) request.getAttribute("msg");
	    if (msg != null && !msg.isEmpty()) {
	%>
	    <p class="error-msg"><%= msg %></p>
	<% } %>

    <form action="<%=request.getContextPath()%>/Adpay/GroupRegister.action"
          method="post"
          onsubmit="return validatePasswords();">

        <div class="input-group">
            <p>代表者名</p>
            <input type="text" name="leader_name" maxlength="20" required
                   pattern="^[^<>]+$"
                   title="< や > は使用できません"
                   placeholder="代表者名を入力してください"
                   value="<%= request.getParameter("leader_name") != null ? request.getParameter("leader_name") : "" %>">
        </div>

        <div class="input-group">
            <p>メールアドレス（ログインID）</p>
            <input type="email" name="leader_address" maxlength="30" required
                   pattern="^[^<>]+$"
                   title="< や > は使用できません"
                   placeholder="example@mail.com"
                   value="<%= request.getParameter("leader_address") != null ? request.getParameter("leader_address") : "" %>">
        </div>

        <div class="input-group">
            <p>代表者電話番号</p>
            <input type="text" name="leader_tel" maxlength="13" required
                   pattern="^0\d{1,2}-\d{1,4}-\d{1,4}$"
                   title="例：090-1234-5678 の形式で入力してください"
                   placeholder="例：090-1234-5678"
                   value="<%= request.getParameter("leader_tel") != null ? request.getParameter("leader_tel") : "" %>">
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

        <div class="submit-wrapper">
            <input type="submit" value="登録">
        </div>

    </form>


<div class="form-links">
        <a href="<%=request.getContextPath()%>/user/group/login_group.jsp">団体ログインに戻る</a>
    </div>

</div>

<script>
function validatePasswords() {
    const pw = document.querySelector('input[name="password"]').value;
    const pwc = document.querySelector('input[name="password_confirm"]').value;
    const errorMsg = document.getElementById("errorMsg");

    if (pw !== pwc) {
        errorMsg.textContent = "パスワードと確認用パスワードが一致しません";
        return false;
    }

    errorMsg.textContent = "";
    return true;
}
</script>

<%@ include file="../../footer.html" %>