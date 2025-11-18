package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Inquiry;

public class InquiryDAO {

    public void insert(Connection con, Inquiry inquiry) throws Exception {
        String sql = "INSERT INTO inquiry(tel, content, user_id, store_id, group_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, inquiry.getTel());
            st.setString(2, inquiry.getContent());
            if (inquiry.getUserId() != null) st.setInt(3, inquiry.getUserId());
            else st.setNull(3, java.sql.Types.INTEGER);
            if (inquiry.getStoreId() != null) st.setInt(4, inquiry.getStoreId());
            else st.setNull(4, java.sql.Types.INTEGER);
            if (inquiry.getGroupId() != null) st.setInt(5, inquiry.getGroupId());
            else st.setNull(5, java.sql.Types.INTEGER);
            st.executeUpdate();
        }
    }

 // 管理者用：全件取得（User名・店舗名・グループ名付き）
    public List<Inquiry> findAll(Connection con) throws Exception {

        String sql =
            "SELECT i.inquiry_id, i.tel, i.content, i.created_at, " +
            "       i.user_id, u.user_name, " +
            "       i.store_id, s.store_name, " +
            "       i.group_id, g.leader_name " +
            "FROM inquiry i " +
            "LEFT JOIN users u ON i.user_id = u.user_id " +
            "LEFT JOIN stores s ON i.store_id = s.store_id " +
            "LEFT JOIN groups g ON i.group_id = g.group_id " +
            "ORDER BY i.created_at DESC";

        List<Inquiry> list = new ArrayList<>();

        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Inquiry inq = new Inquiry();
                inq.setInquiryId(rs.getInt("inquiry_id"));
                inq.setTel(rs.getString("tel"));
                inq.setContent(rs.getString("content"));
                inq.setCreatedAt(rs.getTimestamp("created_at"));

                // --- IDと名前セット ---
                int userId = rs.getInt("user_id");
                inq.setUserId(rs.wasNull() ? null : userId);
                inq.setUserName(rs.getString("user_name"));  // ★追加

                int storeId = rs.getInt("store_id");
                inq.setStoreId(rs.wasNull() ? null : storeId);
                inq.setStoreName(rs.getString("store_name")); // ★追加

                int groupId = rs.getInt("group_id");
                inq.setGroupId(rs.wasNull() ? null : groupId);
                inq.setLeaderName(rs.getString("leader_name")); // ★追加

                list.add(inq);
            }
        }

        return list;
    }
}
