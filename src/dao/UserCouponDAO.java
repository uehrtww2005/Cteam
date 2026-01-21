package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.UserCoupon;

public class UserCouponDAO extends DAO {


    /* =========================
    個人ユーザーへ一括配布
 ========================= */
 public int sendToAllUsers(int couponId, int storeId) throws Exception {

     Connection con = getConnection();
     PreparedStatement ps = null;
     int count = 0;

     try {
    	 String sql =
    			    "INSERT INTO user_coupon (user_id, coupon_id, store_id) " +
    			    "SELECT u.user_id, ?, ? " +
    			    "FROM users u " +
    			    "WHERE NOT EXISTS ( " +
    			    "    SELECT 1 FROM user_coupon uc " +
    			    "    WHERE uc.user_id = u.user_id " +
    			    "      AND uc.coupon_id = ? " +
    			    ")";


         ps = con.prepareStatement(sql);
         ps.setInt(1, couponId);
         ps.setInt(2, storeId);
         ps.setInt(3, couponId);

         count = ps.executeUpdate();
     } finally {
         if (ps != null) ps.close();
         con.close();
     }
     return count;
 }

 /* =========================
    団体ユーザーへ一括配布
 ========================= */
 public int sendToAllGroups(int couponId, int storeId) throws Exception {

     Connection con = getConnection();
     PreparedStatement ps = null;
     int count = 0;

     try {
         String sql =
             "INSERT INTO user_coupon (group_id, coupon_id, store_id) " +
             "SELECT g.group_id, ?, ? " +
             "FROM groups g " +
             "WHERE NOT EXISTS ( " +
             "    SELECT 1 FROM user_coupon uc " +
             "    WHERE uc.group_id = g.group_id " +
             "      AND uc.coupon_id = ? " +
             ")";

         ps = con.prepareStatement(sql);
         ps.setInt(1, couponId);
         ps.setInt(2, storeId);
         ps.setInt(3, couponId);

         count = ps.executeUpdate();
     } finally {
         if (ps != null) ps.close();
         con.close();
     }
     return count;
 }

 public List<UserCoupon> findByUserId(int userId) throws Exception {

	    List<UserCoupon> list = new ArrayList<>();
	    Connection con = getConnection();

	    String sql =
	        "SELECT " +
	        "  uc.user_coupon_id, uc.used, uc.received_at, " +
	        "  c.coupon_id, c.coupon_name, c.coupon_rank, c.coupon_introduct, " +
	        "  c.store_id, s.store_name " +
	        "FROM user_coupon uc " +
	        "JOIN coupon c ON uc.coupon_id = c.coupon_id " +
	        "JOIN stores s ON c.store_id = s.store_id " +
	        "WHERE uc.user_id = ? " +
	        "ORDER BY uc.used, uc.received_at DESC";

	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setInt(1, userId);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        UserCoupon uc = new UserCoupon();

	        uc.setUserCouponId(rs.getInt("user_coupon_id"));
	        uc.setCouponId(rs.getInt("coupon_id"));
	        uc.setStoreId(rs.getInt("store_id"));
	        uc.setStoreName(rs.getString("store_name")); // ★ 追加
	        uc.setCouponName(rs.getString("coupon_name"));
	        uc.setCouponRank(rs.getString("coupon_rank"));
	        uc.setCouponIntroduct(rs.getString("coupon_introduct"));
	        uc.setUsed(rs.getBoolean("used"));
	        uc.setReceivedAt(rs.getTimestamp("received_at"));

	        list.add(uc);
	    }

	    rs.close();
	    ps.close();
	    con.close();

	    return list;
	}


 public List<UserCoupon> findByGroupId(int groupId) throws Exception {

	    List<UserCoupon> list = new ArrayList<>();
	    Connection con = getConnection();

	    String sql =
	        "SELECT " +
	        "  uc.user_coupon_id, uc.used, uc.received_at, " +
	        "  c.coupon_id, c.coupon_name, c.coupon_rank, c.coupon_introduct, " +
	        "  c.store_id, s.store_name " +
	        "FROM user_coupon uc " +
	        "JOIN coupon c ON uc.coupon_id = c.coupon_id " +
	        "JOIN stores s ON c.store_id = s.store_id " +
	        "WHERE uc.group_id = ? " +
	        "ORDER BY uc.used, uc.received_at DESC";

	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setInt(1, groupId);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        UserCoupon uc = new UserCoupon();

	        uc.setUserCouponId(rs.getInt("user_coupon_id"));
	        uc.setCouponId(rs.getInt("coupon_id"));
	        uc.setStoreId(rs.getInt("store_id"));
	        uc.setStoreName(rs.getString("store_name")); // ★ 追加
	        uc.setCouponName(rs.getString("coupon_name"));
	        uc.setCouponRank(rs.getString("coupon_rank"));
	        uc.setCouponIntroduct(rs.getString("coupon_introduct"));
	        uc.setUsed(rs.getBoolean("used"));
	        uc.setReceivedAt(rs.getTimestamp("received_at"));

	        list.add(uc);
	    }

	    rs.close();
	    ps.close();
	    con.close();

	    return list;
	}


 public int markAsUsed(int userCouponId) throws Exception {

	    Connection con = getConnection();
	    PreparedStatement ps = null;

	    String sql =
	        "UPDATE user_coupon SET used = TRUE WHERE user_coupon_id = ?";

	    ps = con.prepareStatement(sql);
	    ps.setInt(1, userCouponId);

	    int result = ps.executeUpdate();

	    ps.close();
	    con.close();

	    return result;
	}




}
