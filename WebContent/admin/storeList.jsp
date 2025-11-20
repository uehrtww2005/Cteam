<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>

<h2>店舗一覧</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>店舗名</th>
        <th>住所</th>
        <th>電話番号</th>
    </tr>
    <c:forEach var="store" items="${storeList}">
        <tr>
            <td>${store.storeId}</td>
            <td>${store.storeName}</td>
            <td>${store.storeAddress}</td>
            <td>${store.storeTel}</td>
        </tr>
    </c:forEach>
</table>

<p><a href="<%=request.getContextPath()%>/Adpay/AdminLogin.action">管理トップに戻る</a></p>

<%@include file="../footer.html" %>
