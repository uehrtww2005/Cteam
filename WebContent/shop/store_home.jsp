<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ include file="../header.html" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_home.css?ver=2.3">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<div class="store-home-container">

    <!-- サイドバー読み込み -->
    <%@ include file="../side.jsp" %>

    <!-- メインコンテンツ -->
    <div class="main-content">

        <!-- おかえりなさいブロック -->
        <div class="welcome-block">
            <h1>お帰りなさい、${store.storeName}様</h1>
        </div>

        <!-- お知らせブロック -->
        <div class="news-block">
            <h3>AdPayからのお知らせ</h3>
            <ul>
                <%
                   List<String> sampleNews = new ArrayList<>();
                   sampleNews.add("システムメンテナンスのお知らせ：11月15日 2:00〜5:00");
                   sampleNews.add("新機能追加：メニュー表の編集機能が改善されました");
                   sampleNews.add("新機能追加：俺馬鹿だからわかんねぇけどよ");

                   for (String news : sampleNews) {
                %>
                  <li><%= news %></li>
                <%
                   }
                %>
            </ul>
        </div>

    </div>

</div>

<%@ include file="../footer.html" %>
