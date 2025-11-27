package Adpay;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Store;
import dao.StoreDAO;
import tool.Action;

public class StoreListServletAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DAOで店舗一覧を取得
        StoreDAO dao = new StoreDAO();
        List<Store> stores = dao.findAll();

        // リクエストスコープに保存
        request.setAttribute("stores", stores);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/storeList.jsp");
        dispatcher.forward(request, response);
    }
}
