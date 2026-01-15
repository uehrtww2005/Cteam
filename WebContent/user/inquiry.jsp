<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<%@ include file="../user_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/inquiry.css">

<html>
<head>
    <title>お問い合わせ</title>
</head>
<body>

<div class="main-content">

    <h2>お問い合わせフォーム</h2>

    <c:if test="${not empty msg}">
        <div style="color: green; font-weight: bold;">
            ${fn:trim(msg)}
        </div>
    </c:if>

    <form action="<%=request.getContextPath()%>/Adpay/UserInquiry.action" method="post">

        <label>電話番号（ハイフンあり）</label>
        <input type="text" name="tel" required
               placeholder="電話番号を入力してください">

        <label>お問い合わせ内容</label>
        <textarea name="content" rows="5" required
                  placeholder="ご質問・ご要望・ご相談内容をご記入ください"></textarea>

        <button type="submit">送信</button>
    </form>

    <div class="form-links">
        <a href="javascript:history.back();">ホームに戻る</a>
    </div>

</div>

</body>
</html>

<%@ include file="../footer.html" %>
