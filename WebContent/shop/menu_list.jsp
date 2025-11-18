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

<div class="store-home-container">  <!-- 横並びの親 -->

    <%-- サイドバーをここに挿入 --%>
    <%@ include file="../store_side.jsp" %>

    <div class="store-main">  <!-- 右側メイン領域 -->

      <!-- メニュー登録フォーム -->
      <h3>メニュー登録フォーム</h3>

      <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
      </c:if>

      <form action="<%=request.getContextPath()%>/Adpay/MenuRegist.action" method="post" enctype="multipart/form-data">
        <input type="hidden" name="store_id" value="${store_id}">
        <label>メニュー名：</label>
        <input type="text" name="menu_name" required pattern="^[^<>]+$" title="店舗名に < や > は使用できません" placeholder="店舗名を入力してください"><br>
        <label>価格：</label>
        <input type="number" name="price" min="0" required><br>
        <label>画像：</label>
        <input type="file" name="menu_image" accept="image/*" required><br>
        <button type="submit">登録</button>
      </form>

      <!-- 横線で区切る -->
      <hr class="section-divider">

      <!-- 登録済みメニュー一覧 -->
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

    </div> <!-- /store-main -->
</div> <!-- /store-home-container -->

<%@ include file="../footer.html" %>

</body>
</html>
