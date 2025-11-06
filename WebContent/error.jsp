<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>エラー発生</title>
</head>
<body>
    <h1>システムエラーが発生しました</h1>
    <p>申し訳ありません。現在、システムで問題が発生しています。</p>

    <h3>エラー詳細：</h3>
    <pre><%= exception != null ? exception.toString() : "詳細情報はありません" %></pre>

    <p><a href="<%= request.getContextPath() %>/Adpay/login.jsp">トップページへ戻る</a></p>
</body>
</html>
