<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_detail.css">

<h1>${store.storeName}</h1>

<div class="store-detail-wrap">
    <!-- 左：画像と紹介文 -->
    <div class="store-left">
        <c:choose>
		    <c:when test="${not empty store}">
		        <img src="${pageContext.request.contextPath}/shop/store_images/${store.storeId}.${store.imageExtension}"
		             alt="店舗画像" class="store-main-image">
		    </c:when>
		    <c:otherwise>
		        <div class="no-image">画像なし</div>
		    </c:otherwise>
		</c:choose>


        <h2>店舗紹介</h2>
        <p><c:out value="${detail.storeIntroduct}" default="（店舗紹介が登録されていません）"/></p>
    </div>

    <!-- 右：営業カレンダー -->
    <div class="store-right">
        <h2>営業カレンダー</h2>

        <c:if test="${empty calendars}">
            <p>カレンダーデータがありません。</p>
        </c:if>

        <c:if test="${not empty calendars}">
            <table class="calendar-table">
                <tr>
                    <th style="color:red;">日</th>
                    <th>月</th>
                    <th>火</th>
                    <th>水</th>
                    <th>木</th>
                    <th>金</th>
                    <th style="color:blue;">土</th>
                </tr>

                <c:set var="dayCounter" value="1" />
                <c:set var="lastDay" value="${calendars[calendars.size()-1].date.day}" />

                <c:forEach var="week" begin="1" end="6">
                    <tr>
                        <c:forEach var="i" begin="1" end="7">
                            <c:choose>
                                <c:when test="${dayCounter <= calendars.size()}">
                                    <c:set var="sc" value="${calendars[dayCounter-1]}" />
                                    <c:set var="cls" value="" />
                                    <c:choose>
                                        <c:when test="${sc.dateStr lt todayStr}"> <c:set var="cls" value="past"/> </c:when>
                                        <c:when test="${(i == 1)}"> <c:set var="cls" value="sunday"/> </c:when>
                                        <c:when test="${(i == 7)}"> <c:set var="cls" value="saturday"/> </c:when>
                                    </c:choose>
                                    <td class="${cls}">
                                        ${sc.dateStr.substring(8,10)}<br/>
                                        <c:choose>
                                            <c:when test="${sc.open}">
                                                ${sc.openTimeStr}〜${sc.closeTimeStr}
                                            </c:when>
                                            <c:otherwise>×</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <c:set var="dayCounter" value="${dayCounter+1}" />
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>

<style>
.store-detail-wrap { display:flex; gap:30px; align-items:flex-start; margin:20px 0; }
.store-left { width:48%; }
.store-right { width:48%; }

.store-main-image { width:100%; max-width:420px; border-radius:6px; box-shadow:0 1px 3px rgba(0,0,0,0.1); }
.no-image { width:100%; height:220px; background:#f1f1f1; display:flex; align-items:center; justify-content:center; color:#888; }

.calendar-table { border-collapse: collapse; width:100%; }
.calendar-table th, .calendar-table td { border:1px solid #ddd; padding:6px; text-align:center; width:14%; height:60px; }
.calendar-table .past { background:#eee; color:#999; }
.calendar-table .sunday { background:#ffecec; }
.calendar-table .saturday { background:#ecf0ff; }
</style>

<%@ include file="../footer.html" %>
