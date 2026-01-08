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

	    String storeIdStr = req.getParameter("store_id");
	    int storeId = Integer.parseInt(storeIdStr);

	    String name = req.getParameter("new_coupon_name");
	    String rank = req.getParameter("new_coupon_rank");
	    String intro = req.getParameter("new_coupon_introduct");

	    // バリデーション
	    if (name == null || name.isEmpty()
	     || rank == null || rank.isEmpty()
	     || intro == null || intro.isEmpty()) {

	        req.setAttribute("error", "未入力項目があります");

	        // 表示用Actionに戻す
	        req.getRequestDispatcher(
	            "CouponPage.action?store_id=" + storeId
	        ).forward(req, res);
	        return; // ★ 必須
	    }

	    Coupon coupon = new Coupon();
	    coupon.setStoreId(storeId);
	    coupon.setCouponName(name);
	    coupon.setCouponRank(rank);
	    coupon.setCouponIntroduct(intro);

	    CouponDAO dao = new CouponDAO();
	    dao.insert(coupon, storeId);

	    System.out.println("クーポン追加完了: " + name);

	    // ★ ここが最大のポイント
	    res.sendRedirect(
	        "CouponPage.action?store_id=" + storeId
	    );
	}
}
