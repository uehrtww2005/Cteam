package Adpay;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Store;
import dao.StoreDAO;

@WebServlet("/Adpay/StoreRegister.action")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class StoreRegisterAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String storeName = request.getParameter("store_name");
        String password = request.getParameter("password");
        String storeAddress = request.getParameter("store_address");
        String tel = request.getParameter("store_tel");
        String passwordConfirm = request.getParameter("password_confirm");
        Part imagePart = request.getPart("store_image");

        // パスワード確認
        if (!password.equals(passwordConfirm)) {
            request.setAttribute("msg", "パスワードと確認用パスワードが一致しません");
            request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
            return;
        }

        // 拡張子準備
        String extension = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String originalFileName = imagePart.getSubmittedFileName();
            int dot = originalFileName.lastIndexOf('.');
            if (dot > 0) {
                extension = originalFileName.substring(dot + 1).toLowerCase();
            }

            // 許可拡張子チェック
            if (!("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension))) {
                request.setAttribute("msg", "対応している画像形式は JPG / JPEG / PNG のみです。");
                request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
                return;
            }
        }

        StoreDAO dao = new StoreDAO();

        // --- 重複チェック（店名は無視、電話番号・住所・パスワード） ---
        try {
            if (dao.isStoreTelExists(tel)) {
                request.setAttribute("msg", "この電話番号は既に登録されています。別の番号を入力してください。");
                request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
                return;
            }

            if (dao.isStoreAddressExists(storeAddress)) {
                request.setAttribute("msg", "この住所は既に登録されています。別の住所を入力してください。");
                request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
                return;
            }

            if (dao.isPasswordExists(password)) {
                request.setAttribute("msg", "このパスワードは既に使用されています。別のパスワードを入力してください。");
                request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "データベースチェック中にエラーが発生しました。");
            request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
            return;
        }

        // --- DB登録 ---
        Store store = new Store();
        store.setStoreName(storeName);
        store.setPassword(password);
        store.setStoreAddress(storeAddress);
        store.setStoreTel(tel);
        store.setImageExtension(extension);

        int storeId = -1;
        try {
            storeId = dao.saveAndReturnId(store);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "登録処理中にエラーが発生しました。");
            request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
            return;
        }

        // --- 画像保存 ---
        if (storeId > 0 && extension != null) {
            String uploadDir = "C:" + File.separator + "Users" + File.separator + "t_uehira" + File.separator +
                               "git" + File.separator + "Cteam" + File.separator + "WebContent" +
                               File.separator + "shop" + File.separator + "store_images";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = storeId + "." + extension;
            String filePath = uploadDir + File.separator + fileName;

            try {
                imagePart.write(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // --- 完了ページ ---
        String msg = (storeId > 0) ? "店舗登録に成功しました！" : "登録失敗";
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/shop/registerResult_store.jsp").forward(request, response);
    }
}
