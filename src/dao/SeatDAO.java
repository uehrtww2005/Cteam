package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Seat;

public class SeatDAO extends DAO {

    // 店舗IDで全席取得
    public List<Seat> getSeatsByStoreId(int storeId) throws Exception {
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
                    seats.add(seat);
                }
            }
        }

        return seats;
    }

    // 新規登録
    public boolean insertSeat(Seat seat) throws Exception {
        String sql = "INSERT INTO seats(store_id, seat_type, seat_name, min_people) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seat.getStoreId());
            ps.setString(2, seat.getSeatType());
            ps.setString(3, seat.getSeatName());
            ps.setInt(4, seat.getMinPeople());

            return ps.executeUpdate() > 0;
        }
    }

    // 更新
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

    // 削除
    public boolean deleteSeat(int seatId) throws Exception {
        String sql = "DELETE FROM seats WHERE seat_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatId);
            return ps.executeUpdate() > 0;
        }
    }

    // 単一の席を取得
    public Seat getSeatById(int seatId) throws Exception {
        Seat seat = null;
        String sql = "SELECT * FROM seats WHERE seat_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setStoreId(rs.getInt("store_id"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setSeatName(rs.getString("seat_name"));
                    seat.setMinPeople(rs.getInt("min_people"));
                }
            }
        }

        return seat;
    }
}
