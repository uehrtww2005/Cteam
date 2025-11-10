<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>AdPay</title>

<!-- ファビコン -->
<%@ include file="../favicon.jsp" %>

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/top.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
</head>
<body>

<!-- ローディング画面 -->
<div id="loader">
    <img src="<%=request.getContextPath()%>/images/adpay.png" alt="Loading...">
</div>

<!-- 背景画像 -->
<div class="bg-image">
    <img src="<%=request.getContextPath()%>/images/泣いて喜ぶ板前.png" alt="背景">
</div>

<!-- 半透明オーバーレイ -->
<div class="bg-overlay"></div>

<!-- ヘッダー -->
<div id="header">
    <header>
        <a href="<%=request.getContextPath()%>/Adpay/login.jsp">
            <img src="<%=request.getContextPath()%>/images/adpay.png" alt="サービス画像">
        </a>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/Adpay/login.jsp">ホーム</a></li>
                <li><a href="#">サービス</a></li>
                <li><a href="#">料金プラン</a></li>
                <%
                    String role = (String) session.getAttribute("role");
                    if (role == null) {
                %>
                    <li><a href="<%=request.getContextPath()%>/admin/login_admin.jsp">管理者ログイン</a></li>
                <% } else { %>
                    <li><a href="<%=request.getContextPath()%>/Adpay/Logout.action">ログアウト</a></li>
                <% } %>
            </ul>
        </nav>
    </header>
</div>

<!-- メインコンテンツ -->
<main id="main-content">
    <h1>
        次世代の広告決済プラットフォーム<br>
        <span class="highlight">「AdPay」</span><br>
        広告と決済を、もっとシームレスに。
    </h1>
    <form action="<%=request.getContextPath()%>/shop/login_store.jsp" method="post">
        <button type="submit">店舗ログインの方はこちら</button>
    </form>
    <form action="<%=request.getContextPath()%>/Adpay/login_choise.jsp" method="post">
        <button type="submit">利用者の方はこちら</button>
    </form>
</main>

<!-- フッター -->
<div id="footer">
    <%@ include file="../footer.html" %>
</div>

<!-- JS: ローディング・フェードイン制御 -->
<script>
window.addEventListener("load", () => {
    const loader = document.getElementById("loader");
    const loaderImg = loader.querySelector("img");
    const bgImage = document.querySelector(".bg-image");
    const bgOverlay = document.querySelector(".bg-overlay");
    const header = document.getElementById("header");
    const main = document.getElementById("main-content");
    const footer = document.getElementById("footer");

    // ローディング画像フェードイン
    loaderImg.style.opacity = 1;

    // ローディング後に黒背景をフェードアウト
    setTimeout(() => {
        loader.style.transition = "opacity 1s ease";
        loader.style.opacity = 0;

        setTimeout(() => {
            loader.style.display = "none";

            // 背景画像 & オーバーレイを表示してフェードイン
            [bgImage, bgOverlay].forEach(el => {
                el.style.display = "block";
                setTimeout(() => el.style.opacity = 1, 50);
            });

            // ヘッダー、メイン、フッターを順にフェードイン
            setTimeout(() => {
                [header, main, footer].forEach(el => el.style.display = (el===header) ? "flex" : "block");
                setTimeout(() => header.style.opacity = 1, 0);
                setTimeout(() => main.style.opacity = 1, 200);
                setTimeout(() => footer.style.opacity = 1, 400);
            }, 500);

        }, 1000);
    }, 1500);
});
</script>

</body>
</html>
