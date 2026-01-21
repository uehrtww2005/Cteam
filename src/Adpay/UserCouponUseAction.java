package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.User;
import dao.UserCouponDAO;
import tool.Action;

public class UserCouponUseAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        if (user == null && group == null) {
            session.setAttribute("message", "ログインしてください。");
            res.sendRedirect("login_choise.jsp");
            return;
        }

        int userCouponId = Integer.parseInt(
            req.getParameter("user_coupon_id")
        );

        UserCouponDAO dao = new UserCouponDAO();
        dao.markAsUsed(userCouponId);

        session.setAttribute("message", "クーポンを使用しました。");

        // 一覧に戻る
        res.sendRedirect("UserCouponList.action");
    }
}
