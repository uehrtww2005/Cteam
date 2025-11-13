package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StoreCalendar;

public class StoreCalendarDAO extends DAO {

    /**
     * 指定店舗の指定月のカレンダー情報を取得
     *
     * @param storeId 店舗ID
     * @param startDate 取得開始日（yyyy-mm-dd）
     * @param endDate 取得終了日（yyyy-mm-dd）
     * @return List<StoreCalendar>
     * @throws Exception
     */
    public List<StoreCalendar> getMonthlyCalendar(int storeId, String startDate, String endDate) throws Exception {
        List<StoreCalendar> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM store_calendar " +
                         "WHERE store_id = ? AND date BETWEEN ? AND ? " +
                         "ORDER BY date";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, storeId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            rs = statement.executeQuery();
            while (rs.next()) {
                StoreCalendar sc = new StoreCalendar();
                sc.setStoreId(rs.getInt("store_id"));
                sc.setDate(rs.getDate("date"));
                sc.setOpen(rs.getBoolean("is_open"));
                sc.setOpenTime(rs.getTime("open_time"));
                sc.setCloseTime(rs.getTime("close_time"));
                list.add(sc);
            }
        } finally {
            if(rs != null) rs.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
        }

        return list;
    }
}
