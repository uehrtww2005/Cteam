package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Seat;
import bean.StoreCalendar;
import bean.StoreDetail;

public class StoreDetailDAO extends DAO {

    private SeatDAO seatDAO = new SeatDAO();
    private StoreCalendarDAO calendarDAO = new StoreCalendarDAO();

    // 店舗詳細と席・カレンダーをまとめて取得
    public StoreDetail getStoreDetailFull(int storeId) throws Exception {
        StoreDetail detail = null;
        String sql = "SELECT * FROM store_details WHERE store_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detail = new StoreDetail();
                    detail.setDetailId(rs.getInt("detail_id"));
                    detail.setStoreId(rs.getInt("store_id"));
                    detail.setStoreIntroduct(rs.getString("store_introduct"));

                    // 席情報とカレンダー情報もセット
                    List<Seat> seats = seatDAO.getSeatsByStoreId(storeId);
                    detail.setSeats(seats);

                    List<StoreCalendar> calendars = calendarDAO.getCalendarsByStoreId(storeId);
                    detail.setCalendars(calendars);
                }
            }
        }

        return detail;
    }

    // 店舗詳細の登録
    public int insertStoreDetail(StoreDetail detail) throws Exception {
        String sql = "INSERT INTO store_details (store_id, store_introduct) VALUES (?, ?) RETURNING detail_id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detail.getStoreId());
            stmt.setString(2, detail.getStoreIntroduct());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public StoreDetail getStoreDetailFullWithTags(int storeId) throws Exception {
        // まず既存の詳細＋席＋カレンダーを取得
        StoreDetail detail = getStoreDetailFull(storeId);
        if (detail == null) return null;

        // タグを取得
        String sql = "SELECT t.tag_name " +
                     "FROM store_tags st " +
                     "JOIN tags t ON st.tag_id = t.tag_id " +
                     "WHERE st.store_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> tagList = new ArrayList<>();
                while (rs.next()) {
                    tagList.add(rs.getString("tag_name"));
                }
                detail.setTags(tagList);
            }
        }

        return detail;
    }

}
