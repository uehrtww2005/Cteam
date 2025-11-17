package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Seat;
import bean.StoreCalendar;
import bean.StoreDetail;
import dao.SeatDAO;
import dao.StoreCalendarDAO;
import dao.StoreDetailDAO;
import dao.TagDAO;
import tool.Action;

import java.util.List;

public class StoreUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int storeId = Integer.parseInt(request.getParameter("store_id"));
        String storeIntroduct = request.getParameter("store_introduct");

        StoreDetail detail = new StoreDetail();
        detail.setStoreId(storeId);
        detail.setStoreIntroduct(storeIntroduct);

        // 席情報（複数）
        SeatDAO seatDAO = new SeatDAO();
        List<Seat> seats = // フロントからのリクエストパラメータを List<Seat> に変換
        detail.setSeats(seats);

        // カレンダー情報（複数）
        StoreCalendarDAO calendarDAO = new StoreCalendarDAO();
        List<StoreCalendar> calendars = // フロントからのリクエストパラメータを List<StoreCalendar> に変換
        detail.setCalendars(calendars);

        // タグ情報（複数）
        String[] selectedTags = request.getParameterValues("selectedTags"); // 複数選択
        TagDAO tagDAO = new TagDAO();
        List<String> tagNames = tagDAO.getOrCreateTags(selectedTags);
        detail.setTags(tagNames);

        StoreDetailDAO detailDAO = new StoreDetailDAO();
        if (!detailDAO.exists(storeId)) {
            detailDAO.insertStoreDetail(detail); // 初回登録
        } else {
            detailDAO.updateStoreDetail(detail); // 更新
        }

        // 席情報・カレンダー情報・タグ情報をそれぞれ DAO で保存
        seatDAO.saveSeats(storeId, seats);
        calendarDAO.saveCalendars(storeId, calendars);
        tagDAO.saveStoreTags(storeId, tagNames);

        response.sendRedirect(request.getContextPath() + "/Adpay/StoreDetail.action?store_id=" + storeId);
    }
}
