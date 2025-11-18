<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menuedit.css">

<h2>メニュー編集</h2>

<!-- 編集フォーム（★バリデーション追加！） -->
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
  <input type="text" name="menu_name" value="${menu.menuName}" required><br>

  <label>価格：</label>
  <input type="number" name="price" value="${menu.price}" min="0" required><br>

  <div class="btn-area">
    <button type="submit" class="update-btn">更新</button>
  </div>
</form>

<!-- 削除フォーム -->
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

<!-- ★ 許可記号バリデーション JS -->
<script>
// 許可記号：＆ ' ， ‐ ． ・
const menuNamePattern = /^[a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆ ，‐．・']+$/;

function validateMenuEditForm() {
    const name = document.querySelector("input[name='menu_name']").value.trim();
    const price = document.querySelector("input[name='price']").value;

    // メニュー名チェック
    if (!menuNamePattern.test(name)) {
        alert("メニュー名に使用できる記号は「＆ ' ， ‐ ． ・」のみです。");
        return false;
    }

    // 価格チェック
    if (price === "" || isNaN(price) || Number(price) < 0) {
        alert("価格は 0 以上の数値で入力してください。");
        return false;
    }

    return true;
}

// 入力中に禁止文字を自動削除
document.querySelector("input[name='menu_name']").addEventListener("input", (e) => {
    const value = e.target.value;
    if (!menuNamePattern.test(value)) {
        e.target.value = value.replace(/[^a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆'，‐．・]/g, "");
        alert("使える記号は「＆ ' ， ‐ ． ・」だけです。");
    }
});
</script>
