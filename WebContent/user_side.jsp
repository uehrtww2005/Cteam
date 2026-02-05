<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_side.css">

<div class="sidebar">

    <!-- トップリンク：ログイン種別で分岐 -->
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <a href="<%=request.getContextPath()%>/Adpay/UserLogin.action" class="sidebar-link">トップ</a>
        </c:when>
        <c:when test="${not empty sessionScope.group}">
            <a href="<%=request.getContextPath()%>/Adpay/GroupLogin.action" class="sidebar-link">トップ</a>
        </c:when>
    </c:choose>

    <a href="<%=request.getContextPath()%>/Adpay/UserCouponList.action" class="sidebar-link">クーポン</a>
    <a href="<%=request.getContextPath()%>/Adpay/UserEdit.action" class="sidebar-link">ユーザー情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/UserInquiry.action" class="sidebar-link">お問い合わせ</a>

    <a href="<%=request.getContextPath()%>/Adpay/#"
	   class="sidebar-link">
	  予約店舗一覧
	</a>


    <!-- プラン内容 -->
    <div class="sidebar-plan">
        <span class="plan-text">
            ランク<br>
            <c:out value="${sessionScope.user.rank}" />
        </span>
    </div>

</div>
