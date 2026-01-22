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

        String storetel = request.getParameter("store_tel");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        // すでにログイン済みなら予約一覧Actionへ
        Store sessionStore = (Store) session.getAttribute("store");
        if (sessionStore != null) {
            response.sendRedirect(
                request.getContextPath() + "/Adpay/StoreHomeReserveList.action"
            );
            return;
        }

        StoreDAO dao = new StoreDAO();
        Store store = dao.login(storetel, password);

        if (store != null) {

            // 利用停止チェック
            if (store.getStatus() == 1) {
                request.setAttribute("msg",
                    "この店舗アカウントは現在利用停止されています。管理者にお問い合わせください。");
                request.getRequestDispatcher("/shop/login_store.jsp")
                       .forward(request, response);
                return;
            }

            // セッション保存
            session.setAttribute("store", store);
            session.setAttribute("role", "store");

            // ★ JSPではなく予約一覧Actionへ
            response.sendRedirect(
                request.getContextPath() + "/Adpay/StoreHomeReserveList.action"
            );

        } else {
            request.setAttribute("msg", "電話番号またはパスワードが間違っています");
            request.getRequestDispatcher("/shop/login_store.jsp")
                   .forward(request, response);
        }
    }
}
