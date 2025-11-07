<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_home.css">

<div class="welcome-page">
  <h1>お帰りなさい！</h1>
  <h2>${store.storeName}様！</h2>

  <div class="plan-box">
    プラン内容：FreePran
  </div>

  <div class="menu-buttons">
    <a href="#" class="btn btn-red">店舗情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store.storeId}"
       class="btn btn-yellow">メニュー表</a>
    <a href="#" class="btn btn-blue">月額プラン</a>
  </div>
</div>

<%@ include file="../footer.html" %>
