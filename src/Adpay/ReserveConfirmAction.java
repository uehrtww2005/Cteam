package Adpay;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Menu;
import bean.StorePrepayment;   // ← 追加
import dao.MenuDAO;
import dao.SeatDAO;
import dao.StorePrepaymentDAO; // ← 追加
import tool.Action;

public class ReserveConfirmAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 予約基本情報
        int storeId = Integer.parseInt(request.getParameter("store_id"));
        String date = request.getParameter("date");
        int seatId = Integer.parseInt(request.getParameter("seat_id"));
        String time = request.getParameter("time");
        int numPeople = Integer.parseInt(request.getParameter("num_people"));
        String customerName = request.getParameter("customerName");
        String customerTel = request.getParameter("customerTel");

        // ===== メニュー情報を取得（hidden input の値から） =====
        MenuDAO menuDao = new MenuDAO();
        List<Menu> allMenus = menuDao.findByStoreId(storeId);

        Map<Integer, Integer> selectedMenuMap = new LinkedHashMap<>();
        List<Menu> selectedMenus = new ArrayList<>();

        int totalAmount = 0; // ← 合計金額計算用

        for (Menu menu : allMenus) {
            String paramName = "menu_" + menu.getMenuId();
            String value = request.getParameter(paramName);
            if (value != null) {
                int qty = Integer.parseInt(value);
                if (qty > 0) { // 数量が 0 より大きければ選択メニューとして登録
                    selectedMenuMap.put(menu.getMenuId(), qty);
                    selectedMenus.add(menu);

                    totalAmount += menu.getPrice() * qty; // ← 合計金額に加算
                }
            }
        }

        // ===== 席情報 =====
        SeatDAO seatDao = new SeatDAO();
        request.setAttribute("seat", seatDao.getSeatById(seatId));

        // ===== 先払い金額計算 =====
        StorePrepaymentDAO spDao = new StorePrepaymentDAO();
        StorePrepayment sp = spDao.findByStoreId(storeId);

        int prepaymentAmount = 0;
        if (sp != null) {
            // 先払い率 *10 /100 の計算
            prepaymentAmount = totalAmount * sp.getPrepaymentRate() * 10 / 100;
        }

        // ===== JSP に渡す値 =====
        request.setAttribute("storeId", storeId);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("numPeople", numPeople);
        request.setAttribute("customerName", customerName);
        request.setAttribute("customerTel", customerTel);

        request.setAttribute("selectedMenus", selectedMenus);
        request.setAttribute("menuCounts", selectedMenuMap);
        request.setAttribute("prepaymentAmount", prepaymentAmount); // ← 追加

        // ===== JSP にフォワード =====
        request.getRequestDispatcher("/shop/reserve_confirm.jsp").forward(request, response);
    }
}
