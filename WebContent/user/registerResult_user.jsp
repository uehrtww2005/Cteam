<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/register_result.css">

<div class="resultcontainer">

	<div class="success-icon">✓</div>

    <h1>ユーザー登録結果</h1>

    <%
        // RegisterAction から送られる msg（リクエスト属性）を取得
        String msg = (String) request.getAttribute("msg");
        if (msg == null || msg.isEmpty()) {
            msg = "処理が完了しました。";
        }
    %>

    <p class="result-message"><%= msg %></p>

    <!-- ログインページへのリンク -->
    <p>
        <a class="result-link"
           href="<%=request.getContextPath()%>/user/login_user.jsp">
            ログインページへ
        </a>
    </p>

</div>

<%@ include file="../footer.html" %>
