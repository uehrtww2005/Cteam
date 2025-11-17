<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html" %>

<h2>店舗情報（初回入力）</h2>

<form action="StoreUpdate.action" method="post">
    <input type="hidden" name="store_id" value="${store_id}">

    <table border="1">
        <tr>
            <th>営業時間</th>
            <td>
                <select name="start_time">
                    <c:forEach var="t" items="${times}">
                        <option value="${t}">${t}</option>
                    </c:forEach>
                </select>
                ～
                <select name="end_time">
                    <c:forEach var="t" items="${times}">
                        <option value="${t}">${t}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>定休日</th>
            <td><input type="text" name="store_close"></td>
        </tr>
        <tr>
            <th>店舗紹介</th>
            <td><textarea name="store_introduct"></textarea></td>
        </tr>
        <tr>
            <th>席情報</th>
            <td><input type="text" name="seat_detail"></td>
        </tr>
        <tr>
            <th>タグ</th>
            <td>
                <select name="selectedTag">
                    <option value="">-- 選択してください --</option>
                    <c:forEach var="tag" items="${allTags}">
                        <option value="${tag}">${tag}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>

    <button type="submit">登録</button>
</form>

<%@ include file="../footer.html" %>
