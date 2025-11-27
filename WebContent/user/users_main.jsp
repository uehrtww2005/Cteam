<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>

<c:choose>

    <%-- ① userログイン時 --%>
    <c:when test="${not empty user}">
        <p class="user-name">${user.userName} さん</p>
        <p class="user-rank">ランク：${user.rank}</p>

        <%-- rankMsg を request または session から表示 --%>
        <c:choose>
            <c:when test="${not empty rankMsg}">
                <p class="rank-msg">${rankMsg}</p>
            </c:when>
            <c:when test="${not empty sessionScope.rankMsg}">
                <p class="rank-msg">${sessionScope.rankMsg}</p>
            </c:when>
        </c:choose>

        <form action="<%=request.getContextPath()%>/Adpay/UserStoreSearch.action" method="post" class="search-form">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <c:if test="${not empty stores}">
            <div class="store-cards">
                <c:forEach var="s" items="${stores}">
                    <div class="store-card">
                        <a class="store-name"
                           href="<%=request.getContextPath()%>/Adpay/StoreDetail.action?store_id=${s.storeId}">
                            ${s.storeName}
                        </a>
                        <br>
                        <img src="<%=request.getContextPath()%>/shop/store_images/${s.storeId}.jpg"
                             alt="${s.storeName}">
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty stores}">
            <p class="no-store">該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <%-- ② groupログイン時 --%>
    <c:when test="${not empty group}">
        <p class="user-name">${group.leaderName} さん</p>
        <p class="user-rank">ランク：${group.rank}</p>

        <%-- group用のrankMsg を request または session から表示 --%>
        <c:choose>
            <c:when test="${not empty rankMsg}">
                <p class="rank-msg">${rankMsg}</p>
            </c:when>
            <c:when test="${not empty sessionScope.rankMsg}">
                <p class="rank-msg">${sessionScope.rankMsg}</p>
            </c:when>
        </c:choose>

        <form action="<%=request.getContextPath()%>/Adpay/UserStoreSearch.action" method="post" class="search-form">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <c:if test="${not empty stores}">
            <div class="store-cards">
                <c:forEach var="s" items="${stores}">
                    <div class="store-card">
                        <a class="store-name"
                           href="<%=request.getContextPath()%>/Adpay/StoreDetail.action?store_id=${s.storeId}">
                            ${s.storeName}
                        </a>
                        <br>
                        <img src="<%=request.getContextPath()%>/shop/store_images/${s.storeId}.jpg"
                             alt="${s.storeName}">
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty stores}">
            <p class="no-store">該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <%-- ③ ログインなし --%>
    <c:otherwise>
        <p class="no-login">ログインしていません。</p>
        <p>
            <a href="<%=request.getContextPath()%>/Adpay/login_choise.jsp">ログイン画面へ</a>
        </p>
    </c:otherwise>

</c:choose>

<%@ include file="../footer.html" %>

