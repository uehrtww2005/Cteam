package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Group;

public class GroupDAO extends DAO{

	public Group get(String leaderAddress) throws Exception {
	    Group group = null; // 初期値は null にしておく
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement("SELECT * FROM groups WHERE leaderAddress=?");
	        statement.setString(1, leaderAddress);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            group = new Group();
	            group.setGroupId(resultSet.getInt("group_id"));
	            group.setLeaderAddress(resultSet.getString("leader_address"));
	            group.setPassword(resultSet.getString("password"));
	            group.setLeaderName(resultSet.getString("leader_name"));
	            group.setLeaderTel(resultSet.getString("leader_tel"));

	        }
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return group;
	}

	public Group login(String leaderAddress, String password) throws Exception {
	    Group group = get(leaderAddress);

	    if (group == null || !group.getPassword().equals(password)) {
	        return null;
	    }

	    return group;
	}

	public boolean save(Group group) throws Exception {
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;
        	try {
              // プリペアードステートメントにINSERT文をセット
              statement = connection.prepareStatement("insert into groups(leader_address, password, leader_name, leader_tel) values(?,?,?,?)");
              // プリペアードステートメントに値をバインド
              statement.setString(1, group.getLeaderAddress());
              statement.setString(2, group.getPassword());
              statement.setString(3, group.getLeaderName());
              statement.setString(4, group.getLeaderTel());

              count = statement.executeUpdate(); //大事



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