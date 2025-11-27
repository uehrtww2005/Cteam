package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Menu;
import bean.Seat;
import bean.Store;
import bean.StoreCalendar;
import bean.StoreDetail;

public class StoreDAO extends DAO {

    private SeatDAO seatDAO = new SeatDAO();
    private StoreCalendarDAO calendarDAO = new StoreCalendarDAO();

    // store_tel で取得
    public Store get(String storeTel) throws Exception {
        Store store = null;
        String sql = "SELECT * FROM stores WHERE store_tel = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, storeTel);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    store = new Store();
                    store.setStoreId(rs.getInt("store_id"));
                    store.setStoreAddress(rs.getString("store_address"));
                    store.setPassword(rs.getString("password"));
                    store.setStoreName(rs.getString("store_name"));
                    store.setStoreTel(rs.getString("store_tel"));
                    // ★追加: ステータスの取得
                    store.setStatus(rs.getInt("status"));
                }
            }
        }

        return store;
    }

    // ログイン
    public Store login(String storeTel, String password) throws Exception {
        Store store = get(storeTel);
        if (store == null || !store.getPassword().equals(password)) return null;
        return store;
    }

    // 登録 (変更なし)
    public boolean save(Store store) throws Exception {
        String sql = "INSERT INTO stores(store_address, password, store_name, store_tel) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, store.getStoreAddress());
            ps.setString(2, store.getPassword());
            ps.setString(3, store.getStoreName());
            ps.setString(4, store.getStoreTel());

            return ps.executeUpdate() > 0;
        }
    }

    // 登録して自動生成IDを返す (変更なし)
    public int saveAndReturnId(Store store) throws Exception {
        int generatedId = -1;
        String sql = "INSERT INTO stores(store_address, password, store_name, store_tel) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, store.getStoreAddress());
            ps.setString(2, store.getPassword());
            ps.setString(3, store.getStoreName());
            ps.setString(4, store.getStoreTel());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }

    // 全店舗取得
    public List<Store> findAll() throws Exception {
        List<Store> list = new ArrayList<>();
        String sql = "SELECT * FROM stores ORDER BY store_id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Store store = new Store();
                store.setStoreId(rs.getInt("store_id"));
                store.setStoreAddress(rs.getString("store_address"));
                store.setPassword(rs.getString("password"));
                store.setStoreName(rs.getString("store_name"));
                store.setStoreTel(rs.getString("store_tel"));
                // ★追加: ステータスの取得
                store.setStatus(rs.getInt("status"));
                list.add(store);
            }
        }

        return list;
    }

    // ★ 改善版：Store + StoreDetail + Seats + Calendar + Menu をまとめて取得 (statusの取得を追加)
    public Store getStoreFull(int storeId) throws Exception {
        Store store = null;

        // まず stores + store_details
        String sql = "SELECT s.store_id, s.store_name, s.store_address, s.store_tel, s.status, " + // statusを追加
                     "d.detail_id, d.store_introduct " +
                     "FROM stores s " +
                     "LEFT JOIN store_details d ON s.store_id = d.store_id " +
                     "WHERE s.store_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    store = new Store();
                    store.setStoreId(rs.getInt("store_id"));
                    store.setStoreName(rs.getString("store_name"));
                    store.setStoreAddress(rs.getString("store_address"));
                    store.setStoreTel(rs.getString("store_tel"));
                    // ★追加: ステータスの取得
                    store.setStatus(rs.getInt("status"));

                    StoreDetail detail = new StoreDetail();
                    detail.setDetailId(rs.getInt("detail_id"));
                    detail.setStoreId(storeId);
                    detail.setStoreIntroduct(rs.getString("store_introduct"));
                    store.setStoreDetail(detail);
                }
            }
        }

        // ... (省略)
        // 他のデータ取得ロジックは変更なし
        // ...

        if (store != null) {
            // Seats 取得
            List<Seat> seats = seatDAO.getSeatsByStoreId(storeId);
            store.getStoreDetail().setSeats(seats);

            // Calendar 取得
            List<StoreCalendar> calendars = calendarDAO.getCalendarsByStoreId(storeId);
            store.getStoreDetail().setCalendars(calendars);

            // Menu 取得
            List<Menu> menuList = new ArrayList<>();
            String menuSql = "SELECT menu_id, menu_name, price FROM menu WHERE store_id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(menuSql)) {

                ps.setInt(1, storeId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Menu menu = new Menu();
                        menu.setMenuId(rs.getInt("menu_id"));
                        menu.setStoreId(storeId);
                        menu.setMenuName(rs.getString("menu_name"));
                        menu.setPrice(rs.getInt("price"));
                        menuList.add(menu);
                    }
                }
            }
            store.setMenu(menuList);
        }

        return store;
    }

    public List<Store> search(String keyword) throws Exception {
        List<Store> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT store_id, store_name, store_address, store_tel "
                   + "FROM stores "
                   + "WHERE store_name LIKE ? "
                   + "OR store_address LIKE ? "
                   + "OR store_tel LIKE ?";

        PreparedStatement st = con.prepareStatement(sql);

        String like = "%" + keyword + "%";
        st.setString(1, like);
        st.setString(2, like);
        st.setString(3, like);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Store s = new Store();
            s.setStoreId(rs.getInt("store_id"));
            s.setStoreName(rs.getString("store_name"));
            s.setStoreAddress(rs.getString("store_address"));
            s.setStoreTel(rs.getString("store_tel"));
            list.add(s);
        }

        rs.close();
        st.close();
        con.close();

        return list;  // ← ここが List<Store>
    }
}