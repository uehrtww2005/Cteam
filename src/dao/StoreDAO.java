package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Menu;
import bean.Store;
import bean.StoreDetail;

public class StoreDAO extends DAO {

    public Store get(String storeTel) throws Exception {
        Store store = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM stores WHERE store_tel=?");
            statement.setString(1, storeTel);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                store = new Store();
                store.setStoreId(resultSet.getInt("store_id"));
                store.setStoreAddress(resultSet.getString("store_address"));
                store.setPassword(resultSet.getString("password"));
                store.setStoreName(resultSet.getString("store_name"));
                store.setStoreTel(resultSet.getString("store_tel"));
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return store;
    }

    public Store login(String storeTel, String password) throws Exception {
        Store store = get(storeTel);

        if (store == null || !store.getPassword().equals(password)) {
            return null;
        }

        return store;
    }

    public boolean save(Store store) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO stores(store_address, password, store_name, store_tel) VALUES (?, ?, ?, ?)");
            statement.setString(1, store.getStoreAddress());
            statement.setString(2, store.getPassword());
            statement.setString(3, store.getStoreName());
            statement.setString(4, store.getStoreTel());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }

    // ★ 新規追加：登録後に自動生成された store_id を返すメソッド
    public int saveAndReturnId(Store store) throws Exception {
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO stores(store_address, password, store_name, store_tel) VALUES (?, ?, ?, ?)",
                 Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, store.getStoreAddress());
            statement.setString(2, store.getPassword());
            statement.setString(3, store.getStoreName());
            statement.setString(4, store.getStoreTel());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // ← store_id を取得
                }
            }
        }

        return generatedId;
    }

    public List<Store> findAll() throws Exception {
        List<Store> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM stores ORDER BY store_id");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Store store = new Store();
                store.setStoreId(rs.getInt("store_id"));
                store.setStoreAddress(rs.getString("store_address"));
                store.setPassword(rs.getString("password"));
                store.setStoreName(rs.getString("store_name"));
                store.setStoreTel(rs.getString("store_tel"));
                list.add(store);
            }
        }

        return list;
    }

    public Store getStoreWithDetailAndMenu(int storeId) throws Exception {
        Store store = null;

        String sql =
        	    "SELECT " +
        	    "s.store_id, " +
        	    "s.store_name, " +
        	    "s.store_address, " +
        	    "s.store_tel, " +
        	    "d.detail_id, " +
        	    "d.store_hours, " +
        	    "d.store_close, " +
        	    "d.store_introduct, " +
        	    "d.seat_detail, " +
        	    "m.menu_id, " +
        	    "m.menu_name " +
        	    "FROM stores s " +
        	    "JOIN store_details d ON s.store_id = d.store_id " +
        	    "LEFT JOIN menu m ON s.store_id = m.store_id " +
        	    "WHERE s.store_id = ?";


        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();

            List<Menu> menuList = new ArrayList<>();

            while (rs.next()) {
                if (store == null) {
                    store = new Store();
                    store.setStoreId(rs.getInt("store_id"));
                    store.setStoreName(rs.getString("store_name"));
                    store.setStoreAddress(rs.getString("store_address"));
                    store.setStoreTel(rs.getString("store_tel"));

                    // StoreDetail設定
                    StoreDetail detail = new StoreDetail();
                    detail.setDetailId(rs.getInt("detail_id"));
                    detail.setStoreId(rs.getInt("store_id"));
                    detail.setStoreHours(rs.getString("store_hours"));
                    detail.setStoreClose(rs.getString("store_close"));
                    detail.setStoreIntroduct(rs.getString("store_introduct"));
                    detail.setSeatDetail(rs.getString("seat_detail"));
                    store.setStoreDetail(detail);
                }

                // Menu設定
                int menuId = rs.getInt("menu_id");
                if (menuId != 0) {
                    Menu menu = new Menu();
                    menu.setMenuId(menuId);
                    menu.setStoreId(rs.getInt("store_id"));
                    menu.setMenuName(rs.getString("menu_name"));
                    menu.setPrice(rs.getInt("price"));
                    menuList.add(menu);
                }
            }

            if (store != null) {
                store.setMenu(menuList);
            }
        }

        return store;
    }

    public Store getStoreWithDetailMenuAndTags(int storeId) throws Exception {
        Store store = getStoreWithDetailAndMenu(storeId);

        if (store != null) {
            // ★ StoreTagDAOを呼んでタグを取得
            StoreTagDAO tagDAO = new StoreTagDAO();
            List<String> tags = tagDAO.getTagsByStoreId(storeId);
            store.setTags(tags);
        }

        return store;
    }



}

