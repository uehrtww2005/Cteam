<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_home.css">

<div class="main-center">

    <h1>本日の予約一覧</h1>

    <form class="search-form"
          method="get"
          action="<%=request.getContextPath()%>/Adpay/StoreHomeReserveList.action">

        日付：
        <input type="date" name="date" value="${selectedDate}" />
        <input type="submit" value="絞り込み" />
    </form>

    <c:choose>
        <c:when test="${not empty reserveList}">
            <table class="reserve-table">
                <tr>
                    <th>予約日時</th>
                    <th>予約者名</th>
                    <th>電話番号</th>
                    <th>席</th>
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
                        <td>${r.seatName}</td>
                        <td>${r.numPeople}人</td>
                        <td>${r.totalPay}円</td>
                        <td>${r.advancePay}円</td>
                        <td>${r.totalPay - r.advancePay}円</td>
                    </tr>
                </c:forEach>

            </table>
        </c:when>

        <c:otherwise>
            <p>予約がありません</p>
        </c:otherwise>
    </c:choose>

</div>

<%@ include file="../footer.html" %>
