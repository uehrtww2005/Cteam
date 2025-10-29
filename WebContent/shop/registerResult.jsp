<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>

<h1>店舗登録結果</h1>

<%
    // RegisterAction から送られる msg（リクエスト属性）を取得
    String msg = (String) request.getAttribute("msg");
    if (msg == null || msg.isEmpty()) {
        msg = "処理が完了しました。";
    }
%>

<p style="font-weight:bold; font-size:1.2em;"><%= msg %></p>

<!-- ログインページへのリンク -->
<p><a href="<%=request.getContextPath()%>/Adpay/login.jsp">ログインページへ</a></p>

<%@ include file="../footer.html" %>
