<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/user_inquiry.css">
<html>
<head>
    <title>お問い合わせ</title>
</head>
<body>
<div class="inquiry-main">

    <h2>お問い合わせフォーム</h2>

    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form action="<%=request.getContextPath()%>/Adpay/UserInquiry.action" method="post">

        <label>電話番号（ハイフンあり）：</label>
        <input type="text" name="tel" required>

        <label>お問い合わせ内容：</label>
        <textarea name="content" rows="5" required></textarea>

        <button type="submit">送信</button>
    </form>

    <div class="form-links">
        <a href="javascript:history.back();">ホームに戻る</a>
    </div>

</div>


</body>
</html>
<%@ include file="../footer.html" %>