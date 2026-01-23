<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.html" %>
<%@ include file="../user_side.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/users_main.css">

<div class="store-main">

    <h1>クーポン一覧</h1>

    <!-- メッセージ -->
    <c:if test="${not empty message}">
        <p style="color:red;">${message}</p>
    </c:if>

    <c:if test="${empty userCouponList}">
        <p>利用可能なクーポンはありません。</p>
    </c:if>

    <c:if test="${not empty userCouponList}">
        <table border="1" width="100%">
            <tr>
                <th>店舗</th>
                <th>クーポン名</th>
                <th>ランク</th>
                <th>説明</th>
                <th>状態</th>
            </tr>

            <c:forEach var="c" items="${userCouponList}">
                <tr>
                    <td>${c.storeName}</td>
                    <td>${c.couponName}</td>
                    <td>${c.couponRank}</td>
                    <td>${c.couponIntroduct}</td>

                    <td>
                        <c:if test="${c.used}">
                            <span style="color:gray;">使用済み</span>
                        </c:if>

                        <c:if test="${not c.used}">
                            <c:choose>

                                <c:when test="${loginRank == 'ゴールド'}">
                                    <form action="UserCouponUse.action" method="post"
                                          onsubmit="return confirmUse('${c.couponName}', ${c.userCouponId});">
                                        <input type="hidden" name="user_coupon_id" value="${c.userCouponId}">
                                        <button type="submit">使用する</button>
                                    </form>
                                </c:when>

                                <c:when test="${loginRank == 'シルバー'
                                    && (c.couponRank == 'ビギナー'
                                     || c.couponRank == 'ブロンズ'
                                     || c.couponRank == 'シルバー')}">
                                    <form action="UserCouponUse.action" method="post"
                                          onsubmit="return confirmUse('${c.couponName}', ${c.userCouponId});">
                                        <input type="hidden" name="user_coupon_id" value="${c.userCouponId}">
                                        <button type="submit">使用する</button>
                                    </form>
                                </c:when>

                                <c:when test="${loginRank == 'ブロンズ'
                                    && (c.couponRank == 'ビギナー'
                                     || c.couponRank == 'ブロンズ')}">
                                    <form action="UserCouponUse.action" method="post"
                                          onsubmit="return confirmUse('${c.couponName}', ${c.userCouponId});">
                                        <input type="hidden" name="user_coupon_id" value="${c.userCouponId}">
                                        <button type="submit">使用する</button>
                                    </form>
                                </c:when>

                                <c:when test="${loginRank == 'ビギナー'
                                    && c.couponRank == 'ビギナー'}">
                                    <form action="UserCouponUse.action" method="post"
                                          onsubmit="return confirmUse('${c.couponName}', ${c.userCouponId});">
                                        <input type="hidden" name="user_coupon_id" value="${c.userCouponId}">
                                        <button type="submit">使用する</button>
                                    </form>
                                </c:when>

                                <c:otherwise>
                                    <span style="color:red;">ランク不足</span>
                                </c:otherwise>

                            </c:choose>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>

<script>
function confirmUse(name, id) {
    if (!confirm("このクーポンを使用しますか？\n\n" + name)) {
        return false;
    }
    alert("クーポン使用完了\n" + "\n名前：" + name);
    return true;
}
</script>

<%@ include file="../footer.html" %>
