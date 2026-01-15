package Adpay;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Store;
import bean.StoreCalendar;
import bean.StoreDetail;
import dao.StoreDAO;
import dao.StoreDetailDAO;
import tool.Action;

public class StoreDetailAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // store_id 必須
        String storeIdStr = request.getParameter("store_id");
        if (storeIdStr == null) {
            throw new IllegalArgumentException("store_id が指定されていません");
        }
        int storeId = Integer.parseInt(storeIdStr);

        // 年月（前月・次月対応）
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        if (request.getParameter("year") != null) {
            year = Integer.parseInt(request.getParameter("year"));
        }
        if (request.getParameter("month") != null) {
            month = Integer.parseInt(request.getParameter("month"));
        }

        // 0月・13月対策
        if (month == 0) {
            month = 12;
            year--;
        }
        if (month == 13) {
            month = 1;
            year++;
        }

        LocalDate firstDay = LocalDate.of(year, month, 1);

        // 店舗基本情報
        StoreDAO storeDAO = new StoreDAO();
        Store store = storeDAO.getStoreFull(storeId);

        // 店舗詳細（紹介文＋カレンダー）
        StoreDetailDAO detailDAO = new StoreDetailDAO();
        StoreDetail detail = detailDAO.getStoreDetailFull(storeId);

        // カレンダー List → Map<LocalDate, StoreCalendar>
        Map<LocalDate, StoreCalendar> calendarMap = new HashMap<>();
        if (detail != null && detail.getCalendars() != null) {
            for (StoreCalendar sc : detail.getCalendars()) {

                // ★ 時刻を文字列化（表示用）
                if (sc.getOpenTime() != null) {
                    sc.setOpenTimeStr(sc.getOpenTime().toString().substring(0, 5));
                }
                if (sc.getCloseTime() != null) {
                    sc.setCloseTimeStr(sc.getCloseTime().toString().substring(0, 5));
                }

                calendarMap.put(sc.getDate().toLocalDate(), sc);
            }
        }

        // JSP へ
        request.setAttribute("store", store);
        request.setAttribute("detail", detail);
        request.setAttribute("calendarMap", calendarMap);
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("firstDay", firstDay);

        request.getRequestDispatcher("/shop/store_detail_view.jsp")
               .forward(request, response);
    }
}
