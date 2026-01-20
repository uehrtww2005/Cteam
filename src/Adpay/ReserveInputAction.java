package Adpay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.Menu;
import bean.Seat;
import bean.StoreCalendar;
import bean.User;
import dao.MenuDAO;
import dao.ReserveDAO;
import dao.SeatDAO;
import dao.StoreCalendarDAO;
import tool.Action;

public class ReserveInputAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int storeId = Integer.parseInt(request.getParameter("store_id"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));

        // カレンダーから営業時間を取得
        StoreCalendarDAO calDao = new StoreCalendarDAO();
        StoreCalendar cal = calDao.findByStoreAndDate(storeId, date);

        LocalTime openTime = null;
        LocalTime closeTime = null;
        if (cal != null && cal.isOpen()) {
            openTime = cal.getOpenTime().toLocalTime();
            closeTime = cal.getCloseTime().toLocalTime();
        }

        request.setAttribute("openTime", openTime);
        request.setAttribute("closeTime", closeTime);

        // 席一覧
        SeatDAO seatDao = new SeatDAO();
        List<Seat> seats = seatDao.getSeatsByStoreId(storeId);
        request.setAttribute("seats", seats);

        // 既存予約を取得
        ReserveDAO reserveDao = new ReserveDAO() {};
        List<Integer> reservedSeatIds = reserveDao.findReservedSeatIds(storeId, date);
        request.setAttribute("reservedSeatIds", reservedSeatIds);

        // セッションからログイン情報取得
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        if (user != null) {
            request.setAttribute("customerName", user.getUserName());
            request.setAttribute("customerTel", user.getUserTel());
        } else if (group != null) {
            request.setAttribute("customerName", group.getLeaderName());
            request.setAttribute("customerTel", group.getLeaderTel());
        }

        MenuDAO menuDao = new MenuDAO();
        List<Menu> menus = menuDao.findByStoreId(storeId);
        request.setAttribute("menus", menus);

        System.out.println("session loginUser: " + session.getAttribute("user"));
        System.out.println("session loginGroup: " + session.getAttribute("group"));

        request.setAttribute("storeId", storeId);
        request.setAttribute("date", date);

        // JSPへフォワード
        request.getRequestDispatcher("/shop/reserve_input.jsp")
               .forward(request, response);
    }
}
