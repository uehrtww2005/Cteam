<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.time.*" %>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_detail.css">

<div class="store-detail-wrap">
    <%@ include file="../user_side.jsp" %>

    <!-- 左：画像・紹介文 -->
    <div class="store-left">
        <h1>${store.storeName}</h1>
        <c:choose>
            <c:when test="${not empty store}">
                <img src="${pageContext.request.contextPath}/shop/store_images/${store.storeId}.${store.imageExtension}"
                     class="store-main-image">
            </c:when>
            <c:otherwise>
                <div class="no-image">画像なし</div>
            </c:otherwise>
        </c:choose>

        <h2>店舗紹介</h2>
        <p>
            <c:out value="${detail.storeIntroduct}" default="（店舗紹介が登録されていません）"/>
        </p>
    </div>

    <!-- 右：営業カレンダー -->
    <div class="store-right">
        <h1>営業カレンダー</h1>

        <div style="margin-bottom:10px;">
            <div class="calendar-nav">
                <a href="?store_id=${store.storeId}&year=${year}&month=${month-1}">◀ 前月</a>
                ${year}年 ${month}月
                <a href="?store_id=${store.storeId}&year=${year}&month=${month+1}">次月 ▶</a>
            </div>
        </div>

        <table class="calendar-table">
            <tr>
                <th>日</th><th>月</th><th>火</th>
                <th>水</th><th>木</th><th>金</th><th>土</th>
            </tr>

            <c:set var="date" value="${firstDay.minusDays(firstDay.dayOfWeek.value % 7)}"/>

            <c:forEach var="week" begin="1" end="6">
                <tr>
                    <c:forEach var="d" begin="1" end="7">
                        <c:set var="sc" value="${calendarMap[date]}"/>
                        <td>
                            <c:if test="${date.monthValue == month}">
                                <div class="calendar-day">${date.dayOfMonth}</div>
                                <c:choose>
                                    <c:when test="${not empty sc && sc.open}">
                                        <a href="${pageContext.request.contextPath}/Adpay/ReserveInput.action?store_id=${store.storeId}&date=${date}" class="calendar-link">
                                            <div class="calendar-time">${sc.openTimeStr}〜${sc.closeTimeStr}</div>
                                        </a>
                                    </c:when>
                                    <c:when test="${not empty sc}">
                                        <div class="calendar-time closed">×</div>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </td>
                        <c:set var="date" value="${date.plusDays(1)}"/>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<style>
.store-detail-wrap {
    display: flex;
    gap: 5px;
    justify-content: flex-start;
    margin: 20px 0;
    width: calc(100% - 220px);
    margin-left: 220px;
}

/* 左 */
.store-left {
    width: 520px;   /* ★画像に合わせた */
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.store-left h1{
    font-size: 50px;   /* ★ ; 修正 */
    margin-top: -4px;
    margin-bottom: -4px;
}

.store-left p{
    width: 100%;
    white-space: pre-line;  /* ★ 改行反映 */
    word-break: break-word;
    text-align: left;
}

.store-left h2{
    text-align: left;
    margin-bottom: 4px;
}

.store-main-image {
    width: 500px;
    height: 300px;
    border-radius: 6px;
    display: block;
    margin-left: 20px;
    object-fit: cover;
}

.no-image {
    width: 500px;
    height: 300px;
    background: #f1f1f1;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 20px;
}

/* 右 */
.store-right {
    width: 400px;
    margin-left: auto;
    margin-right: 20px;
}

.store-right h1 {
    text-align: right;
    margin-bottom: 4px;
    margin-right: 85px;
}

.calendar-nav {
    text-align: right;
    margin-bottom: 5px;
    margin-right: 100px;
}

.calendar-table {
    border-collapse: collapse;
    width: 100%;
    margin-right: 20px;
    table-layout: fixed;
}

.calendar-table th,
.calendar-table td {
    border: 1px solid #ddd;
    width: 14.28%;
    height: 60px;
    font-size: 13px;
    vertical-align: top;
    text-align: center;
    padding: 2px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.calendar-day {
    font-weight: bold;
}

.calendar-time {
    margin-top: 7px;
    font-size: 8px;
    line-height: 0.7;
    white-space: nowrap;
    color: #fff;
}

.calendar-time.closed {
    color: #999;
    font-weight: bold;
    font-size: 13px;
}

.calendar-table th:nth-child(1),
.calendar-table td:nth-child(1) { color: #d9534f; }

.calendar-table th:nth-child(7),
.calendar-table td:nth-child(7) { color: #0275d8; }

.calendar-link,
.calendar-link:visited,
.calendar-link:hover,
.calendar-link:active {
    text-decoration: none;
    color: inherit;
}
</style>

<%@ include file="../footer.html" %>
