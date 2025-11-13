package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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


    public boolean saveWithTags(StoreDetail storedetail) throws Exception {
        Connection connection = null;
        PreparedStatement storeStmt = null;
        PreparedStatement tagSelectStmt = null;
        PreparedStatement tagInsertStmt = null;
        PreparedStatement linkStmt = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // トランザクション開始

            // ① 店舗詳細を登録
            storeStmt = connection.prepareStatement(
                "INSERT INTO store_details (store_id, store_hours, store_close, store_introduct, seat_detail) VALUES (?, ?, ?, ?, ?)"
            );
            storeStmt.setInt(1, storedetail.getStoreId());
            storeStmt.setString(2, storedetail.getStoreHours());
            storeStmt.setString(3, storedetail.getStoreClose());
            storeStmt.setString(4, storedetail.getStoreIntroduct());
            storeStmt.setString(5, storedetail.getSeatDetail());
            storeStmt.executeUpdate();

            // ② タグ登録＆紐づけ
            if (storedetail.getTags() != null) {
                for (String tagName : storedetail.getTags()) {
                    int tagId = -1;

                    // タグが既に存在するか確認
                    tagSelectStmt = connection.prepareStatement("SELECT tag_id FROM tags WHERE tag_name = ?");
                    tagSelectStmt.setString(1, tagName);
                    ResultSet rs = tagSelectStmt.executeQuery();
                    if (rs.next()) {
                        tagId = rs.getInt("tag_id");
                    } else {
                        // なければ新規作成
                        tagInsertStmt = connection.prepareStatement(
                            "INSERT INTO tags (tag_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS
                        );
                        tagInsertStmt.setString(1, tagName);
                        tagInsertStmt.executeUpdate();
                        ResultSet generated = tagInsertStmt.getGeneratedKeys();
                        if (generated.next()) {
                            tagId = generated.getInt(1);
                        }
                        tagInsertStmt.close();
                    }
                    tagSelectStmt.close();

                    // 店舗とタグの紐付けを追加
                    linkStmt = connection.prepareStatement("INSERT INTO store_tags (store_id, tag_id) VALUES (?, ?)");
                    linkStmt.setInt(1, storedetail.getStoreId());
                    linkStmt.setInt(2, tagId);
                    linkStmt.executeUpdate();
                    linkStmt.close();
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (storeStmt != null) storeStmt.close();
            if (tagSelectStmt != null) tagSelectStmt.close();
            if (tagInsertStmt != null) tagInsertStmt.close();
            if (linkStmt != null) linkStmt.close();
            if (connection != null) connection.close();
        }
    }


    public StoreDetail getWithTags(int storeId) throws Exception {
        StoreDetail storedetail = null;

        try (Connection connection = getConnection()) {
            // 店舗詳細を取得
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM store_details WHERE store_id=?"
            );
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                storedetail = new StoreDetail();
                storedetail.setStoreId(rs.getInt("store_id"));
                storedetail.setStoreHours(rs.getString("store_hours"));
                storedetail.setStoreClose(rs.getString("store_close"));
                storedetail.setStoreIntroduct(rs.getString("store_introduct"));
                storedetail.setSeatDetail(rs.getString("seat_detail"));
            }
            rs.close();
            stmt.close();

            // タグを取得
            PreparedStatement tagStmt = connection.prepareStatement(
                "SELECT t.tag_name FROM tags t INNER JOIN store_tags st ON t.tag_id = st.tag_id WHERE st.store_id=?"
            );
            tagStmt.setInt(1, storeId);
            ResultSet tagRs = tagStmt.executeQuery();

            List<String> tags = new ArrayList<>();
            while (tagRs.next()) {
                tags.add(tagRs.getString("tag_name"));
            }
            storedetail.setTags(tags);

            tagRs.close();
            tagStmt.close();
        }

        return storedetail;
    }


}

