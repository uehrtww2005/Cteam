package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false); // 既存セッションを取得（新しく作らない）

        // ログイン種別判定用の変数
        String userType = "guest"; // デフォルト（未ログイン状態）

        if (session != null) {
            // どの種類のユーザーがログインしていたか判定
            if (session.getAttribute("user") != null) {
                userType = "user";
            } else if (session.getAttribute("shop") != null) {
                userType = "shop";
            } else if (session.getAttribute("admin") != null) {
                userType = "admin";
            }

            // セッションを完全破棄
            session.invalidate();
        }

        // 種別ごとにリダイレクト先を分ける
        String redirectPath;
        switch (userType) {
            case "user":
                redirectPath = "/Adpay/login.jsp";
                break;
            case "shop":
                redirectPath = "/Adpay/shop/login.jsp";
                break;
            case "admin":
                redirectPath = "/Adpay/login.jsp";
                break;
            default:
                redirectPath = "/Adpay/login.jsp";
                break;
        }

        // 遷移実行
        response.sendRedirect(request.getContextPath() + redirectPath);
    }
}
