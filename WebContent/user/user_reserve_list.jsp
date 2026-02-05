<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>

<h2>あなたの予約一覧</h2>

<c:choose>
    <c:when test="${empty reserveList}">
        <p>現在、予約はありません。</p>
    </c:when>

    <c:otherwise>
        <table border="1">
            <tr>
                <th>店舗名</th>
                <th>予約日時</th>
                <th>人数</th>
                <th>予約者名</th>
                <th>店舗電話番号</th>
            </tr>

            <c:forEach var="r" items="${reserveList}">
                <tr>
                    <td>${r.storeName}</td>
                    <td>${r.displayDateTime}</td>
                    <td>${r.numPeople} 人</td>
                    <td>${r.customerName}</td>
                    <td>${r.storeTel}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="../footer.html" %>
