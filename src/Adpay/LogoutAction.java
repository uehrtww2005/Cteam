package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false); // ← 既存セッションを取得（新しく作らない）

        if (session != null) {
            session.invalidate(); // ✅ セッション完全破棄！
        }

        // 初期画面（ログインページ）に戻す
        response.sendRedirect(request.getContextPath() + "/Adpay/login.jsp");
    }
}
