package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StoreCalendar;

public class StoreCalendarDAO extends DAO {

    // 指定店舗のカレンダー全件取得
    public List<StoreCalendar> getCalendarsByStoreId(int storeId) throws Exception {
        List<StoreCalendar> calendars = new ArrayList<>();
        String sql = "SELECT * FROM store_calendar WHERE store_id = ? ORDER BY date";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StoreCalendar cal = new StoreCalendar();
                    cal.setStoreId(rs.getInt("store_id"));
                    cal.setDate(rs.getDate("date"));
                    cal.setOpen(rs.getBoolean("is_open"));
                    cal.setOpenTime(rs.getTime("open_time"));
                    cal.setCloseTime(rs.getTime("close_time"));
                    calendars.add(cal);
                }
            }
        }

        return calendars;
    }

    // カレンダー登録
    public boolean insertCalendar(StoreCalendar cal) throws Exception {
        String sql = "INSERT INTO store_calendar (store_id, date, is_open, open_time, close_time) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cal.getStoreId());
            stmt.setDate(2, cal.getDate());
            stmt.setBoolean(3, cal.isOpen());
            stmt.setTime(4, cal.getOpenTime());
            stmt.setTime(5, cal.getCloseTime());

            return stmt.executeUpdate() > 0;
        }
    }

    // カレンダー更新
    public boolean updateCalendar(StoreCalendar cal) throws Exception {
        String sql = "UPDATE store_calendar SET is_open = ?, open_time = ?, close_time = ? " +
                     "WHERE store_id = ? AND date = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, cal.isOpen());
            stmt.setTime(2, cal.getOpenTime());
            stmt.setTime(3, cal.getCloseTime());
            stmt.setInt(4, cal.getStoreId());
            stmt.setDate(5, cal.getDate());

            return stmt.executeUpdate() > 0;
        }
    }

    // カレンダー削除（特定日）
    public boolean deleteCalendar(int storeId, java.sql.Date date) throws Exception {
        String sql = "DELETE FROM store_calendar WHERE store_id = ? AND date = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            stmt.setDate(2, date);

            return stmt.executeUpdate() > 0;
        }
    }
}
