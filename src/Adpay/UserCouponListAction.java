package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.User;
import bean.UserCoupon;
import dao.UserCouponDAO;
import tool.Action;

public class UserCouponListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        UserCouponDAO dao = new UserCouponDAO();
        List<UserCoupon> couponList = null;

        /* =========================
           個人ユーザーの場合
        ========================= */
        if (user != null) {
            couponList = dao.findByUserId(user.getUserId());
            req.setAttribute("loginType", "user");
            req.setAttribute("loginRank", user.getRank());
        }

        /* =========================
           団体ユーザーの場合
        ========================= */
        else if (group != null) {
            couponList = dao.findByGroupId(group.getGroupId());
            req.setAttribute("loginType", "group");
            req.setAttribute("loginRank", group.getRank());
        }

        /* =========================
           未ログイン
        ========================= */
        else {
            req.setAttribute("message", "ログインしてください。");
            req.getRequestDispatcher("/Adpay/login_choise.jsp")
               .forward(req, res);
            return;
        }

        /* =========================
           JSPへ渡す
        ========================= */
        req.setAttribute("userCouponList", couponList);

        req.getRequestDispatcher("/user/user_coupon_list.jsp")
           .forward(req, res);
    }
}
