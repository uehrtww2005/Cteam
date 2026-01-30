package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReserveDAO;
import tool.Action;

public class StoreReserveDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        // ▼ 削除対象の予約ID
        int reservationId =
            Integer.parseInt(request.getParameter("reservation_id"));

        // ▼ 削除処理
        ReserveDAO dao = new ReserveDAO() {};
        dao.delete(reservationId);

        // ▼ 削除後は一覧へ戻す
        response.sendRedirect(
            request.getContextPath() + "/Adpay/StoreReserveList.action"
        );
    }
}
