<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_edit.css">

<div class="store-main">


    <h1>ユーザー情報編集</h1>

    <!-- メッセージ -->
    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <form action="<%=request.getContextPath()%>/Adpay/UserEdit.action"
          method="post"
          onsubmit="return confirmUpdate();">

        <!-- 名前 -->
        <label>名前</label>
        <input type="text" name="name"
               value="<c:out value='${sessionScope.user.userName}${sessionScope.group.leaderName}'/>"
               required>

        <!-- メール -->
        <label>メールアドレス</label>
        <input type="email" name="address"
               value="<c:out value='${sessionScope.user.address}${sessionScope.group.leaderAddress}'/>"
               required>

        <!-- 電話番号 -->
        <label>電話番号</label>
        <input type="text" name="tel"
               value="<c:out value='${sessionScope.user.userTel}${sessionScope.group.leaderTel}'/>"
               required>

        <hr>

        <!-- パスワード変更チェック -->
        <label>
            <input type="checkbox" id="pwCheck" onclick="togglePassword();">
            パスワードを変更する
        </label>

        <!-- パスワード -->
        <div id="passwordArea" style="display:none;">
            <label>新しいパスワード</label>
            <input type="password" name="password" id="password" disabled>

            <label>パスワード（再確認）</label>
            <input type="password" name="passwordConfirm" id="passwordConfirm" disabled>
        </div>

        <br>
        <button type="submit">更新する</button>
    </form>

</div>

<script>
    // 更新確認アラート
    function confirmUpdate() {
        return confirm("この内容でユーザー情報を更新します。よろしいですか？");
    }

    // パスワード入力ON/OFF
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
