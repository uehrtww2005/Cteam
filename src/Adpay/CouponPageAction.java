package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Coupon;
import dao.CouponDAO;
import tool.Action;

public class CouponPageAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    int storeId = Integer.parseInt(req.getParameter("store_id"));
	    req.setAttribute("storeId", storeId);

	    CouponDAO dao = new CouponDAO();
	    List<Coupon> couponList = dao.findByStoreId(storeId);

	    req.setAttribute("couponList", couponList);

	    req.getRequestDispatcher("/shop/store_coupon.jsp").forward(req, res);
	}

}
