<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>


    <h1>AdPay</h1>

    <!-- 管理者ログイン -->
    <form action="<%=request.getContextPath()%>/admin/login_in.jsp" method="get">
        <button>管理者</button>
    </form>

    <!-- 専用・一般ログイン -->
    <form action="<%=request.getContextPath()%>/shop/login_in.jsp" method="get">
        <button type="submit">店舗ログインの方はこちら</button>
    </form>
    <form action="<%=request.getContextPath()%>/user/login_in.jsp" method="get">
        <button type="submit">利用者の方はこちら</button>
    </form>


<%@include file="../footer.html" %>