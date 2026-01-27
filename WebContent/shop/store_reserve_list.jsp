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
            <th>人数</th>
            <th>合計金額</th>
            <th>先払い金額</th>
            <th>残り金額</th>
        </tr>

        <c:forEach var="r" items="${reserveList}">
            <tr>
                <!-- 表示用日時（DB保存なし） -->
                <td>${r.displayDateTime}</td>

                <td>${r.customerName}</td>
                <td>${r.customerTel}</td>
                <td>${r.numPeople} 名</td>
                <td>${r.totalPay} 円</td>
                <td>${r.advancePay} 円</td>

                <!-- 表示時に計算 -->
                <td>
                    ${r.totalPay - r.advancePay} 円
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="${pageContext.request.contextPath}/shop/store_home.jsp">
        店舗トップへ戻る
    </a>

</div>

<%@ include file="../footer.html" %>
