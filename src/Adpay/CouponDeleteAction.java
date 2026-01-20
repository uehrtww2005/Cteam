package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Store;
import dao.CouponDAO;
import tool.Action;

public class CouponDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Store store = (Store) session.getAttribute("store");

        int storeId = store.getStoreId();

        // クーポンID取得
        int couponId = Integer.parseInt(req.getParameter("coupon_id"));

        // 削除処理
        CouponDAO dao = new CouponDAO();
        int result = dao.delete(couponId, storeId);

        if (result > 0) {
            req.setAttribute("message", "クーポンを削除しました。");
        } else {
            req.setAttribute("message", "削除に失敗しました。");
        }

        req.getRequestDispatcher("/shop/store_coupon.jsp").forward(req, res);
    }
}
