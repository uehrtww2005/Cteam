//package Adpay;
//
//import java.io.File;
//import java.io.IOException;
//// Partのインポートはそのまま
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part; // Partのインポートはそのまま
//
//import bean.Store;
//import dao.StoreDAO;
//
//@WebServlet("/Adpay/StoreRegister.action")
//@MultipartConfig(fileSizeThreshold = 1024 * 1024)
//public class StoreRegisterAction extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        // DB登録関連の処理はそのまま (省略)
//        // ...
//
//        String storeName = request.getParameter("store_name");
//        String password = request.getParameter("password");
//        String storeAddress = request.getParameter("store_address");
//        String tel = request.getParameter("store_tel");
//        String passwordConfirm = request.getParameter("password_confirm");
//        Part imagePart = request.getPart("store_image");
//
//        // パスワード確認処理はそのまま (省略)
//        // ...
//
//        Store store = new Store();
//        store.setStoreName(storeName);
//        store.setPassword(password);
//        store.setStoreAddress(storeAddress);
//        store.setStoreTel(tel);
//
//        StoreDAO dao = new StoreDAO();
//        int storeId = -1;
//
//        try {
//            storeId = dao.saveAndReturnId(store); // INSERTしてstore_id取得
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // --- 画像保存処理 ---
//        if (storeId > 0 && imagePart != null && imagePart.getSize() > 0) {
//
//            // 保存先パスをサーバー上の実パスで取得
//            String uploadDir = getServletContext().getRealPath("/shop/store_images");
//
//            // ★★★ 問題特定のためにパスを必ずコンソールに出力 ★★★
//            System.out.println("uploadDir (実パス): " + uploadDir);
//
//            // ディレクトリが存在しない場合は作成
//            File dir = new File(uploadDir);
//            if (!dir.exists()) {
//                boolean made = dir.mkdirs();
//                // ★ フォルダ作成の成否もコンソールに出力
//                System.out.println("フォルダ作成結果：" + made);
//            }
//
//            // ★ 画像の拡張子をアップロードされたファイル名から取得するように改善
//            String originalFileName = imagePart.getSubmittedFileName();
//            String extension = "";
//            int dotIndex = originalFileName.lastIndexOf('.');
//            if (dotIndex > 0) {
//                extension = originalFileName.substring(dotIndex);
//            }
//
//            // ファイル名を store_id と適切な拡張子 に
//            String fileName = storeId + extension;
//            String filePath = uploadDir + File.separator + fileName;
//
//            System.out.println("保存ファイル名：" + fileName);
//            System.out.println("保存先フルパス：" + filePath);
//
//            try {
//                imagePart.write(filePath);
//                System.out.println("画像保存完了！");
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("画像保存に失敗しました。");
//            }
//        }
//
//     // --- 結果表示 ---
//        String msg = (storeId > 0) ? "登録成功！画像も保存されました。" : "登録失敗";
//        request.setAttribute("msg", msg);
//        request.getRequestDispatcher("/shop/registerResult.jsp").forward(request, response);
//    }
//}

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
            // 元の登録画面に戻る
            request.getRequestDispatcher("/shop/register.jsp").forward(request, response);
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
            storeId = dao.saveAndReturnId(store); // INSERTしてstore_id取得
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 画像保存処理 ---
        if (storeId > 0 && imagePart != null && imagePart.getSize() > 0) {

            // ★★★ 修正箇所: getRealPath() を使用せず、指定された絶対パスに固定 ★★★
            // C:\Users\sotu\git\Cteam\WebContent\shop\store_images を構築
            String uploadDir = "C:" + File.separator + "Users" + File.separator + "sotu" + File.separator +
                               "git" + File.separator + "Cteam" + File.separator + "WebContent" +
                               File.separator + "shop" + File.separator + "store_images";

            // ★ グループ開発時の注意を促すログ
            System.out.println("【重要】画像保存先をローカルのGitリポジトリに固定しました。他のメンバー環境では修正が必要です！");
            System.out.println("uploadDir (Gitリポジトリパス): " + uploadDir);

            // ディレクトリが存在しない場合は作成
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean made = dir.mkdirs();
                System.out.println("フォルダ作成結果：" + made);
            }

            // 画像の拡張子をアップロードされたファイル名から取得
            String originalFileName = imagePart.getSubmittedFileName();
            String extension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalFileName.substring(dotIndex);
            }

            // ファイル名を store_id と適切な拡張子 に
            String fileName = storeId + extension;
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
        // registerResult.jsp にフォワードして結果を表示
        request.getRequestDispatcher("/shop/registerResult.jsp").forward(request, response);
    }
}
