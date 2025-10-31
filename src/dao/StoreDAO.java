package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.Store;

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
}
