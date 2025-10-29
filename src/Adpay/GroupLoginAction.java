package Adpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import dao.GroupDAO;

@WebServlet("/Adpay/GroupLogin.action")
public class GroupLoginAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String leaderAddress = request.getParameter("leader_address");
        String password = request.getParameter("password");

        GroupDAO dao = new GroupDAO();
        Group group = null;
        try {
            group = dao.login(leaderAddress, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (group != null) {

        	System.out.println("成功");
            // ログイン成功
            HttpSession session = request.getSession();
            session.setAttribute("group", group);
            request.setAttribute("group", group);

            request.getRequestDispatcher("/user/group/hoge.jsp").forward(request, response);
        } else {
            // 失敗
            request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています。");
            request.getRequestDispatcher("/user/group/login_group.jsp").forward(request, response);
        }
    }
}
