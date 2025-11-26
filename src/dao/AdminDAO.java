package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;

public class AdminDAO extends DAO {

    // 既存のメソッド: 管理者情報の取得
    public Admin get(String adminName) throws Exception {
        Admin admin = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM admin WHERE admin_name=?");
            statement.setString(1, adminName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setPassword(resultSet.getString("password"));
                admin.setAdminName(resultSet.getString("admin_name"));
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return admin;
    }

    // 既存のメソッド: ログイン処理
    public Admin login(String adminName, String password) throws Exception {
        Admin admin = get(adminName);

        if (admin == null || !admin.getPassword().equals(password)) {
            return null;
        }

        return admin;
    }

    // ★追加メソッド: 利用ステータスの更新
    public boolean updateStatus(String targetType, int id, int newStatus) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        String sql = "";

        // 対象テーブルによってSQLを切り替え
        if ("user".equals(targetType)) {
            sql = "UPDATE users SET status=? WHERE user_id=?";
        } else if ("group".equals(targetType)) {
            sql = "UPDATE groups SET status=? WHERE group_id=?";
        } else if ("store".equals(targetType)) {
            sql = "UPDATE stores SET status=? WHERE store_id=?";
        } else {
            // 対象が不正な場合はfalseを返す
            if (connection != null) connection.close();
            return false;
        }

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, newStatus); // 0 or 1
            statement.setInt(2, id);        // ID

            int line = statement.executeUpdate();
            return line > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}