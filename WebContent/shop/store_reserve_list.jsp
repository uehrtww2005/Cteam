<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/css/store_side.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/store_reserve_list.css">

<div class="reserve-main">
    <h1>予約一覧</h1>

    <table>
        <tr>
            <th>予約日時</th>
            <th>予約者名</th>
            <th>電話番号</th>
            <th>席</th>
            <th>人数</th>
            <th>合計金額</th>
            <th>先払い金額</th>
            <th>残り金額</th>
            <th>削除</th>
        </tr>

        <c:forEach var="r" items="${reserveList}">
            <tr>
                <td>${r.displayDateTime}</td>
                <td>${r.customerName}</td>
                <td>${r.customerTel}</td>
                <td>${r.seatName}</td>
                <td>${r.numPeople} 名</td>
                <td>${r.totalPay} 円</td>

                <!-- 先払い金額 -->
                <td>
                    <c:choose>
                        <c:when test="${r.advancePay != null}">
                            ${r.advancePay} 円
                        </c:when>
                        <c:otherwise>
                            0 円
                        </c:otherwise>
                    </c:choose>
                </td>

                <!-- 残り金額 -->
                <td>
                    <c:choose>
                        <c:when test="${r.advancePay != null}">
                            ${r.totalPay - r.advancePay} 円
                        </c:when>
                        <c:otherwise>
                            ${r.totalPay} 円
                        </c:otherwise>
                    </c:choose>
                </td>

                <!-- 削除ボタン（★ここだけ修正） -->
                <td>
                    <form action="${pageContext.request.contextPath}/Adpay/StoreReserveDelete.action"
                          method="post"
                          onsubmit="return confirm('この予約を削除しますか？');">

                        <input type="hidden" name="reservation_id" value="${r.reservationId}">
                        <button type="submit" class="delete-btn">削除</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="${pageContext.request.contextPath}/shop/store_home.jsp">
        店舗トップへ戻る
    </a>
</div>

<%@ include file="../footer.html" %>
