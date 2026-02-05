package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Reserve;
import bean.User;
import dao.ReserveDAO;
import tool.Action;

public class UserReserveListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 未ログイン対策（★ここだけ修正）
        if (user == null) {
            response.sendRedirect(
                request.getContextPath() + "/user/login_user.jsp"
            );
            return;
        }

        int userId = user.getUserId();

        ReserveDAO dao = new ReserveDAO() {};
        List<Reserve> reserveList = dao.findByUserId(userId);

        request.setAttribute("reserveList", reserveList);

        request.getRequestDispatcher("/user/user_reserve_list.jsp")
               .forward(request, response);
    }
}
