<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_home.css?ver=2.0">

<div class="welcome-page">

  <div class="welcome-text">
    <h1>お帰りなさい！</h1>
    <h2>${store.storeName}様！</h2>
  </div>

  <div class="plan-box">
    <span class="plan-glow"></span>
    プラン内容：FreePran
  </div>

  <div class="menu-buttons">
    <a href="#" class="btn btn-red"><span>店舗情報</span></a>
    <a href="<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store.storeId}" class="btn btn-yellow"><span>メニュー表</span></a>
    <a href="#" class="btn btn-blue"><span>月額プラン</span></a>
  </div>

</div>

<%@ include file="../footer.html" %>
