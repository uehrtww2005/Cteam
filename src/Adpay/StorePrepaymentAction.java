package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Store;
import bean.StorePrepayment;
import dao.StorePrepaymentDAO;
import tool.Action;

public class StorePrepaymentAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        // ===== ログインチェック =====
        Store store = (Store) session.getAttribute("store");
        if (store == null) {
            response.sendRedirect(request.getContextPath() + "/shop/login_store.jsp");
            return;
        }

        int storeId = store.getStoreId();

        StorePrepaymentDAO dao = new StorePrepaymentDAO();

        // ===== POST（登録・更新）=====
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            String rateStr = request.getParameter("prepayment_rate");

            if (rateStr == null || rateStr.isEmpty()) {
                request.setAttribute("msg", "先払い率を選択してください。");
            } else {
                int rate = Integer.parseInt(rateStr);

                StorePrepayment sp = new StorePrepayment();
                sp.setStoreId(storeId);
                sp.setPrepaymentRate(rate);

                dao.save(sp);

                request.setAttribute(
                    "msg",
                    "先払い率を「" + rate + "割」に設定しました。"
                );
            }
        }

        // ===== 表示用取得 =====
        StorePrepayment setting = dao.findByStoreId(storeId);
        request.setAttribute("setting", setting);

        // JSPでも store を使えるように
        request.setAttribute("store", store);

        // ===== JSPへ =====
        request.getRequestDispatcher("/shop/prepayment_setting.jsp")
               .forward(request, response);
    }
}
