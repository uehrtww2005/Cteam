package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Store;
import dao.CouponDAO;
import tool.Action;

public class CouponBulkDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Store store = (Store) session.getAttribute("store");

        if (store == null) {
            session.setAttribute("message", "ログインしてください。");
            res.sendRedirect("StoreLogin.action");
            return;
        }

        String[] couponIds = req.getParameterValues("couponIds");

        if (couponIds == null || couponIds.length == 0) {
            session.setAttribute("message", "削除するクーポンを選択してください。");
            res.sendRedirect("CouponPage.action");
            return;
        }

        CouponDAO dao = new CouponDAO();
        int deleteCount = 0;

        for (String idStr : couponIds) {
            int couponId = Integer.parseInt(idStr);
            deleteCount += dao.deleteCompletely(couponId, store.getStoreId());
        }

        // ★ ここが重要
        session.setAttribute("message", deleteCount + "件のクーポンを削除しました。");
        res.sendRedirect("CouponPage.action");
    }
}
