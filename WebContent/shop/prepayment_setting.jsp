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

/* サイドバー前提のメイン領域 */
.main-content {
  margin-left: 220px;              /* ← store_side.jsp の幅 */
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;             /* ★ 見た目の中央 */
}

h2 {
    font-size: 28px;
    color: #fff;
    margin-bottom: 30px;
    border-bottom: 2px solid #FFD700;
    display: inline-block;
    padding-bottom: 5px;
}

/* タイトル */
.main-content h2 {
  margin-top: 40px;
  font-size: 28px;
  text-align: center;
}

/* フォーム全体 */
form {
  margin: 30px auto;
  padding: 30px 40px;
  width: 360px;
  box-sizing: border-box;           /* ★ 重要 */
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

/* セレクト */
select[name="prepayment_rate"] {
  font-size: 22px;
  padding: 12px 16px;
  width: 100%;
  box-sizing: border-box;

  /* なじませるポイント */
  background: rgba(0, 0, 0, 0.45);
  color: #ffffff;

  border: 1px solid #666;
  border-radius: 8px;

  outline: none;
  appearance: none;          /* デフォルト矢印を消す */
  -webkit-appearance: none;
  -moz-appearance: none;

  cursor: pointer;
}

/* フォーカス時（クリック中） */
select[name="prepayment_rate"]:focus {
  border-color: #FFD700;
  box-shadow: 0 0 0 2px rgba(255, 215, 0, 0.25);
}

/* optionの色（重要） */
select[name="prepayment_rate"] option {
  background: #111;
  color: #fff;
}

/* 送信ボタン */
form button {
  margin-top: 25px;
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
  background: linear-gradient(145deg, #222, #111);
  border: 1px solid #666;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0,0,0,0.5);
  transition: 0.3s;
}

form button:hover {
  background: linear-gradient(145deg, #333, #000);
  color: #FFD700;
  border-color: #FFD700;
  transform: translateY(-2px);
  box-shadow: 0 0 15px rgba(255,215,0,0.3);
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

<div class="main-content">

  <h2>先払い率 設定</h2>

  <c:if test="${not empty msg}">
    <div class="success-msg">${msg}</div>
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

</div>

<%@ include file="../footer.html" %>

</body>
</html>
