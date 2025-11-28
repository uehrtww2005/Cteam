package Adpay;

import java.text.SimpleDateFormat;

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

        int storeId = Integer.parseInt(request.getParameter("store_id"));

        // 店舗基本情報
        StoreDAO storeDAO = new StoreDAO();
        Store store = storeDAO.getStoreFull(storeId);
        request.setAttribute("store", store);

        // 店舗詳細（紹介文、カレンダー）
        StoreDetailDAO detailDAO = new StoreDetailDAO();
        StoreDetail detail = detailDAO.getStoreDetailFull(storeId);

        // カレンダーの文字列化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(StoreCalendar sc : detail.getCalendars()) {
            sc.setDateStr(sdf.format(sc.getDate())); // JSP用文字列化
            if(sc.getOpenTime() != null) sc.setOpenTimeStr(sc.getOpenTime().toString().substring(0,5));
            if(sc.getCloseTime() != null) sc.setCloseTimeStr(sc.getCloseTime().toString().substring(0,5));
        }

        // 今日の日付文字列
        String todayStr = sdf.format(new java.util.Date());
        request.setAttribute("todayStr", todayStr);

        request.setAttribute("detail", detail);
        request.setAttribute("calendars", detail.getCalendars());

        request.getRequestDispatcher("/shop/store_detail_view.jsp").forward(request, response);
    }
}
