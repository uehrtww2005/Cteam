<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.html" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>登録完了</title>
  <meta http-equiv="refresh" content="5; URL=<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store_id}">
  <style>
    body { text-align: center; font-family: "Yu Gothic", sans-serif; margin-top: 100px; }
    .msg { font-size: 20px; color: #444; margin-bottom: 20px; }
    .loading { color: #999; font-size: 14px; }
  </style>
</head>
<body>
  <div class="msg">${msg}</div>
  <div class="loading">5秒後にメニュー一覧に戻ります...</div>
</body>
</html>
<%@ include file="../footer.html" %>