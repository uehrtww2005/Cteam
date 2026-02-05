<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html"%>
<%@ include file="../user_side.jsp" %>

<style>
/* ===== 支払い完了ページ ===== */
.payment-complete {
    margin-left: 240px;          /* ← サイドバー分 */
    padding: 40px;
    color: #fff;
    max-width: 900px;
}

.payment-complete h1 {
    font-size: 28px;
    margin-bottom: 30px;
    color: #ffffff;
}

.payment-complete p {
    font-size: 16px;
    margin: 10px 0;
    color: #ddd;
}

/* 戻るリンク */
.payment-complete a {
    display: inline-block;
    margin-top: 30px;
    padding: 12px 24px;

    text-decoration: none;
    color: #fff;
    font-weight: bold;

    border-radius: 8px;
    border: 1px solid #666;
    background: linear-gradient(145deg, #222, #111);

    transition: 0.25s;
}

.payment-complete a:hover {
    background: linear-gradient(145deg, #333, #000);
    box-shadow: 0 0 12px rgba(255,215,0,0.25);
    transform: translateY(-1px);
}
</style>

<div class="payment-complete">
    <h1>お支払い・予約完了</h1>

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
</div>

<%@ include file="../footer.html"%>
