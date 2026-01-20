<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>先払い率 設定</title>

<style>
/* =========================
   先払い率 設定ページ用CSS
========================= */

/* タイトル */
h2 {
  margin-top: 40px;
  font-size: 28px;
  text-align: center;
}

/* フォーム全体 */
form {
  margin: 30px auto;
  padding: 30px 40px;
  width: 360px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  text-align: center;
}

/* ラベル */
form label {
  font-size: 22px;
  font-weight: bold;
  display: block;
  margin-bottom: 10px;
}

/* セレクト（← ここが重要） */
select[name="prepayment_rate"] {
  font-size: 22px;      /* 文字を大きく */
  padding: 10px 16px;   /* 高さを出す */
  width: 100%;
  border-radius: 8px;
  border: 1px solid #aaa;
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

/* メッセージ */
.success-msg {
  color: green;
  font-weight: bold;
  text-align: center;
  margin-bottom: 15px;
}

/* 戻るリンク */
.back-link {
  margin-top: 20px;
  text-align: center;
}
</style>

</head>
<body>

<h2>先払い率 設定</h2>

<c:if test="${not empty msg}">
  <div class="success-msg">
    ${msg}
  </div>
</c:if>

<form action="<%=request.getContextPath()%>/Adpay/StorePrepayment.action"
      method="post">

  <label>先払い率：</label>

  <select name="prepayment_rate" required>
    <option value="">-- 選択してください --</option>

    <c:forEach var="i" begin="1" end="5">
      <option value="${i}"
        <c:if test="${setting != null && setting.prepaymentRate == i}">
          selected
        </c:if>
      >
        ${i}割
      </option>
    </c:forEach>
  </select>

  <button type="submit">設定する</button>
</form>

<div class="back-link">
  <a href="<%=request.getContextPath()%>/Adpay/StoreLogin.action">
    店舗トップへ戻る
  </a>
</div>

<%@ include file="../footer.html" %>

</body>
</html>
