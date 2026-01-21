<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<style>
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
}

.store-main input[type="text"],
.store-main textarea,
.store-main select {
    width: 100%;
    padding: 6px;
    box-sizing: border-box;
    margin-bottom: 14px;
}

button {
    margin-top: 15px;
    background-color: #FFD700;
    color: #fff;
    font-weight: bold;
    font-size: 14px;
    padding: 10px 0;
    width: 100%;
    border: none;
    border-radius: 8px;
    cursor: pointer;
}

.store-main table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

.store-main th,
.store-main td {
    border: 1px solid #aaa;
    padding: 8px;
    text-align: center;
    font-size: 13px;
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
    <h2>クーポン追加</h2>

    <form action="CouponInsert.action" method="post">
        <input type="hidden" name="store_id" value="${store.storeId}">

        <label>クーポン名</label>
        <input type="text" name="new_coupon_name" required>

        <label>ランク</label>
        <select name="new_coupon_rank" required>
            <option value="" disabled selected>選択してください</option>
            <option value="ビギナー">ビギナー</option>
            <option value="ブロンズ">ブロンズ</option>
            <option value="シルバー">シルバー</option>
            <option value="ゴールド">ゴールド</option>
        </select>

        <label>説明</label>
        <textarea name="new_coupon_introduct" rows="3" required></textarea>

        <button type="submit">追加する</button>
    </form>

    <hr>

    <!-- ======================
         クーポン一覧 & 一括操作
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
                            <input type="checkbox"
                                   name="couponIds"
                                   value="${c.couponId}">
                        </td>
                        <td>${c.couponName}</td>
                        <td>${c.couponRank}</td>
                        <td>${c.couponIntroduct}</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="action-buttons">
                <button type="submit" onclick="return sendCoupons();">
                    選択したクーポンを配布
                </button>

                <button type="submit" onclick="return deleteCoupons();">
                    選択したクーポンを削除
                </button>
            </div>
        </form>
    </c:if>

</div>

<script>
/* 全選択 */
document.getElementById("checkAll")?.addEventListener("change", function () {
    const checks = document.querySelectorAll("input[name='couponIds']");
    checks.forEach(c => c.checked = this.checked);
});

function checkedAny() {
    return document.querySelectorAll("input[name='couponIds']:checked").length > 0;
}

function sendCoupons() {
    if (!checkedAny()) {
        alert("クーポンを選択してください");
        return false;
    }
    if (!confirm("選択したクーポンをすべてのユーザー（個人・団体）に配布しますか？")) {
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
