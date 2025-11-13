<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>AdPay</title>
<%@ include file="../header.html" %>
<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/choise.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
</head>
<body>

<main class="choice-container">
  <h1>ログイン選択</h1>

  <form action="<%=request.getContextPath()%>/user/login_user.jsp" method="post">
      <button type="submit">個人の方はこちら</button>
  </form>

  <form action="<%=request.getContextPath()%>/user/group/login_group.jsp" method="post">
      <button type="submit">団体の方はこちら</button>
  </form>

	<!-- フォーム外のリンク -->
	<div class="form-links">
	    <a href="javascript:history.back();">ホームに戻る</a>
	</div>

</main>

<%@ include file="../footer.html" %>

</body>
</html>
