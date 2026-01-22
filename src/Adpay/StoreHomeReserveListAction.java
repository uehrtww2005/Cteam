package Adpay;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Reserve;
import bean.Store;
import dao.ReserveDAO;
import tool.Action;

public class StoreHomeReserveListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        // ★ ログイン中の店舗を取得
        Store store = (Store) session.getAttribute("store");
        if (store == null) {
            response.sendRedirect(request.getContextPath() + "/shop/login_store.jsp");
            return;
        }

        // ★ 日付取得（未指定なら当日）
        String dateStr = request.getParameter("date");
        LocalDate date = (dateStr == null || dateStr.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(dateStr);

        ReserveDAO reserveDao = new ReserveDAO() {};

        // ★ 店舗＋日付で予約取得
        List<Reserve> reserveList =
                reserveDao.findByStoreAndDate(store.getStoreId(), date);

        // JSPへ渡す
        request.setAttribute("reserveList", reserveList);
        request.setAttribute("selectedDate", date);

        request.getRequestDispatcher("/shop/store_home.jsp")
               .forward(request, response);
    }
}
