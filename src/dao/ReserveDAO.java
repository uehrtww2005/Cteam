package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Reserve;

public abstract class ReserveDAO extends DAO {

    public int insert(Reserve r) throws Exception {
        Connection con = getConnection();

        String sql =
            "INSERT INTO reservations " +
            "(store_id, user_id, group_id, reserved_at, customer_name, customer_tel, num_people, advance_pay, total_pay) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement st = con.prepareStatement(sql);

        // 1 store_id
        st.setInt(1, r.getStoreId());

        // 2 user_id（NULL可）
        if (r.getUserId() != null) {
            st.setInt(2, r.getUserId());
        } else {
            st.setNull(2, java.sql.Types.INTEGER);
        }

        // 3 group_id（NULL可）
        if (r.getGroupId() != null) {
            st.setInt(3, r.getGroupId());
        } else {
            st.setNull(3, java.sql.Types.INTEGER);
        }

        // 4 reserved_at（LocalDateTime → Timestamp）
        st.setTimestamp(4, Timestamp.valueOf(r.getReservedAt()));

        // 5〜9 その他
        st.setString(5, r.getCustomerName());
        st.setString(6, r.getCustomerTel());
        st.setInt(7, r.getNumPeople());
        st.setInt(8, r.getAdvancePay());
        st.setInt(9, r.getTotalPay());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }

    public List<Reserve> findByStoreId(int storeId) throws Exception {
        List<Reserve> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM reservations WHERE store_id = ? ORDER BY reserved_at"
        );
        st.setInt(1, storeId);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Reserve r = new Reserve();
            r.setReservationId(rs.getInt("reservation_id"));
            r.setStoreId(rs.getInt("store_id"));
            r.setReservedAt(rs.getTimestamp("reserved_at").toLocalDateTime());
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

    public int delete(int reservationId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM reservations WHERE reservation_id = ?"
        );
        st.setInt(1, reservationId);

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }

    public boolean update(Reserve r) throws Exception {
        Connection con = getConnection();
        PreparedStatement ps = null;
        boolean result = false;

        try {
            String sql =
                "UPDATE reservations SET reserved_at = ?, num_people = ?, advance_pay = ?, total_pay = ? " +
                "WHERE reservation_id = ?";
            ps = con.prepareStatement(sql);

            // LocalDateTime → Timestamp に変換
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




}
