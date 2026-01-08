<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<style>
.calendar-table {
    border-collapse: collapse;
    margin-bottom: 30px;
    width: 100%;
}
.calendar-table th,
.calendar-table td {
    border: 1px solid #aaa;
    padding: 8px;
    text-align: center;
    width: 14%;
    height: 60px;
    cursor: pointer;
    white-space: pre-line;
}
.calendar-table th {
    background-color: #f0f0f0;
}

.modal {
    display: none;
    position: fixed;
    top: 20%;
    left: 30%;
    width: 40%;
    background: #fff;
    border: 1px solid #000;
    padding: 20px;
    z-index: 1000;
}

/* 過去日は見た目上無効化 */
td.past {
    background-color: #eee;
    color: #aaa;
    pointer-events: none;
    cursor: default;
}
</style>

<h1>クーポン追加</h1>

<!-- エラーメッセージ表示 -->
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="CouponInsert.action" method="post">

    <!-- 店舗ID -->
    <input type="hidden" name="store_id" value="${storeId}">


    <!-- クーポン名 -->
    <label>クーポン名：</label><br>
    <input type="text" name="new_coupon_name" required><br><br>

    <!-- ランク -->
    <label>ランク：</label><br>
    <input type="text" name="new_coupon_rank" placeholder="例：ゴールド" required><br><br>

    <!-- 説明 -->
    <label>説明：</label><br>
    <textarea name="new_coupon_introduct" rows="3" cols="40" required></textarea><br><br>

    <button type="submit">追加する</button>
</form>

<hr>

<h2>クーポン一覧</h2>

<c:if test="${empty couponList}">
    <p>登録されているクーポンはありません</p>
</c:if>

<c:if test="${not empty couponList}">
    <table border="1" width="100%" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>クーポン名</th>
            <th>ランク</th>
            <th>説明</th>
        </tr>

        <c:forEach var="c" items="${couponList}">
            <tr>
                <td>${c.couponId}</td>
                <td>${c.couponName}</td>
                <td>${c.couponRank}</td>
                <td>${c.couponIntroduct}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
