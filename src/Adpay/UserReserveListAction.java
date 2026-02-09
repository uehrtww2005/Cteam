package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.Reserve;
import bean.User;
import dao.ReserveDAO;
import tool.Action;

public class UserReserveListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        // user も group も未ログインの場合
        if (user == null && group == null) {
            response.sendRedirect(
                request.getContextPath() + "/user/login_user.jsp"
            );
            return;
        }

        ReserveDAO dao = new ReserveDAO() {};
        List<Reserve> reserveList = null;

        // user ログイン時
        if (user != null) {
            int userId = user.getUserId();
            reserveList = dao.findByUserId(userId);

        // group ログイン時
        } else if (group != null) {
            int groupId = group.getGroupId();
            reserveList = dao.findByGroupId(groupId);
        }

        request.setAttribute("reserveList", reserveList);

        request.getRequestDispatcher("/user/user_reserve_list.jsp")
               .forward(request, response);
    }
}
