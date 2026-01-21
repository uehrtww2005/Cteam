package Adpay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.Reserve;
import bean.User;
import dao.GroupDAO;
import dao.MenuDAO;
import dao.ReserveDAO;
import dao.StorePrepaymentDAO;
import dao.UserDAO;
import tool.Action;

public class ReserveCompleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 予約基本情報
        int storeId = Integer.parseInt(request.getParameter("store_id"));
        String date = request.getParameter("date");   // 2026-01-03
        String time = request.getParameter("time");   // 12:30
        int seatId = Integer.parseInt(request.getParameter("seat_id"));
        int numPeople = Integer.parseInt(request.getParameter("num_people"));
        String customerName = request.getParameter("customerName");
        String customerTel = request.getParameter("customerTel");

        // ===== メニュー情報 =====
        Map<Integer, Integer> menuMap = new LinkedHashMap<>();
        for (String key : request.getParameterMap().keySet()) {
            if (key.startsWith("menu_")) {
                int menuId = Integer.parseInt(key.substring(5));
                int qty = Integer.parseInt(request.getParameter(key));
                if (qty > 0) menuMap.put(menuId, qty);
            }
        }

        // 合計金額計算
        int totalAmount = 0;
        MenuDAO menuDao = new MenuDAO();
        for (int menuId : menuMap.keySet()) {
            totalAmount += menuDao.getMenuById(menuId).getPrice() * menuMap.get(menuId);
        }

        // 先払い計算
        int prepaymentAmount = 0;
        StorePrepaymentDAO spDao = new StorePrepaymentDAO();
        if (spDao.findByStoreId(storeId) != null) {
            int rate = spDao.findByStoreId(storeId).getPrepaymentRate();
            prepaymentAmount = totalAmount * rate * 10 / 100;
        }

        // ===== 予約日時を作成（★重要） =====
        LocalDate reserveDate = LocalDate.parse(date);
        LocalTime reserveTime = LocalTime.parse(time);
        LocalDateTime reservedAt = LocalDateTime.of(reserveDate, reserveTime);

        // Reserveオブジェクト
        Reserve r = new Reserve();
        r.setStoreId(storeId);
        r.setSeatId(seatId);
        r.setCustomerName(customerName);
        r.setCustomerTel(customerTel);
        r.setNumPeople(numPeople);
        r.setReservedAt(reservedAt);   // ★ 修正点
        r.setAdvancePay(prepaymentAmount);
        r.setTotalPay(totalAmount);

        // user / group
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        if (user != null) {
            r.setUserId(user.getUserId());
        } else if (group != null) {
            r.setGroupId(group.getGroupId());
        }

        // 保存
        ReserveDAO reserveDao = new ReserveDAO() {};
        reserveDao.insert(r);

        // ===== 先払い反映 =====
        int perPersonPrepaid = numPeople > 0 ? prepaymentAmount / numPeople : 0;

        if (user != null) {
            int newAmount = user.getPrepaidAmount() + perPersonPrepaid;
            user.setPrepaidAmount(newAmount);
            new UserDAO().updatePrepaidAmount(user.getUserId(), newAmount);
        } else if (group != null) {
            int newAmount = group.getPrepaidAmount() + perPersonPrepaid;
            group.setPrepaidAmount(newAmount);
            new GroupDAO().updatePrepaidAmount(group.getGroupId(), newAmount);
        }

        // 完了画面用
        session.setAttribute("totalAmount", totalAmount);
        session.setAttribute("prepaymentAmount", prepaymentAmount);
        session.setAttribute("perPersonPrepaid", perPersonPrepaid);
        session.setAttribute("customerName", customerName);
        session.setAttribute("date", date);
        session.setAttribute("time", time);
        session.setAttribute("storeId", storeId);

        request.getRequestDispatcher("/shop/reserve_complete.jsp")
               .forward(request, response);
    }
}
