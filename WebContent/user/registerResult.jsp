<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>ユーザー登録結果</h1>

<%
    // RegisterActionから送られる msg パラメータを取得
    String msg = request.getParameter("msg");
    if (msg == null || msg.isEmpty()) {
        msg = "処理が完了しました。";
    }
%>

<p style="font-weight:bold; font-size:1.2em;"><%= msg %></p>

<!-- ログインページへのリンク -->
<p><a href="<%=request.getContextPath()%>/Adpay/login.jsp">ログインページへ</a></p>


<%@ include file="../footer.html" %>
