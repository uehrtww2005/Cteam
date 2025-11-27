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

        // フォームから送信された電話番号とパスワードを取得
        String storetel = request.getParameter("store_tel");
        String password = request.getParameter("password");

        // セッションを取得（存在しない場合は新規作成）
        HttpSession session = request.getSession();

        // すでにログイン済みなら再認証をスキップ
        Store sessionStore = (Store) session.getAttribute("store");
        if (sessionStore != null) {
            // 既にログイン中の店舗 → ホームへ直接遷移
            request.setAttribute("store", sessionStore);
            request.getRequestDispatcher("/shop/store_home.jsp").forward(request, response);
            return;
        }

        // DAOでログイン認証
        StoreDAO dao = new StoreDAO();
        Store store = dao.login(storetel, password);

        if (store != null) {
            // ★追加: 利用停止チェック
            if (store.getStatus() == 1) {
                request.setAttribute("msg", "この店舗アカウントは現在利用停止されています。管理者にお問い合わせください。");
                request.getRequestDispatcher("/shop/login_store.jsp").forward(request, response);
                return; // 処理を中断
            }

            // ログイン成功＆利用可能な場合のみセッション保存
            session.setAttribute("store", store);
            session.setAttribute("role", "store");
            // JSP側でも使えるようにセット
            request.setAttribute("store", store);

            // 店舗用ホームへフォワード
            request.getRequestDispatcher("/shop/store_home.jsp").forward(request, response);

        } else {
            System.out.println("ログイン失敗：フォワード実行");
            request.setAttribute("msg", "電話番号またはパスワードが間違っています");
            request.getRequestDispatcher("/shop/login_store.jsp").forward(request, response);
        }
    }
}