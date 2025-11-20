<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menu.css?ver=1.4">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<html>
<head>
  <title>メニュー登録</title>
</head>
<body>

<div class="store-home-container">

    <%@ include file="../store_side.jsp" %>

    <div class="store-main">

      <h3>メニュー登録フォーム</h3>

      <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
      </c:if>

      <form action="<%=request.getContextPath()%>/Adpay/MenuRegist.action"
            method="post" enctype="multipart/form-data"
            onsubmit="return validateMenuRegistForm();">

        <input type="hidden" name="store_id" value="${store_id}">

        <label>メニュー名：</label>
        <input type="text" id="regMenuName" name="menu_name" required placeholder="メニュー名を入力してください">

        <!-- フィールドエラーメッセージ -->
        <div id="regMenuNameError" style="color:red; font-size:14px; height:18px; margin-bottom:8px;"></div>

        <label>価格</label>
		<input type="number" name="price" min="0" required placeholder="価格を入力してください"><br>

        <label>画像：</label>
        <input type="file" name="menu_image" accept="image/*" required><br>

        <button type="submit">登録</button>
      </form>

      <hr class="section-divider">

      <h3>登録済みメニュー一覧</h3>

      <div class="menu-list">
        <c:forEach var="menu" items="${menuList}">
          <a href="<%=request.getContextPath()%>/Adpay/MenuEditForm.action?menu_id=${menu.menuId}" class="menu-card-link">
            <div class="menu-card">
              <c:if test="${not empty menu.imageExtension}">
                <img class="menu-img"
                     src="<%=request.getContextPath()%>/shop/store_menu_images/${menu.storeId}_${menu.menuId}.${menu.imageExtension}?t=${System.currentTimeMillis()}"
                     alt="メニュー画像">
              </c:if>
              <c:if test="${empty menu.imageExtension}">
                <p>（画像が登録されていません）</p>
              </c:if>
              <div><strong>${menu.menuName}</strong></div>
              <div>${menu.price}円</div>
            </div>
          </a>
        </c:forEach>
      </div>

    </div>
</div>

<%@ include file="../footer.html" %>

<!-- ★ メニュー名バリデーション（禁止記号→削除 & 赤メッセージ表示） -->
<script>
// 許可記号：＆ ' ， ‐ ． ・
const allowedPattern = /^[a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆'，‐．・]+$/;
const disallowedPattern = /[^a-zA-Z0-9ぁ-んァ-ヶ一-龠０-９ 　＆'，‐．・]/g;

const regInput = document.getElementById("regMenuName");
const regError = document.getElementById("regMenuNameError");

// 入力中の処理
regInput.addEventListener("input", () => {
    let value = regInput.value;

    if (value === "") {
        regError.textContent = "";
        return;
    }

    // 禁止記号があれば削除
    if (disallowedPattern.test(value)) {
        regInput.value = value.replace(disallowedPattern, "");
        regError.textContent = "使用できる記号は「＆ ' ， ‐ ． ・」のみです。";
    } else {
        regError.textContent = "";
    }
});

// 送信時の最終チェック
function validateMenuRegistForm() {
    const name = regInput.value.trim();

    // エラーがある状態なら送信禁止
    if (regError.textContent !== "") {
        alert("メニュー名に使用できない記号が含まれています。");
        return false;
    }

    // 送信時も再チェック（保険）
    if (!allowedPattern.test(name)) {
        alert("メニュー名に使用できる記号は「＆ ' ， ‐ ． ・」のみです。");
        return false;
    }

    return true;
}
</script>

</body>
</html>
