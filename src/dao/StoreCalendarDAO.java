package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StoreCalendar;

public class StoreCalendarDAO extends DAO {

    public List<StoreCalendar> getCalendarsByStoreId(int storeId) throws Exception {
        List<StoreCalendar> list = new ArrayList<>();
        String sql = "SELECT * FROM store_calendar WHERE store_id=?";

        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
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

    public void deleteCalendarsByStoreId(int storeId) throws Exception {
        String sql = "DELETE FROM store_calendar WHERE store_id=?";
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);
            ps.executeUpdate();
        }
    }

    public void insertCalendar(int storeId, StoreCalendar cal) throws Exception {
        String sql = "INSERT INTO store_calendar(store_id, date, is_open, open_time, close_time) VALUES(?,?,?,?,?)";
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, storeId);
            ps.setDate(2, cal.getDate());
            ps.setBoolean(3, cal.isOpen());
            ps.setTime(4, cal.getOpenTime());
            ps.setTime(5, cal.getCloseTime());
            ps.executeUpdate();
        }
    }
}
