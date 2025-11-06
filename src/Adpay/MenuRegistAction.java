package Adpay;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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

    // 🟢 GET：一覧表示
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
            List<Menu> menuList = dao.findByStoreId(storeId);
            request.setAttribute("menuList", menuList);
            request.setAttribute("store_id", storeId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "メニュー一覧の取得に失敗しました。");
        }

        if (msg != null && !msg.isEmpty()) {
            request.setAttribute("msg", msg);
        }

        request.getRequestDispatcher("/shop/menu_list.jsp").forward(request, response);
    }

    // 🔴 POST：登録処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String menuName = request.getParameter("menu_name");
        String priceStr = request.getParameter("price");
        Part imagePart = request.getPart("menu_image");
        int storeId = Integer.parseInt(request.getParameter("store_id"));

        // --- 入力チェック ---
        if (menuName == null || menuName.trim().isEmpty()) {
            redirectWithMsg(response, "メニュー名を入力してください。", storeId);
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            redirectWithMsg(response, "価格は数値で入力してください。", storeId);
            return;
        }

        if (imagePart == null || imagePart.getSize() == 0) {
            redirectWithMsg(response, "画像を選択してください。", storeId);
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
            redirectWithMsg(response, "アップロードできる画像は jpg / jpeg / png のみです。", storeId);
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
            redirectWithMsg(response, "メニュー登録中にエラーが発生しました。", storeId);
            return;
        }

        // --- 🟢 即ファイル保存（ローカル直書き） ---
        if (menuId > 0 && imagePart.getSize() > 0) {
            // 保存先ディレクトリ（ローカル固定）
            String uploadDir = "C:" + File.separator + "Users" + File.separator + "sotu" + File.separator +
                               "git" + File.separator + "Cteam" + File.separator + "WebContent" +
                               File.separator + "shop" + File.separator + "store_menu_images";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // フォルダがなければ作成

            // 保存ファイル名：storeId_menuId.拡張子
            String fileName = storeId + "_" + menuId + "." + extension;
            File filePath = new File(dir, fileName);

            try {
                // 即保存（上書き対応）
                Files.copy(imagePart.getInputStream(), filePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                msg += " 画像も保存しました！";
            } catch (IOException e) {
                e.printStackTrace();
                msg += " ただし画像保存に失敗しました。";
            }
        }

        redirectWithMsg(response, msg, storeId);
    }

    // ✅ 共通メソッド
    private void redirectWithMsg(HttpServletResponse response, String msg, int storeId)
            throws IOException {
        String encodedMsg = URLEncoder.encode(msg, "UTF-8");
        response.sendRedirect("MenuRegist.action?store_id=" + storeId + "&msg=" + encodedMsg);
    }
}
