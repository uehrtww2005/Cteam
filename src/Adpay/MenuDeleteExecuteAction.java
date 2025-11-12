package Adpay;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Menu;
import dao.MenuDAO;
import tool.Action;

public class MenuDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        String menuIdStr = request.getParameter("menu_id");
        Integer storeId = Integer.parseInt(request.getParameter("store_id")); // hiddenで渡す

        String msg = "";

        try {
            int menuId = Integer.parseInt(menuIdStr);
            MenuDAO dao = new MenuDAO();

            // 🔹 削除前にメニュー情報取得（名前・画像用）
            Menu menu = dao.getMenuById(menuId);

            int result = dao.delete(menuId, storeId);

            if (result > 0) {
                msg = "メニュー「" + menu.getMenuName() + "」を削除しました。";

                // 🔹 画像ファイル削除
                if (menu != null && menu.getImageExtension() != null && !menu.getImageExtension().isEmpty()) {
                    String uploadDir = "C:" + File.separator + "Users" + File.separator + "k_niwa"
                            + File.separator + "git" + File.separator + "Cteam" + File.separator + "WebContent"
                            + File.separator + "shop" + File.separator + "store_menu_images";

                    String fileName = storeId + "_" + menuId + "." + menu.getImageExtension();
                    File filePath = new File(uploadDir, fileName);
                    if (filePath.exists() && filePath.delete()) {
                        msg += "（画像も削除しました）";
                    }
                }

            } else {
                msg = "削除に失敗しました。";
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "削除中にエラーが発生しました。";
        }

        // メニュー一覧取得
        try {
            MenuDAO dao = new MenuDAO();
            List<Menu> menuList = dao.findByStoreId(storeId);
            request.setAttribute("menuList", menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("msg", msg);

        // フォワード先は menu_list.jsp
        request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
    }
}
