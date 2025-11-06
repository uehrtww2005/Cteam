<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>

<h1>利用者・団体一覧</h1>

<h2>利用者一覧</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>名前</th>
        <th>住所</th>
        <th>電話番号</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.address}</td>
            <td>${user.userTel}</td>
        </tr>
    </c:forEach>
</table>

<h2>団体一覧</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>団体代表名</th>
        <th>住所</th>
        <th>電話番号</th>
    </tr>
    <c:forEach var="group" items="${groupList}">
        <tr>
            <td>${group.groupId}</td>
            <td>${group.leaderName}</td>
            <td>${group.leaderAddress}</td>
            <td>${group.leaderTel}</td>
        </tr>
    </c:forEach>
</table>

<p><a href="<%=request.getContextPath()%>/admin/adminhome.jsp">管理トップに戻る</a></p>

<%@include file="../footer.html" %>
