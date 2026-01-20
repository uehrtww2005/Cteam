package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.StoreCalendar;

public class StoreCalendarDAO extends DAO {

    // 店舗IDから営業カレンダー取得
    public List<StoreCalendar> getCalendarsByStoreId(int storeId) throws Exception {
        List<StoreCalendar> list = new ArrayList<>();
        String sql = "SELECT * FROM store_calendar WHERE store_id=? ORDER BY date";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StoreCalendar c = new StoreCalendar();
                    c.setStoreId(storeId);
                    c.setDate(rs.getDate("date"));
                    c.setOpen(rs.getBoolean("is_open"));
                    c.setOpenTime(rs.getTime("open_time"));
                    c.setCloseTime(rs.getTime("close_time"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    // 店舗IDに紐づくカレンダーを全削除
    public void deleteCalendarsByStoreId(int storeId) throws Exception {
        String sql = "DELETE FROM store_calendar WHERE store_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.executeUpdate();
        }
    }

    // ✅【上書き保存対応】INSERT or UPDATE（UPSERT）
    public void insertCalendar(int storeId, StoreCalendar cal) throws Exception {

        String sql =
            "INSERT INTO store_calendar (store_id, date, is_open, open_time, close_time) " +
            "VALUES (?, ?, ?, ?, ?) " +
            "ON CONFLICT (store_id, date) " +
            "DO UPDATE SET " +
            "is_open = EXCLUDED.is_open, " +
            "open_time = EXCLUDED.open_time, " +
            "close_time = EXCLUDED.close_time";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.setDate(2, cal.getDate());
            ps.setBoolean(3, cal.isOpen());
            ps.setTime(4, cal.getOpenTime());
            ps.setTime(5, cal.getCloseTime());

            ps.executeUpdate(); // ← 新規でも上書きでもOK
        }
    }

    public void deletePastCalendars() throws Exception {
        String sql = "DELETE FROM store_calendar WHERE date < CURRENT_DATE";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public StoreCalendar findByStoreAndDate(int storeId, LocalDate date) throws Exception {

        StoreCalendar sc = null;
        Connection con = getConnection();

        String sql =
            "SELECT * FROM store_calendar " +
            "WHERE store_id = ? AND date = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, storeId);
        st.setDate(2, java.sql.Date.valueOf(date));

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            sc = new StoreCalendar();
            sc.setStoreId(storeId);
            sc.setDate(rs.getDate("date"));
            sc.setOpen(rs.getBoolean("is_open"));
            sc.setOpenTime(rs.getTime("open_time"));
            sc.setCloseTime(rs.getTime("close_time"));
        }

        rs.close();
        st.close();
        con.close();

        return sc;
    }


}
