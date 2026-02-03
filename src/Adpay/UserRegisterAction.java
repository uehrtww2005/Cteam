package Adpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

@WebServlet("/Adpay/UserRegister.action")
public class UserRegisterAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");
        String email = request.getParameter("address");
        String tel = request.getParameter("user_tel");
        String genderStr = request.getParameter("gender");

        // ① パスワード確認
        if (password == null || !password.equals(passwordConfirm)) {
            request.setAttribute("msg", "パスワードと確認用パスワードが一致しません");
            request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
            return;
        }

        // ② 性別未選択チェック（★重要）
        if (genderStr == null) {
            request.setAttribute("msg", "性別を選んで下さい");
            request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
            return;
        }

        int gender;
        try {
            gender = Integer.parseInt(genderStr); // 1 / 2 / 0
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "性別を選んで下さい");
            request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();

        // ③ 重複チェック
        try {
            if (dao.isUserTelExists(tel)) {
                request.setAttribute("msg", "この電話番号は既に登録されています。");
                request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
                return;
            }

            if (dao.isEmailExists(email)) {
                request.setAttribute("msg", "このメールアドレスは既に登録されています。");
                request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
                return;
            }

            if (dao.isPasswordExists(password)) {
                request.setAttribute("msg", "このパスワードは既に使用されています。");
                request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "データベースチェック中にエラーが発生しました。");
            request.getRequestDispatcher("/user/register_user.jsp").forward(request, response);
            return;
        }

        // ④ Userオブジェクト作成
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setAddress(email);
        user.setUserTel(tel);
        user.setGender(gender);

        // ⑤ 登録処理
        boolean success;
        try {
            success = dao.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        // ⑥ 結果表示
        request.setAttribute("msg", success ? "登録成功" : "登録失敗");
        request.getRequestDispatcher("/user/registerResult_user.jsp").forward(request, response);
    }
}
