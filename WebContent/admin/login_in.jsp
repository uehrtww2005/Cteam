<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  このファイルは「管理者ログイン画面」を表示する JSP ページです。
  管理者の名前とパスワードを入力し、LoginAction.java へ送信します。
--%>

<%-- ヘッダー部分（共通デザイン）を読み込む --%>
<%@ include file="../header.html" %>

<h1>管理者 ログイン</h1>

<%--
  フォームの action 属性には、LoginAction に対応する URL を指定。
  request.getContextPath() により、アプリ名（例: /Cteam）を動的に取得しておくことで、
  デプロイ先の環境が変わっても正しく動作する。
--%>
<form action="<%=request.getContextPath()%>/Adpay/Login.action" method="get">

    <%-- 管理者名の入力欄。Action側では getParameter("adminName") で受け取る。 --%>
    <p>名前 <input type="text" name="adminName"></p>

    <%-- パスワード入力欄。セキュリティ上、type="password" で非表示入力。 --%>
    <p>パスワード <input type="password" name="password"></p>

    <%-- 送信ボタン。押すと LoginAction.java が実行される。 --%>
    <p><input type="submit" value="ログイン"></p>
</form>

<%-- フッター部分（共通デザイン）を読み込む --%>
<%@ include file="../footer.html" %>

