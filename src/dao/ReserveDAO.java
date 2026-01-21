package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bean.Reserve;

public abstract class ReserveDAO extends DAO {

    // =====================
    // 新規予約登録
    // =====================
    public int insert(Reserve r) throws Exception {
        Connection con = getConnection();

        String sql =
            "INSERT INTO reserve " +
            "(store_id, seat_id, user_id, group_id, reserved_at, " +
            " customer_name, customer_tel, num_people, advance_pay, total_pay) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement st = con.prepareStatement(sql);

        st.setInt(1, r.getStoreId());
        st.setInt(2, r.getSeatId());

        if (r.getUserId() != null) {
            st.setInt(3, r.getUserId());
        } else {
            st.setNull(3, java.sql.Types.INTEGER);
        }

        if (r.getGroupId() != null) {
            st.setInt(4, r.getGroupId());
        } else {
            st.setNull(4, java.sql.Types.INTEGER);
        }

        st.setTimestamp(5, Timestamp.valueOf(r.getReservedAt()));
        st.setString(6, r.getCustomerName());
        st.setString(7, r.getCustomerTel());
        st.setInt(8, r.getNumPeople());
        st.setInt(9, r.getAdvancePay());
        st.setInt(10, r.getTotalPay());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }

    // =====================
    // 店舗の全予約取得
    // =====================
    public List<Reserve> findByStoreId(int storeId) throws Exception {
        List<Reserve> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM reserve WHERE store_id = ? ORDER BY reserved_at"
        );
        st.setInt(1, storeId);

        ResultSet rs = st.executeQuery();

        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("M/d（E）HH:mm", Locale.JAPAN);

        while (rs.next()) {
            Reserve r = new Reserve();
            r.setReservationId(rs.getInt("reservation_id"));
            r.setStoreId(rs.getInt("store_id"));
            r.setSeatId(rs.getInt("seat_id"));

            LocalDateTime ldt =
                rs.getTimestamp("reserved_at").toLocalDateTime();
            r.setReservedAt(ldt);
            r.setDisplayDateTime(ldt.format(fmt));

            r.setCustomerName(rs.getString("customer_name"));
            r.setCustomerTel(rs.getString("customer_tel"));
            r.setNumPeople(rs.getInt("num_people"));
            list.add(r);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // =====================
    // 予約削除
    // =====================
    public int delete(int reservationId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM reserve WHERE reservation_id = ?"
        );
        st.setInt(1, reservationId);

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }

    // =====================
    // 予約更新
    // =====================
    public boolean update(Reserve r) throws Exception {
        Connection con = getConnection();
        PreparedStatement ps = null;
        boolean result = false;

        try {
            String sql =
                "UPDATE reserve SET reserved_at = ?, num_people = ?, advance_pay = ?, total_pay = ? " +
                "WHERE reservation_id = ?";
            ps = con.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(r.getReservedAt()));
            ps.setInt(2, r.getNumPeople());
            ps.setInt(3, r.getAdvancePay());
            ps.setInt(4, r.getTotalPay());
            ps.setInt(5, r.getReservationId());

            result = ps.executeUpdate() > 0;

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return result;
    }

    // =====================
    // 店舗＋日付で予約取得
    // =====================
    public List<Reserve> findByStoreAndDate(int storeId, LocalDate date) throws Exception {
        List<Reserve> list = new ArrayList<>();
        Connection con = getConnection();

        String sql =
            "SELECT * FROM reserve WHERE store_id = ? AND DATE(reserved_at) = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, storeId);
        ps.setDate(2, java.sql.Date.valueOf(date));

        ResultSet rs = ps.executeQuery();

        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("M/d（E）HH:mm", Locale.JAPAN);

        while (rs.next()) {
            Reserve r = new Reserve();
            r.setReservationId(rs.getInt("reservation_id"));
            r.setStoreId(rs.getInt("store_id"));
            r.setSeatId(rs.getInt("seat_id"));

            LocalDateTime ldt =
                rs.getTimestamp("reserved_at").toLocalDateTime();
            r.setReservedAt(ldt);
            r.setDisplayDateTime(ldt.format(fmt));

            r.setNumPeople(rs.getInt("num_people"));
            list.add(r);
        }

        rs.close();
        ps.close();
        con.close();

        return list;
    }

    // =====================
    // 席＋時間で満席判定
    // =====================
    public boolean isSeatReserved(int seatId, LocalDateTime time) throws Exception {
        Connection con = getConnection();
        String sql =
            "SELECT COUNT(*) FROM reserve WHERE seat_id = ? AND reserved_at = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, seatId);
        ps.setTimestamp(2, Timestamp.valueOf(time));

        ResultSet rs = ps.executeQuery();
        rs.next();

        boolean reserved = rs.getInt(1) > 0;

        rs.close();
        ps.close();
        con.close();
        return reserved;
    }

    // =====================
    // 指定日の予約済み席ID一覧
    // =====================
    public List<Integer> findReservedSeatIds(int storeId, LocalDate date) throws Exception {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT seat_id FROM reserve WHERE store_id=? AND DATE(reserved_at)=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("seat_id"));
                }
            }
        }
        return list;
    }

    // =====================
    // 店舗予約一覧（管理画面用）
    // =====================
    public List<Reserve> ReserveByStoreId(int storeId) throws Exception {

        List<Reserve> list = new ArrayList<>();
        Connection con = getConnection();

        String sql =
            "SELECT * FROM reserve WHERE store_id = ? ORDER BY reserved_at DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, storeId);
        ResultSet rs = ps.executeQuery();

        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("M/d（E）HH:mm", Locale.JAPAN);

        while (rs.next()) {
            Reserve r = new Reserve();
            r.setReservationId(rs.getInt("reservation_id"));
            r.setStoreId(rs.getInt("store_id"));
            r.setSeatId(rs.getInt("seat_id"));
            r.setCustomerName(rs.getString("customer_name"));
            r.setCustomerTel(rs.getString("customer_tel"));
            r.setNumPeople(rs.getInt("num_people"));
            r.setTotalPay(rs.getInt("total_pay"));
            r.setAdvancePay(rs.getInt("advance_pay"));

            LocalDateTime ldt =
                rs.getTimestamp("reserved_at").toLocalDateTime();
            r.setReservedAt(ldt);
            r.setDisplayDateTime(ldt.format(fmt));

            list.add(r);
        }

        rs.close();
        ps.close();
        con.close();

        return list;
    }
}
