package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Menu;

public class MenuDAO extends DAO {

    // 検索処理
    public List<Menu> search(String keyword) throws Exception {
        List<Menu> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM menu WHERE menu_name LIKE ? ORDER BY store_id"
        );
        st.setString(1, "%" + keyword + "%");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Menu menu = new Menu();
            menu.setMenuId(rs.getInt("menu_id"));
            menu.setStoreId(rs.getInt("store_id"));
            menu.setMenuName(rs.getString("menu_name"));
            menu.setPrice(rs.getInt("price"));
            list.add(menu);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // 追加処理
    public int insert(Menu menu, int storeId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "INSERT INTO menu (store_id, menu_name, price) VALUES (?, ?, ?)"
        );
        st.setInt(1, storeId);  // ← ログイン中の店IDをここにセット
        st.setString(2, menu.getMenuName());
        st.setInt(3, menu.getPrice());

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }

    //更新処理
    public int update(Menu menu, int storeId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "UPDATE menu SET menu_name = ?, price = ? WHERE menu_id = ? AND store_id = ?"
        );
        st.setString(1, menu.getMenuName());
        st.setInt(2, menu.getPrice());
        st.setInt(3, menu.getMenuId());
        st.setInt(4, storeId); // ← ログイン中の店IDを追加条件にする

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }


    // 全件取得
    public List<Menu> findAll() throws Exception {
        List<Menu> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM menu ORDER BY menu_id"
        );
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Menu menu = new Menu();
            menu.setMenuId(rs.getInt("menu_id"));
            menu.setStoreId(rs.getInt("store_id"));
            menu.setMenuName(rs.getString("menu_name"));
            menu.setPrice(rs.getInt("price"));
            list.add(menu);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    //削除処理
    public int delete(int menuId, int storeId) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM menu WHERE menu_id = ? AND store_id = ?"
        );
        st.setInt(1, menuId);
        st.setInt(2, storeId); // ← ログイン中の店IDを条件に追加

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }

}
