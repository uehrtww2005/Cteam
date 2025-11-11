package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.User;

public class UserDAO extends DAO {

    public User get(String address) throws Exception {
        User user = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE address=?");
            statement.setString(1, address);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setAddress(resultSet.getString("address")); // ←★これが抜けていた！
                user.setPassword(resultSet.getString("password"));
                user.setUserName(resultSet.getString("user_name"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserTel(resultSet.getString("user_tel"));
                user.setRank(resultSet.getString("rank"));
                user.setPrepaidAmount(resultSet.getInt("prepaid_amount"));
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return user;
    }

    public User login(String address, String password) throws Exception {
        User user = get(address);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public boolean save(User user) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO users(address, password, user_name, gender, user_tel) VALUES(?,?,?,?,?)"
            );

            statement.setString(1, user.getAddress());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserName());
            statement.setInt(4, user.getGender());
            statement.setString(5, user.getUserTel());

            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }

    public List<User> findAll() throws Exception {
        List<User> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ORDER BY user_id");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("user_name"));
                user.setGender(rs.getInt("gender"));
                user.setUserTel(rs.getString("user_tel"));
                user.setRank(rs.getString("rank"));
                user.setPrepaidAmount(rs.getInt("prepaid_amount"));
                list.add(user);
            }
        }

        return list;
    }

    public void updateRank(int userId, String newRank) throws Exception {
        Connection con = getConnection();
        String sql = "UPDATE user SET rank=? WHERE user_id=?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, newRank);
        st.setInt(2, userId);
        st.executeUpdate();
        st.close();
        con.close();
    }

}
