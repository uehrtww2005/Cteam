<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>AdPay</title>

<%@ include file="../header.html" %>

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin_home.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

</head>
<body>

<main class="menu-container">

    <h1>管理メニュー</h1>

    <p>以下のメニューから管理項目を選択してください。</p>

    <!-- 利用者一覧 -->
    <form action="<%=request.getContextPath()%>/Adpay/UserListServlet.action" method="get">
        <button type="submit">利用者一覧</button>
    </form>

    <!-- 店舗一覧 -->
    <form action="<%=request.getContextPath()%>/Adpay/StoreListServlet.action" method="get">
        <button type="submit">店舗一覧</button>
    </form>

    <form action="<%=request.getContextPath()%>/Adpay/InquiryList.action" method="get">
    	<button type="submit">問い合わせ保管庫</button>
    </form>

</main>

<%@ include file="../footer.html" %>

</body>
</html>
