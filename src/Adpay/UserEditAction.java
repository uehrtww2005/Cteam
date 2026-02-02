package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.User;
import dao.GroupDAO;
import dao.UserDAO;
import tool.Action;

public class UserEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // ===== POST以外は表示のみ =====
        if (!req.getMethod().equalsIgnoreCase("POST")) {
            req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
            return;
        }

        // ===== 入力取得 =====
        String name = trim(req.getParameter("name"));
        String address = trim(req.getParameter("address"));
        String tel = trim(req.getParameter("tel"));
        String password = trim(req.getParameter("password"));
        String passwordConfirm = trim(req.getParameter("passwordConfirm"));

        // ===== 必須チェック =====
        if (isEmpty(name) || isEmpty(address) || isEmpty(tel)) {
            req.setAttribute("error", "必須項目が未入力です");
            req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
            return;
        }

        boolean changePassword = !isEmpty(password);

        // ===== パスワードチェック =====
        if (changePassword) {
            if (!password.equals(passwordConfirm)) {
                req.setAttribute("error", "パスワードが一致しません");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }
            if (!password.matches("^[A-Za-z0-9]+$") || password.length() < 6) {
                req.setAttribute("error", "パスワードは6文字以上の半角英数字で入力してください");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }
        }

        /* ==================================================
         * 個人ユーザー編集
         * ================================================== */
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            UserDAO dao = new UserDAO();

            // ===== 重複チェック（自分以外）=====
            if (!tel.equals(user.getUserTel()) && dao.isUserTelExists(tel)) {
                req.setAttribute("error", "この電話番号は既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            if (!address.equals(user.getAddress()) && dao.isEmailExists(address)) {
                req.setAttribute("error", "このメールアドレスは既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            if (changePassword && !password.equals(user.getPassword())
                    && dao.isPasswordExists(password)) {
                req.setAttribute("error", "このパスワードは既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            // ===== 更新 =====
            user.setUserName(name);
            user.setAddress(address);
            user.setUserTel(tel);
            if (changePassword) user.setPassword(password);

            dao.update(user);
            session.setAttribute("user", user);
        }

        /* ==================================================
         * 団体ユーザー編集
         * ================================================== */
        if (session.getAttribute("group") != null) {
            Group group = (Group) session.getAttribute("group");
            GroupDAO dao = new GroupDAO();

            // ===== 重複チェック（自分以外）=====
            if (!tel.equals(group.getLeaderTel()) && dao.isLeaderTelExists(tel)) {
                req.setAttribute("error", "この電話番号は既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            if (!address.equals(group.getLeaderAddress()) && dao.isLeaderAddressExists(address)) {
                req.setAttribute("error", "このメールアドレスは既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            if (changePassword && !password.equals(group.getPassword())
                    && dao.isPasswordExists(password)) {
                req.setAttribute("error", "このパスワードは既に使用されています");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            // ===== 更新 =====
            group.setLeaderName(name);
            group.setLeaderAddress(address);
            group.setLeaderTel(tel);
            if (changePassword) group.setPassword(password);

            dao.update(group);
            session.setAttribute("group", group);
        }

        req.setAttribute("message", "ユーザー情報を更新しました");
        req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
    }

    /* ===== ユーティリティ ===== */
    private String trim(String s) {
        return s == null ? null : s.trim();
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
