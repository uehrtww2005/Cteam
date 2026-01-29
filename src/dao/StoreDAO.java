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
                    store.setStatus(rs.getInt("status"));
                    store.setImageExtension(rs.getString("image_extension"));
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

    // 保存（画像拡張子追加）
    public boolean save(Store store) throws Exception {
        String sql = "INSERT INTO stores(store_address, password, store_name, store_tel, image_extension) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, store.getStoreAddress());
            ps.setString(2, store.getPassword());
            ps.setString(3, store.getStoreName());
            ps.setString(4, store.getStoreTel());
            ps.setString(5, store.getImageExtension());

            return ps.executeUpdate() > 0;
        }
    }

    // 保存して store_id を返す（画像拡張子追加）
    public int saveAndReturnId(Store store) throws Exception {
        int generatedId = -1;
        String sql = "INSERT INTO stores(store_address, password, store_name, store_tel, image_extension) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, store.getStoreAddress());
            ps.setString(2, store.getPassword());
            ps.setString(3, store.getStoreName());
            ps.setString(4, store.getStoreTel());
            ps.setString(5, store.getImageExtension());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }

    // 全店舗取得（管理者用）
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
                store.setStatus(rs.getInt("status"));
                store.setImageExtension(rs.getString("image_extension"));

                list.add(store);
            }
        }

        return list;
    }

    // ★ ユーザー用：利用中店舗のみ取得
    public List<Store> findAllActive() throws Exception {
        List<Store> list = new ArrayList<>();
        String sql = "SELECT * FROM stores WHERE status = 0 ORDER BY store_id";

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
                store.setStatus(rs.getInt("status"));
                store.setImageExtension(rs.getString("image_extension"));

                list.add(store);
            }
        }

        return list;
    }

    // Store + Detail + Seat + Calendar + Menu 全部取得
    public Store getStoreFull(int storeId) throws Exception {
        Store store = null;

        String sql =
                "SELECT s.store_id, s.store_name, s.store_address, s.store_tel, s.status, s.image_extension, "
                + "d.detail_id, d.store_introduct "
                + "FROM stores s "
                + "LEFT JOIN store_details d ON s.store_id = d.store_id "
                + "WHERE s.store_id = ?";

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
                    store.setStatus(rs.getInt("status"));
                    store.setImageExtension(rs.getString("image_extension"));

                    StoreDetail detail = new StoreDetail();
                    detail.setDetailId(rs.getInt("detail_id"));
                    detail.setStoreId(storeId);
                    detail.setStoreIntroduct(rs.getString("store_introduct"));
                    store.setStoreDetail(detail);
                }
            }
        }

        if (store != null) {
            List<Seat> seats = seatDAO.getSeatsByStoreId(storeId);
            store.getStoreDetail().setSeats(seats);

            List<StoreCalendar> calendars = calendarDAO.getCalendarsByStoreId(storeId);
            store.getStoreDetail().setCalendars(calendars);

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

    // キーワード検索（管理者用）
    public List<Store> search(String keyword) throws Exception {
        List<Store> list = new ArrayList<>();

        Connection con = getConnection();

        String sql = "SELECT store_id, store_name, store_address, store_tel, image_extension "
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
            s.setImageExtension(rs.getString("image_extension"));

            list.add(s);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // ★ ユーザー用検索：利用中店舗のみ
    public List<Store> searchActive(String keyword) throws Exception {
        List<Store> list = new ArrayList<>();

        String sql = "SELECT store_id, store_name, store_address, store_tel, image_extension "
                   + "FROM stores "
                   + "WHERE status = 0 AND (store_name LIKE ? OR store_address LIKE ? OR store_tel LIKE ?)";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            st.setString(1, like);
            st.setString(2, like);
            st.setString(3, like);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Store s = new Store();
                    s.setStoreId(rs.getInt("store_id"));
                    s.setStoreName(rs.getString("store_name"));
                    s.setStoreAddress(rs.getString("store_address"));
                    s.setStoreTel(rs.getString("store_tel"));
                    s.setImageExtension(rs.getString("image_extension"));

                    list.add(s);
                }
            }
        }

        return list;
    }

    public void updateImageExtension(int storeId, String extension) throws Exception {
        String sql = "UPDATE stores SET image_extension = ? WHERE store_id ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, extension);
            ps.setInt(2, storeId);
            ps.executeUpdate();
        }
    }

 // 電話番号重複チェック
    public boolean isStoreTelExists(String storeTel) throws Exception {
        String sql = "SELECT COUNT(*) FROM stores WHERE store_tel = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, storeTel);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 住所重複チェック
    public boolean isStoreAddressExists(String storeAddress) throws Exception {
        String sql = "SELECT COUNT(*) FROM stores WHERE store_address = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, storeAddress);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // パスワード重複チェック（必要なら、DBに同じパスワードを登録できないように）
    public boolean isPasswordExists(String password) throws Exception {
        String sql = "SELECT COUNT(*) FROM stores WHERE password = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }


}
