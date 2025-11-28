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
        <th>利用状況</th>
        <th>操作</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.address}</td>
            <td>${user.userTel}</td>

            <%-- 利用状況の表示 --%>
            <td>
                <c:choose>
                    <c:when test="${user.status == 0}"><span style="color:green;">利用中</span></c:when>
                    <c:otherwise><span style="color:red;">停止中</span></c:otherwise>
                </c:choose>
            </td>

            <%-- 利用停止ボタンフォーム --%>
            <td>
                <form action="UpdateStatus.action" method="post" style="margin:0;">
                    <input type="hidden" name="type" value="user">
                    <input type="hidden" name="id" value="${user.userId}">
                    <input type="hidden" name="currentStatus" value="${user.status}">

                    <c:choose>
                        <c:when test="${user.status == 0}">
                            <input type="submit" value="利用停止" onclick="return confirm('本当に停止しますか？');">
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="利用再開" onclick="return confirm('利用再開しますか？');">
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
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
        <th>利用状況</th>
        <th>操作</th>
    </tr>
    <c:forEach var="group" items="${groupList}">
        <tr>
            <td>${group.groupId}</td>
            <td>${group.leaderName}</td>
            <td>${group.leaderAddress}</td>
            <td>${group.leaderTel}</td>

            <%-- 利用状況の表示 --%>
            <td>
                <c:choose>
                    <c:when test="${group.status == 0}"><span style="color:green;">利用中</span></c:when>
                    <c:otherwise><span style="color:red;">停止中</span></c:otherwise>
                </c:choose>
            </td>

            <%-- 利用停止ボタンフォーム --%>
            <td>
                <form action="UpdateStatus.action" method="post" style="margin:0;">
                    <input type="hidden" name="type" value="group">
                    <input type="hidden" name="id" value="${group.groupId}">
                    <input type="hidden" name="currentStatus" value="${group.status}">

                    <c:choose>
                        <c:when test="${group.status == 0}">
                            <input type="submit" value="利用停止" onclick="return confirm('本当に停止しますか？');">
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="利用再開" onclick="return confirm('利用再開しますか？');">
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="<%=request.getContextPath()%>/Adpay/AdminLogin.action">管理トップに戻る</a></p>

<%@include file="../footer.html" %>