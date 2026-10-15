
package Pages;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserRegistry {

    public static final Map<String, Boolean> users =
            new HashMap<>();

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/swing_demo";


    // LOAD USERS FROM WAMP
    public static void loadUsers() {

        String sql = "SELECT username, is_verified FROM users";

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo", "root", ""
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            Map<String, Boolean> loadedUsers = new HashMap<>();

            while (result.next()) {
                String username = result.getString("username");
                boolean verified = result.getBoolean("is_verified");

                loadedUsers.put(username, verified);
            }

            synchronized (users) {
                users.clear();
                users.putAll(loadedUsers);
            }

            System.out.println(
                    "User cache loaded: " + users.size() + " users."
            );

        } catch (SQLException e) {
            System.out.println("Failed to load user cache.");
            e.printStackTrace();
        }
    }

    // CHECK IF USERNAME EXISTS IN CACHE
    public static boolean userExists(String username) {
        synchronized (users) {
            return users.containsKey(username);
        }
    }

    // CHECK IF USER IS VERIFIED
    public static boolean isVerified(String username) {
        synchronized (users) {
            return Boolean.TRUE.equals(users.get(username));
        }
    }

    // ADD NEW USER TO CACHE
    public static void addUser(String username, boolean verified) {
        synchronized (users) {
            users.put(username, verified);
        }
    }

    // UPDATE VERIFICATION STATUS
    public static void updateUser(
            String username, boolean verified) {
        synchronized (users) {
            if (users.containsKey(username)) {
                users.put(username, verified);
            }
        }
    }
}