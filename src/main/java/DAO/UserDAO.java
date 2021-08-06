package DAO;

import Models.User;

import java.sql.*;

public class UserDAO {
    // SQL: https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm

    static final String DB_NAME     = "servlet_test";
    static final String DB_URL      = "jdbc:mysql://localhost/" + DB_NAME;
    static final String USER        = "root";
    static final String PASS        = "";
    static final String DB_DRIVER   = "com.mysql.jdbc.Driver";

    public UserDAO() {

    }

    public void CreateTable() {
        Statement       stmt    = null;
        Connection      con     = null;
        System.out.println("======= CONSTRUCTOR OUT =======");
        try  {
            System.out.println("======= CONSTRUCTOR IN =======");
            Class.forName(DB_DRIVER);
            con     = DriverManager.getConnection(DB_URL, USER, PASS);
            String      sSql    = "CREATE TABLE IF NOT EXISTS users (" +
                    "id         INT AUTO_INCREMENT PRIMARY KEY," +
                    "email      varchar(255) NOT NULL UNIQUE," +
                    "username   varchar(255)," +
                    "password   varchar(255)," +
                    "dob        varchar(255)," +
                    "sex        varchar(255)," +
                    "roles      int(10)" +
                    ")";
            if (con != null) {
                stmt            = con.createStatement();
                stmt.executeUpdate(sSql);
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)    con.close();
                if (stmt != null)   stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public boolean CreateUser(User x) {
        CreateTable();
        String              sSql        = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)";
        Connection          connection  = null;
        PreparedStatement   stmt        = null;

        try {
            connection = getConnection();
            if (connection != null) {
                stmt    = connection.prepareStatement(sSql);
                stmt.setString(1, x.getEmail());
                stmt.setString(2, x.getPassword());
                stmt.setString(3, x.getUsername());
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null)       stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public User getAUser(String email, String password) {
        String              sSql        = "SELECT id, username, password, signature FROM users WHERE email = ? LIMIT 1";
        Connection          connection  = null;
        PreparedStatement   stmt        = null;
        ResultSet           res         = null;
        try {
            connection = getConnection();
            if (connection != null) {
                stmt    = connection.prepareStatement(sSql);
                stmt.setString(1, email);
                System.out.println(sSql);
                res     = stmt.executeQuery();
                while (res.next()) {
                    User x = new User();
                    x.setId(res.getInt("id"));
                    x.setUsername(res.getString("username"));
                    x.setPassword(res.getString("password"));
                    x.setSecreteKey(res.getString("secretKey"));
                    x.setPublicKey(res.getString("publicKey"));
                    return x;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null)       stmt.close();
                if (res != null)        res.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
