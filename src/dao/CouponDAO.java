package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Coupon;

public class CouponDAO extends DAO{

    public List<Coupon> findByStoreId(int storeId) throws Exception {
        List<Coupon> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM coupon WHERE store_id = ? ORDER BY coupon_id"
        );
        st.setInt(1, storeId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Coupon coupon = new Coupon();
            coupon.setCouponId(rs.getInt("coupon_id"));
            coupon.setStoreId(rs.getInt("store_id"));
            coupon.setCouponName(rs.getString("coupon_name"));
            coupon.setCouponIntroduct(rs.getString("coupon_introduct"));
            coupon.setCouponRank(rs.getString("coupon_rank"));
            list.add(coupon);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }


    // ★ クーポン追加
    public int insert(Coupon coupon, int storeId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
        		"INSERT INTO coupon (store_id, coupon_name, coupon_introduct, coupon_rank) VALUES (?, ?, ?, ?)"

        );
        st.setInt(1, storeId);
        st.setString(2, coupon.getCouponName());
        st.setString(3, coupon.getCouponIntroduct());
        st.setString(4, coupon.getCouponRank());

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }

    // ★ 削除処理
    public int delete(int couponId, int storeId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM coupon WHERE coupon_id = ? AND store_id = ?"
        );
        st.setInt(1, couponId);
        st.setInt(2, storeId);

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }
}
