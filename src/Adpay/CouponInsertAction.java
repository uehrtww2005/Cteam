package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Coupon;
import dao.CouponDAO;
import tool.Action;

public class CouponInsertAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        int storeId = Integer.parseInt(req.getParameter("store_id"));
        String intro = req.getParameter("coupon_introduct");
        String rank = req.getParameter("coupon_rank");

        Coupon coupon = new Coupon();
        coupon.setStoreId(storeId);
        coupon.setCouponIntroduct(intro);
        coupon.setCouponRank(rank);

        CouponDAO dao = new CouponDAO();
        dao.insert(coupon, storeId);

        // 追加後に元の編集画面に戻る
        res.sendRedirect("StoreDetailEdit.action?store_id=" + storeId);
    }
}
