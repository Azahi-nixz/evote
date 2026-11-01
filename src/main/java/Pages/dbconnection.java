package Pages;

import java.sql.*;

public class dbconnection {

    public static Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swing_demo", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void createDatabase() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/", "root", "");
            PreparedStatement stmt = con.prepareStatement(
                    "CREATE DATABASE IF NOT EXISTS swing_demo");
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {

        try {
            Connection con = connect();

            String[] tables = {
                "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(100) NOT NULL UNIQUE, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "region VARCHAR(100), "
                    + "is_verified TINYINT(1) DEFAULT 0"
                    + ")",

                "CREATE TABLE IF NOT EXISTS candidates ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "party VARCHAR(50), "
                    + "position VARCHAR(50)"
                    + ")",

                "CREATE TABLE IF NOT EXISTS election_settings ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "is_started TINYINT(1) DEFAULT 0"
                    + ")",

                "CREATE TABLE IF NOT EXISTS votes ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL, "
                    + "candidate_id INT NOT NULL, "
                    + "UNIQUE KEY unique_vote (username)"
                    + ")",

                "CREATE TABLE IF NOT EXISTS candidacy_applications ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "candidate_id VARCHAR(50), "
                    + "name VARCHAR(50), "
                    + "party VARCHAR(50), "
                    + "status VARCHAR(20) DEFAULT 'pending', "
                    + "voter_id VARCHAR(50)"
                    + ")"
            };

            for (String sql : tables) {
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.executeUpdate();
            }

            // Seed election_settings if empty
            PreparedStatement check = con.prepareStatement(
                    "SELECT COUNT(*) FROM election_settings");
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                PreparedStatement seed = con.prepareStatement(
                        "INSERT INTO election_settings (is_started) VALUES (0)");
                seed.executeUpdate();
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
