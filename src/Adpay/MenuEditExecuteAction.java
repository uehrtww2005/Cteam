package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Menu;
import dao.MenuDAO;
import tool.Action;

public class MenuEditExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // --- パラメータ取得 ---
        String menuIdStr = request.getParameter("menu_id");
        String menuName = request.getParameter("menu_name");
        String priceStr = request.getParameter("price");

        if (menuIdStr == null || menuIdStr.isEmpty()) {
            request.setAttribute("msg", "メニューIDが指定されていません。");
            request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
            return;
        }

        int menuId = Integer.parseInt(menuIdStr);
        int price = 0;

        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "価格は数値で入力してください。");
            request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
            return;
        }

        // --- DAOで更新 ---
        try {
            MenuDAO dao = new MenuDAO();
            Menu menu = dao.getMenuById(menuId);

            if (menu == null) {
                request.setAttribute("msg", "該当メニューが存在しません。");
                request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
                return;
            }

            // 更新内容セット
            menu.setMenuName(menuName);
            menu.setPrice(price);

            dao.update(menu);

            // メッセージ作成
            request.setAttribute("msg", "メニュー「" + menuName + "」を更新しました！");

            // 店舗IDを渡して一覧を再取得
            int storeId = menu.getStoreId();
            request.setAttribute("store_id", storeId);
            request.setAttribute("menuList", dao.findByStoreId(storeId));

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "メニュー更新に失敗しました。");
        }

        // --- 完了後は一覧ページに戻す ---
        request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
    }
}
