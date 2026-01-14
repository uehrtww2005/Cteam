package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.StorePrepayment;

public class StorePrepaymentDAO extends DAO {

    // 店舗IDで取得（表示用）
    public StorePrepayment findByStoreId(Integer storeId) throws Exception {

        if (storeId == null) return null;

        StorePrepayment sp = null;
        Connection con = getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT store_id, prepayment_rate FROM store_prepayment WHERE store_id = ?"
        );
        ps.setInt(1, storeId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            sp = new StorePrepayment();
            sp.setStoreId(rs.getInt("store_id"));
            sp.setPrepaymentRate(rs.getInt("prepayment_rate"));
        }

        rs.close();
        ps.close();
        con.close();

        return sp;
    }

    // 登録 or 更新（UPSERT）
    public void save(StorePrepayment sp) throws Exception {

        Connection con = getConnection();

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO store_prepayment (store_id, prepayment_rate) " +
            "VALUES (?, ?) " +
            "ON CONFLICT (store_id) DO UPDATE SET " +
            "prepayment_rate = EXCLUDED.prepayment_rate, " +
            "updated_at = CURRENT_TIMESTAMP"
        );

        ps.setInt(1, sp.getStoreId());
        ps.setInt(2, sp.getPrepaymentRate());

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
