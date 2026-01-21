package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Store;
import dao.UserCouponDAO;
import tool.Action;

public class CouponSendAllAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Store store = (Store) session.getAttribute("store");

        // ログインチェック
        if (store == null) {
            req.setAttribute("message", "ログインしてください。");
            req.getRequestDispatcher("/shop/login_store.jsp").forward(req, res);
            return;
        }

        int storeId = store.getStoreId();

        // チェックされたクーポンID
        String[] couponIds = req.getParameterValues("couponIds");

        if (couponIds == null || couponIds.length == 0) {
            req.setAttribute("message", "配布するクーポンを選択してください。");
            req.getRequestDispatcher("/shop/store_coupon.jsp").forward(req, res);
            return;
        }

        UserCouponDAO dao = new UserCouponDAO();

        int totalUserCount = 0;
        int totalGroupCount = 0;

        // クーポンごとに配布
        for (String idStr : couponIds) {
            int couponId = Integer.parseInt(idStr);

            // 個人ユーザーへ配布
            totalUserCount += dao.sendToAllUsers(couponId, storeId);

            // 団体ユーザーへ配布
            totalGroupCount += dao.sendToAllGroups(couponId, storeId);
        }

        session.setAttribute("message", "クーポンを配布しました");

        res.sendRedirect("CouponPage.action");


    }
}
