<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html"%>

<h1>お支払い完了</h1>

<p>予約者：${customerName}</p>
<p>予約日：${date}</p>
<p>時間：${time}</p>
<p>付与したポイント：${perPersonPrepaid}</p>


<p>ご予約ありがとうございます。</p>

<c:choose>
    <c:when test="${sessionScope.role == 'user'}">
        <a href="${pageContext.request.contextPath}/Adpay/UserLogin.action">
            一覧へ戻る
        </a>
    </c:when>

    <c:when test="${sessionScope.role == 'group'}">
        <a href="${pageContext.request.contextPath}/Adpay/GroupLogin.action">
            一覧へ戻る
        </a>
    </c:when>
</c:choose>



<%@ include file="../footer.html"%>
