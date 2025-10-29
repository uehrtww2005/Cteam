<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../header.html" %>

<h1>AdPay</h1>

<!-- 個人・団体 -->
<form action="<%=request.getContextPath()%>/user/login_in.jsp" method="post">
    <button type="submit">個人の方はこちら</button>
</form>
<form action="<%=request.getContextPath()%>/user/group/login_group.jsp" method="post">
    <button type="submit">団体の方はこちら</button>
</form>

<p><input type="button" value="戻る" onclick="history.back();">

<%@include file="../footer.html" %>