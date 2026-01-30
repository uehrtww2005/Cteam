<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<style>
/* =========================
   タイトル
========================= */
h1 {
    font-size: 36px;
    color: #fff;
    margin-bottom: 30px;
    border-bottom: 2px solid #FFD700;
    display: inline-block;
    padding-bottom: 5px;
}

/* =========================
   レイアウト
========================= */
.store-main {
    margin-left: 220px;
    max-width: 900px;
    margin-right: auto;
    padding: 30px;
}

.store-main form {
    margin-top: 20px;
    max-width: 500px;
}

.store-main label {
    display: block;
    margin-bottom: 4px;
    font-weight: bold;
    color: #fff;
}

/* =========================
   入力フォーム
========================= */
.store-main input[type="text"],
.store-main textarea,
.store-main select {
    width: 100%;
    padding: 6px;
    box-sizing: border-box;
    margin-bottom: 14px;

    background-color: #000;
    color: #fff;
    border: 1px solid #555;
}

/* =========================
   クーポン専用ボタン
========================= */
.coupon-btn {
    appearance: none;
    -webkit-appearance: none;

    margin-top: 25px;
    background-color: #FFD700;
    color: #ffffff;
    font-weight: bold;
    font-size: 16px;

    padding: 12px 0;
    width: 100%;

    border: none;
    border-radius: 8px;

    cursor: pointer;

    background: linear-gradient(145deg, #222, #111);
    border: 1px solid #666;
    box-shadow: 0 4px 10px rgba(0,0,0,0.5);
    transition: 0.3s;
}

.coupon-btn:hover {
    background: linear-gradient(145deg, #333, #000);
    color: #FFD700;
    border-color: #FFD700;
    transform: translateY(-2px);
    box-shadow: 0 0 15px rgba(255,215,0,0.3);
}

.coupon-btn.danger {
    background: linear-gradient(145deg, #d32f2f, #8b0000);
    border: 1px solid #ff4d4d;
    color: #fff;
}

.coupon-btn.danger:hover {
    background: linear-gradient(145deg, #ff4d4d, #b30000);
    border-color: #ff8080;
    box-shadow: 0 0 15px rgba(255,0,0,0.4);
}

/* 削除用 */
.coupon-btn.danger {
    background-color: #c62828;
    color: #fff;
}

.coupon-btn.danger:hover {
    background-color: #e53935;
}

/* =========================
   テーブル
========================= */
.store-main table {
    width: 100%;
    border-collapse: collapse;
    table-layout: auto;
    margin-top: 20px;
}

.store-main th,
.store-main td {
    border: 1px solid #aaa;
    padding: 15px;
    text-align: center;
    font-size: 13px;
}

.store-main table th:nth-child(3),
.store-main table td:nth-child(3) {
    min-width: 30px;   /* 好きな幅に調整OK */
    white-space: nowrap; /* 改行防止 */
}

.store-main th {
    background-color: #111;
    color: #fff;
}

.action-buttons {
    margin-top: 20px;
    display: flex;
    gap: 10px;
}
</style>

<div class="store-main">

    <h1>クーポン管理</h1>

    <!-- メッセージ -->
    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>

    <!-- ======================
         クーポン追加
    ======================= -->
    <form action="CouponInsert.action" method="post">
        <input type="hidden" name="store_id" value="${store.storeId}">

        <label>クーポン名</label>
        <input type="text" name="new_coupon_name" required placeholder="クーポン名を入力してください">

        <label>ランク</label>
        <select name="new_coupon_rank" required>
            <option value="" disabled selected>選択してください</option>
            <option value="ビギナー">ビギナー</option>
            <option value="ブロンズ">ブロンズ</option>
            <option value="シルバー">シルバー</option>
            <option value="ゴールド">ゴールド</option>
        </select>

        <label>説明</label>
        <textarea name="new_coupon_introduct" rows="3" required
                  placeholder="例：60分飲み放題のクーポンです。"></textarea>

        <button type="submit" class="coupon-btn">追加する</button>
    </form>

    <hr>

    <!-- ======================
         クーポン一覧
    ======================= -->
    <h2>登録済みクーポン一覧</h2>

    <c:if test="${empty couponList}">
        <p>登録されているクーポンはありません。</p>
    </c:if>

    <c:if test="${not empty couponList}">
        <form id="couponForm" method="post">
            <table>
                <tr>
                    <th><input type="checkbox" id="checkAll"></th>
                    <th>クーポン名</th>
                    <th>ランク</th>
                    <th>説明</th>
                </tr>

                <c:forEach var="c" items="${couponList}">
                    <tr>
                        <td>
                            <input type="checkbox" name="couponIds" value="${c.couponId}">
                        </td>
                        <td>${c.couponName}</td>
                        <td>${c.couponRank}</td>
                        <td>${c.couponIntroduct}</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="action-buttons">
                <button type="submit" class="coupon-btn"
                        onclick="return sendCoupons();">
                    選択したクーポンを配布
                </button>

                <button type="submit" class="coupon-btn danger"
                        onclick="return deleteCoupons();">
                    選択したクーポンを削除
                </button>
            </div>
        </form>
    </c:if>

</div>

<script>
/* 全選択 */
document.getElementById("checkAll")?.addEventListener("change", function () {
    document.querySelectorAll("input[name='couponIds']")
        .forEach(c => c.checked = this.checked);
});

function checkedAny() {
    return document.querySelectorAll("input[name='couponIds']:checked").length > 0;
}

function sendCoupons() {
    if (!checkedAny()) {
        alert("クーポンを選択してください");
        return false;
    }
    if (!confirm("選択したクーポンをすべてのユーザーに配布しますか？")) {
        return false;
    }
    document.getElementById("couponForm").action = "CouponSendAll.action";
    return true;
}

function deleteCoupons() {
    if (!checkedAny()) {
        alert("削除するクーポンを選択してください");
        return false;
    }
    if (!confirm("選択したクーポンを削除しますか？")) {
        return false;
    }
    document.getElementById("couponForm").action = "CouponBulkDelete.action";
    return true;
}
</script>

<%@ include file="../footer.html" %>
