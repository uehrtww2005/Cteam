<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>

<!-- ユーザーでログインしている場合 -->
<c:if test="${not empty user}">
    <p>${user.address}</p>
</c:if>

<!-- グループリーダーでログインしている場合 -->
<c:if test="${not empty group}">
    <p>${group.leaderAddress}</p>
</c:if>

<%@ include file="../footer.html" %>
