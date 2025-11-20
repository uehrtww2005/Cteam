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

<!-- ★ メニュー名バリデーション（禁止記号→削除 & 赤メッセージ表示） -->
<script>
// ------------------------------------------
// 編集ページ用メニュー名バリデーション
// 許可記号：＆ ' ， ‐ ． ・
// ------------------------------------------

// 禁止記号だけを検出するパターン（もっとも安定）
const disallowedPattern = /[^a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ \u3000＆&':：’，‐．・]/;

const input = document.getElementById("menuNameInput");
const error = document.getElementById("menuNameError");

// 入力中の処理
input.addEventListener("input", () => {
    const value = input.value;

    if (value === "") {
        error.textContent = "";
        return;
    }

    // 禁止記号が見つかったら赤エラー表示
    if (disallowedPattern.test(value)) {
        error.textContent = "使用できる記号は「：＆ ' ， ‐ ． ・」のみです。";
    } else {
        error.textContent = "";
    }
});

// 送信時の最終チェック
function validateMenuEditForm() {
    const value = input.value;

    // 禁止記号が含まれている
    if (disallowedPattern.test(value)) {
        alert("メニュー名に使用できない記号が含まれています。\n使用可能：＆； ' ， ‐ ． ・");
        return false;
    }

    return true;
}
</script>

