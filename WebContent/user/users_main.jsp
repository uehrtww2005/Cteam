<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store.css">

<h1>店舗検索</h1>

<c:choose>
    <c:when test="${not empty user}">
        <p class="user-name">${user.userName} さん</p>
        <p class="user-rank">ランク：${user.rank}</p>
        <c:if test="${not empty rankMsg}">
            <p class="rank-msg">${rankMsg}</p>
        </c:if>

        <form action="#" method="post" class="search-form">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <div class="category-links">
            <a href="#">居酒屋</a>
            <a href="#">レストラン</a>
            <a href="#">コース料理</a>
        </div>

        <c:if test="${not empty stores}">
            <div class="store-cards">
                <c:forEach var="s" items="${stores}">
                    <div class="store-card">
                        <div class="store-image">🏯</div>
                        <div class="store-name">${s.name}</div>
                        <div class="store-category">${s.category}</div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty stores}">
            <p class="no-store">該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <c:when test="${not empty group}">
        <p class="user-name">${group.leaderName} さん</p>
        <p class="user-rank">ランク：${group.rank}</p>
        <c:if test="${not empty rankMsg}">
            <p class="rank-msg">${rankMsg}</p>
        </c:if>

        <form action="#" method="post" class="search-form">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <div class="category-links">
            <a href="#">居酒屋</a>
            <a href="#">レストラン</a>
            <a href="#">コース料理</a>
        </div>

        <c:if test="${not empty stores}">
            <div class="store-cards">
                <c:forEach var="s" items="${stores}">
                    <div class="store-card">
                        <div class="store-image">🏯</div>
                        <div class="store-name">${s.name}</div>
                        <div class="store-category">${s.category}</div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty stores}">
            <p class="no-store">該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <c:otherwise>
        <p class="no-login">ログインしていません。</p>
        <p><a href="<%=request.getContextPath()%>/Adpay/login_choise.jsp">ログイン画面へ</a></p>
    </c:otherwise>
</c:choose>

<%@ include file="../footer.html" %>
