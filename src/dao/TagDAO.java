package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Tag;

public class TagDAO extends DAO {

    // タグ全件取得
    public List<Tag> findAll() throws Exception {
        List<Tag> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tags ORDER BY tag_id");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTagId(rs.getInt("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
                list.add(tag);
            }
        }

        return list;
    }

    // タグ名で取得（なければnull）
    public Tag findByName(String tagName) throws Exception {
        Tag tag = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tags WHERE tag_name = ?")) {
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

    // タグを新規登録してIDを返す
    public int insertAndReturnId(String tagName) throws Exception {
        int tagId = -1;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO tags(tag_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tagName);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    tagId = rs.getInt(1);
                }
            }
        }

        return tagId;
    }
}
