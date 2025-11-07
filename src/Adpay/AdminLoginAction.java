package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Admin;
import dao.AdminDAO;
import tool.Action;

public class AdminLoginAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("UTF-8");

        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");

        // --- 入力チェック ---
        if (adminName == null || password == null ||
            adminName.isEmpty() || password.isEmpty() ||
            !adminName.matches("^[A-Za-z0-9ぁ-んァ-ヶ一-龥ーａ-ｚＡ-Ｚ０-９]+$") ||
            !password.matches("^[A-Za-z0-9ａ-ｚＡ-Ｚ０-９]+$")) {

            request.setAttribute("error", "名前またはパスワードの形式が正しくありません。");
            request.getRequestDispatcher("/admin/login_in.jsp").forward(request, response);
            return;
        }

        // --- DB認証 ---
        AdminDAO dao = new AdminDAO();
        Admin admin = dao.login(adminName, password);

        if (admin != null) {
            // 認証成功 → ホーム画面へ
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            request.getRequestDispatcher("/admin/adminhome.jsp").forward(request, response);

        } else {
            // 認証失敗 → 同じ画面に戻してメッセージ表示
            request.setAttribute("error", "名前またはパスワードが間違っています。");
            request.getRequestDispatcher("/admin/login_in.jsp").forward(request, response);
        }
    }
}
