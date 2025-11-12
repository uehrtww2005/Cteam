<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menuedit.css">

<h2>メニュー編集</h2>

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
  <input type="number" name="price" value="${menu.price}" required><br>

  <button type="submit">更新</button>
</form>

<div class="form-links">
    <a href="javascript:history.back();">MENU一覧へ</a>
</div>

<%@ include file="../footer.html" %>
