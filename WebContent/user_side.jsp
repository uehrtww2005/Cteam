<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_side.css">

<div class="sidebar">
    <a href="<%=request.getContextPath()%>/Adpay/UserLogin.action" class="sidebar-link">トップ</a>
    <a href="#" class="sidebar-link">お知らせ</a>
    <a href="#" class="sidebar-link">ユーザー情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/UserInquiry.action" class="sidebar-link">お問い合わせ</a>

    <!-- プラン内容 -->
    <div class="sidebar-plan">
        <span class="plan-text">ランク<br>${sessionScope.user.rank}</span>
    </div>
</div>
