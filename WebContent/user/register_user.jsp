<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register_user.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<div class="user-page-wrapper">

  <div class="user-image">
      <img src="<%=request.getContextPath()%>/images/adpayno.png" alt="登録アイコン">
  </div>

  <h1>ユーザー新規登録</h1>

  <!-- エラーメッセージ表示 -->
  <%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
%>
    <p class="error-msg"><%= msg %></p>
<% } %>

  <form action="<%=request.getContextPath()%>/Adpay/UserRegister.action"
        method="post"
        onsubmit="return validatePasswords();">

      <div class="input-group">
          <p>ユーザー名</p>
          <input type="text" name="user_name" maxlength="10" required
                 pattern="^[^<>]+$"
                 title="ユーザー名に < や > は使用できません"
                 placeholder="ユーザー名を入力してください"
                 value="<%= request.getParameter("user_name") != null ? request.getParameter("user_name") : "" %>">
      </div>

      <div class="input-group">
          <p>メールアドレス</p>
          <input type="email" name="address" maxlength="30" required
                 pattern="^[^<>]+$"
                 title="メールアドレスに < や > は使用できません"
                 placeholder="example@mail.com"
                 value="<%= request.getParameter("address") != null ? request.getParameter("address") : "" %>">
      </div>

      <div class="input-group">
          <p>電話番号</p>
          <input type="text" name="user_tel" maxlength="13" required
                 pattern="^0\d{1,2}-\d{1,4}-\d{1,4}$"
                 title="例：090-1234-5678 の形式で入力してください"
                 placeholder="例：090-1234-5678"
                 value="<%= request.getParameter("user_tel") != null ? request.getParameter("user_tel") : "" %>">
      </div>

      <div class="input-group gender-group">
          <p>性別</p>
          <div class="gender-options">
              <label>
                  <input type="radio" name="gender" value="1"
                      <%= "1".equals(request.getParameter("gender")) ? "checked" : "" %>>
                  男性
              </label>
              <label>
                  <input type="radio" name="gender" value="2"
                      <%= "2".equals(request.getParameter("gender")) ? "checked" : "" %>>
                  女性
              </label>
              <label>
                  <input type="radio" name="gender" value="0"
                      <%= "0".equals(request.getParameter("gender")) ? "checked" : "" %>>
                  回答したくない
              </label>
          </div>
      </div>

      <div class="input-group">
          <p>パスワード</p>
          <input type="password" name="password" maxlength="15" required
                 pattern="^[A-Za-z0-9]+$"
                 title="半角英数字のみ入力できます"
                 placeholder="半角英数字で入力してください">
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
      <a href="javascript:history.back();">個人ログインに戻る</a>
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

<%@ include file="../footer.html" %>
