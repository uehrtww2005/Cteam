<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menuedit.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<h2>メニュー編集</h2>

<form action="<%=request.getContextPath()%>/Adpay/MenuEditExecute.action"
      method="post"
      onsubmit="return validateMenuEditForm();">

  <input type="hidden" name="menu_id" value="${menu.menuId}">

  <div class="menu-image-section">
      <c:if test="${not empty menu.imagePath}">
        <img class="menu-img"
             src="${pageContext.request.contextPath}/${menu.imagePath}?t=${System.currentTimeMillis()}"
             alt="メニュー画像">
      </c:if>
  </div>

  <label>メニュー名：</label>
  <input type="text" id="menuNameInput" name="menu_name" value="${menu.menuName}" required>
  <!-- フィールドエラーメッセージ -->
  <div id="menuNameError" style="color:red; font-size:14px; height:18px; margin-bottom:8px;"></div>

  <label>価格：</label>
  <input type="number" name="price" value="${menu.price}" min="0" required><br>

  <div class="btn-area">
    <button type="submit" class="update-btn">更新</button>
  </div>
</form>

<form action="<%=request.getContextPath()%>/Adpay/MenuDeleteExecute.action"
      method="post"
      onsubmit="return confirm('本当に削除しますか？');">
    <input type="hidden" name="menu_id" value="${menu.menuId}">
    <input type="hidden" name="store_id" value="${menu.storeId}">
    <button type="submit" class="delete-button">削除</button>
</form>

<div class="form-links">
    <a href="javascript:history.back();" class="back-link">MENU一覧へ戻る</a>
</div>

<%@ include file="../footer.html" %>

<!-- ★ 許可記号バリデーション JS（フィールドエラーメッセージ対応版） -->
<script>
// 許可記号：＆ ' ， ‐ ． ・
// 送信時のチェック
const fullPattern = /^[a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆&:：'’，ー‐．。・]+$/;

// 入力中の禁止文字除去
const disallowedPattern = /[^a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆&:：'’，ー‐．。・]/g;

const menuNameInput = document.getElementById("menuNameInput");
const menuNameError = document.getElementById("menuNameError");

// ---- 入力中（禁止記号を削除＆フィールドメッセージ表示） ----
menuNameInput.addEventListener("input", () => {
    let value = menuNameInput.value;

    // 空ならメッセージ消す
    if (value === "") {
        menuNameError.textContent = "";
        return;
    }

    // 禁止記号がある場合 → 自動削除
    if (disallowedPattern.test(value)) {
        menuNameInput.value = value.replace(disallowedPattern, "");
        menuNameError.textContent = "使用できる記号は「＆： ' ， ‐ ．。 ・」のみです。";
    } else {
        // 許可文字のみ → メッセージクリア
        menuNameError.textContent = "";
    }
});

// ---- 送信時チェック ----
function validateMenuEditForm() {
    const name = menuNameInput.value.trim();
    const price = document.querySelector("input[name='price']").value;

    // フィールドエラーが残っている場合は送信禁止
    if (menuNameError.textContent !== "") {
        alert("メニュー名に使用できない記号が含まれています。");
        return false;
    }

    // 空欄は required に任せる
    if (name !== "" && !fullPattern.test(name)) {
        alert("メニュー名に使用できる記号は「＆： ' ， ‐ ．。 ・」のみです。");
        return false;
    }

    if (price === "" || isNaN(price) || Number(price) < 0) {
        alert("価格は 0 以上の数値で入力してください。");
        return false;
    }

    return true;
}
</script>
