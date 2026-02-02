<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html"%>
<%@ include file="../user_side.jsp" %>
<div class="main-content">
<h1>予約確認</h1>

<p>予約日：${date}</p>
<p>時間：${time}</p>
<p>席：${seat.seatName}（${seat.seatType}）</p>
<p>人数：${numPeople}</p>
<p>予約者名：${customerName}</p>
<p>電話番号：${customerTel}</p>

<h3>選択メニュー</h3>

<table border="1" cellpadding="6">
    <tr>
        <th>メニュー名</th>
        <th>価格</th>
        <th>数量</th>
        <th>小計</th>
    </tr>

    <c:set var="total" value="0"/>
    <c:forEach var="menu" items="${selectedMenus}">
        <c:set var="qty" value="${menuCounts[menu.menuId]}"/>
        <c:set var="sub" value="${menu.price * qty}"/>
        <c:set var="total" value="${total + sub}"/>

        <tr>
            <td>${menu.menuName}</td>
            <td>${menu.price}円</td>
            <td>${qty}</td>
            <td>${sub}円</td>
        </tr>
    </c:forEach>

    <tr>
        <td colspan="3" style="text-align:right;"><strong>合計</strong></td>
        <td><strong>${total}円</strong></td>
    </tr>

    <!-- 追加：先払い金額を表示 -->
    <tr>
        <td colspan="3" style="text-align:right; color:red;"><strong>先払い金額</strong></td>
        <td style="color:red;"><strong>${prepaymentAmount}円</strong></td>
    </tr>
</table>

<br>
<form action="ReserveComplete.action" method="post">
    <input type="hidden" name="store_id" value="${storeId}">
    <input type="hidden" name="date" value="${date}">
    <input type="hidden" name="time" value="${time}">
    <input type="hidden" name="seat_id" value="${seat.seatId}">
    <input type="hidden" name="num_people" value="${numPeople}">
    <input type="hidden" name="customerName" value="${customerName}">
    <input type="hidden" name="customerTel" value="${customerTel}">

    <!-- ★ 各メニューの個数を hidden で送信 -->
    <c:forEach var="menu" items="${selectedMenus}">
        <input type="hidden" name="menu_${menu.menuId}" value="${menuCounts[menu.menuId]}">
    </c:forEach>

    <button type="submit">予約を確定する</button>
</form>

<br>
<a class="back-btn"
   href="${pageContext.request.contextPath}/Adpay/ReserveInput.action?store_id=${storeId}&date=${date}">
    戻る
</a>
</div>
<%@ include file="../footer.html"%>

<style>


.main-content  {
    max-width: 900px;

    position: relative;
    left: calc((100vw - 300px - 900px) / 2 + 220px);
}

form button {
    margin-top: 25px;
    background-color: #FFD700 !important;
    color: #ffffff !important;
    font-weight: bold;
    font-size: 16px;
    padding: 12px 0;
    width: 20%;
    border: none !important;
    border-radius: 8px !important;
    cursor: pointer !important;
    background: linear-gradient(145deg, #222, #111) !important;
    border:1px solid #666 !important;
    box-shadow: 0 4px 10px rgba(0,0,0,0.5) !important;
    transition: 0.3s !important;
}

form button:hover {
    background: linear-gradient(145deg, #333, #000) !important;
    color: #FFD700 !important;
    border-color: #FFD700 !important;
    transform: translateY(-2px) !important;
    box-shadow: 0 0 15px rgba(255,215,0,0.3) !important;
}

.back-btn {
    color: #ffffff;          /* 白文字 */
    text-decoration: underline;  /* リンク感 */
    font-weight: normal;
}

/* ホバー時（ちょい反応するだけ） */
.back-btn:hover {
    opacity: 0.8;
}

/* 訪問済みでも色変えない */
.back-btn:visited {
    color: #ffffff;
}

</style>