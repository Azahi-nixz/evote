
package Pages;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserRegistry {

    public static final Map<String, Boolean> users =
            new HashMap<>();

    // Voter map
    public static final Map<String, Voter> voters =
            new HashMap<>();

    // Current logged-in user
    public static String currentUser = null;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/swing_demo";


    // Load users from wamp
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

    // Check if username exists in cache
    public static boolean userExists(String username) {
        synchronized (users) {
            return users.containsKey(username);
        }
    }

    // Check if user is verified
    public static boolean isVerified(String username) {
        synchronized (users) {
            return Boolean.TRUE.equals(users.get(username));
        }
    }

    // Add new user to cache
    public static void addUser(String username, boolean verified) {
        synchronized (users) {
            users.put(username, verified);
        }
    }

    // Update verification status
    public static void updateUser(
            String username, boolean verified) {

        // Update in-memory cache
        synchronized (users) {
            if (users.containsKey(username)) {
                users.put(username, verified);
            }
        }

        // Update database
        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, "root", ""
                );
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE users SET is_verified = ? "
                                + "WHERE username = ?"
                )
        ) {
            statement.setBoolean(1, verified);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update verification.");
            e.printStackTrace();
        }
    }

    // Register a voter
    public static void registerVoter(Voter voter) {
        synchronized (voters) {
            voters.put(voter.getVoterId(), voter);
        }
    }

    // Get a voter by username
    public static Voter getVoter(String username) {
        synchronized (voters) {
            return voters.get(username);
        }
    }

    // Set current logged-in user
    public static void setCurrentUser(String username) {
        currentUser = username;
    }
}