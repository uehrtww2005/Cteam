<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>メニュー登録</title>

</head>
<body>

  <h2>メニュー登録フォーム</h2>

  <c:if test="${not empty msg}">
    <div class="msg">${msg}</div>
  </c:if>

  <form action="MenuRegist.action" method="post" enctype="multipart/form-data">
    <input type="hidden" name="store_id" value="${store_id}">
    <label>メニュー名：</label>
    <input type="text" name="menu_name" required><br>
    <label>価格：</label>
    <input type="number" name="price" required><br>
    <label>画像：</label>
    <input type="file" name="menu_image" accept="image/*"><br>
    <button type="submit">登録</button>
  </form>

  <h2>登録済みメニュー一覧</h2>

  <div class="menu-list">
    <c:forEach var="menu" items="${menuList}">
      <div class="menu-card">
        <img class="menu-img"
             src="<%=request.getContextPath()%>/shop/store_menu_images/${menu.storeId}_${menu.menuId}.${menu.imageExtension}?t=${System.currentTimeMillis()}"
             alt="メニュー画像">
        <div><strong>${menu.menuName}</strong></div>
        <div>${menu.price}円</div>
      </div>
    </c:forEach>
  </div>

</body>
</html>