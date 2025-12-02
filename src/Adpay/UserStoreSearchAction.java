package Adpay;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Group;
import bean.Store;
import bean.User;
import dao.StoreDAO;
import tool.Action;

public class UserStoreSearchAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ▼ ログイン情報取得
        User loginUser = (User) request.getSession().getAttribute("user");
        Group loginGroup = (Group) request.getSession().getAttribute("group");

        String keyword = request.getParameter("keyword");
        StoreDAO dao = new StoreDAO();

        List<Store> stores;

        if (keyword != null && !keyword.isEmpty()) {
            stores = dao.search(keyword);
        } else {
            stores = dao.findAll();
        }

        // ▼ 店舗一覧をセッションに保存（JSP が sessionScope を使うため）
        request.getSession().setAttribute("stores", stores);

        // ▼ user / group もセッションに保存（これが無いと JSP に表示されない）
        if (loginUser != null) {
            request.getSession().setAttribute("user", loginUser);
        }
        if (loginGroup != null) {
            request.getSession().setAttribute("group", loginGroup);
        }

        // ▼ JSP へフォワード
        RequestDispatcher rd =
                request.getRequestDispatcher("/user/users_main.jsp");
        rd.forward(request, response);
    }
}
