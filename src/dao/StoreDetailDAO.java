package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.StoreDetail;
import bean.Tag;

public class StoreDetailDAO extends DAO {

    // store_details 取得
    public StoreDetail get(int storeId) throws Exception {
        StoreDetail detail = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM store_details WHERE store_id=?")) {
            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detail = new StoreDetail();
                    detail.setStoreId(rs.getInt("store_id"));
                    detail.setStoreHours(rs.getString("store_hours"));
                    detail.setStoreClose(rs.getString("store_close"));
                    detail.setStoreIntroduct(rs.getString("store_introduct"));
                    detail.setSeatDetail(rs.getString("seat_detail"));
                }
            }
        }
        return detail;
    }

    // store_details 更新
    public boolean update(StoreDetail detail) throws Exception {
        int count;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE store_details SET store_hours=?, store_close=?, store_introduct=?, seat_detail=? WHERE store_id=?")) {
            ps.setString(1, detail.getStoreHours());
            ps.setString(2, detail.getStoreClose());
            ps.setString(3, detail.getStoreIntroduct());
            ps.setString(4, detail.getSeatDetail());
            ps.setInt(5, detail.getStoreId());
            count = ps.executeUpdate();
        }
        return count > 0;
    }

    // store_details 初回保存
    public boolean save(StoreDetail detail) throws Exception {
        int count;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO store_details(store_id, store_hours, store_close, store_introduct, seat_detail) VALUES (?, ?, ?, ?, ?)")) {
            ps.setInt(1, detail.getStoreId());
            ps.setString(2, detail.getStoreHours());
            ps.setString(3, detail.getStoreClose());
            ps.setString(4, detail.getStoreIntroduct());
            ps.setString(5, detail.getSeatDetail());
            count = ps.executeUpdate();
        }
        return count > 0;
    }

    // store_tags 保存（1つだけ）
    public void saveSelectedTag(StoreDetail detail) throws Exception {
        try (Connection con = getConnection()) {
            // 既存削除
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM store_tags WHERE store_id=?")) {
                ps.setInt(1, detail.getStoreId());
                ps.executeUpdate();
            }
            // 新規登録
            if (detail.getSelectedTag() != null) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO store_tags(store_id, tag_id) VALUES (?, ?)")) {
                    ps.setInt(1, detail.getStoreId());
                    ps.setInt(2, detail.getSelectedTag().getTagId());
                    ps.executeUpdate();
                }
            }
        }
    }

    // タグ付きで取得
    public StoreDetail getWithSelectedTag(int storeId) throws Exception {
        StoreDetail detail = get(storeId);
        if (detail == null) return null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT t.tag_id, t.tag_name FROM tags t " +
                 "INNER JOIN store_tags st ON t.tag_id = st.tag_id WHERE st.store_id=?")) {
            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tag t = new Tag();
                    t.setTagId(rs.getInt("tag_id"));
                    t.setTagName(rs.getString("tag_name"));
                    detail.setSelectedTag(t);
                }
            }
        }
        return detail;
    }

    // 存在確認
    public boolean exists(int storeId) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT 1 FROM store_details WHERE store_id=?")) {
            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
