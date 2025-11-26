package Adpay;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Store;
import dao.StoreDAO;
import tool.Action;

public class UserStoreSearchAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String keyword = request.getParameter("keyword");
        StoreDAO dao = new StoreDAO();

        List<Store> stores;

        if (keyword != null && !keyword.isEmpty()) {
            stores = dao.search(keyword);   // OK: List<Store>
        } else {
            stores = dao.findAll();         // OK: List<Store>
        }

        request.setAttribute("stores", stores);

        RequestDispatcher rd =
                request.getRequestDispatcher("/user/users_main.jsp");
        rd.forward(request, response);
    }
}
