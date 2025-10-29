//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import bean.Menu;
//import bean.Product;
//
//public class MenuDAO extends DAO{
//
//	public List<Menu> search(String keyword) throws Exception {
//		List<Menu> list=new ArrayList<>();
//
//		Connection con=getConnection();
//
//		PreparedStatement st=con.prepareStatement(
//				"select * from product where name like ? order by id");
//		st.setString(1, "%"+keyword+"%");
//		ResultSet rs=st.executeQuery();
//
//		while (rs.next()) {
//			Menu menu=new Menu();
//			menu.setMenuId(rs.getInt("menu_id"));
//			menu.setName(rs.getString("name"));
//			menu.setPrice(rs.getInt("price"));
//			menu.setStatus(rs.getInt("status"));
//			list.add(menu);
//		}
//
//		st.close();
//		con.close();
//
//		return list;
//	}
//
//	public int insert(Product product) throws Exception {
//		Connection con=getConnection();
//
//		PreparedStatement st=con.prepareStatement(
//				"insert into product values(null, ?, ?)");
//		st.setString(1, product.getName());
//		st.setInt(2, product.getPrice());
//		int line=st.executeUpdate();
//
//		st.close();
//		con.close();
//		return line;
//	}
//
//	public int update(List<Product> product) throws Exception {
//		Connection con=getConnection();
//
//		PreparedStatement st=con.prepareStatement(
//				"update product set status = ? where id = ?");
//
//		for (Product p : product) {
//			st.setInt(1, p.getStatus());
//			st.setInt(2, p.getId());
//			st.addBatch();
//		}
//
//		int[] results = st.executeBatch();
//		st.close();
//		con.close();
//
//		int total = 0;
//		for (int r :results) {
//			total += r;
//		}
//
//		return total;
//	}
//
//	public List<Product> findAll() throws Exception {
//		List<Product> list=new ArrayList<>();
//
//		Connection con=getConnection();
//
//		PreparedStatement st=con.prepareStatement(
//				"select * from product order by id");
//		ResultSet rs=st.executeQuery();
//
//		while (rs.next()) {
//			Product p=new Product();
//			p.setId(rs.getInt("id"));
//			p.setName(rs.getString("name"));
//			p.setPrice(rs.getInt("price"));
//			p.setStatus(rs.getInt("status"));
//			list.add(p);
//		}
//
//		st.close();
//		con.close();
//
//		return list;
//	}
//
//}
