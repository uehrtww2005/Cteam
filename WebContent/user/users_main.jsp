<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/users_main.css">

<div class="store-main">

    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <p class="user-name">${sessionScope.user.userName} さん</p>
            <p class="user-rank">ランク：${sessionScope.user.rank}</p>
            <p class="rank-msg">${sessionScope.rankMsg}</p>

            <form action="<%=request.getContextPath()%>/Adpay/UserStoreSearch.action" method="post" class="search-form">
                <input type="text" name="keyword" placeholder="検索">
                <button type="submit">検索</button>
            </form>

            <c:if test="${not empty stores}">
                <div class="store-cards">
                    <c:forEach var="s" items="${stores}">
                        <div class="store-card">
                            <a class="store-name" href="<%=request.getContextPath()%>/Adpay/StoreDetail.action?store_id=${s.storeId}">
                                ${s.storeName}
                            </a>
                            <br>
                            <img src="<%=request.getContextPath()%>/shop/store_images/${s.storeId}.${s.imageExtension}"
                                 alt="${s.storeName}">
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty stores}">
                <p class="no-store">該当する店舗はありません。</p>
            </c:if>
        </c:when>


        <c:when test="${not empty sessionScope.group}">
            <p class="user-name">${sessionScope.group.leaderName} さん</p>
            <p class="user-rank">ランク：${sessionScope.group.rank}</p>
            <p class="rank-msg">${sessionScope.rankMsg}</p>

            <form action="<%=request.getContextPath()%>/Adpay/UserStoreSearch.action" method="post" class="search-form">
                <input type="text" name="keyword" placeholder="検索">
                <button type="submit">検索</button>
            </form>

            <c:if test="${not empty sessionScope.stores}">
                <div class="store-list">
                    <c:forEach var="s" items="${sessionScope.stores}">
                        <div class="store-row">
                            <div class="store-image">
                                <img src="<%=request.getContextPath()%>/shop/store_images/${s.storeId}.${s.imageExtension}"
                                     alt="${s.storeName}">
                            </div>
                            <div class="store-info">
                                <a class="store-name" href="<%=request.getContextPath()%>/Adpay/StoreDetail.action?store_id=${s.storeId}">
                                    ${s.storeName}
                                </a>
                                <p class="store-place">${s.address}</p>
                                <p class="store-genre">${s.genre}</p>
                                <div class="price-box">
                                    <span class="price">￥${s.priceMin} ～ ￥${s.priceMax}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty sessionScope.stores}">
                <p class="no-store">該当する店舗はありません。</p>
            </c:if>
        </c:when>


        <c:otherwise>
            <p class="no-login">ログインしていません。</p>
            <p><a href="<%=request.getContextPath()%>/Adpay/login_choise.jsp">ログイン画面へ</a></p>
        </c:otherwise>
    </c:choose>

</div>

<%@ include file="../footer.html" %>
