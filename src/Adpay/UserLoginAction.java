package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;

public class UserLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("manpi"); // 動作確認用

        // フォームから送信されたメールアドレスとパスワードを取得
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        // DAOでログイン認証
        UserDAO dao = new UserDAO();
        User user = dao.login(address, password);

        if (user != null) {
            // ログイン成功 → セッションにユーザー情報を保存
        	HttpSession session = request.getSession();
        	session.setAttribute("user", user);
        	request.setAttribute("user", user);


            // ログイン成功ページへフォワード
            request.getRequestDispatcher("/user/hoge.jsp").forward(request, response);

        } else {
            System.out.println("ログイン失敗：フォワード実行");
            request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています");
            request.getRequestDispatcher("/user/login_in.jsp").forward(request, response);
        }

    }
}
