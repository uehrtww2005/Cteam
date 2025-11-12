<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.html" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>登録完了</title>
  <style>
    body {
      background-color: #121212; /* 黒ベース */
      color: #f5f5f5;
      font-family: "Yu Gothic", "Hiragino Sans", sans-serif;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .msg {
      font-size: 28px;
      font-weight: bold;
      color: #f8f8f8;
      text-shadow: 0 0 10px rgba(255, 215, 0, 0.4);
      margin-bottom: 20px;
      animation: fadeIn 1.5s ease-in-out;
    }

    .loading {
      color: #d4af37; /* 金色 */
      font-size: 16px;
      opacity: 0.8;
      animation: blink 1.8s infinite;
    }

    /* フェードイン */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    /* 点滅アニメーション */
    @keyframes blink {
      0%, 100% { opacity: 0.6; }
      50% { opacity: 1; }
    }

    /* 金のボーダーライン */
    .line {
      width: 60%;
      height: 2px;
      background: linear-gradient(to right, transparent, #d4af37, transparent);
      margin: 25px 0;
      animation: lineGlow 3s ease-in-out infinite;
    }

    @keyframes lineGlow {
      0%, 100% { opacity: 0.6; }
      50% { opacity: 1; }
    }

  </style>
  <script>
    let countdown = 5; // 秒数
    function updateCountdown() {
      document.getElementById("countdown").innerText = countdown;
      if (countdown === 0) {
        // 0秒になったら一覧に遷移
        window.location.href = "<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store_id}";
      } else {
        countdown--;
        setTimeout(updateCountdown, 1000); // 1秒ごとに更新
      }
    }
    window.onload = updateCountdown;
  </script>
</head>
<body>
  <div class="msg">✨ ${msg} ✨</div>
  <div class="line"></div>
  <div class="loading">
    <span id="countdown">5</span>秒後にメニュー一覧に戻ります...
  </div>
</body>
</html>
<%@ include file="../footer.html" %>
