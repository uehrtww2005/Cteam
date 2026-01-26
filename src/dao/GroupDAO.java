package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Group;

public class GroupDAO extends DAO{

	public Group get(String leaderAddress) throws Exception {
	    Group group = null; // 初期値は null にしておく
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement("SELECT * FROM groups WHERE leader_address=?");
	        statement.setString(1, leaderAddress);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            group = new Group();
	            group.setGroupId(resultSet.getInt("group_id"));
	            group.setLeaderAddress(resultSet.getString("leader_address"));
	            group.setPassword(resultSet.getString("password"));
	            group.setLeaderName(resultSet.getString("leader_name"));
	            group.setLeaderTel(resultSet.getString("leader_tel"));
	            group.setRank(resultSet.getString("rank"));
	            group.setPrepaidAmount(resultSet.getInt("prepaid_amount"));
	            group.setStatus(resultSet.getInt("status"));

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


	public List<Group> findAll() throws Exception {
	    List<Group> list = new ArrayList<>();

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement("SELECT * FROM groups ORDER BY group_id");
	         ResultSet rs = statement.executeQuery()) {

	        while (rs.next()) {
	            Group group = new Group();
	            group.setGroupId(rs.getInt("group_id"));
	            group.setLeaderAddress(rs.getString("leader_address"));
	            group.setPassword(rs.getString("password"));
	            group.setLeaderName(rs.getString("leader_name"));
	            group.setLeaderTel(rs.getString("leader_tel"));
	            group.setRank(rs.getString("rank"));
	            group.setPrepaidAmount(rs.getInt("prepaid_amount"));
	            group.setStatus(rs.getInt("status"));
	            list.add(group);
	        }
	    }

	    return list;
	}

	// ランク更新
    public void updateRank(int groupId, String newRank) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            String sql = "UPDATE groups SET rank=? WHERE group_id=?";
            st = con.prepareStatement(sql);
            st.setString(1, newRank);
            st.setInt(2, groupId);
            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }

 // 先払い額更新
    public void updatePrepaidAmount(int groupId, int newAmount) throws Exception {
        Connection con = getConnection();
        String sql = "UPDATE groups SET prepaid_amount=? WHERE group_id=?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, newAmount);
        st.setInt(2, groupId);
        st.executeUpdate();
        st.close();
        con.close();
    }


    public void update(Group group) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            // パスワード変更あり／なしでSQLを分岐
            if (group.getPassword() != null && !group.getPassword().isEmpty()) {

                String sql = "UPDATE groups "
                           + "SET leader_name = ?, leader_address = ?, leader_tel = ?, password = ? "
                           + "WHERE group_id = ?";

                st = con.prepareStatement(sql);
                st.setString(1, group.getLeaderName());
                st.setString(2, group.getLeaderAddress());
                st.setString(3, group.getLeaderTel());
                st.setString(4, group.getPassword());
                st.setInt(5, group.getGroupId());

            } else {

                String sql = "UPDATE groups "
                           + "SET leader_name = ?, leader_address = ?, leader_tel = ? "
                           + "WHERE group_id = ?";

                st = con.prepareStatement(sql);
                st.setString(1, group.getLeaderName());
                st.setString(2, group.getLeaderAddress());
                st.setString(3, group.getLeaderTel());
                st.setInt(4, group.getGroupId());
            }

            st.executeUpdate();

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }



}