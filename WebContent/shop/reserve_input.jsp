<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.time.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html"%>
<%@ include file="../user_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reserve_form.css">


<h1>予約入力</h1>

<p>予約日：${date}</p>

<form id="reserveForm" action="ReserveConfirm.action" method="post">
<input type="hidden" name="store_id" value="${storeId}">
<input type="hidden" name="date" value="${date}">

    <!-- 席選択 -->
<label>席：</label>
<select name="seat_id" required>
<c:forEach var="s" items="${seats}">
<c:choose>
<c:when test="${reservedSeatIds.contains(s.seatId)}">
<option value="${s.seatId}" disabled>
                        ${s.seatName}（${s.seatType}） - 予約済
</option>
</c:when>
<c:otherwise>
<option value="${s.seatId}">
                        ${s.seatName}（${s.seatType}）
</option>
</c:otherwise>
</c:choose>
</c:forEach>
</select>

    <br><br>

    <!-- 時間 -->
<label>時間：</label>
<select name="time" required>
<%
            LocalTime open = (LocalTime) request.getAttribute("openTime");
            LocalTime close = (LocalTime) request.getAttribute("closeTime");
            if (open != null && close != null) {
                LocalTime t = open;
                while (!t.isAfter(close.minusMinutes(30))) {
        %>
<option value="<%=t%>"><%=t%></option>
<%
                    t = t.plusMinutes(30);
                }
            }
        %>
</select>

    <br><br>

    <!-- 人数 -->
<label>人数：</label>
<select id="numPeople" name="num_people" onchange="updateQuantityLimit()">
<c:forEach var="i" begin="1" end="8">
<option value="${i}">${i}人</option>
</c:forEach>
</select>

    <br><br>

    <!-- 名前・電話番号 -->
<label>予約者名：</label>
<input type="text" name="customerName" value="${customerName}" required>

    <br><br>

    <label>電話番号：</label>
<span>${customerTel}</span>
<input type="hidden" name="customerTel" value="${customerTel}">

    <br><br>

    <!-- ===== メニュー一覧 ===== -->
<h3>コース一覧</h3>

    <div class="menu-list">
<c:forEach var="menu" items="${menus}">
<div class="menu-card"
                 data-id="${menu.menuId}"
                 data-name="${menu.menuName}"
                 data-price="${menu.price}"
                 data-info="${menu.info}"
                 data-img="${pageContext.request.contextPath}/${menu.imagePath}"
                 onclick="openModalFromCard(this)">

                <div class="menu-title">
<span class="check-mark" id="check-${menu.menuId}">✔</span>
<strong>${menu.menuName}</strong>
</div>

                <c:if test="${not empty menu.imagePath}">
<img class="menu-img"
                         src="${pageContext.request.contextPath}/${menu.imagePath}?t=${System.currentTimeMillis()}"
                         width="120">
</c:if>

                <div>${menu.price}円</div>
<div>個数: <span id="qty-${menu.menuId}">0</span></div>

                <input type="hidden"
                       name="menu_${menu.menuId}"
                       id="input-${menu.menuId}"
                       value="0">
</div>
</c:forEach>
</div>

    <br>

    <div id="totalPriceBox">
        合計金額：<span id="totalPrice">0</span>円
</div>

    <br>

    <button type="submit">予約確認へ</button>
</form>

<!-- ===== モーダル ===== -->
<div id="menuModal" class="modal" onclick="closeModal()">
<div class="modal-content" onclick="event.stopPropagation();">
<span class="close" onclick="closeModal()">×</span>

        <h3 id="modalMenuName"></h3>
<img id="modalMenuImg" style="max-width:200px; margin-bottom:10px;">
<p id="modalMenuPrice"></p>
<p id="modalMenuInfo"></p>

        <label>個数:
<select id="modalQty"></select>


        <button onclick="applyQuantity()">決定</button></label>
</div>
</div>

<script>
let currentMenuId = null;
let menuData = {}; // menuId → {price, qty}

function openModalFromCard(card) {
    openModal(
        card.dataset.id,
        card.dataset.name,
        parseInt(card.dataset.price),
        card.dataset.info,
        card.dataset.img
    );
}

function openModal(id, name, price, info, imgPath) {
    currentMenuId = id;
    if (!menuData[currentMenuId]) menuData[currentMenuId] = { price: price, qty: 0 };

    document.getElementById("modalMenuName").textContent = name;
    document.getElementById("modalMenuPrice").textContent = price + "円";
    document.getElementById("modalMenuInfo").textContent = info || "";

    const img = document.getElementById("modalMenuImg");
    if (imgPath && !imgPath.includes("null")) {
        img.src = imgPath;
        img.style.display = "block";
    } else img.style.display = "none";

    createQtyOptions();
    document.getElementById("modalQty").value = menuData[currentMenuId].qty;

    document.getElementById("menuModal").style.display = "flex";
}

function createQtyOptions() {
    const limit = parseInt(document.getElementById("numPeople").value);
    let otherSum = 0;
    for (let id in menuData) if (id !== currentMenuId) otherSum += menuData[id].qty;

    const maxQty = Math.max(0, limit - otherSum);
    const select = document.getElementById("modalQty");
    select.innerHTML = "";
    for (let i = 0; i <= maxQty; i++) {
        const opt = document.createElement("option");
        opt.value = i;
        opt.textContent = i;
        select.appendChild(opt);
    }
}

function applyQuantity() {
    const qty = parseInt(document.getElementById("modalQty").value);
    menuData[currentMenuId].qty = qty;
    document.getElementById("qty-" + currentMenuId).textContent = qty;
    document.getElementById("check-" + currentMenuId).style.display = qty > 0 ? "inline" : "none";
    document.getElementById("input-" + currentMenuId).value = qty;
    updateTotal();
    closeModal();
}

function updateQuantityLimit() {
    const limit = parseInt(document.getElementById("numPeople").value);
    let total = 0;
    for (let id in menuData) total += menuData[id].qty;

    if (total > limit) {
        for (let id in menuData) {
            if (total <= limit) break;
            const diff = Math.min(menuData[id].qty, total - limit);
            menuData[id].qty -= diff;
            total -= diff;
            document.getElementById("qty-" + id).textContent = menuData[id].qty;
            document.getElementById("check-" + id).style.display = menuData[id].qty > 0 ? "inline" : "none";
            document.getElementById("input-" + id).value = menuData[id].qty;
        }
    }
    updateTotal();
}

function updateTotal() {
    let total = 0;
    for (let id in menuData) total += menuData[id].price * menuData[id].qty;
    document.getElementById("totalPrice").textContent = total;
}

function closeModal() {
    document.getElementById("menuModal").style.display = "none";
}

/* ===== ここから人数とメニュー個数のバリデーション ===== */
document.getElementById("reserveForm").onsubmit = function() {
    let totalQty = 0;
    for (let id in menuData) totalQty += menuData[id].qty;

    const numPeople = parseInt(document.getElementById("numPeople").value, 10);
    const halfPeople = Math.ceil(numPeople / 2);

    if (totalQty < halfPeople) {
        alert("人数の半分以上のメニューを選んでください。");
        return false;
    }

    if (totalQty < numPeople) {
        const proceed = confirm("メニューの数と人数が一致していませんが、予約しますか？");
        if (!proceed) return false;
    }

    return true;
};
</script>

<style>
.menu-list { display:flex; flex-wrap:wrap; gap:16px; }
.menu-card { width:160px; padding:8px; background:#9e9e9e; border-radius:8px; text-align:center; cursor:pointer; }
.menu-title { display:flex; justify-content:center; align-items:center; gap:6px; }
.check-mark { color:green; font-size:18px; display:none; }
#totalPriceBox { font-size:1.1em; font-weight:bold; }
.modal { display:none; position:fixed; inset:0; background:rgba(0,0,0,0.6); justify-content:center; align-items:center; }
.modal-content { background:#c0c0c0; padding:20px; border-radius:10px; width:320px; text-align:center; position:relative; }
.close { font-size:22px; cursor:pointer; position:absolute; top:10px; left:10px; }
</style>

<%@ include file="../footer.html"%>