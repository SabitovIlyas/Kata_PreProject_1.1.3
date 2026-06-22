package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection conn = util.connect()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastname VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL" +
                    ")";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection conn = util.connect()) {
            String sql = "DROP TABLE IF EXISTS users";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = util.connect()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)"
            );
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = util.connect()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM users WHERE id=?"
            );
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = util.connect()) {
            String sql = "SELECT * FROM users";
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery(sql);

            while (set.next()) {
                long id = set.getLong("id");
                String name = set.getString("name");
                String lastName = set.getString("lastname");
                Byte age = set.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection conn = util.connect()) {
            String sql = "DELETE FROM users";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL!");
            throw new RuntimeException(e);
        }
    }
}
