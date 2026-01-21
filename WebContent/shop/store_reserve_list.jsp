<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html"%>

<h1>予約一覧</h1>

<table border="1" cellpadding="6">
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
            <!-- ★ここだけ変更 -->
            <td>${r.displayDateTime}</td>

            <td>${r.customerName}</td>
            <td>${r.customerTel}</td>
            <td>${r.numPeople}</td>
            <td>${r.totalPay} 円</td>
            <td>${r.advancePay} 円</td>

            <!-- ★ DB保存なし・表示時だけ計算 -->
            <td>
                ${r.totalPay - r.advancePay} 円
            </td>
        </tr>
    </c:forEach>
</table>

<br>

<a href="${pageContext.request.contextPath}/shop/store_home.jsp">
    店舗トップへ戻る
</a>

<%@ include file="../footer.html"%>
