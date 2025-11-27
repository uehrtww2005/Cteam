<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_side.css">

<div class="sidebar">
    <a href="<%=request.getContextPath()%>/Adpay/UserLogin.action" class="sidebar-link">トップ</a>
    <a href="#" class="sidebar-link">お知らせ</a>
    <a href="<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store.storeId}" class="sidebar-link">メニュー表</a>
    <a href="<%=request.getContextPath()%>/Adpay/StoreDetailEdit.action?store_id=${store.storeId}" class="sidebar-link">店舗情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/StoreInquiry.action" class="sidebar-link">お問い合わせ</a>


    <a href="#" class="sidebar-link">月額プラン</a>

    <!-- プラン内容 -->
    <div class="sidebar-plan">
        <span class="plan-text">ランク<br>ビギナー</span>
    </div>
</div>
