package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Admin;

public class AdminDAO extends DAO{

	public Admin get(String adminName) throws Exception {
	    Admin admin = null; // 初期値は null にしておく
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

	public Admin login(String adminName, String password) throws Exception {
	    Admin admin = get(adminName);

	    if (admin == null || !admin.getPassword().equals(password)) {
	        return null;
	    }

	    return admin;
	}
}