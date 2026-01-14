<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.time.*" %>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_detail.css">

<h1>${store.storeName}</h1>

<div class="store-detail-wrap">

    <!-- 左：画像・紹介文 -->
    <div class="store-left">
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
            <c:out value="${detail.storeIntroduct}"
                   default="（店舗紹介が登録されていません）"/>
        </p>
    </div>

    <!-- 右：営業カレンダー -->
    <div class="store-right">
        <h2>${year}年 ${month}月 営業カレンダー</h2>

        <div style="margin-bottom:10px;">
            <a href="?store_id=${store.storeId}&year=${year}&month=${month-1}">◀ 前月</a>
            |
            <a href="?store_id=${store.storeId}&year=${year}&month=${month+1}">次月 ▶</a>
        </div>

        <table class="calendar-table">
            <tr>
                <th>日</th><th>月</th><th>火</th>
                <th>水</th><th>木</th><th>金</th><th>土</th>
            </tr>

            <%-- 表示開始日（その月の週の先頭・日曜） --%>
            <c:set var="date"
                   value="${firstDay.minusDays(firstDay.dayOfWeek.value % 7)}"/>

            <c:forEach var="week" begin="1" end="6">
                <tr>
                    <c:forEach var="d" begin="1" end="7">

                        <c:set var="sc" value="${calendarMap[date]}"/>

                        <td>
						    <c:if test="${date.monthValue == month}">

						        <!-- 日付 -->
						        <div class="calendar-day">
						            ${date.dayOfMonth}
						        </div>

						        <!-- 時間 or × -->
						        <c:choose>
						            <c:when test="${not empty sc && sc.open}">
								    <a href="${pageContext.request.contextPath}/Adpay/ReserveInput.action?store_id=${store.storeId}&date=${date}"class="calendar-link">

								        <div class="calendar-time">
								            ${sc.openTimeStr}〜${sc.closeTimeStr}
								        </div>
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
.store-detail-wrap { display:flex; gap:30px; margin:20px 0; }
.store-left, .store-right { width:48%; }

.calendar-link,
.calendar-link:visited,
.calendar-link:hover,
.calendar-link:active {
    text-decoration: none;
    color: inherit;
}


.store-right h2 {
    margin-top: -60px;
}

.store-main-image {
    width:100%;
    max-width:420px;
    border-radius:6px;
}
.no-image {
    height:220px;
    background:#f1f1f1;
    display:flex;
    align-items:center;
    justify-content:center;
}

.calendar-table {
    border-collapse: collapse;
    width: 100%;
    table-layout: fixed;
}

.calendar-table th,
.calendar-table td {
    border: 1px solid #ddd;
    height: 70px;
    font-size: 13px;
    vertical-align: top;
    text-align: center;
    padding: 6px 4px;
}

/* 日付 */
.calendar-day {
    font-weight: bold;
}

/* 時間・×（日付の下） */
.calendar-time {
    margin-top: 4px;
    font-size: 11px;
    line-height: 1.2;
    white-space: nowrap;
    color: #333;
}

/* × のとき */
.calendar-time.closed {
    color: #999;
}

.calendar-time {
    color: #fff;
}

.calendar-time.closed {
    font-weight: bold;      /* ← 太字 */
    font-size: 13px;        /* ← 少し大きく（任意） */
}


</style>

<%@ include file="../footer.html" %>
