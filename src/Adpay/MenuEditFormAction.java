package Adpay;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Menu;
import dao.MenuDAO;
import tool.Action;

public class MenuEditFormAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // パラメータ取得
            String menuIdStr = request.getParameter("menu_id");
            if (menuIdStr == null || menuIdStr.isEmpty()) {
                request.setAttribute("msg", "メニューIDが指定されていません。");
                RequestDispatcher rd = request.getRequestDispatcher("/shop/menu_list.jsp");
                rd.forward(request, response);
                return;
            }

            int menuId = Integer.parseInt(menuIdStr);

            // DAOを使って対象メニューを取得
            MenuDAO dao = new MenuDAO();
            Menu menu = dao.getMenuById(menuId);

            if (menu == null) {
                request.setAttribute("msg", "メニューが見つかりません。");
                RequestDispatcher rd = request.getRequestDispatcher("/shop/menu_list.jsp");
                rd.forward(request, response);
                return;
            }

            // メニュー情報をリクエストにセット
            request.setAttribute("menu", menu);

            // 編集ページへフォワード
            RequestDispatcher rd = request.getRequestDispatcher("/shop/menu_edit.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "編集ページの読み込みに失敗しました。");
            RequestDispatcher rd = request.getRequestDispatcher("/shop/menu_list.jsp");
            rd.forward(request, response);
        }
    }
}
