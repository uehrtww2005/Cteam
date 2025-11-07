package Adpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

@WebServlet("/Adpay/Register.action")
public class UserRegisterAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        String email = request.getParameter("address");
        String tel = request.getParameter("user_tel");
        String genderStr = request.getParameter("gender");
        String passwordConfirm = request.getParameter("password_confirm");

     // 1. パスワードと確認用が一致するかチェック
        if (!password.equals(passwordConfirm)) {
            request.setAttribute("msg", "パスワードと確認用パスワードが一致しません");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }


        int gender = 0;
        try {
            gender = Integer.parseInt(genderStr);
        } catch (Exception e) {}

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setAddress(email);
        user.setUserTel(tel);
        user.setGender(gender);

        UserDAO dao = new UserDAO();
        boolean success = false;
        try {
            success = dao.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JSP に渡すメッセージ
        String msg = success ? "登録成功" : "登録失敗";

        // JSP に値を渡して内部転送（URLに出ない）
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/user/registerResult.jsp").forward(request, response);
    }

}
