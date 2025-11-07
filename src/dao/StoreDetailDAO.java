package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.StoreDetail;

public class StoreDetailDAO extends DAO {

    public StoreDetail get(int storeId) throws Exception {
        StoreDetail storedetail = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM store_details WHERE store_id=?");
            statement.setInt(1, storeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                storedetail = new StoreDetail();
                storedetail.setStoreId(resultSet.getInt("detail_id"));
                storedetail.setStoreId(resultSet.getInt("store_id"));
                storedetail.setStoreHours(resultSet.getString("store_hours"));
                storedetail.setStoreClose(resultSet.getString("store_close"));
                storedetail.setStoreIntroduct(resultSet.getString("store_introduct"));
                storedetail.setSeatDetail(resultSet.getString("seat_detail"));
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return storedetail;
    }

    public boolean save(StoreDetail storedetail) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO store_details (store_id, store_hours, store_close, store_introduct, seat_detail) VALUES (?, ?, ?, ?, ?)"
            );

            // どの店舗の詳細か指定
            statement.setInt(1, storedetail.getStoreId());
            statement.setString(2, storedetail.getStoreHours());
            statement.setString(3, storedetail.getStoreClose());
            statement.setString(4, storedetail.getStoreIntroduct());
            statement.setString(5, storedetail.getSeatDetail());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }


    // ★ 新規追加：登録後に自動生成された store_id を返すメソッド
    public int saveAndReturnId(StoreDetail storedetail) throws Exception {
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
            		 "INSERT INTO store_details (store_id, store_hours, store_close, store_introduct, seat_detail) VALUES (?, ?, ?, ?, ?)",
                 Statement.RETURN_GENERATED_KEYS)) {

        	statement.setInt(1, storedetail.getStoreId());
            statement.setString(2, storedetail.getStoreHours());
            statement.setString(3, storedetail.getStoreClose());
            statement.setString(4, storedetail.getStoreIntroduct());
            statement.setString(5, storedetail.getSeatDetail());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // ← store_id を取得
                }
            }
        }

        return generatedId;
    }


 // 店舗詳細を更新するメソッド
    public boolean update(StoreDetail storedetail) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "UPDATE store_details SET store_hours=?, store_close=?, store_introduct=?, seat_detail=? WHERE store_id=?"
            );

            statement.setString(1, storedetail.getStoreHours());
            statement.setString(2, storedetail.getStoreClose());
            statement.setString(3, storedetail.getStoreIntroduct());
            statement.setString(4, storedetail.getSeatDetail());
            statement.setInt(5, storedetail.getStoreId());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }

 // 店舗詳細を削除するメソッド
    public boolean delete(int storeId) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "DELETE FROM store_details WHERE store_id=?"
            );
            statement.setInt(1, storeId);

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }
}
