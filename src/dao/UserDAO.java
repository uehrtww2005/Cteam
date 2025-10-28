package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;

public class UserDAO extends DAO{

	public User get(String address) throws Exception {
	    User user = null; // 初期値は null にしておく
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement("SELECT * FROM users WHERE address=?");
	        statement.setString(1, address);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            user = new User();
	            user.setUserId(resultSet.getInt("user_id"));
	            user.setPassword(resultSet.getString("password"));
	            user.setUserName(resultSet.getString("admin_name"));
	        }
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return user;
	}

	public User login(String address, String password) throws Exception {
	    User user = get(address);

	    if (user == null || !user.getPassword().equals(password)) {
	        return null;
	    }

	    return user;
	}

	/*public boolean save(User user) throws Exception {
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;

        try {
            // データベースから学生を取得
            User old = get(user.getNo());
            if (old == null) {
                // 学生が存在しなかった場合
                // プリペアードステートメントにINSERT文をセット
                statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
                // プリペアードステートメントに値をバインド
                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.isAttend());
                statement.setString(6, student.getSchool().getCd());
            }

            // プリペアードステートメントを実行
            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        if (count > 0) {
            // 実行件数が1件以上ある場合
            return true;
        } else {
            // 実行件数が0件の場合
            return false;
        }
    }*/
}