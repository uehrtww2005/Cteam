package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Prepay;

public class PrepayDAO extends DAO {

    /**
     * 先払い額を保存（store_id × course_id が存在する場合は更新）
     */
    public void savePrepayAmount(int storeId, int menuId, int prepayAmount) throws Exception {

        String sql =
            "INSERT INTO prepay_setting (store_id, menu_id, prepay_amount) " +
            "VALUES (?, ?, ?) " +
            "ON CONFLICT (store_id, menu_id) " +
            "DO UPDATE SET prepay_amount = EXCLUDED.prepay_amount";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, storeId);
        ps.setInt(2, menuId);
        ps.setInt(3, prepayAmount);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * 店舗に紐づくコースの先払い設定を取得
     */
    public List<Prepay> findByStore(int storeId) throws Exception {

        List<Prepay> list = new ArrayList<>();

        String sql =
            "SELECT store_id, menu_id, prepay_amount " +
            "FROM prepay_setting WHERE store_id = ?";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, storeId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Prepay p = new Prepay();
            p.setStoreId(rs.getInt("store_id"));
            p.setMenuId(rs.getInt("menu_id"));
            p.setPrepayAmount(rs.getInt("prepay_amount"));

            list.add(p);
        }

        rs.close();
        ps.close();

        return list;
    }

    /**
     * コース削除時の先払い設定削除
     */
    public void deleteByCourse(int storeId, int menuId) throws Exception {

        String sql = "DELETE FROM prepay_setting WHERE store_id = ? AND menu_id = ?";

        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, storeId);
        ps.setInt(2, menuId);

        ps.executeUpdate();
        ps.close();
    }
}
