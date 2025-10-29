package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Store;
import dao.StoreDAO;
import tool.Action;

public class StoreLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


        // フォームから送信されたメールアドレスとパスワードを取得
        String storetel = request.getParameter("store_tel");
        String password = request.getParameter("password");

        // DAOでログイン認証
        StoreDAO dao = new StoreDAO();
        Store store = dao.login(storetel, password);

        if (store != null) {
            // ログイン成功 → セッションにユーザー情報を保存
        	HttpSession session = request.getSession();
        	session.setAttribute("store", store);
        	request.setAttribute("store", store);


            // ログイン成功ページへフォワード
            request.getRequestDispatcher("/shop/hoge.jsp").forward(request, response);

        } else {
            System.out.println("ログイン失敗：フォワード実行");
            request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています");
            request.getRequestDispatcher("/shop/login_in.jsp").forward(request, response);
        }

    }
}
