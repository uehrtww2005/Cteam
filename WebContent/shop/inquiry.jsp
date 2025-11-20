<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/inquiry2.css">
<!-- サイドバー読み込み -->
    <%@ include file="../store_side.jsp" %>
<html>
<head>
    <title>お問い合わせ</title>
</head>
<body>

<h2>お問い合わせフォーム</h2>

<c:if test="${not empty msg}">
    <div style="color: green; font-weight: bold;">${msg}</div>
</c:if>

<form action="<%=request.getContextPath()%>/Adpay/StoreInquiry.action" method="post">

    <label>電話番号（ハイフンあり）</label><br>
    <input type="text" name="tel" required placeholder="電話番号を入力してください"><br><br>

    <label>お問い合わせ内容</label><br>
    <textarea name="content" rows="5" cols="40" required placeholder="ご質問・ご要望・ご相談内容をご記入ください"	></textarea><br><br>

    <button type="submit">送信</button>
</form>

<div class="form-links">
    <a href="javascript:history.back();">ホームに戻る</a>
</div>

</body>
</html>
<%@ include file="../footer.html" %>