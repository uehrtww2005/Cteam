<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>


<h2>本日の予約一覧</h2>

<form method="get"
      action="<%=request.getContextPath()%>/Adpay/StoreHomeReserveList.action">
    日付：
    <input type="date" name="date" value="${selectedDate}" />
    <input type="submit" value="絞り込み" />
</form>

<br>

<c:choose>
    <c:when test="${not empty reserveList}">
        <table border="1">
            <tr>
                <th>予約日時</th>
                <th>予約者名</th>
                <th>電話番号</th>
                <th>人数</th>
                <th>合計金額</th>
                <th>先払い</th>
                <th>残金</th>
            </tr>

            <c:forEach var="r" items="${reserveList}">
                <tr>
                    <td>${r.displayDateTime}</td>
                    <td>${r.customerName}</td>
                    <td>${r.customerTel}</td>
                    <td>${r.numPeople}</td>
                    <td>${r.totalPay}</td>
                    <td>${r.advancePay}</td>
                    <td>${r.totalPay - r.advancePay}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>予約がありません</p>
    </c:otherwise>
</c:choose>

<%@ include file="../footer.html" %>
