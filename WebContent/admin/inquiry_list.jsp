<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/inquiry.css">

<html>
<head>
    <title>お問い合わせ一覧（管理）</title>
    <style>
        table {
            width: 95%;
            border-collapse: collapse;
            margin: 20px;
        }
        th, td {
            border: 1px solid #aaa;
            padding: 8px;
        }
        th {
            background: #ddd;
        }
    </style>
</head>
<body>

<h2>お問い合わせ一覧（管理者）</h2>

<table>
    <tr>
        <th>受取日付</th>
        <th>名前</th>
        <th>電話番号</th>
        <th>お問い合わせ内容</th>
    </tr>

    <c:forEach var="inq" items="${inquiryList}">
    <tr>
        <td>${inq.createdAt}</td>
        <td>
            <c:choose>
                <c:when test="${not empty inq.userName}">${inq.userName}</c:when>
                <c:when test="${not empty inq.storeName}">${inq.storeName}</c:when>
                <c:when test="${not empty inq.leaderName}">${inq.leaderName}</c:when>
                <c:otherwise>不明</c:otherwise>
            </c:choose>
        </td>
        <td>${inq.tel}</td>
        <td>${inq.content}</td>
    </tr>
</c:forEach>


</table>

</body>
</html>
<%@ include file="../footer.html" %>
