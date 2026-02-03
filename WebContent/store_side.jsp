<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_side.css">

<div class="sidebar">
    <a href="<%=request.getContextPath()%>/Adpay/StoreHomeReserveList.action"class="sidebar-link">トップ</a>
    <a href="<%=request.getContextPath()%>/Adpay/StorePrepayment.action" class="sidebar-link">先払い率設定</a>
    <a href="<%=request.getContextPath()%>/Adpay/MenuRegist.action?store_id=${store.storeId}" class="sidebar-link">コース表</a>
    <a href="<%=request.getContextPath()%>/Adpay/StoreDetailEdit.action?store_id=${store.storeId}" class="sidebar-link">店舗情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/CouponPage.action?store_id=${store.storeId}" class="sidebar-link">クーポン情報</a>
    <a href="<%=request.getContextPath()%>/Adpay/StoreInquiry.action" class="sidebar-link">お問い合わせ</a>


    <a href="<%=request.getContextPath()%>/Adpay/StoreReserveList.action?store_id=${store.storeId}"
	   class="sidebar-link">
	  予約者一覧
	</a>
</div>
