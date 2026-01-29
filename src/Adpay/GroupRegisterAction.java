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
            request.getRequestDispatcher("/user/group/register_group.jsp").forward(request, response);
            return;
        }

        GroupDAO dao = new GroupDAO();

        // 2. 重複チェック（電話番号・住所・パスワード）
        try {
            if (dao.isLeaderTelExists(leaderTel)) {
                request.setAttribute("msg", "この電話番号は既に登録されています。");
                request.getRequestDispatcher("/user/group/register_group.jsp").forward(request, response);
                return;
            }

            if (dao.isLeaderAddressExists(leaderAddress)) {
                request.setAttribute("msg", "この住所は既に登録されています。");
                request.getRequestDispatcher("/user/group/register_group.jsp").forward(request, response);
                return;
            }

            if (dao.isPasswordExists(password)) {
                request.setAttribute("msg", "このパスワードは既に使用されています。");
                request.getRequestDispatcher("/usergroup//register_group.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "データベースチェック中にエラーが発生しました。");
            request.getRequestDispatcher("/user/register_group.jsp").forward(request, response);
            return;
        }

        // 3. Groupオブジェクト作成
        Group group = new Group();
        group.setLeaderName(leaderName);
        group.setLeaderAddress(leaderAddress);
        group.setPassword(password);
        group.setLeaderTel(leaderTel);

        // 4. DAO呼び出し
        boolean success = false;
        try {
            success = dao.save(group);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. 結果メッセージ
        String msg = success ? "グループ登録に成功しました。" : "グループ登録に失敗しました。";

        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/user/group/registerResult_group.jsp").forward(request, response);
    }
}
