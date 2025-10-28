package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	            user.setUserName(resultSet.getString("user_name"));
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

	public boolean save(User user) throws Exception {
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;
        	try {
              // プリペアードステートメントにINSERT文をセット
              statement = connection.prepareStatement("insert into users(address, password, user_name, gender, user_tel) values(?,?,?,?,?");
              // プリペアードステートメントに値をバインド
              statement.setString(1, user.getAddress());
              statement.setString(2, user.getPassword());
              statement.setString(3, user.getUserName());
              statement.setInt(4, user.getGender());
              statement.setString(5, user.getUserTel());

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
	    }
}