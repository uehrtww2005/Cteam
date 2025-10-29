package Adpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Group;
import dao.GroupDAO;

@WebServlet("/Adpay/GroupRegister.action")
public class GroupRegisterAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // JSPフォームから受け取るパラメータ
        String leaderName = request.getParameter("leader_name");
        String leaderAddress = request.getParameter("leader_address");
        String password = request.getParameter("password");
        String leaderTel = request.getParameter("leader_tel");
        String passwordConfirm = request.getParameter("password_confirm");

        // 1. パスワードと確認用が一致するかチェック
           if (!password.equals(passwordConfirm)) {
               request.setAttribute("msg", "パスワードと確認用パスワードが一致しません");
               request.getRequestDispatcher("/user/register.jsp").forward(request, response);
               return;
           }

        // Groupオブジェクト作成
        Group group = new Group();
        group.setLeaderName(leaderName);
        group.setLeaderAddress(leaderAddress);
        group.setPassword(password);
        group.setLeaderTel(leaderTel);

        // DAO呼び出し
        GroupDAO dao = new GroupDAO();
        boolean success = false;
        try {
            success = dao.save(group);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JSPに渡すメッセージ
        String msg = success ? "グループ登録に成功しました。" : "グループ登録に失敗しました。";

        // JSPに値を渡して内部転送（URLに出ない）
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/user/group/registerResult.jsp").forward(request, response);
    }
}
