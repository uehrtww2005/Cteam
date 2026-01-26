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

        HttpSession session = req.getSession();

        // ===== 入力取得 =====
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String tel = req.getParameter("tel");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("passwordConfirm");

        // null防止＆空白削除
        if (name != null) name = name.trim();
        if (address != null) address = address.trim();
        if (tel != null) tel = tel.trim();
        if (password != null) password = password.trim();
        if (passwordConfirm != null) passwordConfirm = passwordConfirm.trim();

     // フォーム送信かどうか判定
        if (req.getMethod().equalsIgnoreCase("POST")) {

            // ===== 必須チェック =====
            if (name == null || name.isEmpty() ||
                address == null || address.isEmpty() ||
                tel == null || tel.isEmpty()) {

                req.setAttribute("error", "必須項目が未入力です");
                req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                return;
            }

            // ===== パスワード確認 =====
            boolean changePassword = (password != null && !password.isEmpty());

            if (changePassword) {
                if (!password.equals(passwordConfirm)) {
                    req.setAttribute("error", "パスワードが一致しません");
                    req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                    return;
                }
                if (password.length() < 6) {
                    req.setAttribute("error", "パスワードは6文字以上にしてください");
                    req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);
                    return;
                }
            }

            /* ===== 個人ユーザー更新 ===== */
            if (session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");

                user.setUserName(name);
                user.setAddress(address);
                user.setUserTel(tel);

                if (changePassword) {
                    user.setPassword(password);
                }

                new UserDAO().update(user);
                session.setAttribute("user", user);
            }

            /* ===== 団体ユーザー更新 ===== */
            if (session.getAttribute("group") != null) {
                Group group = (Group) session.getAttribute("group");

                group.setLeaderName(name);
                group.setLeaderAddress(address);
                group.setLeaderTel(tel);

                if (changePassword) {
                    group.setPassword(password);
                }

                new GroupDAO().update(group);
                session.setAttribute("group", group);
            }

            req.setAttribute("message", "ユーザー情報を更新しました");
        }

        // 初回表示 or 更新後表示
        req.getRequestDispatcher("/user/user_edit.jsp").forward(req, res);

    }
}
