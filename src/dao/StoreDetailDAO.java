package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        StoreDetail detail = getStoreDetailFull(storeId);
        if (detail == null) return null;

        // タグ1つだけ取得
        String sql = "SELECT t.tag_name " +
                     "FROM store_tags st " +
                     "JOIN tags t ON st.tag_id = t.tag_id " +
                     "WHERE st.store_id = ? LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detail.setTag(rs.getString("tag_name")); // ← 単一
                }
            }
        }

        return detail;
    }


    public void insertStoreTag(int storeId, int tagId) throws Exception {
        String sql = "INSERT INTO store_tags (store_id, tag_id) VALUES (?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.setInt(2, tagId);
            ps.executeUpdate();
        }
    }

    public void updateStoreDetail(StoreDetail detail) throws Exception {

        try (Connection conn = getConnection()) {

            // 1. store_details 更新
            String sqlDetail = "UPDATE store_details SET store_introduct=? WHERE store_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setString(1, detail.getStoreIntroduct());
                ps.setInt(2, detail.getStoreId());
                ps.executeUpdate();
            }

            // 2. タグ（1つだけ）更新 → DELETE → INSERT 1件
            String sqlDeleteTags = "DELETE FROM store_tags WHERE store_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteTags)) {
                ps.setInt(1, detail.getStoreId());
                ps.executeUpdate();
            }

            if (detail.getTag() != null && !detail.getTag().trim().isEmpty()) {
                // tag_name → tag_id を取得
                String sqlTagId = "SELECT tag_id FROM tags WHERE tag_name=?";
                int tagId = -1;

                try (PreparedStatement ps = conn.prepareStatement(sqlTagId)) {
                    ps.setString(1, detail.getTag());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            tagId = rs.getInt("tag_id");
                        }
                    }
                }

                if (tagId != -1) {
                    String sqlInsertTag = "INSERT INTO store_tags(store_id, tag_id) VALUES(?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(sqlInsertTag)) {
                        ps.setInt(1, detail.getStoreId());
                        ps.setInt(2, tagId);
                        ps.executeUpdate();
                    }
                }
            }

            // 3. 席 → 削除して追加
            seatDAO.deleteSeatsByStoreId(detail.getStoreId());
            if (detail.getSeats() != null) {
                for (Seat s : detail.getSeats()) {
                    seatDAO.insertSeat(detail.getStoreId(), s);
                }
            }

            // 4. カレンダー → 削除して追加
            calendarDAO.deleteCalendarsByStoreId(detail.getStoreId());
            if (detail.getCalendars() != null) {
                for (StoreCalendar cal : detail.getCalendars()) {
                    calendarDAO.insertCalendar(detail.getStoreId(), cal);
                }
            }
        }
    }

    public void saveOrUpdateStoreDetail(StoreDetail detail) throws Exception {
        StoreDetail exist = getStoreDetailFull(detail.getStoreId());
        if (exist == null) {
            int id = insertStoreDetail(detail);
            detail.setDetailId(id);
        } else {
            updateStoreDetail(detail);
        }
    }

}
