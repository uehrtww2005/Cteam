<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_edit.css">

<div class="store-main">

    <h1>ユーザー情報編集</h1>

    <!-- メッセージ表示 -->
    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <form action="<%=request.getContextPath()%>/Adpay/UserEdit.action"
          method="post"
          onsubmit="return confirmUpdate();">

        <!-- ユーザー名 -->
        <label>名前</label>
        <input type="text" name="name"
               maxlength="10"
               required
               pattern="^[^<>]+$"
               title="ユーザー名に < や > は使用できません"
               value="<c:out value='${sessionScope.user.userName}${sessionScope.group.leaderName}'/>">

        <!-- メールアドレス -->
        <label>メールアドレス</label>
        <input type="email" name="address"
               maxlength="30"
               required
               pattern="^[^<>]+$"
               title="メールアドレスに < や > は使用できません"
               value="<c:out value='${sessionScope.user.address}${sessionScope.group.leaderAddress}'/>">

        <!-- 電話番号 -->
        <label>電話番号</label>
        <input type="text" name="tel"
               maxlength="13"
               required
               pattern="^0\d{1,2}-\d{1,4}-\d{1,4}$"
               title="例：090-1234-5678 の形式で入力してください"
               value="<c:out value='${sessionScope.user.userTel}${sessionScope.group.leaderTel}'/>">

        <hr>

        <!-- パスワード変更 -->
        <label>
            <input type="checkbox" id="pwCheck" onclick="togglePassword();">
            パスワードを変更する
        </label>

        <div id="passwordArea" style="display:none;">

            <label>新しいパスワード</label>
            <input type="password" name="password" id="password"
                   maxlength="15"
                   pattern="^[A-Za-z0-9]+$"
                   title="半角英数字のみ入力できます"
                   disabled>

            <label>パスワード（再確認）</label>
            <input type="password" name="passwordConfirm" id="passwordConfirm"
                   pattern="^[A-Za-z0-9]+$"
                   title="半角英数字のみ入力できます"
                   disabled>

        </div>

        <br>
        <button type="submit">更新する</button>
    </form>

</div>

<script>
/* 更新確認＋パスワード一致チェック */
function confirmUpdate() {
    const pwCheck = document.getElementById("pwCheck").checked;

    if (pwCheck) {
        const pw = document.getElementById("password").value;
        const pwc = document.getElementById("passwordConfirm").value;

        if (pw === "" || pwc === "") {
            alert("パスワードを入力してください");
            return false;
        }

        if (pw !== pwc) {
            alert("パスワードと確認用パスワードが一致しません");
            return false;
        }
    }

    return confirm("この内容でユーザー情報を更新します。よろしいですか？");
}

/* パスワード入力欄ON/OFF */
function togglePassword() {
    const checked = document.getElementById("pwCheck").checked;
    const area = document.getElementById("passwordArea");
    const pw = document.getElementById("password");
    const pwc = document.getElementById("passwordConfirm");

    if (checked) {
        area.style.display = "block";
        pw.disabled = false;
        pwc.disabled = false;
        pw.required = true;
        pwc.required = true;
    } else {
        area.style.display = "none";
        pw.disabled = true;
        pwc.disabled = true;
        pw.required = false;
        pwc.required = false;
        pw.value = "";
        pwc.value = "";
    }
}
</script>

<%@ include file="../footer.html" %>
