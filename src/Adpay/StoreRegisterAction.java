package Adpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Store;
import dao.StoreDAO;

@WebServlet("/Adpay/StoreRegister.action")
public class StoreRegisterAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String storeName = request.getParameter("store_name");
        String password = request.getParameter("password");
        String storeAddres = request.getParameter("store_address");
        String tel = request.getParameter("store_tel");
        String passwordConfirm = request.getParameter("password_confirm");

        // 1. パスワードと確認用が一致するかチェック
           if (!password.equals(passwordConfirm)) {
               request.setAttribute("msg", "パスワードと確認用パスワードが一致しません");
               request.getRequestDispatcher("/user/register.jsp").forward(request, response);
               return;
           }

        Store store = new Store();
        store.setStoreName(storeName);
        store.setPassword(password);
        store.setStoreAddress(storeAddres);
        store.setStoreTel(tel);


        StoreDAO dao = new StoreDAO();
        boolean success = false;
        try {
            success = dao.save(store);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JSP に渡すメッセージ
        String msg = success ? "登録成功" : "登録失敗";

        // JSP に値を渡して内部転送（URLに出ない
        System.out.print("aa");
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/shop/registerResult.jsp").forward(request, response);
    }

}
