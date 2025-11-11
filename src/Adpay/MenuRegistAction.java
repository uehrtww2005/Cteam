package Adpay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Menu;
import dao.MenuDAO;

@WebServlet("/Adpay/MenuRegist.action")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class MenuRegistAction extends HttpServlet {

    // 🟢 GET：store_idごとのメニュー一覧表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String msg = request.getParameter("msg");
        String storeIdStr = request.getParameter("store_id");

        if (storeIdStr == null || storeIdStr.isEmpty()) {
            request.setAttribute("msg", "店舗情報が見つかりません。");
            request.getRequestDispatcher("/shop/store_home.jsp").forward(request, response);
            return;
        }

        int storeId = Integer.parseInt(storeIdStr);

        try {
            MenuDAO dao = new MenuDAO();
            List<Menu> menuList = dao.findByStoreId(storeId);  // 🟢 店舗ごとのメニュー取得
            request.setAttribute("menuList", menuList);
            request.setAttribute("store_id", storeId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "メニュー一覧の取得に失敗しました。");
        }

        if (msg != null && !msg.isEmpty()) {
            request.setAttribute("msg", msg);
        }

        // 🟢 menu_regist.jsp にフォワード（同ページで登録＋一覧を表示する）
        request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
    }

    // 🔴 POST：メニュー登録処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String menuName = request.getParameter("menu_name");
        String priceStr = request.getParameter("price");
        Part imagePart = request.getPart("menu_image");
        int storeId = Integer.parseInt(request.getParameter("store_id"));

        // --- 入力チェック ---
        if (menuName == null || menuName.trim().isEmpty()) {
            forwardToComplete(request, response, "メニュー名を入力してください。", storeId);
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            forwardToComplete(request, response, "価格は数値で入力してください。", storeId);
            return;
        }

        if (imagePart == null || imagePart.getSize() == 0) {
            forwardToComplete(request, response, "画像を選択してください。", storeId);
            return;
        }

        // --- 拡張子チェック ---
        String originalFileName = imagePart.getSubmittedFileName();
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex + 1).toLowerCase();
        }

        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
            forwardToComplete(request, response, "アップロードできる画像は jpg / jpeg / png のみです。", storeId);
            return;
        }

        // --- DB登録 ---
        Menu menu = new Menu();
        menu.setStoreId(storeId);
        menu.setMenuName(menuName);
        menu.setPrice(price);
        menu.setImageExtension(extension);

        MenuDAO dao = new MenuDAO();
        int menuId;
        String msg;

        try {
            menuId = dao.saveAndReturnId(menu, storeId);
            msg = "メニュー「" + menuName + "」を登録しました！";
        } catch (Exception e) {
            e.printStackTrace();
            forwardToComplete(request, response, "メニュー登録中にエラーが発生しました。", storeId);
            return;
        }

        // --- 画像ファイル保存 ---
        if (menuId > 0 && imagePart.getSize() > 0) {
            String uploadDir = "C:" + File.separator + "Users" + File.separator + "k_niwa" + File.separator +
                    "git" + File.separator + "Cteam" + File.separator + "WebContent" +
                    File.separator + "shop" + File.separator + "store_menu_images";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = storeId + "_" + menuId + "." + extension;
            File filePath = new File(dir, fileName);

            try {
                Files.copy(imagePart.getInputStream(), filePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                msg += " 画像も保存しました！";
            } catch (IOException e) {
                e.printStackTrace();
                msg += " ただし画像保存に失敗しました。";
            }
        }

        // 🟢 完了後、再度メニュー一覧を表示
        request.setAttribute("msg", msg);
        request.setAttribute("store_id", storeId);
        try {
            List<Menu> menuList = dao.findByStoreId(storeId);
            request.setAttribute("menuList", menuList);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", msg + "（一覧再取得に失敗）");
        }

        // 🟢 登録完了後も同じJSP（menu_regist.jsp）へ戻す
        request.getRequestDispatcher("/shop/menu_complete.jsp").forward(request, response);
    }

    // ✅ 旧：完了専用ページに飛ばしていた処理（不要なら削除OK）
    private void forwardToComplete(HttpServletRequest request, HttpServletResponse response, String msg, int storeId)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.setAttribute("store_id", storeId);
        // 🟢 一覧も表示できるように再利用
        try {
            MenuDAO dao = new MenuDAO();
            List<Menu> menuList = dao.findByStoreId(storeId);
            request.setAttribute("menuList", menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/shop/menu_complete.jsp").forward(request, response);
    }
}
