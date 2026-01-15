<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.html" %>
<%@ include file="../store_side.jsp" %>

<h2>先払い率 設定</h2>

<c:if test="${not empty msg}">
    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
        ${msg}
    </div>
</c:if>

<form action="<%=request.getContextPath()%>/Adpay/StorePrepayment.action"
      method="post">

    <label>先払い率：</label><br>

    <select name="prepayment_rate" required>
        <option value="">-- 選択してください --</option>

        <c:forEach var="i" begin="3" end="10">
            <option value="${i}"
                <c:if test="${setting != null && setting.prepaymentRate == i}">
                    selected
                </c:if>
            >
                ${i}割
            </option>
        </c:forEach>
    </select>

    <br><br>

    <button type="submit">設定する</button>
</form>

<div style="margin-top: 20px;">
    <a href="<%=request.getContextPath()%>/Adpay/StoreLogin.action">
        店舗トップへ戻る
    </a>
</div>

<%@ include file="../footer.html" %>
