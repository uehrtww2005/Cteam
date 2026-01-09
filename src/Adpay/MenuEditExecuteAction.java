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

        String menuIdStr = request.getParameter("menu_id");
        String menuName  = request.getParameter("menu_name");
        String info      = request.getParameter("info"); // ★ 追加
        String priceStr  = request.getParameter("price");

        if (menuIdStr == null || menuIdStr.isEmpty()) {
            request.setAttribute("msg", "メニューIDが指定されていません。");
            request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
            return;
        }

        int menuId = Integer.parseInt(menuIdStr);

        int price;
        try {
            price = Integer.parseInt(priceStr);
            if (price < 0) {
                request.setAttribute("msg", "価格は0以上で入力してください。");
                request.getRequestDispatcher("/shop/menu_edit.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "価格は数値で入力してください。");
            request.getRequestDispatcher("/shop/menu_edit.jsp").forward(request, response);
            return;
        }

        MenuDAO dao = new MenuDAO();
        Menu menu = dao.getMenuById(menuId);

        if (menu == null) {
            request.setAttribute("msg", "該当メニューが存在しません。");
            request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
            return;
        }

        // --- 更新内容 ---
        menu.setMenuName(menuName);
        menu.setPrice(price);
        menu.setInfo(info); // ★ ここ重要

        dao.update(menu);

        // --- 完了画面用 ---
        int storeId = menu.getStoreId();
        request.setAttribute("msg", "メニュー「" + menuName + "」を更新しました！");
        request.setAttribute("store_id", storeId);
        request.setAttribute("menuList", dao.findByStoreId(storeId));

        // ★ 統一
        request.getRequestDispatcher("/shop/menu_complete.jsp")
               .forward(request, response);
    }
}
