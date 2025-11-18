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

    // 管理者用：全件取得
    public List<Inquiry> findAll(Connection con) throws Exception {
        String sql = "SELECT inquiry_id, tel, content, user_id, store_id, group_id, created_at FROM inquiry ORDER BY created_at DESC";
        List<Inquiry> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Inquiry inq = new Inquiry();
                inq.setInquiryId(rs.getInt("inquiry_id"));
                inq.setTel(rs.getString("tel"));
                inq.setContent(rs.getString("content"));
                int userId = rs.getInt("user_id");
                inq.setUserId(rs.wasNull() ? null : userId);
                int storeId = rs.getInt("store_id");
                inq.setStoreId(rs.wasNull() ? null : storeId);
                int groupId = rs.getInt("group_id");
                inq.setGroupId(rs.wasNull() ? null : groupId);
                list.add(inq);
            }
        }
        return list;
    }
}
