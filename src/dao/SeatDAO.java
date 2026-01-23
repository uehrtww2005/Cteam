package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Seat;

public class SeatDAO extends DAO {

    // =========================
    // 【ユーザー側】有効な席のみ取得
    // =========================
    public List<Seat> getSeatsByStoreId(int storeId) throws Exception {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE store_id = ? AND is_active = true ORDER BY seat_id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setStoreId(rs.getInt("store_id"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setSeatName(rs.getString("seat_name"));
                    seat.setMinPeople(rs.getInt("min_people"));
                    seats.add(seat);
                }
            }
        }
        return seats;
    }

    // =========================
    // 【店側】全席取得（管理用）
    // =========================
    public List<Seat> getAllSeatsByStoreId(int storeId) throws Exception {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE store_id = ? ORDER BY seat_id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setStoreId(rs.getInt("store_id"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setSeatName(rs.getString("seat_name"));
                    seat.setMinPeople(rs.getInt("min_people"));
                    seat.setActive(rs.getBoolean("is_active"));
                    seats.add(seat);
                }
            }
        }
        return seats;
    }

    // =========================
    // 新規席登録（is_active=true）
    // =========================
    public boolean insertSeat(Seat seat) throws Exception {
        String sql = "INSERT INTO seats (store_id, seat_type, seat_name, min_people, is_active) VALUES (?, ?, ?, ?, true)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seat.getStoreId());
            ps.setString(2, seat.getSeatType());
            ps.setString(3, seat.getSeatName());
            ps.setInt(4, seat.getMinPeople());

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // 席情報更新
    // =========================
    public boolean updateSeat(Seat seat) throws Exception {
        String sql = "UPDATE seats SET seat_type = ?, seat_name = ?, min_people = ? WHERE seat_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, seat.getSeatType());
            ps.setString(2, seat.getSeatName());
            ps.setInt(3, seat.getMinPeople());
            ps.setInt(4, seat.getSeatId());

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // 単一席取得
    // =========================
    public Seat getSeatById(int seatId) throws Exception {
        Seat seat = null;
        String sql = "SELECT * FROM seats WHERE seat_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, seatId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setSeatName(rs.getString("seat_name"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setStoreId(rs.getInt("store_id"));
                    seat.setMinPeople(rs.getInt("min_people"));
                    seat.setActive(rs.getBoolean("is_active"));
                }
            }
        }
        return seat;
    }

    // =========================
    // 【重要】店舗の席を無効化（論理削除）
    // =========================
    public void deleteSeatsByStoreId(int storeId) throws Exception {
        String sql = "UPDATE seats SET is_active = false WHERE store_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.executeUpdate();
        }
    }

    // =========================
    // 店舗ID指定で席追加
    // =========================
    public void insertSeat(int storeId, Seat s) throws Exception {
        String sql = "INSERT INTO seats (store_id, seat_type, seat_name, min_people, is_active) VALUES (?, ?, ?, ?, true)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ps.setString(2, s.getSeatType());
            ps.setString(3, s.getSeatName());
            ps.setInt(4, s.getMinPeople());
            ps.executeUpdate();
        }
    }
}
