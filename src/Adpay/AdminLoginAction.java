package Adpay;

import javax.servlet.http.*;
import bean.Admin;
import dao.AdminDAO;
import tool.Action;

public class AdminLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        // GETでアクセスした場合
        if (request.getMethod().equalsIgnoreCase("GET")) {
            if (session != null && "admin".equals(session.getAttribute("role"))) {
                // ログイン済み → ホーム画面を表示（フォワードでもOK）
                request.getRequestDispatcher("/admin/adminhome.jsp").forward(request, response);
            } else {
                // 未ログイン → ログイン画面を表示
                request.getRequestDispatcher("/admin/login_admin.jsp").forward(request, response);
            }
            return;
        }

        // POSTでログインフォーム送信時
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");

        if (adminName == null || password == null ||
            adminName.isEmpty() || password.isEmpty() ||
            !adminName.matches("^[A-Za-z0-9ぁ-んァ-ヶ一-龥ーａ-ｚＡ-Ｚ０-９]+$") ||
            !password.matches("^[A-Za-z0-9ａ-ｚＡ-Ｚ０-９]+$")) {

            request.setAttribute("error", "名前またはパスワードの形式が正しくありません。");
            request.getRequestDispatcher("/admin/login_admin.jsp").forward(request, response);
            return;
        }

        AdminDAO dao = new AdminDAO();
        Admin admin = dao.login(adminName, password);

        if (admin != null) {
            session = request.getSession();
            session.setAttribute("admin", admin);
            session.setAttribute("role", "admin");
            request.getRequestDispatcher("/admin/adminhome.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "名前またはパスワードが間違っています。");
            request.getRequestDispatcher("/admin/login_admin.jsp").forward(request, response);
        }
    }
}
