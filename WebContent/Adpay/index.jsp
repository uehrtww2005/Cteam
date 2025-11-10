<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	この JSP ファイルは、トップページ（例：index.jsp）として動作します。
	ユーザーが最初にアクセスしたとき、自動的にログインページ（login.jsp）へリダイレクトします。
--%>

<%
		// コンテキストパスを取得（例："/Cteam" のようなアプリケーション名）
		// これにより、アプリをサブディレクトリに配置しても正しく動作する。
		String contextPath = request.getContextPath();

		// login.jsp（Adpayフォルダ内）にリダイレクトする
		// ブラウザが新しいURLへ移動する
		response.sendRedirect(contextPath + "/Adpay/login.jsp");
%>
