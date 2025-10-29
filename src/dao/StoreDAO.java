package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Store;

public class StoreDAO extends DAO{

	public Store get(String storeTel) throws Exception {
	    Store store = null; // 初期値は null にしておく
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement("SELECT * FROM stores WHERE storeTel=?");
	        statement.setString(1, storeTel);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            store = new Store();
	            store.setStoreId(resultSet.getInt("group_id"));
	            store.setStoreAddress(resultSet.getString("store_address"));
	            store.setPassword(resultSet.getString("password"));
	            store.setStoreName(resultSet.getString("store_name"));
	            store.setStoreTel(resultSet.getString("leader_tel"));

	        }
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return store;
	}

	public Store login(String storeTel, String password) throws Exception {
	    Store store = get(storeTel);

	    if (store == null || !store.getPassword().equals(password)) {
	        return null;
	    }

	    return store;
	}


	public boolean save(Store store) throws Exception {
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;
        	try {
              // プリペアードステートメントにINSERT文をセット
              statement = connection.prepareStatement("insert into stores(store_address, password, store_name, store_tel) values(?,?,?,?)");
              // プリペアードステートメントに値をバインド
              statement.setString(1, store.getStoreAddress());
              statement.setString(2, store.getPassword());
              statement.setString(3, store.getStoreName());
              statement.setString(4, store.getStoreTel());

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
