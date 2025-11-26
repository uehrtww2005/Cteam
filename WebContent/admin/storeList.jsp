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
        <th>利用状況</th>
        <th>操作</th>
    </tr>
    <c:forEach var="store" items="${storeList}">
        <tr>
            <td>${store.storeId}</td>
            <td>${store.storeName}</td>
            <td>${store.storeAddress}</td>
            <td>${store.storeTel}</td>

            <%-- 利用状況の表示 --%>
            <td>
                <c:choose>
                    <c:when test="${store.status == 0}">利用中</c:when>
                    <c:otherwise><span style="color:red;">停止中</span></c:otherwise>
                </c:choose>
            </td>

            <%-- 利用停止ボタンフォーム --%>
            <td>
                <form action="UpdateStatus.action" method="post" style="margin:0;">
                    <input type="hidden" name="type" value="store">
                    <input type="hidden" name="id" value="${store.storeId}">
                    <input type="hidden" name="currentStatus" value="${store.status}">

                    <c:choose>
                        <c:when test="${store.status == 0}">
                            <input type="submit" value="利用停止" onclick="return confirm('本当に停止しますか？');">
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="利用再開">
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="<%=request.getContextPath()%>/Adpay/AdminLogin.action">管理トップに戻る</a></p>

<%@include file="../footer.html" %>