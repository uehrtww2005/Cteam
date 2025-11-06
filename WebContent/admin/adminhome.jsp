<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<p>以下のメニューから管理項目を選択してください。</p>

<ul>
    <li><a href="<%=request.getContextPath()%>/Adpay/UserListServlet.action">利用者一覧</a></li>
    <li><a href="<%=request.getContextPath()%>/Adpay/StoreListServlet.action">店舗一覧</a></li>
</ul>

<%@include file="../footer.html" %>
