package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Coupon;
import bean.Store;
import dao.CouponDAO;
import tool.Action;

public class CouponPageAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Store store = (Store) session.getAttribute("store");

        // 未ログイン対策
        if (store == null) {
            res.sendRedirect("login_store.jsp");
            return;
        }

        int storeId = store.getStoreId();

        // クーポン一覧取得
        CouponDAO dao = new CouponDAO();
        List<Coupon> couponList = dao.findByStoreId(storeId);
        req.setAttribute("couponList", couponList);

        // フラッシュメッセージ処理
        Object message = session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }



        req.getRequestDispatcher("/shop/store_coupon.jsp").forward(req, res);
    }
}
