package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Admin;
import dao.AdminDAO;
import tool.Action;

public class LoginAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // フォームから送信された「管理者名」と「パスワード」を取得
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");

        // DAO（データアクセスオブジェクト）を利用して、ログイン認証を行う
        AdminDAO dao = new AdminDAO();
        Admin admin = dao.login(adminName, password);

        // 動作確認用の出力（サーバーのコンソールに表示）
        System.out.println("unko");

        // 認証結果（adminオブジェクト）をリクエストスコープに保存
        // この情報はフォワード先の JSP（hoge.jsp）で利用できる
        request.setAttribute("admin", admin);

        // 認証結果を表示する JSP へ画面遷移
        // 「/」から始まるので WebContent 直下を基準に /admin/hoge.jsp を参照する
        request.getRequestDispatcher("/admin/adminhome.jsp").forward(request, response);
    }
}
