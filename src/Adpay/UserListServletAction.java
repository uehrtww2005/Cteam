package Adpay;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Group;
import bean.User;
import dao.GroupDAO;
import dao.UserDAO;
import tool.Action;

public class UserListServletAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ユーザー一覧を取得
        UserDAO userDao = new UserDAO();
        List<User> users = userDao.findAll();

        // 団体一覧を取得
        GroupDAO groupDao = new GroupDAO();
        List<Group> groups = groupDao.findAll();

        // リクエストスコープに保存
        request.setAttribute("userList", users);
        request.setAttribute("groupList", groups);

        System.out.println("UserListServletAction execute called!");


        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userList.jsp");
        dispatcher.forward(request, response);
    }
}
