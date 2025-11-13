package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StoreTagDAO extends DAO {

    // 店舗にタグを紐づける
    public void addTagToStore(int storeId, int tagId) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO store_tags (store_id, tag_id) VALUES (?, ?) ON CONFLICT DO NOTHING")) {
            ps.setInt(1, storeId);
            ps.setInt(2, tagId);
            ps.executeUpdate();
        }
    }

    // 店舗IDからタグ一覧を取得
    public List<String> getTagsByStoreId(int storeId) throws Exception {
        List<String> tags = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT t.tag_name FROM tags t JOIN store_tags st ON t.tag_id = st.tag_id WHERE st.store_id = ?")) {
            ps.setInt(1, storeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tags.add(rs.getString("tag_name"));
                }
            }
        }

        return tags;
    }

    // タグ名から店舗IDリストを取得
    public List<Integer> getStoreIdsByTag(String tagName) throws Exception {
        List<Integer> storeIds = new ArrayList<>();

        String sql = "SELECT st.store_id FROM store_tags st JOIN tags t ON st.tag_id = t.tag_id WHERE t.tag_name = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tagName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    storeIds.add(rs.getInt("store_id"));
                }
            }
        }

        return storeIds;
    }
}
