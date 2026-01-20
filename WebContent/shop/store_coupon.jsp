<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<style>
/* =============================
   メインレイアウト
============================= */
.store-main {
    margin-left: 220px;
    max-width: 900px;
    margin-right: auto;
    padding: 30px;

}

/* =============================
   フォーム共通
============================= */
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
.store-main textarea {
    width: 100%;
    padding: 6px;
    box-sizing: border-box;
    margin-bottom: 14px;
}

.store-main button {
    padding: 8px 20px;
}
/* 送信ボタン */
form button {
    margin-top: 25px;
    background-color: #FFD700 !important;
    color: #ffffff !important;
    font-weight: bold;
    font-size: 16px;
    padding: 12px 0;
    width: 100%;
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

/* =============================
   テーブル
============================= */
.store-main table {
    width: 100%;
    border-collapse: collapse;
}

.store-main th,
.store-main td {
    border: 1px solid #aaa;
    padding: 8px;
    text-align: center;
    font-size: 13px;
}
}

.store-main th {
    background-color: #111;
}

.store-main h1 {
    color: #fff;
    margin-bottom: 20px;
    padding-bottom: 8px;
    border-bottom: 2px solid #FFD700; /* 下線 */
    display: inline-block;
}
</style>

<div class="store-main">

    <h1>クーポン追加</h1>

    <!-- エラーメッセージ -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <!-- クーポン追加フォーム -->
    <form action="CouponInsert.action" method="post">
        <input type="hidden" name="store_id" value="${storeId}">

        <label for="couponName">クーポン名</label>
        <input type="text" id="couponName" name="new_coupon_name"placeholder="例：クーポン名を入力してください" required>

        <label for="couponRank">ランク</label>
		<select id="couponRank" name="new_coupon_rank" required>
		    <option value="" disabled selected>ランクを選択してください</option>
		    <option value="ビギナー">ビギナー</option>
		    <option value="ブロンズ">ブロンズ</option>
		    <option value="シルバー">シルバー</option>
		    <option value="ゴールド">ゴールド</option>
		</select>


        <label for="couponIntro">説明</label>
        <textarea id="couponIntro" name="new_coupon_introduct" rows="3" placeholder="例：飲み放題付きのクーポンです。" required></textarea>

        <button type="submit">追加する</button>
    </form>


    <hr>

    <h2>クーポン一覧</h2>

    <c:if test="${empty couponList}">
        <p>登録されているクーポンはありません</p>
    </c:if>

    <c:if test="${not empty couponList}">
        <table>
            <tr>
                <th>クーポン名</th>
                <th>ランク</th>
                <th>説明</th>
            </tr>

            <c:forEach var="c" items="${couponList}">
                <tr>
                    <td>${c.couponName}</td>
                    <td>${c.couponRank}</td>
                    <td>${c.couponIntroduct}</td>
                   	<td>

                   	</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <form action="CouponDelete.action" method="post">
       <button type="submit">削除する</button>
    </form>

</div>

<%@ include file="../footer.html" %>
