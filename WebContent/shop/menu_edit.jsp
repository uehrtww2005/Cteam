<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menuedit.css">

<h2>メニュー編集</h2>

<!-- 編集フォーム -->
<form action="<%=request.getContextPath()%>/Adpay/MenuEditExecute.action" method="post">
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
  <input type="number" name="price" value="${menu.price}" required min="0">


  <div class="btn-area">
    <button type="submit" class="update-btn">更新</button>
  </div>
</form>

<!-- 削除フォーム -->
<form action="<%=request.getContextPath()%>/Adpay/MenuDeleteExecute.action" method="post" onsubmit="return confirm('本当に削除しますか？');">
    <input type="hidden" name="menu_id" value="${menu.menuId}">
    <input type="hidden" name="store_id" value="${menu.storeId}">
    <button type="submit" class="delete-button">削除</button>
</form>


<div class="form-links">
    <a href="javascript:history.back();" class="back-link">MENU一覧へ戻る</a>
</div>

<%@ include file="../footer.html" %>
