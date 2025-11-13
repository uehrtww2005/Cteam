<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<h1>AdPay</h1>

<c:choose>
    <c:when test="${not empty user}">
        <p>${user.userName} さん</p>
        <p>ランク：${user.rank}</p>
        <c:if test="${not empty rankMsg}">
            <p>${rankMsg}</p>
        </c:if>

        <form action="#" method="post">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <div>
            <a href="#">居酒屋</a> |
            <a href="#">レストラン</a> |
            <a href="#">コース料理</a>
        </div>

        <c:if test="${not empty stores}">
            <c:forEach var="s" items="${stores}">
                <p>${s.name}（カテゴリ：${s.category}）</p>
            </c:forEach>
        </c:if>

        <c:if test="${empty stores}">
            <p>該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <c:when test="${not empty group}">
        <p>${group.leaderName} さん</p>
        <p>ランク：${group.rank}</p>
        <c:if test="${not empty rankMsg}">
            <p>${rankMsg}</p>
        </c:if>

        <form action="#" method="post">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </form>

        <div>
            <a href="#">居酒屋</a> |
            <a href="#">レストラン</a> |
            <a href="#">コース料理</a>
        </div>

        <c:if test="${not empty stores}">
            <c:forEach var="s" items="${stores}">
                <p>${s.name}（カテゴリ：${s.category}）</p>
            </c:forEach>
        </c:if>

        <c:if test="${empty stores}">
            <p>該当する店舗はありません。</p>
        </c:if>
    </c:when>

    <c:otherwise>
        <p>ログインしていません。</p>
        <p>
            <a href="<%=request.getContextPath()%>/Adpay/login_choise.jsp">ログイン画面へ</a>
        </p>
    </c:otherwise>
</c:choose>

<%@ include file="../footer.html" %>
