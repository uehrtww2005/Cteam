<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menu.css?ver=1.6">
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

      <h3>コースメニュー登録フォーム</h3>

      <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
      </c:if>

      <form action="<%=request.getContextPath()%>/Adpay/MenuRegist.action"
            method="post"
            enctype="multipart/form-data"
            onsubmit="return validateMenuRegistForm();">

        <input type="hidden" name="store_id" value="${store_id}">

        <!-- メニュー名 -->
        <label>コース名：</label>
        <input type="text"
               id="regMenuName"
               name="menu_name"
               required
               placeholder="コース名を入力してください">

        <div id="regMenuNameError"
             style="color:red; font-size:14px; height:18px; margin-bottom:10px;"></div>

        <!-- 価格 -->
        <label>価格：(税抜)</label>
        <input type="number"
               name="price"
               min="0"
               required
               placeholder="価格を入力してください"><br>

        <!-- ★ メニュー詳細 -->
        <label>コース詳細：</label>
        <textarea name="info"
                  rows="5"
                  placeholder="コースの説明・特徴・注意事項など"
                  style="width:100%; max-width:500px; padding:8px;"></textarea>

        <br>

        <!-- 画像 -->
        <label>コース画像：</label>
        <input type="file"
               name="menu_image"
               accept="image/*"
               required><br>

        <button type="submit">登録</button>
      </form>

      <hr class="section-divider">

      <h3>登録済みコースメニュー一覧</h3>

	<div class="menu-list-wrapper">
		  <div class="menu-list">
		    <c:forEach var="menu" items="${menuList}">
		      <a href="<%=request.getContextPath()%>/Adpay/MenuEditForm.action?menu_id=${menu.menuId}"
		         class="menu-card-link">

		        <div class="menu-card">

		          <c:if test="${not empty menu.imageExtension}">
		            <img class="menu-img"
		                 src="<%=request.getContextPath()%>/shop/store_menu_images/${menu.storeId}_${menu.menuId}.${menu.imageExtension}?t=${System.currentTimeMillis()}"
		                 alt="メニュー画像">
		          </c:if>

		          <c:if test="${empty menu.imageExtension}">
		            <p>（画像が登録されていません）</p>
		          </c:if>

		          <strong>${menu.menuName}</strong>
		          <div>${menu.price}円</div>

		        </div>
		      </a>
		    </c:forEach>
		  </div>
		</div>
    </div>
</div>

<!-- ▼ ページネーション -->
<div class="pagination">
  <a href="?page=1" class="page prev">«</a>
  <a href="?page=1" class="page active">1</a>
  <a href="?page=2" class="page">2</a>
  <a href="?page=3" class="page">3</a>
  <a href="?page=2" class="page next">»</a>
</div>

<%@ include file="../footer.html" %>

<!-- ===============================
     メニュー名バリデーション
================================ -->
<script>
/*
 許可内容
 ・ひらがな / カタカナ / 漢字
 ・半角・全角英数字
 ・長音「ー」
 ・許可記号：＆ ' ， ‐ ． ・ ＜ ＞ < > 【 】
*/

// 「禁止文字」を検出する方式（安全）
const disallowedPattern =
    /[^a-zA-Z0-9ａ-ｚＡ-Ｚ０-９ぁ-んァ-ヶヴー一-龠\u3000＆&'’，‐．・＜＞<>【】]/;

const regInput = document.getElementById("regMenuName");
const regError = document.getElementById("regMenuNameError");

// 入力中チェック
regInput.addEventListener("input", () => {
    const value = regInput.value;

    if (value === "") {
        regError.textContent = "";
        return;
    }

    if (disallowedPattern.test(value)) {
        regError.textContent =
            "使用できる記号は「＆ ' ， ‐ ． ・ ＜ ＞ < > 【 】」のみです。";
    } else {
        regError.textContent = "";
    }
});

// 送信時チェック
function validateMenuRegistForm() {
    const value = regInput.value;

    if (disallowedPattern.test(value)) {
        alert(
            "メニュー名に使用できない文字が含まれています。\n" +
            "使用可能：ひらがな・カタカナ・漢字・英数字・ー\n" +
            "記号：＆ ' ， ‐ ． ・ ＜ ＞ < > 【 】"
        );
        return false;
    }
    return true;
}
</script>

</body>
</html>
