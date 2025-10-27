<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>


    <h1>AdPay</h1>

    <!-- 管理者ログイン -->
    <form action="<%=request.getContextPath()%>/admin/Login.action" method="get">
        <button>管理者</button>
    </form>

    <!-- 専用・一般ログイン -->
    <form action="<%=request.getContextPath()%>/shop/Login.action" method="get">
        <button type="submit">専用</button>
    </form>

    <form action="<%=request.getContextPath()%>/user/Login.action" method="get">
        <button type="submit">一般</button>
    </form>

    <!-- 登録リンク -->
    <p><a href="<%=request.getContextPath()%>/user/Register.action">一般の新規登録はこちら</a></p>
    <p><a href="<%=request.getContextPath()%>/shop/Register.action">店舗専用の新規登録はこちら</a></p>

<%@include file="../footer.html" %>