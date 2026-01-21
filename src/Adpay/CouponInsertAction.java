package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Coupon;
import dao.CouponDAO;
import tool.Action;

public class CouponInsertAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(); // ★ 追加

        String storeIdStr = req.getParameter("store_id");
        int storeId = Integer.parseInt(storeIdStr);

        String name = req.getParameter("new_coupon_name");
        String rank = req.getParameter("new_coupon_rank");
        String intro = req.getParameter("new_coupon_introduct");

        // バリデーション
        if (name == null || name.isEmpty()
         || rank == null || rank.isEmpty()
         || intro == null || intro.isEmpty()) {

            // ★ session に入れる
            session.setAttribute("message", "未入力項目があります");

            res.sendRedirect(
                "CouponPage.action?store_id=" + storeId
            );
            return;
        }

        Coupon coupon = new Coupon();
        coupon.setStoreId(storeId);
        coupon.setCouponName(name);
        coupon.setCouponRank(rank);
        coupon.setCouponIntroduct(intro);

        CouponDAO dao = new CouponDAO();
        dao.insert(coupon, storeId);

        // ★ 成功メッセージ
        session.setAttribute("message", "クーポンを追加しました");

        // 一覧再表示
        res.sendRedirect(
            "CouponPage.action?store_id=" + storeId
        );
    }
}
