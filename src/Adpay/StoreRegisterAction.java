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

        // --- DB登録 ---
        Store store = new Store();
        store.setStoreName(storeName);
        store.setPassword(password);
        store.setStoreAddress(storeAddress);
        store.setStoreTel(tel);

        StoreDAO dao = new StoreDAO();
        int storeId = -1;

        try {
            storeId = dao.saveAndReturnId(store);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 画像保存処理 ---
        if (storeId > 0 && imagePart != null && imagePart.getSize() > 0) {

            String uploadDir = "C:" + File.separator + "Users" + File.separator + "sotu" + File.separator +
                               "git" + File.separator + "Cteam" + File.separator + "WebContent" +
                               File.separator + "shop" + File.separator + "store_images";

            System.out.println("uploadDir (Gitリポジトリパス): " + uploadDir);

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 元のファイル名から拡張子取得
            String originalFileName = imagePart.getSubmittedFileName();
            String extension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalFileName.substring(dotIndex + 1).toLowerCase();
            }

            // ✅ 拡張子チェック（jpg, jpeg, png のみ許可）
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
                request.setAttribute("msg", "対応している画像形式は JPG, JPEG, PNG のみです。");
                request.getRequestDispatcher("/shop/register_store.jsp").forward(request, response);
                return;
            }

            // ファイル名を store_id + 拡張子 に設定
            String fileName = storeId + "." + extension;
            String filePath = uploadDir + File.separator + fileName;

            System.out.println("保存ファイル名：" + fileName);
            System.out.println("保存先フルパス：" + filePath);

            try {
                imagePart.write(filePath);
                System.out.println("画像保存完了！");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("画像保存に失敗しました。");
            }
        }

        // --- 結果表示 ---
        String msg = (storeId > 0) ? "登録成功！画像も保存されました。" : "登録失敗";
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/shop/registerResult_store.jsp").forward(request, response);
    }
}
