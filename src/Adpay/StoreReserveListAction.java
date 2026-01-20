package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Reserve;
import bean.Store;
import dao.ReserveDAO;
import tool.Action;

public class StoreReserveListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        int storeId;

        // ▼ ① リンクから store_id を取得
        String storeIdStr = request.getParameter("store_id");

        if (storeIdStr != null) {
            storeId = Integer.parseInt(storeIdStr);
        } else {
            // ▼ ② 保険：セッションから取得
            Store store = (Store) session.getAttribute("store");

            if (store == null) {
                response.sendRedirect(request.getContextPath() + "/store/login.jsp");
                return;
            }
            storeId = store.getStoreId();
        }

        // ▼ 予約一覧取得
        ReserveDAO reserveDao = new ReserveDAO() {};

        List<Reserve> reserveList = reserveDao.ReserveByStoreId(storeId);

        // ▼ JSPへ渡す
        request.setAttribute("reserveList", reserveList);
        request.setAttribute("storeId", storeId);

        // ▼ 一覧画面へ
        request.getRequestDispatcher("/shop/store_reserve_list.jsp")
               .forward(request, response);
    }
}
