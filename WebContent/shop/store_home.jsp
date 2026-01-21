<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../header.html" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_home.css?ver=4.0">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗トップ</title>
</head>

<body>

<%@ include file="../store_side.jsp" %>

<!-- ウェルカムアラート -->
<div id="welcome-alert">
  おかえりなさい、${store.storeName}様
</div>

<div class="store-home-container">
<div class="main-content">


<!-- =====================
   予約一覧（既存）
===================== -->
<div class="reserve-block">

<h2>予約確認</h2>

<form action="store_home" method="get" class="reserve-search">
  <label>予約日：</label>
  <input type="date" name="targetDate" value="${param.targetDate}">
  <select name="status">
    <option value="">全て</option>
    <option value="attention">要対応</option>
    <option value="normal">通常</option>
  </select>
  <button type="submit">表示</button>
</form>

<table class="reserve-table">
<thead>
<tr>
  <th>時間</th>
  <th>席</th>
  <th>名前</th>
  <th>人数</th>
  <th>電話</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${empty reserveList}">
<tr><td colspan="5" class="no-data">予約はありません</td></tr>
</c:when>
<c:otherwise>
<c:forEach var="r" items="${reserveList}">
<tr class="reserve-row" onclick="openDetail()">
  <td>${r.reservedAt}</td>
  <td>${r.seatName}</td>
  <td>${r.customerName}</td>
  <td>${r.numPeople}</td>
  <td>${r.customerTel}</td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

</div>
</div>

</div>
</div>

<!-- 予約詳細モーダル -->
<div id="reserve-modal" class="modal">
<div class="modal-content">
<h3>予約詳細</h3>
<p>※ 編集・キャンセルは今後対応</p>
<button onclick="closeDetail()">閉じる</button>
</div>
</div>

<script>
window.addEventListener('load', () => {
  const alert = document.getElementById('welcome-alert');
  setTimeout(() => alert.classList.add('show'), 300);
  setTimeout(() => alert.classList.remove('show'), 3300);
});

function openDetail(){
  document.getElementById('reserve-modal').style.display='flex';
}
function closeDetail(){
  document.getElementById('reserve-modal').style.display='none';
}
</script>

<%@ include file="../footer.html" %>
</body>
</html>
