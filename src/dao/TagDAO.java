package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Tag;

public class TagDAO extends DAO {

    // 全タグ取得
    public List<Tag> findAll() throws Exception {
        List<Tag> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tags ORDER BY tag_name");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tag t = new Tag();
                t.setTagId(rs.getInt("tag_id"));
                t.setTagName(rs.getString("tag_name"));
                list.add(t);
            }
        }
        return list;
    }

    // IDからタグ取得
    public Tag findById(int id) throws Exception {
        Tag tag = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tags WHERE tag_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tag = new Tag();
                    tag.setTagId(rs.getInt("tag_id"));
                    tag.setTagName(rs.getString("tag_name"));
                }
            }
        }
        return tag;
    }

    // 追加：タグ名からタグ取得
    public Tag findByName(String tagName) throws Exception {
        Tag tag = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tags WHERE tag_name=?")) {
            ps.setString(1, tagName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tag = new Tag();
                    tag.setTagId(rs.getInt("tag_id"));
                    tag.setTagName(rs.getString("tag_name"));
                }
            }
        }
        return tag;
    }
}
