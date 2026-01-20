package Adpay;

import java.time.LocalDateTime;
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
        String date = request.getParameter("date");
        int seatId = Integer.parseInt(request.getParameter("seat_id"));
        String time = request.getParameter("time");
        int numPeople = Integer.parseInt(request.getParameter("num_people"));
        String customerName = request.getParameter("customerName");
        String customerTel = request.getParameter("customerTel");

        // メニュー情報を hidden input から取得
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

        // Reserveオブジェクトにセット
        Reserve r = new Reserve();
        r.setStoreId(storeId);
        r.setSeatId(seatId);
        r.setCustomerName(customerName);
        r.setCustomerTel(customerTel);
        r.setNumPeople(numPeople);
        r.setReservedAt(LocalDateTime.now());
        r.setAdvancePay(prepaymentAmount);
        r.setTotalPay(totalAmount);

        // ★ userId / groupId をセット
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        if (user != null) {
            r.setUserId(user.getUserId());
        } else if (group != null) {
            r.setGroupId(group.getGroupId());
        }

        // DAOで保存
        ReserveDAO reserveDao = new ReserveDAO() {};
        reserveDao.insert(r);

        // ===== 先払いを User / Group に加算（人数で割った分を加算） =====
        int perPersonPrepaid = numPeople > 0 ? prepaymentAmount / numPeople : 0;

        if (user != null) {
            int newAmount = user.getPrepaidAmount() + perPersonPrepaid;
            user.setPrepaidAmount(newAmount);
            UserDAO userDao = new UserDAO();
            userDao.updatePrepaidAmount(user.getUserId(), newAmount);
        } else if (group != null) {
            int newAmount = group.getPrepaidAmount() + perPersonPrepaid;
            group.setPrepaidAmount(newAmount);
            GroupDAO groupDao = new GroupDAO();
            groupDao.updatePrepaidAmount(group.getGroupId(), newAmount);
        }

        // JSPに渡す値

        session.setAttribute("totalAmount", totalAmount);
        session.setAttribute("prepaymentAmount", prepaymentAmount);
        session.setAttribute("perPersonPrepaid", perPersonPrepaid);
        session.setAttribute("customerName", customerName);
        session.setAttribute("date", date);
        session.setAttribute("time", time);
        session.setAttribute("storeId", storeId);


        // 完了画面へ
        request.getRequestDispatcher("/shop/reserve_complete.jsp").forward(request, response);
    }
}
