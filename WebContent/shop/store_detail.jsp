<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.html" %>

<h2>店舗情報（編集）</h2>

<form action="StoreUpdate.action" method="post">
    <input type="hidden" name="store_id" value="${detail.storeId}">

    <table border="1">
        <tr>
            <th>営業時間</th>
            <td>
                <select name="start_time">
                    <c:forEach var="t" items="${times}">
                        <option value="${t}" <c:if test="${detail.storeHours != null && detail.storeHours.split(' ～ ')[0] eq t}">selected</c:if>>
                            ${t}
                        </option>
                    </c:forEach>
                </select>
                ～
                <select name="end_time">
                    <c:forEach var="t" items="${times}">
                        <option value="${t}" <c:if test="${detail.storeHours != null && detail.storeHours.split(' ～ ')[1] eq t}">selected</c:if>>
                            ${t}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>定休日</th>
            <td><input type="text" name="store_close" value="${detail.storeClose}"></td>
        </tr>
        <tr>
            <th>店舗紹介</th>
            <td><textarea name="store_introduct">${detail.storeIntroduct}</textarea></td>
        </tr>
        <tr>
            <th>席情報</th>
            <td><input type="text" name="seat_detail" value="${detail.seatDetail}"></td>
        </tr>
        <tr>
            <th>タグ</th>
            <td>
                <select name="selectedTag">
                    <option value="">-- 選択してください --</option>
                    <c:forEach var="tag" items="${allTags}">
                        <option value="${tag}" <c:if test="${detail.selectedTag != null && detail.selectedTag.tagName eq tag}">selected</c:if>>
                            ${tag}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>

    <button type="submit">保存</button>
</form>

<%@ include file="../footer.html" %>
